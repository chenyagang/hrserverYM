package org.sang.service;

import org.sang.bean.ExportCount;
import org.sang.bean.TalentOper;
import org.sang.bean.TalentPool;
import org.sang.mapper.TalentOperMapper;
import org.sang.mapper.TalentPoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TalentPoolService extends BaseServiceImpl<TalentPool> {


    @Autowired
    private TalentOperMapper talentOperMapper;

    @Autowired
    private TalentPoolMapper talentPoolMapper;


    public List<ExportCount> getExportCountList(Map<String, Object> map) {
        List<ExportCount> exportList = new ArrayList<>();
        List<TalentOper> talentPools = talentOperMapper.selectAllForCount(map);
        Map<Integer, ExportCount> exportMap = new HashMap<>();
        if (talentPools != null && talentPools.size() > 0) {
            for (TalentOper to : talentPools) {
                ExportCount ec = exportMap.get(to.getHrId());
                if (ec == null) {
                    ec = new ExportCount();
                    ec.setHrName(to.getHrName());
                    exportMap.put(to.getHrId(), ec);
                }
                switch (to.getStatus()) {
                    case 0:
                        //通话数量
                        ec.setPhoneCount(ec.getPhoneCount() + 1);
                        break;
                    case 1:
                        //通话数量
                        ec.setPhoneSuccessCount(ec.getPhoneSuccessCount() + 1);
                        break;
                    case 2:
                        break;
                    case 3:
                        //参加内面数量
                        ec.setIsInsideSuccessCount(ec.getIsInsideSuccessCount() + 1);
                        break;
                    case 4:
                        break;
                    case 5:
                        //内面成功数量
                        ec.setInsideSuccessCount(ec.getIsInsideSuccessCount() + 1);
                        break;
                    case 6:
                        break;
                    case 7:
                        //参加外面数量
                        ec.setIsCustomerSuccessCount(ec.getIsCustomerSuccessCount() + 1);
                        break;
                    case 8:
                        break;
                    case 9:
                        //外面成功数量
                        ec.setCustomerSuccessCount(ec.getCustomerSuccessCount() + 1);
                        break;
                    case 10:
                        break;
                    case 11:
                        //offer成功数量
                        ec.setOfferSuccessCount(ec.getOfferSuccessCount() + 1);
                        break;
                    case 12:
                        break;
                    case 13:
                        //入职成功数量
                        ec.setEntrySuccessCount(ec.getEntrySuccessCount() + 1);
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    default:
                        break;

                }
            }
        }

        //计算百分比
        for(Integer key : exportMap.keySet()){
            ExportCount ec = exportMap.get(key);
            double denominator = 0;
            double numerator = 0;
            //邀约比例
            denominator = ec.getPhoneCount();
            numerator = ec.getPhoneSuccessCount();
            if(denominator != 0){
                ec.setPhoneSuccessPercent(doubleFormat((numerator/denominator) * 100) + "%");
            }else{
                ec.setPhoneSuccessPercent("100%");
            }

            //内面比例
            denominator = ec.getIsInsideSuccessCount();
            numerator = ec.getInsideSuccessCount();
            if(denominator != 0){
                ec.setInsideSuccessPercent(doubleFormat((numerator/denominator)) * 100 + "%");
            }else{
                ec.setInsideSuccessPercent("100%");
            }

            //复试比例
            denominator = ec.getIsCustomerSuccessCount();
            numerator = ec.getCustomerSuccessCount();
            if(denominator != 0){
                ec.setCustomerSuccessPercent(doubleFormat((numerator/denominator) * 100) + "%");
            }else{
                ec.setCustomerSuccessPercent("100%");
            }
            //offer比例
            denominator = ec.getCustomerSuccessCount();
            numerator = ec.getOfferSuccessCount();
            if(denominator != 0){
                ec.setOfferSuccessPercent(doubleFormat((numerator/denominator) * 100) + "%");
            }else{
                ec.setOfferSuccessPercent("100%");
            }

            exportList.add(ec);
        }

        return exportList;
    }

    public double doubleFormat(double d) {
        BigDecimal bg = new BigDecimal(d);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }


    public List<TalentPool> getOfferList(Map<String, Object> map) {
        return talentPoolMapper.selectOffer(map);
    }
}
