package com.cf.crs.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 下单
 *
 */
@Data
@Builder
@NoArgsConstructor
public class OrderEntity {

    private String apiKey;

    private String secretKey;

    //account-id	string	true	NA	账户 ID，取值参考 GET /v1/account/accounts。现货交易使用 ‘spot’ 账户的 account-id；逐仓杠杆交易，请使用 ‘margin’ 账户的 account-id；全仓杠杆交易，请使用 ‘super-margin’ 账户的 account-id
    //symbol	string	true	NA	交易对,即btcusdt, ethbtc...（取值参考GET /v1/common/symbols）
    //type	string	true	NA	订单类型，包括buy-market, sell-market, buy-limit, sell-limit, buy-ioc, sell-ioc, buy-limit-maker, sell-limit-maker（说明见下文）, buy-stop-limit, sell-stop-limit, buy-limit-fok, sell-limit-fok, buy-stop-limit-fok, sell-stop-limit-fok
    //amount	string	true	NA	订单交易量（市价买单为订单交易额）
    //price	string	false	NA	订单价格（对市价单无效）
    //source	string	false	spot-api	现货交易填写“spot-api”，逐仓杠杆交易填写“margin-api”，全仓杠杆交易填写“super-margin-api”, C2C杠杆交易填写"c2c-margin-api"
    //client-order-id	string	false	NA	用户自编订单号（最大长度64个字符，须在24小时内保持唯一性）
    //stop-price	string	false	NA	止盈止损订单触发价格
    //operator	string	false	NA	止盈止损订单触发价运算符 gte – greater than and equal (>=), lte – less than and equal (<=)

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
     * 现货交易填写“spot-api”，逐仓杠杆交易填写“margin-api”，全仓杠杆交易填写“super-margin-api”, C2C杠杆交易填写"c2c-margin-api"
     */
    private String source;

    /**
     * 用户自编订单号（最大长度64个字符，须在24小时内保持唯一性）
     */
    private String clientOrderId;

    /**
     * 止盈止损订单触发价格
     */
    private String stopPrice;

    /**
     * operator gte – 止盈止损订单触发价运算符 gte – greater than and equal (>=), lte – less than and equal (<=)
     */
    private String operator;

    /**
     * 卖出价格
     */
    private String sellPrice;

    /**
     * 撤单时间
     */
    private Long cancelTime;

}
