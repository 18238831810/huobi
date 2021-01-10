package com.cf.crs.huobi.model.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Group {

  Long groupId;

  Long expiryDate;

  String remainAmt;

}
