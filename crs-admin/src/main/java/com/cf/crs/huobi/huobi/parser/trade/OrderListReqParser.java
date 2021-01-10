package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.OrderListReq;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class OrderListReqParser implements HuobiModelParser<OrderListReq> {

  @Override
  public OrderListReq parse(JSONObject json) {
    return OrderListReq.builder()
        .topic(json.getString("topic"))
        .ts(json.getLong("ts"))
        .orderList(new OrderParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public OrderListReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderListReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
