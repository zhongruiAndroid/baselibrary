package com.library.base.bean;

import com.library.base.BaseObj;

/**
 * Created by Administrator on 2018/4/23.
 */

public class PayObj extends BaseObj {
    /**
     * payment_type : 1
     * payment_url : http:///app/AliSecurity/notifyUrl.aspx
     */

    private int payment_type;
    private String payment_url;

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }
}
