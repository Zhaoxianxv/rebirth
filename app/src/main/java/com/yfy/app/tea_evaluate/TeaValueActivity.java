package com.yfy.app.tea_evaluate;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.tea_evaluate.adpter.SelectValueAdapter;
import com.yfy.app.tea_evaluate.bean.KeValue;
import com.yfy.app.tea_evaluate.bean.ResultInfo;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.rebirth.R;
import com.yfy.recycerview.RecycleViewDivider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class TeaValueActivity extends BaseActivity implements Callback<ResEnv> {

    private static final String TAG = TeaValueActivity.class.getSimpleName();

    private ArrayList<KeValue> depTags=new ArrayList<>();
    private SelectValueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler);
        initSQtoobar();
        initRecycler();
//        getYear();


        getData();
    }

    private int index=0;
    private void getData(){
        depTags=getIntent().getParcelableArrayListExtra(TagFinal.OBJECT_TAG);
        index=getIntent().getIntExtra(TagFinal.ALBUM_LIST_INDEX,0 );
        adapter.setMain_index(index);
        adapter.setDataList(depTags);
        adapter.setLoadState(TagFinal.LOADING_COMPLETE);
    }



    private void initSQtoobar() {
        assert toolbar!=null;
        toolbar.setTitle("选择年份");
    }


    private RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView = (RecyclerView) findViewById(R.id.public_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        xlist.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.gray)));
        adapter=new SelectValueAdapter(this);
        recyclerView.setAdapter(adapter);

    }





    /**
     * ----------------------------retrofit-----------------------
     */


//    public void getYear()  {
//
//        ReqEnv reqEnvelop = new ReqEnv();
//        ReqBody reqBody = new ReqBody();
//        YearReq request = new YearReq();
//        //获取参数
//
//        reqBody.year_Req = request;
//        reqEnvelop.body= reqBody;
//
//        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().judge_year(reqEnvelop);
//        call.enqueue(this);
////        showProgressDialog("正在加载");
//
//    }



    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
            } catch (IOException e) {
                Logger.e("zxx", "onResponse: IOException");
                e.printStackTrace();
            }
            toastShow("数据出差了");
        }
        if (!isActivity())return;
        dismissProgressDialog();
        dismissProgressDialog();
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b = respEnvelope.body;
            if (b.year_Response != null) {
                String result = b.year_Response.year_Result;
                Logger.e("zxx", "tab: " + result);
                ResultInfo infor=gson.fromJson(result, ResultInfo.class);
                if (StringJudge.isNotNull(infor)){
//                    depTags=infor.getJudge_year();
//                    adapter.setDataList(depTags);
//                    adapter.setLoadState(TagFinal.LOADING_COMPLETE);
                }
            }
        }

    }


    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        dismissProgressDialog();
        Logger.e("onFailure"+call.request().headers().toString());
        toastShow(R.string.fail_do_not);
    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
