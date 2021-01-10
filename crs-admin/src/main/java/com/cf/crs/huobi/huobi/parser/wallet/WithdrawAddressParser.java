package com.cf.crs.huobi.huobi.parser.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.wallet.WithdrawAddress;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class WithdrawAddressParser implements HuobiModelParser<WithdrawAddress> {

  @Override
  public WithdrawAddress parse(JSONObject json) {
    return null;
  }

  @Override
  public WithdrawAddress parse(JSONArray json) {
    return null;
  }

  @Override
  public List<WithdrawAddress> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(WithdrawAddress.class);
  }
}
