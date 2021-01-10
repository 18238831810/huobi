package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.TradeClearingEvent;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class TradeClearingEventParser implements HuobiModelParser<TradeClearingEvent> {

  @Override
  public TradeClearingEvent parse(JSONObject json) {
    return TradeClearingEvent.builder()
        .action(json.getString("action"))
        .topic(json.getString("ch"))
        .tradeClearing(new TradeClearingParser().parse(json.getJSONObject("data")))
        .build();
  }

  @Override
  public TradeClearingEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<TradeClearingEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
