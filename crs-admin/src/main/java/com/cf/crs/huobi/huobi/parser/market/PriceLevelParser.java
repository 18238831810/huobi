package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.PriceLevel;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class PriceLevelParser implements HuobiModelParser<PriceLevel> {

  @Override
  public PriceLevel parse(JSONObject json) {
    return null;
  }

  @Override
  public PriceLevel parse(JSONArray json) {
    return PriceLevel.builder()
        .price(json.getBigDecimal(0))
        .amount(json.getBigDecimal(1))
        .build();
  }

  @Override
  public List<PriceLevel> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }
    List<PriceLevel> list = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONArray array = jsonArray.getJSONArray(i);
      list.add(parse(array));
    }
    return list;
  }
}
