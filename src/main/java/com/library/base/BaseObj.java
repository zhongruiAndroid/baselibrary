package com.library.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/20.
 */

public class BaseObj implements Serializable {
    private String msg;
    private String SMSCode;//获取短信验证码
    private String url;//上传文件返回路径
    private String img;//上传图片返回路径
    private int is_check;//首页是否有未读消息、是否有红点(1有 0无)
    private String card_id;//添加信用卡发短信返回card_id
    private String user_agreement;//用户协议
    protected int message_sink;//消息设置1：开，0：关

    public int getMessage_sink() {
        return message_sink;
    }

    public void setMessage_sink(int message_sink) {
        this.message_sink = message_sink;
    }

    public String getUser_agreement() {
        return user_agreement;
    }

    public void setUser_agreement(String user_agreement) {
        this.user_agreement = user_agreement;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getSMSCode() {
        return SMSCode;
    }
    public void setSMSCode(String SMSCode) {
        this.SMSCode = SMSCode;
    }
}
