package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.SubUserManagementResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class SubUserManagementResultParser implements HuobiModelParser<SubUserManagementResult> {

  @Override
  public SubUserManagementResult parse(JSONObject json) {
    return json.toJavaObject(SubUserManagementResult.class);
  }

  @Override
  public SubUserManagementResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserManagementResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
