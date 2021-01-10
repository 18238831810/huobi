package com.cf.crs.huobi.client.req.crossmargin;

import com.cf.crs.huobi.constant.enums.MarginTransferDirectionEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginTransferRequest {

  private MarginTransferDirectionEnum direction;

  private String currency;

  private BigDecimal amount;

}
