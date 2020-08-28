package com.yfy.app.net;


import com.yfy.app.net.atten.*;
import com.yfy.app.net.maintain.*;
import com.yfy.app.net.tea_evaluate.*;
import com.yfy.app.net.user.*;
import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by SmileXie on 16/7/15.
 */
@Root(name = Base.BODY)
public class ResBody {


    @Element(name = TagFinal.NEWS_MENU+Base.RESPONSE, required = false)
    public NewMenuRes news_menu;

    @Element(name = TagFinal.SCHOOL_NEWS_BANNER+"Response", required = false)
    public NewBannerRes news_banner;


    @Element(name = TagFinal.GET_CURRENT_TERM+"Response", required = false)
    public GetTermRes getCurrentTermResponse;

    @Element(name = TagFinal.GETNOTICENUM+"Response", required = false)
    public NticeNumRes getnoticenumResponse;

    @Element(name = TagFinal.GET_USER_ADMIN+"Response", required = false)
    public AdminRes adminRes;

    @Element(name = "readnoticeResponse", required = false)
    public ReadNoticeRes readnoticeResponse;

    @Element(name = TagFinal.USER_GET_MOBILE+"Response", required = false)
    public CallRes callResponse;

    @Element(name = TagFinal.RESET_CODE+"Response", required = false)
    public ResetCodeRes phone_edit_code_res;

    /**
     * -------------------------atten---------------------
     */

    @Element(name = "get_attendance_review_countResponse", required = false)
    public AttenCountRes get_attendance_review_countResponse;

    @Element(name = "attendance_typeResponse", required = false)
    public AttenTypeRes attendance_typeResponse;

    @Element(name = TagFinal.ATTENNEW_USER_LIST+"Response", required = false)
    public AdminUserRes attendance_approveResponse;

    @Element(name = TagFinal.ATTENNEW_GET_MAIN_LIST_ADMIN+Base.RESPONSE, required = false)
    public AttenAdminListRes atten_admin_list_body;

    @Element(name = TagFinal.ATTENNEW_GET_MAIN_LIST_USER+Base.RESPONSE, required = false)
    public AttenUserListRes atten_user_list_body;

    /**
     * ------------------------maintain-----------------------------
     */

    @Element(name = TagFinal.MAINNEW_GET_MAIN_LIST_USER+Base.RESPONSE, required = false)
    public MaintainListRes get_Maintain_userResponse;
    @Element(name = TagFinal.MAINNEW_GET_MAIN_LIST_ADMIN+Base.RESPONSE, required = false)
    public AdminListRes maintain_admin_body;

    @Element(name = TagFinal.MAINNEW_GET_COUNT+Base.RESPONSE, required = false)
    public MaintainCountRes get_maintain_review_countResponse;

    @Element(name = TagFinal.MAINNEW_GET_TYPE+Base.RESPONSE, required = false)
    public BranchTypeRes getMaintainclassResponse;

    @Element(name = TagFinal.MAINNEW_GET_OFICE+Base.RESPONSE, required = false)
    public OfficeRes officeRes;

    /**
     * -------------------box-----------------------
     */
//    @Element(name = TagFinal.BOX_GET_LEADER_LIST+"Response", required = false)
//    public LeaderRes leaderRes;
//    @Element(name = TagFinal.BOX_GET_COUNT_LEADER+"Response", required = false)
//    public CleaderRes cleaderRes;
//    @Element(name = TagFinal.BOX_GET_COUNT_USER+"Response", required = false)
//    public CUserRes cUserRes;



    //----------------tea evaluate----------------------

    @Element(name = TagFinal.TEA_JUDGE_CLASS+"Response", required = false)
    public JudgeAddRes judge_Response;

    @Element(name =  TagFinal.TEA_JUDGE_STATISTICS_CLASS+"Response", required = false)
    public JudgeTjRes judge_tj_Response;

    @Element(name =  TagFinal.TEA_JUDGE_STATISTICS+"Response", required = false)
    public JudgeChartRes judge_chart_Response;

    @Element(name = TagFinal.TEA_JUDGE_YEAR+"Response", required = false)
    public YearRes year_Response;

    @Element(name =  TagFinal.TEA_ADD_PARAMETER+"Response", required = false)
    public JudgeparaRes para_Response;

    @Element(name = TagFinal.TEA_JUDGE_INFO+"Response", required = false)
    public JudgeItemRes item_Response;

}
