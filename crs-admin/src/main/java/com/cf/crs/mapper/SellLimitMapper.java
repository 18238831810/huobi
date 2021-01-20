package com.cf.crs.mapper;

import com.cf.crs.common.dao.BaseDao;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.SellLimit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellLimitMapper extends BaseDao<SellLimit> {
}
