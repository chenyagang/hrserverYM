package org.sang.controller.emp;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.sang.bean.Employee;
import org.sang.bean.Hr;
import org.sang.bean.Position;
import org.sang.bean.RespBean;
import org.sang.common.*;
import org.sang.common.poi.PoiParseWord;
import org.sang.common.poi.PoiParseXLS;
import org.sang.common.poi.PoiUtils;
import org.sang.resources.Resource;
import org.sang.service.*;
import org.sang.utils.DateTimeUtil;
import org.sang.utils.WordDocx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    @Autowired
    Resource resourcel;

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
        Hr hr = new Hr();
        if(0!=employee.getHr_id()){
            hr = hrService.getHrByIdEdit( employee.getHr_id());
        }
        employee.setHr(hr.getName());
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

    @RequestMapping(value = "/updateInterview", method = RequestMethod.PUT)
    public RespBean updateInterview(String ids) {

        if (!StringUtils.isEmpty(ids)) {
            String[] listId = ids.split(",");
            for (int i = 0; i < listId.length; ) {
                if (empService.updateEmpShowResumeById(Integer.parseInt(listId[i].trim())) != 0) {
                    if (i == listId.length - 1) {
                        return RespBean.ok("设置不能编辑成功！");
                    }
                }
            }
        }
        return RespBean.error("设置不能编辑失败！");
    }

    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public Map<String, Object> getEmployeeByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "YES") String hrFlag) {
        int hrId = 0;
        Long count = 0L;
        if ("YES".equals(hrFlag.trim())) {
            hrId = new Long(HrUtils.getCurrentHr().getId()).intValue();
            count = empService.getCountByNameHrId(name, hrId);
        } else {
            count = empService.getCountByName(name);
        }
        Map<String, Object> map = new HashMap<>();
        List<Employee> employeeByPage = empService.getEmployeeByPage(page, size, name, hrId);
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
        Employee emp = null;
        try {
            emp = empService.getById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        List list = new ArrayList();
        list.add(emp);
        return PoiUtils.exportEmp2Excel(list);
    }

    @RequestMapping(value = "/exportWord", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportWord(String id) throws IOException, InvalidFormatException {
        Employee emp = empService.getById(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String inFile = System.getProperty("user.dir") + "/hrserver/src/main/resources/static/moban.docx";
        Map<String, Object> data = new HashMap<>();
        data.put("name", emp.getName());
        data.put("sex", emp.getGender());
        data.put("interviewTime", formatter.format(emp.getInterviewTime()));
        data.put("workTime", formatter.format(emp.getWorkTime()));
        data.put("phone", emp.getPhone());
        data.put("introduction", emp.getIntroduction());
        data.put("degree", emp.getTiptopDegree());
        data.put("workExperience", emp.getWorkExperience());
        data.put("projectExperience", emp.getProjectExperience());

        return WordDocx.readwriteWord(inFile, data);
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
            @RequestParam(defaultValue = "YES") String hr_id) {
        int hrId = 0;
        Long count = 0L;
        if ("YES".equals(hr_id)) {
            hrId = Integer.parseInt(String.valueOf(HrUtils.getCurrentHr().getId()));
            count = empService.getCountByNameHrId(name, hrId);
        } else {
            count = empService.getCountByName(name);
        }
        Map<String, Object> map = new HashMap<>();
        List<Employee> employeeByPage = empService.getEmployeeByPageAndHrId(hrId, page, size, name);

        map.put("talents", employeeByPage);
        map.put("count", count);
        return ResultCodeEnum.SUCCESS.getResponse(map);
    }

    @RequestMapping(value = "/transferAuthority", method = RequestMethod.POST)
    public RespBean TransferAuthority(String id) {
        Employee employee = empService.getById(id);
        employee.setTransferTime(new Date());
        employee.setHr_id(HrUtils.getCurrentHr().getId().intValue());
        employee.setHr(HrUtils.getCurrentHr().getName());
        try {
            if (empService.updateEmp(employee) == 1) {
                return RespBean.ok("转让成功!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return RespBean.error("转让失败!");
    }

//    /*
//     * 采用file.Transto 来保存上传的文件
//     */
//    @RequestMapping("fileUpload2")
//    public String  fileUpload2(@RequestParam("file") CommonsMultipartFile file) throws IOException {
//        long  startTime=System.currentTimeMillis();
//        System.out.println("fileName："+file.getOriginalFilename());
//        String path="E:/"+new Date().getTime()+file.getOriginalFilename();
//
//        File newFile=new File(path);
//        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
//        file.transferTo(newFile);
//        long  endTime=System.currentTimeMillis();
//        System.out.println("采用file.Transto的运行时间："+String.valueOf(endTime-startTime)+"ms");
//        return "/success";
//    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public RespBean uploadResumeFile(@RequestParam("file") MultipartFile file) {
//        String fileName=filePathName+new Date().getTime()+file.getOriginalFilename();
        String timeName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String osName = System.getProperties().getProperty("os.name");
        String fileSeparator = System.getProperties().getProperty("file.separator");
        String path = resourcel.getServerFilePath() + File.separator + timeName + file.getOriginalFilename();
        String mkdirName = resourcel.getServerFilePath() + File.separator;
        if (osName.contains("Windows") || "\\".equals(fileSeparator)) {//如果是windows使用win路径
            path = resourcel.getWinFilePath() + File.separator + timeName + file.getOriginalFilename();
            mkdirName = resourcel.getWinFilePath() + File.separator;
        }

        File newFile = null;
        try {
            File fileMkdir = new File(mkdirName);
            if (!fileMkdir.exists()) {
                fileMkdir.mkdir();
            }
            newFile = new File(path);
            //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
            file.transferTo(newFile);
        } catch (Exception e) {
            CommonUtis.deleteFile(path);//上传失败删除上传文件
            return RespBean.error("上传失败");
        }
        return analysisWordPDF(newFile, path);//分析简历关键字返回实体
    }

//    public static void main(String[] args) {
//        deleteFile("F:\\photoTest\\20200329114428谢艳明-项目经理简历.pdf");
//    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.PUT)
    public RespBean deleteFile(String fileURL) {
        if (CommonUtis.deleteFile(fileURL)) {
            return RespBean.ok("删除文件成功");
        }
        return RespBean.error("删除文件失败");
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile(String fileName, HttpServletResponse response) {
        try {
            InputStream inStream = new FileInputStream(fileName);
            response.reset();
            response.setContentType("bin");
            String[] file = fileName.split("\\\\");
            response.setContentType("application/force-download");
            response.addHeader("Content-disposition", "attachment;fileName=" + file[file.length - 1]);
            // 循环取出流中的数据
            byte[] buf = new byte[1024];
            int len = 0;
            // 创建输出对象
            OutputStream os = response.getOutputStream();
            while ((len = inStream.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * @author ll
     * @Description 导入数据
     * @date 2018/11/13 15:50
     * @param [file]
     * @return org.sang.common.ResponseData
     */
//    @RequestMapping(value = "/importEmp", method = RequestMethod.POST)
    public RespBean analysisWordPDF(@RequestParam("file") File file, String filePath) {
        Employee emp = new Employee();
        String suffixName = filePath.substring(filePath.lastIndexOf(".") + 1);
        if (PoiParseWord.DOCX.equals(suffixName) || PoiParseWord.DOC.equals(suffixName)) {
            emp = PoiParseWord.readWord(file);
        } else if (PoiParseWord.PDF.equals(suffixName)) {
            emp = PoiParseWord.readPDF(file);
        } else if (PoiParseWord.TXT.equals(suffixName)) {
            emp = PoiParseWord.readTXT(file);
        } else if (PoiParseWord.XLS.equals(suffixName) || PoiParseWord.XLSX.equals(suffixName)) { //解析 xlsx，xls
            try {
                String strExcel = PoiParseXLS.readExcel(file);
                emp = CommonUtis.substring_index(strExcel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return RespBean.error("文件格式不正确！");
        }
        emp.setFileURL(filePath);
        emp.setHr(HrUtils.getCurrentHr().getName());
        emp.setHr_id(Integer.parseInt(String.valueOf(HrUtils.getCurrentHr().getId())));
        if (StringUtils.isEmpty(emp.getWedlock())) {
            emp.setWedlock("未婚");
        }
        if (StringUtils.isEmpty(emp.getTiptopDegree())) {
            emp.setTiptopDegree("大专");
        }
        if (StringUtils.isEmpty(emp.getGender())) {
            emp.setGender(CommonUtis.MAN);
        }

        if (null == emp.getInterviewTime()) {
            emp.setInterviewTime(new Date());
        }

        if (null == emp.getWorkTime()) {
            emp.setWorkTime(new Date());
        }

        if (null == emp.getTransferTime()) {
            emp.setTransferTime(new Date());
        }
        if (StringUtils.isEmpty(emp.getGraduationTime())) {
            emp.setGraduationTime(DateTimeUtil.getNewDateToStr(DateTimeUtil.FORMATTIME_DATE_1));
        }
        emp.setIsDeletFile("1");//设置取消创建employee删除已上传的文件
        return RespBean.ok("上传成功!", emp);
    }

    @RequestMapping(value = "/addEmp", method = RequestMethod.POST)
    public RespBean addEmp(Employee employee) {
        String name = employee.getName();
        String phone = employee.getPhone();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)) {
            CommonUtis.deleteFile(employee.getFileURL());
            return RespBean.error("姓名或手机号码必填，请检查是否填写或者检查格式问题");
        }
        Employee employeeByPhone = null;
        employeeByPhone = empService.getEmpByPhone(phone);
        if (null != employeeByPhone) {
            CommonUtis.deleteFile(employee.getFileURL());
            return RespBean.error("重复上传！手机号码 已存在!");
        }
        if (StringUtils.isEmpty(employee.getWedlock())) {
            employee.setWedlock("未婚");
        }
        if (StringUtils.isEmpty(employee.getTiptopDegree())) {
            employee.setTiptopDegree("大专");
        }
        if (StringUtils.isEmpty(employee.getGender())) {
            employee.setGender(CommonUtis.MAN);
        }

        if (StringUtils.isEmpty(employee.getGraduationTime())) {
            employee.setGraduationTime(DateTimeUtil.getNewDateToStr(DateTimeUtil.FORMATTIME_DATE_1));
        }

        if (null == employee.getInterviewTime()) {
            employee.setInterviewTime(new Date());
        }

        if (null == employee.getWorkTime()) {
            employee.setWorkTime(new Date());
        }

        if (null == employee.getTransferTime()) {
            employee.setTransferTime(new Date());
        }

        if (empService.addEmp(employee) == 0) {
            CommonUtis.deleteFile(employee.getFileURL());
            return RespBean.error("添加候选人失败！");
        }
        return RespBean.ok("添加成功!");

    }

}

