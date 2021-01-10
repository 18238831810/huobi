package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.OrderUpdateEvent;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

@Deprecated
public class OrderUpdateEventParser implements HuobiModelParser<OrderUpdateEvent> {

    @Override
    public OrderUpdateEvent parse(JSONObject json) {
        return OrderUpdateEvent.builder()
                .topic(json.getString("topic"))
                .ts(json.getLong("ts"))
                .update(new OrderUpdateParser().parse(json.getJSONObject("data")))
                .build();
    }

    @Override
    public OrderUpdateEvent parse(JSONArray json) {
        return null;
    }

    @Override
    public List<OrderUpdateEvent> parseArray(JSONArray jsonArray) {
        return null;
    }
}
