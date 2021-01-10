package com.cf.crs.huobi.client.req.trade;

import com.cf.crs.huobi.constant.enums.QueryDirectionEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderHistoryRequest {

  private String symbol;

  private Long startTime;

  private Long endTime;

  private QueryDirectionEnum direction;

  private Integer size;

}
