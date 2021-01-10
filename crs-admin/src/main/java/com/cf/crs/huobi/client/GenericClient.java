package com.cf.crs.huobi.client;

import com.cf.crs.huobi.client.req.generic.CurrencyChainsRequest;
import com.cf.crs.huobi.constant.Options;
import com.cf.crs.huobi.constant.enums.ExchangeEnum;
import com.cf.crs.huobi.exception.SDKException;
import com.cf.crs.huobi.model.generic.CurrencyChain;
import com.cf.crs.huobi.model.generic.MarketStatus;
import com.cf.crs.huobi.model.generic.Symbol;
import com.cf.crs.huobi.huobi.HuobiGenericService;

import java.util.List;

public interface GenericClient {

  String getSystemStatus();

  MarketStatus getMarketStatus();

  List<Symbol> getSymbols();

  List<String> getCurrencys();

  List<CurrencyChain> getCurrencyChains(CurrencyChainsRequest request);

  Long getTimestamp();

  static GenericClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiGenericService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
