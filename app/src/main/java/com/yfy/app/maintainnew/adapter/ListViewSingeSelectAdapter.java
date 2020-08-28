package com.yfy.app.maintainnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import com.yfy.app.maintainnew.bean.Dobean;
import com.yfy.rebirth.R;

import java.util.List;

public class ListViewSingeSelectAdapter extends BaseAdapter {


    private Context context;
    private List<Dobean> datas;
//    private Handler                 handler;
    private int checked = -1;//初始选择为-1，position没有-1嘛，那就是谁都不选咯


    public void setDatas(List<Dobean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public ListViewSingeSelectAdapter(Context context, List<Dobean> datas) {
        this.datas = datas;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.public_checked_textview , null);
            viewHolder.name = convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(datas.get(position).getOperatename());
        return convertView;
    }

    public class ViewHolder{
        CheckedTextView name;
    }

}
