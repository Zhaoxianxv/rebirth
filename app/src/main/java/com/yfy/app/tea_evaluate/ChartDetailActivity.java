package com.yfy.app.tea_evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.app.tea_evaluate.adpter.DetailAdapter;
import com.yfy.app.tea_evaluate.bean.ParamBean;
import com.yfy.app.tea_evaluate.bean.ResultJudge;
import com.yfy.app.tea_evaluate.bean.TeaDetail;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.*;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;
import com.yfy.view.SQToolBar;

import java.util.ArrayList;
import java.util.List;


public class ChartDetailActivity extends WcfActivity {

    private static final String TAG = ChartDetailActivity.class.getSimpleName();
    private  int idi;
    private DetailAdapter adapter;
    private List<TeaDetail> lis=new ArrayList<>();
    private String isSubmit="";
    @Bind(R.id.public_deleted_text)
    TextView delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_delete_main);
        delete.setVisibility(View.GONE);
        delete.setTextColor(ColorRgbUtil.getBaseColor());
        initRecycler();
        getData();
    }

    private TextView menu;
    private void initSQToolbar(String title){
        assert toolbar!=null;
        toolbar.setTitle(title);
        menu=toolbar.addMenuText(TagFinal.ONE_INT,"编辑");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mActivity,RedactActivity.class);
                intent.putExtra(TagFinal.ID_TAG,idi);
                startActivityForResult(intent,TagFinal.UI_REFRESH);
            }
        });
//        menu.setVisibility(View.GONE);
    }

    private boolean isShow=false;

    private void getData(){

        String id=getIntent().getStringExtra(TagFinal.ID_TAG);
        isShow=getIntent().getBooleanExtra(TagFinal.TYPE_TAG,false);
        String title=getIntent().getStringExtra("title");
        idi = ConvertObjtect.getInstance().getInt(id);
        getInfoData(true,idi);
        title=title.split(" ")[0];
        initSQToolbar(title);
    }



    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView = findViewById(R.id.public_delete_recycler);
        swipeRefreshLayout = findViewById(R.id.public_deleted_swipe);
        //AppBarLayout 展开执行刷新
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(ColorRgbUtil.getBaseColor());
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                getInfoData(false,idi);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线

        adapter=new DetailAdapter(this,lis);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.public_deleted_text)
    void setDelete(){
         deleteItem(idi);
    }


    public void deleteItem(int id_){


        Object[] parmas=new Object[]{
                Base.user.getSession_key(),
                id_
        };

        ParamsTask grade= new ParamsTask(parmas, TagFinal.TEA_DELETE_JUDGE,TagFinal.TEA_DELETE_JUDGE);
        execute(grade);
        showProgressDialog("正在删除");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_REFRESH:
                    getInfoData(false,idi);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ----------------------------retrofit-----------------------
     */
    public void getInfoData(boolean is,int id_){


        Object[] parmas=new Object[]{
                Base.user.getSession_key(),
                id_
        };

        ParamsTask grade= new ParamsTask(parmas, TagFinal.TEA_JUDGE_INFO,TagFinal.TEA_JUDGE_INFO);
        execute(grade);
        if (is)showProgressDialog("正在加载");
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
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Logger.e("zxx", "onSuccess: "+result );
        if (!isActivity())return false;
        dismissProgressDialog();
        closeSwipeRefresh();
        String name=wcfTask.getName();
        if (name.equals(TagFinal.TEA_JUDGE_INFO)){
            ResultJudge info=gson.fromJson(result, ResultJudge.class);
            if (info.getResult().equals(TagFinal.TRUE)){
                initData(info);
            }
            return false;
        }
        if (name.equals(TagFinal.TEA_DELETE_JUDGE)){
            ResultJudge info=gson.fromJson(result, ResultJudge.class);
            if (info.getResult().equals(TagFinal.TRUE)){
                setResult(RESULT_OK);
                onPageBack();

            }else{
                toastShow(info.getError_code());
            }
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return ;
        dismissProgressDialog();
        closeSwipeRefresh();
    }

    private void initData(ResultJudge info){

        List<TeaDetail> list=new ArrayList<>();
        TeaDetail oneDetail=new TeaDetail();
        oneDetail.setHead_image(Base.user.getHead_pic());
        oneDetail.setTitle(Base.user.getName());
        oneDetail.setIssubmit(info.getIssubmit());
        oneDetail.setState(info.getState());


        if (info.getState().equals("已通过")){
            menu.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }else{
            menu.setVisibility(View.VISIBLE);
            if (isShow){
                delete.setVisibility(View.VISIBLE);
            }
        }

        oneDetail.setType("head");
        oneDetail.setView_type(TagFinal.TYPE_TOP);
        list.add(oneDetail);
        if (StringJudge.isNotEmpty(info.getJudge_record())){
            for (ParamBean bean:info.getJudge_record()){
                TeaDetail detail=new TeaDetail();
                detail.setTitle(bean.getTitle());
                detail.setContent(bean.getContent());
                detail.setType(bean.getType());
                detail.setView_type(TagFinal.TYPE_ITEM);
                list.add(detail);
            }
        }
        TeaDetail detaiScore=new TeaDetail();
        detaiScore.setTitle("个人得分");
        detaiScore.setContent(info.getScore());
        detaiScore.setType("text");
        detaiScore.setView_type(TagFinal.TYPE_ITEM);

        list.add(detaiScore);
        if (StringJudge.isEmpty(info.getReason())){

        }else{
            TeaDetail detaireason=new TeaDetail();
            detaireason.setTitle("审核备注");
            detaireason.setContent(info.getReason());
            detaireason.setType("text");
            detaireason.setView_type(TagFinal.TYPE_ITEM);
            list.add(detaireason);
        }

        TeaDetail detai=new TeaDetail();
        detai.setTitle("");
        detai.setContent("");
        detai.setType("text");
        detai.setView_type(TagFinal.TYPE_ITEM);
        list.add(detai);
        if (StringJudge.isNotEmpty(info.getAttachment())){
            List<String> name=StringUtils.getListToString(info.getAttachment(),",");
            for (int i=0; i<name.size();i++){
                String s1=name.get(i);
                TeaDetail detail=new TeaDetail();
                detail.setIcon(i+1+"");
                detail.setContent(s1);
                detail.setType("icon");
                detail.setView_type(TagFinal.TYPE_FOOTER);
                list.add(detail);
            }
        }
        adapter.setDataList(list);
        adapter.setLoadState(2);
    }
}
