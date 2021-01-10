package com.cf.crs.huobi.client;

import com.cf.crs.huobi.client.req.margin.*;
import com.cf.crs.huobi.constant.Options;
import com.cf.crs.huobi.constant.enums.ExchangeEnum;
import com.cf.crs.huobi.exception.SDKException;
import com.cf.crs.huobi.model.isolatedmargin.IsolatedMarginAccount;
import com.cf.crs.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;
import com.cf.crs.huobi.model.isolatedmargin.IsolatedMarginSymbolInfo;
import com.cf.crs.huobi.huobi.HuobiIsolatedMarginService;

import java.util.List;

public interface IsolatedMarginClient {

  Long transfer(IsolatedMarginTransferRequest request);

  Long applyLoan(IsolatedMarginApplyLoanRequest request);

  Long repayLoan(IsolatedMarginRepayLoanRequest request);

  List<IsolatedMarginLoadOrder> getLoanOrders(IsolatedMarginLoanOrdersRequest request);

  List<IsolatedMarginAccount> getLoanBalance(IsolatedMarginAccountRequest request);

  List<IsolatedMarginSymbolInfo> getLoanInfo(IsolatedMarginLoanInfoRequest request);

  static IsolatedMarginClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiIsolatedMarginService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
