package org.sang.common;

import org.springframework.util.StringUtils;

import java.io.File;

public class CommonUtis {
    public static final String FAIL = "1";

    public static final String SUCCESS = "0";

    public static final int FAIL_500 = 500;

    public static final int SUCCESS_200 = 200;

    public static final String MAN = "男";

    public static final String WOMAN = "女";

    public static final String UNMARRIED = "未婚";

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
