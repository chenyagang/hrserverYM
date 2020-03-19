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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

        TalentPool tp = talentPoolService.queryById(talentPool.getId() + "");
        if(tp.getHrId() != HrUtils.getCurrentHr().getId().intValue()){
            //一周之内不能编辑别人的
            long nowMillis = System.currentTimeMillis();
            long addMillis = Long.valueOf(tp.getAddDate());
            if(nowMillis - addMillis <= 7*24*3600*1000){
                return ResultCodeEnum.CANNOT_EDIT_IN_WEEK.getResponse();
            }else{
                talentPool.setHrId(HrUtils.getCurrentHr().getId().intValue());
            }
        }

        //记录改变状态的时间  以便统计
        if(talentPool.getStatus() != null && tp.getStatus() != talentPool.getStatus()){
            talentPool.setOperTime(System.currentTimeMillis() + "");
            TalentOper to = new TalentOper();
            to.setHrId(tp.getHrId());
            to.setOperTime(System.currentTimeMillis());
            to.setTalentId(talentPool.getId());
            to.setStatus(talentPool.getStatus());
            talentOperService.add(to);
        }

        if(talentPool.getInterviewDate() != null){
            talentPool.setInterviewDate(DateTimeUtil.dateToStamp(talentPool.getInterviewDate()) + "");
        }
        talentPool.setAddDate(null);
        talentPoolService.edit(talentPool);
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
    public void exportTalent(TalentPool talentPool , HttpServletResponse response) {
        List<TalentPool> talentPools = talentPoolService.queryAll(talentPool);

        if(talentPools != null){
            for(TalentPool tp : talentPools){
                tp.setStatusText(statusTextArr[tp.getStatus()]);
                if(tp.getAddDate() != null){
                    tp.setAddDate(DateTimeUtil.timeMillinToDateStr(Long.parseLong(tp.getAddDate()) ,DateTimeUtil.FORMATTIME_DATE_3));
                }
                if(tp.getInterviewDate() != null){
                    tp.setInterviewDate(DateTimeUtil.timeMillinToDateStr(Long.parseLong(tp.getInterviewDate()) ,DateTimeUtil.FORMATTIME_DATE_3));
                }
            }
        }

        byte[] bytes = PoiUtils.exportTalent2Excel("sheet", ExportField.ExportTitle.TALENT_TITLE, ExportField.ExportColumn.TALENT_COLUMN, talentPools, false);
        String fileName = "数据";
        try {
            fileName = new String((fileName + ".xls").getBytes("gb2312"),
                    "ISO8859-1");// 设置文件编码
        }catch (Exception e){
            e.printStackTrace();
        }
        download(response, bytes, fileName);// 下载文档
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
                queryTp.setPhone(tp.getPhone());
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
        talentPool.setAddDate(nowMillis + "");
        talentPool.setOperTime(nowMillis + "");
        if(talentPool.getInterviewDate() != null){
            talentPool.setInterviewDate(DateTimeUtil.dateToStamp(talentPool.getInterviewDate()) + "");
        }
        talentPool.setHrId(HrUtils.getCurrentHr().getId().intValue());
        talentPoolService.add(talentPool);


        talentPool.setOperTime(System.currentTimeMillis() + "");
        TalentOper to = new TalentOper();
        to.setHrId(talentPool.getHrId());
        to.setOperTime(System.currentTimeMillis());
        to.setTalentId(talentPool.getId());
        to.setStatus(talentPool.getStatus());
        talentOperService.add(to);

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
    public void exportCount(String countStart , String countEnd , HttpServletResponse response) {
        //long countStartTime = DateTimeUtil.dateToStamp(countStart , DateTimeUtil.FORMATTIME_DATE_1);
        //long countEndTime = DateTimeUtil.dateToStamp(countEnd , DateTimeUtil.FORMATTIME_DATE_1);
        //Long hrId = HrUtils.getCurrentHr().getId();
        Map<String , Object> map = new HashMap<>();
        map.put("countStartTime",countStart);
        map.put("countEndTime",countEnd);
        //map.put("hrId",hrId);
        List<ExportCount> exportCountList = talentPoolService.getExportCountList(map);
        //byte[] bytes = PoiUtils.exportTalent2Excel("sheet", ExportField.ExportTitle.TALENT_COUNT_TITLE, ExportField.ExportColumn.TALENT_COUNT_TITLE, exportCountList, false);
        HSSFWorkbook wb = new HSSFWorkbook();

        String[][] data1 = PoiUtils.convertByObj(ExportField.ExportColumn.TALENT_COUNT_COLUMN, exportCountList);
        PoiUtils.getWorkBook(wb, "HR周报情况", 0, ExportField.ExportTitle.TALENT_COUNT_TITLE, data1, true);

        List<TalentPool> offerList = talentPoolService.getOfferList(map);
        String[][] data2 = PoiUtils.convertByObj(ExportField.ExportColumn.TALENT_OFFER_COLUMN, offerList);
        PoiUtils.getWorkBook(wb, "已offer人员情况", 1, ExportField.ExportTitle.TALENT_OFFER_TITLE, data2, true);
        byte[] bytes = PoiUtils.getBytes(wb);

        String countStartTime = DateTimeUtil.timeMillinToDateStr(Long.parseLong(countStart) , DateTimeUtil.FORMATTIME_DATE_4);
        String countEndTime = DateTimeUtil.timeMillinToDateStr(Long.parseLong(countEnd) , DateTimeUtil.FORMATTIME_DATE_4);

        String fileName = countStartTime + "-" + countEndTime;

        try {
            fileName = new String((fileName + ".xls").getBytes("gb2312"),
                    "ISO8859-1");// 设置文件编码
        }catch (Exception e){
            e.printStackTrace();
        }
        download(response, bytes, fileName);// 下载文档
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
     * @Description 导入数据
     * @date 2018/11/13 15:50
     * @param [file]
     * @return org.sang.common.ResponseData
     */
    @RequestMapping(value = "/importEmp", method = RequestMethod.POST)
    public ResponseData importEmp(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        if(null == file || file.getOriginalFilename().lastIndexOf(".") == -1){
            Map<String, Object> map = new HashMap<>();
            return new ResponseData(commonUtis.FAIL_1,"文件是空或者文件类型错误");
        }
        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        if (PoiParseWord.DOCX.equals(suffixName) || PoiParseWord.DOC.equals(suffixName) || PoiParseWord.PDF.equals(suffixName)) {
        }else {
            Map<String, Object> map = new HashMap<>();
            return  new ResponseData(commonUtis.FAIL_1,"文件类型错误,目前只支持: .doc .docx .pdf 格式文件");
        }
        ResponseData fileResponseData = uploadFile(file,request);//上传文件
        if( fileResponseData.getResultCode()==1){//上传文件失败 返回
            return fileResponseData;
        }

        Employee employee = new Employee();
        if (PoiParseWord.DOCX.equals(suffixName) || PoiParseWord.DOC.equals(suffixName)) {
            employee = PoiParseWord.readWord(file,employee);
        }else {
            employee = PoiParseWord.readPDF(file,employee);
        }
        employee.setFileURL(fileResponseData.getMessage());
        employee.setHR(HrUtils.getCurrentHr().getName());
        Employee employeeNameAndPhone;
        employeeNameAndPhone = empService.getEmpByNameAndPhone(employee.getName(),employee.getPhone());
        if(null != employeeNameAndPhone){
            commonUtis.deleteFile(fileResponseData.getMessage());
            return new ResponseData(commonUtis.FAIL_1,"重复上传！姓名,手机号码 已存在!");
        }
        int i = empService.addEmp(employee);
        if(empService.addEmp(employee)==0){
            commonUtis.deleteFile(fileResponseData.getMessage());
            return new ResponseData(commonUtis.FAIL_1,"插入数据失败");
        }

        return new ResponseData(commonUtis.SUCCESS_0,"上传成功!");
    }

    @RequestMapping(value="/uploadFile",method=RequestMethod.POST)
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().trim();
        String fileN=file.getOriginalFilename();
        int index=fileN.indexOf(".");
        String fileName=uuid+fileN.substring(index);
        String filePathName;
        try {
            File fileMkdir=new File("F:\\photoTest");
            if(!fileMkdir.exists()) {
                fileMkdir.mkdir();
            }
            filePathName = fileMkdir.getPath()+"\\"+fileName;
            //定义输出流 将文件保存在F盘  file.getOriginalFilename()为获得文件的名字
            FileOutputStream os = new FileOutputStream(fileMkdir.getPath()+"\\"+fileName);
            InputStream in = file.getInputStream();
            int b = 0;
            while((b=in.read())!=-1){ //读取文件
                os.write(b);
            }
            os.flush(); //关闭流
            in.close();
            os.close();
        } catch (Exception e) {
            return new ResponseData(commonUtis.FAIL_1,"上传失败");
        }
        return new ResponseData(commonUtis.SUCCESS_0,filePathName);
    }


}
