package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.SubUserApiKeyModificationResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class SubUserApiKeyModificationResultParser implements HuobiModelParser<SubUserApiKeyModificationResult> {

  @Override
  public SubUserApiKeyModificationResult parse(JSONObject json) {
    return json.toJavaObject(SubUserApiKeyModificationResult.class);
  }

  @Override
  public SubUserApiKeyModificationResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserApiKeyModificationResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
