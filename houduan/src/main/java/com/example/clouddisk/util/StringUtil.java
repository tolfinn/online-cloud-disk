package com.example.clouddisk.util;

public class StringUtil {

    /**
     * 非空校验
     * @param str
     * @return
     */
    public static boolean isNull(String str){
        if(str==null){
            return true;
        }
        str = str.trim();//去除空格 防止用户输入空格
        if("".equals(str)){
            return true;
        }
        return false;
    }
}
