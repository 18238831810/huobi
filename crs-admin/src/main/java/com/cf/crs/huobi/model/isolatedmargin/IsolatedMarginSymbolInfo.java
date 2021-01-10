package com.cf.crs.huobi.model.isolatedmargin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IsolatedMarginSymbolInfo {

  private String symbol;

  private List<IsolatedMarginCurrencyInfo> currencies;

}
