package com.yfy.final_tag;

import android.os.Environment;

/**
 * Created by yfyandr on 2017/9/12.
 */

public  class TagFinal {


    public static String getAppFile(){
        return Environment.getExternalStorageDirectory().toString() + "/yfy/";
    }
    /**
     *   int final tag
     */
    public final static int ZERO_INT = 0;//常量 0
    public final static int ONE_INT = 1;//常量 1
    public final static int TWO_INT = 2;//常量
    public final static int THREE_INT = 3;//常量 3
    public final static int FOUR_INT = 4;//常量 4
    public final static int TEN_INT = 10;//常量 10
    public final static int TWO_TEN = 15;//常量 10


    public final static int TYPE_REFRESH = 5;//
    public final static int TYPE_FOOTER = ONE_INT;//
    public final static int TYPE_ITEM = TWO_INT;//
    public final static int TYPE_PARENT = THREE_INT;//
    public final static int TYPE_CHILD = FOUR_INT;//
    public final static int TYPE_TOP = ZERO_INT;//

    public final static int LOADING = ONE_INT;//
    public final static int LOADING_COMPLETE = TWO_INT;//
    public final static int LOADING_END = THREE_INT;//


    public final static int REFRESH_END = ONE_INT;//
    public final static int REFRESH_START = TWO_INT;//


    public static final int CALL_PHONE = 1006;//NET_WIFI
    public static final int CAMERA= 1003;//调用摄像头
    public static final int PHOTO_ALBUM = 1004;//调用相册
    public static final int NET_WIFI = 1005;//NET_WIFI
    public static final int UI_TAG = 1101;//tag
    public static final int UI_CONTENT = 1102;//content
    public static final int UI_REFRESH = 1201;//页面刷新
    public static final int UI_ADD = 1202;//进入添加
    public static final int UI_ADMIN = 1203;//审核操作

    public static final int PAGE_NUM = TEN_INT;//常量页码条数
    /**
     *   String final tag
     */
    //版本更新请求网址
    public static final int TIME_OUT = 8000;


    //常用字段名称


    public static final String TRUE="true";
    public static final String FALSE="false";
    public static final String REFRESH="refresh";
    public static final String REFRESH_MORE="refresh_more";
    public static final String MAP_TXT_TAG="map_txt";
    public static final String MAP_PIC_TAG="map_pic";
    public static final String ALBUM_LIST_INDEX="index";
    public static final String ALBUM_SINGLE="single";
    public static final String ALBUM_TAG="album_tag";
    public static final String ALBUM_SINGE_URI="album_singe_uri";
    public static final String ACTION_INTENT_FILTER="zxx.intent.badge";
    public static final String BADGE="badge";//桌面小红点badge

    public static final String PRAISE_TAG="praise_tag";
    public static final String DELETE_TAG="delete_tag";
    public static final String ISSUE_TAG="issue_tag";
    public static final String FORBID_TAG="forbid_tag";
    public static final String CLASS_ID="class_id";//
    public static final String CLASS_BEAN="class_bean";//班级对象tag
    public static final String OBJECT_TAG="object_tag";
    public static final String ID_TAG="id_tag";
    public static final String NAME_TAG="name_tag";
    public static final String TITLE_TAG="title_tag";
    public static final String CONTENT_TAG="content_tag";
    public static final String HINT_TAG="hint_tag";
    public static final String URI_TAG="uri_tag";

    public static final String TYPE_TAG="type_tag";


    public static final String USER_TYPE_TEA="tea";
    public static final String USER_TYPE_STU="stu";
    public static final String ZXX="zxx";

    /**
     *   //url
     */


    public static final String HUAWEI = "http://www.yfyit.com/yszc.html";//

    /**
     * -------------------new--------------
     */
    public static final String NEWS_MENU="getmenu";//获取栏目列表
    public static final String SCHOOL_NEWS_LIST ="getnewslist";//获取栏目新闻列表
    public static final String  SCHOOL_NEWS_BANNER = "getscroll_picture";


