package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.SubUserApiKeyGenerationResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class SubUserApiKeyGenerationResultParser implements HuobiModelParser<SubUserApiKeyGenerationResult> {

  @Override
  public SubUserApiKeyGenerationResult parse(JSONObject json) {
    return json.toJavaObject(SubUserApiKeyGenerationResult.class);
  }

  @Override
  public SubUserApiKeyGenerationResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserApiKeyGenerationResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
