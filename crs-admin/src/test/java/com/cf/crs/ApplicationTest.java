package com.cf.crs;


import com.alibaba.fastjson.JSON;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.OrderEntity;
import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.huobi.model.account.AccountBalance;
import com.cf.crs.service.AccountService;
import com.cf.crs.service.TradeService;
import com.cf.crs.service.market.CoinsEnum;
import com.cf.crs.service.market.MarketSlumpChangeService;
import com.cf.crs.service.market.SlumpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {


    @Autowired
    AccountService accountService;

    @Autowired
    TradeService tradeService;

    @Autowired
    MarketSlumpChangeService marketSlumpChangeService;

    @Test
    public void testSlump()
    {

        SlumpRequest  slumpRequest = SlumpRequest.builder().candlestickIntervalEnum(CandlestickIntervalEnum.MIN60)
                .coinsEnum(CoinsEnum.USDT_BTC).totalUsdt(500).build();
        List<OrderEntity> orderEntities= marketSlumpChangeService.getSlumpChangeOrders(slumpRequest);
        for (OrderEntity orderEntity:orderEntities) {
            System.out.println(orderEntity);
        }
    }

    @Test
    public void testSaveSlump()
    {
        double totalUsdt=500;
        CoinsEnum[] coinsEnums= CoinsEnum.values();
        for (CoinsEnum coinsEnum:coinsEnums) {

        }
        SlumpRequest slumpRequest = SlumpRequest.builder().candlestickIntervalEnum(CandlestickIntervalEnum.MIN60).coinsEnum(CoinsEnum.USDT_BTC).totalUsdt(totalUsdt).build();
        marketSlumpChangeService.saveSlumpChangeOrders(slumpRequest);
    }

    /**
     * 获取用户信息
     */
    @Test
    public void getAccount(){
        Account account = accountService.getAccount("fa4e6356-67181a8e-dab4c45e6f-dc250", "a8f67e99-2d7ad560-fefabfd7-1ef78");
        System.out.println(JSON.toJSONString(account));
    }

    /**
     * 获取用户余额
     */
    @Test
    public void getAccountBalance(){
        AccountBalance accountBalance = accountService.getAccountBalance("fa4e6356-67181a8e-dab4c45e6f-dc25011", "a8f67e99-2d7ad560-fefabfd7-1ef78");
        System.out.println(JSON.toJSONString(accountBalance));
    }

    @Test
    public void createOrder(){
        OrderEntity orderEntity = OrderEntity.builder().symbol("btcusdt").price("30071.19").
                amount("0.000239").sellPrice("38871.19").cancelTime(System.currentTimeMillis()).build();
        Long order = tradeService.createOrder(orderEntity);
        System.out.println(order);
    }




    @Test
    public void getOrderDetail()
    {
        ///v1/order/orders/getClientOrder
    }
}
