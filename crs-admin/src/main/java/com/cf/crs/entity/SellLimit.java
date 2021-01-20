package com.cf.crs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 卖单数据
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sell_limit")
public class SellLimit {
    /*`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
      `api_key` varchar(200) DEFAULT NULL COMMENT 'apiKey',
      `secret_key` varchar(200) DEFAULT NULL COMMENT 'secretKey',
      `account_id` bigint(13) DEFAULT NULL COMMENT '用户id',
      `symbol` varchar(100) DEFAULT NULL COMMENT '交易对,即btcusdt, ethbtc...',
      `price` varchar(100) DEFAULT NULL COMMENT '订单价格',
      `amount` varchar(100) DEFAULT NULL COMMENT '订单交易量',
      `order_id` bigint(20) DEFAULT NULL COMMENT '订单单号',
      `buy_order_id` bigint(20) DEFAULT NULL COMMENT '限价买入下单订单单号',
      `create_time` bigint(20) DEFAULT NULL COMMENT '下单时间',
      `status` int(7) DEFAULT '0' COMMENT '订单状态（0：未成交，1：部分成交，2：已成交，3:已撤单）',*/

    private Long id;

    private String apiKey;

    private String secretKey;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 订单单号
     */
    private Long orderId;

    /**
     * 交易对,即btcusdt, ethbtc...（取值参考GET /v1/common/symbols）
     */
    private String symbol;

    /**
     * 订单价格（对市价单无效）
     */
    private String price;

    /**
     * 订单交易量（市价买单为订单交易额）
     */
    private String amount;


    /**
     * 卖出价格
     */
    private Long buyOrderId;

    /**
     * 下单时间
     */
    private Long createTime;

    /**
     * 订单状态（0：未成交，1：部分成交，2：已成交，3:已撤单)
     */
    private int status;


    /**
     * 订单详情
     */
    private String orderDetail;
}
