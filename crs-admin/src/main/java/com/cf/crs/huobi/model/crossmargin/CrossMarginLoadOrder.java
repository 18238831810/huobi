package com.cf.crs.huobi.model.crossmargin;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginLoadOrder {

  private Long id;

  private Long userId;

  private Long accountId;

  private String currency;

  private BigDecimal loanAmount;

  private BigDecimal loanBalance;

  private BigDecimal interestAmount;

  private BigDecimal interestBalance;

  private BigDecimal filledPoints;

  private BigDecimal filledHt;

  private String state;

  private Long createdAt;

  private Long accruedAt;

}
