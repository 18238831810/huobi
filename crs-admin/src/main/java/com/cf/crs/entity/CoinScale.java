package com.cf.crs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("coin_scale")
public class CoinScale {
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 代币
     */
    private String symbol;
    /**
     * 下单量的小数位
     */
    private int amountScale;
    /**
     * 下单价的小数位
     */
    private int prizeScale;

}
