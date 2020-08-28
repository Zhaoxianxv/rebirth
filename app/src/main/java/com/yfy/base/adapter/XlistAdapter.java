package com.yfy.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yfy1 on 2017/1/11.
 */

public abstract class XlistAdapter<TItem> extends BaseAdapter {

    protected LayoutInflater inflater;
    protected Context context;
    protected List<TItem> list;
    private ResInfo resInfo;


    public XlistAdapter(Context context, List<TItem> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        init();
    }


    private void init() {
        resInfo = getResInfo();
        if (resInfo != null) {
            if (resInfo.initIds == null)
                resInfo.initIds = new int[] {};
            if (resInfo.listnnerIds == null)
                resInfo.listnnerIds = new int[] {};
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int position2 = i;
        final ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            view=inflater.inflate(resInfo.layout,null);
            for (int id : resInfo.initIds) {
                holder.setView(id, view.findViewById(id)); //
            }
            view.setTag(holder);
            onceInit(i, holder, list);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        for (int id : resInfo.listnnerIds) {
            holder.getView(View.class, id).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onInnerClick(v, position2, list, holder);
                        }
                    });
        }
        renderData(i, holder, list);

        return view;
    }




    private OnAdapterListenner<TItem> listenner = null;

    public void setOnAdapterListenner(OnAdapterListenner<TItem> listenner) {
        this.listenner = listenner;
    }

    public static interface OnAdapterListenner<TItem> {

        public void onAdapterClick(View v,
                                   int position,
                                   List<TItem> list,
                                   XlistAdapter<TItem> adapter,
                                   XlistAdapter<TItem>.ViewHolder holder);
    }

    public void onInnerClick(View v, int position, List<TItem> list, ViewHolder holder) {
        if (listenner != null) {
            listenner.onAdapterClick(v, position, list, this, holder);
        }
    }


    protected void onceInit(int position, ViewHolder holder, List<TItem> list) {}


    public abstract void renderData(int position, ViewHolder holder, List<TItem> list);



    public void setDataList(List<TItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }



    public static class ResInfo {
        public int[] initIds;
        public int[] listnnerIds;
        public int layout;
    }

    public class ViewHolder{
        SparseArray<Object> objectMaps = new SparseArray<Object>();

        public void setView(int key, View v) {
            setObject(key, v);
        }

        public void setObject(int key, Object value) {
            objectMaps.put(key, value);
        }

        @SuppressWarnings("unchecked")
        public <T> T getView(Class<T> clazz, int key) {
            return (T) this.objectMaps.get(key);
        }

        public boolean setText(int resId, String text) {
            View view = getView(View.class, resId);
            if (view != null && view instanceof TextView) {
                ((TextView) view).setText(text);
                return true;
            }
            return false;
        }

        public boolean setVisible(int resId, int visiblity) {
            View view = getView(View.class, resId);
            if (view instanceof View) {
                if (visiblity != view.getVisibility()) {
                    view.setVisibility(visiblity);
                }
                return true;
            }
            return false;
        }

    }


    public abstract XlistAdapter.ResInfo getResInfo();


    public void notifyDataSetChanged(List<TItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
