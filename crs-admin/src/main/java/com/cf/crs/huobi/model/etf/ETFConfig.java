package com.cf.crs.huobi.model.etf;

import com.cf.crs.huobi.constant.enums.EtfStatusEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ETFConfig {

  private int purchaseMinAmount;
  private int purchaseMaxAmount;
  private int redemptionMinAmount;
  private int redemptionMaxAmount;
  private BigDecimal purchaseFeeRate;
  private BigDecimal redemptionFeeRate;
  private EtfStatusEnum status;
  private List<ETFUnitPrice> unitPriceList;

}
