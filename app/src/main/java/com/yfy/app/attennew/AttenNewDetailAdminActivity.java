package com.yfy.app.attennew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.app.ContentDialogEditActivity;
import com.yfy.app.attennew.adapter.DetailAdminAdapter;
import com.yfy.app.attennew.bean.AttenBean;
import com.yfy.app.attennew.bean.AttenFlow;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.DateUtils;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;

import java.util.ArrayList;
import java.util.List;

public class AttenNewDetailAdminActivity extends WcfActivity {

    private static final String TAG = AttenNewDetailAdminActivity.class.getSimpleName();
    private AttenBean bean;

    @Bind(R.id.maintiain_detail_list)
    RecyclerView listView;
    @Bind(R.id.detail_item_yes)
    Button admin_name;
    private DetailAdminAdapter adapter;
    private List<AttenFlow> flowBeenS=new ArrayList<>();
    private String date="",id="";
    @Bind(R.id.maintiain_detail_bottom)
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atten_new_detail);
        getData();
        date= DateUtils.getDateTime(getResources().getString(R.string.date_type_3));
        initSQtoolbar();
    }

    public void initSQtoolbar(){
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


        if (bean.getDealstate().equals("已销假")||bean.getDealstate().equals("驳回请假")){
            layout.setVisibility(View.GONE);
        }else{
            layout.setVisibility(View.VISIBLE);
            if (bean.getDealstate().equals("同意请假")){
                admin_name.setText("销假");
            }else{
                admin_name.setText("审批");
            }
        }

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        adapter=new DetailAdminAdapter(mActivity,flowBeenS,bean,false);

        listView.setAdapter(adapter);

    }

    @OnClick(R.id.detail_item_yes)
    void setAdmin(){

        if(admin_name.getText().toString().equals("销假")){
            Intent intent=new Intent(mActivity,ContentDialogEditActivity.class);
            intent.putExtra(TagFinal.TITLE_TAG,"销假");
            startActivityForResult(intent, TagFinal.UI_REFRESH);
        }else{
            Intent intent=new Intent(mActivity,AttenDoingActivity.class);
            intent.putExtra(TagFinal.ID_TAG,bean.getId());
            intent.putExtra(TagFinal.TYPE_TAG,bean.getDealstate().equals("同意请假")?true:false);
            startActivityForResult(intent, TagFinal.UI_CONTENT);
        }
    }

    private String dealstate="",content="";

    private void choiceState(){


        dealstate="5";
        if (StringJudge.isEmpty(dealstate)){
            toast("请选择审批状态");
            return;
        }
        if (StringJudge.isEmpty(content)){
            content="";
        }

        Object[] params = new Object[] {
            Base.user.getSession_key(),
            bean.getId(),
            content,
            "",//table_plan
            dealstate
        };
        ParamsTask choice = new ParamsTask(params, TagFinal.ATTENNEW_ADMIN_DO, TagFinal.ATTENNEW_ADMIN_DO);
        showProgressDialog("");
        execute(choice);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_CONTENT:
                    setResult(RESULT_OK);
                    finish();
                    break;
                case TagFinal.UI_REFRESH:
                    content=data.getStringExtra(TagFinal.OBJECT_TAG);
                    choiceState();
                    break;
            }
        }
    }

    @Override
    public void onPageBack() {
        setResult(RESULT_OK);
        super.onPageBack();
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Logger.e( "onSuccess: "+result );
        if (!isActivity())return false;
        dismissProgressDialog();
        String name=wcfTask.getName();
        if (name.equals(TagFinal.ATTENNEW_ADMIN_DO)){
            if (StringJudge.isSuccess(gson,result)){
                setResult(RESULT_OK);
                finish();
            }else{
                toastShow(R.string.success_not_do);
            }
        }
        if (StringJudge.isSuccess(gson,result)){
            onPageBack();
        }else{
            toastShow(R.string.success_not_do);
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return;
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
