package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.crossmargin.GeneralRepayLoanRecord;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class GeneralRepayLoanRecordParser implements HuobiModelParser<GeneralRepayLoanRecord> {
    @Override
    public GeneralRepayLoanRecord parse(JSONObject json) {
        return json.toJavaObject(GeneralRepayLoanRecord.class);
    }

    @Override
    public GeneralRepayLoanRecord parse(JSONArray json) {
        return null;
    }

    @Override
    public List<GeneralRepayLoanRecord> parseArray(JSONArray jsonArray) {
        return jsonArray.toJavaList(GeneralRepayLoanRecord.class);
    }
}
