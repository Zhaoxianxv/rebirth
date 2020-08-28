package com.yfy.charting_mp.interfaces;

import com.yfy.charting_mp.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    public CandleData getCandleData();
}
