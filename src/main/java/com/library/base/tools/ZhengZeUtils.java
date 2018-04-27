package com.library.base.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static boolean notMobile(String str){
        return !isMobile(str);
    }
    public static boolean isMobile(String str){
        String reg = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9])\\d{8}$";
        return str.matches(reg.trim());
    }
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean notEmail(String email) {
        return !isEmail(email);
    }
    /*public static boolean isMobile(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }*/
}
