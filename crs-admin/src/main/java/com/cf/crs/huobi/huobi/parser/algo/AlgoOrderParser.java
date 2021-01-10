package com.cf.crs.huobi.huobi.parser.algo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.algo.AlgoOrder;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class AlgoOrderParser implements HuobiModelParser<AlgoOrder> {

  @Override
  public AlgoOrder parse(JSONObject json) {
    return json.toJavaObject(AlgoOrder.class);
  }

  @Override
  public AlgoOrder parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AlgoOrder> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(AlgoOrder.class);
  }
}
