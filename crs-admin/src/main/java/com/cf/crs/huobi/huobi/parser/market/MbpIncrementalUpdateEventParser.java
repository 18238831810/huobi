package com.cf.crs.huobi.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.crs.huobi.constant.WebSocketConstants;
import com.cf.crs.huobi.model.market.MbpIncrementalUpdateEvent;
import com.cf.crs.huobi.huobi.parser.HuobiModelParser;
import com.cf.crs.huobi.huobi.utils.DataUtils;

import java.util.List;

public class MbpIncrementalUpdateEventParser implements HuobiModelParser<MbpIncrementalUpdateEvent> {

  @Override
  public MbpIncrementalUpdateEvent parse(JSONObject json) {

    String topic;
    String action;
    if (json.containsKey("rep")) {
      topic = json.getString("rep");
      action = WebSocketConstants.ACTION_REP;
    } else {
      topic = json.getString("ch");
      action = WebSocketConstants.ACTION_SUB;
    }


    String dataKey = DataUtils.getDataKey(json);
    JSONObject data = json.getJSONObject(dataKey);
    return MbpIncrementalUpdateEvent.builder()
        .action(action)
        .topic(topic)
        .ts(json.getLong("ts"))
        .seqNum(data.getLong("seqNum"))
        .prevSeqNum(data.getLong("prevSeqNum"))
        .bids(new PriceLevelParser().parseArray(data.getJSONArray("bids")))
        .asks(new PriceLevelParser().parseArray(data.getJSONArray("asks")))
        .build();
  }

  @Override
  public MbpIncrementalUpdateEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MbpIncrementalUpdateEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
