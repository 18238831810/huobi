package com.cf.crs.service.marketv2;

import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.vo.SlumpMarket;
import com.cf.crs.entity.vo.SlumpMarketPoint;
import com.cf.crs.huobi.client.req.market.CandlestickRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
import com.cf.crs.huobi.huobi.HuobiMarketService;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.huobi.model.market.Candlestick;
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
public class SlumpMarketService {

    @Autowired
    TradeService tradeService;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountSettingService accountSettingService;

    @Autowired
    CoinScaleService coinScaleService;

    public void saveSlumpOrders()
    {
        List<SlumpMarket> accountSettingList= accountSettingService.getSlumpMarket();
        for (SlumpMarket slumpMarket:accountSettingList) {
            saveSlumpOrders( slumpMarket );
        }

    }

    public void saveSlumpOrders(SlumpMarket slumpMarket )
    {
        List<BuyLimit> orderEntities= this.getSlumpOrders(slumpMarket);
        if(CollectionUtils.isEmpty(orderEntities))
        {
            log.warn("toString->{}",slumpMarket.toString());
            return;
        }

        Account account= accountService.getAccount(slumpMarket.getAccountSetting().getApiKey(),slumpMarket.getAccountSetting().getSecretKey());

        for (BuyLimit buyLimit:orderEntities) {
            if(tradeService.getByUnitKey(buyLimit.getUnikey())!=null) continue;
            buyLimit.setAccountId(account.getId());
            tradeService.createOrder(buyLimit);
        }

    }

    public List<BuyLimit> getSlumpOrders(SlumpMarket slumpMarket) {
        HuobiMarketService huobiMarketService = new HuobiMarketService(new HuobiOptions());
        List<BuyLimit> result = new ArrayList<>();
        List<?>  symbols = slumpMarket.getSymbol();
        for (Object symbolOb:symbols) {
            String symbol=symbolOb.toString();
            slumpMarket.setCurrentSymbol(symbol);
            slumpMarket.setCoinScale(coinScaleService.getBySymbol(symbol));
            CandlestickRequest candlestickRequest= CandlestickRequest.builder()
                    .symbol(symbol)
                    .interval(slumpMarket.getCandlestickIntervalEnum())
                    .size(2).build();
            List<Candlestick> candlestickList = huobiMarketService.getCandlestick(candlestickRequest);
            if (CollectionUtils.isEmpty(candlestickList) && candlestickList.size()!=2)
            {
                log.warn("symbol->{} Interval->{} 's Candlestick is null",symbol,slumpMarket.getCandlestickIntervalEnum().getCode());
                continue;
            }
            result.addAll(getSlumpFromCandlestick(candlestickList.get(1),  slumpMarket));
        }
        return result;
    }

    private List<BuyLimit> getSlumpFromCandlestick(Candlestick candlestick, SlumpMarket slumpMarket) {

        //如何获取的行情不对的话就不下单
        if (!isRightMarketData(candlestick,slumpMarket.getCandlestickIntervalEnum()))
        {
            log.warn("id->{} > now->{} ",candlestick.getId(),System.currentTimeMillis());
            return null;
        }

        List<BuyLimit> result = new ArrayList<>();
        List<SlumpMarketPoint> slumpMarketPoints = slumpMarket.getSlumpMarketPoints();
        for (SlumpMarketPoint slumpMarketPoint : slumpMarketPoints) {
            BuyLimit buyLimit = getTradeOut(candlestick,slumpMarketPoint, slumpMarket);
            result.add(buyLimit);
        }
        return result;
    }

    private boolean isRightMarketData(Candlestick candlestick,com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum candlestickIntervalEnum)
    {
        long nowTime =System.currentTimeMillis();
        switch (candlestickIntervalEnum)
        {
            case MIN30:
            {
                long between= nowTime-candlestick.getId().longValue()*1000;
                return (between>1*30*60*1000 &&  between<2*30*60*1000);
            }
            case MIN60:
            {
                long between= nowTime-candlestick.getId().longValue()*1000;
                return (between>1*60*60*1000 &&  between<2*1*60*60*1000);
            }
            case HOUR4:
            {
                long between= nowTime-candlestick.getId().longValue()*1000;
                return (between>4*60*60*1000 &&  between<2*4*60*60*1000);
            }
            case DAY1:
            {
                long between= nowTime-candlestick.getId().longValue()*1000;
                return (between>24*60*60*1000 &&  between<2*24*60*60*1000);
            }
            case WEEK1:
            {
                long between= nowTime-candlestick.getId().longValue()*1000;
                return (between>7*24*60*60*1000 &&  between<2*7*24*60*60*1000);
            }
            default:{
                log.warn("暂时不支持->{}",candlestickIntervalEnum.getCode());
                return false;
            }
        }
    }


