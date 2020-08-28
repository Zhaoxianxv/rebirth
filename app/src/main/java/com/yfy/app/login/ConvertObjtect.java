package com.yfy.app.login;

import android.util.Log;

/**
 * Created by yfy1 on 2016/11/24.
 *
 */
public class ConvertObjtect {

    public static ConvertObjtect objtect;

    public static ConvertObjtect getInstance(){
        if (objtect==null){
            objtect=new ConvertObjtect();
        }
        return objtect;
    }


    /**
     * @return
     * String convert int
     */
    public int getInt(String s){
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Log.e("zxx","ConvertObjtect:");
            return -1;
        }
        int i=Integer.valueOf(s).intValue();
        return i;
    }
    /**
     * @return
     * String convert float
     */
    public float getFloat(String s){
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException e) {

            return -1;
        }
        float i=Float.valueOf(s).floatValue();
        return i;
    }

    public String getString(float f){

        return String.valueOf(f);
    }
    public String getString(int i){

        return String.valueOf(i);
    }


}
