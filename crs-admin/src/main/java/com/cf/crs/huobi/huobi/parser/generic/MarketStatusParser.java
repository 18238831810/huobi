package com.cf.crs.huobi.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.generic.MarketStatus;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class MarketStatusParser implements HuobiModelParser<MarketStatus> {

  @Override
  public MarketStatus parse(JSONObject json) {
    return json.toJavaObject(MarketStatus.class);
  }

  @Override
  public MarketStatus parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketStatus> parseArray(JSONArray jsonArray) {
    return null;
  }
}
