package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface EmpMapper {
    List<Nation> getAllNations();

    List<Employee> getAllEmployee();

    List<PoliticsStatus> getAllPolitics();

    int addEmp(Employee employee);

    Long getMaxWorkID();

    List<Employee> getEmployeeByPage(@Param("start") Integer start, @Param("size") Integer size, @Param("name")String name);

    Long getCountByName(@Param("name") String name);

    Employee getById(String id);

    int updateEmp( Employee employee);

    int deleteEmpById(@Param("ids") String[] ids);

    int addEmps(@Param("emps") List<Employee> emps);

    List<Employee> getEmployeeByPageShort(@Param("start") int start, @Param("size") Integer size);

    Employee getEmpByNameAndPhone(String name,String phone);

}
