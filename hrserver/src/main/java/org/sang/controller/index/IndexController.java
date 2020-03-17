package org.sang.controller.index;

import org.sang.bean.Hr;
import org.sang.bean.MsgContent;
import org.sang.bean.RespBean;
import org.sang.bean.SysMsg;
import org.sang.service.HrService;
import org.sang.service.SysMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
