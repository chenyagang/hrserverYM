package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.TalentPool;
import org.sang.pagebean.Interview;

import java.util.List;
import java.util.Map;

public interface TalentPoolMapper extends BaseMapper<TalentPool>{

    public List<TalentPool> selectOffer(Map<String , Object> map);
    public List<Interview> selectByIds(Long hrId,@Param("ids")String[] id);
}