package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.SubUserTradableMarketResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class SubUserTradableMarketResultParser implements HuobiModelParser<SubUserTradableMarketResult> {

  @Override
  public SubUserTradableMarketResult parse(JSONObject json) {
    return SubUserTradableMarketResult.builder()
        .list(new SubUserTradableMarketStateParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public SubUserTradableMarketResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserTradableMarketResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
