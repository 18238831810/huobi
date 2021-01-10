package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketDetail;
import com.cf.crs.huobi.model.market.MarketDetailEvent;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class MarketDetailEventParser implements HuobiModelParser<MarketDetailEvent> {

  @Override
  public MarketDetailEvent parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);

    JSONObject data = json.getJSONObject(dataKey);
    MarketDetail marketDetail = new MarketDetailParser().parse(data);
    return MarketDetailEvent.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .detail(marketDetail)
        .build();
  }

  @Override
  public MarketDetailEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDetailEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
