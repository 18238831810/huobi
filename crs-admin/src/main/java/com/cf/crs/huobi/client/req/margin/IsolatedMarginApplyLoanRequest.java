package com.cf.crs.huobi.client.req.margin;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginApplyLoanRequest {

  private String symbol;

  private String currency;

  private BigDecimal amount;

}
