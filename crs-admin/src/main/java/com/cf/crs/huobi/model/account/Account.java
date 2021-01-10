package com.cf.crs.huobi.model.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

  private Long id;

  private String type;

  private String state;

  private String subtype;

}
