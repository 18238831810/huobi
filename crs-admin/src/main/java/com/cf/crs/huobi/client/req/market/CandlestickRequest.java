package com.cf.crs.huobi.client.req.market;

import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CandlestickRequest {

  private String symbol;

  private CandlestickIntervalEnum interval;

  private Integer size;

}
