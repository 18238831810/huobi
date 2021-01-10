package com.cf.crs.huobi.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawAddressResult {

  private Long nextId;

  private List<WithdrawAddress> withdrawAddressList;

}
