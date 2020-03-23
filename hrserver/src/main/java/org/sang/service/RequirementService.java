package org.sang.service;

import org.sang.bean.Department;
import org.sang.bean.Requirements;
import org.sang.mapper.DepartmentMapper;
import org.sang.mapper.RequirementsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sang on 2018/1/7.
 */
@Service
@Transactional
public class RequirementService {
    @Autowired
    RequirementsMapper requirementsMapper;
    public int addRequirement(Requirements requirements) {
        try{
            return requirementsMapper.addRequirement(requirements);
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }
    public int updateRequirement(Requirements requirements) {
        return requirementsMapper.updateRequirement(requirements);
    }
    public List<Requirements> select(Integer page,Integer size,String customer) {
        int start = (page - 1) * size;
        return requirementsMapper.select(start,size,customer);
    }
    public Long selectByCount(String customer) {
        Requirements Requirement=new Requirements();
        Requirement.setCustomer(customer);
        return requirementsMapper.selectByCount(Requirement);
    }
}
