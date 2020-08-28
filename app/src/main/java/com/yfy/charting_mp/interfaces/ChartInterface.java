
package com.yfy.charting_mp.interfaces;

import android.graphics.PointF;
import android.graphics.RectF;
import com.yfy.charting_mp.data.ChartData;
import com.yfy.charting_mp.utils.ValueFormatter;

/**
 * Interface that provides everything there is to know about the dimensions,
 * bounds, and range of the chart.
 * 
 * @author Philipp Jahoda
 */
public interface ChartInterface {

    public float getXChartMin();

    public float getXChartMax();

    public float getYChartMin();

    public float getYChartMax();
    
    public int getXValCount();

    public int getWidth();

    public int getHeight();

    public PointF getCenterOfView();

    public PointF getCenterOffsets();

    public RectF getContentRect();
    
    public ValueFormatter getDefaultValueFormatter();

    public ChartData getData();
}
