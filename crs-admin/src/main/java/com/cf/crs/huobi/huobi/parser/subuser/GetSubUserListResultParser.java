package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.GetSubUserListResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class GetSubUserListResultParser implements HuobiModelParser<GetSubUserListResult> {

  @Override
  public GetSubUserListResult parse(JSONObject json) {
    return GetSubUserListResult.builder()
        .userList(new SubUserStateParser().parseArray(json.getJSONArray("data")))
        .nextId(json.getLong("nextId"))
        .build();
  }

  @Override
  public GetSubUserListResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetSubUserListResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
