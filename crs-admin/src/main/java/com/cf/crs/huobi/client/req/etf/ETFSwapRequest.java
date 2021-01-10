package com.cf.crs.huobi.client.req.etf;

import com.cf.crs.huobi.constant.enums.EtfSwapDirectionEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ETFSwapRequest {

  private EtfSwapDirectionEnum direction;

  private String etfName;

  private BigDecimal amount;

}
