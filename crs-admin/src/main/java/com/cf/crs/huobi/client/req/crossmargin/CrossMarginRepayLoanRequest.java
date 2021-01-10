package com.cf.crs.huobi.client.req.crossmargin;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginRepayLoanRequest {

  private Long orderId;

  private BigDecimal amount;

}
