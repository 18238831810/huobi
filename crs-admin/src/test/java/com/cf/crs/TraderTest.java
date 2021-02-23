package com.cf.crs;

import com.cf.crs.entity.BuyLimit;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.huobi.model.trade.Order;
import com.cf.crs.service.AccountService;
import com.cf.crs.service.TradeService;
import com.cf.crs.service.market.MarketSlumpChangeService;
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

    @Autowired
    MarketSlumpChangeService marketSlumpChangeService;

    String apiKey ="e86f40c1-c7d024fe-ghxertfvbf-02f16",
            secretKey="5e8d4b45-1fcd8068-f6a09700-b56b2";

    public Account getAccount()
    {
        return accountService.getAccount(apiKey,secretKey);
    }

    @Test
    public void createOrder(){
        BuyLimit orderEntity = BuyLimit.builder().symbol("polsusdt").price("1.1936").
                amount("6").sellPrice("8.19")
                .accountId(getAccount().getId()).unikey(System.currentTimeMillis()+"").cancelTime(System.currentTimeMillis()+60*60000).build();
        Long order = tradeService.createOrder(orderEntity);
        System.out.println(order);
    }



    @Test
    public void saveSucOrders(){
        marketSlumpChangeService.saveSucOrders();
    }



    @Test
    public void detailOrder(){
        Order order= tradeService.getTradeClient(apiKey,secretKey).getOrder(218591052698428L);
        System.out.println(order);
    }
    @Test
    public void cancelOrder(){
        Long order= tradeService.getTradeClient(apiKey,secretKey).cancelOrder(218590809449306L);
        System.out.println(order);
    }


}
