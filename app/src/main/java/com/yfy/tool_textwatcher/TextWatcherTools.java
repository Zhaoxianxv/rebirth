package com.yfy.tool_textwatcher;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by yfyandr on 2017/10/16.
 */

public abstract class TextWatcherTools implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 限制小数位
     * @param s
     * @param i
     */
    public void i(Editable s, int i){
        String edit_String=s.toString();
        int posDot=edit_String.indexOf(".");
        if (posDot<0){
            return;
        }
        if (posDot==0){
            s.delete(0,edit_String.length());
            return;
        }
        if (edit_String.length()-posDot-i>i+1)
            s.delete(posDot+i+1,edit_String.length());//限制2位小数.
    }

    public void iedit(Editable s,int num){
        String edit_String=s.toString();
        int posDot=edit_String.indexOf(".");
        if (posDot<0){
            return;
        }
        if (posDot==0){
            s.delete(0,edit_String.length());
            return;
        }
        if (edit_String.length()-posDot-1>num)
            s.delete(posDot+num+1,edit_String.length());//限制2位小数.
    }

}
