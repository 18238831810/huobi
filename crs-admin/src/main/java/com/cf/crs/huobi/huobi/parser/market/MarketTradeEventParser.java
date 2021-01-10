package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketTradeEvent;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class MarketTradeEventParser implements HuobiModelParser<MarketTradeEvent> {

  @Override
  public MarketTradeEvent parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);

    JSONObject data = json.getJSONObject(dataKey);
    JSONArray dataArray = data.getJSONArray("data");

    return MarketTradeEvent.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .list(new MarketTradeParser().parseArray(dataArray))
        .build();
  }

  @Override
  public MarketTradeEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketTradeEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
