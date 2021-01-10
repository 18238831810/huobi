package com.cf.crs.huobi.model.market;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketDepthReq {

  private String ch;

  private Long ts;

  private MarketDepth depth;

}
