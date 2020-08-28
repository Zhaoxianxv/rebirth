package com.yfy.db;

import android.util.Log;
import com.yfy.base.App;
import com.yfy.final_tag.TagFinal;
import com.yfy.greendao.UserDao;

import java.util.List;

public class GreenDaoManager {
    public static GreenDaoManager manager;

    private GreenDaoManager() {
    }

    public synchronized static GreenDaoManager getInstance() {
        if (manager == null) {
            manager=new GreenDaoManager();
        }
        return manager;
    }

    private UserDao getUserDao() {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return ((App) App.getApp().getApplicationContext()).getDaoSession().getUserDao();
    }





    public User getUser(String  session_key){
        return App.getApp().getDaoSession().getUserDao().queryRaw("where session_key = \""+session_key+"\"").get(0);
    }

    /**清除 用户*/
    public void clearUser() {
        getUserDao().deleteAll();
    }
//    /**
//     * 添加用户
//     * @param note
//     */
//
//    public  void addNote(User note) {
//        clearUser();
//        if (note == null ) {
//            Log.e("zxx","db add user : user==null");
//        } else {
//            // 插入操作，简单到只要你创建一个 Java 对象
//            getUserDao().insert(note);
//        }
//    }

    /**
     * 根据id,删除数据
     * @param id      用户id
     */
    public void deleteNote(long id){
        getUserDao().deleteByKey(id);
        Log.i(TagFinal.ZXX, "delete");
    }

    /**
     * 根据用户类,删除信息
     * @param user    用户信息类
     */
    public void deleteNote(User user){
        getUserDao().delete(user);
    }
    /**
     * 根据用户信息,插件或修改信息
     * @param user              用户信息
     * @return 插件或修改的用户id
     */
    public long saveNote(User user){
        return getUserDao().insertOrReplace(user);
    }
    /**
     * 取出所有数据
     * @return      所有数据信息
     */
    public List<User> loadAllNote(){
        return getUserDao().loadAll();
    }
    /**
     * 根据查询条件,返回数据列表
     * @param where        条件
     * @param params       参数  "where astir_id = \""+astir_id+"\" and class_id = \""+class_id+"\""
     * @return             数据列表
     *
     */
    public List<User> queryNote(String where, String... params){
        return getUserDao().queryRaw(where, params);
    }

    /**
     * 批量插入或修改用户信息
     * @param list      用户信息列表
     */
    public void saveNoteLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        getUserDao().getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User user = list.get(i);
                    getUserDao().insertOrReplace(user);
                }
            }
        });

    }


}