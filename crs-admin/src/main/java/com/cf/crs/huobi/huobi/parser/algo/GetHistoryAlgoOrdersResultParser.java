package com.cf.crs.huobi.huobi.parser.algo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.algo.GetHistoryAlgoOrdersResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class GetHistoryAlgoOrdersResultParser implements HuobiModelParser<GetHistoryAlgoOrdersResult> {

  @Override
  public GetHistoryAlgoOrdersResult parse(JSONObject json) {
    return GetHistoryAlgoOrdersResult.builder()
        .list(new AlgoOrderParser().parseArray(json.getJSONArray("data")))
        .nextId(json.getLong("nextId"))
        .build();
  }

  @Override
  public GetHistoryAlgoOrdersResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetHistoryAlgoOrdersResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
