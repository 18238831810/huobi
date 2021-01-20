package com.cf.crs.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.OrderEntity;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 订单
 */
@Slf4j
@Service
public class TradeService {



    @Autowired
    AccountService accountService;

    @Autowired
    BuyLimitMapper buyLimitMapper;

    @Autowired
    SellLimitMapper sellLimitMapper;


    /**
     * 获取下单TradeClient
     * @param apiKey
     * @param secretKey
     * @return
     */
    public TradeClient getTradeClient(String apiKey,String secretKey){
        return TradeClient.create(HuobiOptions.builder().apiKey(apiKey).secretKey(secretKey).build());
    }


    /**
     * 限价下单
     * @param orderEntity 此处限价下单，主要填 apiKey，secretKey，symbol，price，amount，sellPrice，cancelTime
     */
    public Long createOrder(OrderEntity orderEntity){
        //TradeClient tradeClient = getTradeClient(orderEntity.getApiKey(), orderEntity.getSecretKey());
        try {
            //Long orderId = tradeClient.createOrder(CreateOrderRequest.spotBuyLimit(account.getId(),orderEntity.getSymbol(), new BigDecimal(orderEntity.getPrice()), new BigDecimal(orderEntity.getAmount())));
            //限价下单，存入数据库
            log.info("限价下单成功:{},{}",orderEntity.getAccountId(), JSON.toJSONString(orderEntity));
            //此处最好有短信提醒

            //下单数据入库
            saveBuyLimitOrder(orderEntity,0L);
            return 1L;
        } catch (Exception e) {
            log.info("限价下单失败:{}", JSON.toJSONString(orderEntity));
            log.error(e.getMessage(),e);
            return null;
        }
    }


    /**
     * 限价下单入库
     * @param orderEntity
     */
    private void saveBuyLimitOrder(OrderEntity orderEntity,Long orderId) {
        BuyLimit buyLimit = BuyLimit.builder().apiKey(orderEntity.getApiKey()).secretKey(orderEntity.getSecretKey()).accountId(orderEntity.getAccountId()).
                price(orderEntity.getPrice()).amount(orderEntity.getAmount()).symbol(orderEntity.getSymbol()).
                sellPrice(orderEntity.getSellPrice()).createTime(System.currentTimeMillis()).
                cancelTime(orderEntity.getCancelTime()).status(0).orderId(orderId).unikey(orderEntity.getUnikey()).build();
        buyLimitMapper.insert(buyLimit);
    }

    public List<BuyLimit> getByUnitKey(Collection<String> unikeys)
    {
        return buyLimitMapper.selectBatchIds(unikeys);
    }

    public BuyLimit getByUnitKey(String unikey)
    {
        return buyLimitMapper.selectOne(new QueryWrapper<BuyLimit>().eq("unikey",unikey).last(" limit 1"));
    }

    /**
     * 定时处理挂单数据
     */
    public void synBuyLimit(){
        List<BuyLimit> list = getBuyLimitList();
        for (BuyLimit buyLimit : list) {
            workOrder(buyLimit);
        }
    }


    /**
     * 获取等待中的订单
     * @return
     */
    public List<BuyLimit> getBuyLimitList(){
        return buyLimitMapper.selectList(new QueryWrapper<BuyLimit>().lt("status", 2));
    }


