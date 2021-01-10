package com.cf.crs.huobi.client.req.market;

import com.cf.crs.huobi.constant.enums.DepthSizeEnum;
import com.cf.crs.huobi.constant.enums.DepthStepEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketDepthRequest {

  private String symbol;

  private DepthSizeEnum depth;

  private DepthStepEnum step;

}
