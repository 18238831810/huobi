package com.cf.crs.huobi.client.req.account;


import com.cf.crs.huobi.constant.enums.TransferMasterTypeEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferSubuserRequest {

  private Long subUid;

  private String currency;

  private BigDecimal amount;

  private TransferMasterTypeEnum type;

}
