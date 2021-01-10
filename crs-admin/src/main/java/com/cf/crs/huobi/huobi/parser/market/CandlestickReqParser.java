package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.market.CandlestickReq;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class CandlestickReqParser implements HuobiModelParser<CandlestickReq> {

  @Override
  public CandlestickReq parse(JSONObject json) {

    String dataKey = DataUtils.getDataKey(json);

    return CandlestickReq.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .candlestickList(new CandlestickParser().parseArray(json.getJSONArray(dataKey)))
        .build();
  }

  @Override
  public CandlestickReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CandlestickReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
