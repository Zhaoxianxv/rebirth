package com.yfy.app.bean;


import java.util.List;

public class NoticeRes {

    /**
     * result : true
     * error_code :
     */

    private String result;
    private String error_code;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }


    private List<ContactsGroup> userinfotx;

    public List<ContactsGroup> getUserinfotx() {
        return userinfotx;
    }

    public void setUserinfotx(List<ContactsGroup> userinfotx) {
        this.userinfotx = userinfotx;
    }


}
