package com.cf.crs.entity.vo;

import com.cf.crs.entity.AccountSetting;
import com.cf.crs.entity.CoinScale;
import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SlumpMarket {

    private double totalUsdt;

    private CandlestickIntervalEnum candlestickIntervalEnum;

    private List<?> symbol;

    private List<SlumpMarketPoint> slumpMarketPoints;

    private AccountSetting accountSetting;

    /**
     * 当前币种
     */
    private String currentSymbol;
    /**
     * 当前币种的规格
     */
    private CoinScale coinScale;



}
