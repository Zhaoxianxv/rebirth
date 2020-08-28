package com.yfy.app.bean;

import java.util.List;

public class GradeBean {

    /**
     * gradename : 1年级
     * gradeid : 0
     */

    private String gradename;
    private String gradeid;
    private List<ClassGrade> classlist;


    public List<ClassGrade> getClasslist() {
        return classlist;
    }

    public void setClasslist(List<ClassGrade> classlist) {
        this.classlist = classlist;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }
}
