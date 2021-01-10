package com.cf.crs.huobi.client.req.account;

import com.cf.crs.huobi.constant.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountAssetValuationRequest {

  AccountTypeEnum accountType;

  String valuationCurrency;

  Long subUid;

}
