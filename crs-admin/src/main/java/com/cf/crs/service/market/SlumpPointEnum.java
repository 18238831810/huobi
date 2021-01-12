package com.cf.crs.service.market;

import lombok.Getter;
import lombok.Setter;

/**
 * 暴跌参数配置
 */
public enum SlumpPointEnum {
    STEP_ONE_POINT(3, 0.1),
    STEP_TWO_POINT(5, 0.2),
    STEP_THREE_POINT(7, 0.3),
    STEP_FOUR_POINT(10, 0.4);
    //    STEP_FIVE_POINT(20,7)

    /**
     * 暴跌点数
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

    SlumpPointEnum(int slumpPoint, double capitalPoint) {
        this.slumpPoint = slumpPoint;
        this.capitalPoint = capitalPoint;
    }

}