    /**
     * 生成挂单订单
     * @param candlestick
     * @param slumpMarketPoint
     * @param slumpMarket
     * @return
     */
    private BuyLimit getTradeOut(Candlestick candlestick,SlumpMarketPoint slumpMarketPoint, SlumpMarket slumpMarket) {
        BuyLimit buyLimit = new BuyLimit();
        buyLimit.setSymbol(slumpMarket.getCurrentSymbol());
        buyLimit.setMarketId(String.valueOf(candlestick.getId()));

        //下单的价格
        BigDecimal buyPrice = candlestick.getLow().multiply(BigDecimal.valueOf(100 - slumpMarketPoint.getSlumpPoint())).divide(BigDecimal.valueOf(100), slumpMarket.getCoinScale().getPrizeScale(), BigDecimal.ROUND_DOWN);
        buyLimit.setPrice(buyPrice.toString());

        //下单的数量,暂时没有算手续费,需要后期查询此单时实际成交的量
        BigDecimal bigDecimal = BigDecimal.valueOf(slumpMarket.getTotalUsdt()).multiply(BigDecimal.valueOf(slumpMarketPoint.getCapitalPoint())).divide(buyPrice, slumpMarket.getCoinScale().getAmountScale(), BigDecimal.ROUND_DOWN);
        buyLimit.setAmount(bigDecimal.toString());

        buyLimit.setTotal(bigDecimal.multiply(buyPrice).setScale(1,BigDecimal.ROUND_DOWN).toString());
        //挂卖出涨幅
        BigDecimal sellAllPoint =BigDecimal.valueOf(slumpMarketPoint.getSellPoint()).divide(BigDecimal.valueOf(100));
        //挂卖出单的价
        BigDecimal sellPrice=buyPrice.multiply(BigDecimal.valueOf(1).add(sellAllPoint)).setScale(slumpMarket.getCoinScale().getPrizeScale(), BigDecimal.ROUND_DOWN);
        buyLimit.setSellPrice(sellPrice.toString());

        //撤单时间为下下单时间的下一个小时，也是行情的下下个小时(改为55分钟)
        buyLimit.setCancelTime(candlestick.getId()*1000+ getCanceTime(slumpMarket.getCandlestickIntervalEnum()));

        buyLimit.setCreateTime(System.currentTimeMillis());
        buyLimit.setApiKey(slumpMarket.getAccountSetting().getApiKey());
        buyLimit.setSecretKey(slumpMarket.getAccountSetting().getSecretKey());
        buyLimit.setMarketPrice(String.valueOf(candlestick.getLow()));
        buyLimit.setDumpValue(String.valueOf(slumpMarketPoint.getSlumpPoint()));
        buyLimit.setSellPoint(String.valueOf(slumpMarketPoint.getSellPoint()));

        //此key是按时间+symbol+暴跌百分比 MD5
        String oop=slumpMarket.getAccountSetting().getId()+":"+candlestick.getId()+":"+buyLimit.getSymbol()+":"+slumpMarketPoint.getSlumpPoint();
        buyLimit.setUnikey( DigestUtils.md5DigestAsHex(oop.getBytes()));
        return buyLimit;
    }

    private long getCanceTime(com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum candlestickIntervalEnum)
    {
            switch (candlestickIntervalEnum)
            {
                case MIN30: return 2*30 * 60*1000-3*60*1000;
                case MIN60: return 2*60*60*1000-5*60*1000;
                case HOUR4:return  2*4*60*60*1000-5*60*1000;
                case DAY1:return   2*24*60*60*1000-5*60*1000;
                case WEEK1:return  2*30*24*60*60*1000-5*60*1000;
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
