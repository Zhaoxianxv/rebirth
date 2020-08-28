package com.yfy.app.tea_evaluate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.yfy.app.net.*;
import com.yfy.app.net.tea_evaluate.JudgeAddReq;
import com.yfy.app.net.tea_evaluate.YearReq;
import com.yfy.app.tea_evaluate.adpter.EvaluateMainAdapter;
import com.yfy.app.tea_evaluate.bean.EvaluateMain;
import com.yfy.app.tea_evaluate.bean.ResultInfo;
import com.yfy.app.tea_evaluate.bean.YearData;
import com.yfy.base.Base;
import com.yfy.base.activity.BaseActivity;
import com.yfy.dialog.DialogTools;
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

public class TeaEvaluateActivity extends BaseActivity implements Callback<ResEnv> {
    private static final String TAG = TeaEvaluateActivity.class.getSimpleName();
    private EvaluateMainAdapter adapter;
    private List<EvaluateMain> dataList=new ArrayList<>();
    private int year=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        initSQToolbar();
        initRecycler();
        getYear();
    }

    private TextView menu;
    private  void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("教师考评");
        menu=toolbar.addMenuText(TagFinal.ONE_INT,"选择年份");
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

        recyclerView = findViewById(R.id.public_recycler);
        swipeRefreshLayout = findViewById(R.id.public_swip);
        //AppBarLayout 展开执行刷新
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(ColorRgbUtil.getBaseColor());
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                if (year==0){
                    closeSwipeRefresh();
                    return;
                }
                refresh(false, year);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.gray)));
        adapter=new EvaluateMainAdapter(this,dataList);
        recyclerView.setAdapter(adapter);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_CONTENT:
                    break;
                case TagFinal.UI_TAG:
                    YearData item=data.getParcelableExtra(TagFinal.OBJECT_TAG);
                    Base.year=item.getYear();
                    Base.year_name=item.getYearname();
                    menu.setText(item.getYearname());
                    year= ConvertObjtect.getInstance().getInt(Base.year);
                    refresh(true, year);
                    break;
            }
        }
    }


    /**
     * --------------------retrofit-------------
     */

    private void showDialog(){
        DialogTools.getInstance().showDialog(mActivity, "", "请选择年份", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent=new Intent(mActivity,TeaTagActivity.class);
                startActivityForResult(intent,TagFinal.UI_TAG);

            }
        });
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




    public void refresh(boolean is,int year){


        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        JudgeAddReq request = new JudgeAddReq();
        //获取参数

        request.setYear(year);

        reqBody.judgeReq = request;
        reqEnvelop.body = reqBody;

        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().teacher_judge_class(reqEnvelop);
        call.enqueue(this);
        if (is) showProgressDialog("正在加载");

    }

    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        closeSwipeRefresh();
        Logger.e("zxx", "onResponse: "+response.code());
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
            if (b.judge_Response != null) {
                String result = b.judge_Response.get_teacher_judge_classResult;
                ResultInfo info=gson.fromJson(result, ResultInfo.class);
                adapter.setDataList(info.getJudge_class());
                adapter.setLoadState(2);
                Logger.e("zxx", "tab: " + result);
            }
            if (b.year_Response != null) {
                String result = b.year_Response.year_Result;
                Logger.e("zxx", "tab: " + result);
                ResultInfo infor=gson.fromJson(result, ResultInfo.class);
                if (StringJudge.isNotNull(infor)){
                    for (YearData bean:infor.getJudge_year()){
                        if (bean.getIsnow().equals("是")){
                            Base.year=bean.getYear();
                            Base.year_name=bean.getYearname();
                            menu.setText(bean.getYearname());
                            year= ConvertObjtect.getInstance().getInt(Base.year);
                            refresh(true, year);
                            break;
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure"+call.request().headers().toString() );
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
        closeSwipeRefresh();
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
