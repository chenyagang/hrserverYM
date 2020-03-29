package org.sang.common;

import org.sang.bean.Employee;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtis {
    public static final String FAIL = "1";

    public static final String SUCCESS = "0";

    public static final int FAIL_500 = 500;

    public static final int SUCCESS_200 = 200;

    public static final String MAN = "男";

    public static final String WOMAN = "女";

    public static final String UNMARRIED = "未婚";

    public static final String REGEX_PHONE = "1[34578][0-9]{9}"; //匹配手机号码

    private static String matchesPath = "[A-Za-z]:\\\\[^:?\"><*]*"; // 验证字符串是否为正确路径名的正则表达式

    private static String name = "姓名";
    private static String name_space = "姓 名";
    private static String post = "职位";
    private static String post_space = "职 位";
    private static String yingbingzhiwei = "应聘职位";
    private static String phone_space = "电 话";
    private static String phone = "电话";
    private static String gangweimiaoshu = "岗位描述";
    private static String gangweiyaoqiu = "岗位要求";
    private static String gerenjianjie = "个人简介";
    private static String school = "学校";
    private static String school_space = "学 校";
    private static String education = "学历";
    private static String dazhuan = "大专";
    private static String benke = "本科";
    private static String boshi = "博士";
    private static String shuoshi = "硕士";
    private static String gaozhong = "高中";
    private static String jybj = "教育背景";
    private static String education_space = "学 历";
    private static String gender = "性别";
    private static String gender_space = "性别";
    private static String graduationtime = "毕业时间";
    private static String workingplace = "工作地点";
    private static String weizhi = "位置";
    private static String communicationcontent = "沟通内容";
    private static String wedlock = "婚姻状况";
    private static String yihun = "已婚";
    private static String weihun = "未婚";
    private static String liyi = "离异";


    public static String getMatchStr(String str,String regex){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        //2、获取匹配器
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if(m.find()){
            return str.substring(m.start(),m.end());
        }
//        int a = m.start();
//        int b = m.end();
        return "";

    }

    public static String getMatchGroup(String str,String regex){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        //2、获取匹配器
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);

        if(m.find()){
            return regex;
        }
//        int a = m.start();
//        int b = m.end();
        return "";

    }



    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFile(String sPath) {
       boolean flag = false;
       File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFileSpath(sPath);
            } else {  // 为目录时调用删除目录方法
//                return deleteDirectory(sPath);
            }
        }
        return flag;
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFileSpath(String sPath) {
        boolean  flag = false;
       File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param    // sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
//    public boolean deleteDirectory(String sPath) {
//        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
//        if (!sPath.endsWith(File.separator)) {
//            sPath = sPath + File.separator;
//        }
//        File dirFile = new File(sPath);
//        //如果dir对应的文件不存在，或者不是一个目录，则退出
//        if (!dirFile.exists() || !dirFile.isDirectory()) {
//            return false;
//        }
//        boolean flag = true;
//        //删除文件夹下的所有文件(包括子目录)
//        File[] files = dirFile.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            //删除子文件
//            if (files[i].isFile()) {
//                flag = deleteFile(files[i].getAbsolutePath());
//                if (!flag) break;
//            } //删除子目录
//            else {
//                flag = deleteDirectory(files[i].getAbsolutePath());
//                if (!flag) break;
//            }
//        }
//        if (!flag) return false;
//        //删除当前目录
//        if (dirFile.delete()) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public static Employee substring_index(String str) {
        Employee employee = new Employee();
        str = repaceWhiteSapce(str);
        int a = -1;
        a = str.indexOf(name_space);
        if (a != -1) {
            a += 4;
            employee.setName(getKeyword(a, str, 4));
        }

        if(StringUtils.isEmpty(employee.getName())){
            a = str.indexOf(name);
            if (a != -1) {
                a += 3;
                employee.setName(getKeyword(a, str, 3));
                if(StringUtils.isEmpty(employee.getName())){
                    employee.setName(str.substring(0,4).trim());//设置为第一行前3个字符串
                }
            }else{
                if(StringUtils.isEmpty(employee.getName())){
                    employee.setName(str.substring(0,4).trim());//设置为第一行前3个字符串
                }
            }
        }

//        a = str.indexOf(education_space);
//        if (a != -1) {
//            a += 4;
//            employee.setTiptopDegree(getKeyword(a, str, 4));
//            if(!StringUtils.isEmpty(employee.getTiptopDegree())){
//                a = -1;
//            }
//        }
//
//        if(StringUtils.isEmpty(employee.getTiptopDegree())){
//            a = str.indexOf(education);
//            if (a != -1) {
//                a += 3;
//                employee.setTiptopDegree(getKeyword(a, str, 3));
//                if(StringUtils.isEmpty(employee.getTiptopDegree())){
//                    //正则匹配学历 本科
//                    employee.setTiptopDegree(CommonUtis.getMatchGroup(str, benke));
//                    if(StringUtils.isEmpty(employee.getTiptopDegree())){
//                        employee.setTiptopDegree(CommonUtis.getMatchGroup(str, dazhuan)); //正则匹配学历 大专
//                    }
//                }
//            }else{//正则匹配学历
//                employee.setTiptopDegree(CommonUtis.getMatchGroup(str, benke));
//                if(StringUtils.isEmpty(employee.getTiptopDegree())){
//                    employee.setTiptopDegree(CommonUtis.getMatchGroup(str, dazhuan));
//                }
//            }
//        }

        //正则匹配学历 本科
        if(StringUtils.isEmpty(employee.getTiptopDegree())){
            employee.setTiptopDegree(CommonUtis.getMatchGroup(str, boshi));
        }
        if(StringUtils.isEmpty(employee.getTiptopDegree())){
            employee.setTiptopDegree(CommonUtis.getMatchGroup(str, shuoshi));
        }
        if(StringUtils.isEmpty(employee.getTiptopDegree())){
            employee.setTiptopDegree(CommonUtis.getMatchGroup(str, benke));
        }
        if(StringUtils.isEmpty(employee.getTiptopDegree())){
            employee.setTiptopDegree(CommonUtis.getMatchGroup(str, dazhuan));
        }
        if(StringUtils.isEmpty(employee.getTiptopDegree())){
            employee.setTiptopDegree(CommonUtis.getMatchGroup(str, gaozhong));
        }

        a = str.indexOf(phone_space);
        if (a != -1) {
            a += 4;
            employee.setPhone(getKeyword(a, str, 4));

        }

        if(StringUtils.isEmpty(employee.getPhone())){
            a = str.indexOf(phone);
            if (a != -1) {
                a += 3;
                employee.setPhone(getKeyword(a, str, 3));
                if(StringUtils.isEmpty(employee.getPhone())){
                    employee.setPhone(CommonUtis.getMatchStr(str, CommonUtis.REGEX_PHONE));//正则匹配手机号码
                }
            }else{
                employee.setPhone(CommonUtis.getMatchStr(str, CommonUtis.REGEX_PHONE));
            }
        }
        a = str.indexOf(post_space);
        if (a != -1) {
            a += 4;
            employee.setJob(getKeyword(a, str, 4));
        }

        if(StringUtils.isEmpty(employee.getJob())){
            a = str.indexOf(post);
            if (a != -1) {
                a += 3;
                employee.setJob(getKeyword(a, str, 3));
            }
        }

        if(StringUtils.isEmpty(employee.getJob())){
            a = str.indexOf(yingbingzhiwei);
            if (a != -1) {
                a += 5;
                employee.setJob(getKeyword(a, str, 5));
            }
        }
        a = str.indexOf(gangweimiaoshu);
        if (a != -1) {
            a += 5;
            employee.setJobDescription(getKeyword(a, str, 5));
        }
        if(StringUtils.isEmpty(employee.getJobDescription())){
            a = str.indexOf(gangweiyaoqiu);
            if (a != -1) {
                a += 5;
                employee.setJobDescription(getKeyword(a, str, 5));
            }
        }

        if(StringUtils.isEmpty(employee.getJobDescription())){
            a = str.indexOf(gerenjianjie);
            if (a != -1) {
                a += 5;
                employee.setJobDescription(getKeyword(a, str, 5));
            }
        }

        a = str.indexOf(school_space);
        if (a != -1) {
            a += 4;
            employee.setSchool(getKeyword(a, str, 4));
        }

        if(StringUtils.isEmpty(employee.getSchool())){
            a = str.indexOf(school);
            if (a != -1) {
                a += 3;
                employee.setSchool(getKeyword(a, str, 3));
            }
        }
        if(StringUtils.isEmpty(employee.getSchool())){
            a = str.indexOf(jybj);
            if (a != -1) {
                a += 5;
                employee.setSchool(getKeyword(a, str, 5));
            }
        }

        a = str.indexOf(gender_space);
        if (a != -1) {
            a += 4;
            employee.setGender(getKeyword(a, str, 4));
        }

        if(StringUtils.isEmpty(employee.getSchool())){
            a = str.indexOf(gender);
            if (a != -1) {
                a += 3;
                employee.setGender(getKeyword(a, str, 3));
            }
        }

        a = str.indexOf(graduationtime);
        if (a != -1) {
            a += 5;
            employee.setGraduationTime(getKeyword(a, str, 4));
        }

        a = str.indexOf(workingplace);
        if (a != -1) {
            a += 5;
            employee.setWorkplace(getKeyword(a, str, 5));
        }

        if(StringUtils.isEmpty(employee.getWorkplace())){
            a = str.indexOf(weizhi);
            if (a != -1) {
                a += 3;
                employee.setWorkplace(getKeyword(a, str, 3));
            }
        }

//        a = str.indexOf(wedlock);
//        if (a != -1) {
//            a += 5;
//            employee.setWedlock(getKeyword(a, str, 5));
//        }
        if(StringUtils.isEmpty(employee.getWedlock())){
            employee.setWedlock(CommonUtis.getMatchGroup(str, yihun));
        }
        if(StringUtils.isEmpty(employee.getWedlock())){
            employee.setWedlock(CommonUtis.getMatchGroup(str, weihun));
        }

        if(StringUtils.isEmpty(employee.getWedlock())){
            employee.setWedlock(CommonUtis.getMatchGroup(str, liyi));
        }
        a = str.indexOf(communicationcontent);
        if (a != -1) {
            a += 5;
            employee.setCommunicationContent(getKeyword(a, str, 5));
        }
        return employee;
    }


    public static String getKeyword(int a, String str, int index) {
        String temp = "";
        if (a != -1 && !StringUtils.isEmpty(str)) {
            temp = str.substring(a - index, a);//预期截取关键字+：
            str = str.substring(a);//截取需要值
//            str = str.replaceAll("\r|\n", " ");//去除r n换成空格
            if (temp.indexOf(":") != -1 || temp.indexOf("：") != -1) {
                return getProcessingField(str);
            }
            for (int i = 0; i < str.length(); i++) {
                char[] stringArr = str.toCharArray();
                if (stringArr[i] == '\0' || Character.isSpace(stringArr[i]) || stringArr[i] == ':' || stringArr[i] == '：' || stringArr[i] == '\n') {
                    str = str.substring(i + 1);
                    str = str.replaceAll("\r|\n", " ");//去除r n换成空格
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

}