    public static final String USER_BASE_DATA = "get_stu_baseinfo";//获取个人中心基础数据
    public static final String USER_BASE_UPDATA = "set_stu_baseinfo";//设置个人中心基础数据
    public static final String SCHEDULE = "http://www.cdeps.sc.cn/kcb.aspx?sessionkey=";//教师课程表url+sessionkey
    public static final String DEYU_KEY = "http://www.cdeps.sc.cn/showdykp.aspx?sessionkey=";//班级评比
    public static final String POINT_PATH = "http://xsl.yfyit.com/detail.aspx?id=30072";//新生报名招生须知地址

    public static final String AUTHEN_BMCX = "bmcx";//
    public static final String AUTHEN_GET_STU = "getstuxx";//获取学生
    public static final String AUTHEN_SET_STU = "setstuxx";//设置学生信息

    public static final String BASE_GET_CLASS = "get_class_list";//class:145*456

    public static final String SCHOOL_GET_NEWS_LIST = "getnewslist";//公告


    /**
     * -------------------user相关-------------------
     */
    public static final String USER_GET_TERM = "gettermlistnew";//获取学期列表
    public static final String GET_CURRENT_TERM = "getCurrentTermnew";//获取当前学期
    public static final String USER_GET_MOBILE = "get_Mobile";//获取电话
    public static final String USER_SET_MOBILE = "set_Mobile";//置电话
    public static final String GETNOTICENUM = "getnoticenum";//小红点
    public static final String CLEAR_NOTICE_NUM = "clearnoticenum";//清除红点
    public static final String READNOTICE = "readnotice";//阅读小红点
    public static final String GET_USER_ADMIN = "get_user_right";//获取权限
    public static final String USER_ADD_HEAD = "addphoto";//用户添加头像
    public static final String USER_LOGOUT = "logout";//用户退出登录logout
    public static final String LOGIN = "login";//登录
    public static final String LOGIN_SIGN = "sign";//登录
    public static final String LOGSTU = "logstu";//重名学生login
    public static final String GET_DUPLICATION_USER = "user_duplication";//返回重名学生

    public static final String USER_ALTER_PASS_WORD = "resetpassword";

    public static final String SEND_PHONE_CODE="get_phone_code";//给用户绑定号码发送验证码（返回有code）
    public static final String RESET_CODE="reset_password_vcode";//获取验证码（输入phone）
    public static final String RESET_PASSWORD="reset_password_password";//通过验证码设置密码
    /**
     * -------------------foot_book-------------------------------
     */
    public static final String FOOT_ = "cookbook_praise";//
    public static final String FOOT_get = "get_cookbook";//
    /**getnewslist
     * ----------------notice----------------------
     */




    public final static String MAINTIAN_ADD= "add_LogisticsMaintenance";//新增报修
    public final static String MAINNEW_GET_MAIN_LIST_USER = "get_LogisticsMaintenance_list";//获取首页列表普通用户dealstate;//user：0; admin:-1
    public final static String MAINNEW_GET_MAIN_LIST_ADMIN = "get_LogisticsMaintenance_admin";//获取首页列表管理用户
    public final static String MAINNEW_DELETE_MAINTAIN= "del_LogisticsMaintenance";//撤销自己申请
    public final static String MAINNEW_SYXX= "set_LogisticsMaintenance_state";//同意，拒绝， dealstate,//1,完成2，拒绝,3 维修中
    public final static String MAINNEW_SET_TYPE= "set_LogisticsMaintenance_classid";//部门转交
    public final static String MAINNEW_GET_TYPE= "get_LogisticsMaintenance_class";//获取部门分类
    public final static String MAINNEW_GET_OFICE= "get_LogisticsMaintenance_office";//获取报修地址
    public final static String MAINNEW_GET_COUNT= "get_LogisticsMaintenance_review_count";//获取需要处理次数
    public final static String MAINNEW_GET_OPERATE= "get_LogisticsMaintenance_operate";//获取后勤点击处理后能做的处理操作列表
    public final static String MAINNEW_GET_DETAIL= "get_LogisticsMaintenance_byid";//增加返回isremark 如果是true则已经备注了，无须在备注(已完成或者已拒绝都可以添加备注)
    /**
     *  审核列表 attendance_review_list_syxx(string session_key, int userid=0, string key="", int type=0, int status=0, int page, int size)
        自己列表 (string session_key, int type=0, int status=0, int page, int size)
        审核操作 attendance_did_review_syxx(string session_key, string id, string reply, string table_plan, int review_result)
     attendance_list_cdpx
     */
    /**
     * -----------------atten------------------
     */
    public final static String ATTENNEW_GET_MAIN_LIST_USER= "attendance_list_syxx";//获取首页列表普通用户
    public final static String ATTENNEW_GET_MAIN_LIST_ADMIN= "attendance_review_list_syxx";//获取首页审核列表
    public final static String ATTENNEW_ADMIN_DO ="attendance_did_review_syxx";//审核操作(同意1，申请2，驳回3，校长4)
    public final static String ATTENNEW_SUBMIT ="attendance_submit1";//新增提交cdpx
    public final static String ATTENNEW_DELETE ="delete_attendance";//撤销申请
    //    public final static String ATTENNEW_USER_LIST ="attendance_approvenew";//审核人列表（需转交)
    public final static String ATTENNEW_USER_LIST ="attendance_transmit_user";//审核人列表(不需转交)
    public final static String ATTENNEW_TYPE ="attendance_type";//请假type
    public final static String ATTENNEW_ADMIN_COUNT ="get_attendance_review_count";//审核操作数量




