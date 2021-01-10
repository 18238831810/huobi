package com.cf.crs.huobi.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.subuser.GetSubUserAccountListResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class GetSubUserAccountListResultParser implements HuobiModelParser<GetSubUserAccountListResult> {

  @Override
  public GetSubUserAccountListResult parse(JSONObject json) {
    return json.toJavaObject(GetSubUserAccountListResult.class);
  }

  @Override
  public GetSubUserAccountListResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetSubUserAccountListResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
