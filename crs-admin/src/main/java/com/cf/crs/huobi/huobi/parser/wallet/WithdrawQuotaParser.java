package com.cf.crs.huobi.huobi.parser.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.wallet.WithdrawQuota;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class WithdrawQuotaParser implements HuobiModelParser<WithdrawQuota> {

  @Override
  public WithdrawQuota parse(JSONObject json) {
    return json.toJavaObject(WithdrawQuota.class);
  }

  @Override
  public WithdrawQuota parse(JSONArray json) {
    return null;
  }

  @Override
  public List<WithdrawQuota> parseArray(JSONArray jsonArray) {
    return null;
  }
}
