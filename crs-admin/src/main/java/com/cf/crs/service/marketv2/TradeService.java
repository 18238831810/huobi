package com.cf.crs.service.marketv2;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.SellLimit;
import com.cf.crs.huobi.client.TradeClient;
import com.cf.crs.huobi.client.req.trade.CreateOrderRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
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
public class TradeService  {


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


    /**
     * 限价下单
     *
     * @param buyLimit 此处限价下单，主要填 apiKey，secretKey，symbol，price，amount，sellPrice，cancelTime
     */
    public Long createOrder(BuyLimit buyLimit) {
        try {
            Long orderId = getTradeClient(buyLimit.getApiKey(),buyLimit.getSecretKey()).createOrder(CreateOrderRequest.spotBuyLimit(buyLimit.getAccountId(), buyLimit.getSymbol(), new BigDecimal(buyLimit.getPrice()), new BigDecimal(buyLimit.getAmount())));
            buyLimit.setOrderId(orderId);
            buyLimitMapper.insert(buyLimit);
            return orderId;
        } catch (Exception e) {
            log.info("限价下单失败:{}", JSON.toJSONString(buyLimit));
           // log.error(e.getMessage(), e);
            log.error("error->{}",e.getMessage());
            return null;
        }
    }

    /**
     * 生成出售订单
     *
     * @param buyLimit
     */
    public void createSellOrder(BuyLimit buyLimit, long now) {
        TradeClient tradeClient = getTradeClient(buyLimit.getApiKey(),buyLimit.getSecretKey());
        Order order = tradeClient.getOrder(buyLimit.getOrderId());
        //如果到时间还没有任何成交，直播取消
        if (order.getFilledAmount().compareTo(BigDecimal.ZERO) == 0 && (buyLimit.getCancelTime() < now)) {
            cancelOrder(buyLimit, 1);
            return;
        }
        if (order.getFilledAmount().compareTo(BigDecimal.ZERO) > 0) {
            createNotAllFilledOrder(tradeClient, buyLimit, order, now);
        }
    }

    private void createNotAllFilledOrder(TradeClient tradeClient, BuyLimit buyLimit, Order order, long now) {
        BigDecimal total = createSellOrder(tradeClient, buyLimit);
        //如果下单了
        if (total.compareTo(BigDecimal.ZERO) > 0) {
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
        if (buyLimit.getCancelTime() < now) {
            cancelOrder(buyLimit, 4);
        }

    }

    private BigDecimal createSellOrder(TradeClient tradeClient, BuyLimit buyLimit) {
        BigDecimal total = getSellTotal(buyLimit);
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            Long orderId = tradeClient.createOrder(CreateOrderRequest.spotSellLimit(buyLimit.getAccountId(), buyLimit.getSymbol(), new BigDecimal(buyLimit.getSellPrice()), total));
            if (orderId != null) {
                SellLimit sellLimit = SellLimit.builder().amount(buyLimit.getAmount()).buyId(buyLimit.getId()).ctime(System.currentTimeMillis()).price(buyLimit.getSellPrice())
                        .marketId(buyLimit.getMarketId()).total(buyLimit.getTotal()).accountId(buyLimit.getAccountId()).apiKey(buyLimit.getApiKey()).secretKey(buyLimit.getSecretKey()).status(0).orderId(orderId.toString()).build();
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
        List<SellLimit> list = sellLimitMapper.selectList(new QueryWrapper<SellLimit>().eq("buy_id", buyLimit.getId()));
        BigDecimal total = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(list))
            for (SellLimit sellLimit : list) {
                total.add(new BigDecimal(sellLimit.getAmount()));
            }
        return new BigDecimal(buyLimit.getAmount()).multiply(FEI).setScale(BigDecimal.ROUND_DOWN).subtract(total);
    }

    private static final BigDecimal FEI=BigDecimal.valueOf(0.998);

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

    public boolean cancelOrder(BuyLimit buyLimit, int status) {
        Long cancelId = cancelOrder(buyLimit);
        if (cancelId != null && (cancelId.longValue() == buyLimit.getOrderId().longValue())) {
            updateStauts(buyLimit.getId(), status);
            return true;
        }
        log.info("cancalOrder->(cancelId->{} id->{})", cancelId, buyLimit.getId());
        return false;
    }

    private Long cancelOrder(BuyLimit buyLimit) {
        try {
            TradeClient tradeClient = getTradeClient(buyLimit.getApiKey(), buyLimit.getSecretKey());
            Long cancelId = tradeClient.cancelOrder(buyLimit.getOrderId());
            return cancelId;
        } catch (Exception e) {
            log.error("【取消买单时出错】 id->{} error->{}", buyLimit.getId(), e.getMessage());
            if (e.getMessage().indexOf("order-orderstate-error: Incorrect order state") >= 0)
                updateStauts(buyLimit.getId(), 1, e.getMessage());
        }
        return null;
    }

    private void updateStauts(long id, int status) {
        updateStauts(id, status, null);
    }

    private void updateStauts(long id, int status, String orderDetail) {
        try {
            buyLimitMapper.update(null, new UpdateWrapper<BuyLimit>().set("status", status).set("order_detail", orderDetail).eq("id", id));
        } catch (Exception e) {
            log.error("必须处理的【更新买单时出错】error->{} id->{} status->{}", id, status);
        }

    }

    /**
     * 更新卖出量
     */
    public void updatSelled() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int offset=10*i;
            List<SellLimit>  list=  sellLimitMapper.selectList(new QueryWrapper<SellLimit>().eq("status", 0).last("limit 10 offset "+offset));
            if (CollectionUtils.isEmpty(list)) return;
            for (SellLimit sellLimit : list) {
                TradeClient tradeClient = getTradeClient(sellLimit.getApiKey(), sellLimit.getSecretKey());
                Order order = tradeClient.getOrder(Long.parseLong(sellLimit.getOrderId()));
                UpdateWrapper updateWrapper=  new UpdateWrapper<SellLimit>().set("utime", System.currentTimeMillis()).set("selled_amount", order.getFilledAmount().toString()).eq("id", sellLimit.getId());
                if (order.getFilledAmount().compareTo(order.getAmount()) == 0)
                    updateWrapper.set("status", 1);
                sellLimitMapper.update(null,updateWrapper);
            }
            if (list.size()<10) return;
        }
    }

}
