package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.CandlestickEvent;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class CandlestickEventParser implements HuobiModelParser<CandlestickEvent> {

  @Override
  public CandlestickEvent parse(JSONObject json) {

    String dataKey = DataUtils.getDataKey(json);

    return CandlestickEvent.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .candlestick(new CandlestickParser().parse(json.getJSONObject(dataKey)))
        .build();
  }

  @Override
  public CandlestickEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CandlestickEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
