package com.cf.crs.huobi.client;

import com.cf.crs.huobi.client.req.algo.CancelAlgoOrderRequest;
import com.cf.crs.huobi.client.req.algo.CreateAlgoOrderRequest;
import com.cf.crs.huobi.client.req.algo.GetHistoryAlgoOrdersRequest;
import com.cf.crs.huobi.client.req.algo.GetOpenAlgoOrdersRequest;
import com.cf.crs.huobi.constant.Options;
import com.cf.crs.huobi.constant.enums.ExchangeEnum;
import com.cf.crs.huobi.exception.SDKException;
import com.cf.crs.huobi.model.algo.*;
import com.cf.crs.huobi.huobi.HuobiAlgoService;

public interface AlgoClient {

  CreateAlgoOrderResult createAlgoOrder(CreateAlgoOrderRequest request);

  CancelAlgoOrderResult cancelAlgoOrder(CancelAlgoOrderRequest request);

  GetOpenAlgoOrdersResult getOpenAlgoOrders(GetOpenAlgoOrdersRequest request);

  GetHistoryAlgoOrdersResult getHistoryAlgoOrders(GetHistoryAlgoOrdersRequest request);

  AlgoOrder getAlgoOrdersSpecific(String clientOrderId);


  static AlgoClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiAlgoService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
