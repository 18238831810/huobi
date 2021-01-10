package com.cf.crs.huobi.model.generic;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrencyChain {

  private String currency;

  private String instStatus;

  private List<Chain> chains;

}
