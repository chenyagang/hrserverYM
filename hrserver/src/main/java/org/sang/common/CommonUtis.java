package org.sang.common;

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


//    String regex = "1[34578][0-9]{9}";//匹配手机号码
//    String x ="(([\\u4E00-\\u9FA5]{2,7})|([a-zA-Z]{3,10}))";
//    //2、获取匹配器
//    Pattern p = Pattern.compile(x);
//    Matcher m = p.matcher(s);
//
//    //3、使用匹配器的group()方法来获取:（find方法是判断是否具有匹配子串）、
//        System.out.println("”" + s + "符合要求的有：");
////        System.out.println(s.substring(m.start(0),m.end(0)));
//        while (m.find()) {
//        System.out.println(m.group());
//        System.out.println("====="+s.substring(m.start(0),m.end(0)));
//    }


    public static String getMatchStr(String str,String regex){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        //2、获取匹配器
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        m.find();
//        int a = m.start();
//        int b = m.end();
        return str.substring(m.start(),m.end());

    }

    public static String getMatchGroup(String str,String regex){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        //2、获取匹配器
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        m.find();
//        int a = m.start();
//        int b = m.end();
        return m.group().trim();

    }

    public static boolean deleteFile(String filePath){

        boolean delete_flag = false;
        if(StringUtils.isEmpty(filePath)){
            return delete_flag;
        }
        File file = new File(filePath);
        if (file.exists() && file.isFile() && file.delete())
            delete_flag = true;
        else
            delete_flag = false;
        file.delete();
        return delete_flag;
    }

}
