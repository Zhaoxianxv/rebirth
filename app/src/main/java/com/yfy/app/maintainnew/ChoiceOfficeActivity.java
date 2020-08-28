package com.yfy.app.maintainnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.yfy.app.maintainnew.adapter.SelectedOfficeAdapter;
import com.yfy.app.maintainnew.bean.GoodBean;
import com.yfy.app.maintainnew.bean.GoodDep;
import com.yfy.app.maintainnew.bean.GoodsInfo;
import com.yfy.app.maintainnew.bean.MainRes;
import com.yfy.app.net.*;
import com.yfy.app.net.maintain.OfficeReq;
import com.yfy.base.activity.BaseActivity;
import com.yfy.dialog.CPWListView;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.ColorRgbUtil;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.rebirth.R;
import com.yfy.recycerview.RecycleViewDivider;
import com.yfy.view.SQToolBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChoiceOfficeActivity extends BaseActivity implements Callback<ResEnv> {

    private static final String TAG = ChoiceOfficeActivity.class.getSimpleName();


    private SelectedOfficeAdapter adapter;
    private List<GoodBean> goods=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        getShapeKind();
        initSQtoobar();

        initDialog();
        initRecycler();

    }


    private void initSQtoobar() {
        assert toolbar!=null;
        toolbar.setTitle("选择地点");
        toolbar.addMenuText(TagFinal.ONE_INT, "选择校区");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                initData();
            }
        });


    }

    private List<GoodDep> school_dep=new ArrayList<>();
    private List<String> school_name=new ArrayList<>();
    private CPWListView cpwListView;
    private void initData(){
        if (StringJudge.isEmpty(school_dep)){
            toast(R.string.success_not_details);
            return;
        }else{
            school_name.clear();
            for (GoodDep dep:school_dep){
                school_name.add(dep.getSchoolname());
            }
        }
        adapter.setDataList(new ArrayList<GoodsInfo>());
        adapter.setLoadState(TagFinal.LOADING_COMPLETE);
        cpwListView.setDatas(school_name);
        cpwListView.showAtCenter();

    }

    private void initDialog(){
        cpwListView = new CPWListView(mActivity);
        cpwListView.setOnPopClickListenner(new CPWListView.OnPopClickListenner() {
            @Override
            public void onClick(int index) {

                Logger.e("   "+index);
                adapter.setDataList(school_dep.get(index).getOffices());
                adapter.setLoadState(TagFinal.LOADING_COMPLETE);
                cpwListView.dismiss();
            }
        });

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
        adapter=new SelectedOfficeAdapter(mActivity);
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
        OfficeReq officeReq = new OfficeReq();
        //获取参数

        reqBody.officeReq = officeReq;
        reqEnvelop.body = reqBody;

        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().get_office(reqEnvelop);
        call.enqueue(this);
        showProgressDialog("正在加载");

    }


    private void initAdapter(GoodDep infor){

        List<GoodsInfo> infos=infor.getOffices();
        for (GoodsInfo info:infos){
            GoodBean parent=new GoodBean();
            parent.setView_type(TagFinal.TYPE_PARENT);
            parent.setOfficename(info.getOfficename());
            parent.setId(info.getId());
            goods.add(parent);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_TAG:
                    setResult(RESULT_OK,data);
                    finish();
                    break;
            }
        }
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
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow("数据出差了");
        }

        closeSwipeRefresh();
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.officeRes!=null) {
                String result = b.officeRes.officeResult;
                Logger.e(call.request().headers().toString()+result);
                MainRes res=gson.fromJson(result, MainRes.class);
                if (StringJudge.isNotNull(res)){
                    school_dep=res.getSchools();
                    initData();
                }
            }
        }else{
            Logger.e("evn: null" );
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
