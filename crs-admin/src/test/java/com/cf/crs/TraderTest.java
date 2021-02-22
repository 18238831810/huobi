package com.cf.crs;

import com.cf.crs.entity.OrderEntity;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.service.AccountService;
import com.cf.crs.service.TradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraderTest {

    @Autowired
    TradeService tradeService;

    @Autowired
    AccountService accountService;

    String apiKey ="e86f40c10-c7d024fe-ghxertfvbf-02f16",
            secretKey="5e8d4b450-1fcd8068-f6a09700-b56b2";

    public Account getAccount()
    {
        return accountService.getAccount(apiKey,secretKey);
    }

    @Test
    public void createOrder(){
        OrderEntity orderEntity = OrderEntity.builder().apiKey(apiKey).secretKey(secretKey).symbol("btcusdt").price("20071").
                amount("0.000239").sellPrice("38871.19")
                .accountId(getAccount().getId()).cancelTime(System.currentTimeMillis()).build();
        Long order = tradeService.createOrder(orderEntity);
        System.out.println(order);
    }


}
