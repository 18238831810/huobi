package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.account.AccountFuturesTransferResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class AccountFuturesTransferResultParser implements HuobiModelParser<AccountFuturesTransferResult> {

  @Override
  public AccountFuturesTransferResult parse(JSONObject json) {
    return AccountFuturesTransferResult.builder().data(json.getLong("data")).build();
  }

  @Override
  public AccountFuturesTransferResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountFuturesTransferResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
