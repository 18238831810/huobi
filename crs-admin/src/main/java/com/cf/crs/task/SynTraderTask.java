package com.cf.crs.task;

import com.cf.crs.job.task.ITask;
import com.cf.crs.service.marketv2.SlumpMarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 考评计划任务
 * @author frank
 * 2019/10/16
 **/
@Slf4j
@Component("synTraderTask")
public class SynTraderTask implements ITask{

    @Autowired
    SlumpMarketService slumpMarketService;

    @Override
    public void run(String params) {
        try {
            saveSlumpChangeOrders(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void saveSlumpChangeOrders(String param)
    {
        if(GloabConsts.ALLOW_RUNNING)
        {
            //根据行情下挂单
            if("slump_orders".equalsIgnoreCase(param))
            {
                slumpMarketService.saveSlumpOrders();
            }
            //查询库中的订单是否已经下单成功
            else if("slump_seller".equalsIgnoreCase(param))
            {
                slumpMarketService.saveSucOrders();
            }
            else if("slump_sell_total".equalsIgnoreCase(param))
            {
                slumpMarketService.synSelled();
            }
        }
        else
            log.warn("ALLOW_RUNNING->{}",GloabConsts.ALLOW_RUNNING);

    }


}
