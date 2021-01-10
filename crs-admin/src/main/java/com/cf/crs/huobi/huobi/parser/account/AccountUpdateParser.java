package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.account.AccountUpdate;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class AccountUpdateParser implements HuobiModelParser<AccountUpdate> {

  @Override
  public AccountUpdate parse(JSONObject json) {
    AccountUpdate update = json.toJavaObject(AccountUpdate.class);
    return update;
  }

  @Override
  public AccountUpdate parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountUpdate> parseArray(JSONArray jsonArray) {
    return null;
  }
}
