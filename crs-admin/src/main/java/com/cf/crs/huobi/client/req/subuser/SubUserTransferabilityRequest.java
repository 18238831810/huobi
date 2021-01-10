package com.cf.crs.huobi.client.req.subuser;

import com.cf.crs.huobi.constant.enums.TransferabilityAccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserTransferabilityRequest {

  private String subUids;

  private TransferabilityAccountTypeEnum accountType;

  private String transferrable;

}
