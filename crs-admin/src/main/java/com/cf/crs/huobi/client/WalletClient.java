package com.cf.crs.huobi.client;

import com.cf.crs.huobi.client.req.wallet.*;
import com.cf.crs.huobi.constant.Options;
import com.cf.crs.huobi.constant.enums.ExchangeEnum;
import com.cf.crs.huobi.exception.SDKException;
import com.cf.crs.huobi.model.wallet.DepositAddress;
import com.cf.crs.huobi.model.wallet.DepositWithdraw;
import com.cf.crs.huobi.model.wallet.WithdrawAddressResult;
import com.cf.crs.huobi.model.wallet.WithdrawQuota;
import com.cf.crs.huobi.huobi.HuobiWalletService;

import java.util.List;

public interface WalletClient {

  List<DepositAddress> getDepositAddress(DepositAddressRequest request);

  WithdrawQuota getWithdrawQuota(WithdrawQuotaRequest request);

  WithdrawAddressResult getWithdrawAddress(WithdrawAddressRequest request);

  Long createWithdraw(CreateWithdrawRequest request);

  Long cancelWithdraw(Long withdrawId);

  List<DepositWithdraw> getDepositWithdraw(DepositWithdrawRequest request);

  static WalletClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiWalletService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
