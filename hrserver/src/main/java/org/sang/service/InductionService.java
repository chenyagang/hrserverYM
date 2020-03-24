package org.sang.service;

import org.sang.bean.Employee;
import org.sang.bean.Induction;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.mapper.EmpMapper;
import org.sang.mapper.InductionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
@Service
@Transactional
public class InductionService {
    @Autowired
    InductionMapper inductionMapper;
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");


    public int addInduction(Induction induction) {
        return inductionMapper.addInduction(induction);
    }

    public int updateInduction(Induction induction) {
        return inductionMapper.updateInduction(induction);
    }

    public List<Induction> getInductioneByPage(Integer page, Integer size, String name,Integer hr_id) {
        int start = (page - 1) * size;
        try {
            return inductionMapper.getInductioneByPage(start, size, name,hr_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Long getCountByName(String name,Integer hr_id) {
        try {
            return inductionMapper.getCountByName(name,hr_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public List<Induction> getByIds(String id){
        try {
            String[] split = id.split(",");
            return inductionMapper.getByIds(split);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
