package com.yfy.app.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import com.google.gson.Gson;
import com.yfy.app.RebirthMainActivity;
import com.yfy.app.info_submit.activity.AuthenticationActivity;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.db.GreenDaoManager;
import com.yfy.db.User;
import com.yfy.db.UserPreferences;
import com.yfy.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.*;
import com.yfy.json.JsonParser;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;

import static com.yfy.final_tag.RxCaptcha.TYPE.NUMBER;

/**
 * A login screen that offers login via email/password.
 *
 *
 */
public class LoginRebirthActivity extends WcfActivity {

    private final static String TAG = LoginRebirthActivity.class.getSimpleName();

    @Bind(R.id.login_password)
    EditText password;
    @Bind(R.id.login_phone)
    EditText phone;

    private String account, passwords;


    private String userType;

    @Bind(R.id.login_code_image)
    ImageView code_icon;
    @Bind(R.id.login_code)
    EditText code;
    private String edit_code = "";
    private String code_s="";
    private RxCaptcha rxCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_rebirth_main);
        initDialog();
        rxCaptcha=RxCaptcha.build();
        rxCaptcha
                .codeLength(4)
                .fontSize(60)
                .lineNumber(2)
                .size(200, 70)
                .type(NUMBER)
                .into(code_icon);
        code_s=rxCaptcha.getCode();
    }


    @OnClick(R.id.login_code_image)
    void setImage(){
        rxCaptcha=RxCaptcha.build();
        rxCaptcha
                .codeLength(4)
                .fontSize(60)
                .lineNumber(2)
                .size(200, 70)
                .type(NUMBER)
                .into(code_icon);
        code_s=rxCaptcha.getCode();
    }




    @OnClick( R.id.login_buttom)
    void setlogin(){
        account=phone.getText().toString();
        passwords=password.getText().toString();
        if (Utils.isEmpty(passwords)) {
            toast( R.string.app_login_password);
            return;
        }
        if (Utils.isEmpty(account)) {
            toast(R.string.app_login_phone);
            return;
        }


        edit_code=code.getText().toString();
        if (StringJudge.isEmpty(edit_code)) {
            toastShow(R.string.please_edit_code);
            return ;
        }
        if (!edit_code.equals(code_s)){
            toastShow(R.string.please_edit_yse_code);
            return ;
        }
        closeKeyWord();

        album_select.showAtBottom();
    }



    ConfirmAlbumWindow album_select;
    private void initDialog() {
        album_select = new ConfirmAlbumWindow(mActivity);
        album_select.setOne_select("教师");
        album_select.setTwo_select("学生");
        album_select.setName("请选择账号类型");
        album_select.setOnPopClickListenner(new ConfirmAlbumWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.popu_select_one:
                        userType = "2";
                        startLogin();
                        break;
                    case R.id.popu_select_two:
                        userType = "1";
                        startLogin();
                        break;
                }

            }
        });
    }

    private void startLogin() {
        //登陆时传的Constants.APP_ID：
        String apikey=JPushInterface.getRegistrationID(mActivity);
        if(apikey==null){
            apikey="";
        }
        Object[] params = new Object[] {
                account,
                passwords,
                userType,
                apikey,
                "and" };

        Log.e("zxx","apikey"+apikey);
        ParamsTask loginTask = new ParamsTask(params, TagFinal.LOGIN, TagFinal.LOGIN);
        showProgressDialog("");
        execute(loginTask);
    }




    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Log.e("zxx", result);
        if (!isActivity())return false;
        dismissProgressDialog();

        if (JsonParser.isSuccess(result)) {
            toast("登录成功");
            Gson gson=new Gson();
            UserInfor userinfor= gson.fromJson(result,UserInfor.class);
            //(Long id, @NotNull String session_key, int class_id, int fxt_id,
            //            @NotNull String head_pic, @NotNull String user_id, @NotNull String name,
            //            @NotNull String username, @NotNull String pass_word,
            //            @NotNull String user_type)
            User user=new User(
                    null,
                    userinfor.getSession_key(),
                    0,
                    userinfor.getUserinfo().getFxid(),
                    userinfor.getUserinfo().getHeadPic(),
                    userinfor.getUserinfo().getId(),
                    userinfor.getUserinfo().getName(),
                    userinfor.getUserinfo().getUsername(),
                    passwords,
                    userType.equals("1")? TagFinal.USER_TYPE_STU : TagFinal.USER_TYPE_TEA
            );
            Base.user=user;
            UserPreferences.getInstance().saveSession_key(user.getSession_key());
            GreenDaoManager.getInstance().clearUser();
            GreenDaoManager.getInstance().saveNote(user);

            startActivity(new Intent(mActivity,RebirthMainActivity.class));
            setResult(RESULT_OK);
            onPageBack();
        } else {
            toast(JsonParser.getErrorCode(result));
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return;
        dismissProgressDialog();
        toast(R.string.fail_login_not);
    }
//    @OnClick(R.id.forget_login_password)
//    void setforget(){
//        Intent intent=new Intent(mActivity,PhoneCodectivity.class);
//        startActivity(intent);
//    }
    @OnClick(R.id.login_stu)
    void setforget(){
        Intent intent=new Intent(mActivity,AuthenticationActivity.class);
        startActivity(intent );
    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}

