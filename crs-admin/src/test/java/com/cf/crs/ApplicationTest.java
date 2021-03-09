package com.cf.crs;


import com.cf.crs.entity.BuyLimit;
import com.cf.crs.huobi.client.TradeClient;
import com.cf.crs.service.marketv2.AccountService;
import com.cf.crs.service.marketv2.DozenNewMarketService;
import com.cf.crs.service.marketv2.SlumpMarketService;
import com.cf.crs.service.marketv2.TradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {


    @Autowired
    AccountService accountService;

    @Autowired
    TradeService tradeService;

    @Autowired
    SlumpMarketService slumpMarketService;

    @Autowired
    DozenNewMarketService dozenNewMarketService;

    @Test
    public void testSlump()
    {

        //slumpMarketService.saveSlumpOrders();
        dozenNewMarketService.saveDozenNewMarketOrders();
    }

    @Test
    public void testSaveSlump()
    {
        /*double totalUsdt=500;
        CoinsEnum[] coinsEnums= CoinsEnum.values();
        for (CoinsEnum coinsEnum:coinsEnums) {

        }
        SlumpRequest slumpRequest = SlumpRequest.builder().candlestickIntervalEnum(CandlestickIntervalEnum.MIN60).coinsEnum(CoinsEnum.USDT_BTC).totalUsdt(totalUsdt).build();
        marketSlumpChangeService.saveSlumpChangeOrders(slumpRequest);*/
    }

    /**
     * 获取用户信息
     */
    @Test
    public void getAccount(){
        /*Account account = accountService.getAccount();
        System.out.println(JSON.toJSONString(account));*/
    }

    /**
     * 获取用户余额
     */
    @Test
    public void getAccountBalance(){
       /* AccountBalance accountBalance = accountService.getAccountBalance();
        System.out.println(JSON.toJSONString(accountBalance));*/
    }

    @Test
    public void createOrder(){
        BuyLimit orderEntity = BuyLimit.builder().symbol("btcusdt").price("30071.19").
                amount("0.000239").sellPrice("38871.19").cancelTime(System.currentTimeMillis()).build();
        Long order = tradeService.createOrder(orderEntity);
        System.out.println(order);
    }




    @Test
    public void getSlumpRequest()
    {
       /* TradeClient tradeClient = tradeService.getTradeClient();
        Long cancelId = tradeClient.cancelOrder(218610019128896L);*/
    }

    @Test
    public void setSeller()
    {
        slumpMarketService.saveSucOrders();
    }
    @Test
    public void synSelled()
    {
        slumpMarketService.synSelled();
    }
}
