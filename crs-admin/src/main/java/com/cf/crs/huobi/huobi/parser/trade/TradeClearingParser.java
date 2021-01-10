package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.TradeClearing;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class TradeClearingParser implements HuobiModelParser<TradeClearing> {

  @Override
  public TradeClearing parse(JSONObject json) {
    return json.toJavaObject(TradeClearing.class);
  }

  @Override
  public TradeClearing parse(JSONArray json) {
    return null;
  }

  @Override
  public List<TradeClearing> parseArray(JSONArray jsonArray) {
    return null;
  }
}
