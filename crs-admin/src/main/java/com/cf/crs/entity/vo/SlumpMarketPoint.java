package com.cf.crs.entity.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Builder
public class SlumpMarketPoint {

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
}
