package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.attennew.AttenNewActivity;
import com.yfy.app.auto_update.UploadDataService;
import com.yfy.app.login.AlterActivity;
import com.yfy.app.login.AlterCllActivity;
import com.yfy.app.login.IntegralResult;
import com.yfy.app.login.LoginActivity;
import com.yfy.app.login.LoginRebirthActivity;
import com.yfy.app.maintainnew.MaintainNewActivity;
import com.yfy.app.net.*;
import com.yfy.app.net.user.AdminReq;
import com.yfy.app.net.user.CallReq;
import com.yfy.app.tea_evaluate.TeaEvaluateActivity;
import com.yfy.app.tea_evaluate.TeaTJMainActivity;
import com.yfy.base.App;
import com.yfy.base.Base;
import com.yfy.base.activity.BaseActivity;
import com.yfy.base.activity.WcfActivity;
import com.yfy.db.GreenDaoManager;
import com.yfy.db.UserAdmin;
import com.yfy.db.UserPreferences;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RebirthMainActivity extends WcfActivity implements Callback<ResEnv> {
    private static final String TAG = RebirthMainActivity.class.getSimpleName();

    @Bind(R.id.bar_title)
    TextView title;
    @Bind(R.id.user_tell)
    AppCompatTextView user_tell;
    @Bind(R.id.user_head)
    ImageView user_head;
    @Bind(R.id.user_name)
    TextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rebirth_main);

        if (App.isServiceRunning(mActivity,"UploadDataService")){
            Logger.e("UploadDataService: " );
        }else{
            startService(new Intent(mActivity,UploadDataService.class));//开启更新
        }
        initSQToolbar();
        initTypeDialog();
        if (Base.user!=null){
            initView();
            getCall();
            getAdmin();
        }else{

        }
    }
    private void initSQToolbar(){
//        assert toolbar!=null;
//        toolbar.cancelNavi();
//        toolbar.setTitle("");
//        toolbar.addMenu(TagFinal.ONE_INT, R.drawable.main_oa_set);
//        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//        });
        title.setText("成都市新生路小学");
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



    private void initView(){
        user_name.setText(Base.user.getName());
        if (StringJudge.isEmpty(Base.user.getHead_pic())){
            Glide.with(mActivity)
                    .load(R.drawable.radius_oval_gray)
                    .apply(new RequestOptions().circleCrop())
                    .into(user_head);
        }else{
            Glide.with(mActivity)
                    .load(Base.user.getHead_pic())
                    .apply(new RequestOptions().circleCrop())
                    .into(user_head);
        }
    }




    @OnClick(R.id.bar_menu)
    void setMenu(){
        startActivity(new Intent(mActivity,VsionDetailActivity.class));
    }

    @OnClick(R.id.user_one)
    void setUserOne(){
        Intent intent=new Intent(mActivity,AlterCllActivity.class);
        startActivityForResult(intent,TagFinal.UI_TAG );
    }
    @OnClick(R.id.user_two)
    void setUserTwo(){
        if (Base.user==null)return;
        album_select.showAtBottom();
    }

    @OnClick(R.id.user_three)
    void setUserThree(){
        if (Base.user==null)return;
        startActivity(new Intent(mActivity, AlterActivity.class));
    }

    @OnClick(R.id.user_four)
    void setUserFour(){
        if (Base.user==null)return;
        logout();
    }

    @OnClick(R.id.oa_one)
    void setOaOne(){
        if (isLogined("请登录")) {
            if(StringJudge.isEmpty(UserPreferences.getInstance().getUserAdmin().getIshqadmin())){
                getAdmin();
                return;
            }
            startActivity(new Intent(this, MaintainNewActivity.class));
        }
    }

    @OnClick(R.id.oa_two)
    void setOaTwo(){
        if (isLogined("请登录")) {
            if(StringJudge.isEmpty(UserPreferences.getInstance().getUserAdmin().getIsqjadmin())){
                getAdmin();
                return;
            }
            startActivity(new Intent(this, AttenNewActivity.class));
        }
    }

    @OnClick(R.id.oa_three)
    void setOaThree(){
        if (isLogined("请登录")) {
            if(Base.user.getUser_type().equals(TagFinal.USER_TYPE_TEA)){
                Intent intent = new Intent(mActivity, TeaEvaluateActivity.class);
                startActivity(intent);
            }else{
                toastShow("学生用户无此功能");
            }
        }
    }

    @OnClick(R.id.oa_four)
    void setOaFour(){
        if (isLogined("请登录")) {
            if(Base.user.getUser_type().equals(TagFinal.USER_TYPE_TEA)){
                Intent intent = new Intent(mActivity, TeaTJMainActivity.class);
                startActivity(intent);
            }else{
                toastShow("学生用户无此功能");
            }
        }
    }









    private void uploadPic(String path) {
        Object[] params = new Object[] {
                Base.user.getSession_key(),
                path,
                path };
        ParamsTask uploadPicTask = new ParamsTask(params, TagFinal.USER_ADD_HEAD, TagFinal.USER_ADD_HEAD);
        ExtraRunTask wrapTask = new ExtraRunTask(uploadPicTask);
        wrapTask.setExtraRunnable(extraRunnable);
        showProgressDialog("");
        execute(wrapTask);
    }

    private void logout() {
        Object[] params = new Object[] {
                Base.user.getSession_key(),
                "and",
                UserPreferences.getInstance().getJpushKey()
        };
        ParamsTask task = new ParamsTask(params, TagFinal.USER_LOGOUT,  TagFinal.USER_LOGOUT);
        showProgressDialog("");
        execute(task);
    }

    private ExtraRunTask.ExtraRunnable extraRunnable = new ExtraRunTask.ExtraRunnable() {

        @Override
        public Object[] onExecute(Object[] params) {
            String photoPath = params[1].toString();
            params[1] = Base64Utils.fileToBase64Str(photoPath);
            params[2] = FileTools.getFileName(photoPath);
            return params;
        }
    };


    private boolean isLogined(String tips) {
        if (Base.user == null) {
            toastShow(tips);
            Intent intent=new Intent(this, LoginRebirthActivity.class);
            startActivityForResult(intent,TagFinal.UI_REFRESH);
            return false;
        }
        return true;
    }

    /**
     * ----------------------------retrofit-----------------------
     */
    public void getCall() {

        if (Base.user==null)return;
        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        CallReq request = new CallReq();
        //获取参数
        reqBody.callReq = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().call_phone(reqEnvelop);
        call.enqueue(this);
    }




    public void getAdmin() {

        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        AdminReq request = new AdminReq();
        //获取参数
        reqBody.adminReq = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().get_user_right(reqEnvelop);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
                Logger.e(TagFinal.ZXX, s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            toastShow("数据出差了");
        }
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.callResponse!=null){
                String result=b.callResponse.callResult;
                Logger.e( call.request().headers().toString()+result);
                IntegralResult info=gson.fromJson(result,IntegralResult.class);
                if (StringJudge.isEmpty(info.getMobile())){
                    user_tell.setText(R.string.not_edit_phone_number);
                }else{
                    user_tell.setText(info.getMobile());
                    UserPreferences.getInstance().saveTell(info.getMobile());

                }
            }
            if (b.adminRes!=null){
                String result=b.adminRes.result;
                Logger.e(call.request().headers().toString()+result);
                if (StringJudge.isEmpty(result)){
                    toast("当前用户没有权限");
                    return ;
                }else{
                    toast("成功获取权限");
                }
                UserAdmin admin= gson.fromJson(result,UserAdmin.class);
                UserPreferences.getInstance().saveUserAdmin(admin);
//                List<ClassBean> class_list=admin.getClassinfo();
//                if (StringJudge.isEmpty(class_list)){
//                    UserPreferences.getInstance().saveClassIds("");
//                }else{
//                    List<String> stringList=new ArrayList<>();
//                    for (ClassBean bean:class_list){
//                        stringList.add(String.valueOf(bean.getClassid()));
//                    }
//                    UserPreferences.getInstance().saveClassIds(StringUtils.subStr(stringList,"," ));
//                }

            }
        }else{
            Logger.e(TagFinal.ZXX, "evn: null " );
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e( "onFailure  "+call.request().headers().toString() );
        dismissProgressDialog();
    }




    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        dismissProgressDialog();
        String name=wcfTask.getName();
        if (name.equals(TagFinal.USER_LOGOUT)){
            Base.user=null;
            GreenDaoManager.getInstance().clearUser();
            UserPreferences.getInstance().clearUserData();
            startActivity(new Intent(mActivity,LoginRebirthActivity.class));
            finish();
        }
        if (name.equals(TagFinal.USER_ADD_HEAD)){
//            Base.user.setHead_pic(uploadPath);
            GreenDaoManager.getInstance().clearUser();
            GreenDaoManager.getInstance().saveNote(Base.user);
            initView();
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        dismissProgressDialog();
        toastShow(getResources().getString(R.string.fail_do));
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }


    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    private void takePhoto() {
        FileCamera camera=new FileCamera(mActivity);
        startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
    }
    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    private void photoAlbum() {
        Intent intent;
        intent = new Intent(mActivity,AlbumOneActivity.class);
        Bundle b = new Bundle();
        b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
        b.putBoolean(TagFinal.ALBUM_SINGLE, true);
        intent.putExtras(b);
        startActivityForResult(intent,TagFinal.PHOTO_ALBUM);
    }
    @PermissionFail(requestCode = TagFinal.CAMERA)
    private void showCamere() {
        Toast.makeText(mActivity, R.string.permission_fail_camere, Toast.LENGTH_SHORT).show();
    }
    @PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
    private void showTip1() {
        Toast.makeText(mActivity, R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }



    public String uploadPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            switch (requestCode) {
                case TagFinal.CAMERA:
                    uploadPath=FileCamera.photo_camera;
                    uploadPic(uploadPath);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    uploadPath=photo_a.get(0).getPath();
                    uploadPic(uploadPath);
                    break;
                case TagFinal.UI_ADD:
                    initView();
                    getCall();
                    break;
                case TagFinal.UI_TAG:
                    getCall();
                    break;
                case TagFinal.UI_REFRESH:
                    initView();
                    getCall();
                    break;
            }

        }
    }


}
