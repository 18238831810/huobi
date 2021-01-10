package com.cf.crs.huobi.model.account;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountChangeEvent {

  private String event;

  private List<AccountChange> list;

}
