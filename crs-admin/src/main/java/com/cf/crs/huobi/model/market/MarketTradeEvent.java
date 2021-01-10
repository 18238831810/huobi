package com.cf.crs.huobi.model.market;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketTradeEvent {

  private String ch;

  private Long ts;

  private List<MarketTrade> list;
}
