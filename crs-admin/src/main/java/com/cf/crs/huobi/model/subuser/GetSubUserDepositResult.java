package com.cf.crs.huobi.model.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSubUserDepositResult {

  private List<SubUserDeposit> list;

  private Long nextId;

}
