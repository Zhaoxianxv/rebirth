package com.yfy.app.maintainnew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yfy.app.maintainnew.adapter.MaintainAdminAdapter;
import com.yfy.app.maintainnew.bean.MainBean;
import com.yfy.app.maintainnew.bean.MainRes;
import com.yfy.app.net.*;
import com.yfy.app.net.maintain.AdminListReq;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.rebirth.R;
import com.yfy.recycerview.EndlessRecyclerOnScrollListener;
import com.yfy.recycerview.RecycleViewDivider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaintainNewAdminActivity extends WcfActivity implements Callback<ResEnv> {

    private static final String TAG = MaintainNewAdminActivity.class.getSimpleName();


    private String mothed;
    private int page=0;
    private String dealstate;//user：0; admin:-1

    private MaintainAdminAdapter adapter;
    private List<MainBean> mainBeens=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        initSQToolbar();
        initRecycler();
        initView();
    }

    @Override
    public void onPageBack() {
        setResult(RESULT_OK);
        super.onPageBack();
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity())return false;
        closeSwipeRefresh();
        dismissProgressDialog();
        Logger.e( "onSuccess: "+result );
        String name=wcfTask.getName();
        if (!StringJudge.isSuccess(gson,result))return false;
        if (name.equals(TagFinal.REFRESH)){
            mainBeens.clear();
            MainRes infor=gson.fromJson(result,MainRes.class);
            mainBeens=infor.getMaintains();
            adapter.setDataList(mainBeens);
            if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                adapter.setLoadState(3);
                toastShow(R.string.success_loadmore_end);
            }else{
                adapter.setLoadState(2);
            }
            return false;
        }
        if (name.equals(TagFinal.REFRESH_MORE)){
            MainRes infor=gson.fromJson(result,MainRes.class);
            if (infor.getMaintains().size()==TagFinal.ZERO_INT)return false;
            mainBeens.addAll(infor.getMaintains());
            adapter.setDataList(mainBeens);
            if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                toastShow(R.string.success_loadmore_end);
                adapter.setLoadState(TagFinal.LOADING_END);
            }else{
                adapter.setLoadState(TagFinal.LOADING_COMPLETE);
            }
            return false;
        }

        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return;
        closeSwipeRefresh();
        dismissProgressDialog();
        toast(R.string.fail_do_not);

    }
    public void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle(R.string.maintain_do);
    }



    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView = (RecyclerView) findViewById(R.id.public_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.public_swip);
        //AppBarLayout 展开执行刷新
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                refresh(false,TagFinal.REFRESH);
            }
        });
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(TagFinal.LOADING);
                refresh(false,TagFinal.REFRESH_MORE);
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
        adapter=new MaintainAdminAdapter(mActivity,mainBeens);
        recyclerView.setAdapter(adapter);

    }


    public void initView(){
        dealstate="-1";
        mothed=TagFinal.MAINNEW_GET_MAIN_LIST_ADMIN;
        refresh(true,TagFinal.REFRESH);
    }


//
//    private void refresh(boolean is,String laod_type){
//        if (laod_type.equals(TagFinal.REFRESH)){
//            page=0;
//        }else{
//            page++;
//        }
//        Object[] params = new Object[] {
//                Base.user.getSession_key(),
//                dealstate,
//                page,
//                TagFinal.TEN_INT
//        };
//        WcfTask num = new ParamsTask(params, mothed,laod_type);
//        if (is)showProgressDialog("");
//        execute(num);
//    }


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_REFRESH:
                    refresh(false,TagFinal.REFRESH);
                    break;
            }
        }
    }






    public void refresh(boolean is,String loadType){
        if (loadType.equals(TagFinal.REFRESH)){
            page=0;
        }else{
            page++;
        }
//        Object[] params = new Object[] {
//            Base.user.getSession_key(),
//            dealstate,
//            page,
//            TagFinal.TEN_INT
//        };
//        ParamsTask gettype = new ParamsTask(params, mothed, loadType);
//        execute(gettype);

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        AdminListReq request = new AdminListReq();
        //获取参数
        request.setPage(page);

        reqBody.maintain_adminlist = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().maintain_admin(reqEnvelop);
        call.enqueue(this);
        if (is) showProgressDialog("");
    }






    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        closeSwipeRefresh();
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
                Logger.e( s);
            } catch (IOException e) {
                Logger.e( "onResponse: IOException");
                e.printStackTrace();
            }
            toast("数据出差了");
        }
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.maintain_admin_body!=null){
                String result=b.maintain_admin_body.result;
                Logger.e(call.request().headers().toString()+result);
                MainRes infor=gson.fromJson(result,MainRes.class);
                if (page==0){
                    mainBeens.clear();

                    mainBeens=infor.getMaintains();
                    adapter.setDataList(mainBeens);
                    if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                        toastShow(R.string.success_loadmore_end);
                        adapter.setLoadState(3);
                    }else{
                        adapter.setLoadState(2);
                    }
                }else{
                    if (infor.getMaintains().size()==TagFinal.ZERO_INT)return ;
                    mainBeens.addAll(infor.getMaintains());
                    adapter.setDataList(mainBeens);
                    if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                        toastShow(R.string.success_loadmore_end);
                        adapter.setLoadState(2);
                    }else{
                        adapter.setLoadState(2);
                    }
                }
            }
        }else{
            Logger.e( "respEnvelope=null" );
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
