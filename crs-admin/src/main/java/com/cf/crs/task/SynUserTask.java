package com.cf.crs.task;

import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import com.cf.crs.job.task.ITask;
import com.cf.crs.service.TradeService;
import com.cf.crs.service.market.CoinsEnum;
import com.cf.crs.service.market.MarketSlumpChangeService;
import com.cf.crs.service.market.SlumpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 考评计划任务
 * @author frank
 * 2019/10/16
 **/
@Slf4j
@Component("synUserTask")
public class SynUserTask implements ITask{

    @Autowired
    MarketSlumpChangeService marketSlumpChangeService;

    @Autowired
    TradeService tradeService;

    @Override
    public void run(String params) {
        try {
            log.info("同步用户信息计划开始执行");
            saveSlumpChangeOrders(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    private void saveSlumpChangeOrders(String param)
    {
        if("slump_orders".equalsIgnoreCase(param))
        {
            SlumpRequest slumpRequest = SlumpRequest.builder().candlestickIntervalEnum(CandlestickIntervalEnum.MIN60)
                    .coinsEnum(CoinsEnum.BTC_USDT).totalUsdt(500).build();
            marketSlumpChangeService.saveSlumpChangeOrders(slumpRequest);
        }
    }

}
