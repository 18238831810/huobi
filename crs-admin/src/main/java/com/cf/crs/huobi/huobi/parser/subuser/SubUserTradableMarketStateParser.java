package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.SubUserTradableMarketState;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class SubUserTradableMarketStateParser implements HuobiModelParser<SubUserTradableMarketState> {

  @Override
  public SubUserTradableMarketState parse(JSONObject json) {
    return null;
  }

  @Override
  public SubUserTradableMarketState parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserTradableMarketState> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(SubUserTradableMarketState.class);
  }
}
