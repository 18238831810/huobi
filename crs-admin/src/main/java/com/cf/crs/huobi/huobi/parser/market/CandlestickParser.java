package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.Candlestick;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class CandlestickParser implements HuobiModelParser<Candlestick> {

  @Override
  public Candlestick parse(JSONObject json) {
    return json.toJavaObject(Candlestick.class);
  }

  @Override
  public Candlestick parse(JSONArray json) {
    return null;
  }

  @Override
  public List<Candlestick> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(Candlestick.class);
  }
}
