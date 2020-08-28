
package com.yfy.charting_mp.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.yfy.charting_mp.data.LineData;
import com.yfy.charting_mp.interfaces.LineDataProvider;
import com.yfy.charting_mp.renderer.LineChartRenderer;
import com.yfy.charting_mp.utils.FillFormatter;

/**
 * Chart that draws lines, surfaces, circles, ...
 * 
 * @author Philipp Jahoda
 */
public class LineChart extends BarLineChartBase<LineData> implements LineDataProvider {

    private FillFormatter mFillFormatter;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new LineChartRenderer(this, mAnimator, mViewPortHandler);
        mFillFormatter = new DefaultFillFormatter();
    }

    @Override
    protected void calcMinMax() {
        super.calcMinMax();

        if (mDeltaX == 0 && mData.getYValCount() > 0)
            mDeltaX = 1;
    }

    @Override
    public void setFillFormatter(FillFormatter formatter) {

        if (formatter == null)
            formatter = new DefaultFillFormatter();
        else
            mFillFormatter = formatter;
    }

    @Override
    public FillFormatter getFillFormatter() {
        return mFillFormatter;
    }
    
    @Override
    public LineData getLineData() {
        return mData;
    }
}
