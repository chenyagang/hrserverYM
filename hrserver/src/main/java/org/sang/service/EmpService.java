package org.sang.service;

import org.sang.bean.Employee;
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
public class EmpService {
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

    public List<Employee> getEmployeeByPage(Integer page, Integer size, String name) {
        int start = (page - 1) * size;
        try {
            return empMapper.getEmployeeByPage(start, size, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Long getCountByName(String name) {
        try {
            return empMapper.getCountByName(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getById(String id){
        try {
            return empMapper.getById(id);
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
        return empMapper.getEmployeeByPage(null, null, null);
    }

    public int addEmps(List<Employee> emps) {
        return empMapper.addEmps(emps);
    }

    public int addEmp(Employee emp) {
        try {
            int i = empMapper.addEmp(emp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
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

}
