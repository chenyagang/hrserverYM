package org.sang.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.sang.bean.Employee;
import org.sang.bean.Induction;
import org.sang.bean.RespBean;
import org.sang.bean.TalentPool;
import org.sang.common.HrUtils;
import org.sang.common.ResponseData;
import org.sang.common.ResultCodeEnum;
import org.sang.common.poi.PoiUtils;
import org.sang.service.*;
import org.sang.utils.WordDocx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by sang on 2018/1/12.
 */
@RestController
@RequestMapping("/employee/basic")
public class InductionController {
    @Autowired
    InductionService inductionService;

    @Autowired
    TalentPoolService talentPoolService;

    @Autowired
    EmpService empService;

    @RequestMapping(value = "/updateInduction", method = RequestMethod.PUT)
    public RespBean updateEmp(Induction induction) {
        try {
            if (inductionService.updateInduction(induction) == 1) {
                return RespBean.ok("更新成功!");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
            return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/insertInduction", method = RequestMethod.POST)
    public RespBean insertInduction(Induction Induction) {
        Induction.setHrId(HrUtils.getCurrentHr().getId().intValue());
        try {
            if (inductionService.addInduction(Induction) == 1) {
                return RespBean.ok("添加成功!");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RespBean.error("添加失败!");
    }

    @RequestMapping(value = "/getInductionByPage", method = RequestMethod.GET)
    public Map<String, Object> getInductionByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "YES") String hrFlag) {
        int hrId = 0;
        if("YES".equals(hrFlag.trim())){
            hrId =new Long(HrUtils.getCurrentHr().getId()).intValue();
        }
        Map<String, Object> map = new HashMap<>();
        List<Induction> Induction = inductionService.getInductioneByPage(page, size,name,hrId);
        Long count = inductionService.getCountByName(name,hrId);
        map.put("emps", Induction);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/addInduction", method = RequestMethod.POST)
    public RespBean addInduction(Induction induction) {

        try {
            TalentPool talentPool= talentPoolService.queryById(induction.getTalentpoolId());
            Employee employee =empService.queryById(String.valueOf(talentPool.getEmployeeId()));
            String mes= "更新不可编辑失败 ！";
            if(0!=talentPoolService.updateShowEdit(induction.getTalentpoolId().trim())){
                mes= "更新不可编辑成功 ！！";
            }
            talentPoolService.updateShowEdit(induction.getTalentpoolId());
            induction.setName(employee.getName());
            induction.setGender(employee.getGender());
            induction.setCustomer(talentPool.getRecommendClient());
            induction.setGraduationTime(employee.getGraduationTime());
            induction.setDegree(employee.getTiptopDegree());
            induction.setWorkAge(employee.getWorkAge().intValue());
            induction.setPhone(employee.getPhone());
            induction.setChannel(employee.getChannel());
            induction.setHrId(HrUtils.getCurrentHr().getId().intValue());
            inductionService.add(induction);
            return RespBean.ok("编辑成功!"+mes);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RespBean.error("编辑失败!");

    }

    @RequestMapping(value = "/exportInduction", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportInduction(String id) {
        return PoiUtils.exportInductionExcel(inductionService.getByIds(id));
    }

}
