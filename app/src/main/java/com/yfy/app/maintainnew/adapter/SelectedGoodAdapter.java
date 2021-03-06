package com.yfy.app.maintainnew.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yfy.app.maintainnew.bean.GoodBean;
import com.yfy.final_tag.ColorRgbUtil;
import com.yfy.final_tag.TagFinal;
import com.yfy.rebirth.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedGoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<GoodBean> dataList;
    private Activity mContext;

    public void setDataList(List<GoodBean> dataList) {
        this.dataList = dataList;
//        getRandomHeights(dataList);
    }

    public List<GoodBean> getDataList() {
        return dataList;
    }

    // 当前加载状态，默认为加载完成
    private int loadState = 2;


    public SelectedGoodAdapter(Activity mContext){
        this.mContext=mContext;
        this.dataList = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return TagFinal.TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_singe_item_layout, parent, false);
            return new RecyclerViewHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RecyclerViewHolder) {
            RecyclerViewHolder reHolder = (RecyclerViewHolder) holder;
            reHolder.bean=dataList.get(position);
            reHolder.index=position;
            reHolder.name.setText(reHolder.bean.getGoodsname());
            if (reHolder.bean.isSelect()){
                reHolder.name.setTextColor(ColorRgbUtil.getBaseColor());
            }else{
                reHolder.name.setTextColor(ColorRgbUtil.getBaseText());
            }

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        GoodBean bean;
        int index;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.selected_item_name);
            type= (TextView) itemView.findViewById(R.id.selected_item_type);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //单/多选
                    if (bean.isSelect())return;
                    int i=0;
                    for (GoodBean b:dataList){
                        if (b.isSelect()){
                            b.setSelect(false);
                            notifyItemChanged(i,b );
                        }
                       i++;
                    }
                    bean.setSelect(true);
                    notifyItemChanged(index,bean);
                }
            });

        }
    }




    /**
     * 设置上拉加载状态
     *
     * @param loadState 1.正在加载 2.加载完成 3.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }









}
