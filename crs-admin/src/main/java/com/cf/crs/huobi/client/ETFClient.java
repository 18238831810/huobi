package com.cf.crs.huobi.client;

import com.cf.crs.huobi.client.req.etf.ETFSwapListRequest;
import com.cf.crs.huobi.client.req.etf.ETFSwapRequest;
import com.cf.crs.huobi.model.etf.ETFConfig;
import com.cf.crs.huobi.model.etf.ETFSwapRecord;

import java.util.List;

public interface ETFClient {

  ETFConfig getConfig(String etfName);

  void etfSwap(ETFSwapRequest request);

  List<ETFSwapRecord> getEtfSwapList(ETFSwapListRequest request);

}
