package com.cf.crs.huobi.model.wallet;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawChainQuota {

  private String chain;

  private String maxWithdrawAmt;

  private String withdrawQuotaPerDay;

  private String remainWithdrawQuotaPerDay;

  private String withdrawQuotaPerYear;

  private String remainWithdrawQuotaPerYear;

  private String withdrawQuotaTotal;

  private String remainWithdrawQuotaTotal;
}
