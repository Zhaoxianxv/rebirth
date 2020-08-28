package com.yfy.app.maintainnew.bean;

public class Dobean {

    /**
     * operatecode : 2
     * operatename : 拒绝维修
     */

    private String operatecode;
    private String operatename;

    private boolean select=false;


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getOperatecode() {
        return operatecode;
    }

    public void setOperatecode(String operatecode) {
        this.operatecode = operatecode;
    }

    public String getOperatename() {
        return operatename;
    }

    public void setOperatename(String operatename) {
        this.operatename = operatename;
    }
}
