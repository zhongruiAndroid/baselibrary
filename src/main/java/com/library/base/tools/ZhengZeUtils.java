package com.library.base.tools;

/**
 * Created by Administrator on 2018/1/5.
 */

public class ZhengZeUtils {
    public static boolean isShuZiAndZiMu(String str){
        String reg = "(?i)^(?!([a-z]*|\\d*)$)[a-z\\d]+$";
        return str.matches(reg.trim());
    }
    public static boolean isShuZiOrZiMu(String str){
        String reg = "^[A-Za-z0-9]+$";
        return str.matches(reg.trim());
    }
    public static boolean isMobile(String str){
        String reg = "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";
        return str.matches(reg.trim());
    }
    /*public static boolean isMobile(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }*/
}
