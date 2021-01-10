package com.cf.crs.huobi.client.req.crossmargin;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeneralRepayLoanRequest {

    private String accountId;

    private String currency;

    private String amount;

    private String transactId;

}
