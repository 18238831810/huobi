package com.cf.crs.huobi.model.market;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Candlestick {

  private Long id;

  private BigDecimal amount;

  private BigDecimal count;

  private BigDecimal open;

  private BigDecimal high;

  private BigDecimal low;

  private BigDecimal close;

  private BigDecimal vol;

}
