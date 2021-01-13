package com.cf.crs.service.market;

import lombok.Getter;
import lombok.Setter;

/**
 * 暴跌参数配置
 */
public enum SlumpPointEnum {
    STEP_ONE_POINT(3, 0.1,5),
    STEP_TWO_POINT(5, 0.2,6.5),
    STEP_THREE_POINT(7, 0.3,7.5),
    STEP_FOUR_POINT(10, 0.4,8.5);
    //    STEP_FIVE_POINT(20,7)

    /**
     * 暴跌点数（单位百分之几）
     */
    @Setter
    @Getter
    private double slumpPoint;
    /**
     * 资金占比
     */
    @Setter
    @Getter
    private double capitalPoint;

    /**
     * 涨幅点（单位百分之几）
     */
    @Setter
    @Getter
    private double sellPoint;

    SlumpPointEnum(int slumpPoint, double capitalPoint,double sellPoint) {
        this.slumpPoint = slumpPoint;
        this.capitalPoint = capitalPoint;
        this.sellPoint=sellPoint;
    }

}
