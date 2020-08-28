package com.yfy.app.tea_evaluate;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yfy.app.net.*;
import com.yfy.app.net.tea_evaluate.JudgeChartReq;
import com.yfy.app.tea_evaluate.adpter.BarAdapter;
import com.yfy.app.tea_evaluate.bean.BarItem;
import com.yfy.app.tea_evaluate.bean.ChartBean;
import com.yfy.app.tea_evaluate.bean.ChartInfo;
import com.yfy.app.tea_evaluate.bean.ResultInfo;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.rebirth.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarActivity extends BaseActivity implements Callback<ResEnv> {
    private static final String TAG = BarActivity.class.getSimpleName();
    private BarAdapter adapter;
    private List<BarItem> adapterData=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tea_bar);
        initRecycler();
        getData();

        getChartData(true, 0);
    }

    private String parent_id;
    private void getData(){
        parent_id=getIntent().getStringExtra(TagFinal.OBJECT_TAG);
        initSQToolbar(getIntent().getStringExtra(TagFinal.TITLE_TAG));

    }

//    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.public_swip);
        //AppBarLayout 展开执行刷新
        // 设置刷新控件颜色
//        swipeRefreshLayout.setColorSchemeColors(ColorRgbUtil.getBaseColor());
        // 设置下拉刷新
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
                // 刷新数据
//                getChartData(false,year );
//            }
//        });


        recyclerView = findViewById(R.id.tea_bar_graph);
        LinearLayoutManager ms= new LinearLayoutManager(mActivity);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(ms);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        adapter=new BarAdapter(mActivity);
        recyclerView.setAdapter(adapter);

    }



    private void initSQToolbar(String title){
        assert toolbar!=null;
        toolbar.setTitle(title);

    }





    private void setData(List<ChartInfo> chartinfo) {
        adapterData.clear();
        Collections.reverse(chartinfo);
        BarItem top=new BarItem();
        adapterData.add(top);
        for (ChartInfo info:chartinfo){
            BarItem item=new BarItem();

            boolean is=false;
            for (ChartBean bean:info.getInfo()){
               if (bean.getId().equals(parent_id)){
                   item.setMax_score(bean.getMax_score());
                   item.setScore(bean.getScore());
                   item.setMiddle_score(bean.getMiddle_score());
                   is=true;
               }
            }

            item.setYearname(info.getYearname());
            item.setYear(info.getYear());
            item.setId(parent_id);
            if (is){
                adapterData.add(item);
            }

        }

        adapter.setDataList(adapterData);
        adapter.notifyDataSetChanged();
//        if (adapterData.size()>3){
//            adapter.notifyItemChanged(0,adapterData.get(0));
//            adapter.notifyItemChanged(1,adapterData.get(1));
//            adapter.notifyItemChanged(2,adapterData.get(2));
//            adapter.notifyItemChanged(3,adapterData.get(3));
//        }else{
//            for (int i=0;i<adapterData.size();i++){
//                adapter.notifyItemChanged(i,adapterData.get(i));
//            }
//        }
//
//        recyclerView.scrollToPosition(adapterData.size());
//        recyclerView.scrollToPosition(0);
//        adapter.setLoadState(TagFinal.LOADING_COMPLETE);

    }

    /**
     * ----------------------------retrofit-----------------------
     */





    public void getChartData(boolean is,int year)  {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        JudgeChartReq request = new JudgeChartReq();
        //获取参数

        request.setYear(0);
        reqBody.judgechartReq = request;
        reqEnvelop.body = reqBody;

        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().judge_statistics(reqEnvelop);
        call.enqueue(this);
//        if (is)showProgressDialog("");

    }

    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        Logger.e("zxx", "onResponse: "+response.code());
        if (!isActivity())return;
        dismissProgressDialog();
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            toastShow("数据出差了");
        }
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b = respEnvelope.body;
            if (b.judge_chart_Response != null) {
                String result = b.judge_chart_Response.judge_statisticsResult;
                Logger.e("judge_chart_Response: " + result);
                ResultInfo info=gson.fromJson(result, ResultInfo.class);
                List<ChartInfo> chartinfo=info.getJudge_statistics();
                if (StringJudge.isEmpty(chartinfo)){
                    toastShow(R.string.not_have_data);
                }else{
                    setData(chartinfo);
                }

            }
        }
    }
    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        dismissProgressDialog();
        Logger.e("onFailure "+call.request().headers().toString());
        toastShow(R.string.fail_do_not);
    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }



}
