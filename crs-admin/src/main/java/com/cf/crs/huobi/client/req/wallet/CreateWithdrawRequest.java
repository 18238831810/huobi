package com.cf.crs.huobi.client.req.wallet;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateWithdrawRequest {

  private String address;

  private BigDecimal amount;

  private BigDecimal fee;

  private String currency;

  private String chain;

  private String addrTag;

}
