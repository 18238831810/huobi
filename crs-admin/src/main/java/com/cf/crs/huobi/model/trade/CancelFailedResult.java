package com.cf.crs.huobi.model.trade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelFailedResult {

  private Long orderId;

  private String errMsg;

  private String errCode;

  private Integer orderState;

}
