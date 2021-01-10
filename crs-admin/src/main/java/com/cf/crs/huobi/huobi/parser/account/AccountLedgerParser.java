package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.account.AccountLedger;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class AccountLedgerParser implements HuobiModelParser<AccountLedger> {

  @Override
  public AccountLedger parse(JSONObject json) {
    return null;
  }

  @Override
  public AccountLedger parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountLedger> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(AccountLedger.class);
  }
}
