package org.sang.utils;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class WordDocx {
    /**
     *      * 实现对word读取和修改操作
     *      * @param inFile word模板路径和名称
     *      * @param data 填充数据
     *      * @throws InvalidFormatException
     *      * @throws IOException
     *     
     */
    public static  ResponseEntity<byte[]> readwriteWord(String inFile, Map<String, Object> data) throws InvalidFormatException, IOException {


        InputStream is = new FileInputStream(inFile);
        XWPFDocument document;
        try {
            document = new XWPFDocument(OPCPackage.open(is));
            // 替换段落里面的变量
            replaceInPara(document, data);
            // 替换表格里面的变量
            replaceInTable(document, data);
            String fileName = "" + System.currentTimeMillis();
            fileName += ".docx";
            //            OutputStream os = new FileOutputStream("D:\\test\\" + fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.write(baos);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String("wendang.docx".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            close(baos);
            close(is);
            return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     *      * 替换段落里面的变量
     *      * @param doc 要替换的文档   
     *      * @param params 参数
     *     
     */

    private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }


    /**
     *      * 替换段落里面的变量
     *      * @param para 要替换的段落
     *      * @param params 参数
     *     
     */

    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        String runText = "";
        int fsize = 0;
        String fontFamily = "";
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            if (runs.size() > 0) {
                int j = runs.size();
                for (int i = 0; i < j; i++) {
                    XWPFRun run = runs.get(0);
                    fsize = run.getFontSize();//获取句中字的大小
                    fontFamily = run.getFontFamily();//获取字体样式
                    String i1 = run.toString();
                    runText += i1;
                    para.removeRun(0);

                }

            }
            matcher = matcher(runText);

            if (matcher.find()) {
                while ((matcher = matcher(runText)).find()) {
                    runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                }
                XWPFRun nrun = para.insertNewRun(0);
                nrun.setText(runText);
                if (fsize > 0) {
                    nrun.setFontSize(fsize);
                }
                if (fontFamily != "") {
                    nrun.setFontFamily(fontFamily);
                }
            }
        }

    }


    /**
     *  
     *      * 替换表格里面的变量 
     *      * @param doc 要替换的文档 
     *      * @param params 参数 
     *      
     */

    private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     *      * 正则匹配字符串
     *      *
     *      * @param str
     *      * @return
     *     
     */

    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     *      * 关闭输入流
     *      *
     *      * @param is
     *     
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *      * 关闭输出流
     *      *
     *      * @param os
     *     
     */
    private static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void close(ByteArrayOutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
