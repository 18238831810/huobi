package com.cf.crs.huobi.model.wallet;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepositWithdraw {

  private Long id;

  private String type;

  private String currency;

  private String txHash;

  private String chain;

  private BigDecimal amount;

  private String address;

  private String addressTag;

  private BigDecimal fee;

  private String state;

  private String errorCode;

  private String errorMessage;

  private Long createdAt;

  private Long updatedAt;

}
