package com.cf.crs.huobi.model.trade;

import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeeRate {

  private String symbol;

  private BigDecimal makerFeeRate;

  private BigDecimal takerFeeRate;

  private BigDecimal actualMakerRate;

  private BigDecimal actualTakerRate;

}
