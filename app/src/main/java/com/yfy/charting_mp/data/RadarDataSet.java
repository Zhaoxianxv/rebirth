
package com.yfy.charting_mp.data;

import com.yfy.charting_mp.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class RadarDataSet extends LineRadarDataSet<com.yfy.charting_mp.data.Entry> {
    
    public RadarDataSet(List<com.yfy.charting_mp.data.Entry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public DataSet<com.yfy.charting_mp.data.Entry> copy() {

        List<com.yfy.charting_mp.data.Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < mYVals.size(); i++) {
            yVals.add(mYVals.get(i).copy());
        }

        RadarDataSet copied = new RadarDataSet(yVals, getLabel());
        copied.mColors = mColors;
        copied.mHighLightColor = mHighLightColor;

        return copied;
    }
}
