package com.yfy.app.attennew.bean;

import com.yfy.app.bean.NameId;
import com.yfy.app.bean.UserNameId;

import java.util.List;

/**
 * Created by yfyandr on 2017/12/28.
 */

public class AttenRes {

    /**
     * result : true
     * error_code :
     */
    private String result;
    private String error_code;

    public void setResult(String result) {
        this.result = result;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getResult() {
        return result;
    }

    public String getError_code() {
        return error_code;
    }

    //--------------------------

    private List<AttenBean> attendance_lists;

    public List<AttenBean> getMaintains() {
        return attendance_lists;
    }

    public void setMaintains(List<AttenBean> maintains) {
        attendance_lists = maintains;
    }


    //需要处理次数
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    //--------------------请假审批人----------------------
    private List<UserNameId> approve_user;
    public void setSubject(List<UserNameId> subject) {
        this.approve_user = approve_user;
    }
    public List<UserNameId> getSubject() {
        return approve_user;
    }
    private List<NameId> user_lists;

    public List<NameId> getUser_lists() {
        return user_lists;
    }

    public void setUser_lists(List<NameId> user_lists) {
        this.user_lists = user_lists;
    }
}
