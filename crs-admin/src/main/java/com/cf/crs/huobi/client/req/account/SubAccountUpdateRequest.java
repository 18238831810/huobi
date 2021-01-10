package com.cf.crs.huobi.client.req.account;

import com.cf.crs.huobi.constant.enums.AccountUpdateModeEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubAccountUpdateRequest {

  private AccountUpdateModeEnum accountUpdateMode;

}
