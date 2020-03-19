package org.sang.common;

import java.io.File;

public class commonUtis {
    public static final String FAIL = "1";

    public static final String SUCCESS = "0";

    public static final int FAIL_1 = 1;

    public static final int SUCCESS_0 = 0;


    public static boolean deleteFile(String filePath){
        boolean delete_flag = false;
        File file = new File(filePath);
        if (file.exists() && file.isFile() && file.delete())
            delete_flag = true;
        else
            delete_flag = false;
        file.delete();
        return delete_flag;
    }

}
