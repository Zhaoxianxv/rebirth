package com.yfy.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.TagFinal;
import com.yfy.rebirth.R;

public class VsionDetailActivity extends BaseActivity
{


    @Bind(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsion_detail);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initSQtoolbar("关于我们");
    }
    private void initSQtoolbar(String title){
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.setNavi(R.drawable.ic_back);
        version.setText("(Version\t\t" + AppLess.$vername() + ")");
    }


    @OnClick(R.id.version_tag)
    void setTag(){

        Intent intent = new Intent(mActivity, WebActivity.class);
        Bundle b = new Bundle();
        b.putString(TagFinal.URI_TAG, TagFinal.HUAWEI);
        b.putString(TagFinal.TITLE_TAG, "隐私声明");
        intent.putExtras(b);
        startActivity(intent);
    }
}
