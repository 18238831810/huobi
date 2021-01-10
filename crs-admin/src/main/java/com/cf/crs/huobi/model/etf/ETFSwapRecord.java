package com.cf.crs.huobi.model.etf;

import com.cf.crs.huobi.constant.enums.EtfSwapTypeEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ETFSwapRecord {

  private Long id;

  private String currency;

  private BigDecimal amount;

  private EtfSwapTypeEnum type;

  private Integer status;

  private Long gmtCreated;

  private ETFDetail detail;


}
