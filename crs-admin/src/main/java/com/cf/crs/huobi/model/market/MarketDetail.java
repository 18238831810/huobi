package com.cf.crs.huobi.model.market;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketDetail {

  private Long id;

  private BigDecimal open;

  private BigDecimal close;

  private BigDecimal low;

  private BigDecimal high;

  private Long count;

  private BigDecimal vol;

  private BigDecimal amount;

  private Long version;


}
