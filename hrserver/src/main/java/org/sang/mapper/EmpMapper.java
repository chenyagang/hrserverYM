package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.bean.TalentPool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sang on 2018/1/12.
 */
public interface EmpMapper {
    List<Nation> getAllNations();

    List<Employee> getAllEmployee();

    List<PoliticsStatus> getAllPolitics();

    int addEmp(Employee employee);

    Long getMaxWorkID();

    List<Employee> getEmployeeByPage(@Param("start") Integer start, @Param("size") Integer size, @Param("name")String name, @Param("hr_id")int hr_id);

    Long getCountByName(@Param("name") String name,@Param("hr_id")int hr_id);

    Employee getById(String id);

    List<Employee> getByIds(@Param("ids")String[] id);

    int updateEmp( Employee employee);

    int deleteEmpById(@Param("ids") String[] ids);

    int addEmps(@Param("emps") List<Employee> emps);

    List<Employee> getEmployeeByPageShort(@Param("start") int start, @Param("size") Integer size);

    Employee getEmpByNameAndPhone(@Param("name") String name,@Param("phone") String phone);

    Employee getEmpByPhone(@Param("phone") String phone);

    int updateEmpShowInterviewById(@Param("id") int id);

    List<Employee> getEmployeeByPageAndHrId(@Param("hr_id") long hr_id,@Param("start") Integer start, @Param("size") Integer size, @Param("name")String name);

    public List<Employee> getEmpCountByHrPhoneName(@Param("phone") String phone,@Param("name") String name);


}
