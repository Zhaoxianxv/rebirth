package com.yfy.app.tea_evaluate.bean;

import java.util.List;

public class ParamBean {

    /**
     * title : 获奖级别
     * content : 1
     * id : 74
     * type : select
     * info : ["区级","市级","省级","国家级"]
     */

    private String title;
    private String content;
    private String id;
    private String type;
    private List<KeValue> info;

    private int view_type;


    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<KeValue> getInfo() {
        return info;
    }

    public void setInfo(List<KeValue> info) {
        this.info = info;
    }
}
