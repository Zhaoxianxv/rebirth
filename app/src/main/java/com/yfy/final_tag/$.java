package com.yfy.final_tag;

import android.content.Context;

public final class $ {
    /**
     * 单例
     */
    private static $ instance;
    public static $ getInstance() {
        if (instance == null) {
            synchronized ($.class) {
                if (instance == null) {
                    instance = new $();
                }
            }
        }
        return instance;
    }

    /**
     * 名义上为build,实则是检查一些必须配置的变量
     */
    public void build(){
        if (sAppContext == null) {
            throw new RuntimeException("please config the lesscode application context");
        }
    }

    /**
     * *********************************************************************************************
     * Global ApplicationContext
     * *********************************************************************************************
     */
    static Context sAppContext;
    public $ context(Context context) {
        sAppContext = context;
        return this;
    }

    /**
     * *********************************************************************************************
     * AppLess
     * *********************************************************************************************
     */
    public static final String KEY_DOWNLOAD_URL = "download_url";
    static String sUpdateJsonUrl;
    public $ app(String updateJsonUrl) {
        sUpdateJsonUrl = updateJsonUrl;
        return this;
    }

    /**
     * *********************************************************************************************
     * HttpLess
     * *********************************************************************************************
     */
    static int sConnectTimeOut = 5000;
    static int sReadTimeout = 5000;
    public $ http(int connectTimeOut, int readTimeout) {
        sConnectTimeOut = connectTimeOut;
        sReadTimeout = readTimeout;
        return this;
    }

}
