package com.yfy.app.tea_evaluate.adpter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yfy.app.tea_evaluate.bean.YearData;
import com.yfy.final_tag.TagFinal;
import com.yfy.rebirth.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectYearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<YearData> dataList;
    private Activity mContext;

    public void setDataList(List<YearData> dataList) {
        this.dataList = dataList;
//        getRandomHeights(dataList);
    }
    // 当前加载状态，默认为加载完成
    private int loadState = 2;


    public SelectYearAdapter(Activity mContext){
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_singe_item, parent, false);
            return new RecyclerViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RecyclerViewHolder) {
            RecyclerViewHolder reHolder = (RecyclerViewHolder) holder;
            reHolder.bean=dataList.get(position);
            reHolder.name.setText(reHolder.bean.getYearname());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        RelativeLayout layout;
        YearData bean;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.selected_singe_item_name);
            type= (TextView) itemView.findViewById(R.id.selected_singe_item_type);
            layout=itemView.findViewById(R.id.selected_singe_item_layout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //单/多选
                    Intent intent=new Intent();
                    intent.putExtra(TagFinal.OBJECT_TAG,bean);
                    mContext.setResult(RESULT_OK,intent);
                    mContext.finish();
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
