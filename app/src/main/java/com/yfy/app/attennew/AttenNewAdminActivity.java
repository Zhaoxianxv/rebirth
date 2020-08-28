package com.yfy.app.attennew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.yfy.app.attennew.adapter.MaintainAdminAdapter;
import com.yfy.app.attennew.bean.AttenBean;
import com.yfy.app.attennew.bean.AttenRes;
import com.yfy.app.net.*;
import com.yfy.app.net.atten.AttenAdminListReq;
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

public class AttenNewAdminActivity extends WcfActivity implements Callback<ResEnv> {

    private static final String TAG = AttenNewAdminActivity.class.getSimpleName();
    private int page=0;
    private MaintainAdminAdapter adapter;
    private List<AttenBean> mainBeens=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        initSQToolbar();
        initRecycler();
        initView();
    }


    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity())return false;
        dismissProgressDialog();
        closeSwipeRefresh();
        Logger.e( "onSuccess: "+result );
        String name=wcfTask.getName();
        if (!StringJudge.isSuccess(gson,result))return false;
        if (name.equals(TagFinal.REFRESH)){
            mainBeens.clear();
            AttenRes infor=gson.fromJson(result,AttenRes.class);
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
            AttenRes infor=gson.fromJson(result,AttenRes.class);
            if (infor.getMaintains().size()==TagFinal.ZERO_INT)return false;
            mainBeens.addAll(infor.getMaintains());
            adapter.setDataList(mainBeens);
            if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                toastShow(R.string.success_loadmore_end);
                adapter.setLoadState(3);
            }else{
                adapter.setLoadState(2);
            }
            return false;
        }

        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return;
        dismissProgressDialog();
        closeSwipeRefresh();
        toastShow(R.string.fail_do_not);

    }
    public void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("请假审批");
        toolbar.setOnNaviClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView = (RecyclerView) findViewById(R.id.public_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.public_swip);
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
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
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
        refresh(true,TagFinal.REFRESH);
    }



    private void refresh(boolean is,String type_load){
        if (type_load.equals(TagFinal.REFRESH)){
            page=0;
        }     else{
            page++;
        }
//        Object[] params = new Object[] {
//                Base.user.getSession_key(),
//                0,
//                "",
//                0,
//                0,
//                page,
//                TagFinal.TEN_INT
//        };
//        WcfTask num = new ParamsTask(params, TagFinal.ATTENNEW_GET_MAIN_LIST_ADMIN,type_load);
//        if (is){
//            showProgressDialog("");
//        }
//        execute(num);
        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        AttenAdminListReq request = new AttenAdminListReq();
        //获取参数
        request.setPage(page);

        reqBody.atten_admin_list_body = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().atten_admin_list(reqEnvelop);
        call.enqueue(this);
//        Logger.e(reqEnvelop.toString());
        if (is) showProgressDialog("");
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
            toast("数据出差了");
        }
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;

            if (b.atten_admin_list_body!=null){
                String result=b.atten_admin_list_body.result;
                Logger.e(call.request().headers().toString()+result );
                AttenRes infor=gson.fromJson(result,AttenRes.class);
                if (page==0){
                    mainBeens.clear();
                    mainBeens=infor.getMaintains();
                    adapter.setDataList(mainBeens);
                    if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                        adapter.setLoadState(3);
                        toastShow(R.string.success_loadmore_end);
                    }else{
                        adapter.setLoadState(2);
                    }
                }else{
                    if (infor.getMaintains().size()==TagFinal.ZERO_INT)return ;
                    mainBeens.addAll(infor.getMaintains());
                    adapter.setDataList(mainBeens);
                    if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                        toastShow(R.string.success_loadmore_end);
                        adapter.setLoadState(3);
                    }else{
                        adapter.setLoadState(2);
                    }
                }

            }
        }else{
            Logger.e(TagFinal.ZXX, "onResponse: 22" );
        }
    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
        closeSwipeRefresh();
        toast(R.string.fail_do_not);
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
