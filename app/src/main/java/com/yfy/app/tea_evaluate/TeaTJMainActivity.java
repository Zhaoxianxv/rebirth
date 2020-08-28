package com.yfy.app.tea_evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.yfy.app.net.*;
import com.yfy.app.net.tea_evaluate.JudgeChartReq;
import com.yfy.app.net.tea_evaluate.YearReq;
import com.yfy.app.tea_evaluate.adpter.TeaTJMainAdapter;
import com.yfy.app.tea_evaluate.bean.ChartBean;
import com.yfy.app.tea_evaluate.bean.ChartInfo;
import com.yfy.app.tea_evaluate.bean.ResultInfo;
import com.yfy.app.tea_evaluate.bean.YearData;
import com.yfy.base.Base;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.*;
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

public class TeaTJMainActivity extends BaseActivity implements Callback<ResEnv> {
    private static final String TAG = TeaTJMainActivity.class.getSimpleName();
    private int year=0;
    private TeaTJMainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_swip_recycler);
        initSQToolbar();
        initRecycler();
        getYear();
    }

    private TextView oneMenu;
    private void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("教师个人成果");
        oneMenu=toolbar.addMenuText(TagFinal.ONE_INT,"" );
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mActivity,TeaTagActivity.class);
                startActivityForResult(intent,TagFinal.UI_TAG);
            }
        });
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.public_swip);
        //AppBarLayout 展开执行刷新
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(ColorRgbUtil.getBaseColor());
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                getChartData(false,year );
            }
        });


        recyclerView = findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.gray)));
        adapter=new TeaTJMainAdapter(mActivity);
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                    getChartData(false, year);
                    break;
                case TagFinal.UI_TAG:
                    YearData item=data.getParcelableExtra(TagFinal.OBJECT_TAG);
                    Base.year=item.getYear();
                    oneMenu.setText(item.getYearname());
                    year= ConvertObjtect.getInstance().getInt(Base.year);
                    getChartData(false, year);
                    break;
            }
        }
    }

    /**
     * ----------------------------retrofit-----------------------
     */


    public void getYear()  {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        YearReq request = new YearReq();
        //获取参数

        reqBody.year_Req = request;
        reqEnvelop.body= reqBody;

        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().judge_year(reqEnvelop);
        call.enqueue(this);
//        showProgressDialog("正在加载");

    }



    public void getChartData(boolean is,int year)  {

        if (year==0){
            toastShow(R.string.please_choose_time);
            closeSwipeRefresh();
            return;
        }
        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        JudgeChartReq request = new JudgeChartReq();
        //获取参数

        request.setYear(year);
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
        closeSwipeRefresh();
        dismissProgressDialog();
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
                Logger.e("zxx", s);
            } catch (IOException e) {
                Logger.e("zxx", "onResponse: IOException");
                e.printStackTrace();
            }
            toastShow("数据出差了");
        }
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b = respEnvelope.body;
            if (b.year_Response != null) {
                String result = b.year_Response.year_Result;
                Logger.e( "tab: " + result);
                ResultInfo infor=gson.fromJson(result, ResultInfo.class);
                if (StringJudge.isNotNull(infor)){
                    for (YearData bean:infor.getJudge_year()){
                        if (bean.getIsnow().equals("是")){
                            Base.year=bean.getYear();
                            Base.year_name=bean.getYearname();
                            oneMenu.setText(bean.getYearname());
                            year= ConvertObjtect.getInstance().getInt(Base.year);
                            getChartData(true, year);
                            break;
                        }
                    }

                }
            }
            if (b.judge_chart_Response != null) {
                String result = b.judge_chart_Response.judge_statisticsResult;
                Logger.e("judge_chart_Response: " + result);
                ResultInfo info=gson.fromJson(result, ResultInfo.class);
                List<ChartInfo> chartinfo=info.getJudge_statistics();
                if (StringJudge.isEmpty(chartinfo)){
                    toastShow(R.string.not_have_data);
                }else{
                    initSetData(chartinfo.get(0).getInfo());
                }

            }
        }
    }

    private List<ChartBean> adapterData=new ArrayList<>();
    private void initSetData(List<ChartBean> list){
        adapterData.clear();
//        ChartBean chartBean =new ChartBean();
//        chartBean.setView_type(TagFinal.TYPE_TOP);
//        adapterData.add(chartBean);
        for (ChartBean bean:list){
            bean.setView_type(TagFinal.TYPE_ITEM);
            adapterData.add(bean);
        }
        adapter.setDataList(adapterData);
        adapter.setLoadState(TagFinal.LOADING_COMPLETE);
    }
    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        closeSwipeRefresh();
        Logger.e("onFailure "+call.request().headers().toString());
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
