package com.cf.crs.huobi.model.trade;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Deprecated
public class OrderUpdate {


  private Long orderId;

  private String clientOrderId;

  private Long matchId;

  private String symbol;

  private BigDecimal price;

  private BigDecimal unfilledAmount;

  private BigDecimal filledAmount;

  private BigDecimal filledCashAmount;

  private String orderType;

  private String orderState;

  private String role;

}
