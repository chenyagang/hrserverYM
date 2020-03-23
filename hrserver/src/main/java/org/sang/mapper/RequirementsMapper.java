package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.bean.Requirements;

import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface RequirementsMapper {

    int addRequirement(Requirements requirements);

    int updateRequirement(Requirements requirements);

    List<Requirements> select(Integer start,Integer size, String customer );

    Long selectByCount(Requirements Requirements);
}
