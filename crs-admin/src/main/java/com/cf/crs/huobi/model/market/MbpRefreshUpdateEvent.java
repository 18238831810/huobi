package com.cf.crs.huobi.model.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbpRefreshUpdateEvent {

  private String topic;

  private Long ts;

  private Long seqNum;

  private List<PriceLevel> bids;

  private List<PriceLevel> asks;

}
