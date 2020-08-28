package com.yfy.app.tea_evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yfy.app.tea_evaluate.adpter.EvaluateOneAdapter;
import com.yfy.app.tea_evaluate.bean.ItemBean;
import com.yfy.app.tea_evaluate.bean.ResultInfo;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.ColorRgbUtil;
import com.yfy.final_tag.ConvertObjtect;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;
import com.yfy.recycerview.EndlessRecyclerOnScrollListener;
import com.yfy.recycerview.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

public class ChartEvaluateActivity extends WcfActivity {

    private static final String TAG = ChartEvaluateActivity.class.getSimpleName();
    private int  page=0;
    private EvaluateOneAdapter adapter;
    private List<ItemBean> dataList=new ArrayList<>();
    private int year=0;
    private int id_=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        initSQToolbar();


        initRecycler();
        getData();
    }

    private void getData(){
        Intent intent=getIntent();
        String id=intent.getStringExtra(TagFinal.ID_TAG);
        String y=intent.getStringExtra("year");
        String title=intent.getStringExtra(TagFinal.TITLE_TAG);

        id_= ConvertObjtect.getInstance().getInt(id);
        year= ConvertObjtect.getInstance().getInt(y);
        assert toolbar!=null;
        toolbar.setTitle(title);
        Base.year=y;
        refresh(true, TagFinal.REFRESH);
    }
    private  void initSQToolbar(){
        assert toolbar!=null;
//        toolbar.setTitle("教师考评");

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
                refresh(false, TagFinal.REFRESH);
            }
        });
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {

                refresh(false,TagFinal.REFRESH_MORE);
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
        adapter=new EvaluateOneAdapter(this,dataList);
        recyclerView.setAdapter(adapter);

    }



    public void refresh(boolean is,String loadType){

        if (loadType.equals(TagFinal.REFRESH)){
            page=0;
        }else{
            page++;
        }

        Object[] parmas=new Object[]{
                Base.user.getSession_key(),
                year,//year
                id_,
                page,//page
                TagFinal.TEN_INT//size
        };

        ParamsTask grade= new ParamsTask(parmas, TagFinal.TEA_JUDGE_LIST,loadType);
        if (is) showProgressDialog("正在加载");
        execute(grade);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_CONTENT:
                    refresh(true,TagFinal.REFRESH);
                    break;
            }
        }
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity()) return false;
        dismissProgressDialog();
        closeSwipeRefresh();
        Logger.e("zxx", "onSuccess: "+result );
        String name=wcfTask.getName();

        if (name.equals(TagFinal.REFRESH)){
            ResultInfo info=gson.fromJson(result,ResultInfo.class);
            if (info.getJudge_record().size()<TagFinal.TEN_INT){
                toastShow("加载结束");
            }
            dataList=info.getJudge_record();
            adapter.setDataList(dataList);
            adapter.setLoadState(2);
            return false;
        }
        if (name.equals(TagFinal.REFRESH)){
            ResultInfo info=gson.fromJson(result,ResultInfo.class);
            if (info.getJudge_record().size()<TagFinal.TEN_INT){
                toastShow("加载结束");
            }
            dataList.addAll(info.getJudge_record());
            adapter.setDataList(dataList);
            adapter.setLoadState(2);
            return false;
        }

        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity()) return ;
        dismissProgressDialog();
        closeSwipeRefresh();
    }

    /**
     * --------------------retrofit-------------
     */


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
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
