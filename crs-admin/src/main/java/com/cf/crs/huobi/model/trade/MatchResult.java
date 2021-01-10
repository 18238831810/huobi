package com.cf.crs.huobi.model.trade;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchResult {

  private Long id;

  private Long orderId;

  private Long matchId;

  private String symbol;

  private String type;

  private String source;

  private BigDecimal price;

  private BigDecimal filledAmount;

  private BigDecimal filledFees;

  private String role;

  private BigDecimal filledPoints;

  private String feeDeductCurrency;

  private long createdAt;

  private String feeCurrency;

  private long tradeId;

  private String feeDeductState;


}
