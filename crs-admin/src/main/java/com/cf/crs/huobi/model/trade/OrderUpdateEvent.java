package com.cf.crs.huobi.model.trade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderUpdateEvent {

  private String topic;

  private Long ts;

  private OrderUpdate update;

}
