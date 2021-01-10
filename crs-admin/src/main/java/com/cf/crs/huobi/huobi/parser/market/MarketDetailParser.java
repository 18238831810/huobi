package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketDetail;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class MarketDetailParser implements HuobiModelParser<MarketDetail> {

  @Override
  public MarketDetail parse(JSONObject json) {
    return json.toJavaObject(MarketDetail.class);
  }

  @Override
  public MarketDetail parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDetail> parseArray(JSONArray jsonArray) {
    return null;
  }
}
