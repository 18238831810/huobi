package com.cf.crs.huobi.huobi.parser.etf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.etf.ETFDetail;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class ETFDetailParser implements HuobiModelParser<ETFDetail> {

  @Override
  public ETFDetail parse(JSONObject json) {
    return ETFDetail.builder()
        .rate(json.getBigDecimal("rate"))
        .fee(json.getBigDecimal("fee"))
        .pointCardAmount(json.getBigDecimal("point_card_amount"))
        .usedCurrencyList(new ETFUnitPriceParser().parseArray(json.getJSONArray("used_currency_list")))
        .obtainCurrencyList(new ETFUnitPriceParser().parseArray(json.getJSONArray("obtain_currency_list")))
        .build();
  }

  @Override
  public ETFDetail parse(JSONArray json) {
    return null;
  }

  @Override
  public List<ETFDetail> parseArray(JSONArray jsonArray) {
    return null;
  }
}
