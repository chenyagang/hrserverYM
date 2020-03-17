package org.sang.mapper;

import org.sang.bean.TalentOper;

import java.util.List;
import java.util.Map;

public interface TalentOperMapper extends BaseMapper<TalentOper> {
    public List<TalentOper> selectAllForCount(Map<String , Object> map);
}