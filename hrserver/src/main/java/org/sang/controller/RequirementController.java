package org.sang.controller;


import org.sang.bean.Employee;
import org.sang.bean.Requirements;
import org.sang.bean.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sang.service.RequirementService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sang on 2018/1/12.
 */
@RestController
@RequestMapping("/requirement")
public class RequirementController {
    @Autowired
    RequirementService requirementService;

    @RequestMapping(value = "/selectByPage", method = RequestMethod.GET)
    public Map<String, Object> selectByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            String customer) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Requirements> requirementByPage = requirementService.select(page, size, customer);
            Long count = requirementService.selectByCount(customer);
            map.put("emps", requirementByPage);
            map.put("count", count);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.PUT)
    public RespBean updateById(Requirements requirement) {
        if (requirementService.updateRequirement(requirement) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public RespBean insert(Requirements requirement) {
        if (requirementService.addRequirement(requirement) == 1) {
            return RespBean.ok("新增成功!");
        }
        return RespBean.error("新增失败!");
    }
}
