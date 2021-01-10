package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.account.AccountTransferResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class AccountTransferResultParser implements HuobiModelParser<AccountTransferResult> {

  @Override
  public AccountTransferResult parse(JSONObject json) {
    return AccountTransferResult.builder()
        .transactId(json.getLong("transact-id"))
        .transactTime(json.getLong("transact-time"))
        .build();
  }

  @Override
  public AccountTransferResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountTransferResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
