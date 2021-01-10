package com.cf.crs.huobi.huobi.parser.algo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.algo.CreateAlgoOrderResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class CreateAlgoOrderResultParser implements HuobiModelParser<CreateAlgoOrderResult> {

  @Override
  public CreateAlgoOrderResult parse(JSONObject json) {
    return CreateAlgoOrderResult.builder()
        .clientOrderId(json.getString("clientOrderId"))
        .build();
  }

  @Override
  public CreateAlgoOrderResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CreateAlgoOrderResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
