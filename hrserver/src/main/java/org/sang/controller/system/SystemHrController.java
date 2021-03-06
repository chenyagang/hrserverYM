package org.sang.controller.system;

import org.sang.bean.Hr;
import org.sang.bean.RespBean;
import org.sang.common.HrUtils;
import org.sang.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sang on 2018/1/2.
 */
@RestController
@RequestMapping("/system/hr")
public class SystemHrController {
    @Autowired
    HrService hrService;

    @RequestMapping("/id/{hrId}")
    public Hr getHrById(@PathVariable Long hrId) {
        return hrService.getHrById(hrId);
    }

    @RequestMapping(value = "/{hrId}", method = RequestMethod.DELETE)
    public RespBean deleteHr(@PathVariable Long hrId) {
        if (hrService.deleteHr(hrId) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public Hr getCurrentUser() {
        return HrUtils.getCurrentHr();
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateHr(Hr hr) {
        if (hrService.updateHr(hr) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public RespBean updateHrRoles(Long hrId, Long[] rids) {
        if (hrService.updateHrRoles(hrId, rids) == rids.length) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping("/{keywords}")
    public List<Hr> getHrsByKeywords(@PathVariable(required = false) String keywords) {
        List<Hr> hrs = hrService.getHrsByKeywords(keywords);
        return hrs;
    }


    @RequestMapping(value = "/hr/reg", method = RequestMethod.POST)
    public RespBean hrReg(String username, String password) {
        try {
            int i = hrService.hrReg(username, password);

            if (i == 1) {
                return RespBean.ok("注册成功!");
            } else if (i == -1) {
                return RespBean.error("用户名重复，注册失败!");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RespBean.error("注册失败!");
    }

    @RequestMapping(value = "/getAllHrList", method = RequestMethod.GET)
    public Map<String, Object> getAllHrList() {
        List<Map<Long, String>> hrList = new ArrayList<>();
        Map<String, Object> hrMap = new HashMap<>();
        List<Hr> hrs = hrService.getAllHr();
//        for (Hr h :hrs){
//            Map<Long, String> map = new HashMap<>();
//            map.put(h.getId(),h.getName());
//            hrList.add(map);
//        }
        hrMap.put("hrs",hrs);
        return hrMap;
    }




}
