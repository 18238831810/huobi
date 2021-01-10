package com.cf.crs.huobi.model.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserDeposit {

  private Long id;

  private String currency;

  private String txHash;

  private String chain;

  private BigDecimal amount;

  private String address;

  private String addressTag;

  private String state;

  private Long createTime;

  private Long updateTime;

}
