package com.yfy.app.tea_evaluate.adpter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yfy.app.tea_evaluate.BarActivity;
import com.yfy.app.tea_evaluate.bean.ChartBean;
import com.yfy.charting_mp.animation.Easing;
import com.yfy.charting_mp.charts.PieChart;
import com.yfy.charting_mp.components.Legend;
import com.yfy.charting_mp.data.Entry;
import com.yfy.charting_mp.data.PieData;
import com.yfy.charting_mp.data.PieDataSet;
import com.yfy.charting_mp.utils.ColorTemplate;
import com.yfy.charting_mp.utils.PercentFormatter;
import com.yfy.final_tag.*;
import com.yfy.rebirth.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class TeaTJMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    private List<ChartBean> dataList=new ArrayList<>();
    private Activity mContext;

    public void setDataList(List<ChartBean> dataList) {
        this.dataList = dataList;
    }
    // 当前加载状态，默认为加载完成
    private int loadState = 2;

    public TeaTJMainAdapter(Activity mContext){

        this.mContext=mContext;
        this.dataList = new ArrayList<>();
    }



    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView

        return dataList.get(position).getView_type();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //进行判断显示类型，来创建返回不同的View
        switch (viewType){
            case TagFinal.TYPE_ITEM:
                View view_date = LayoutInflater.from(parent.getContext()).inflate(R.layout.tea_item_date, parent, false);
                return new RecyclerDateHolder(view_date);
            case TagFinal.TYPE_TOP:
                View top_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tea_item_piechart, parent, false);
                return new PieHolder(top_view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RecyclerDateHolder) {
            RecyclerDateHolder reHolder = (RecyclerDateHolder) holder;
            reHolder.bean=dataList.get(position);
            reHolder.name.setText(reHolder.bean.getTitle());
            reHolder.date.setText(StringUtils.getTextJoint("%1$s个",reHolder.bean.getScore()));
        }
//        if (holder instanceof PieHolder) {
//            PieHolder pie = (PieHolder) holder;
////            pie.bean=dataList.get(position);
//            pie.setData();
//        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }

    private class RecyclerDateHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView name;
        ChartBean bean;
        RecyclerDateHolder(View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.tea_chioce_date);
            name= (TextView) itemView.findViewById(R.id.tea_chioce_naem);
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext,BarActivity.class);
                    intent.putExtra(TagFinal.OBJECT_TAG,bean.getId());
                    intent.putExtra(TagFinal.TITLE_TAG,bean.getTitle());
                    mContext.startActivityForResult(intent,TagFinal.UI_REFRESH );
                }
            });
        }
    }

    private class PieHolder extends RecyclerView.ViewHolder {
        PieChart mChart;
        ChartBean bean;
        PieHolder(View itemView) {
            super(itemView);
            mChart=  itemView.findViewById(R.id.chart1);
//
            mChart.setUsePercentValues(true);
            mChart.setDescription("");

            mChart.setDragDecelerationFrictionCoef(0.95f);


            mChart.setDrawHoleEnabled(true);
            mChart.setHoleColorTransparent(true);

            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);
            mChart.setHoleRadius(40f);
            mChart.setTransparentCircleRadius(50f);

            mChart.setDrawCenterText(true);
            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);

            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);

            // add a selection listener
//            mChart.setOnChartValueSelectedListener();





            mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
            // mChart.spin(2000, 0, 360);

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

        }


        private void setData() {

            float all_score=0f;
            List<ChartBean> pieData=new ArrayList<>();
            for (ChartBean chartBean:dataList){
                if (chartBean.getScore().equals("0")){
                    continue;
                }else{
                    all_score+=ConvertObjtect.getInstance().getFloat(chartBean.getScore());
                    pieData.add(chartBean);
                }
            }

            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            ArrayList<String> xVals = new ArrayList<String>();

            if (StringJudge.isEmpty(pieData)){
                xVals.add("无数据");
                yVals1.add(new Entry(5f, 0));
            }else{
                for (int i = 0; i < pieData.size(); i++) {
                    yVals1.add(new Entry(ConvertObjtect.getInstance().getFloat(pieData.get(i).getScore()), i));
                    xVals.add(pieData.get(i).getTitle());
                }
            }

            mChart.setCenterText(StringUtils.getTextJoint("总分:%1$s",StringUtils.getTwoTofloat(all_score)));
            // IMPORTANT: In a PieChart, no values (Entry) should have the same
            // xIndex (even if from different DataSets), since no values can be
            // drawn above each other.

            PieDataSet dataSet = new PieDataSet(yVals1, "");
            dataSet.setSliceSpace(1f);
            dataSet.setSelectionShift(5f);

            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<Integer>();


            colors.add(ColorRgbUtil.getOne());
            colors.add(ColorRgbUtil.getTwo());
            colors.add(ColorRgbUtil.getThree());
            colors.add(ColorRgbUtil.getFour());
            colors.add(ColorRgbUtil.getFive());
            colors.add(ColorRgbUtil.getSix());
            colors.add(ColorRgbUtil.getSeven());
            colors.add(ColorRgbUtil.getEigth());
            colors.add(ColorRgbUtil.getBaseColor());

            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);

            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(9f);
            data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tf);
            mChart.setData(data);

            // undo all highlights
            mChart.highlightValues(null);

            mChart.invalidate();
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
