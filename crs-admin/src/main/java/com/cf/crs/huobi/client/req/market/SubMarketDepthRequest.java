package com.cf.crs.huobi.client.req.market;

import com.cf.crs.huobi.constant.enums.DepthStepEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubMarketDepthRequest {
  private String symbol;

  private DepthStepEnum step;

}
