package com.yfy.base;


import com.yfy.db.User;

/**
 * Created by Daniel on 2016/3/8.
 * 产量们
 */
public class Base {
    public static String DB_NAME="yfydb";

    public static final String USER_BASE_DATA = "get_stu_baseinfo";//获取个人中心基础数据
    public static final String USER_BASE_UPDATA = "set_stu_baseinfo";//设置个人中心基础数据
    public static final String SCHEDULE = "http://www.cdeps.sc.cn/kcb.aspx?sessionkey=";//教师课程表url+sessionkey
    public static final String DEYU_KEY = "http://www.cdeps.sc.cn/showdykp.aspx?sessionkey=";//班级评比
    public static final String POINT_PATH = "http://xsl.yfyit.com/detail.aspx?id=30072";//新生报名招生须知地址


    public final static String URL = "http://xsl.yfyit.com/service2.svc";
    public final static String RETROFIT_URI = "http://xsl.yfyit.com/";
    //app更新地址
    public static final String AUTO_UPDATE_URI = "http://www.yfyit.com/apk/xsl.txt";
    public static final String AUTHORITY = "com.yfy.rebirth.fileProvider";//android 7.0文件访问权限Tag（要和Provider一直）
//--------------------------------------
    public static final String NET_SOAP_ACTION = "http://tempuri.org/Service2/";
    public static final String Content_Type = "Content-Type: text/xml;charset=UTF-8";//
    public static final String SOAP_ACTION = "SOAPAction: http://tempuri.org/Service2/";//
    public static final String POST_URI = "/Service2.svc";//
    public static final String NAMESPACE = "http://tempuri.org/";//




    public static final String TEM = "tem";//
    public static final String ARR = "arr";//
    public static final String BODY = "Body";//
    public static final String RESPONSE = "Response";//
    public static final String RESULT = "Result";//
    public static final String XMLNS = "xmlns";//

    public static final String session_key = "session_key";//
    public static final String token = "token";//
    public static final String type = "type";//
    public static final String state = "state";//
    //    public static final String fxid = "fxid";//
    public static final String page = "page";//
    public static final String size = "size";//
    public static final String pagesize = "pagesize";//
    public static final String date = "date";//
    public static final String data = "data";//
    public static final String id = "id";//
    public static final String index = "index";//
    public static final String reason = "reason";//
    public static final String name = "name";//
    public static final String title = "title";//
    public static final String content = "content";//
    public static final String image = "image";//
    public static final String voice = "voice";//
    public static final String count = "count";//
    public static final String can_edit = "can_edit";//
    public static final String num = "num";//
    public static final String phone = "phone";//
    public static final String WCF_TXT = "wcf.txt";
    public static final String error_code = "session_key不正确";









    public static final int TIME_OUT = 10000;
    public final static String APP_ID="";
    public static final int UPLOAD_LIMIT = 100 * 1024;
    public static final long TOTAL_UPLOAD_LIMIT = 4 * 1024 * 1024;




    public static float density = 0;
    public static String wcfInfo = "";
    public static User user = null;



    //tea
    public static String year="";
    public static String year_name="";
    public static String type_id="";
    public static String type_name="";
}
