package com.cf.crs.task;

import com.cf.crs.job.task.ITask;
import com.cf.crs.service.marketv2.DozenNewMarketService;
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

    @Autowired
    DozenNewMarketService dozenNewMarketService;

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
            /**
             * 卖单挂单后处理
             */
            else if("slump_sell_total".equalsIgnoreCase(param))
            {
                slumpMarketService.synSelled();
            }
            /**
             * 新币买入操作
             */
            else if("dozen_orders".equalsIgnoreCase(param))
            {
                dozenNewMarketService.saveDozenNewMarketOrders();
            }
        }
        else
            log.warn("ALLOW_RUNNING->{}",GloabConsts.ALLOW_RUNNING);

    }


}
