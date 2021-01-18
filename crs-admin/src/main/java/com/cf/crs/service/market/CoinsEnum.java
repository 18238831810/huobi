package com.cf.crs.service.market;

import lombok.Getter;
import lombok.Setter;

/**
 * 币参数配置
 */
public enum CoinsEnum {
    USDT_BTC("btcusdt", 6, 1),
    USDT_ETH("ethusdt", 6, 1),
    USDT_DOT("ethusdt", 4, 2),
    USDT_LINK("ethusdt", 2, 2),
    ETH_LTC("ethusdt", 4, 2),
    ETH_BCH("ethusdt", 4, 2);

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
