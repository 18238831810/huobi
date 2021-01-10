package com.cf.crs.huobi.client.req.market;

import com.cf.crs.huobi.constant.enums.DepthStepEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqMarketDepthRequest {

  private String symbol;

  private DepthStepEnum step;

}
