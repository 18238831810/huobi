package com.cf.crs.service;


import com.cf.crs.huobi.client.AccountClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {



    @Autowired
    AccountService accountService;


    public void get(String apiKey,String secretKey){
        AccountClient accountClient = accountService.getAccountClient(apiKey, secretKey);
    }








}
