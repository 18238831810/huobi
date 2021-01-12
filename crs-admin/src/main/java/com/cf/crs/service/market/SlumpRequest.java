package com.cf.crs.service.market;

import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 暴跌下单参数实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlumpRequest {

    private CoinsEnum coinsEnum;

    private CandlestickIntervalEnum candlestickIntervalEnum;

    private double requestUsdt;
}
