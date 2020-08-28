package com.yfy.charting_mp.interfaces;

import com.yfy.charting_mp.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    public ScatterData getScatterData();
    
}
