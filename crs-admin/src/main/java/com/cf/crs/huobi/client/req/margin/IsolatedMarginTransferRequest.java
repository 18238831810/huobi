package com.cf.crs.huobi.client.req.margin;

import com.cf.crs.huobi.constant.enums.MarginTransferDirectionEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginTransferRequest {

  private MarginTransferDirectionEnum direction;

  private String symbol;

  private String currency;

  private BigDecimal amount;

}
