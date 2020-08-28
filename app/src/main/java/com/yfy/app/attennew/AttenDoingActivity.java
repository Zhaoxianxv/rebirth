package com.yfy.app.attennew;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.ColorRgbUtil;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;
import com.yfy.tool_textwatcher.MyWatcher;
import com.yfy.view.SQToolBar;

public class AttenDoingActivity extends WcfActivity {

    private static final String TAG = AttenDoingActivity.class.getSimpleName();


    @Bind(R.id.atten_state_one)
    TextView state_one;
    @Bind(R.id.atten_state_two)
    TextView state_two;
    @Bind(R.id.atten_state_three)
    TextView state_three;
    @Bind(R.id.atten_doing_content)
    EditText editText;


    private String dealstate="",content="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atten_doing);
        initSQToolbar();
        getData();
        initView();

    }


    private String bean_id;
    private boolean is_end=false;
    private void getData(){
        bean_id=getIntent().getStringExtra(TagFinal.ID_TAG);
        is_end=getIntent().getBooleanExtra(TagFinal.TYPE_TAG,false);
    }



    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("请假审批");
        toolbar.addMenuText(1,R.string.submit);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                choiceState();

            }
        });
    }


    private void initView() {
        editText.addTextChangedListener(new MyWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                content=s.toString().trim();
            }
        });
        if (is_end){
            state_one.setVisibility(View.GONE);
            state_two.setVisibility(View.GONE);
            state_three.setVisibility(View.VISIBLE);
        }else{
            state_one.setVisibility(View.VISIBLE);
            state_two.setVisibility(View.VISIBLE);
            state_three.setVisibility(View.GONE);
        }
    }




    @OnClick(R.id.atten_state_one)
    void setOne(){
        dealstate="1";
        changeIng();
        state_one.setTextColor(ColorRgbUtil.getForestGreen());
    }
    @OnClick(R.id.atten_state_two)
    void setOk(){
        dealstate="3";
        changeIng();
        state_two.setTextColor(ColorRgbUtil.getForestGreen());
    }
    @OnClick(R.id.atten_state_three)
    void setDoing(){
        dealstate="5";
        changeIng();
        state_three.setTextColor(ColorRgbUtil.getForestGreen());
    }

    public void changeIng(){
        state_one.setTextColor(Color.rgb(128,128,128));
        state_two.setTextColor(Color.rgb(128,128,128));
        state_three.setTextColor(Color.rgb(128,128,128));
    }



    private void choiceState(){


        if (StringJudge.isEmpty(bean_id)){
            toast("id错误");
            return;
        }
        if (StringJudge.isEmpty(dealstate)){
            toast("请选择审批状态");
            return;
        }
        if (StringJudge.isEmpty(content)){
            content="";
        }

        Object[] params = new Object[] {
            Base.user.getSession_key(),
            bean_id,
            content,
            "",//table_plan
            dealstate
        };
        ParamsTask choice = new ParamsTask(params, TagFinal.ATTENNEW_ADMIN_DO, TagFinal.ATTENNEW_ADMIN_DO);
        showProgressDialog("");
        execute(choice);
    }




    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity())return false;
        dismissProgressDialog();
        if (StringJudge.isSuccess(gson,result)){
            setResult(RESULT_OK);
            finish();
        }else{
            toastShow(R.string.success_not_do);
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return ;
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
