package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.SubUserTransferabilityResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class SubUserTransferabilityResultParser implements HuobiModelParser<SubUserTransferabilityResult> {

  @Override
  public SubUserTransferabilityResult parse(JSONObject json) {
    return SubUserTransferabilityResult.builder()
        .list(new SubUserTransferabilityStateParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public SubUserTransferabilityResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserTransferabilityResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
