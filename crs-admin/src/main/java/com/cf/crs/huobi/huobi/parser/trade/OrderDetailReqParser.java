package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.OrderDetailReq;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class OrderDetailReqParser implements HuobiModelParser<OrderDetailReq> {

  @Override
  public OrderDetailReq parse(JSONObject json) {
    return OrderDetailReq.builder()
        .topic(json.getString("topic"))
        .ts(json.getLong("ts"))
        .order(new OrderParser().parse(json.getJSONObject("data")))
        .build();
  }

  @Override
  public OrderDetailReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderDetailReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
