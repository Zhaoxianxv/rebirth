package com.yfy.app.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.RegexUtils;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.json.JsonParser;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;
import com.yfy.view.SQToolBar;

public class AlterCllActivity extends WcfActivity {


    private static final String TAG = AlterCllActivity.class.getSimpleName();
    @Bind(R.id.call_edit_first)
    EditText first;
    @Bind(R.id.call_edit_again)
    EditText again;
    private String first_editor,again_editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_call_edit);
        initSQToolbar();
        if (StringJudge.isEmpty(UserPreferences.getInstance().getTell())){
        }else{
            first.setText(UserPreferences.getInstance().getTell());
        }
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        dismissProgressDialog();
        if (JsonParser.isSuccess(result)){
            toast("联系号码设置成功！");
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
        TextView titlebar=toolbar.setTitle("联系电话");
        TextView menuOne=toolbar.addMenuText(1,R.string.ok);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                closeKeyWord();
                isSend();

            }
        });
    }

    public void  isSend(){
        first_editor=first.getText().toString().trim();
        again_editor=again.getText().toString().trim();
        if (StringJudge.isEmpty(first_editor)){
            toast("请输入电话号码");
            return ;
        }
        if (StringJudge.isEmpty(first_editor)){
            toast("请再次输入电话号码");
            return ;
        }
        if (first_editor.equals(again_editor)){
            alterpass(first_editor);
        }else{
            toast("确认号码是否一致！");
        }
    }


    private void alterpass(String no) {

        if (RegexUtils.isMobilePhoneNumber(no)){

        }else{
            toast("支持：13，14，15，17，18，19 .手机号段");
            return;
        }

        Object[] params = new Object[] {
                Base.user.getSession_key(),
                no,};
        ParamsTask loginTask = new ParamsTask(params, TagFinal.USER_SET_MOBILE, TagFinal.USER_SET_MOBILE);
        execute(loginTask);
        showProgressDialog("");
    }
}