     /*created：已创建，该状态订单尚未进入撮合队列。
        submitted : 已挂单等待成交，该状态订单已进入撮合队列当中。
        partial-filled : 部分成交，该状态订单在撮合队列当中，订单的部分数量已经被市场成交，等待剩余部分成交。
        filled : 已成交。该状态订单不在撮合队列中，订单的全部数量已经被市场成交。
        partial-canceled : 部分成交撤销。该状态订单不在撮合队列中，此状态由partial-filled转化而来，订单数量有部分被成交，但是被撤销。
        canceling : 撤销中。该状态订单正在被撤销的过程中，因订单最终需在撮合队列中剔除才会被真正撤销，所以此状态为中间过渡态。
        canceled : 已撤销。该状态订单不在撮合订单中，此状态订单没有任何成交数量，且被成功撤销。*/
    /**
     * 查询订单状态并处理订单
     * @param buyLimit
     * @return
     */
    public void workOrder(BuyLimit buyLimit){
        TradeClient tradeClient = getTradeClient(buyLimit.getApiKey(),buyLimit.getSecretKey());
        try {
            Order order = tradeClient.getOrder(buyLimit.getOrderId());
            //限价下单，存入数据库
            log.info("查询订单:{},{}",buyLimit.getOrderId(),JSON.toJSONString(order));
            //如果点单状态改变，则走后续步骤 1.订单撤单，则改变订单状态，2.订单成功，则入库持仓表 3.如果还未成功并到达撤单时间，则撤单
            String state = order.getState();
            if ("created".equals(state) || "submitted".equals(state)){
                //挂单还未成交
                if (buyLimit.getCancelTime() >= System.currentTimeMillis()){
                    //超出撤单时间还未成交，进行撤单操作
                    cancelOrder(tradeClient,buyLimit);
                }
            }else if("partial-filled".equals(state)){
                //部分成交，更新更新订单库，并根据成交量选择挂单卖出
                if (order.getAmount().multiply(new BigDecimal(3)).compareTo(new BigDecimal(buyLimit.getAmount())) >= 0){
                    //部分成交
                }
            }else if("filled".equals(state)){
                //已成交
                if(buyLimit.getStatus() == 0){
                    //一步到位，直接全部成交了。全部卖出并更新订单库
                    sellAllOrder(tradeClient, buyLimit, order);
                }else if(buyLimit.getStatus() == 1){
                   //部分成交

                }
            }

        } catch (Exception e) {
            log.info("撤单失败:{}，{}",buyLimit.getOrderId(),JSON.toJSONString(buyLimit));
            log.error(e.getMessage(),e);
        }
    }


    /**
     * 部分卖出下单
     * @param tradeClient
     * @param buyLimit
     */
    @Transactional(rollbackFor = Exception.class)
    public Long sellPartialOrder(TradeClient tradeClient,BuyLimit buyLimit,Order order){
       //查询已经卖出的记录
        //sellLimitMapper.selectList()
        return 1L;
    }

    /**
     * 全部卖出下单
     * @param tradeClient
     * @param buyLimit
     */
    @Transactional(rollbackFor = Exception.class)
    public Long sellAllOrder(TradeClient tradeClient,BuyLimit buyLimit,Order order){
        buyLimit.setStatus(3);
        buyLimit.setOrderDetail(JSON.toJSONString(order));
        buyLimitMapper.updateById(buyLimit);
        Long orderId = tradeClient.createOrder(CreateOrderRequest.spotSellLimit(buyLimit.getAccountId(),buyLimit.getSymbol(), new BigDecimal(buyLimit.getPrice()), new BigDecimal(buyLimit.getAmount())));
        //卖出下单，存入数据库
        log.info("sell suc:{},{}",buyLimit.getAccountId(), JSON.toJSONString(buyLimit));
        //保存卖单数据入库
        try {
            SellLimit sellLimit = SellLimit.builder().apiKey(buyLimit.getApiKey()).secretKey(buyLimit.getSecretKey()).accountId(buyLimit.getAccountId()).
                    price(buyLimit.getPrice()).amount(buyLimit.getAmount()).symbol(buyLimit.getSymbol()).createTime(System.currentTimeMillis()).
                    status(0).orderId(orderId).buyOrderId(buyLimit.getOrderId()).build();
            sellLimitMapper.insert(sellLimit);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return 1L;
    }





    /**
     * 撤单
     * @param buyLimit
     * @param tradeClient
     * @return
     */
    public void cancelOrder(TradeClient tradeClient,BuyLimit buyLimit){
        try {
            tradeClient.cancelOrder(buyLimit.getOrderId());
            //限价下单，存入数据库
            log.info("撤单成功:{},{}",buyLimit.getOrderId(),JSON.toJSONString(buyLimit));
            //更改订单状态
            buyLimit.setStatus(1);
            buyLimitMapper.updateById(buyLimit);
        } catch (Exception e) {
            log.info("撤单失败:{}，{}",buyLimit.getOrderId(),JSON.toJSONString(buyLimit));
            log.error(e.getMessage(),e);
        }
    }


}
