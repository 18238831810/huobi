package com.cf.crs.huobi.client.req.account;

import com.cf.crs.huobi.constant.enums.BalanceModeEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubAccountChangeRequest {

  private BalanceModeEnum balanceMode;

}
