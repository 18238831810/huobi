package com.cf.crs.huobi.model.isolatedmargin;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginLoadOrder {

  private Long id;

  private Long userId;

  private Long accountId;

  private String symbol;

  private String currency;

  private BigDecimal paidCoin;

  private BigDecimal paidPoint;

  private BigDecimal deductAmount;

  private BigDecimal deductRate;

  private String deductCurrency;

  private BigDecimal loanAmount;

  private BigDecimal loanBalance;

  private BigDecimal interestRate;

  private BigDecimal interestAmount;

  private BigDecimal interestBalance;

  private String state;

  private Long createdAt;

  private Long updatedAt;

  private Long accruedAt;

}
