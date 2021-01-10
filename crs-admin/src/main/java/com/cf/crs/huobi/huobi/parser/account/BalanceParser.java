package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.account.Balance;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class BalanceParser implements HuobiModelParser<Balance> {

  @Override
  public Balance parse(JSONObject json) {
    Balance balance = json.toJavaObject(Balance.class);
    balance.setType(json.getString("type"));
    return balance;
  }

  @Override
  public Balance parse(JSONArray json) {
    return null;
  }

  @Override
  public List<Balance> parseArray(JSONArray jsonArray) {
    List<Balance> balanceList = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      balanceList.add(parse(jsonObject));
    }
    return balanceList;
  }
}
