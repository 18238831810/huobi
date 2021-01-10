package com.cf.crs.huobi.model.wallet;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawQuota {

  private String currency;

  private List<WithdrawChainQuota> chains;

}
