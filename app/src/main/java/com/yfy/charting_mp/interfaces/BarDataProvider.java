package com.yfy.charting_mp.interfaces;

import com.yfy.charting_mp.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    public BarData getBarData();
    public boolean isDrawBarShadowEnabled();
    public boolean isDrawValueAboveBarEnabled();
    public boolean isDrawHighlightArrowEnabled();
    //public boolean isDrawValuesForWholeStackEnabled();
}