    /**
     * ---------------------------功能室申请------------
     */
    public final static String Order_USER_LIST="my_funcRoom";//我的记录-1 全部 5 已结束
    public final static String ORDER_GET_COUNT="review_funcRoom_count";//个数

    public final static String ORDER_ADMIN_RECORD="review_funcRoom_record";//审核记录 list
    public final static String ORDER_LOGISTICS_LIST="logistics_funcRoom";//后勤处理列表

    public final static String ORDER_GET_DETAIL="get_funcRoom_detail";//单条详情
    public final static String ORDER_GET_ROOM_NAME="get_funcRoom_name";//获取功能室名称

    public final static String ORDER_GET_GRADE="get_funcRoom_type";//获取预约等级
    public final static String ORDER_QUERY="query_funcRoom";//查功能室状态
    public final static String ORDER_ADMIN_LIST="review_funcRoom_list";//审核记录 0,
    public final static String ORDER_SUBMIT="submit_funcRoomnew";//提交
    public final static String ORDER_AUDIT="review_funcRoom";//审核接口：2拒绝1同意
    public final static String ORDER_SET_SCORE="set_funcRoom_score";//打分 islogistics(0不打，1打), logisticsscore, logisticscontent,
    public final static String MERO_SON_CONTENT="review_funcRoom_content";



    /**
     * ---------------------------教师平车------------------
     */

    public final static String TEA_JUDGE_STATISTICS_CLASS="get_teacher_judge_statistics_class";//获取统计考评类型
    public final static String TEA_JUDGE_CLASS="get_teacher_judge_class";//获取添加考评类型
    public final static String TEA_JUDGE_YEAR="get_teacher_judge_year";//获取考评年份。
    public final static String TEA_ADD_PARAMETER="get_teacher_judge_parameter";//获取添加考评参数(无响应)
    public final static String TEA_ADD_JUDGE="add_teacher_judge";//添加考评
    //    public final static String TEA_DELECTED_PIC="del_teacher_judge_image";//删除考评附件
    public final static String TEA_DELECTED_PIC="del_teacher_judge_imagecdpx";//删除考评附件


    public final static String TEA_JUDGE_STATISTICS="get_teacher_judge_statistics";//获取考评统计数据
    public final static String TEA_JUDGE_LIST="get_teacher_judge_record_list";//获取某一年单项获奖记录列表
    public final static String TEA_JUDGE_INFO="get_teacher_judge_info";//获取单个考评的详细内容 (not)
    public final static String TEA_DELETE_JUDGE="del_teacher_judge_list";//del item
    public final static String TEA_DELETE_JE="get_teacher_judge_statistics_list";//????


}


