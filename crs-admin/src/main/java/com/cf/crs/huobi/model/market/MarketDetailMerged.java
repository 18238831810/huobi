package com.cf.crs.huobi.model.market;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketDetailMerged {

  private Long id;

  private BigDecimal amount;

  private Long count;

  private BigDecimal open;

  private BigDecimal close;

  private BigDecimal low;

  private BigDecimal high;

  private BigDecimal vol;

  @JSONField(deserialize = false)
  private PriceLevel bid;

  @JSONField(deserialize = false)
  private PriceLevel ask;

}
