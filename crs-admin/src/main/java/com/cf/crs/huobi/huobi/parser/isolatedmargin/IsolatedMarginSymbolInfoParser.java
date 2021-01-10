package com.cf.crs.huobi.huobi.parser.isolatedmargin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.isolatedmargin.IsolatedMarginSymbolInfo;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class IsolatedMarginSymbolInfoParser implements HuobiModelParser<IsolatedMarginSymbolInfo> {

  @Override
  public IsolatedMarginSymbolInfo parse(JSONObject json) {
    return IsolatedMarginSymbolInfo.builder()
        .symbol(json.getString("symbol"))
        .currencies(new IsolatedMarginCurrencyInfoParser().parseArray(json.getJSONArray("currencies")))
        .build();
  }

  @Override
  public IsolatedMarginSymbolInfo parse(JSONArray json) {
    return null;
  }

  @Override
  public List<IsolatedMarginSymbolInfo> parseArray(JSONArray jsonArray) {

    if (jsonArray == null || jsonArray.isEmpty()) {
      return new ArrayList<>();
    }

    List<IsolatedMarginSymbolInfo> list = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      list.add(parse(jsonObject));
    }

    return list;
  }
}
