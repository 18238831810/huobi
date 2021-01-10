package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketTradeReq;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class MarketTradeReqParser implements HuobiModelParser<MarketTradeReq> {

  @Override
  public MarketTradeReq parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);

    JSONArray dataArray = json.getJSONArray(dataKey);

    return MarketTradeReq.builder()
        .ch(json.getString("req"))
        .list(new MarketTradeParser().parseArray(dataArray))
        .build();
  }

  @Override
  public MarketTradeReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketTradeReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
