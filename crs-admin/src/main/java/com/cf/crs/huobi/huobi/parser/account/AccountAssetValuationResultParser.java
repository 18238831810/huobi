package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.account.AccountAssetValuationResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class AccountAssetValuationResultParser implements HuobiModelParser<AccountAssetValuationResult> {

  @Override
  public AccountAssetValuationResult parse(JSONObject json) {
    return json.toJavaObject(AccountAssetValuationResult.class);
  }

  @Override
  public AccountAssetValuationResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountAssetValuationResult> parseArray(JSONArray jsonArray) {
    return null;
  }

}
