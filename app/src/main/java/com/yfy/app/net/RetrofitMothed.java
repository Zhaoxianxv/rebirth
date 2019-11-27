package com.yfy.app.net;

import com.yfy.app.net.user.ReadNoticeReq;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by yfyandr on 2018/6/1.
 */

public class RetrofitMothed {

    public static void readRedNum(String key, Callback<ResEnv> callback){

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        ReadNoticeReq request = new ReadNoticeReq();
        //获取参数
        request.type=key;
        reqBody.readnotice = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().read_notice(reqEnvelop);
        call.enqueue(callback);
    }

}
