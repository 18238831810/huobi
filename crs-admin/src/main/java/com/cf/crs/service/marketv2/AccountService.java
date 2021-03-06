package com.cf.crs.service.marketv2;


import com.alibaba.fastjson.JSON;
import com.cf.crs.huobi.client.AccountClient;
import com.cf.crs.huobi.client.req.account.AccountBalanceRequest;
import com.cf.crs.huobi.constant.HuobiOptions;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.huobi.model.account.AccountBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户账户信息
 */
@Slf4j
@Service
public class AccountService {


    /**
     * 获取AccountClient
     * @param apiKey
     * @param secretKey
     * @return
     */
    public AccountClient getAccountClient(String apiKey,String secretKey){
        return AccountClient.create(HuobiOptions.builder().apiKey(apiKey).secretKey(secretKey).build());
    }

    /**
     * 获取账户信息
     * @param apiKey
     * @param secretKey
     * @return
     */
    public Account getAccount(String apiKey,String secretKey){
        AccountClient accountClient = getAccountClient(apiKey, secretKey);
        List<Account> list = accountClient.getAccounts();
        if (CollectionUtils.isEmpty(list)) return null;
        log.info("accountList:{}", JSON.toJSONString(list));
        return list.get(0);
    }


    /**
     * 获取账户余额
     * @param apiKey
     * @param secretKey
     * @return
     */
    public AccountBalance getAccountBalance(String apiKey,String secretKey){
        AccountClient accountClient = getAccountClient(apiKey, secretKey);
        Account account = getAccount(apiKey, secretKey);
        return accountClient.getAccountBalance(AccountBalanceRequest.builder().accountId(account.getId()).build());
    }

}
