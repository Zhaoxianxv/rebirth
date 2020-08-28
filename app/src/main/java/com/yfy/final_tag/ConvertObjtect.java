package com.yfy.final_tag;

import android.util.Log;

/**
 * Created by yfy1 on 2016/11/24.
 * 转换数据类型
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
            Log.e("zxx","ConvertObjtect:---不是int数据的字符串");
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
            Log.e("zxx","ConvertObjtect:---不是float数据的字符串");
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


    /**
     * new DecimalFormat(".0").format(score) 取小数位
     */

}
