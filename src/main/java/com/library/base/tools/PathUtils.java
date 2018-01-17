package com.library.base.tools;

import android.content.Context;
import android.os.Environment;

/**
 * Created by Administrator on 2018/1/11.
 */

public class PathUtils {
    public static final String fileSaveName= "save";
    public static final String fileCompressionName= "compression";
    public static String getImgCompressionPath(){
        return getImgCompressionPath(null);
    }
    public static String getImgCompressionPath(Context context){
        String folderName;
        if(context==null){
            folderName="skimg";
        }else{
            String packageName = context.getPackageName();
            String[] fileName = packageName.split("\\.");
            folderName=fileName[fileName.length-1];
        }
        String path= Environment.getExternalStorageDirectory().getPath() + "/"+folderName+"/"+fileCompressionName+"/";
        return path;
    }
}
