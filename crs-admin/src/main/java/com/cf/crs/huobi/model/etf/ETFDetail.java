package com.cf.crs.huobi.model.etf;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ETFDetail {

  private BigDecimal rate;

  private BigDecimal fee;

  private BigDecimal pointCardAmount;

  private List<ETFUnitPrice> obtainCurrencyList;

  private List<ETFUnitPrice> usedCurrencyList;


}
