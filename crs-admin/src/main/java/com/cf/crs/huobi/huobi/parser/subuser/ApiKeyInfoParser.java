package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.ApiKeyInfo;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class ApiKeyInfoParser implements HuobiModelParser<ApiKeyInfo> {

  @Override
  public ApiKeyInfo parse(JSONObject json) {
    return null;
  }

  @Override
  public ApiKeyInfo parse(JSONArray json) {
    return null;
  }

  @Override
  public List<ApiKeyInfo> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(ApiKeyInfo.class);
  }
}
