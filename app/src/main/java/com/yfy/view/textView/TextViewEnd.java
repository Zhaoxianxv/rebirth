package com.yfy.view.textView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by yfyandr on 2017/9/15.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewEnd extends TextView {
    public TextViewEnd(Context context) {
        super(context);
    }

    public TextViewEnd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewEnd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
