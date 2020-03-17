package org.sang.mapper;

import org.sang.bean.TalentPool;

import java.util.List;
import java.util.Map;

public interface TalentPoolMapper extends BaseMapper<TalentPool>{

    public List<TalentPool> selectOffer(Map<String , Object> map);

}