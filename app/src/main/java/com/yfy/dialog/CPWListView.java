package com.yfy.dialog;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.yfy.rebirth.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 弹窗视图
 */
public class CPWListView extends PopupWindow  {
	private Activity context;
	private ListView listview;
	private PopListAdapter adapter;
    private List<String> datas;

    public void setDatas(List<String> datas) {
        this.datas = datas;
        adapter.setDatas(datas);
    }

    public void setListview(ListView listview) {
        this.listview = listview;
    }

    public CPWListView(Activity context) {
		super(context);
		this.context = context;
		initalize();
	}

	private void initalize() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_dialog_listview, null);
        listview = view.findViewById(R.id.pop_list);//发起群聊
        adapter=new PopListAdapter(context);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选中的参数
//                int index = state_list .getCheckedItemPosition();     // 即获取选中位置
                if(ListView.INVALID_POSITION != position) {

                    if (listenner!=null){
                        listenner.onClick(position);
                    }
                }
            }
        });
		setContentView(view);
		initWindow();
	}

	private void initWindow() {
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 0.8));//width比例

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
				backgroundAlpha( context, 1.0f);
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





    public class PopListAdapter extends BaseAdapter {
        private Context context;
        private List<String> datas;
        public void setDatas(List<String> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }
        public PopListAdapter(Context context) {
            this.datas = new ArrayList<>();
            this.context = context;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PopListAdapter.ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new PopListAdapter.ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.public_checked_textview , null);
                viewHolder.name = convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (PopListAdapter.ViewHolder)convertView.getTag();
            }
            viewHolder.name.setText(datas.get(position));
            return convertView;
        }

        public class ViewHolder{
            CheckedTextView name;
        }

    }








	private OnPopClickListenner listenner = null;

	public void setOnPopClickListenner(OnPopClickListenner listenner) {
		this.listenner = listenner;
	}

	public static interface OnPopClickListenner {
		public void onClick(int index);
	}
}