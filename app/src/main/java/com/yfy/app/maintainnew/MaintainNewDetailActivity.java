package com.yfy.app.maintainnew;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import butterknife.Bind;
import com.yfy.app.maintainnew.adapter.DetailAdapter;
import com.yfy.app.maintainnew.bean.FlowBean;
import com.yfy.app.maintainnew.bean.MainBean;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.dialog.DialogTools;
import com.yfy.final_tag.CallPhone;
import com.yfy.final_tag.ConvertObjtect;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.permission.PermissionFail;
import com.yfy.permission.PermissionGen;
import com.yfy.permission.PermissionSuccess;
import com.yfy.rebirth.R;

import java.util.ArrayList;
import java.util.List;

public class MaintainNewDetailActivity extends WcfActivity {

    private static final String TAG = MaintainNewDetailActivity.class.getSimpleName();
    private MainBean bean;

    @Bind(R.id.public_recycler)
    RecyclerView listView;
    private DetailAdapter adapter;
    private List<FlowBean> flowBeenS=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        initSQToolbar();
        getData();
    }

    @Override
    public void onPageBack() {
        setResult(RESULT_OK);
        super.onPageBack();
    }

    private void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("报修详情");
    }

    public void getData(){

        Bundle bundle=getIntent().getExtras();
        if (StringJudge.isContainsKey(bundle, TagFinal.OBJECT_TAG)){
            bean=bundle.getParcelable(TagFinal.OBJECT_TAG);
        }
        if (bean==null)return;
        if (bean.getMaintain_info()!=null){
            flowBeenS=bean.getMaintain_info();
            intiListView();
        }
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




    private SwipeRefreshLayout swipeRefreshLayout;
    public void intiListView(){
        swipeRefreshLayout=findViewById(R.id.public_swip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                closeSwipeRefresh();
            }
        });
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
//        xlist.setLayoutManager(new GridLayoutManager(this, 1));
        listView.setItemAnimator(new DefaultItemAnimator());


        //添加分割线
        adapter=new DetailAdapter(mActivity,flowBeenS,bean,true);
        adapter.setDoAdmin(new DetailAdapter.DoAdmin() {
            @Override
            public void onClickDo(final String id, String tag) {
                DialogTools.getInstance().showDialog(
                        mActivity,
                        "",
                        "是否要撤销申请！",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                deleteItem(ConvertObjtect.getInstance().getInt(id));
                                dialogInterface.dismiss();
                            }
                });
            }
        });
        listView.setAdapter(adapter);

    }




    private void deleteItem(int id){
        Object[] params = new Object[] {
                Base.user.getSession_key(),
                id
        };
        ParamsTask delete = new ParamsTask(params, TagFinal.MAINNEW_DELETE_MAINTAIN);
        execute(delete);
        showProgressDialog("");
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Logger.e( "onSuccess: "+result );
        dismissProgressDialog();
        if (StringJudge.isSuccess(gson,result)){
            onPageBack();
            return false;
        }else{
            toastShow(R.string.success_not_do);
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }

    @PermissionSuccess(requestCode = TagFinal.CALL_PHONE)
    public void call(){
        CallPhone.callDirectly(mActivity, "");
    }
    @PermissionFail(requestCode = TagFinal.CALL_PHONE)
    public void callFail(){
        Toast.makeText(getApplicationContext(), R.string.permission_fail_call, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
