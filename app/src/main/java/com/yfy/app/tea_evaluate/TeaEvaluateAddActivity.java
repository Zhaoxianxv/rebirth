package com.yfy.app.tea_evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.net.*;
import com.yfy.app.net.tea_evaluate.JudgeparaReq;
import com.yfy.app.tea_evaluate.adpter.AddListAdapter;
import com.yfy.app.tea_evaluate.bean.AddPararem;
import com.yfy.app.tea_evaluate.bean.ResultInfo;
import com.yfy.app.tea_evaluate.bean.ResultJudge;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.dialog.AbstractDialog;
import com.yfy.dialog.ConfirmAlbumWindow;
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

public class TeaEvaluateAddActivity extends WcfActivity implements Callback<ResEnv> {

    private static final String TAG = TeaEvaluateAddActivity.class.getSimpleName();

    private String param_id="0";

    private AddListAdapter adapter;
    private List<AddPararem> params=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler);
        getData();
        initSQToolbar();

        initTypeDialog();
        initRecycler();

    }

    private void getData(){
        Bundle bundle=getIntent().getExtras();
        if (StringJudge.isContainsKey(bundle,TagFinal.ID_TAG)){
            param_id = bundle.getString(TagFinal.ID_TAG);
        }
        if (StringJudge.isContainsKey(bundle,TagFinal.NAME_TAG)){
            String name = bundle.getString(TagFinal.NAME_TAG);
            Base.type_name=name;
        }
        getShapeKind(ConvertObjtect.getInstance().getInt(param_id));
    }

    private  void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("添加获奖");
        toolbar.addMenuText(TagFinal.ONE_INT,"完成");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (StringJudge.isEmpty(redactAdapter())){
                    return;
                }
                add(redactAdapter());

            }
        });
    }
    private String redactAdapter(){

        List<AddPararem> params=adapter.getDataList();

        StringBuilder sb=new StringBuilder();
        for (AddPararem bean :params){

            switch (bean.getType()){
                case "date":
                    if (StringJudge.isEmpty(bean.getTitle())){
                        toastShow("请填写："+bean.getName());
                        return null;
                    }
                    appenString(sb,bean);
                    break;
                case "text":
                    if (StringJudge.isEmpty(bean.getTitle())){
                        toastShow("请填写："+bean.getName());
                        return null;
                    }
                    appenString(sb,bean);
                    break;
                case "select":
                    if (StringJudge.isEmpty(bean.getTitle())){
                        toastShow("请选择："+bean.getName());
                        return null;
                    }
                    appenString(sb,bean);
                    break;
                case "int":
                    if (StringJudge.isEmpty(bean.getTitle())){
                        toastShow("请填写："+bean.getName());
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

    private StringBuilder appenString(StringBuilder sb,AddPararem bean){
        sb=sb.append(bean.getId()).append("^").append(bean.getTitle()).append("|");
        return sb;
    }

    private StringBuilder appenString(StringBuilder sb, AddPararem bean, String is){
        sb=sb.append(bean.getId()).append("^").append("[image]").append("|");
        return sb;
    }

    private RecyclerView recyclerView;
    public MultiPictureView add_mult;
    public void initRecycler(){

        recyclerView = findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.gray)));
        adapter=new AddListAdapter(this,params);

        adapter.setPic(new AddListAdapter.Pic() {
            @Override
            public void add(MultiPictureView add_mul) {
                album_select.showAtBottom();
                add_mult=add_mul;
            }
        });
        recyclerView.setAdapter(adapter);

    }



    public void add(String content){
        Object[] parmas=new Object[]{
                Base.user.getSession_key(),
                Base.user.getName(),
                0,//新增为0
                Base.year,
                Base.type_id,//type_id
                content,
                "",
                ""
        };
        ParamsTask ok= new ParamsTask(parmas, TagFinal.TEA_ADD_JUDGE,TagFinal.TEA_ADD_JUDGE);
        ExtraRunTask wrapTask = new ExtraRunTask(ok);
        wrapTask.setExtraRunnable(extraRunnable);
        showProgressDialog("");
        execute(wrapTask);

    }

    private ExtraRunTask.ExtraRunnable extraRunnable = new ExtraRunTask.ExtraRunnable() {

        @Override
        public Object[] onExecute(Object[] params) {

            if (add_mult==null){
                params[6] = "";
                params[7] = "";
            }else{
                List<Photo> list=new ArrayList<>();
                int i=0;
                for (String uri:add_mult.getList()){
                    Photo p=new Photo();
                    i++;
                    String picName = String.valueOf(System.currentTimeMillis())+String.valueOf(i);
                    p.setFileName(picName+".jpg");
                    p.setPath(uri);
                    list.add(p);
                }

                params[6] = Base64Utils.getZipTitle2(list);
                params[7] = Base64Utils.getZipBase64Str(list);
            }

            return params;
        }
    };




    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Logger.e(TagFinal.ZXX, "onSuccess: "+result );
        if (!isActivity()) return false;
        dismissProgressDialog();
        String name=wcfTask.getName();
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
        if (!isActivity()) return ;
        toastShow(R.string.fail_do_not);
        dismissProgressDialog();
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
                    AddPararem addPararem=adapter.getDataList().get(index);
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

    public void getShapeKind(int param_id)  {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        JudgeparaReq request = new JudgeparaReq();
        request.setId(param_id);
        //获取参数


        reqBody.para_Req = request;
        reqEnvelop.body= reqBody;

        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().judge_item(reqEnvelop);
        call.enqueue(this);
        showProgressDialog("正在加载");

    }





    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        Logger.e("zxx", "onResponse: "+response.code());
        if (!isActivity())return;
        dismissProgressDialog();
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
            if (b.para_Response != null) {
                String result = b.para_Response.judge_paraResult;
                Logger.e("zxx", "tab: " + result);
                ResultInfo info=gson.fromJson(result, ResultInfo.class);
                redact(info);
            }
        }
    }


    private void redact(ResultInfo info){
        params.clear();
        AddPararem year=new AddPararem();
        year.setName(Base.year_name);
        year.setTitle("获奖年份");
        year.setType("base");
        params.add(year);
        AddPararem type=new AddPararem();
        type.setName(Base.type_name);
        type.setTitle("获奖分类");
        type.setType("base");
        params.add(type);

//        AddPararem icon=new AddPararem();
//        icon.setType("icon");
//        params.add(icon);
        params.addAll(info.getJudge_parameter());
        adapter.setDataList(params);
        adapter.setLoadState(2);

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure "+call.request().headers().toString());
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }


}
