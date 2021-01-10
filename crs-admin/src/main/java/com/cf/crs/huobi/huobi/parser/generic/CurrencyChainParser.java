package com.cf.crs.huobi.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.generic.CurrencyChain;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class CurrencyChainParser implements HuobiModelParser<CurrencyChain> {

  @Override
  public CurrencyChain parse(JSONObject json) {
    return json.toJavaObject(CurrencyChain.class);
  }

  @Override
  public CurrencyChain parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CurrencyChain> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(CurrencyChain.class);
  }
}
