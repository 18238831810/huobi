package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.account.AccountReq;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class AccountReqParser implements HuobiModelParser<AccountReq> {

  @Override
  public AccountReq parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);
    return AccountReq.builder()
        .topic(json.getString("topic"))
        .ts(json.getLong("ts"))
        .cid(json.getString("cid"))
        .balanceList(new AccountBalanceParser().parseArray(json.getJSONArray(dataKey)))
        .build();
  }

  @Override
  public AccountReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
