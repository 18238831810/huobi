package com.cf.crs.huobi.client.req.subuser;

import com.cf.crs.huobi.constant.enums.TradableMarketAccountTypeEnum;
import com.cf.crs.huobi.constant.enums.TradableMarketActivationEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserTradableMarketRequest {

  private String subUids;

  private TradableMarketAccountTypeEnum accountType;

  private TradableMarketActivationEnums activation;

}
