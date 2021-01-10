package com.cf.crs.huobi.client.req.crossmargin;

import com.cf.crs.huobi.constant.enums.QuerySortEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeneralLoanOrdersRequest {

    private String repayId;

    private String accountId;

    private String currency;

    private long startTime;

    private long endTime;

    private QuerySortEnum sort;

    private int limit;

    private long fromId;

}
