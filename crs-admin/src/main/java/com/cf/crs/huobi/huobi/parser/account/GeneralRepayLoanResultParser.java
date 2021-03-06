package com.cf.crs.huobi.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.model.crossmargin.GeneralRepayLoanResult;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;

import java.util.List;

public class GeneralRepayLoanResultParser implements HuobiModelParser<GeneralRepayLoanResult> {
    @Override
    public GeneralRepayLoanResult parse(JSONObject json) {
        return json.toJavaObject(GeneralRepayLoanResult.class);
    }

    @Override
    public GeneralRepayLoanResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<GeneralRepayLoanResult> parseArray(JSONArray jsonArray) {
        return jsonArray.toJavaList(GeneralRepayLoanResult.class);
    }
}
