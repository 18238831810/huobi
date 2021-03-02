package com.cf.crs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account_setting")
public class AccountSetting {
   @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 火币
     */
    private String apiKey;
    /**
     * 火币
     */

    private String secretKey;
    /**
     * 规则
     */

    private String json;
    /**
     * 规则状态 1：启用 0:其它
     */

    private int status;

    /**
     * 用户的ID
     */
    private int uid;
}
