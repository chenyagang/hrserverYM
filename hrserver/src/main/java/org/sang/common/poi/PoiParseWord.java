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

    /**
     *
     */
    private OPCPackage oPCPackage;
    private XWPFDocument xwpfDocument;

    private FileInputStream fileInputStream;
    private POIFSFileSystem poifsFileSystem;
    private HWPFDocument hwpfDocument;

    public static Employee readPDF(File pdfFile) {
//        File pdfFile = null;
//        pdfFile = getFile(Mfiles);
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
            String content="";
            for (int page = 1; page <= pages; page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);
                content += stripper.getText(document);
//               String a = retriveText(content,xingming,1);
//                return employee = substring_index(content, employee);
            }
            return CommonUtis.substring_index(content);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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
    public static Employee readWord(File file) {
        String str = "";
//        File file = null;
//        file = getFile(Mfiles);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String doc1 = extractor.getText();
            return CommonUtis.substring_index(doc1);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 解析TXT 文件
     *
     * @throws IOException
     */
    public static Employee readTXT(File file) {
//        File file = null;
//        file = getFile(Mfiles);
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
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
//        System.out.println("txt内容为==> " + txt);
        return CommonUtis.substring_index(txt);
    }


//    public static boolean isIncludeKeywords(String str) {
//        for (int i = 0; i < keywordList.length; i++) {
//            if (str.contains(keywordList[i])) {
//                return true;
//            }
//        }
//        return false;
//    }





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
