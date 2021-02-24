package com.cf.crs.service.market;

import com.cf.crs.entity.BuyLimit;
import com.cf.crs.huobi.client.req.market.CandlestickRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
import com.cf.crs.huobi.huobi.HuobiMarketService;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.huobi.model.market.Candlestick;
import com.cf.crs.service.AccountService;
import com.cf.crs.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据行情数据算出下跌指点百分点后的挂单
 */
@Service
@Slf4j
public class MarketSlumpChangeService {

    @Autowired
    TradeService tradeService;

    @Autowired
    AccountService accountService;

    public void saveSlumpChangeOrders(SlumpRequest slumpRequest )
    {
        List<BuyLimit> orderEntities= this.getSlumpChangeOrders(slumpRequest);
        if(CollectionUtils.isEmpty(orderEntities))
        {
            log.warn("toString->{}",slumpRequest.toString());
            return;
        }

        Account account= accountService.getAccount();

        for (BuyLimit buyLimit:orderEntities) {
            if(tradeService.getByUnitKey(buyLimit.getUnikey())!=null) continue;
            buyLimit.setAccountId(account.getId());
            tradeService.createOrder(buyLimit);
        }

    }


    public List<BuyLimit> getSlumpChangeOrders(SlumpRequest slumpRequest) {
        HuobiMarketService huobiMarketService = new HuobiMarketService(new HuobiOptions());
        CandlestickRequest candlestickRequest= CandlestickRequest.builder()
                .symbol(slumpRequest.getCoinsEnum().getSymbol())
                .interval(slumpRequest.getCandlestickIntervalEnum())
                .size(2).build();
        List<Candlestick> candlestickList = huobiMarketService.getCandlestick(candlestickRequest);
        if (CollectionUtils.isEmpty(candlestickList) && candlestickList.size()!=2)
        {
            log.warn("symbol->{} Interval->{} 's Candlestick is null",slumpRequest.getCoinsEnum().getSymbol(),slumpRequest.getCandlestickIntervalEnum().getCode());
            return null;
        }
        return getSlumpChangeFromCandlestick(candlestickList.get(1),  slumpRequest);
    }

    private List<BuyLimit> getSlumpChangeFromCandlestick(Candlestick candlestick, SlumpRequest slumpRequest) {

        //Long dayHour = DateUtils.stringToDate(DateUtils.format(new Date(), "yyyyMMddHH"), "yyyyMMddHH").getTime();
        //如何获取的行情不对的话就不下单
        if (!isRightMarketData(candlestick,slumpRequest.getCandlestickIntervalEnum()))
        {
            log.warn("id->{} > now->{} ",candlestick.getId(),System.currentTimeMillis());
            return null;
        }

        List<BuyLimit> result = new ArrayList<>();
        SlumpPointEnum[] slumpPointEnums = SlumpPointEnum.values();
        for (SlumpPointEnum slumpPointEnum : slumpPointEnums) {
            BuyLimit buyLimit = getTradeOut(candlestick,slumpPointEnum, slumpRequest);
            result.add(buyLimit);
        }
        return result;
    }

    private boolean isRightMarketData(Candlestick candlestick,com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum candlestickIntervalEnum)
    {
        long now =System.currentTimeMillis();
        switch (candlestickIntervalEnum)
        {
            case MIN30:
            {
                long between= now-candlestick.getId().longValue()*1000;
                return (between>30*60*1000 &&  between<60*60*1000);
            }
            case MIN60:
            {
                long between= now-candlestick.getId().longValue()*1000;
                return (between>60*60*1000 &&  between<2*60*60*1000);
            }
            default: return false;
        }
    }


    /**
     * 生成挂单订单
     * @param candlestick
     * @param slumpRequest
     * @return
     */
    private BuyLimit getTradeOut(Candlestick candlestick,SlumpPointEnum slumpPointEnum, SlumpRequest slumpRequest) {
        BuyLimit buyLimit = new BuyLimit();
        buyLimit.setSymbol(slumpRequest.getCoinsEnum().getSymbol());
        buyLimit.setMarketId(String.valueOf(candlestick.getId()));

        //下单的价格
        BigDecimal buyPrice = candlestick.getLow().multiply(BigDecimal.valueOf(100 - slumpPointEnum.getSlumpPoint())).divide(BigDecimal.valueOf(100), slumpRequest.getCoinsEnum().getPrizeScale(), BigDecimal.ROUND_DOWN);
        buyLimit.setPrice(buyPrice.toString());

        //下单的数量,暂时没有算手续费,需要后期查询此单时实际成交的量
        BigDecimal bigDecimal = BigDecimal.valueOf(slumpRequest.getTotalUsdt()).multiply(BigDecimal.valueOf(slumpPointEnum.getCapitalPoint())).divide(buyPrice, slumpRequest.getCoinsEnum().getAmountScale(), BigDecimal.ROUND_DOWN);
        buyLimit.setAmount(bigDecimal.toString());

        buyLimit.setTotal(bigDecimal.multiply(buyPrice).setScale(1,BigDecimal.ROUND_DOWN).toString());
        //挂卖出涨幅
        BigDecimal sellAllPoint =BigDecimal.valueOf(slumpPointEnum.getSellPoint()).divide(BigDecimal.valueOf(100));
        //挂卖出单的价
        BigDecimal sellPrice=buyPrice.multiply(BigDecimal.valueOf(1).add(sellAllPoint)).setScale(slumpRequest.getCoinsEnum().getPrizeScale(), BigDecimal.ROUND_DOWN);
        buyLimit.setSellPrice(sellPrice.toString());

        //撤单时间为下下单时间的下一个小时，也是行情的下下个小时(改为55分钟)
        buyLimit.setCancelTime(candlestick.getId()*1000+ getCanceTime(slumpRequest.getCandlestickIntervalEnum()));

        buyLimit.setCreateTime(System.currentTimeMillis());
        buyLimit.setApiKey(accountService.getApiKey());
        buyLimit.setSecretKey(accountService.getSecretKey());
        buyLimit.setMarketPrice(String.valueOf(candlestick.getLow()));
        buyLimit.setDumpValue(String.valueOf(slumpPointEnum.getSlumpPoint()));
        buyLimit.setSellPoint(String.valueOf(slumpPointEnum.getSellPoint()));

        //此key是按时间+symbol+暴跌百分比 MD5
        String oop=candlestick.getId()+":"+buyLimit.getSymbol()+":"+slumpPointEnum.getSlumpPoint();
        buyLimit.setUnikey( DigestUtils.md5DigestAsHex(oop.getBytes()));
        return buyLimit;
    }

    private long getCanceTime(com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum candlestickIntervalEnum)
    {
            switch (candlestickIntervalEnum)
            {
                case MIN30: return (27+30) * 60*1000;
                case MIN60: return (55+60) * 60*1000;
                default: return System.currentTimeMillis();
            }
    }
    public void saveSucOrders()
    {
        long now =System.currentTimeMillis();
        List<BuyLimit>  buyLimits=  tradeService.getExpireAndNotSucBuyLimit();
        if(CollectionUtils.isEmpty(buyLimits)) {
            return;
        }

        for (BuyLimit buyLimit: buyLimits) {
            tradeService.createSellOrder(buyLimit,now);
        }
    }

    /**
     * 处理卖单成交情况
     */
    public void synSelled() {
        tradeService.updatSelled();
    }
}
