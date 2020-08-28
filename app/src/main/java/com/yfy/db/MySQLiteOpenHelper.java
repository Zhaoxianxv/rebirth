package com.yfy.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.yfy.greendao.DaoMaster;
import com.yfy.greendao.DaoSession;
import com.yfy.jpush.Logger;
import org.greenrobot.greendao.database.Database;

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            Log.d("zxx", "数据库是最新版本" + oldVersion + "，不需要升级");
            return;
        }
        Log.e("zxx", "数据库从版本" + oldVersion + "升级到版本" + newVersion);
        switch (oldVersion) {
            case 1:
                String sql = "";
                db.execSQL(sql);
            case 2:
            default:
                break;
        }
    }
}

