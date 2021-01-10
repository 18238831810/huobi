package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketDepth;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class MarketDepthParser implements HuobiModelParser<MarketDepth> {

  @Override
  public MarketDepth parse(JSONObject json) {
    return MarketDepth.builder()
        .version(json.getLong("version"))
        .ts(json.getLong("ts"))
        .bids(new PriceLevelParser().parseArray(json.getJSONArray("bids")))
        .asks(new PriceLevelParser().parseArray(json.getJSONArray("asks")))
        .build();
  }

  @Override
  public MarketDepth parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDepth> parseArray(JSONArray jsonArray) {
    return null;
  }
}
