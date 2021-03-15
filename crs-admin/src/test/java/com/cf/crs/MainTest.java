package com.cf.crs;

import com.cf.crs.common.utils.DateUtils;

import java.util.Date;

import static java.util.concurrent.TimeUnit.MINUTES;

public class MainTest {

    public static void main(String[] a)
    {
        Long dayHour = DateUtils.stringToDate(DateUtils.format(new Date(), "yyyyMMddHH"), "yyyyMMddHH").getTime();
        System.out.println(dayHour + 1 * 60 * 60*1000);
        System.out.println(MINUTES.toMillis(30));
    }
}
