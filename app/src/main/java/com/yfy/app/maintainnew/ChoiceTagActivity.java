package com.yfy.app.maintainnew;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yfy.app.maintainnew.adapter.SelectedSingeAdapter;
import com.yfy.app.maintainnew.bean.DepTag;
import com.yfy.app.maintainnew.bean.MainRes;
import com.yfy.app.net.*;
import com.yfy.app.net.maintain.BranchTypeReq;
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
import java.util.ArrayList;
import java.util.List;

public class ChoiceTagActivity extends BaseActivity implements Callback<ResEnv> {

    private static final String TAG = ChoiceTagActivity.class.getSimpleName();


    private SelectedSingeAdapter adapter;
    private List<DepTag> depTags=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        getShapeKind();
        initSQtoobar();
        initRecycler();

    }


    private void initSQtoobar() {
        assert toolbar!=null;
        toolbar.setTitle("选择部门");
    }






    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        swipeRefreshLayout =  findViewById(R.id.public_swip);
        //AppBarLayout 展开执行刷新
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(ColorRgbUtil.getBaseColor());
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                getShapeKind();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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


    public void getShapeKind()  {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        BranchTypeReq branchTypeReq = new BranchTypeReq();
        //获取参数

        reqBody.getMaintainclass = branchTypeReq;
        reqEnvelop.body = reqBody;

        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().getMaintainclass(reqEnvelop);
        call.enqueue(this);
        showProgressDialog("正在加载");

    }



    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
                Logger.e("zxx", s);
            } catch (IOException e) {
                Logger.e(TagFinal.ZXX, "onResponse: IOException");
                e.printStackTrace();
            }
            toastShow("数据出差了");
        }

        closeSwipeRefresh();
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.getMaintainclassResponse!=null) {
                String result = b.getMaintainclassResponse.getclassidResult;
                Logger.e(TagFinal.ZXX, "onResponse: "+result);
                MainRes infor=gson.fromJson(result, MainRes.class);
                if (StringJudge.isNotNull(infor)){
                    Logger.e(call.request().headers().toString()+result);
                    depTags=infor.getMaintainclass();
                    adapter.setDataList(depTags);
                    adapter.setLoadState(TagFinal.LOADING_COMPLETE);
                }
            }
        }else{
            Logger.e(TagFinal.ZXX, "evn: 22" );
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
        closeSwipeRefresh();
    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
