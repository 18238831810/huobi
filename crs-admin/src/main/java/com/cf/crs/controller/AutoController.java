package com.cf.crs.controller;

import com.cf.crs.task.GloabConsts;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auto")
@RestController
public class AutoController {

    @RequestMapping("/syn")
    public String syn(String key) {
        if ("start".equalsIgnoreCase(key))
            GloabConsts.ALLOW_RUNNING = true;
        else
            GloabConsts.ALLOW_RUNNING = false;
        return GloabConsts.ALLOW_RUNNING + "";
    }

}
