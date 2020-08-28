package com.yfy.app.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.net.*;
import com.yfy.app.net.user.ResetCodeReq;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.*;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class PhoneCodectivity extends WcfActivity implements Callback<ResEnv> {
    private static final String TAG = PhoneCodectivity.class.getSimpleName();
    @Bind(R.id.reset_edit_phone)
    EditText edit_phone;
    @Bind(R.id.reset_edit_code)
    EditText edit_code;
    @Bind(R.id.send_code)
    TextView send_code;
    @Bind(R.id.reset_edit_new_password)
    EditText edit_new_pass;
    @Bind(R.id.reset_alter_edit_new_password)
    EditText alter_edit_pass;
    MyCountDownTimer mycount;


    private String code,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_phone_code);
        mycount = new MyCountDownTimer(60000, 1000);
        initSQToolbar();
    }

    @Override
    protected void onDestroy() {
        mycount.cancel();
        super.onDestroy();
        mycount = null;
    }

    private void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("重置密码");
    }
    @OnClick(R.id.send_code)
    void setSendcode(){
        String tell=edit_phone.getText().toString().trim();
        if (StringJudge.isEmpty(tell)){
            toastShow(R.string.please_edit_phone);
            return;
        }
        if (RegexUtils.isMobilePhoneNumber(tell)){
            sendCode(true,tell);
        }else{
            toastShow("支持：13，14，15，17，18，19 .手机号段");
        }
//        mycount.start();

    }

    private String first_password;
    @OnClick(R.id.forget_commit_btn)
    void setBtn(){
        String e_code=edit_code.getText().toString().trim();
        if (StringJudge.isEmpty(e_code)){
            toastShow(R.string.please_edit_code);
            return;
        }
        if (!code.equals(e_code)){
            toastShow("验证码输入错误！");
            return;
        }

        first_password = edit_new_pass.getText().toString().trim();
        String alter_password=alter_edit_pass.getText().toString().trim();
        if (StringJudge.isEmpty(first_password)){
            toastShow(R.string.please_edit_password);
            return;
        }
        if(!RegexUtils.isCharAndNumbar(first_password)){
            toastShow("密码必须是六位以上且包含字母、数字");
            return;
        }

        if (StringJudge.isEmpty(alter_password)){
            toastShow("请再次输入新密码");
            return;
        }

        if (first_password.equals(alter_password)){
            resetPassWord();
        }else{
            toastShow("新密码输入不一致");
            return ;
        }
    }

    /**
     * 倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (send_code==null)return;
            send_code.setClickable(true);
            send_code.setBackgroundResource(R.drawable.send_code_reg);
            send_code.setTextColor(ColorRgbUtil.getBaseColor());
            send_code.setText("重新发送");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (send_code==null)return;
            send_code.setBackgroundResource(R.drawable.radius4_graybg_grayline);
            send_code.setTextColor(ColorRgbUtil.getBaseColor());
            send_code.setClickable(false);
            send_code.setText(StringUtils.getTextJoint("(%1$d)重试", millisUntilFinished / 1000 ));
        }
    }







    private void resetPassWord() {
        //登陆时传的Constants.APP_ID：
        if (StringJudge.isEmpty(user_id)){
            toastShow("请重新获取验证码");
            return;
        }
        Object[] params = new Object[] {
                user_id,
                first_password,
                "tea" };
        ParamsTask loginTask = new ParamsTask(params, TagFinal.RESET_PASSWORD, TagFinal.RESET_PASSWORD);
        showProgressDialog("");
        execute(loginTask);
    }
    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity())return false;
        dismissProgressDialog();
        String name=wcfTask.getName();
        Logger.e(result);
        if (name.equals(TagFinal.RESET_PASSWORD)){
            BaseRes res=gson.fromJson(result,BaseRes.class);
            if (res.getResult().equals(TagFinal.TRUE)){
                toastShow("密码成功找回");
                finish();
            }else{
                toastShow(res.getError_code());
            }

        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return;
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }





    /**
     * ----------------------------retrofit-----------------------
     */

    public void sendCode(boolean is,String phone_num_s)  {
        ReqEnv reqEnvelop = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        ResetCodeReq request = new ResetCodeReq();
        //获取参数
        request.setPhone(phone_num_s);
        reqBody.phone_edit_code = request;
        reqEnvelop.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().get_phone_code(reqEnvelop);
        call.enqueue(this);
        if (is)showProgressDialog("");

    }

    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (response.code()==500){
            try {
                String s=response.errorBody().string();
                Logger.e(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            toastShow("数据出差了");
        }
        if (!isActivity())return;
        dismissProgressDialog();
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.phone_edit_code_res!=null){
                String result=b.phone_edit_code_res.result;
                Logger.e( call.request().headers().toString()+result);
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals(TagFinal.TRUE)){
                    mycount.start();
                    code=res.getVerification_Code();
                    user_id=res.getUserid();
//                    UserPreferences.getInstance().savePhoneCode(code);
//                    UserPreferences.getInstance().saveTimeLong(System.currentTimeMillis());
//                    UserPreferences.getInstance().saveIsCode(false);
                }else{
                    toastShow(res.getError_code());
                }
            }
        }else{
            toastShow(R.string.fail_do_not);
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        dismissProgressDialog();
        Logger.e( "onFailure  "+call.request().headers().toString() );
        toast(R.string.fail_do_not);

    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }

}
