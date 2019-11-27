package com.yfy.app.net;



import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 接口请求
 */
public interface InterfaceApi {


    @Headers({Base.Content_Type, "SOAPAction: http://tempuri.org/Service2/login"})
    @POST("/Service2.svc")
    Call<ResEnv> login(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.GET_CURRENT_TERM})
    @POST(Base.POST_URI)
    Call<ResEnv> get_current_term(@Body ReqEnv Envelope);



    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.GETNOTICENUM})
    @POST(Base.POST_URI)
    Call<ResEnv> getnoticenum(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.NEWS_MENU})
    @POST(Base.POST_URI)
    Call<ResEnv> news_menu(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.SCHOOL_NEWS_BANNER})
    @POST(Base.POST_URI)
    Call<ResEnv> news_banner(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.GET_USER_ADMIN})
    @POST(Base.POST_URI)
    Call<ResEnv> get_user_right(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.READNOTICE})
    @POST(Base.POST_URI)
    Call<ResEnv> read_notice(@Body ReqEnv Envelope);



    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_GET_MOBILE})
    @POST(Base.POST_URI)
    Call<ResEnv> call_phone(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.RESET_CODE})
    @POST(Base.POST_URI)
    Call<ResEnv> get_phone_code(@Body ReqEnv Envelope);

    /**
     * -------------------box-----------------------
     */

//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.BOX_GET_LEADER_LIST})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> box_leaders(@Body ReqEnv Envelope);
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.BOX_GET_COUNT_LEADER})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> box_c_leader(@Body ReqEnv Envelope);
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.BOX_GET_COUNT_USER})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> box_c_user(@Body ReqEnv Envelope);
    /**
     * --------------------------------maintain----------------------------------
     */

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINNEW_GET_COUNT})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_count(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINNEW_GET_MAIN_LIST_USER})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_user(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINNEW_GET_MAIN_LIST_ADMIN})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_admin(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+ TagFinal.MAINNEW_GET_TYPE})
    @POST(Base.POST_URI)
    Call<ResEnv> getMaintainclass(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+ TagFinal.MAINNEW_GET_OFICE})
    @POST(Base.POST_URI)
    Call<ResEnv> get_office(@Body ReqEnv Envelope);
//
    /**
     *----------------------atten-----------------------
     */

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTENNEW_GET_MAIN_LIST_USER})
    @POST(Base.POST_URI)
    Call<ResEnv> atten_user_list(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTENNEW_GET_MAIN_LIST_ADMIN})
    @POST(Base.POST_URI)
    Call<ResEnv> atten_admin_list(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTENNEW_ADMIN_COUNT})
    @POST(Base.POST_URI)
    Call<ResEnv> atten_count(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTENNEW_TYPE})
    @POST(Base.POST_URI)
    Call<ResEnv> atten_type(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTENNEW_USER_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> atten_get_admin(@Body ReqEnv Envelope);

//
//
//    /**
//     * ---------------------notice--------------------
//     */
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.NOTICE_GET_CONTENT})
//    @POST(TagFinal.POST_URI)
//    Call<ResNoticeEnv> notice_get_detail(@Body ReqNoticeEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.NOTICE_READ})
//    @POST(TagFinal.POST_URI)
//    Call<ResNoticeEnv> notice_read(@Body ReqNoticeEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.NOTICE_GET_READSTATE})
//    @POST(TagFinal.POST_URI)
//    Call<ResNoticeEnv> read_state(@Body ReqNoticeEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.NOTICE_GET_TEA})
//    @POST(TagFinal.POST_URI)
//    Call<ResNoticeEnv> notice_get_tea(@Body ReqNoticeEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.NOTICE_GET_STU})
//    @POST(TagFinal.POST_URI)
//    Call<ResNoticeEnv> notice_get_stu(@Body ReqNoticeEnv Envelope);

//    /**
//     *----------------------order-----------------------
//     */
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.READNOTICE})
//    @POST(TagFinal.POST_URI)
//    Call<ResOrderEnv> read_notice_order(@Body ReqOrderEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.ORDER_GET_ROOM_NAME})
//    @POST(TagFinal.POST_URI)
//    Call<ResOrderEnv> get_funcRoom_name(@Body ReqOrderEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.ORDER_QUERY})
//    @POST(TagFinal.POST_URI)
//    Call<ResOrderEnv> query_funcRoom(@Body ReqOrderEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.Order_User_Detsail})
//    @POST(TagFinal.POST_URI)
//    Call<ResOrderEnv> my_funcRoom(@Body ReqOrderEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.ORDER_GET_DETAIL})
//    @POST(TagFinal.POST_URI)
//    Call<ResOrderEnv> get_funcRoom_detail(@Body ReqOrderEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.ORDER_GET_GRADE})
//    @POST(TagFinal.POST_URI)
//    Call<ResOrderEnv> get_funcRoom_type(@Body ReqOrderEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.ORDER_GET_COUNT})
//    @POST(TagFinal.POST_URI)
//    Call<ResOrderEnv> review_funcRoom_count(@Body ReqOrderEnv Envelope);
//
//
//    /**
//     * -----------------------moral-------------------
//     */
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.CONVENTION_GET_TIME})
//    @POST(TagFinal.POST_URI)
//    Call<ResMoralEnv> moral_time(@Body ReqMoralEnv Envelope);

    /**
     * -----------------------tea评测--------------------------
     */
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.TEA_JUDGE_CLASS})
//    @POST(TagFinal.POST_URI)
//    Call<ResTeaEnv> teacher_judge_class(@Body ReqTeaEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.TEA_JUDGE_STATISTICS_CLASS})
//    @POST(TagFinal.POST_URI)
//    Call<ResTeaEnv> judge_tj_class(@Body ReqTeaEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.TEA_JUDGE_STATISTICS})
//    @POST(TagFinal.POST_URI)
//    Call<ResTeaEnv> judge_statistics(@Body ReqTeaEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.TEA_JUDGE_YEAR})
//    @POST(TagFinal.POST_URI)
//    Call<ResTeaEnv> judge_year(@Body ReqTeaEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.TEA_ADD_PARAMETER})
//    @POST(TagFinal.POST_URI)
//    Call<ResTeaEnv> judge_item(@Body ReqTeaEnv Envelope);
//
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.TEA_JUDGE_INFO})
//    @POST(TagFinal.POST_URI)
//    Call<ResTeaEnv> item_info(@Body ReqTeaEnv Envelope);
//
//
//    /**
//     * ---------------------applied---------------------
//     */
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.APPLIED_ADMIN_LIST})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> applied_admin_list(@Body ReqEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.APPLIED_USER_LIST})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> applied_user_list(@Body ReqEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.APPLIED_GET_DETAIL})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> applied_item_detail(@Body ReqEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.APPLIED_GET_PROJEDCT_STATE})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> appliedGetList(@Body ReqEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.NOTICE_GET_TEA})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> notice_get_tea(@Body ReqEnv Envelope);
//
//    @Headers({TagFinal.Content_Type, TagFinal.SOAP_ACTION+TagFinal.NOTICE_GET_STU})
//    @POST(TagFinal.POST_URI)
//    Call<ResEnv> notice_get_stu(@Body ReqEnv Envelope);

}
