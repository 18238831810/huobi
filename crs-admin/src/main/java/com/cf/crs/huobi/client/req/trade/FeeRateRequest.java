package com.cf.crs.huobi.client.req.trade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeeRateRequest {

  private String symbols;

}
