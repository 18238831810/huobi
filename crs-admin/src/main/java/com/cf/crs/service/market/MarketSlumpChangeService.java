package com.cf.crs.service.market;

import com.cf.crs.common.utils.DateUtils;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.OrderEntity;
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
import java.util.Date;
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
        List<OrderEntity> orderEntities= this.getSlumpChangeOrders(slumpRequest);
        if(CollectionUtils.isEmpty(orderEntities))
        {
            log.warn("toString->{}",slumpRequest.toString());
            return;
        }

        Account account= accountService.getAccount();

        for (OrderEntity orderEntity:orderEntities) {
            if(tradeService.getByUnitKey(orderEntity.getUnikey())!=null) continue;
            tradeService.createOrder(orderEntity,account);
        }

    }


    public List<OrderEntity> getSlumpChangeOrders(SlumpRequest slumpRequest) {
        HuobiMarketService huobiMarketService = new HuobiMarketService(new HuobiOptions());
        CandlestickRequest candlestickRequest= CandlestickRequest.builder()
                .symbol(slumpRequest.getCoinsEnum().getSymbol())
                .interval(slumpRequest.getCandlestickIntervalEnum())
                .size(1).build();
        List<Candlestick> candlestickList = huobiMarketService.getCandlestick(candlestickRequest);
        if (CollectionUtils.isEmpty(candlestickList))
        {
            log.warn("symbol->{} Interval->{} 's Candlestick is null",slumpRequest.getCoinsEnum().getSymbol(),slumpRequest.getCandlestickIntervalEnum().getCode());
            return null;
        }
        return getSlumpChangeFromCandlestick(candlestickList.get(0),  slumpRequest);
    }

    private List<OrderEntity> getSlumpChangeFromCandlestick(Candlestick candlestick, SlumpRequest slumpRequest) {

        Long dayHour = DateUtils.stringToDate(DateUtils.format(new Date(), "yyyyMMddHH"), "yyyyMMddHH").getTime();
        //如何获取的行情不对的话就不下单
        if (candlestick.getId() >= dayHour)
        {
            log.warn("id->{} > dayHour->{} ",candlestick.getId(),dayHour);
            return null;
        }

        List<OrderEntity> result = new ArrayList<>();
        SlumpPointEnum[] slumpPointEnums = SlumpPointEnum.values();
        for (SlumpPointEnum slumpPointEnum : slumpPointEnums) {
            OrderEntity orderEntity = getTradeOut(candlestick,slumpPointEnum, slumpRequest);
            result.add(orderEntity);
        }
        return result;
    }

    /**
     * 生成挂单订单
     * @param candlestick
     * @param slumpRequest
     * @return
     */
    private OrderEntity getTradeOut(Candlestick candlestick,SlumpPointEnum slumpPointEnum, SlumpRequest slumpRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setSymbol(slumpRequest.getCoinsEnum().getSymbol());
        orderEntity.setInitialPrice(candlestick.getLow().toString());
        //下单的价格
        BigDecimal buyPrice = candlestick.getLow().multiply(BigDecimal.valueOf(100 - slumpPointEnum.getSlumpPoint())).divide(BigDecimal.valueOf(100), slumpRequest.getCoinsEnum().getPrizeScale(), BigDecimal.ROUND_DOWN);
        orderEntity.setPrice(buyPrice.toString());

        //下单的数量,暂时没有算手续费,需要后期查询此单时实际成交的量
        BigDecimal bigDecimal = BigDecimal.valueOf(slumpRequest.getTotalUsdt()).multiply(BigDecimal.valueOf(slumpPointEnum.getCapitalPoint())).divide(buyPrice, slumpRequest.getCoinsEnum().getAmountScale(), BigDecimal.ROUND_DOWN);
        orderEntity.setAmount(bigDecimal.toString());

        //挂卖出涨幅
        BigDecimal sellAllPoint =BigDecimal.valueOf(slumpPointEnum.getSellPoint()).divide(BigDecimal.valueOf(100));
        //挂卖出单的价
        BigDecimal sellPrice=buyPrice.multiply(BigDecimal.valueOf(1).add(sellAllPoint)).setScale(slumpRequest.getCoinsEnum().getPrizeScale(), BigDecimal.ROUND_DOWN);
        orderEntity.setSellPrice(sellPrice.toString());

        //撤单时间为下下单时间的下一个小时，也是行情的下下个小时
        orderEntity.setCancelTime(candlestick.getId()+ 1 * 60 * 60*1000);

        //此key是按时间+symbol+暴跌百分比 MD5
        String oop=candlestick.getId()+":"+orderEntity.getSymbol()+":"+slumpPointEnum.getSlumpPoint();
        orderEntity.setUnikey( DigestUtils.md5DigestAsHex(oop.getBytes()));
        return orderEntity;
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
       // tradeService.updatSelled();
    }
}
