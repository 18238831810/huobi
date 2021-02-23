package com.cf.crs.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.OrderEntity;
import com.cf.crs.entity.SellLimit;
import com.cf.crs.huobi.client.TradeClient;
import com.cf.crs.huobi.client.req.trade.CreateOrderRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.huobi.model.trade.Order;
import com.cf.crs.mapper.BuyLimitMapper;
import com.cf.crs.mapper.SellLimitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单
 */
@Slf4j
@Service
public class TradeService implements   AbstractHuobiPraramService{



    @Autowired
    AccountService accountService;

    @Autowired
    BuyLimitMapper buyLimitMapper;

    @Autowired
    SellLimitMapper sellLimitMapper;


    /**
     * 获取下单TradeClient
     *
     * @param apiKey
     * @param secretKey
     * @return
     */
    public TradeClient getTradeClient(String apiKey, String secretKey) {
        return TradeClient.create(HuobiOptions.builder().apiKey(apiKey).secretKey(secretKey).build());
    }

    public TradeClient getTradeClient() {
        return getTradeClient(apiKey, secretKey);
    }

    /**
     * 限价下单
     *
     * @param orderEntity 此处限价下单，主要填 apiKey，secretKey，symbol，price，amount，sellPrice，cancelTime
     */
    public Long createOrder(OrderEntity orderEntity, Account account) {
        try {
            Long orderId = getTradeClient().createOrder(CreateOrderRequest.spotBuyLimit(account.getId(), orderEntity.getSymbol(), new BigDecimal(orderEntity.getPrice()), new BigDecimal(orderEntity.getAmount())));
            //限价下单，存入数据库
            log.info("限价下单成功:{},{}", orderEntity.getAccountId(), JSON.toJSONString(orderEntity));
            //此处最好有短信提醒
            //下单数据入库
            saveBuyLimitOrder(orderEntity, orderId);
            return orderId;
        } catch (Exception e) {
            log.info("限价下单失败:{}", JSON.toJSONString(orderEntity));
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public Long createOrder(OrderEntity orderEntity)
    {
        return createOrder( orderEntity, accountService.getAccount());
    }

    /**
     * 生成出售订单
     *
     * @param buyLimit
     */
    public void createSellOrder(BuyLimit buyLimit, long now) {
        TradeClient tradeClient = getTradeClient();
        Order order = tradeClient.getOrder(buyLimit.getOrderId());

        //如果到时间还没有任何成交，直播取消
        if (order.getFilledAmount().longValue() == 0 && (buyLimit.getCancelTime() < now)) {
            cancalOrder(buyLimit,1);
            return;
        }
        if (order.getFilledAmount().longValue() > 0) {
            createNotAllFilledOrder(tradeClient, buyLimit, order, now);
        }
    }

    private void createNotAllFilledOrder(TradeClient tradeClient, BuyLimit buyLimit, Order order, long now) {
        BigDecimal total = createSellOrder(tradeClient, buyLimit);
        //如果下单了
        if (total.longValue() > 0) {
            //如果全成交
            if (order.getFilledAmount().compareTo(order.getAmount()) == 0) {
                updateStauts(buyLimit.getId(), 2);
                return;
            }

            //如果有成交，但是时间未到了
            if ((buyLimit.getCancelTime() > now)) {
                updateStauts(buyLimit.getId(), 3);
                return;
            }

            //如果有成交，但是时间已经到了
            if ((buyLimit.getCancelTime() < now)) {
                updateStauts(buyLimit.getId(), 4);
                return;
            }
        }
        if(buyLimit.getCancelTime() < now)
        {
            cancalOrder(buyLimit,4);
        }

    }

    private BigDecimal createSellOrder(TradeClient tradeClient, BuyLimit buyLimit) {
        BigDecimal total = getSellTotal(buyLimit);
        if (total.longValue() > 0) {
            Long orderId = tradeClient.createOrder(CreateOrderRequest.spotSellLimit(buyLimit.getAccountId(), buyLimit.getSymbol(), new BigDecimal(buyLimit.getSellPrice()), total));
            if (orderId != null) {
                SellLimit sellLimit = SellLimit.builder().amount(buyLimit.getAmount()).buyId(buyLimit.getId()).ctime(System.currentTimeMillis()).price(buyLimit.getSellPrice())
                        .status(0).orderId(orderId.toString()).build();
                saveSellLimit(sellLimit);
            }
        }
        return total;
    }

    private void saveSellLimit(SellLimit sellLimit) {
        try {
            sellLimitMapper.insert(sellLimit);
        } catch (Exception e) {
            log.error("必须处理的【下卖单时出错】error->{} sellLimit->{}", e.getMessage(), sellLimit.toString());
        }
    }

    /**
     * 计算出要卖的量
     *
     * @param buyLimit
     * @return
     */
    private BigDecimal getSellTotal(BuyLimit buyLimit) {
        List<SellLimit> list = sellLimitMapper.selectList(new QueryWrapper<SellLimit>().eq("buyId", buyLimit.getId()));
        BigDecimal total = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(list))
            for (SellLimit sellLimit : list) {
                total.add(new BigDecimal(sellLimit.getAmount()));
            }
        return new BigDecimal(buyLimit.getAmount()).subtract(total);
    }

    /**
     * 限价下单入库
     *
     * @param orderEntity
     */
    private void saveBuyLimitOrder(OrderEntity orderEntity, Long orderId) {
        BuyLimit buyLimit = BuyLimit.builder().apiKey(apiKey).secretKey(secretKey).accountId(orderEntity.getAccountId()).
                price(orderEntity.getPrice()).amount(orderEntity.getAmount()).symbol(orderEntity.getSymbol()).
                sellPrice(orderEntity.getSellPrice()).createTime(System.currentTimeMillis()).
                cancelTime(orderEntity.getCancelTime()).status(0).orderId(orderId).unikey(orderEntity.getUnikey()).build();
        buyLimitMapper.insert(buyLimit);
    }


    public BuyLimit getByUnitKey(String unikey) {
        return buyLimitMapper.selectOne(new QueryWrapper<BuyLimit>().eq("unikey", unikey).last(" limit 1"));
    }

    /**
     * 获取等待中的订单
     *
     * @return
     */
    public List<BuyLimit> getBuyLimitList() {
        return buyLimitMapper.selectList(new QueryWrapper<BuyLimit>().eq("status", 0));
    }


    public List<BuyLimit> getExpireAndNotSucBuyLimit() {
        return buyLimitMapper.selectList(new QueryWrapper<BuyLimit>().in("status", 0, 3, 4));
    }

    public boolean cancalOrder(BuyLimit buyLimit,int status) {
        TradeClient tradeClient = getTradeClient(buyLimit.getApiKey(), buyLimit.getSecretKey());
        Long cancelId = tradeClient.cancelOrder(buyLimit.getOrderId());
        if (cancelId != null && (cancelId.longValue() == buyLimit.getOrderId().longValue())) {
            updateStauts(buyLimit.getId(), status);
            return true;
        }
        log.info("cancalOrder->(cancelId->{} id->{})", cancelId, buyLimit.getId());
        return false;
    }

    private void updateStauts(long id, int status) {
        try {
            buyLimitMapper.update(null, new UpdateWrapper<BuyLimit>().set("status", status).eq("id", id));
        } catch (Exception e) {
            log.error("必须处理的【更新买单时出错】error->{} id->{} status->{}", id, status);
        }

    }

}
