package com.cf.crs.service.marketv2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cf.crs.entity.CoinScale;
import com.cf.crs.mapper.CoinScaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinScaleService {
    @Autowired
    CoinScaleMapper coinScaleMapper;

    public CoinScale getBySymbol(String symbol)
    {
       return  coinScaleMapper.selectOne(new QueryWrapper<CoinScale>().eq("symbol",symbol.toLowerCase()).last("limit 1"));
    }
}
