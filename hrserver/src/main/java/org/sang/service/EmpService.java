package org.sang.service;

import org.sang.bean.Employee;
import org.sang.bean.Induction;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
@Service
@Transactional
public class EmpService extends BaseServiceImpl<Employee>{
    @Autowired
    EmpMapper empMapper;
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public List<Nation> getAllNations() {
        return empMapper.getAllNations();
    }

    public List<Employee> getAllEmployee() {
        return empMapper.getAllEmployee();
    }

    public List<PoliticsStatus> getAllPolitics() {
        return empMapper.getAllPolitics();
    }


    public Long getMaxWorkID() {
        Long maxWorkID = empMapper.getMaxWorkID();
        return maxWorkID == null ? 0 : maxWorkID;
    }

    public List<Employee> getEmployeeByPage(Integer page, Integer size, String name,int hr_id) {
        int start = (page - 1) * size;
        int sizes = page * size;
        try {
            return empMapper.getEmployeeByPage(start, sizes, name,hr_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Long getCountByNameHrId(String name,int hr_id) {
        try {
            return empMapper.getCountByNameHrId(name,hr_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public Long getCountByName(String name) {
        try {
            return empMapper.getCountByName(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public Employee getById(String id){
        try {
            return empMapper.getById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Employee> getByIds(String id){
        try {
            String[] split = id.split(",");
            return empMapper.getByIds(split);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean deleteEmpById(String ids) {
        String[] split = ids.split(",");
        return empMapper.deleteEmpById(split) == split.length;
    }

    public List<Employee> getAllEmployees() {
        return empMapper.getEmployeeByPage(null, null, null,0);
    }

    public int addEmps(List<Employee> emps) {
        return empMapper.addEmps(emps);
    }

    public int addEmp(Employee emp) {
        int i =0;
        try {
             i = empMapper.addEmp(emp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    public int updateEmp(Employee emp) {
        try {
            int i=empMapper.updateEmp(emp);
            return i;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    public List<Employee> getEmployeeByPageShort(Integer page, Integer size) {
        int start = (page - 1) * size;
        return empMapper.getEmployeeByPageShort(start, size);
    }

    public Employee getEmpByNameAndPhone(String name,String phone) {
        return empMapper.getEmpByNameAndPhone(name,phone);
    }

    public Employee getEmpByPhone(String phone) {
        try {
            return  empMapper.getEmpByPhone(phone);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateEmpShowResumeById(int id) {
        try {
            int i=empMapper.updateEmpShowInterviewById(id);
            return i;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<Employee> getEmployeeByPageAndHrId(long hrId,Integer page, Integer size, String name) {
        int start = (page - 1) * size;
        try {
            return empMapper.getEmployeeByPageAndHrId(hrId,start, size, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public List<Employee> getEmpCountByHrPhoneName(String phone,String name){
        try {
            return empMapper.getEmpCountByHrPhoneName(phone,name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
