package com.yfy.db;

import android.content.Context;
import android.content.SharedPreferences;
import com.yfy.base.App;


/**
 *
 */
public class UserPreferences extends Preferences {

    /**
     * 配置文件名儿
     */
    private final String PREFERENCE_NAME = "USER_INFO";


    private static UserPreferences userPreferences;

    private UserPreferences() {
    }

    public static UserPreferences getInstance() {
        if (userPreferences == null) {
            userPreferences = new UserPreferences();
        }
        return userPreferences;
    }

    public SharedPreferences getPreference() {
        return App.getApp().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    //用户退出时本地数据清除
    public void clearUserData(){
        String md5=getMdCode();
        String jpush_key=getJpushKey();
        clearAll();
        saveJpushKey(jpush_key);
        saveMdCode(md5);
    }
    /**
     * 保存用户 greenDao name
     * 取得用户 greenDao name
     */
    private final String GreenDao="greenDao_name";
    public void saveGreenDaoName(String name) {
        saveString(GreenDao, name);
    }
    public String getGreenDaoName() {
        return getString(GreenDao,"");
    }



    /**
     * 用户权限存储
     * @return
     */
    public UserAdmin getUserAdmin() {
        UserAdmin admin=new UserAdmin();
        admin.setIsassessadmin(userPreferences.getString("admin_isassessadmin",""));
        admin.setIshqadmin(userPreferences.getString("admin_ishqadmin",""));
        admin.setIsnoticeadmin(userPreferences.getString("admin_isnoticeadmin",""));
        admin.setIsqjadmin(userPreferences.getString("admin_isqjadmin",""));
        admin.setIsxcadmin(userPreferences.getString("admin_isxcadmin",""));
        admin.setIsfuncRoom(userPreferences.getString("admin_isfuncRoom",""));
        admin.setIslogistics(userPreferences.getString("admin_islogistics",""));
        admin.setIselectiveteacher(userPreferences.getString("admin_iselectiveteacher",""));
        admin.setIselectiveadmin(userPreferences.getString("admin_iseventadmin",""));
        return admin;
    }
    public UserAdmin saveUserAdmin( UserAdmin admin) {

        saveString("admin_isassessadmin",admin.getIsassessadmin());
        saveString("admin_ishqadmin",admin.getIshqadmin());
        saveString("admin_isnoticeadmin",admin.getIsnoticeadmin());
        saveString("admin_isqjadmin",admin.getIsqjadmin());
        saveString("admin_isxcadmin",admin.getIsxcadmin());
        saveString("admin_isfuncRoom",admin.getIsfuncRoom());
        saveString("admin_islogistics",admin.getIslogistics());
        saveString("admin_iselectiveteacher",admin.getIselectiveteacher());
        saveString("admin_iseventadmin",admin.getIselectiveadmin());
        return admin;
    }

    /**
     * 保存tell
     */
    public void saveTell(String tell){
        saveString("tell_string",tell);
    }
    public String getTell(){
        return getString("tell_string","");
    }

    /**
     * 保存 推送 key
     */

    private final String JpushKey="jpush_key";
    public void saveJpushKey(String key){
        saveString(JpushKey,key);
    }
    public String getJpushKey(){return getString(JpushKey,"");
    }
    /**
     * 保存 md5 code
     */

    private final String MdCode="md5_code";
    public void saveMdCode(String key){
        saveString(MdCode,key);
    }
    public String getMdCode(){
        return getString(MdCode,"");
    }

    //
    /**
     * 保存first
     */
    private static final String TAG_FIRST = "first";
    public void saveFirstTimeOpen(boolean name){
        saveBooolean(TAG_FIRST, name);
    }

    public boolean getIsFirstTimeOpen(){
        return getBoolean(TAG_FIRST,true);
    }



    /**
     * 保存流程输入类容
     */
    public void saveContent(String password) {
        saveString("maintain_content", password);
    }
    public String getContent() {return getString("maintain_content", "");
    }

    public void saveSession_key(String name) {
        saveString("session_key", name);
    }
    public String getSession_key() {
        return getString("session_key","");
    }



    /**
     * 保存主页模块顺序
     */
    public void saveIndex(String index_s){
        saveString("main_index",index_s);
    }
    public String getIndex(){
        return getString("main_index","");
    }

    public void saveUserSetView(String name) {
        saveString("user_set_view", name);
    }
    public String getUserSetView() {
        return getString("user_set_view","");
    }


}
