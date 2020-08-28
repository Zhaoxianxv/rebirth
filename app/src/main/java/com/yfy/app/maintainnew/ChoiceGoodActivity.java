package com.yfy.app.maintainnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.yfy.app.maintainnew.adapter.SelectedGoodAdapter;
import com.yfy.app.maintainnew.bean.GoodBean;
import com.yfy.app.maintainnew.bean.GoodsInfo;
import com.yfy.app.net.*;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.ColorRgbUtil;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.rebirth.R;
import com.yfy.recycerview.RecycleViewDivider;
import com.yfy.view.SQToolBar;

import java.util.ArrayList;
import java.util.List;

public class ChoiceGoodActivity extends BaseActivity {

    private static final String TAG = ChoiceGoodActivity.class.getSimpleName();


    private SelectedGoodAdapter adapter;
    private List<GoodBean> goods=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        initSQtoobar();
        initRecycler();
        getData();

    }


    private void initSQtoobar() {
        assert toolbar!=null;
        toolbar.setTitle("选择物品");
        toolbar.addMenuText(TagFinal.ONE_INT,R.string.ok);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {

                for (GoodBean b:adapter.getDataList()){
                    if (b.isSelect()){

                        Intent intent=new Intent();
                        intent.putExtra(TagFinal.ID_TAG,info.getId());
                        intent.putExtra(TagFinal.NAME_TAG,info.getOfficename());
                        intent.putExtra(TagFinal.OBJECT_TAG,b);
                        setResult(RESULT_OK,intent);
                        finish();
                        return;
                    }
                }
                toastShow("未选择物品");
            }
        });
    }


    private GoodsInfo info;
    private void getData(){
        info=getIntent().getParcelableExtra(TagFinal.OBJECT_TAG);
        if (StringJudge.isEmpty(info.getGoods())){
            toast("暂无数据");
        }else{
           adapter.setDataList(info.getGoods());
           adapter.setLoadState(TagFinal.LOADING_COMPLETE);
        }
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
                closeSwipeRefresh();
                // 刷新数据
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
        adapter=new SelectedGoodAdapter(mActivity);
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



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
