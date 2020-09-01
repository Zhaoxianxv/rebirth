package com.yfy.app.tea_evaluate.adpter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yfy.app.login.ConvertObjtect;
import com.yfy.app.tea_evaluate.ChartEvaluateActivity;
import com.yfy.app.tea_evaluate.bean.BarItem;
import com.yfy.final_tag.StringUtils;
import com.yfy.final_tag.TagFinal;
import com.yfy.rebirth.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class BarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<BarItem> dataList;
    private Activity mContext;

    public void setDataList(List<BarItem> dataList) {
        this.dataList = dataList;
    }
    // 当前加载状态，默认为加载完成
    private int loadState = 2;


    public BarAdapter(Activity mContext){
        this.mContext=mContext;
        this.dataList = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position==0){
            return TagFinal.TYPE_TOP;
        }else {
            return TagFinal.TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tea_bar_item, parent, false);
            return new RecyclerViewHolder(view);

        }
        if (viewType == TagFinal.TYPE_TOP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tea_bar_top, parent, false);
            return new TopHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RecyclerViewHolder) {
            RecyclerViewHolder reHolder = (RecyclerViewHolder) holder;
            reHolder.bean=dataList.get(position);
            reHolder.name.setText(reHolder.bean.getYearname());

            float score= ConvertObjtect.getInstance().getFloat(reHolder.bean.getScore());
            float max_score= ConvertObjtect.getInstance().getFloat(reHolder.bean.getMax_score());
            float min_score= ConvertObjtect.getInstance().getFloat(reHolder.bean.getMiddle_score());
            ViewGroup.LayoutParams one=reHolder.score_one.getLayoutParams();
            ViewGroup.LayoutParams two=reHolder.score_two.getLayoutParams();
            float mheiht=reHolder.tag_one.getMeasuredHeight();
            if (score==0||max_score==0){
                reHolder.tag_one.setProgress(0);
                one.height=ViewGroup.LayoutParams.WRAP_CONTENT;
            }else{
                if (score==max_score){
                    one.height=ViewGroup.LayoutParams.MATCH_PARENT;
                }else{
//                    one.height=(int)(score/max_score*mheiht);
                    one.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                reHolder.tag_one.setProgress((int)(score/max_score*100));
            }

            if (min_score==0||max_score==0){
                reHolder.tag_two.setProgress(0);
                two.height=ViewGroup.LayoutParams.WRAP_CONTENT;
            }else{
                reHolder.tag_two.setProgress((int)(min_score/max_score*100));
                if (min_score==max_score){
                    two.height=ViewGroup.LayoutParams.MATCH_PARENT;//能马上刷新
                }else{
//                    two.height=(int)(score/max_score*mheiht);//不能马上刷新

                    two.height=ViewGroup.LayoutParams.WRAP_CONTENT;}
            }
            reHolder.score_one.setLayoutParams(one);
            reHolder.score_two.setLayoutParams(two);
            reHolder.score_one.setText(StringUtils.stringToGetTextJoint("得分:%1$s",new DecimalFormat(".0").format(score)));
            reHolder.score_two.setText(StringUtils.stringToGetTextJoint("中分:%1$s",new DecimalFormat(".0").format(min_score)));
        }
        if (holder instanceof TopHolder) {
            TopHolder top = (TopHolder) holder;
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView name;
        TextView score_one;
        TextView score_two;
        ProgressBar tag_one;
        ProgressBar tag_two;
        RelativeLayout layout;
        BarItem bean;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.bar_item_title);
            score_one=itemView.findViewById(R.id.bar_item_one);
            score_two=itemView.findViewById(R.id.bar_item_two);
            layout=itemView.findViewById(R.id.tea_bar_layout);
            tag_one=itemView.findViewById(R.id.bar_one_view);
            tag_two=itemView.findViewById(R.id.bar_two_view);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(mContext,ChartEvaluateActivity.class);
                    intent.putExtra(TagFinal.TITLE_TAG, bean.getYearname());
                    intent.putExtra(TagFinal.ID_TAG,bean.getId());
                    intent.putExtra("year",bean.getYear());
                    mContext.startActivity(intent);


                }
            });
        }
    }


    private class TopHolder extends RecyclerView.ViewHolder {
        TextView name;

        BarItem bean;
        TopHolder(View itemView) {
            super(itemView);

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





    /**
     * 柱状图辅助类
     */






}
