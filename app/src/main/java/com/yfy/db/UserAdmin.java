package com.yfy.db;

/**
 * Created by yfy1 on 2016/11/30.
 */
public class UserAdmin {

    /**
     * isassessadmin : true
     * ishqadmin : true
     * isnoticeadmin : true
     * isqjadmin : true
     * isxcadmin : true
     * username : null
     * isboss   У
     *
     */

    private String isassessadmin;
    private String ishqadmin;//
    private String isnoticeadmin;
    private String isqjadmin;
    private String isxcadmin;//
    private String username;
    private String isfuncRoom;
    private String ishqlader;
    private String islogistics;//后勤
    private String iselectiveadmin;//考察管理员权限
    private String iselectiveteacher;//教师权限


    public String getIselectiveadmin() {
        return iselectiveadmin;
    }

    public void setIselectiveadmin(String iselectiveadmin) {
        this.iselectiveadmin = iselectiveadmin;
    }

    public String getIselectiveteacher() {
        return iselectiveteacher;
    }

    public void setIselectiveteacher(String iselectiveteacher) {
        this.iselectiveteacher = iselectiveteacher;
    }

    public String getIslogistics() {
        return islogistics;
    }

    public void setIslogistics(String islogistics) {
        this.islogistics = islogistics;
    }

    public String getIshqlader() {
        return ishqlader;
    }

    public void setIshqlader(String ishqlader) {
        this.ishqlader = ishqlader;
    }

    public String getIsfuncRoom() {
        return isfuncRoom;
    }

    public void setIsfuncRoom(String isfuncRoom) {
        this.isfuncRoom = isfuncRoom;
    }

    public String getIsassessadmin() {
        return isassessadmin;
    }

    public void setIsassessadmin(String isassessadmin) {
        this.isassessadmin = isassessadmin;
    }

    public String getIshqadmin() {
        return ishqadmin;
    }

    public void setIshqadmin(String ishqadmin) {
        this.ishqadmin = ishqadmin;
    }

    public String getIsnoticeadmin() {
        return isnoticeadmin;
    }

    public void setIsnoticeadmin(String isnoticeadmin) {
        this.isnoticeadmin = isnoticeadmin;
    }

    public String getIsqjadmin() {
        return isqjadmin;
    }

    public void setIsqjadmin(String isqjadmin) {
        this.isqjadmin = isqjadmin;
    }

    public String getIsxcadmin() {
        return isxcadmin;
    }

    public void setIsxcadmin(String isxcadmin) {
        this.isxcadmin = isxcadmin;
    }

    public String  getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
