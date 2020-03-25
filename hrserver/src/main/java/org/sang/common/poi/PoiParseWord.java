package org.sang.common.poi;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.sang.bean.Employee;
import org.sang.common.CommonUtis;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoiParseWord {

    /**
     * Word2003版本后缀名
     */
    public final static String DOC = "doc";

    /**
     * Word2007以上版本后缀名
     */
    public final static String DOCX = "docx";

    public final static String PDF = "pdf";

    public final static String XLSX = "xlsx";

    public final static String XLS = "xls";

    public final static String TXT = "txt";

    /**
     * 系统临时文件路径
     */
    private final static String SYSTEM_TEMPORARY_PATH = System.getProperty("java.io.tmpdir");

    private final static String name = "姓名";
    private final static String name_space = "姓 名";
    private final static String post = "职位";
    private final static String post_space = "职 位";
    private final static String phone_space = "电 话";
    private final static String phone = "电话";
    private final static String gangweimiaoshu = "岗位描述";
    private final static String gangweiyaoqiu = "岗位要求";
    private final static String school = "学校";
    private final static String school_space = "学 校";
    private final static String education = "学历";
    private final static String education_space = "学 历";
    private final static String gender = "性别";
    private final static String gender_space = "性别";
    private final static String graduationtime = "毕业时间";
    private final static String workingplace = "工作地点";
    private final static String communicationcontent = "沟通内容";
    private final static String wedlock = "婚姻状况";

    private final static String keywordList[] = {"姓名", "姓 名", "职位", "职 位", "岗位", "岗 位", "学校",
            "学 校", "学历", "学 历", "毕业时间", "性别", "工作地点", "沟通内容"};

//    private final static String post_name = "岗位名称";
//    private final static String customer = "客户";
//    private final static String customer_space = "客 户";
//    private final static String post_description = "岗位描述";
//    private final static String demand_quantity = "需求数量";
//    private final static String working_place = "工作地点";
//    private final static String interviewer = "面试官";
    /**
     *
     */
    private OPCPackage oPCPackage;
    private XWPFDocument xwpfDocument;

    private FileInputStream fileInputStream;
    private POIFSFileSystem poifsFileSystem;
    private HWPFDocument hwpfDocument;

    public static Employee readPDF(MultipartFile Mfiles, Employee employee) {
        File pdfFile = null;
        pdfFile = getFile(Mfiles);
        PDDocument document = null;
        try {
            document = PDDocument.load(pdfFile);
            // 获取页码
            int pages = document.getNumberOfPages();
            // 读文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            /*stripper.setStartPage(1);
            stripper.setEndPage(pages);
            String content = stripper.getText(document);
            System.out.println(content);*/
            for (int page = 1; page <= pages; page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);
                String content = stripper.getText(document);
//               String a = retriveText(content,xingming,1);
                return employee = substring_index(content, employee);

//                parseContent(content, candidateList);
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
        return employee;
    }

    public static File getFile(MultipartFile Mfiles) {
        InputStream in = null;
        FileInputStream fin = null;
        File file = new File(Mfiles.getOriginalFilename());
        InputStream inputStream = null;
        try {
            inputStream = Mfiles.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStreamToFile(inputStream, file);

        return file;
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 解析word文档（*.doc）
//     * @throws IOException
//     */
//    public static void readAndWriterTest3() throws IOException {
//        File file = new File("C:\\Users\\tuzongxun123\\Desktop\\aa.doc");
//        String str = "";
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            HWPFDocument doc = new HWPFDocument(fis);
//            String doc1 = doc.getDocumentText();
//            System.out.println(doc1);
//            StringBuilder doc2 = doc.getText();
//            System.out.println(doc2);
//            Range rang = doc.getRange();
//            String doc3 = rang.text();
//            System.out.println(doc3);
//            fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 解析word文档（*.doc,*.docx）
     *
     * @throws IOException
     */
    public static Employee readWord(MultipartFile Mfiles, Employee employee) {
        String str = "";
        File file = null;
        file = getFile(Mfiles);
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String doc1 = extractor.getText();
            employee = substring_index(doc1, employee);
            System.out.println(str);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * 解析TXT 文件
     *
     * @throws IOException
     */
    public static Employee readTXT(MultipartFile Mfiles, Employee employee) {
        File file = null;
        file = getFile(Mfiles);
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
//            FileInputStream fis =
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String txt = new String(sb); //StringBuffer ==> String
        System.out.println("txt内容为==> " + txt);
        employee = substring_index(txt, employee);
        return employee;
    }


    public static Employee substring_index(String str, Employee employee) {
        str = repaceWhiteSapce(str);
        if (isIncludeKeywords(str)) {
            int a = -1;
            a = str.indexOf(name_space);
            if (a != -1) {
                a += 4;
                employee.setName(getKeyword(a, str, 4));
            }
            a = str.indexOf(name);
            if (a != -1) {
                a += 3;
                employee.setName(getKeyword(a, str, 3));
            }

            a = str.indexOf(education_space);
            if (a != -1) {
                a += 4;
                employee.setEducation(getKeyword(a, str, 4));
            }

            a = str.indexOf(education);
            if (a != -1) {
                a += 3;
                employee.setEducation(getKeyword(a, str, 3));
            }

            a = str.indexOf(phone_space);
            if (a != -1) {
                a += 4;
                employee.setPhone(getKeyword(a, str, 4));
            }
            a = str.indexOf(phone);
            if (a != -1) {
                a += 3;
                employee.setPhone(getKeyword(a, str, 3));
            }

            if (a != -1) {//正则匹配手机号码
                CommonUtis.getMatchStr(str, CommonUtis.REGEX_PHONE);
            }

            a = str.indexOf(post_space);
            if (a != -1) {
                a += 4;
                employee.setPost(getKeyword(a, str, 4));
            }
            a = str.indexOf(post);
            if (a != -1) {
                a += 3;
                employee.setPost(getKeyword(a, str, 3));
            }

            a = str.indexOf(gangweimiaoshu);
            if (a != -1) {
                a += 4;
                employee.setJobDescription(getKeyword(a, str, 4));
            }
            a = str.indexOf(gangweiyaoqiu);
            if (a != -1) {
                a += 3;
                employee.setJobDescription(getKeyword(a, str, 3));
            }

            a = str.indexOf(school_space);
            if (a != -1) {
                a += 4;
                employee.setSchool(getKeyword(a, str, 4));
            }
            a = str.indexOf(school);
            if (a != -1) {
                a += 3;
                employee.setSchool(getKeyword(a, str, 3));
            }

            a = str.indexOf(gender_space);
            if (a != -1) {
                a += 4;
                employee.setGender(getKeyword(a, str, 4));
            }

            a = str.indexOf(gender);
            if (a != -1) {
                a += 3;
                employee.setGender(getKeyword(a, str, 3));
            }

            a = str.indexOf(graduationtime);
            if (a != -1) {
                a += 5;
                employee.setGraduationTime(getKeyword(a, str, 4));
            }

            a = str.indexOf(workingplace);
            if (a != -1) {
                a += 5;
                employee.setWorkingPlace(getKeyword(a, str, 5));
            }
            a = str.indexOf(wedlock);
            if (a != -1) {
                a += 5;
                employee.setWedlock(getKeyword(a, str, 5));
            }
            a = str.indexOf(communicationcontent);
            if (a != -1) {
                a += 5;
                employee.setCommunicationContent(getKeyword(a, str, 5));
            }
        }
        return employee;

    }

    public static boolean isIncludeKeywords(String str) {
        for (int i = 0; i < keywordList.length; i++) {
            if (str.contains(keywordList[i])) {
                return true;
            }
        }
        return false;
    }

    public static String getKeyword(int a, String str, int index) {
        String temp = "";
        if (a != -1 && !StringUtils.isEmpty(str)) {
            temp = str.substring(a - index, a);//预期截取关键字+：
            str = str.substring(a);//截取需要值
            if (temp.indexOf(":") != -1 || temp.indexOf("：") != -1) {
                return getProcessingField(str);
            }
            for (int i = 0; i < str.length(); i++) {
                char[] stringArr = str.toCharArray();
                if (stringArr[i] == '\0' || Character.isSpace(stringArr[i]) || stringArr[i] == ':' || stringArr[i] == '：' || stringArr[i] == '\n') {
                    str = str.substring(i + 1);
                    return getProcessingField(str);
                }
            }


        }
        return "";
    }

    public static String getProcessingField(String str) {
        str = trimLeft(str);
        char[] stringArr = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (stringArr[i] == '\0' || Character.isSpace(stringArr[i])) {

                str = str.substring(0, i + 1);

                System.out.println(str.replaceAll("\\s*", ""));
                return str.replaceAll("\\s*", "");
            }

        }
        return str;
    }

    /**
     * 去右空格
     *
     * @param str
     * @return
     */
    public static String trimRight(String str) {
        if (str == null || str.equals("")) {
            return str;
        } else {
            return str.replaceAll("[　 ]+$", "");
        }
    }

    /**
     * 去左空格
     *
     * @param str
     * @return
     */
    public static String trimLeft(String str) {
        if (str == null || str.equals("")) {
            return str;
        } else {
            return str.replaceAll("^[　 ]+", "");
        }
    }

    /**
     * 将字符串中连续的多个空格替换成一个空格
     *
     * @param original
     * @return
     */
    public static String repaceWhiteSapce(String original) {
        StringBuilder sb = new StringBuilder();
        boolean isFirstSpace = false;//标记是否是第一个空格
        original = original.trim();//如果考虑开头和结尾有空格的情形
        char c;
        for (int i = 0; i < original.length(); i++) {
            c = original.charAt(i);
            if (c == ' ' || c == '\t')//遇到空格字符时,先判断是不是第一个空格字符
            {
                if (!isFirstSpace) {
                    sb.append(c);
                    isFirstSpace = true;
                }
            } else {//遇到非空格字符时
                sb.append(c);
                isFirstSpace = false;
            }
        }
        return sb.toString();
    }


//    public static List<Candidate> importTalentPool(MultipartFile file) {
//
////        File toFile = null;
////        if (file.equals("") || file.getSize() <= 0) {
////            file = null;
////        } else {
////            InputStream ins = null;
////            ins = file.getInputStream();
////            toFile = new File(file.getOriginalFilename());
////            inputStreamToFile(ins, toFile);
////            ins.close(); 123.xml
////        }
//
////        String suffixName = file.getOriginalFilename().split("\\.")[1];
//        if(file.getOriginalFilename().lastIndexOf(".") == -1){
//            return null; //
//        }
//        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
//        if (DOCX.equals(suffixName) || DOC.equals(suffixName)) {
////            readAndWriterTest4(suffixName);
//
//        }
//        if (PDF.equals(suffixName)) {
//            readPDF(suffixName);
//        }
//        return null;
//    }


    /**
     * 未用到下面代码，暂时保留
     *
     * @param file
     */
    public PoiParseWord(File file) {
        String suffixName = file.getPath().split("\\.")[1];
        try {
            if (DOCX.equals(suffixName)) {
                oPCPackage = POIXMLDocument.openPackage(file.getPath());
                xwpfDocument = new XWPFDocument(oPCPackage);
            }
            if (DOC.equals(suffixName)) {
                fileInputStream = new FileInputStream(file);
                poifsFileSystem = new POIFSFileSystem(fileInputStream);
                hwpfDocument = new HWPFDocument(poifsFileSystem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取出word文档表格第cellRowIdx行，第cellColIdx列的值（DOCX）
     *
     * @param cellRowIdx 行
     * @param cellColIdx 列
     */
    @SuppressWarnings("unused")
    public String getSpecifyDataForDocx(int cellRowIdx, int cellColIdx) {
        try {
            // 获取页面中的表格
            Iterator<XWPFTable> it = xwpfDocument.getTablesIterator();
            while (it.hasNext()) {
                // 循环页面中的表格
                XWPFTable table = (XWPFTable) it.next();
                StringBuffer str = new StringBuffer();
                // 获取表格中的行
                XWPFTableRow row = table.getRow(cellRowIdx);
                // 获取行中共有多少列
                List<XWPFTableCell> cells = row.getTableCells();
                // 获取列
                XWPFTableCell cell = cells.get(cellColIdx);
                // 获取列中的段落
                StringBuffer allFilePath = new StringBuffer();
                for (int i = 0; i < cell.getParagraphs().size(); i++) {
                    List<XWPFRun> runs = cell.getParagraphs().get(i).getRuns();
                    for (int j = 0; j < runs.size(); j++) {
                        // 获取单个对象
                        XWPFRun r = runs.get(j);
                        String text = r.getText(r.getTextPosition());
                        // 如果字符为空，可能是附件一类的文件，比如图片之类的，需要另外解析,此处处理为图片
                        if (text == null) {
                            List<XWPFPicture> piclist = r.getEmbeddedPictures();
                            for (int k = 0; k < piclist.size(); k++) {
                                String filePath = SYSTEM_TEMPORARY_PATH + UUID.randomUUID() + ".jpg";
                                XWPFPicture pic = piclist.get(k);
                                byte[] picbyte = pic.getPictureData().getData();
                                // 将图片写入本地文件
                                @SuppressWarnings("resource")
                                FileOutputStream fos = new FileOutputStream(filePath);
                                fos.write(picbyte);
                                allFilePath.append(filePath);
                            }
                        } else {
                            allFilePath.append(text);
                        }
                    }
                }
                return allFilePath.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭
     */
    public void closeForDocx() {
        try {
            oPCPackage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取出word文档表格第cellRowIdx行，第cellColIdx列的值（DOC）
     *
     * @param cellRowIdx 行
     * @param cellColIdx 列
     */
    @SuppressWarnings({"resource", "unused"})
    public String getSpecifyDataForDoc(int cellRowIdx, int cellColIdx) {
        try {
            Range range = hwpfDocument.getRange();
            TableIterator it = new TableIterator(range);
            PicturesTable picturesTable = hwpfDocument.getPicturesTable();
            while (it.hasNext()) {

                Table tb = (Table) it.next();
                TableRow tr = tb.getRow(cellRowIdx);
                TableCell td = tr.getCell(cellColIdx);
                CharacterRun cr = td.getCharacterRun(0);
                if (picturesTable.hasPicture(cr)) {
                    Picture pic = picturesTable.extractPicture(cr, true);
                    byte[] picbyte = pic.getContent();
                    String filePath = SYSTEM_TEMPORARY_PATH + UUID.randomUUID() + ".jpg";
                    // 将图片写入本地文件
                    FileOutputStream fos = new FileOutputStream(filePath);
                    fos.write(picbyte);
                    return filePath;
                } else {
                    for (int k = 0; k < td.numParagraphs(); k++) {
                        Paragraph para = td.getParagraph(k);
                        String s = para.text();
                        s = s.substring(0, s.length() - 1);
                        return s;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
