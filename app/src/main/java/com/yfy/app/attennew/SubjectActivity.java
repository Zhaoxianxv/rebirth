package com.yfy.app.attennew;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yfy.app.attennew.adapter.SelectedSingeAdapter;
import com.yfy.app.attennew.bean.AttenRes;
import com.yfy.app.net.*;
import com.yfy.app.net.atten.AdminUserReq;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.ColorRgbUtil;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.rebirth.R;
import com.yfy.recycerview.RecycleViewDivider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;


public class SubjectActivity extends BaseActivity implements Callback<ResEnv> {

    private static final String TAG = SubjectActivity.class.getSimpleName();



    private SelectedSingeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        getUserList(true);
        initSQtoolbar();

        initRecycler();

    }
    private void initSQtoolbar() {
        assert toolbar!=null;
        toolbar.setTitle("选择审批人");
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView = (RecyclerView) findViewById(R.id.public_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.public_swip);
        //AppBarLayout 展开执行刷新
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(ColorRgbUtil.getBaseColor());
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                getUserList(false);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        xlist.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.Gray)));
        adapter=new SelectedSingeAdapter(mActivity);
        recyclerView.setAdapter(adapter);

    }

    public void closeSwipeRefresh(){
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 200);
        }
    }


    /**
     * ----------------------------retrofit-----------------------
     */



    public void getUserList(boolean is) {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        AdminUserReq request = new AdminUserReq();
        //获取参数

        reqBody.atten_get_admin= request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().atten_get_admin(reqEnvelop);
        call.enqueue(this);
        if (is)showProgressDialog("正在加载");

    }


    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        closeSwipeRefresh();
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
                Logger.e(TagFinal.ZXX, s);
            } catch (IOException e) {
                Logger.e(TagFinal.ZXX, "onResponse: IOException");
                e.printStackTrace();
            }
            toastShow("数据出差了");
        }
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;

            if (b.attendance_approveResponse!=null) {
                String result = b.attendance_approveResponse.attendance_approveResult;
                Logger.e(call.request().headers().toString()+result);
                AttenRes infor=gson.fromJson(result,AttenRes.class);
                if (StringJudge.isEmpty(infor.getUser_lists())){
                    toastShow("没有数据");
                }else{
                    adapter.setDataList(infor.getUser_lists());
                    adapter.setLoadState(2);
                }
            }

        }else{
            Logger.e(TagFinal.ZXX, "evn: null" );
        }
    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());
        toast(R.string.fail_do_not);
        dismissProgressDialog();
        closeSwipeRefresh();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
