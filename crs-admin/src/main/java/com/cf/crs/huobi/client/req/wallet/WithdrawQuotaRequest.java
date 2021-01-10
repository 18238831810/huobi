package com.cf.crs.huobi.client.req.wallet;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawQuotaRequest {

  private String currency;
}
