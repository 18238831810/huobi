package com.cf.crs.huobi.client.req.margin;

import com.cf.crs.huobi.constant.enums.LoanOrderStateEnum;
import com.cf.crs.huobi.constant.enums.QueryDirectionEnum;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginLoanOrdersRequest {

  private String symbol;

  private Date startDate;

  private Date endDate;

  private List<LoanOrderStateEnum> states;

  private Long from;

  private QueryDirectionEnum direction;

  private Integer size;

  private Long subUid;

  public String getStatesString(){
    String stateString = null;
    if (this.getStates() != null && this.getStates().size() > 0) {
      StringBuffer statesBuffer = new StringBuffer();
      this.getStates().forEach(orderType -> {
        statesBuffer.append(orderType.getCode()).append(",");
      });
      stateString = statesBuffer.substring(0, statesBuffer.length() - 1);
    }
    return stateString;
  }

}
