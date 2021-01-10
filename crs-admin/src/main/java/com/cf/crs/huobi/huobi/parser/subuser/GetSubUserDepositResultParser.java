package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.GetSubUserDepositResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class GetSubUserDepositResultParser implements HuobiModelParser<GetSubUserDepositResult> {

  @Override
  public GetSubUserDepositResult parse(JSONObject json) {
    return GetSubUserDepositResult.builder()
        .nextId(json.getLong("nextId"))
        .list(new SubUserDepositParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public GetSubUserDepositResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetSubUserDepositResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
