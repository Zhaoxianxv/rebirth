package com.yfy.app.attennew;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Bind;
import com.yfy.app.attennew.adapter.DetailAdapter;
import com.yfy.app.attennew.bean.AttenBean;
import com.yfy.app.attennew.bean.AttenFlow;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.dialog.DialogTools;
import com.yfy.final_tag.ConvertObjtect;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;

import java.util.ArrayList;
import java.util.List;

public class AttenNewDetailActivity extends WcfActivity {

    private AttenBean bean;

    @Bind(R.id.maintiain_detail_list)
    RecyclerView listView;
    @Bind(R.id.maintiain_detail_bottom)
    LinearLayout layout;
    private DetailAdapter adapter;
    private List<AttenFlow> flowBeenS=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atten_new_detail);
        initSQToolbar();
        layout.setVisibility(View.GONE);
        getData();
    }

    @Override
    public void onPageBack() {
        setResult(RESULT_OK);
        super.onPageBack();
    }

    private void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("请假详情");
    }

    public void getData(){

        Bundle bundle=getIntent().getExtras();
        if (StringJudge.isContainsKey(bundle, TagFinal.OBJECT_TAG)){
            bean=bundle.getParcelable(TagFinal.OBJECT_TAG);
        }
        if (bean==null)return;
        if (bean.getAttendance_info()!=null){
            flowBeenS=bean.getAttendance_info();
            intiListView();
        }
    }




    public void intiListView(){

        listView.setLayoutManager(new LinearLayoutManager(this));
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
        ParamsTask delete = new ParamsTask(params, TagFinal.ATTENNEW_DELETE);
        showProgressDialog("");
        execute(delete);
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Logger.e(TagFinal.ZXX, "onSuccess: "+result );
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
}
