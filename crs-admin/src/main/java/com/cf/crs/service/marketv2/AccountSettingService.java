package com.cf.crs.service.marketv2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cf.crs.entity.AccountSetting;
import com.cf.crs.entity.vo.DozenNewOrder;
import com.cf.crs.entity.vo.SlumpMarket;
import com.cf.crs.entity.vo.SlumpMarketPoint;
import com.cf.crs.huobi.constant.enums.CandlestickIntervalEnum;
import com.cf.crs.mapper.AccountSettingMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountSettingService  {

    @Autowired
    AccountSettingMapper accountSettingMapper;


    public List<SlumpMarket> getSlumpMarket()
    {
        List<SlumpMarket> result=null;
        List<AccountSetting>  accountSettingList= accountSettingMapper.selectList(new QueryWrapper<AccountSetting>().eq("status",1).eq("type","slump"));
        if(!CollectionUtils.isEmpty(accountSettingList))
        {
            result = new ArrayList();
            for (AccountSetting accountSetting:accountSettingList) {
                result.add(getSlumpMarket(accountSetting));
            }
        }
        return result;
    }

    public List<DozenNewOrder> getDozenNewMarket()
    {
        List<DozenNewOrder> result=null;
        List<AccountSetting>  accountSettingList= accountSettingMapper.selectList(new QueryWrapper<AccountSetting>().eq("status",0).eq("type","dozen"));
        if(!CollectionUtils.isEmpty(accountSettingList))
        {
            result = new ArrayList();
            for (AccountSetting accountSetting:accountSettingList) {
                result.add(getDozenNewOrders(accountSetting));
            }
        }
        return result;
    }

    private DozenNewOrder getDozenNewOrders(AccountSetting accountSetting) {
        JSONObject jsonObject = JSONObject.fromObject(accountSetting.getJson());
        DozenNewOrder dozenNewOrder = new DozenNewOrder();
        dozenNewOrder.setSymbol(jsonObject.getString("symbol"));
        dozenNewOrder.setAccountSetting(accountSetting);
        JSONArray jsonArray =  JSONObject.fromObject(accountSetting.getJson()).getJSONArray("orders");
        for (int i = 0; i < jsonArray.size(); i++) {
            DozenNewOrder.DozenNew dozenNew = new DozenNewOrder().new DozenNew();
            dozenNew.setPrice(jsonArray.getJSONObject(i).getDouble("price"));
            dozenNew.setTotalUsdt(jsonArray.getJSONObject(i).getDouble("total_usdt"));
            dozenNew.setId(jsonArray.getJSONObject(i).getString("id"));
            dozenNewOrder.getOrders().add(dozenNew);
        }
        return dozenNewOrder;
    }

    private SlumpMarket getSlumpMarket(AccountSetting accountSetting)
    {
        JSONObject jsonObject =JSONObject.fromObject(accountSetting.getJson());
       return  SlumpMarket.builder()
                .totalUsdt(jsonObject.getDouble("total_usdt"))
                .symbol(Arrays.asList(jsonObject.getJSONArray("symbols").toArray()))
                .slumpMarketPoints(getSlumpPoint(jsonObject))
                .accountSetting(accountSetting)
                .candlestickIntervalEnum(getCandlestickIntervalEnum(jsonObject)).build();

    }

    private List<SlumpMarketPoint> getSlumpPoint(JSONObject json)
    {
        List<SlumpMarketPoint> result=new ArrayList();
        JSONArray jsonArray= json.getJSONArray("points");
       for (int i=0;i<jsonArray.size();i++)
       {
           JSONObject jsonObject= jsonArray.getJSONObject(i);
           SlumpMarketPoint slumpMarketPoint= SlumpMarketPoint.builder()
                   .slumpPoint(jsonObject.getDouble("slump_point"))
                   .sellPoint(jsonObject.getDouble("sell_point"))
                   .capitalPoint(jsonObject.getDouble("capital_point"))
                   .build();
           result.add(slumpMarketPoint);
       }
        return result;
    }

    private CandlestickIntervalEnum getCandlestickIntervalEnum(JSONObject json)
    {
        String candlestickInterval= json.getString("candlestick_interval");
        if(StringUtils.isEmpty(candlestickInterval))  return  CandlestickIntervalEnum.MIN60;
        candlestickInterval=candlestickInterval.toLowerCase().trim();
        switch (candlestickInterval)
        {
            case "15min":
                return  CandlestickIntervalEnum.MIN15;
            case "30min":
                return  CandlestickIntervalEnum.MIN30;
            case "4hour":
                return  CandlestickIntervalEnum.HOUR4;
            case "1day":
                return  CandlestickIntervalEnum.DAY1;
            case "1mon":
                return  CandlestickIntervalEnum.MON1;
            case "1week":
                return  CandlestickIntervalEnum.WEEK1;
            case "1year":
                return  CandlestickIntervalEnum.YEAR1;
            default:
                return  CandlestickIntervalEnum.MIN60;
        }

    }

}
