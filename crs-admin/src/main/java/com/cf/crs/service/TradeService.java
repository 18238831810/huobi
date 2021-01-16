package com.cf.crs.service;


import com.alibaba.fastjson.JSON;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.OrderEntity;
import com.cf.crs.huobi.client.TradeClient;
import com.cf.crs.huobi.client.req.trade.CreateOrderRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.mapper.BuyLimitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        String apiKey = orderEntity.getApiKey();
        String secretKey = orderEntity.getSecretKey();
        TradeClient tradeClient = getTradeClient(apiKey, secretKey);
        Account account = accountService.getAccount(apiKey, secretKey);
        if (account == null) return null;
        try {
            Long orderId = tradeClient.createOrder(CreateOrderRequest.spotBuyLimit(account.getId(),orderEntity.getSymbol(), new BigDecimal(orderEntity.getPrice()), new BigDecimal(orderEntity.getAmount())));
            //限价下单，存入数据库
            log.info("限价下单成功:{},{}",orderId, JSON.toJSONString(orderEntity));
            //此处最好有短信提醒

            //下单数据入库
            saveBuyLimitOrder(orderEntity, apiKey, secretKey, account,orderId);
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
     * @param apiKey
     * @param secretKey
     * @param account
     */
    private void saveBuyLimitOrder(OrderEntity orderEntity, String apiKey, String secretKey, Account account,Long orderId) {
        BuyLimit build = BuyLimit.builder().apiKey(apiKey).secretKey(secretKey).accountId(account.getId()).
                price(orderEntity.getPrice()).amount(orderEntity.getAmount()).symbol(orderEntity.getSymbol()).
                sellPrice(orderEntity.getSellPrice()).createTime(System.currentTimeMillis()).
                cancelTime(orderEntity.getCancelTime()).status(0).orderId(orderId).build();
        buyLimitMapper.insert(build);
    }


}
