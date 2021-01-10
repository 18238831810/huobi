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
public class SubUserAccountInfo {

  private String accountType;

  private String activation;

  private Boolean transferrable;

  private List<SubUserAccount> accountIds;

}
