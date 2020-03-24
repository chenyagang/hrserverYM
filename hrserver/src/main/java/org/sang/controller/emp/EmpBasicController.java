package org.sang.controller.emp;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.sang.bean.Employee;
import org.sang.bean.Position;
import org.sang.bean.RespBean;
import org.sang.common.*;
import org.sang.common.poi.PoiParseWord;
import org.sang.common.poi.PoiUtils;
import org.sang.service.*;
import org.sang.utils.WordDocx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by sang on 2018/1/12.
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {
    @Autowired
    EmpService empService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    PositionService positionService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    ExecutorService executorService;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    HrService hrService;

    @RequestMapping(value = "/basicdata", method = RequestMethod.GET)
    public Map<String, Object> getAllNations() {
        Map<String, Object> map = new HashMap<>();
        map.put("nations", empService.getAllNations());
        map.put("politics", empService.getAllPolitics());
        map.put("deps", departmentService.getDepByPid(-1L));
        map.put("positions", positionService.getAllPos());
        map.put("joblevels", jobLevelService.getAllJobLevels());
        map.put("workID", String.format("%08d", empService.getMaxWorkID() + 1));
        return map;
    }

    @RequestMapping("/maxWorkID")
    public String maxWorkID() {
        return String.format("%08d", empService.getMaxWorkID() + 1);
    }

//    @RequestMapping(value = "/emp", method = RequestMethod.POST)
//    public RespBean addEmp(Employee employee) {
//     /*   if (empService.addEmp(employee) == 1) {
//            List<Position> allPos = positionService.getAllPos();
//            for (Position allPo : allPos) {
//                if (allPo.getId() == employee.getPosId()) {
//                    employee.setPosName(allPo.getName());
//                }
//            }
//            executorService.execute(new EmailRunnable(employee,
//                    javaMailSender, templateEngine));
//            return RespBean.ok("添加成功!");
//        }*/
//        return RespBean.error("添加失败!");
//    }

    @RequestMapping(value = "/updateEmp", method = RequestMethod.PUT)
    public RespBean updateEmp(Employee employee) {
        if (empService.updateEmp(employee) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteEmpById(@PathVariable String ids) {
        if (empService.deleteEmpById(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");

    }

    @RequestMapping(value="/updateInterview",method = RequestMethod.PUT)
    public RespBean updateInterview(String ids) {

        if (!StringUtils.isEmpty(ids)) {
            String[] listId = ids.split(",");
            for (int i = 0; i < listId.length;){
                if (empService.updateEmpShowResumeById(Integer.parseInt(listId[i].trim())) != 0) {
                    if (i == listId.length-1) {
                        return RespBean.ok("更新候选人面试状态成功！");
                    }
                }
            }
        }
        return RespBean.error("更新候选人面试状态失败！");
    }

    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public Map<String, Object> getEmployeeByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "YES") String hrFlag) {
        int hrId = 0;
        Long count =0L;
        if("YES".equals(hrFlag.trim())){
            hrId =new Long(HrUtils.getCurrentHr().getId()).intValue();
            count =  empService.getCountByNameHrId(name,hrId);
        }else {
            count = empService.getCountByName(name);
        }
        Map<String, Object> map = new HashMap<>();
        List<Employee> employeeByPage = empService.getEmployeeByPage(page, size,name,hrId);
        map.put("emps", employeeByPage);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/exportEmp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmp(String id) {

        return PoiUtils.exportEmp2Excel(empService.getByIds(id));
    }

    @RequestMapping(value = "/exportExecl", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportExecl(String id) {
        Employee emp=null;
        try {
            emp = empService.getById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        List list =new ArrayList();
        list.add(emp);
        return PoiUtils.exportEmp2Excel(list);
    }

    @RequestMapping(value = "/exportWord", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportWord(String id) throws IOException, InvalidFormatException {
        Employee emp=empService.getById(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String inFile=System.getProperty("user.dir")+"/hrserver/src/main/resources/static/moban.docx";
        Map<String, Object> data=new HashMap<>();
        data.put("name",emp.getName());
        data.put("sex",emp.getGender());
        data.put("interviewTime",formatter.format(emp.getInterviewTime()));
        data.put("workTime",formatter.format(emp.getWorkTime()));
        data.put("phone",emp.getPhone());
        data.put("introduction",emp.getIntroduction());
        data.put("degree",emp.getTiptopDegree());
        data.put("workExperience",emp.getWorkExperience());
        data.put("projectExperience",emp.getProjectExperience());

        return WordDocx.readwriteWord(inFile,data);
    }

    @RequestMapping(value = "/importEmp", method = RequestMethod.POST)
    public RespBean importEmp(MultipartFile file) {
        List<Employee> emps = PoiUtils.importEmp2List(file,
                empService.getAllNations(), empService.getAllPolitics(),
                departmentService.getAllDeps(), positionService.getAllPos(),
                jobLevelService.getAllJobLevels());
        if (empService.addEmps(emps) == emps.size()) {
            return RespBean.ok("导入成功!");
        }
        return RespBean.error("导入失败!");
    }

    @RequestMapping(value = "/uploadResume", method = RequestMethod.POST)
    public RespBean uploadResume(MultipartFile file) {
        List<Employee> emps = PoiUtils.importEmp2List(file,
                empService.getAllNations(), empService.getAllPolitics(),
                departmentService.getAllDeps(), positionService.getAllPos(),
                jobLevelService.getAllJobLevels());
        if (empService.addEmps(emps) == emps.size()) {
            return RespBean.ok("导入成功!");
        }
        return RespBean.error("导入失败!");
    }

    @RequestMapping(value = "/empByPageAndHrId", method = RequestMethod.GET)
    public ResponseData getEmployeeByPageAndHrId(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String name,
           @RequestParam(defaultValue = "NO") String hr_id) {
        int hrId = 0;
        Long count = 0L;
        if("YES".equals(hr_id)){
            hrId =Integer.parseInt(String.valueOf(HrUtils.getCurrentHr().getId()));
            count = empService.getCountByNameHrId(name,hrId);
        }else {
            count = empService.getCountByName(name);
        }
        Map<String, Object> map = new HashMap<>();
        List<Employee> employeeByPage = empService.getEmployeeByPageAndHrId(hrId,page, size,name);

        map.put("talents", employeeByPage);
        map.put("count", count);
        return ResultCodeEnum.SUCCESS.getResponse(map);
    }

    @RequestMapping(value = "/transferAuthority", method = RequestMethod.POST)
    public RespBean TransferAuthority(String id) {
        Employee employee = empService.getById(id);
        employee.setTransferTime(new Date());
        employee.setHr_id(HrUtils.getCurrentHr().getId().intValue());
        try {
            if (empService.updateEmp(employee) == 1) {
                return RespBean.ok("转让成功!");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RespBean.error("转让失败!");
    }

    @RequestMapping(value="/uploadFile",method=RequestMethod.POST)
    public RespBean uploadResumeFile(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().trim();
        String fileN=file.getOriginalFilename();
        String fileName=uuid+fileN;
        String filePathName;
        try {
            File fileMkdir=new File("F:\\photoTest");
            if(!fileMkdir.exists()) {
                fileMkdir.mkdir();
            }
            filePathName = fileMkdir.getPath()+"\\"+fileName;
            //定义输出流 将文件保存在F盘  file.getOriginalFilename()为获得文件的名字
             FileOutputStream os = new FileOutputStream(filePathName);
             InputStream in = file.getInputStream();
            int b = 0;
            while((b=in.read())!=-1){ //读取文件
                os.write(b);
            }
            os.flush(); //关闭流
            in.close();
            os.close();
        } catch (Exception e) {
            return RespBean.error("上传失败");
        }
        return analysisWordPDF(file,filePathName);//分析简历关键字返回实体
    }

    /*
     * @author ll
     * @Description 导入数据
     * @date 2018/11/13 15:50
     * @param [file]
     * @return org.sang.common.ResponseData
     */
//    @RequestMapping(value = "/importEmp", method = RequestMethod.POST)
    public RespBean analysisWordPDF(@RequestParam("file")MultipartFile file,String filePath) {
        Employee emp = new Employee();
        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        if (PoiParseWord.DOCX.equals(suffixName) || PoiParseWord.DOC.equals(suffixName)) {
            emp = PoiParseWord.readWord(file,emp);
        }else {
            emp = PoiParseWord.readPDF(file,emp);
        }
        emp.setFileURL(filePath);
        emp.setHr(HrUtils.getCurrentHr().getName());
        emp.setHr_id(Integer.parseInt(String.valueOf(HrUtils.getCurrentHr().getId())));
//        Employee employeePhone = null;
//        String name = employee.getName();
//        String phone =  employee.getPhone();
//        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)){
//            CommonUtis.deleteFile(fileRespBean.getMsg());
//            return RespBean.error("姓名或手机号码必填，请检查是否填写或者检查格式问题");
//        }
//        employeePhone = empService.getEmpByPhone(phone);
//        if(null==employeePhone){
//            return RespBean.error("根据电话号码查找数据库失败，请查看日志");
//        }
//        if(null != employeePhone){
//            CommonUtis.deleteFile(fileRespBean.getMsg());
//            return RespBean.error("重复上传！手机号码 已存在!");
//        }
        if(StringUtils.isEmpty(emp.getWedlock())){
            emp.setWedlock(CommonUtis.UNMARRIED);
        }
        if(StringUtils.isEmpty(emp.getGender())){
            emp.setGender(CommonUtis.MAN);
        }
//        if(empService.addEmp(employee)==0){
//            CommonUtis.deleteFile(fileRespBean.getMsg());
//            return RespBean.error("添加候选人失败！");
//        }emp
        return RespBean.ok("上传成功!",emp);
    }

    @RequestMapping(value = "/addEmp", method = RequestMethod.POST)
    public RespBean addEmp(Employee employee) {
        String name = employee.getName();
        String phone =  employee.getPhone();
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)){
            CommonUtis.deleteFile(employee.getFileURL());
            return RespBean.error("姓名或手机号码必填，请检查是否填写或者检查格式问题");
        }
        Employee employeeByPhone = null;
        employeeByPhone = empService.getEmpByPhone(phone);
        if(null != employeeByPhone){
            CommonUtis.deleteFile(employee.getFileURL());
            return RespBean.error("重复上传！手机号码 已存在!");
        }
        if(empService.addEmp(employee)==0){
            CommonUtis.deleteFile(employee.getFileURL());
            return RespBean.error("添加候选人失败！");
        }
        return RespBean.ok("添加成功!");

    }



}
