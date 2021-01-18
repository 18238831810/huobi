package com.cf.crs.service.market;

import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import lombok.*;

/**
 * 暴跌下单参数实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SlumpRequest {

    /**
     * 币参数配置
     */
    private CoinsEnum coinsEnum;

    /**
     * K线时长参数
     */
    private CandlestickIntervalEnum candlestickIntervalEnum;

    /**
     * 下单的总金额
     */
    private double totalUsdt;
}
