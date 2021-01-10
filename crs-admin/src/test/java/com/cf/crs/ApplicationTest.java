package com.cf.crs;


import com.alibaba.fastjson.JSON;
import com.cf.crs.huobi.model.account.Account;
import com.cf.crs.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {


    @Autowired
    AccountService accountService;

    @Test
    public void test(){
        Account account = accountService.getAccount("fa4e6356-67181a8e-dab4c45e6f-dc250", "a8f67e99-2d7ad560-fefabfd7-1ef78");
        System.out.println(JSON.toJSONString(account));
    }
}
