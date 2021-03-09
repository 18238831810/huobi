package com.cf.crs.entity.vo;

import com.cf.crs.entity.AccountSetting;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DozenNewOrder {
    private List<DozenNew> orders = new ArrayList<>();
    private String symbol;
    private int amountScale;
    private AccountSetting accountSetting ;

    @Data
    public class DozenNew {
        private String id;
        private double totalUsdt;
        private double price;
    }

}


