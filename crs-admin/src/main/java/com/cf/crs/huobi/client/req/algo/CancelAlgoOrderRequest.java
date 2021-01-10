package com.cf.crs.huobi.client.req.algo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelAlgoOrderRequest {

  private List<String> clientOrderIds;

}
