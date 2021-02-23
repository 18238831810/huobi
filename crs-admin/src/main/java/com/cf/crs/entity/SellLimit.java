package com.cf.crs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("seller_limit")
public class SellLimit {

    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 买入时的订单
     */
    private long buyId;


    private String apiKey;

    private String secretKey;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 卖出价格
     */
    private String price;

    /**
     * 卖出量
     */
    private String amount;

    /**
     * 卖出订单
     */
    private String orderId;

    /**
     * 创建时间
     */
    private long ctime;

    /**
     * 状态
     */
    private int status;

    private String total;
    /**
     * 基于哪一个K线行情下的单
     */
    private String marketId;


}
