package com.yfy.charting_mp.interfaces;

import com.yfy.charting_mp.components.YAxis.AxisDependency;
import com.yfy.charting_mp.data.BarLineScatterCandleData;
import com.yfy.charting_mp.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    public Transformer getTransformer(AxisDependency axis);
    public int getMaxVisibleCount();
    public boolean isInverted(AxisDependency axis);
    
    public int getLowestVisibleXIndex();
    public int getHighestVisibleXIndex();

    public BarLineScatterCandleData getData();
}
