package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketBBOEvent;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class MarketBBOEventParser implements HuobiModelParser<MarketBBOEvent> {

  @Override
  public MarketBBOEvent parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);

    return MarketBBOEvent.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .bbo(new MarketBBOParser().parse(json.getJSONObject(dataKey)))
        .build();
  }

  @Override
  public MarketBBOEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketBBOEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
