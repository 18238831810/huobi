package com.cf.crs.service.market;

import com.cf.crs.common.utils.DateUtils;
import com.cf.crs.entity.OrderEntity;
import com.cf.crs.huobi.client.req.market.CandlestickRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import com.cf.crs.huobi.huobi.HuobiMarketService;
import com.cf.crs.huobi.model.market.Candlestick;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        //下单的价格
        BigDecimal tradeOutPrice = candlestick.getLow().multiply(BigDecimal.valueOf(100 - slumpPointEnum.getSlumpPoint())).divide(BigDecimal.valueOf(100), slumpRequest.getCoinsEnum().getPrizeScale(), BigDecimal.ROUND_DOWN);
        orderEntity.setSellPrice(tradeOutPrice.toString());

        //下单的数量
        BigDecimal bigDecimal = BigDecimal.valueOf(slumpRequest.getTotalUsdt()).multiply(BigDecimal.valueOf(slumpPointEnum.getSlumpPoint())).divide(tradeOutPrice, slumpRequest.getCoinsEnum().getAmountScale(), BigDecimal.ROUND_DOWN);
        orderEntity.setAmount(bigDecimal.toString());
        //撤单时间为下下单时间的下一个小时，也是行情的下下个小时
        orderEntity.setCancelTime(candlestick.getId() + 2 * 60 * 60);

        return orderEntity;
    }
}
