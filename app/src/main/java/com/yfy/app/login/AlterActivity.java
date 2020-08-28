package com.yfy.app.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.StringJudge;
import com.yfy.json.JsonParser;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;
import com.yfy.view.SQToolBar;

public class AlterActivity extends WcfActivity {


    @Bind(R.id.alter_old_password)
    EditText old;
    @Bind(R.id.alter_new_first_password)
    EditText first;
    @Bind(R.id.alter_new_again_password)
    EditText again;
    private String oldpass,firstpass,againpass;
    private final String resetpassword="resetpassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_alter);
        initSQToolbar();
        initView();
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Log.e("zxx","alter "+result);
        dismissProgressDialog();
        if (JsonParser.isSuccess(result)){
            Base.user=null;
            UserPreferences.getInstance().clearUserData();
//            GreenDaoManager.getInstance().clearUser();
            toast("密码修改成功，请重新登录");
            setResult(RESULT_OK);
            onPageBack();
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        dismissProgressDialog();

    }

    public void initSQToolbar(){
        assert toolbar!=null;
        TextView titlebar=toolbar.setTitle("修改密码");
        TextView menuOne=toolbar.addMenuText(1,R.string.ok);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isSend()){
                    alterpass();
                }

            }
        });
    }
    public void initView(){

    }
    public boolean isSend(){
        oldpass=old.getText().toString().trim();
        firstpass=first.getText().toString().trim();
        againpass=again.getText().toString().trim();
        if (StringJudge.isEmpty(oldpass)){
            toast("请输入密码");
            return false;
        }
        if (StringJudge.isEmpty(firstpass)){
            toast("请输入新密码");
            return false;
        }
        if (StringJudge.isEmpty(againpass)){
            toast("请再次输入新密码");
            return false;
        }
        if (firstpass.equals(againpass)){

        }else{
            toast("新密码输入不一致");
//            Log.e("zxx","alter "+firstpass+"  "+againpass);
            return false;
        }
        return true;
    }


    private void alterpass() {

        Object[] params = new Object[] {
                Base.user.getSession_key(),
                oldpass,
                firstpass,
                };
        ParamsTask loginTask = new ParamsTask(params, resetpassword, resetpassword);
        showProgressDialog("");
        execute(loginTask);
    }
}
