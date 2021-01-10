package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.OrderUpdateV2;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class OrderUpdateV2Parser implements HuobiModelParser<OrderUpdateV2> {

  @Override
  public OrderUpdateV2 parse(JSONObject json) {
    return json.toJavaObject(OrderUpdateV2.class);
  }

  @Override
  public OrderUpdateV2 parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderUpdateV2> parseArray(JSONArray jsonArray) {
    return null;
  }
}
