package com.cf.crs.service.marketv2;

import com.cf.crs.entity.AccountSetting;
import com.cf.crs.entity.BuyLimit;
import com.cf.crs.entity.vo.DozenNewOrder;
import com.cf.crs.huobi.model.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 新上币下单
 */
@Service
@Slf4j
public class DozenNewMarketService {

    @Autowired
    TradeService tradeService;

    @Autowired
    AccountService accountService;
    @Autowired
    AccountSettingService accountSettingService;

    /**
     * 新上币种下单入口
     */
    public void saveDozenNewMarketOrders() {
        List<DozenNewOrder> list =accountSettingService.getDozenNewMarket();
        if(!CollectionUtils.isEmpty(list)) {
            for (DozenNewOrder dozenNewOrder : list) {
                saveDozenNewMarketOrders(getDozenNewMarketOrders(dozenNewOrder), dozenNewOrder.getAccountSetting());
            }
        }
    }



    public  List<BuyLimit> getDozenNewMarketOrders(DozenNewOrder dozenNewOrder) {
        List<BuyLimit> list = new ArrayList();
        for (DozenNewOrder.DozenNew dozenNew : dozenNewOrder.getOrders()) {
            list.add(getBuyLimits(dozenNewOrder,dozenNew));
        }
        return list;
    }

    private void  saveDozenNewMarketOrders(List<BuyLimit> list,AccountSetting accountSetting)
    {
        if(CollectionUtils.isEmpty(list))
        {
            log.warn("accountSetting.toString->{}",accountSetting.toString());
            return;
        }

        Account account= accountService.getAccount(accountSetting.getApiKey(),accountSetting.getSecretKey());

        for (BuyLimit buyLimit:list) {
            if(tradeService.getByUnitKey(buyLimit.getUnikey())!=null) continue;
            buyLimit.setAccountId(account.getId());
            loopTrader( buyLimit);
        }
    }

    private void  loopTrader(BuyLimit buyLimit) {
        while(true)
        {
            Long orderId = tradeService.createOrder(buyLimit);
            if(orderId!=null)
            {
                log.warn("orderId->{},Amount->{},Price->{},symbol->{}",orderId,buyLimit.getAmount(),buyLimit.getPrice(),buyLimit.getSymbol());
                break;
            }
            else
            {
                try
                {
                    Thread.sleep(100);
                }catch (Exception e)
                {
                    log.error(e.getMessage());
                }
            }
        }
    }


    private BuyLimit getBuyLimits(DozenNewOrder dozenNewOrder, DozenNewOrder.DozenNew dozenNew)
    {
        BuyLimit buyLimit = new BuyLimit();
        buyLimit.setSymbol(dozenNewOrder.getSymbol());
        buyLimit.setMarketId(dozenNew.getId());
        //下单的价格
        buyLimit.setPrice(String.valueOf(dozenNew.getPrice()));

        //下单的数量,暂时没有算手续费,需要后期查询此单时实际成交的量
        BigDecimal bigDecimal = BigDecimal.valueOf(dozenNew.getTotalUsdt()).divide(new BigDecimal(dozenNew.getPrice()), dozenNewOrder.getAmountScale(), BigDecimal.ROUND_DOWN);
        buyLimit.setAmount(bigDecimal.toString());

        buyLimit.setTotal(dozenNew.getTotalUsdt()+"");
        //挂卖出单的价
        buyLimit.setSellPrice(String.valueOf(dozenNew.getPrice()*10));

        //撤单时间为下下单时间的下一个小时，也是行情的下下个小时(改为55分钟)
        buyLimit.setCreateTime(System.currentTimeMillis());
        buyLimit.setCancelTime(System.currentTimeMillis()+5*60*60000);

        buyLimit.setApiKey(dozenNewOrder.getAccountSetting().getApiKey());

        buyLimit.setSecretKey(dozenNewOrder.getAccountSetting().getSecretKey());
        buyLimit.setMarketPrice(buyLimit.getPrice());
        buyLimit.setDumpValue(0+"");
        buyLimit.setSellPoint(1000+"");

        buyLimit.setCandlestick("dozen");
        buyLimit.setRuleId(dozenNewOrder.getAccountSetting().getId());

        String oop=dozenNewOrder.getAccountSetting().getId()+":"+buyLimit.getSymbol()+":"+dozenNew.getId();
        buyLimit.setUnikey( DigestUtils.md5DigestAsHex(oop.getBytes()));
        return buyLimit;
    }
}
