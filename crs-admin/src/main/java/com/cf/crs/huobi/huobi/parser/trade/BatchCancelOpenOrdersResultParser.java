package com.cf.crs.huobi.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.trade.BatchCancelOpenOrdersResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class BatchCancelOpenOrdersResultParser implements HuobiModelParser<BatchCancelOpenOrdersResult> {

  @Override
  public BatchCancelOpenOrdersResult parse(JSONObject json) {
    return BatchCancelOpenOrdersResult.builder()
        .successCount(json.getInteger("success-count"))
        .failedCount(json.getInteger("failed-count"))
        .nextId(json.getLong("next-id"))
        .build();
  }

  @Override
  public BatchCancelOpenOrdersResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<BatchCancelOpenOrdersResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
