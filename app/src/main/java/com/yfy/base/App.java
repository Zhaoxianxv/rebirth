package com.yfy.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import com.yfy.db.MySQLiteOpenHelper;
import com.yfy.final_tag.$;
import com.yfy.greendao.DaoMaster;
import com.yfy.greendao.DaoSession;
import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aj
 * multiD
 * ex 配置原因。让应用的 Application 继承 MultiDexApplication
 */
public class App extends MultiDexApplication {
    private List<Activity> activities = new ArrayList<Activity>();
    private static App app;


    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        setDatabase();
        app = this;
        initLesscode();
//        分析工具初始化
//        LeakCanary.install(this);
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    public static App getApp() {
        return app;
    }
    public void addActivity(Activity activity) {
        activities.add(activity);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : activities) {
            activity.finish();
        }
        System.exit(0);
    }



    /**
     * 设置greenDAO
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),Base.DB_NAME, null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    private void initLesscode() {
        $.getInstance()
                .context(getApplicationContext())
                .http(5000, 5000)
                .build();
    }
}
