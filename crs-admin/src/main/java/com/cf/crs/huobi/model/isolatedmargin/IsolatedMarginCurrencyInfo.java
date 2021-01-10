package com.cf.crs.huobi.model.isolatedmargin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IsolatedMarginCurrencyInfo {

  private String currency;

  private BigDecimal interestRate;

  private BigDecimal minLoanAmt;

  private BigDecimal maxLoanAmt;

  private BigDecimal loanableAmt;

  private BigDecimal actualRate;

}
