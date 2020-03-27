package org.sang.common.poi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sang.bean.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PoiParseXLS {

    public final static String XLSX = ".xlsx";
    public final static String XLS=".xls";


//    public static void main(String[] args)  throws Exception{
//
//        String str = setFormat("[{\"编号\":\"1.0\",\"姓名\":\"江南一点雨\",\"工号\":\"00000001\",\"性别\":\"男\",\"出生日期\":\"1990-01-01 00:00:00\",\"身份证号码\":\"610122199001011256\",\"婚姻状况\":\"已婚\",\"民族\":\"汉族\",\"籍贯\":\"陕西\",\"政治面貌\":\"普通公民\",\"电话号码\":\"18565558897\",\"联系地址\":\"深圳市南山区\",\"所属部门\":\"总办\",\"职称\":\"教授\",\"职位\":\"技术总监\",\"聘用形式\":\"劳务合同\",\"最高学历\":\"本科\",\"专业\":\"信息管理与信息系统\",\"毕业院校\":\"深圳大学\",\"入职日期\":\"2018-01-01 00:00:00\",\"在职状态\":\"在职\",\"邮箱\":\"laowang@qq.com\",\"合同期限(年)\":\"2.0\",\"合同起始日期\":\"2018-01-01 00:00:00\",\"合同终止日期\":\"2020-01-01 00:00:00\"},{\"编号\":\"2.0\",\"姓名\":\"\",\"工号\":\"\",\"性别\":\"\",\"出生日期\":\"\",\"身份证号码\":\"\",\"婚姻状况\":\"\",\"民族\":\"\",\"籍贯\":\"\",\"政治面貌\":\"\",\"电话号码\":\"\",\"联系地址\":\"\",\"所属部门\":\"\",\"职称\":\"\",\"职位\":\"\",\"聘用形式\":\"\",\"最高学历\":\"\",\"专业\":\"\",\"毕业院校\":\"\",\"入职日期\":\"\",\"在职状态\":\"\",\"邮箱\":\"\",\"合同期限(年)\":\"\",\"合同起始日期\":\"\",\"合同终止日期\":\"\"},{\"编号\":\"3.0\",\"姓名\":\"\",\"工号\":\"\",\"性别\":\"\",\"出生日期\":\"\",\"身份证号码\":\"\",\"婚姻状况\":\"\",\"民族\":\"\",\"籍贯\":\"\",\"政治面貌\":\"\",\"电话号码\":\"\",\"联系地址\":\"\",\"所属部门\":\"\",\"职称\":\"\",\"职位\":\"\",\"聘用形式\":\"\",\"最高学历\":\"\",\"专业\":\"\",\"毕业院校\":\"\",\"入职日期\":\"\",\"在职状态\":\"\",\"邮箱\":\"\",\"合同期限(年)\":\"\",\"合同起始日期\":\"\",\"合同终止日期\":\"\"}]\n");
//
//        System.out.println(str);
//    }

    public static String setFormat(String str){
        String strFM ="";
        strFM = str.replaceAll("\\[","").replace("{","")
                .replace("\"","").replace(","," ");
//        strFM = strFM.replace("{","");
//        strFM = strFM.replace("\"","");
//        strFM = strFM.replace(","," ");
        return strFM;
    }

    /**
     * 获取Excel文件（.xls和.xlsx都支持）
     * @param  file
     * @return  解析excle后的Json数据
     * @throws
     * @throws
     * @throws
     */
    public static String readExcel(File file) throws Exception{
//        File file = null;
//        file = PoiParseWord.getFile(Mfiles);
        int res = checkFile(file);
        if (res == 0) {
            System.out.println("File not found");
        }else if (res == 1) {
           String xlsx = readXLSX(file).toString();
            return setFormat(xlsx);
//            return readXLSX(file);
        }else if (res == 2) {
            String xls = readXLSX(file).toString();
            return setFormat(xls);
//            return readXLS(file);
        }
        JSONArray array = new JSONArray();
        return "";
    }

    /**
     * 判断File文件的类型
     * @param file 传入的文件
     * @return 0-文件为空，1-XLSX文件，2-XLS文件，3-其他文件
     */
    public static int checkFile(File file){
        if (file==null) {
            return 0;
        }
        String flieName = file.getName();
        if (flieName.endsWith(XLSX)) {
            return 1;
        }
        if (flieName.endsWith(XLS)) {
            return 2;
        }
        return 3;
    }

    /**
     * 读取XLSX文件
     * @param file
     * @return
     * @throws
     * @throws
     */
    public static JSONArray readXLSX(File file) throws InvalidFormatException, IOException{
        Workbook book = new XSSFWorkbook(file);
        Sheet sheet = book.getSheetAt(0);
        return read(sheet, book);
    }

    /**
     * 读取XLS文件
     * @param file
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static JSONArray readXLS(File file) throws FileNotFoundException, IOException{
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
        Workbook book = new HSSFWorkbook(poifsFileSystem);
        Sheet sheet = book.getSheetAt(0);
        return read(sheet, book);
    }

    /**
     * 解析数据
     * @param sheet 表格sheet对象
     * @param book 用于流关闭
     * @return
     * @throws
     */
    public static JSONArray read(Sheet sheet, Workbook book) throws IOException{
        int rowStart = sheet.getFirstRowNum();	// 首行下标
        int rowEnd = sheet.getLastRowNum();	// 尾行下标
        // 如果首行与尾行相同，表明只有一行，直接返回空数组
        if (rowStart == rowEnd) {
            book.close();
            return new JSONArray();
        }
        // 获取第一行JSON对象键
        Row firstRow = sheet.getRow(rowStart);
        int cellStart = firstRow.getFirstCellNum();
        int cellEnd = firstRow.getLastCellNum();
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        for (int j = cellStart; j < cellEnd; j++) {
            keyMap.put(j,getValue(firstRow.getCell(j), rowStart, j, book, true));
        }
        // 获取每行JSON对象的值
        JSONArray array = new JSONArray();
        for(int i = rowStart+1; i <= rowEnd ; i++) {
            Row eachRow = sheet.getRow(i);
            JSONObject obj = new JSONObject();
            StringBuffer sb = new StringBuffer();
            for (int k = cellStart; k < cellEnd; k++) {
                if (eachRow != null) {
                    String val = getValue(eachRow.getCell(k), i, k, book, false);
                    sb.append(val);		// 所有数据添加到里面，用于判断该行是否为空
                    obj.put(keyMap.get(k),val);
                }
            }
            if (sb.toString().length() > 0) {
                array.add(obj);
            }
        }
        book.close();
        return array;
    }

    /**
     * 获取每个单元格的数据
     * @param cell 单元格对象
     * @param rowNum 第几行
     * @param index 该行第几个
     * @param book 主要用于关闭流
     * @param isKey 是否为键：true-是，false-不是。 如果解析Json键，值为空时报错；如果不是Json键，值为空不报错
     * @return
     * @throws
     */
    public static String getValue(Cell cell, int rowNum, int index, Workbook book, boolean isKey) throws IOException {

        // 空白或空
        if (cell == null || cell.getCellType()==Cell.CELL_TYPE_BLANK ) {
            if (isKey) {
                book.close();
                throw new NullPointerException(String.format("the key on row %s index %s is null ", ++rowNum,++index));
            }else{
                return "";
            }
        }

        // 0. 数字 类型
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.format(date);
            }
            String val = cell.getNumericCellValue()+"";
            val = val.toUpperCase();
            if (val.contains("E")) {
                val = val.split("E")[0].replace(".", "");
            }
            return val;
        }

        // 1. String类型
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String val = cell.getStringCellValue();
            if (val == null || val.trim().length()==0) {
                if (book != null) {
                    book.close();
                }
                return "";
            }
            return val.trim();
        }

        // 2. 公式 CELL_TYPE_FORMULA
        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getStringCellValue();
        }

        // 4. 布尔值 CELL_TYPE_BOOLEAN
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue()+"";
        }

        // 5.	错误 CELL_TYPE_ERROR
        return "";
    }


}