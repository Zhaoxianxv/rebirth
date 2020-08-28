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
import com.yfy.rebirth.R;


/**
 * 弹窗视图
 */
public class ConfirmContentWindow extends PopupWindow  {
	private Activity context;
	private TextView title, content,ok;

	private String title_s,content_s,ok_s;

	public void setTitle_s(String title_s,String content_s,String ok_s) {
		this.title_s = title_s;
		this.content_s = content_s;
		this.ok_s = ok_s;
		title.setText(title_s);
		content.setText(content_s);
		ok.setText(ok_s);

	}


	public ConfirmContentWindow(Activity context) {
		super(context);
		this.context = context;
		initalize();
	}

	private void initalize() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_dialog_content, null);
		title = view.findViewById(R.id.pop_dialog_title);//发起群聊
		content = view.findViewById(R.id.pop_dialog_content);//发起群聊
		ok = view.findViewById(R.id.pop_dialog_ok);//发起群聊
        ok.setOnClickListener(onClickListener);
		setContentView(view);
		initWindow();
	}

	private void initWindow() {
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 0.7));//width比例

		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		backgroundAlpha( context, 1.0f);//0.0-1.0
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
		backgroundAlpha( context, 0.5f);//0.0-1.0
		//弹窗位置设置
//		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
		//showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 10, 110);//有偏差
		showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}

	public void showAtBottom() {
		//弹窗位置设置
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