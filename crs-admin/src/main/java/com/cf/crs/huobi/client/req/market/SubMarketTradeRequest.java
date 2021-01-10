package com.cf.crs.huobi.client.req.market;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubMarketTradeRequest {

  private String symbol;

}
