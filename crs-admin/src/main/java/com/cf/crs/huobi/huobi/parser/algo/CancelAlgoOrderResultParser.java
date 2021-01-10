package com.cf.crs.huobi.huobi.parser.algo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.algo.CancelAlgoOrderResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class CancelAlgoOrderResultParser implements HuobiModelParser<CancelAlgoOrderResult> {

  @Override
  public CancelAlgoOrderResult parse(JSONObject json) {
    return json.toJavaObject(CancelAlgoOrderResult.class);
  }

  @Override
  public CancelAlgoOrderResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CancelAlgoOrderResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
