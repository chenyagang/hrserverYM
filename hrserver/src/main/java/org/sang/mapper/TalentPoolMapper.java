package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.TalentPool;
import org.sang.pagebean.Interview;

import java.util.List;
import java.util.Map;

public interface TalentPoolMapper extends BaseMapper<TalentPool>{

    public List<TalentPool> selectOffer(Map<String , Object> map);

    List<TalentPool> getTalentPoolPage(@Param("hr_id") long hr_id, @Param("start") Integer start, @Param("size") Integer size, @Param("name")String name);

    public TalentPool selectById(String id);

    public List<Interview> selectByIds(Long hrId,@Param("ids")String[] id);
}