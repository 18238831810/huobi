package com.cf.crs.huobi.huobi.parser.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.wallet.DepositAddress;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class DepositAddressParser implements HuobiModelParser<DepositAddress> {

  @Override
  public DepositAddress parse(JSONObject json) {
    return json.toJavaObject(DepositAddress.class);
  }

  @Override
  public DepositAddress parse(JSONArray json) {
    return null;
  }

  @Override
  public List<DepositAddress> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(DepositAddress.class);
  }
}
