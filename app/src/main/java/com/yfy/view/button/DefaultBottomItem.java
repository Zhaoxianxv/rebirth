package com.yfy.view.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.yfy.rebirth.R;


/**
 * Created by Aj Liao on 2016/3/7.
 */
public class DefaultBottomItem extends BottomItem {
    private Drawable normalDrawable;
    private Drawable pressDrawable;
    private String text;

    private ImageView bottomIcon;
    private TextView bottomText;
    private View bottomBadge;
    private TextView bottomTextBadge;

    public DefaultBottomItem(Context context) {
        super(context);
    }

    public DefaultBottomItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.bottom);
        text = a.getString(R.styleable.bottom_text);
        normalDrawable = a.getDrawable(R.styleable.bottom_normalRes);
        pressDrawable = a.getDrawable(R.styleable.bottom_pressRes);
        a.recycle();
    }

    public DefaultBottomItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    public DefaultBottomItem init() {
        bottomIcon = (ImageView) findViewById(R.id.bottom_icons);
        bottomIcon.setImageDrawable(normalDrawable);
        bottomText = (TextView) findViewById(R.id.bottom_text);
        bottomText.setText(text);

        bottomBadge = findViewById(R.id.bottom_badge);
        bottomTextBadge = (TextView) findViewById(R.id.bottom_text_badge);
        return this;
    }


    public void setbottomText(String name){
        text=name;
        bottomText.setText(text);
    }
    public void setbottomText(String name, Typeface typeface){
        text=name;
        bottomText.setText(text);
        bottomText.setTypeface(typeface);

    }
    /**
     * 改变字的颜色
     * @param checked
     * @param
     */
    @Override
    public void switchViewStatus(boolean checked) {
        switchViewStatus(checked,Color.WHITE);
    }
    @Override
    public void switchViewStatus(boolean checked,final int i) {
       switchViewStatus(checked,i,0XFF686868);
    }
    @Override
    public void switchViewStatus(boolean checked, int un, int dwon) {
        if (checked) {
            bottomIcon.setImageDrawable(pressDrawable);
            bottomIcon.setColorFilter(un);
            bottomText.setTextColor(un);
        } else {
            bottomIcon.setImageDrawable(normalDrawable);
            bottomIcon.setColorFilter(dwon);
            bottomText.setTextColor(dwon);
        }
    }

    @Override
    public void showBadge() {
        bottomBadge.setVisibility(VISIBLE);
        bottomTextBadge.setVisibility(GONE);
    }

    @Override
    public void hideBadge() {
        bottomBadge.setVisibility(GONE);
        bottomTextBadge.setVisibility(GONE);
    }

    @Override
    public void showBadge(int count) {
        bottomBadge.setVisibility(GONE);
        bottomTextBadge.setVisibility(VISIBLE);
        if (count < 100) {
            bottomTextBadge.setText(count + "");
        } else {
            bottomTextBadge.setText("..");
        }
    }
}
