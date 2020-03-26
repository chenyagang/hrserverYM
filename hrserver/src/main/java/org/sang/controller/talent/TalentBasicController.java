package org.sang.controller.talent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.sang.bean.*;
import org.sang.common.*;
import org.sang.common.poi.PoiParseWord;
import org.sang.common.poi.PoiUtils;
import org.sang.service.EmpService;
import org.sang.service.HrService;
import org.sang.service.TalentOperServiceImpl;
import org.sang.service.TalentPoolService;
import org.sang.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/talent/basic")
public class TalentBasicController {

    @Autowired
    private TalentPoolService talentPoolService;

    @Autowired
    private HrService hrService;

    @Autowired
    private TalentOperServiceImpl talentOperService;

    @Autowired
    private EmpService empService;

    private String [] statusTextArr = {"通话中","预约成功",  "预约失败", "参加内面",  "未参加内面","内面成功", "内面失败","参加客面","未参加客面","客面成功", "客面失败", "offer成功", "offer失败","入职成功", "入职失败", "已完成","未完成"};

    /*
     * @author ll
     * @Description 列表需要的基础数据
     * @date 2018/11/13 15:50
     * @param []
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/basicData", method = RequestMethod.GET)
    public ResponseData basicData() {
        List<Hr> hrList = hrService.getAllHr();
        Map<String, Object> map = new HashMap<>();
        map.put("hrList", hrList);
        return ResultCodeEnum.SUCCESS.getResponse(map);
    }

    /*
     * @author ll
     * @Description 分页列表
     * @date 2018/11/13 15:50
     * @param [talentPool]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/getTalentPage", method = RequestMethod.GET)
    public ResponseData getTalentPage(TalentPool talentPool) {
        Map<String, Object> map = new HashMap<>();
        talentPool.setPageNo((talentPool.getPageNo() - 1) * talentPool.getPageSize());
        //获取当前登录信息将信息塞入hrId
        talentPool.setHrId(HrUtils.getCurrentHr().getId().intValue());
        List<TalentPool> talentPoolList = talentPoolService.queryPage(talentPool);
        int count = talentPoolService.queryPageCount(talentPool);
        map.put("talents", talentPoolList);
        map.put("count", count);
        return ResultCodeEnum.SUCCESS.getResponse(map);
    }

    /*
     * @author ll
     * @Description 更新
     * @date 2018/11/13 15:50
     * @param [talentPool]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/updateTalent", method = RequestMethod.POST)
    public ResponseData updateTalent(TalentPool talentPool) {

//        TalentPool tp = talentPoolService.queryById(talentPool.getId() + "");
            try {
                talentPoolService.edit(talentPool);
            }catch ( Exception ex){
                ex.printStackTrace();
            }

        return ResultCodeEnum.SUCCESS.getResponse();
    }
    /*
     * @author ll
     * @Description 导出数据
     * @date 2018/11/13 15:50
     * @param [talentPool, response]
     * @return void
     */
    @RequestMapping(value = "/exportTalent", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportTalent(String id , HttpServletResponse response) {

//        ResponseEntity<byte[]> responseEntity = PoiUtils.exportInterviewExcel(talentPoolService.getExportById(id));
        return null;
    }

    public void download(HttpServletResponse response, byte[] byt,
                         String fileName) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="
                + fileName);

        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            out.write(byt);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /*
     * @author ll
     * @Description 导入数据
     * @date 2018/11/13 15:50
     * @param [file]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/importTalent", method = RequestMethod.POST)
    public ResponseData importEmp(MultipartFile file) {
        List<Hr> hrList = hrService.getAllHr();
        List<TalentPool> talentPools = PoiUtils.importTalent2List(file, hrList);
        if(talentPools.size() > 0){
            for(TalentPool tp : talentPools){
                //根据手机查询  是否已经 存在
                TalentPool queryTp = new TalentPool();
                List<TalentPool> list = talentPoolService.queryAll(queryTp);
                if(list != null && list.size() > 0){
                    tp.setId(list.get(0).getId());
                    talentPoolService.edit(tp);
                   //存在 更新
                }else{
                    //不存在 添加
                    talentPoolService.add(tp);
                }
            }
        }

        return ResultCodeEnum.SUCCESS.getResponse();
    }


    /*
     * @author ll
     * @Description 新增
     * @date 2018/11/13 15:51
     * @param [talentPool]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData add(TalentPool talentPool) {
        Long nowMillis = System.currentTimeMillis();
        talentPool.setHrId(HrUtils.getCurrentHr().getId().intValue());
        try{
            talentPoolService.add(talentPool);

            TalentOper to = new TalentOper();
            to.setHrId(talentPool.getHrId());
            to.setOperTime(System.currentTimeMillis());
            to.setTalentId(talentPool.getId());
            talentOperService.add(to);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ResultCodeEnum.SUCCESS.getResponse();
    }

    /*
     * @author ll
     * @Description 导出统计
     * @date 2018/11/13 15:51
     * @param [talentPool]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/exportCount", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportCount(String id , HttpServletResponse response) {
        ResponseEntity<byte[]> responseEntity=null;
        try {

            responseEntity = PoiUtils.exportInterviewExcel(talentPoolService.getExportById(HrUtils.getCurrentHr().getId(),id));
        }catch (Exception ex){
            ex.printStackTrace();
        }
       return responseEntity;// 下载文档
    }


    /*
     * @author ll
     * @Description 根据主键获取
     * @date 2018/11/14 9:58
     * @param [id]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/getTalent", method = RequestMethod.GET)
    public ResponseData getTalent(String id) {
        TalentPool talentPool = talentPoolService.queryById(id);
        return ResultCodeEnum.SUCCESS.getResponse(talentPool);
    }

    /*
     * @author ll
     * @Description 新增
     * @date 2018/11/13 15:51
     * @param [talentPool]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/addTalentPool", method = RequestMethod.POST)
    public RespBean addTalentPool(TalentPool talentPool) {
//        Long nowMillis = System.currentTimeMillis();
//        talentPool.setAddDate(nowMillis + "");
//        talentPool.setOperTime(nowMillis + "");
        if(StringUtils.isEmpty(talentPool.getRecommendTime())){ //talentPool.getInterviewDate()) + "",CommonUtis.YYYY_MM_DD
            talentPool.setRecommendTime(DateTimeUtil.dateToStamp(talentPool.getRecommendTime())+"");
        }
        talentPool.setHrId(HrUtils.getCurrentHr().getId().intValue());
        talentPool.setHrName(HrUtils.getCurrentHr().getName());
        if(0!=talentPoolService.add(talentPool)){
            return RespBean.ok("加入面试成功！");
        }
        return RespBean.error("加入面试失败！");
    }




    /*
     * @author ll
     * @Description 面试分页列表
     * @date 2018/11/13 15:50
     * @param [talentPool]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/getTalentPoolPage", method = RequestMethod.GET)
    public ResponseData getTalentPoolPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "YES") String hr_id) {
        int hrId = 0;
        Long count = 0L;
        if("YES".equals(hr_id)){
            hrId =Integer.parseInt(String.valueOf(HrUtils.getCurrentHr().getId()));
            count = empService.getCountByNameHrId(name,hrId);
        }else {
            count = empService.getCountByName(name);
        }
        Map<String, Object> map = new HashMap<>();
        List<TalentPool> employeeByPage = talentPoolService.getTalentPoolPage(hrId,page, size,name);
        map.put("talents", employeeByPage);
        map.put("count", count);
        return ResultCodeEnum.SUCCESS.getResponse(map);
    }

    /*
     * @author ll
     * @Description 面试分页列表
     * @date 2018/11/13 15:50
     * @param [talentPool]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/getInterviewPage", method = RequestMethod.GET)
    public ResponseData getInterviewPage(TalentPool talentPool) {
        Map<String, Object> map = new HashMap<>();
        talentPool.setPageNo((talentPool.getPageNo() - 1) * talentPool.getPageSize());
        List<TalentPool> talentPoolList = talentPoolService.queryPage(talentPool);
        int count = talentPoolService.queryPageCount(talentPool);
        map.put("talents", talentPoolList);
        map.put("count", count);
        return ResultCodeEnum.SUCCESS.getResponse(map);
    }

    @RequestMapping(value = "/updateShowEdit", method = RequestMethod.PUT)
    public RespBean updateShowEdit(String ids) {

        if (!StringUtils.isEmpty(ids)) {
            String[] listId = ids.split(",");
            for (int i = 0; i < listId.length; ) {
                if (talentPoolService.updateShowEdit(listId[i].trim()) != 0) {
                    if (i == listId.length - 1) {
                        return RespBean.ok("设置不能编辑成功！");
                    }
                }
            }
        }
        return RespBean.error("设置不能编辑失败！");
    }

}
