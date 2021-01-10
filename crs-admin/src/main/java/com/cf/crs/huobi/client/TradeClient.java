package com.cf.crs.huobi.client;

import com.cf.crs.huobi.client.req.trade.*;
import com.cf.crs.huobi.constant.Options;
import com.cf.crs.huobi.constant.enums.ExchangeEnum;
import com.cf.crs.huobi.exception.SDKException;
import com.cf.crs.huobi.model.trade.*;
import com.cf.crs.huobi.huobi.HuobiTradeService;
import com.cf.crs.huobi.utils.ResponseCallback;

import java.util.List;

public interface TradeClient {

  Long createOrder(CreateOrderRequest request);

  Long cancelOrder(Long orderId);

  Integer cancelOrder(String clientOrderId);

  BatchCancelOpenOrdersResult batchCancelOpenOrders(BatchCancelOpenOrdersRequest request);

  BatchCancelOrderResult batchCancelOrder(List<Long> ids);

  List<Order> getOpenOrders(OpenOrdersRequest request);

  Order getOrder(Long orderId);

  Order getOrder(String clientOrderId);

  List<Order> getOrders(OrdersRequest request);

  List<Order> getOrdersHistory(OrderHistoryRequest request);

  List<MatchResult> getMatchResult(Long orderId);

  List<MatchResult> getMatchResults(MatchResultRequest request);

  List<FeeRate> getFeeRate(FeeRateRequest request);

  void subOrderUpdateV2(SubOrderUpdateV2Request request, ResponseCallback<OrderUpdateV2Event> callback);

  void subTradeClearing(SubTradeClearingRequest request, ResponseCallback<TradeClearingEvent> callback);

  static TradeClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiTradeService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }

}
