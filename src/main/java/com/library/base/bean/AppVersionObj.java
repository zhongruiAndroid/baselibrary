package com.library.base.bean;


import com.library.base.BaseObj;

/**
 * Created by Administrator on 2018/1/18.
 */

public class AppVersionObj extends BaseObj {
    /**
     * android_version : 1.1
     * android_vs_name : 2
     * android_vs_url : http://121.40.186.118:6699/upload/app-release.apk
     */

    private int android_version;
    private String android_vs_name;
    private String android_vs_url;

    public int getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(int android_version) {
        this.android_version = android_version;
    }

    public String getAndroid_vs_name() {
        return android_vs_name;
    }

    public void setAndroid_vs_name(String android_vs_name) {
        this.android_vs_name = android_vs_name;
    }

    public String getAndroid_vs_url() {
        return android_vs_url;
    }

    public void setAndroid_vs_url(String android_vs_url) {
        this.android_vs_url = android_vs_url;
    }
}
