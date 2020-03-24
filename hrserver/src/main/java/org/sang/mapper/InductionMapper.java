package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Induction;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;

import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface InductionMapper {
    int addInduction(Induction Induction);
    int updateInduction(Induction Induction);
    List<Induction> getInductioneByPage(@Param("start") Integer start, @Param("size") Integer size, @Param("name")String name, @Param("hr_id")int hr_id);
    Long getCountByName(@Param("name") String name,@Param("hr_id")Integer hr_id);
    List<Induction> getByIds(@Param("ids")String[] id);
}
