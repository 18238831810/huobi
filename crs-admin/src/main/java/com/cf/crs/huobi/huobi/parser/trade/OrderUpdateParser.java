package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.OrderUpdate;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class OrderUpdateParser implements HuobiModelParser<OrderUpdate> {

  @Override
  public OrderUpdate parse(JSONObject json) {
    OrderUpdate update = json.toJavaObject(OrderUpdate.class);
    update.setUnfilledAmount(json.getBigDecimal("unfilled-amount"));
    update.setFilledAmount(json.getBigDecimal("filled-amount"));
    update.setFilledCashAmount(json.getBigDecimal("filled-cash-amount"));
    update.setOrderId(json.getLong("order-id"));
    update.setMatchId(json.getLong("match-id"));
    update.setRole(json.getString("role"));
    update.setOrderType(json.getString("order-type"));
    update.setOrderState(json.getString("order-state"));
    update.setClientOrderId(json.getString("client-order-id"));
    return update;
  }

  @Override
  public OrderUpdate parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderUpdate> parseArray(JSONArray jsonArray) {
    return null;
  }
}
