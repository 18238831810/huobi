package com.cf.crs.task;

import com.cf.crs.job.task.ITask;
import com.cf.crs.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理订单数据
 * @author frank
 * 2019/10/16
 **/
@Slf4j
@Component("synTradeTask")
public class SynTradeTask implements ITask{

    @Autowired
    TradeService tradeService;

    @Override
    public void run(String params) {
        try {
            log.info("处理订单数据计划开始执行");
           // tradeService.synBuyLimit();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
