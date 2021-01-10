package com.cf.crs.huobi.model.algo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlgoOrder {

  private Long accountId;

  private String source;

  private String clientOrderId;

  private Long orderId;

  private String symbol;

  private BigDecimal orderPrice;

  private BigDecimal orderSize;

  private BigDecimal orderValue;

  private String timeInForce;

  private String orderType;

  private BigDecimal stopPrice;

  private BigDecimal trailingRate;

  private Long orderOrigTime;

  private Long lastActTime;

  private Long orderCreateTime;

  private String orderStatus;

  private Integer errCode;

  private String errMessage;

}
