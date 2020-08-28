package com.yfy.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;


/**
 * Created by yfyandr on 2018/4/9.
 */

public class DialogOneEdit extends AlertDialog implements OnClickListener{



    private EditText edit;

    private OnEditSetListener mEditSetListenner;

    protected DialogOneEdit(Context context) {
        super(context);
    }
    protected DialogOneEdit(Context context, int themeResId) {
        super(context, themeResId);
    }



    public DialogOneEdit(Context context, int themeResId, OnEditSetListener mEditSetListenner) {
        super(context, themeResId);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int mWidth = dm.widthPixels;

        edit = new EditText(context);
        edit.setTextSize(17);
//        edit.setGravity(Gravity.CENTER_VERTICAL);
//        editText.setInputType();
        edit.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        edit.setHeight(RecyclerView.LayoutParams.WRAP_CONTENT);
//        edit.setMinHeight(dip2px(context,150f));
//        edit.setPadding(15, 0, 15, 0);//会影响布局
        edit.setSingleLine(true);
//        edit.setBackgroundColor(Color.TRANSPARENT);
        setView(edit);
        setButton(BUTTON_POSITIVE, "确定", this);//按钮
        setButton(BUTTON_NEGATIVE, "取消", this);//按钮
//        if (watcher!=null){
//            edit.addTextChangedListener(watcher);
//        }
        this.mEditSetListenner=mEditSetListenner;
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mEditSetListenner != null) {
                    mEditSetListenner.onEditSet(edit.getText().toString().trim());
                }
                dialog.cancel();
                break;
            case BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }

    public interface OnEditSetListener {

        void onEditSet(String content);
    }
    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
