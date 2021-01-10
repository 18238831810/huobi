package com.cf.crs.huobi.client.req.wallet;

import com.cf.crs.huobi.constant.enums.DepositWithdrawTypeEnum;
import com.cf.crs.huobi.constant.enums.QueryDirectionEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepositWithdrawRequest {

  private DepositWithdrawTypeEnum type;

  private String currency;

  private Long from;

  private Integer size;

  private QueryDirectionEnum direction;

}
