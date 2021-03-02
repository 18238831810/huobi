package com.cf.crs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account_setting")
public class AccountSetting {
    private String apiKey;
    private String secretKey;
    private String json;
    private int status;
}
