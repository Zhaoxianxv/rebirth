package com.yfy.app.net;

import com.yfy.app.net.atten.*;
import com.yfy.app.net.maintain.*;
import com.yfy.app.net.tea_evaluate.*;
import com.yfy.app.net.user.*;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 */
@Root(name = "Body", strict = false)
public class ReqBody {

    @Element(name = TagFinal.GET_USER_ADMIN, required = false)
    public AdminReq adminReq;

    @Element(name = TagFinal.NEWS_MENU, required = false)
    public NewMenuReq newMenuReq;

    @Element(name = TagFinal.SCHOOL_NEWS_BANNER, required = false)
    public NewBannerReq newBannerReq;

    @Element(name = TagFinal.GET_CURRENT_TERM, required = false)
    public GetTermReq get_current_term;

    @Element(name = TagFinal.READNOTICE, required = false)
    public ReadNoticeReq readnotice;


    @Element(name = TagFinal.GETNOTICENUM, required = false)
    public NticeNumReq getnoticenum;




    @Element(name = TagFinal.USER_GET_MOBILE, required = false)
    public CallReq callReq;





    @Element(name = TagFinal.RESET_CODE, required = false)
    public ResetCodeReq phone_edit_code;

    /**
     * -----------------------atten------------------------
     */



    @Element(name = TagFinal.ATTENNEW_GET_MAIN_LIST_USER, required = false)
    public AttenUserListReq atten_user_list_body;

    @Element(name = TagFinal.ATTENNEW_GET_MAIN_LIST_ADMIN, required = false)
    public AttenAdminListReq atten_admin_list_body;


    @Element(name = TagFinal.ATTENNEW_ADMIN_COUNT, required = false)
    public AttenCountReq count_atten;


    @Element(name = TagFinal.ATTENNEW_TYPE, required = false)
    public AttenTypeReq type_atten;

    @Element(name = TagFinal.ATTENNEW_USER_LIST, required = false)
    public AdminUserReq atten_get_admin;

    /**
     * ------------------------maintain------------------
     */


    @Element(name = TagFinal.MAINNEW_GET_COUNT, required = false)
    public MaintainCountReq count_maintain;

    @Element(name = TagFinal.MAINNEW_GET_MAIN_LIST_USER, required = false)
    public MaintainListReq maintain_userlist;

    @Element(name = TagFinal.MAINNEW_GET_MAIN_LIST_ADMIN, required = false)
    public AdminListReq maintain_adminlist;

    @Element(name = TagFinal.MAINNEW_GET_TYPE, required = false)
    public BranchTypeReq getMaintainclass;

    @Element(name = TagFinal.MAINNEW_GET_OFICE, required = false)
    public OfficeReq officeReq;



    //----------------tea evaluate----------------------


    @Element(name = TagFinal.TEA_JUDGE_CLASS, required = false)
    public JudgeAddReq judgeReq;

    @Element(name = TagFinal.TEA_JUDGE_STATISTICS_CLASS, required = false)
    public JudgeTjReq judgetjReq;

    @Element(name = TagFinal.TEA_JUDGE_STATISTICS, required = false)
    public JudgeChartReq judgechartReq;

    @Element(name = TagFinal.TEA_JUDGE_YEAR, required = false)
    public YearReq year_Req;

    @Element(name = TagFinal.TEA_ADD_PARAMETER, required = false)
    public JudgeparaReq para_Req;


    @Element(name = TagFinal.TEA_JUDGE_INFO, required = false)
    public JudgeItemReq item_Req;

}
