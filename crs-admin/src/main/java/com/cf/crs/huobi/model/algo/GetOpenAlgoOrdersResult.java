package com.cf.crs.huobi.model.algo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOpenAlgoOrdersResult {

  private List<AlgoOrder> list;

  private Long nextId;

}
