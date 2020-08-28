package com.yfy.dialog;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.yfy.final_tag.StringJudge;
import com.yfy.rebirth.R;


/**
 * 弹窗视图
 */
public class ConfirmAlbumWindow extends PopupWindow  {
	private Activity context;
	private TextView title, one, two,cancel;
	private String name,one_select,two_select;

	public ConfirmAlbumWindow(Activity context) {
		super(context);
		this.context = context;
		initalize();
	}

	public void setName(String name) {
		this.name = name;
		if (StringJudge.isEmpty(name)){
			title.setText("");
		}else{
			title.setText(name);
		}

	}

	public void setOne_select(String one_select) {
		this.one_select = one_select;
		if (StringJudge.isEmpty(one_select)){
			one.setText("");
		}else{
			one.setText(one_select);
		}

	}

	public void setTwo_select(String two_select) {
		this.two_select = two_select;
		if (StringJudge.isEmpty(two_select)){
			two.setText("");
		}else{
			two.setText(two_select);
		}
	}

	private void initalize() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_dialog_pic, null);
		title = view.findViewById(R.id.popu_select_title);//
		one = view.findViewById(R.id.popu_select_one);//
		two = view.findViewById(R.id.popu_select_two);//
		cancel = view.findViewById(R.id.popu_select_cancel);//



		one.setOnClickListener(onClickListener);
		two.setOnClickListener(onClickListener);
		cancel.setOnClickListener(onClickListener);
		setContentView(view);
		initWindow();
	}

	private void initWindow() {
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 1));//width比例

		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		backgroundAlpha( context, 1f);//0.0-1.0
		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha( context, 1f);
			}
		});
	}

	//设置添加屏幕的背景透明度
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}

	public void showAtBottom(View view) {
		//弹窗位置设置
		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
		//showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 10, 110);//有偏差
		//        listPopupWindow.setHorizontalOffset(Math.abs((view.getWidth()) / 2));//水平距离
		//        listPopupWindow.setVerticalOffset(0);//垂直距离
	}

	public void showAtCenter() {
		//弹窗位置设置
		backgroundAlpha( context, 0.5f);//0.0-1.0
//		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
		//showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 10, 110);//有偏差
		showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}

	public void showAtBottom() {
		//弹窗位置设置
		backgroundAlpha( context, 0.5f);//0.0-1.0
		showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
	}



	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (listenner != null) {
				listenner.onClick(v);
			}
			dismiss();
		}
	};

	private OnPopClickListenner listenner = null;

	public void setOnPopClickListenner(OnPopClickListenner listenner) {
		this.listenner = listenner;
	}

	public static interface OnPopClickListenner {
		public void onClick(View view);
	}
}