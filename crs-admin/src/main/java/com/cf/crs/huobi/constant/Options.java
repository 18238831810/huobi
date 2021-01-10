package com.cf.crs.huobi.constant;

import com.cf.crs.huobi.constant.enums.ExchangeEnum;

public interface Options {

  String getApiKey();

  String getSecretKey();

  ExchangeEnum getExchange();

  String getRestHost();

  String getWebSocketHost();

  boolean isWebSocketAutoConnect();

}
