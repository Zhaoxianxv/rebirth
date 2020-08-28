package com.yfy.app.attennew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.app.attennew.adapter.MaintainAdapter;
import com.yfy.app.attennew.bean.AttenBean;
import com.yfy.app.attennew.bean.AttenRes;
import com.yfy.app.net.*;
import com.yfy.app.net.atten.AttenCountReq;
import com.yfy.app.net.atten.AttenUserListReq;
import com.yfy.base.activity.WcfActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.rebirth.R;
import com.yfy.recycerview.EndlessRecyclerOnScrollListener;
import com.yfy.recycerview.RecycleViewDivider;
import com.yfy.view.SQToolBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttenNewActivity extends WcfActivity implements Callback<ResEnv> {

    private static final String TAG = AttenNewActivity.class.getSimpleName();


    private String mothed;
    private int page=0;
    private int type =0;
    private int state =0;
    private String dealstate;//user：0; admin:-1

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private MaintainAdapter adapter;
    private List<AttenBean> mainBeens=new ArrayList<>();

    @Bind(R.id.count)
    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atten_new_main);
        initSQToolbar();
        initToolbar();
        initCollapsing();
        initRecycler();
        initView();
        getCountNum();//attendance
        readNotice("attendance");
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity())return false;
        dismissProgressDialog();
        closeSwipeRefresh();
//        Logger.e( "onSuccess: "+StringUtils.string2Json(result) );
//        Logger.e( "onSuccess: "+result );
        String name=wcfTask.getName();
//        if (!StringJudge.isSuccess(gson,result))return false;
        if (name.equals(TagFinal.REFRESH)){
            mainBeens.clear();
            AttenRes infor=gson.fromJson(result,AttenRes.class);
            mainBeens=infor.getMaintains();
            adapter.setDataList(mainBeens);
            if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                toastShow(R.string.success_loadmore_end);
                adapter.setLoadState(3);
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

    }

    public void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("请假");
        toolbar.addMenuText(TagFinal.ONE_INT,"申请");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mActivity,AttenAddActivity.class);
                startActivityForResult(intent,TagFinal.UI_REFRESH);
            }
        });
        toolbar.setOnNaviClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    /**
     * Toolbar 的NavigationIcon大小控制mipmap
     */
    private Toolbar mToolbar;
    public void initToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.maintain_admin_top_text);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(null);  //隐藏掉返回键比如首页，可以调用
//        mToolbar.setNavigationOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onBackPressed();
//                    }
//                });
    }

    //配置CollapsingToolbarLayout布局
    public void initCollapsing() {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        mCollapsingToolbarLayout.setTitle(" ");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView = (RecyclerView) findViewById(R.id.maintiain_recycler);
        appBarLayout = (AppBarLayout) findViewById(R.id.maintain_appbar_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.maintain_new_swip);
        //AppBarLayout 展开执行刷新
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeRefreshLayout.setEnabled(true);
                    mToolbar.setAlpha(0);
                    mToolbar.setVisibility(View.GONE);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                    mToolbar.setAlpha(1);
                    mToolbar.setVisibility(View.VISIBLE);
                }
            }
        });
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                refresh(false,TagFinal.REFRESH);
                getCountNum();
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
        adapter=new MaintainAdapter(this,mainBeens);
        recyclerView.setAdapter(adapter);

    }


    public void initView(){
        dealstate="0";
        mothed=TagFinal.ATTENNEW_GET_MAIN_LIST_USER;
        if (UserPreferences.getInstance().getUserAdmin().getIsqjadmin().equals(TagFinal.TRUE)){
            appBarLayout.setVisibility(View.VISIBLE);
        }else{
            appBarLayout.setVisibility(View.GONE);
        }
        refresh(true,TagFinal.REFRESH);
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


    @OnClick(R.id.maintain_audit_do)
    void setAudit(){
        Intent intent=new Intent(mActivity,AttenNewAdminActivity.class);
        startActivityForResult(intent,TagFinal.UI_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_REFRESH:
                    refresh(false,TagFinal.REFRESH);
                    getCountNum();
                    break;
            }
        }
    }



    /**
     * --------------------------retrofit--------------------------
     */
    public void readNotice(String key) {
        RetrofitMothed.readRedNum(key,new Callback<ResEnv>() {
            @Override
            public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
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
                dismissProgressDialog();
                ResEnv respEnvelope = response.body();
                if (respEnvelope != null) {
                    ResBody b=respEnvelope.body;
                    if (b.readnoticeResponse!=null){
                        String result=b.readnoticeResponse.readnoticeResult;
                        Logger.e(call.request().headers().toString()+result);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResEnv> call, Throwable t) {
                Logger.e(call.request().headers().toString()+"failure");
            }
        });
    }

    public void getCountNum()  {
        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        AttenCountReq request = new AttenCountReq();
        //获取参数

        reqBody.count_atten = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().atten_count(reqEnvelop);
        call.enqueue(this);
//        Logger.e(reqEnvelop.toString());
    }



    /**
     * @param is 显示 true: showProgressDialog("正在登录"); false:null
     * @param loadType 上啦，下拉
     */
    public void refresh(boolean is,String loadType){
        if (loadType.equals(TagFinal.REFRESH)){
            page=0;
        }else{
            page++;
        }
//        Object[] params = new Object[] {
//            Base.user.getSession_key(),
//            type,
//            state ,
//            page,
//            TagFinal.TEN_INT
//        };
//        ParamsTask gettype = new ParamsTask(params, mothed, loadType);
//        execute(gettype);
        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        AttenUserListReq request = new AttenUserListReq();
        //获取参数
        request.setPage(page);

        reqBody.atten_user_list_body = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().atten_user_list(reqEnvelop);
        call.enqueue(this);
//        Logger.e(reqEnvelop.toString());
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
            if (b.get_attendance_review_countResponse!=null){
                String result=b.get_attendance_review_countResponse.get_attendance_review_countResult;
                Logger.e(call.request().headers().toString()+result );
                AttenRes infor=gson.fromJson(result,AttenRes.class);
                if (StringJudge.isEmpty(infor.getCount())){
                    count.setVisibility(View.GONE);
                }else{
                    if (infor.getCount().length()>2) count.setText("99");
                    count.setText(infor.getCount());
                }
            }
            if (b.atten_user_list_body!=null){
                String result=b.atten_user_list_body.result;
                Logger.e(call.request().headers().toString()+result );
                AttenRes infor=gson.fromJson(result,AttenRes.class);
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
                        adapter.setLoadState(3);
                    }else{
                        adapter.setLoadState(2);
                    }
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
        dismissProgressDialog();
        closeSwipeRefresh();
        toast(R.string.fail_do_not);
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }



}
