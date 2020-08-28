package com.yfy.app.album;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.TagFinal;
import com.yfy.glide.GlideTools;
import com.yfy.rebirth.R;
import com.yfy.view.image.PinchImageView;

public class SingePicShowActivity extends BaseActivity {


    private String url,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_singe_pic_show);
        getData();
        initSQToolbar();
    }


    private void initSQToolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.show_pic_one_title_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (title!=null){
            toolbar.setTitle(title);
        }else{
            toolbar.setTitle("返回");
        }
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void getData(){
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey(TagFinal.ALBUM_SINGE_URI)) {
                url = b.getString(TagFinal.ALBUM_SINGE_URI);
            }
            if (b.containsKey("title")) {
                title = b.getString("title");
            }
        }
        initView();
    }
    public void initView(){
        PinchImageView imageView= (PinchImageView) findViewById(R.id.big_url_pic);
        GlideTools.loadImage(mActivity,url ,imageView );

    }

}
