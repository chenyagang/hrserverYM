package org.sang.common.poi;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.sang.bean.*;
import org.sang.pagebean.Interview;
import org.sang.utils.DateTimeUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sang on 2018/1/16.
 */
public class PoiUtils {

    public static ResponseEntity<byte[]> exportEmp2Excel(List<Employee> emps) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("员工信息");
            //3.2设置文档管理员
            dsi.setManager("江南一点雨");
            //3.3设置组织机构
            dsi.setCompany("XXX集团");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("员工信息表");
            //4.2.设置文档标题
            si.setTitle("员工信息");
            //4.3 设置文档作者
            si.setAuthor("XXX集团");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("候选人信息表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 12 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 12 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 10 * 256);
            sheet.setColumnWidth(7, 10 * 256);
            sheet.setColumnWidth(8, 16 * 256);
            sheet.setColumnWidth(9, 12 * 256);
            sheet.setColumnWidth(10, 15 * 256);
            sheet.setColumnWidth(11, 20 * 256);
            sheet.setColumnWidth(12, 16 * 256);
            sheet.setColumnWidth(13, 14 * 256);
            sheet.setColumnWidth(14, 14 * 256);
            sheet.setColumnWidth(15, 12 * 256);
            sheet.setColumnWidth(16, 8 * 256);
            sheet.setColumnWidth(17, 16 * 256);
            sheet.setColumnWidth(18, 20 * 256);
            sheet.setColumnWidth(19, 12 * 256);
            sheet.setColumnWidth(20, 8 * 256);
            sheet.setColumnWidth(21, 25 * 256);
            sheet.setColumnWidth(22, 14 * 256);
            sheet.setColumnWidth(23, 12 * 256);
            sheet.setColumnWidth(24, 12 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("编号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("姓名");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("电话");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("学校");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("最高学历");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("毕业时间");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(6);
            cell6.setCellValue("性别");
            cell6.setCellStyle(headerStyle);
            HSSFCell cell7 = headerRow.createCell(7);
            cell7.setCellValue("岗位");
            cell7.setCellStyle(headerStyle);
            HSSFCell cell8 = headerRow.createCell(8);
            cell8.setCellValue("工龄");
            cell8.setCellStyle(headerStyle);
            HSSFCell cell9 = headerRow.createCell(9);
            cell9.setCellValue("婚姻状况");
            cell9.setCellStyle(headerStyle);
            HSSFCell cell10 = headerRow.createCell(10);
            cell10.setCellValue("当前薪资");
            cell10.setCellStyle(headerStyle);
            HSSFCell cell11 = headerRow.createCell(11);
            cell11.setCellValue("期望薪资");
            cell11.setCellStyle(headerStyle);
            HSSFCell cell12 = headerRow.createCell(12);
            cell12.setCellValue("推荐客户");
            cell12.setCellStyle(headerStyle);
            HSSFCell cell13 = headerRow.createCell(13);
            cell13.setCellValue("渠道");
            cell13.setCellStyle(headerStyle);
            HSSFCell cell14 = headerRow.createCell(14);
            cell14.setCellValue("沟通内容");
            cell14.setCellStyle(headerStyle);
            HSSFCell cell15 = headerRow.createCell(15);
            cell15.setCellValue("HR");
            cell15.setCellStyle(headerStyle);
            HSSFCell cell16 = headerRow.createCell(16);
            cell16.setCellValue("面试时间");
            cell16.setCellStyle(headerStyle);
            HSSFCell cell17 = headerRow.createCell(17);
            cell17.setCellValue("到岗时间");
            cell17.setCellStyle(headerStyle);
            HSSFCell cell18 = headerRow.createCell(18);
            cell18.setCellValue("简介");
            cell18.setCellStyle(headerStyle);
            HSSFCell cell19 = headerRow.createCell(19);
            cell19.setCellValue("工作经验描述");
            cell19.setCellStyle(headerStyle);
            HSSFCell cell20 = headerRow.createCell(20);
            cell20.setCellValue("项目经验");
            cell20.setCellStyle(headerStyle);
            HSSFCell cell21 = headerRow.createCell(21);
            cell21.setCellValue("工作地点");
            cell21.setCellStyle(headerStyle);
            //6.装数据
            for (int i = 0; i < emps.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                Employee emp = emps.get(i);
                row.createCell(0).setCellValue(emp.getId()==null?0:emp.getId());
                row.createCell(1).setCellValue(emp.getName()==null?"":emp.getName());
                row.createCell(2).setCellValue(emp.getPhone()==null?"":emp.getPhone());
                row.createCell(3).setCellValue(emp.getSchool()==null?"":emp.getSchool());
                row.createCell(4).setCellValue(emp.getTiptopDegree()==null?"":emp.getTiptopDegree());
                HSSFCell birthdayCell = row.createCell(5);
                birthdayCell.setCellValue(emp.getGraduationTime()==null?"":emp.getGraduationTime());
                birthdayCell.setCellStyle(dateCellStyle);
//                row.createCell(5).setCellValue(emp.getGraduationTime());
                row.createCell(6).setCellValue(emp.getGender()==null?"":emp.getGender());
                row.createCell(7).setCellValue(emp.getJob()==null?"":emp.getJob());
                row.createCell(8).setCellValue(emp.getWorkAge()==null?0:emp.getWorkAge());
                row.createCell(9).setCellValue(emp.getWedlock()==null?"":emp.getWedlock());
                row.createCell(10).setCellValue(emp.getCurrentSalary()==null?0:emp.getCurrentSalary());
                row.createCell(11).setCellValue(emp.getExpectedSalary()==null?0:emp.getExpectedSalary());
                row.createCell(12).setCellValue(emp.getRecommendClient()==null?"":emp.getRecommendClient());
                row.createCell(13).setCellValue(emp.getChannel()==null?"":emp.getChannel());
                row.createCell(14).setCellValue(emp.getCommunicationContent()==null?"":emp.getCommunicationContent());
                row.createCell(15).setCellValue(emp.getHr()==null?"":emp.getHr());

                HSSFCell interviewTime = row.createCell(16);
                interviewTime.setCellValue(emp.getInterviewTime()==null?new Date():emp.getInterviewTime());
                interviewTime.setCellStyle(dateCellStyle);

                HSSFCell workTime = row.createCell(17);
                workTime.setCellValue(emp.getWorkTime()==null?new Date():emp.getWorkTime());
                workTime.setCellStyle(dateCellStyle);

                row.createCell(18).setCellValue(emp.getIntroduction()==null?"":emp.getIntroduction());
                row.createCell(19).setCellValue(emp.getWorkExperience()==null?"":emp.getIntroduction());
                row.createCell(20).setCellValue(emp.getProjectExperience()==null?"":emp.getIntroduction());
                row.createCell(21).setCellValue(emp.getWorkplace()==null?"":emp.getIntroduction());
            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String("候选人信息.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static ResponseEntity<byte[]> exportInductionExcel(List<Induction> emps) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("入职信息");
            //3.2设置文档管理员
            dsi.setManager("江南一点雨");
            //3.3设置组织机构
            dsi.setCompany("睿辉");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("入职信息表");
            //4.2.设置文档标题
            si.setTitle("入职信息");
            //4.3 设置文档作者
            si.setAuthor("睿辉");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("入职信息表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 12 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 12 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 10 * 256);
            sheet.setColumnWidth(7, 10 * 256);
            sheet.setColumnWidth(8, 16 * 256);
            sheet.setColumnWidth(9, 12 * 256);
            sheet.setColumnWidth(10, 15 * 256);
            sheet.setColumnWidth(11, 20 * 256);
            sheet.setColumnWidth(12, 16 * 256);
            sheet.setColumnWidth(13, 14 * 256);
            sheet.setColumnWidth(14, 14 * 256);
            sheet.setColumnWidth(15, 12 * 256);
            sheet.setColumnWidth(16, 8 * 256);
            sheet.setColumnWidth(17, 16 * 256);
            sheet.setColumnWidth(18, 20 * 256);
            sheet.setColumnWidth(19, 12 * 256);
            sheet.setColumnWidth(20, 8 * 256);
            sheet.setColumnWidth(21, 25 * 256);
            sheet.setColumnWidth(22, 14 * 256);
            sheet.setColumnWidth(23, 12 * 256);
            sheet.setColumnWidth(24, 12 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("序号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("姓名");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("性别");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("入职时间");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("技术模块");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("部门");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(6);
            cell6.setCellValue("入职地点");
            cell6.setCellStyle(headerStyle);
            HSSFCell cell7 = headerRow.createCell(7);
            cell7.setCellValue("员工类别");
            cell7.setCellStyle(headerStyle);
            HSSFCell cell8 = headerRow.createCell(8);
            cell8.setCellValue("工作年限");
            cell8.setCellStyle(headerStyle);
            HSSFCell cell9 = headerRow.createCell(9);
            cell9.setCellValue("客户");
            cell9.setCellStyle(headerStyle);
            HSSFCell cell10 = headerRow.createCell(10);
            cell10.setCellValue("转正时间");
            cell10.setCellStyle(headerStyle);
            HSSFCell cell11 = headerRow.createCell(11);
            cell11.setCellValue("毕业时间");
            cell11.setCellStyle(headerStyle);
            HSSFCell cell12 = headerRow.createCell(12);
            cell12.setCellValue("学历");
            cell12.setCellStyle(headerStyle);
            HSSFCell cell13 = headerRow.createCell(13);
            cell13.setCellValue("手机");
            cell13.setCellStyle(headerStyle);
            HSSFCell cell14 = headerRow.createCell(14);
            cell14.setCellValue("正式薪资");
            cell14.setCellStyle(headerStyle);
            HSSFCell cell15 = headerRow.createCell(15);
            cell15.setCellValue("试用期薪资");
            cell15.setCellStyle(headerStyle);
            HSSFCell cell16 = headerRow.createCell(16);
            cell16.setCellValue("客户报价");
            cell16.setCellStyle(headerStyle);
            HSSFCell cell17 = headerRow.createCell(17);
            cell17.setCellValue("定级");
            cell17.setCellStyle(headerStyle);
            HSSFCell cell18 = headerRow.createCell(18);
            cell18.setCellValue("身份证");
            cell18.setCellStyle(headerStyle);
            HSSFCell cell19 = headerRow.createCell(19);
            cell19.setCellValue("银行卡号");
            cell19.setCellStyle(headerStyle);
            HSSFCell cell20 = headerRow.createCell(20);
            cell20.setCellValue("银行");
            cell20.setCellStyle(headerStyle);
            HSSFCell cell21 = headerRow.createCell(21);
            cell21.setCellValue("渠道");
            cell21.setCellStyle(headerStyle);
            HSSFCell cell22 = headerRow.createCell(22);
            cell22.setCellValue("公司");
            cell22.setCellStyle(headerStyle);
            HSSFCell cell23 = headerRow.createCell(23);
            cell23.setCellValue("备注");
            cell23.setCellStyle(headerStyle);
            HSSFCell cell24 = headerRow.createCell(24);
            cell24.setCellValue("入项");
            cell24.setCellStyle(headerStyle);

            //6.装数据
            for (int i = 0; i < emps.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                Induction emp = emps.get(i);
                row.createCell(0).setCellValue(emp.getId()==null?0:emp.getId());
                row.createCell(1).setCellValue(emp.getName()==null?"":emp.getName());
                row.createCell(2).setCellValue(emp.getGender()==null?"":emp.getGender());
                row.createCell(3).setCellValue(emp.getInductionDate()==null?"":emp.getInductionDate());
                row.createCell(4).setCellValue(emp.getTechnologyModule()==null?"":emp.getTechnologyModule());
                row.createCell(5).setCellValue(emp.getDepartment()==null?"":emp.getDepartment());
                row.createCell(6).setCellValue(emp.getInductionPlace()==null?"":emp.getInductionPlace());
                row.createCell(7).setCellValue(emp.getCategory()==null?"":emp.getCategory());
                row.createCell(8).setCellValue(emp.getWorkAge()==null?0:emp.getWorkAge());
                row.createCell(9).setCellValue(emp.getCustomer()==null?"":emp.getCustomer());
                row.createCell(10).setCellValue(emp.getPositiveTime()==null?"":emp.getPositiveTime());
                row.createCell(11).setCellValue(emp.getGraduationTime()==null?"":emp.getGraduationTime());
                row.createCell(12).setCellValue(emp.getDegree()==null?"":emp.getDegree());
                row.createCell(13).setCellValue(emp.getPhone()==null?"":emp.getPhone());
                row.createCell(14).setCellValue(emp.getSalary()==null?0:emp.getSalary());
                row.createCell(15).setCellValue(emp.getProbationSalary()==null?0:emp.getProbationSalary());
                row.createCell(16).setCellValue(emp.getCstomerQuotation()==null?0:emp.getCstomerQuotation());
                row.createCell(17).setCellValue(emp.getGrading()==null?"":emp.getGrading());
                row.createCell(18).setCellValue(emp.getIdNumber()==null?"":emp.getIdNumber());
                row.createCell(19).setCellValue(emp.getBankCardNumber()==null?"":emp.getBankCardNumber());
                row.createCell(20).setCellValue(emp.getBank()==null?"":emp.getBank());
                row.createCell(21).setCellValue(emp.getChannel()==null?"":emp.getChannel());
                row.createCell(22).setCellValue(emp.getCompany()==null?"":emp.getCompany());
                row.createCell(23).setCellValue(emp.getNote()==null?"":emp.getNote());
                row.createCell(24).setCellValue(emp.getItem()==null?"":emp.getItem());

            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String("入职信息表.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }


    public static ResponseEntity<byte[]> exportInterviewExcel(List<Interview> interviews) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("面试信息");
            //3.2设置文档管理员
            dsi.setManager("江南一点雨");
            //3.3设置组织机构
            dsi.setCompany("瑞辉");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("面试信息表");
            //4.2.设置文档标题
            si.setTitle("面试信息");
            //4.3 设置文档作者
            si.setAuthor("江南一点雨");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("面试人信息表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 12 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 12 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 10 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("编号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("hr");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("推荐客户");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("推荐时间");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("岗位");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("工作年限");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(6);
            cell6.setCellValue("工作进展");
            cell6.setCellStyle(headerStyle);
            //6.装数据
            for (int i = 0; i < interviews.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                Interview interview = interviews.get(i);
                row.createCell(0).setCellValue(interview.getId()==null?0:interview.getId());
                row.createCell(1).setCellValue(interview.getUserName()==null?"":interview.getUserName());
                row.createCell(2).setCellValue(interview.getRecommendClient()==null?"":interview.getRecommendClient());
                HSSFCell workTime = row.createCell(3);
                workTime.setCellValue(interview.getRecommendTime());
                workTime.setCellStyle(dateCellStyle);

                row.createCell(4).setCellValue(interview.getJob()==null?"":interview.getJob());
                row.createCell(5).setCellValue(interview.getWorkAge()==null?0:interview.getWorkAge());
                row.createCell(6).setCellValue(interview.getProgress()==null?"":interview.getProgress());
            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String("候选人信息.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

//    public static ResponseEntity<byte[]> exportWord(Employee emp) {
//        HttpHeaders headers = null;
//        ByteArrayOutputStream baos = null;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        InputStream in = null;
//        XWPFDocument document = null;
//
//        try {
//            in = new FileInputStream(new File(System.getProperty("user.dir")+"/hrserver/src/main/resources/static/moban.docx"));
//            document = new XWPFDocument(in);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        // 读取文本内容
//        Range bodyRange = document.getRange();
//        System.out.println(bodyRange.toString());
//        System.out.println(bodyRange.text());
//        // 替换内容

//        //导出到文件
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            document.write((OutputStream)byteArrayOutputStream);
//            OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir")+"/hrserver/src/main/resources/static/wendang.docx");
//            outputStream.write(byteArrayOutputStream.toByteArray());
//
//            headers = new HttpHeaders();
//            headers.setContentDispositionFormData("attachment",
//                    new String("wendang.docx".getBytes("UTF-8"), "iso-8859-1"));
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            outputStream.close();
//            return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.CREATED);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static List<Employee> importEmp2List(MultipartFile file,
                                                List<Nation> allNations,
                                                List<PoliticsStatus> allPolitics,
                                                List<Department> allDeps,
                                                List<Position> allPos,
                                                List<JobLevel> allJobLevels) {
        List<Employee> emps = new ArrayList<>();
        try {
        HSSFWorkbook workbook =
                new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
        HSSFSheet sheet = workbook.getSheetAt(i);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        Employee employee;
        for (int j = 0; j < physicalNumberOfRows; j++) {
        if (j == 0) {
            continue;//标题行
        }
        HSSFRow row = sheet.getRow(j);
        if (row == null) {
            continue;//没数据
        }
        int physicalNumberOfCells = row.getPhysicalNumberOfCells();
        employee = new Employee();
        for (int k = 0; k < physicalNumberOfCells; k++) {
        HSSFCell cell = row.getCell(k);
        switch (cell.getCellTypeEnum()) {
        case STRING: {
            String cellValue = cell.getStringCellValue();
            if (cellValue == null) {
                cellValue = "";
            }
            switch (k) {
                case 1:
                    employee.setName(cellValue);
                    break;
                case 3:
                    employee.setGender(cellValue);
                    break;
                case 6:
                    employee.setWedlock(cellValue);
                    break;

                case 10:
                    employee.setPhone(cellValue);
                    break;
                case 16:
                    employee.setTiptopDegree(cellValue);
                    break;

                case 18:
                    employee.setSchool(cellValue);
                    break;
                case 19:
            }
        }
        break;
        default: {
            switch (k) {

            }
        }
            break;
        }
        }
            emps.add(employee);
        }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emps;
    }



    public static List<TalentPool> importTalent2List(MultipartFile file,List<Hr> hrList ) {
        List<TalentPool> talentList = new ArrayList<>();
        Map<String , Long> hrMap = new HashMap<>();

        if(hrList != null && hrList.size() > 0){
            for(Hr hr : hrList){
                hrMap.put(hr.getName() , hr.getId());
            }
        }
        InputStream is = null;
        Workbook workbook = null;
        try {
            is = file.getInputStream();
            if(file.getOriginalFilename().toLowerCase().endsWith(".xls")){
                workbook = new HSSFWorkbook(is);
            }else{
                workbook = new XSSFWorkbook(is);
            }
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    Row row = sheet.getRow(j);
                    if (row == null) {
                        continue;//没数据
                    }

                    TalentPool talentPool = new TalentPool();
                    String value = "";

                    Cell cell = row.getCell(1);
                    value = cell == null ? null : cell.toString().trim();
                    if(value == null || "".equals(value)){
                        value = DateTimeUtil.timeMillinToDateStr(DateTimeUtil.FORMATTIME_DATE_2);
                    }
//                    talentPool.setAddDate(value);

                    cell = row.getCell(2);
                    value = cell == null ? null : cell.toString().trim();
                    talentPool.setName(value);

                    talentList.add(talentPool);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return talentList;
    }


    public static byte[] getBytes(HSSFWorkbook wb) {
        if (null == wb) {
            throw new NullPointerException();
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            return os.toByteArray();
        } catch (Exception e) {
            return new byte[] {};
        }
    }

    public static byte[] exportTalent2Excel(String sheetName, String[] head,
                                       String[] alias, List mapData, boolean isIndex){
        HSSFWorkbook wb = new HSSFWorkbook();
        String[][] data = convertByObj(alias, mapData);
        getWorkBook(wb, sheetName, 0, head, data, isIndex);
        byte[] bytes = getBytes(wb);
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }



    public static HSSFWorkbook getWorkBook(HSSFWorkbook wb, String sheetName,
                                           int sheetIndex, String[] head, String[][] data, boolean isIndexes) {
        if (null == wb) {
            throw new NullPointerException();
        }

        int maxCell = isIndexes ? head.length + 1 : head.length;

        int begin = isIndexes ? 1 : 0;

        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(15);
        //HSSFRow row = sheet.createRow(sheetIndex);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFCell cell = null;

        if(begin != 0){
            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue("序号");
        }

        for (int i = 0; i < head.length; i++) {
            cell = row.createCell(i + begin);
            cell.setCellStyle(style);
            cell.setCellValue(head[i]);
        }

        for (int i = 0; i < data.length; i++) {
            row = sheet.createRow(i + 1);

            if (isIndexes) {
                row.createCell(0).setCellValue(i + 1);
            }

            for (int index = 0; index < data[i].length && index < maxCell; index++) {
                if (data[i][index] != null) {
                    row.createCell(index + begin).setCellValue(
                            data[i][index].replace("&nbsp;", ""));
                }
            }
        }

        return wb;
    }


    public static String[][] convertByObj(String[] alias,
                                          List mapData) {
        if (null == alias || alias.length < 1 || null == mapData
                || mapData.isEmpty()) {
            return new String[][] {};
        }

        String[][] result = new String[mapData.size()][alias.length];

        int row = 0;
        for (Object obj : mapData) {

            for (int i = 0; i < alias.length; i++) {

                //Field f;
                Method methods;
                try {
                    //f = obj.getClass().getDeclaredField(alias[i]);
                    methods = obj.getClass().getMethod("get"+alias[i]);
                    result[row][i] = methods.invoke(obj) == null ? "" : methods.invoke(obj).toString();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            row++;
        }

        return result;
    }
}
