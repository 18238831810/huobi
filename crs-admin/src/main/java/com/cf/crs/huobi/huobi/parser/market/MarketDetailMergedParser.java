package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketDetailMerged;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class MarketDetailMergedParser implements HuobiModelParser<MarketDetailMerged> {

  @Override
  public MarketDetailMerged parse(JSONObject json) {


    MarketDetailMerged detailMerged =  json.toJavaObject(MarketDetailMerged.class);
    detailMerged.setAsk(new PriceLevelParser().parse(json.getJSONArray("ask")));
    detailMerged.setBid(new PriceLevelParser().parse(json.getJSONArray("bid")));
    return detailMerged;

  }

  @Override
  public MarketDetailMerged parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDetailMerged> parseArray(JSONArray jsonArray) {
    return null;
  }
}
