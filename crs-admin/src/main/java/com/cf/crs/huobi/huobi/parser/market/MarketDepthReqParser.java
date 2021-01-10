package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.MarketDepthReq;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class MarketDepthReqParser implements HuobiModelParser<MarketDepthReq> {

  @Override
  public MarketDepthReq parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);
    return MarketDepthReq.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .depth(new MarketDepthParser().parse(json.getJSONObject(dataKey)))
        .build();
  }

  @Override
  public MarketDepthReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDepthReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
