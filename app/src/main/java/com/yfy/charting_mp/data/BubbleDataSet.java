
package com.yfy.charting_mp.data;

import android.graphics.Color;
import com.yfy.charting_mp.data.BubbleEntry;
import com.yfy.charting_mp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BubbleDataSet extends BarLineScatterCandleDataSet<com.yfy.charting_mp.data.BubbleEntry> {

    // NOTE: Do not initialize these, as the calcMinMax is called by the super,
    // and the initializers are called after that and can reset the values
    protected float mXMax;
    protected float mXMin;
    protected float mMaxSize;

    private float mHighlightCircleWidth = 2.5f;

    public BubbleDataSet(List<com.yfy.charting_mp.data.BubbleEntry> yVals, String label) {
        super(yVals, label);
    }

    /**
     * Sets the width of the circle that surrounds the bubble when highlighted,
     * in dp.
     * 
     * @param width
     */
    public void setHighlightCircleWidth(float width) {
        mHighlightCircleWidth = Utils.convertDpToPixel(width);
    }

    public float getHighlightCircleWidth() {
        return mHighlightCircleWidth;
    }

    /**
     * Sets a color with a specific alpha value.
     * @param color
     * @param alpha from 0-255
     */
    public void setColor(int color, int alpha) {
        super.setColor(Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color)));
    }

    @Override
    protected void calcMinMax(int start, int end) {
        if (mYVals.size() == 0)
            return;

        final List<com.yfy.charting_mp.data.BubbleEntry> entries = getYVals();

        int endValue;

        if (end == 0)
            endValue = mYVals.size() - 1;
        else
            endValue = end;

        mLastStart = start;
        mLastEnd = endValue;

        mYMin = yMin(entries.get(start));
        mYMax = yMax(entries.get(start));

        // need chart width to guess this properly

        for (int i = start; i <= endValue; i++) {

            final com.yfy.charting_mp.data.BubbleEntry entry = entries.get(i);
            
            final float ymin = yMin(entry);
            final float ymax = yMax(entry);

            if (ymin < mYMin)
            {
                mYMin = ymin;
            }

            if (ymax > mYMax)
            {
                mYMax = ymax;
            }

            final float xmin = xMin(entry);
            final float xmax = xMax(entry);

            if (xmin < mXMin)
            {
                mXMin = xmin;
            }

            if (xmax > mXMax)
            {
                mXMax = xmax;
            }

            final float size = largestSize(entry);

            if (size > mMaxSize)
            {
                mMaxSize = size;
            }
        }
    }

    @Override
    public DataSet<com.yfy.charting_mp.data.BubbleEntry> copy() {

        List<com.yfy.charting_mp.data.BubbleEntry> yVals = new ArrayList<com.yfy.charting_mp.data.BubbleEntry>();

        for (int i = 0; i < mYVals.size(); i++) {
            yVals.add(mYVals.get(i).copy());
        }

        BubbleDataSet copied = new BubbleDataSet(yVals, getLabel());
        copied.mColors = mColors;
        copied.mHighLightColor = mHighLightColor;

        return copied;
    }

    public float getXMax() {
        return mXMax;
    }

    public float getXMin() {
        return mXMin;
    }

    public float getMaxSize() {
        return mMaxSize;
    }

    private float yMin(com.yfy.charting_mp.data.BubbleEntry entry) {
        return entry.getVal();
    }

    private float yMax(com.yfy.charting_mp.data.BubbleEntry entry) {
        return entry.getVal();
    }

    private float xMin(com.yfy.charting_mp.data.BubbleEntry entry) {
        return (float) entry.getXIndex();
    }

    private float xMax(com.yfy.charting_mp.data.BubbleEntry entry) {
        return (float) entry.getXIndex();
    }

    private float largestSize(BubbleEntry entry) {
        return entry.getSize();
    }
}
