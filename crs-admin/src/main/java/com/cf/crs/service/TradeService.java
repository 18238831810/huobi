package com.cf.crs.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.OrderEntity;
import com.cf.crs.huobi.client.TradeClient;
import com.cf.crs.huobi.client.req.trade.CreateOrderRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.huobi.model.trade.Order;
import com.cf.crs.mapper.BuyLimitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return buyLimitMapper.selectList(new QueryWrapper<BuyLimit>().eq("status", 0));
    }

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


            //更改订单状态
            //buyLimit.setStatus(1);
            //buyLimitMapper.updateById(buyLimit);
        } catch (Exception e) {
            log.info("撤单失败:{}，{}",buyLimit.getOrderId(),JSON.toJSONString(buyLimit));
            log.error(e.getMessage(),e);
        }
    }



    /**
     * 撤单
     * @param buyLimit
     * @return
     */
    public void cancelOrder(BuyLimit buyLimit){
        TradeClient tradeClient = getTradeClient(buyLimit.getApiKey(),buyLimit.getSecretKey());
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
