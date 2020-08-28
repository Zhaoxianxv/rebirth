package com.yfy.app.maintainnew;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.app.maintainnew.adapter.DetailAdminAdapter;
import com.yfy.app.maintainnew.bean.DepTag;
import com.yfy.app.maintainnew.bean.FlowBean;
import com.yfy.app.maintainnew.bean.MainBean;
import com.yfy.app.maintainnew.bean.MainRes;
import com.yfy.app.net.*;
import com.yfy.app.net.maintain.BranchTypeReq;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.*;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ExtraRunTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.permission.PermissionFail;
import com.yfy.permission.PermissionGen;
import com.yfy.permission.PermissionSuccess;
import com.yfy.rebirth.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaintainNewDetailAdminActivity extends WcfActivity implements Callback<ResEnv> {

    private static final String TAG = MaintainNewDetailAdminActivity.class.getSimpleName();
    private MainBean bean;

    @Bind(R.id.maintiain_detail_list)
    RecyclerView listView;
    private DetailAdminAdapter adapter;
    private List<FlowBean> flowBeenS=new ArrayList<>();
    private String date="",id="",dealstate="";
    @Bind(R.id.maintiain_detail_bottom)
    LinearLayout layout;

    @Bind(R.id.detail_item_choice)
    Button button_type;
    @Bind(R.id.detail_item_yes)
    Button button_do;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_new_detail);
        getData();
        date= DateUtils.getDateTime(getResources().getString(R.string.date_type_3));
        initSQtoolbar();
    }

    public void initSQtoolbar(){
        assert toolbar!=null;
        toolbar.setTitle("报修详情");
    }
    public void getData(){
        Bundle bundle=getIntent().getExtras();
        layout.setVisibility(View.GONE);
        if (StringJudge.isContainsKey(bundle, TagFinal.OBJECT_TAG)){
            bean=bundle.getParcelable(TagFinal.OBJECT_TAG);
        }
        id=bean.getId();
        getDetail();
//        if (bean==null)return;
//        if (bean.getMaintain_info()!=null){
//            flowBeenS=bean.getMaintain_info();
//            intiListView();
//        }
//        if (bean.getDealstate().equals("完成维修")||bean.getDealstate().equals("拒绝维修")){
//            layout.setVisibility(View.GONE);
//        }else{
//            layout.setVisibility(View.VISIBLE);
//        }

    }




    public void intiListView(){
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
//        xlist.setLayoutManager(new GridLayoutManager(this, 1));
        listView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        adapter=new DetailAdminAdapter(mActivity,flowBeenS,bean,false);

        listView.setAdapter(adapter);
        //滚动底对齐
//        mLayoutManager.scrollToPositionWithOffset(adapter.getItemCount()-1, 0);
//        mLayoutManager.setStackFromEnd(true);

    }


    private void rollOut() {

        Object[] params = new Object[] {
                Base.user.getSession_key(),
                id,
                type_id };
        ParamsTask rollOutTask = new ParamsTask(params, TagFinal.MAINNEW_SET_TYPE, TagFinal.MAINNEW_SET_TYPE);
        showProgressDialog("");
        execute(rollOutTask);
    }


    private void getDetail() {

        Object[] params = new Object[] {
            Base.user.getSession_key(),
            id};
        ParamsTask rollOutTask = new ParamsTask(params, TagFinal.MAINNEW_GET_DETAIL, TagFinal.MAINNEW_GET_DETAIL);
        showProgressDialog("");
        execute(rollOutTask);
    }

    private AlertDialog.Builder  listDialog;
    private List<DepTag> depTags=new ArrayList<>();
    private String type_id;//
    private void initDialog() {
        List<String> txts=new ArrayList<>();
        for(DepTag s:depTags){
            txts.add(s.getName());
        }
        String[] strArr = new String[txts.size()];
        txts.toArray(strArr);
        listDialog = new AlertDialog.Builder(mActivity);
//		builder.setIcon(R.drawable.ic_launcher);
        listDialog.setTitle("请选择");
        //    设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        listDialog.setSingleChoiceItems(strArr, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                type_id=depTags.get(which).getId();
            }
        });
        listDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rollOut();
                dialog.dismiss();
            }
        });
        listDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        listDialog.show();
    }

    private void choiceState(String content){
        Object[] params = new Object[] {
                id,
                Base.user.getSession_key(),
                Base.user.getName(),
                content,
                date,//date
                dealstate,//1,完成2，拒绝
                0,//int
                "0",//money
                "",//pictures
                "",//fileContext
                "",//delpictures
        };
        ParamsTask choice = new ParamsTask(params, TagFinal.MAINNEW_SYXX, TagFinal.MAINNEW_SYXX);
        ExtraRunTask wrapTask = new ExtraRunTask(choice);
        wrapTask.setExtraRunnable(extraRunnable);
        execute(wrapTask);
    }

    private ExtraRunTask.ExtraRunnable extraRunnable = new ExtraRunTask.ExtraRunnable() {

        @Override
        public Object[] onExecute(Object[] params) {
            List<Photo> list=new ArrayList<>();
            params[9] = Base64Utils.filesToZipBase64Notice(list);
            return params;
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_CONTENT:
                    String content=data.getStringExtra(TagFinal.OBJECT_TAG);
                    choiceState(content);
                    break;
                case TagFinal.ONE_INT:
                    onPageBack();
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
        if (!isActivity())return false;
        dismissProgressDialog();
        Logger.e(TagFinal.ZXX,  "onSuccess: "+result );
        String taskName = wcfTask.getName();
        if (taskName.equals(TagFinal.MAINNEW_GET_TYPE)){
            MainRes infor=gson.fromJson(result, MainRes.class);
            depTags=infor.getMaintainclass();
            if (StringJudge.isEmpty(depTags)){
                toastShow("暂无数据！");
            }else{
                initDialog();
            }
            return false;
        }
        if (taskName.equals(TagFinal.MAINNEW_SET_TYPE)){
            onPageBack();
            return false;
        }
        if (taskName.equals(TagFinal.MAINNEW_GET_DETAIL)){
            MainRes infor=gson.fromJson(result, MainRes.class);
            if (StringJudge.isEmpty(infor.getMaintains())){
                toast("没有数据");
            }else{
                bean=infor.getMaintains().get(0);
                if (bean==null)return false;
                if (bean.getMaintain_info()!=null){
                    flowBeenS=bean.getMaintain_info();
                    intiListView();
                }
                if (bean.getDealstate().equals("完成维修")||bean.getDealstate().equals("拒绝维修")){
                    if (bean.getIsremark().equals(TagFinal.TRUE)){
                        layout.setVisibility(View.GONE);
                    }else{
                        layout.setVisibility(View.VISIBLE);
                        button_do.setText("备注");
                        button_type.setVisibility(View.GONE);
                        button_do.setVisibility(View.VISIBLE);
                    }

                }else{
                    button_do.setText("处理维修");
                    layout.setVisibility(View.VISIBLE);
                }
            }
            return false;
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




    //转交
    @OnClick(R.id.detail_item_choice)
    void setChicoe(){
        if (StringJudge.isEmpty(depTags)){
            getShapeKind();
        }else{
            initDialog();
        }
    }
    //处理
    @OnClick(R.id.detail_item_yes)
    void setDo(){
        Intent intent=new Intent(mActivity,MaintainDoingActivity.class);
        intent.putExtra("data",id);
        startActivityForResult(intent,TagFinal.ONE_INT);
    }



    /**
     * ----------------------------retrofit-----------------------
     */


    public void getShapeKind()  {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        BranchTypeReq request = new BranchTypeReq();
        //获取参数

        reqBody.getMaintainclass = request;
        reqEnvelop.body = reqBody;

        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().getMaintainclass(reqEnvelop);
        call.enqueue(this);
        showProgressDialog("正在登录");

    }


    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
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
        if (!isActivity())return;
        dismissProgressDialog();
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.getMaintainclassResponse!=null) {
                String result = b.getMaintainclassResponse.getclassidResult;
                Logger.e(call.request().headers().toString()+result);
                MainRes infor=gson.fromJson(result, MainRes.class);
                depTags=infor.getMaintainclass();
                if (StringJudge.isEmpty(depTags)){
                    toastShow("暂无数据！");
                }else{
                    initDialog();
                }
            }
        }else{
            Logger.e("zxx", "evn: null" );
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());
        toast(R.string.fail_do_not);
        dismissProgressDialog();
    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }

    @PermissionSuccess(requestCode = TagFinal.CALL_PHONE)
    public void call(){
        Intent myIntentDial=new Intent("android.intent.action.CALL",Uri.parse("tel:"));
        startActivity(myIntentDial);
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
