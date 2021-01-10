package com.cf.crs.task;

import com.cf.crs.job.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 考评计划任务
 * @author frank
 * 2019/10/16
 **/
@Slf4j
@Component("synUserTask")
public class SynUserTask implements ITask{


    @Override
    public void run(String params) {
        try {
            log.info("同步用户信息计划开始执行");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
