
package com.yfy.charting_mp.data;

import com.yfy.charting_mp.data.BarData;
import com.yfy.charting_mp.data.BubbleData;
import com.yfy.charting_mp.data.CandleData;
import com.yfy.charting_mp.data.LineData;
import com.yfy.charting_mp.data.ScatterData;

import java.util.List;

/**
 * Data object that allows the combination of Line-, Bar-, Scatter-, Bubble- and
 * CandleData. Used in the CombinedChart class.
 * 
 * @author Philipp Jahoda
 */
public class CombinedData extends BarLineScatterCandleData<BarLineScatterCandleDataSet<?>> {

    private com.yfy.charting_mp.data.LineData mLineData;
    private com.yfy.charting_mp.data.BarData mBarData;
    private com.yfy.charting_mp.data.ScatterData mScatterData;
    private com.yfy.charting_mp.data.CandleData mCandleData;
    private com.yfy.charting_mp.data.BubbleData mBubbleData;

    public CombinedData() {
        super();
    }

    public CombinedData(List<String> xVals) {
        super(xVals);
    }

    public CombinedData(String[] xVals) {
        super(xVals);
    }

    public void setData(com.yfy.charting_mp.data.LineData data) {
        mLineData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.yfy.charting_mp.data.BarData data) {
        mBarData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.yfy.charting_mp.data.ScatterData data) {
        mScatterData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.yfy.charting_mp.data.CandleData data) {
        mCandleData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public void setData(com.yfy.charting_mp.data.BubbleData data) {
        mBubbleData = data;
        mDataSets.addAll(data.getDataSets());
        init();
    }

    public BubbleData getBubbleData() {
        return mBubbleData;
    }

    public LineData getLineData() {
        return mLineData;
    }

    public BarData getBarData() {
        return mBarData;
    }

    public ScatterData getScatterData() {
        return mScatterData;
    }

    public CandleData getCandleData() {
        return mCandleData;
    }

    @Override
    public void notifyDataChanged() {
        if (mLineData != null)
            mLineData.notifyDataChanged();
        if (mBarData != null)
            mBarData.notifyDataChanged();
        if (mCandleData != null)
            mCandleData.notifyDataChanged();
        if (mScatterData != null)
            mScatterData.notifyDataChanged();
        if (mBubbleData != null)
            mBubbleData.notifyDataChanged();
    }
}
