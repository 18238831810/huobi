package com.cf.crs.huobi.client;

import com.cf.crs.huobi.client.req.account.TransferSubuserRequest;
import com.cf.crs.huobi.client.req.subuser.*;
import com.cf.crs.huobi.constant.Options;
import com.cf.crs.huobi.constant.enums.ExchangeEnum;
import com.cf.crs.huobi.exception.SDKException;
import com.cf.crs.huobi.model.account.AccountBalance;
import com.cf.crs.huobi.model.account.SubuserAggregateBalance;
import com.cf.crs.huobi.model.subuser.*;
import com.cf.crs.huobi.model.wallet.DepositAddress;
import com.cf.crs.huobi.huobi.HuobiSubUserService;

import java.util.List;

public interface SubUserClient {


  List<SubUserCreationInfo> subuserCreation(SubUserCreationRequest request);


  GetSubUserListResult getSubUserList(GetSubUserListRequest request);

  SubUserState getSubuserState(Long subUid);

  SubUserManagementResult subuserManagement(SubUserManagementRequest request);

  GetSubUserAccountListResult getSubuserAccountList(GetSubUserAccountListRequest request);

  SubUserTransferabilityResult subuserTransferability(SubUserTransferabilityRequest request);

  SubUserTradableMarketResult subuserTradableMarket(SubUserTradableMarketRequest request);

  SubUserApiKeyGenerationResult subuserApiKeyGeneration(SubUserApiKeyGenerationRequest request);

  SubUserApiKeyModificationResult subuserApiKeyModification(SubUserApiKeyModificationRequest request);

  void subuserApiKeyDeletion(SubUserApiKeyDeletionRequest request);

  GetApiKeyListResult getApiKeyList(GetApiKeyListRequest request);

  List<DepositAddress> getSubUserDepositAddress(Long subUid, String currency);

  GetSubUserDepositResult getSubUserDeposit(GetSubUserDepositRequest request);
  /**
   * Transfer to sub-user
   * @param request
   * @return
   */
  long transferSubuser(TransferSubuserRequest request);

  /**
   * Get sub-user's account balance
   * @param subuserId
   * @return
   */
  List<AccountBalance> getSubuserAccountBalance(Long subuserId);

  /**
   * Get the aggregated balance of all sub-accounts of the current user.
   * @return
   */
  List<SubuserAggregateBalance> getSubuserAggregateBalance();

  static SubUserClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiSubUserService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }

  long getUid();
}
