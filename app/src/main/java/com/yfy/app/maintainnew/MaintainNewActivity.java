package com.yfy.app.maintainnew;

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
import com.yfy.app.maintainnew.adapter.MaintainAdapter;
import com.yfy.app.maintainnew.bean.MainBean;
import com.yfy.app.maintainnew.bean.MainRes;
import com.yfy.app.net.*;
import com.yfy.app.net.maintain.MaintainCountReq;
import com.yfy.app.net.maintain.MaintainListReq;
import com.yfy.app.net.user.ReadNoticeReq;
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

public class MaintainNewActivity extends WcfActivity implements Callback<ResEnv> {

    private static final String TAG = MaintainNewActivity.class.getSimpleName();


    private String mothed;
    private int page=0;
    private String dealstate;//user：0; admin:-1

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private MaintainAdapter adapter;
    private List<MainBean> mainBeens=new ArrayList<>();

    @Bind(R.id.count)
    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_new_main);
        initSQToolbar();
        initToolbar();
        initCollapsing();
        initRecycler();
        initView();
        getCountNum();
        readRedNum("maintain");
    }


    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity())return false;
        dismissProgressDialog();
        closeSwipeRefresh();
        Logger.e( "onSuccess: "+result );
        String name=wcfTask.getName();

//        if (!StringJudge.isSuccess(gson,result))return false;
        if (name.equals(TagFinal.REFRESH)){
            mainBeens.clear();
            MainRes infor=gson.fromJson(result,MainRes.class);
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
            MainRes infor=gson.fromJson(result,MainRes.class);
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
        toolbar.setTitle(R.string.maintain);
        toolbar.addMenuText(TagFinal.ONE_INT,R.string.apply_maintain);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mActivity,MaintainNewAddActivity.class);
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
        //		getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用，
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
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        mCollapsingToolbarLayout.setTitle(" ");
        //通过CollapsingToolbarLayout修改字体颜色
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
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                refresh(true,TagFinal.REFRESH_MORE);
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
        mothed=TagFinal.MAINNEW_GET_MAIN_LIST_USER;
        if (UserPreferences.getInstance().getUserAdmin().getIshqadmin().equals(TagFinal.TRUE)){
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
        Intent intent=new Intent(mActivity,MaintainNewAdminActivity.class);
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
     * ----------------------------retrofit-----------------------
     */


    public void readRedNum(String key){

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        ReadNoticeReq request = new ReadNoticeReq();
        //获取参数
        request.type=key;
        reqBody.readnotice = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().read_notice(reqEnvelop);
        call.enqueue(this);
    }



    public void getCountNum()  {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        MaintainCountReq request = new MaintainCountReq();
        //获取参数

        reqBody.count_maintain = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().maintain_count(reqEnvelop);
        call.enqueue(this);

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
        MaintainListReq request = new MaintainListReq();
        //获取参数
        request.setPage(page);

        reqBody.maintain_userlist = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().maintain_user(reqEnvelop);
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
            if (b.get_maintain_review_countResponse!=null){
                String result=b.get_maintain_review_countResponse.get_maintain_review_countResult;
                Logger.e(call.request().headers().toString()+result);
                MainRes infor=gson.fromJson(result,MainRes.class);
                if (StringJudge.isEmpty(infor.getCount())){
                    count.setVisibility(View.GONE);
                }else{
                    if (infor.getCount().length()>2) count.setText("99");
                    count.setText(infor.getCount());
                }
            }
            if (b.get_Maintain_userResponse!=null){
                String result=b.get_Maintain_userResponse.get_Maintain_userResult;
                Logger.e(call.request().headers().toString()+result);
                if (page==0){
                    mainBeens.clear();
                    MainRes infor=gson.fromJson(result,MainRes.class);
                    mainBeens=infor.getMaintains();
                    adapter.setDataList(mainBeens);
                    if (infor.getMaintains().size()!=TagFinal.TEN_INT){
                        toastShow(R.string.success_loadmore_end);
                        adapter.setLoadState(3);
                    }else{
                        adapter.setLoadState(2);
                    }
                }else{
                    MainRes infor=gson.fromJson(result,MainRes.class);
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
            if (b.readnoticeResponse!=null){
                String result=b.readnoticeResponse.readnoticeResult;
                Logger.e(call.request().headers().toString()+result);
            }
        }else{
            Logger.e( "respEnvelope=null" );
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
