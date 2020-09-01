package com.yfy.app.tea_evaluate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.tea_evaluate.adpter.RedactListAdapter;
import com.yfy.app.tea_evaluate.bean.ParamBean;
import com.yfy.app.tea_evaluate.bean.ResultInfo;
import com.yfy.app.tea_evaluate.bean.ResultJudge;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.dialog.ConfirmAlbumWindow;
import com.yfy.dialog.DialogTools;
import com.yfy.final_tag.*;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ExtraRunTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.permission.PermissionFail;
import com.yfy.permission.PermissionGen;
import com.yfy.permission.PermissionSuccess;
import com.yfy.permission.PermissionTools;
import com.yfy.rebirth.R;
import com.yfy.recycerview.RecycleViewDivider;
import com.yfy.view.SQToolBar;
import com.yfy.view.multi.MultiPictureView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RedactActivity extends WcfActivity implements Callback<ResEnv> {

    private static final String TAG = RedactActivity.class.getSimpleName();

    private RedactListAdapter adapter;
    private List<ParamBean> dataList=new ArrayList<>();
    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        getData();
        initTypeDialog();
        initSQToolbar();
        initRecycler();

    }

    @Override
    public void onPageBack() {
        finish();
    }
    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }


    private void getData(){
        id=getIntent().getIntExtra(TagFinal.ID_TAG,-1);
        getInfoData(true,id);
    }
    private  void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("获奖编辑");
        toolbar.addMenuText(TagFinal.ONE_INT,"完成");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                closeKeyWord();
                if (StringJudge.isEmpty(redactAdapter())){
                    return;
                }
                add(redactAdapter());
            }
        });
    }

    private String redactAdapter(){

        List<ParamBean> params=adapter.getDataList();

        StringBuilder sb=new StringBuilder();
        for (ParamBean bean :params){
            switch (bean.getType()){
                case "date":
                    appenString(sb,bean);
                    break;
                case "text":
                    if (StringJudge.isEmpty(bean.getContent())){
                        toastShow("请填写："+bean.getTitle());
                        return null;
                    }
                    appenString(sb,bean);
                    break;
                case "select":
                    appenString(sb,bean);
                    break;
                case "int":
                    if (StringJudge.isEmpty(bean.getContent())){
                        toastShow("请填写："+bean.getTitle());
                        return null;
                    }
                    appenString(sb,bean);
                    break;
                case "multifile":
                    appenString(sb,bean,"");
                    break;
            }
        }
        String content=sb.toString();
        if (content.length() > 0) {
            content = content.substring(0, content.length() - 1);
        }
        return content;
    }

    private StringBuilder appenString(StringBuilder sb,ParamBean bean){
        sb=sb.append(bean.getId()).append("^").append(bean.getContent()).append("|");
        return sb;
    }

    private StringBuilder appenString(StringBuilder sb, ParamBean bean, String is){
        sb=sb.append(bean.getId()).append("^").append("[image]").append("|");
        return sb;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public MultiPictureView add_mult;//陈明富
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
                getInfoData(false, id);
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
        adapter=new RedactListAdapter(this,dataList);


        adapter.setRedact(new RedactListAdapter.Redact() {
            @Override
            public void del(final String image, final String id) {
                DialogTools.getInstance().showDialog(mActivity, "", "确定删除！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        closeKeyWord();
                        delectImage(id,image);
                    }
                });
            }
            @Override
            public void addMult(MultiPictureView mult) {
                album_select.showAtBottom();
                add_mult=mult;
            }
        });
        recyclerView.setAdapter(adapter);

    }


    private ConfirmAlbumWindow album_select;
    private void initTypeDialog() {
        album_select = new ConfirmAlbumWindow(mActivity);
        album_select.setOne_select(getResources().getString(R.string.take_photo));
        album_select.setTwo_select(getResources().getString(R.string.album));
        album_select.setName(getResources().getString(R.string.upload_type));
        album_select.setOnPopClickListenner(new ConfirmAlbumWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.popu_select_one:
                        PermissionTools.tryCameraPerm(mActivity);
                        break;
                    case R.id.popu_select_two:
                        PermissionTools.tryWRPerm(mActivity);
                        break;
                }
            }
        });
    }





    public void getInfoData(boolean is,int id_){


        Object[] parmas=new Object[]{
                Base.user.getSession_key(),
                id_
        };

        ParamsTask grade= new ParamsTask(parmas, TagFinal.TEA_JUDGE_INFO,TagFinal.TEA_JUDGE_INFO);
        execute(grade);
        if (is)showProgressDialog("正在加载");
    }
    public void delectImage(String type_id,String image){


        Object[] parmas=new Object[]{
                Base.user.getSession_key(),
                id,
                type_id,
                image
        };

        ParamsTask del= new ParamsTask(parmas, TagFinal.TEA_DELECTED_PIC,TagFinal.TEA_DELECTED_PIC);
        execute(del);
        showProgressDialog("正在加载");
    }

    public void add(String content){
        Object[] parmas=new Object[]{
                Base.user.getSession_key(),
                Base.user.getName(),
                id,//新增为0
                Base.year,
                Base.type_id,//type_id
                content,
                "",
                ""
        };
        ParamsTask ok= new ParamsTask(parmas, TagFinal.TEA_ADD_JUDGE,TagFinal.TEA_ADD_JUDGE);
        ExtraRunTask wrapTask = new ExtraRunTask(ok);
        wrapTask.setExtraRunnable(extraRunnable);
        execute(wrapTask);
        showProgressDialog("正在加载");
    }

    private ExtraRunTask.ExtraRunnable extraRunnable = new ExtraRunTask.ExtraRunnable() {

        @Override
        public Object[] onExecute(Object[] params) {

            if (add_mult==null){
                params[6] = "";
                params[7] = "";
            }else{
                List<Photo> list=new ArrayList<>();
                for (String uri:add_mult.getList()){
                    Photo p=new Photo();
                    p.setPath(uri);
                    list.add(p);
                }
                params[6] = Base64Utils.getZipTitle2(list);
                params[7] = Base64Utils.getZipBase64Str(list);
            }

            return params;
        }
    };

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


    private void redact(ResultJudge info){
        dataList.clear();
        ParamBean year=new ParamBean();
        year.setContent(Base.year_name);
        year.setTitle("获奖年份");
        year.setType("base");
        dataList.add(year);
        ParamBean type=new ParamBean();
        type.setContent(Base.type_name);
        type.setTitle("获奖分类");
        type.setType("base");
        dataList.add(type);

        for ( ParamBean icon:info.getJudge_record()){
            if (icon.getType().equals("multifile")){
                if (StringJudge.isNotEmpty(icon.getContent())){
                    List<String> name= StringUtils.listToStringSplitCharacters(info.getAttachment(),",");
                    for (String s1:name){
                        ParamBean bean=new ParamBean();
                        String[] names=s1.split(Pattern.quote("/"));
                        bean.setTitle(names[names.length-1]);
                        bean.setContent(s1);
                        bean.setId(icon.getId());
                        bean.setType("del");
                        dataList.add(bean);
                    }
                }
            }
        }

        //--------------
//        ParamBean icon=new ParamBean();
//        icon.setType("icon");
//        dataList.add(icon);
        dataList.addAll(info.getJudge_record());
        adapter.setDataList(dataList);
        adapter.setLoadState(2);
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
            redact(info);

        }
        if (name.equals(TagFinal.TEA_DELECTED_PIC)){
            ResultJudge info=gson.fromJson(result, ResultJudge.class);
            if (info.getResult().equals(TagFinal.TRUE)){
//                toast(info.getError_code());

                setResult(RESULT_OK);
                onPageBack();
            }else{
                toastShow(info.getError_code());
            }
        }
        if (name.equals(TagFinal.TEA_ADD_JUDGE)){
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







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.UI_TAG:
                    Bundle b=data.getExtras();
                    if (StringJudge.isContainsKey(b,TagFinal.ID_TAG)){
//                        chioce_grade.setText(b.getString(TagFinal.NAME_TAG));
//                        chioce_grade.setTextColor(ColorRgbUtil.getBaseText());
                    }
                    break;
                case TagFinal.CAMERA:
                    addMult(FileCamera.photo_camera);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    setMultList(photo_a);
                    break;
                case TagFinal.UI_REFRESH:
                    int index=data.getIntExtra(TagFinal.ALBUM_LIST_INDEX,0 );
                    String select_name=data.getStringExtra(TagFinal.OBJECT_TAG);
                    ParamBean addPararem=adapter.getDataList().get(index);
                    addPararem.setTitle(select_name);
                    adapter.notifyItemChanged(index,addPararem);
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    public void addMult(String uri){
        if (uri==null) return;
        add_mult.addItem(uri);
    }
    public void setMultList(List<Photo> list){
        for (Photo photo:list){
            if (photo==null) continue;
            addMult(photo.getPath());
        }
    }


    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    private void takePhoto() {
        FileCamera camera=new FileCamera(mActivity);
        startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
    }
    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    private void photoAlbum() {
        Intent intent;
        intent = new Intent(mActivity, AlbumOneActivity.class);
        Bundle b = new Bundle();
        b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
        b.putBoolean(TagFinal.ALBUM_SINGLE, false);
        intent.putExtras(b);
        startActivityForResult(intent,TagFinal.PHOTO_ALBUM);
    }
    @PermissionFail(requestCode = TagFinal.CAMERA)
    private void showCamere() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_camere, Toast.LENGTH_SHORT).show();
    }
    @PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
    private void showTip1() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    /**
     * --------------------------retrofit---------------------
     */



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
            ResBody b = respEnvelope.body;
            if (b.para_Response != null) {
                String result = b.para_Response.judge_paraResult;
                Logger.e(call.request().headers().toString() + result);
                ResultInfo info=gson.fromJson(result, ResultInfo.class);
//
//                params = info.getJudge_parameter();
//                adapter.setDataList(params);
//                adapter.setLoadState(2);
            }



        }
    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("error  "+call.request().headers().toString() );
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }

}
