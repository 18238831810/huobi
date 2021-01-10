package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.SubUserTransferabilityState;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class SubUserTransferabilityStateParser implements HuobiModelParser<SubUserTransferabilityState> {

  @Override
  public SubUserTransferabilityState parse(JSONObject json) {
    return null;
  }

  @Override
  public SubUserTransferabilityState parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserTransferabilityState> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(SubUserTransferabilityState.class);
  }
}
