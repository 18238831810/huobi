package com.cf.crs.service.market;

import lombok.Getter;
import lombok.Setter;

/**
 * 币参数配置
 */
public enum CoinsEnum {
    USDT_BTC("btcusdt", 6, 1)
    // USDT_BCH("bchusdt", 4, 2),
   // USDT_DOT("dotusdt", 4, 2),
   // USDT_LINK("linkusdt", 2, 2),
   // USDT_LTC("ltcusdt", 4, 2),
   // USDT_ETH("ethusdt", 6, 1)
   ;

    /**
     * 代币
     */
    @Setter
    @Getter
    private String symbol;
    /**
     * 下单量的小数位
     */
    @Setter
    @Getter
    private int amountScale;
    /**
     * 下单价的小数位
     */
    @Setter
    @Getter
    private int prizeScale;

    CoinsEnum(String symbol, int amountScale, int prizeScale) {
        this.symbol = symbol;
        this.amountScale = amountScale;
        this.prizeScale = prizeScale;
    }
}
