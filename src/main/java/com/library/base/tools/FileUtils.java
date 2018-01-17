package com.library.base.tools;


import com.library.base.tools.has.AndroidUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/1/9.
 */

public class FileUtils {
    public static String formetFileSize(Double fileS) {
        return formetFileSize(fileS,true,2);
    }
    public static String formetFileSize(Double fileS,boolean big,int length){
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString;
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + getFileSizeDanWei("B",big,length);
        } else if (fileS < 1048576) {
            fileSizeString = df.format(AndroidUtils.chuFa(fileS, 1024)) + getFileSizeDanWei("KB",big,length);
        } else if (fileS < 1073741824) {
            fileSizeString = df.format(AndroidUtils.chuFa(fileS, 1048576)) + getFileSizeDanWei("MB",big,length);
        } else {
            fileSizeString = df.format(AndroidUtils.chuFa(fileS, 1073741824)) + getFileSizeDanWei("GB",big,length);
        }
        return fileSizeString;
    }
    private static String getFileSizeDanWei(String danWei,boolean big,int length){
          if(length==1){
              danWei=danWei.substring(0,1);
          }else{
              danWei=danWei.substring(0,2);
          }
          return big?danWei.toUpperCase():danWei.toLowerCase();
    }

//    public static final String fileSavePath= Environment.getExternalStorageDirectory().getPath() + "/yangyu/save/";
//    public static final String filePath= Environment.getExternalStorageDirectory().getPath() + "/yangyu/compression/";
//    public static final String fileName= Long.toString(System.nanoTime())+".png";
    public static void makeFolder(String path) {
        makeFolder(path,false);
    }
    public static void makeFolder(String path, boolean isHidden) {
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        if(isHidden){
            File hiddenDir = new File(path+".nomedia");
            if (!hiddenDir.exists()) {
                hiddenDir.mkdirs();
            }
        }

    }
}
