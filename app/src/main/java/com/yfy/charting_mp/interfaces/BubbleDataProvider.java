package com.yfy.charting_mp.interfaces;

import com.yfy.charting_mp.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    public BubbleData getBubbleData();
}
