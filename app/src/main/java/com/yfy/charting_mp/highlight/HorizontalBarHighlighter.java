package com.yfy.charting_mp.highlight;

import com.yfy.charting_mp.components.YAxis;
import com.yfy.charting_mp.data.BarDataSet;
import com.yfy.charting_mp.highlight.Highlight;
import com.yfy.charting_mp.interfaces.BarDataProvider;

/**
 * Created by Philipp Jahoda on 22/07/15.
 */
public class HorizontalBarHighlighter extends BarHighlighter {

	public HorizontalBarHighlighter(BarDataProvider chart) {
		super(chart);
	}

	@Override
	public com.yfy.charting_mp.highlight.Highlight getHighlight(float x, float y) {

		Highlight h = super.getHighlight(x, y);

		if (h == null)
			return h;
		else {

			BarDataSet set = mChart.getBarData().getDataSetByIndex(h.getDataSetIndex());

			if (set.isStacked()) {

				// create an array of the touch-point
				float[] pts = new float[2];
				pts[0] = y;

				// take any transformer to determine the x-axis value
				mChart.getTransformer(set.getAxisDependency()).pixelsToValue(pts);

				return getStackedHighlight(h, set, h.getXIndex(), h.getDataSetIndex(), pts[0]);
			} else
				return h;
		}
	}

	@Override
	protected int getXIndex(float x) {

		if (!mChart.getBarData().isGrouped()) {

			// create an array of the touch-point
			float[] pts = new float[2];
			pts[1] = x;

			// take any transformer to determine the x-axis value
			mChart.getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(pts);

			return (int) Math.round(pts[1]);
		} else {

			float baseNoSpace = getBase(x);

			int setCount = mChart.getBarData().getDataSetCount();
			int xIndex = (int) baseNoSpace / setCount;

			int valCount = mChart.getData().getXValCount();

			if (xIndex < 0)
				xIndex = 0;
			else if (xIndex >= valCount)
				xIndex = valCount - 1;

			return xIndex;
		}
	}

	/**
	 * Returns the base y-value to the corresponding x-touch value in pixels.
	 * 
	 * @param y
	 * @return
	 */
	@Override
	protected float getBase(float y) {

		// create an array of the touch-point
		float[] pts = new float[2];
		pts[1] = y;

		// take any transformer to determine the x-axis value
		mChart.getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(pts);
		float yVal = pts[1];

		int setCount = mChart.getBarData().getDataSetCount();

		// calculate how often the group-space appears
		int steps = (int) ((float) yVal / ((float) setCount + mChart.getBarData().getGroupSpace()));

		float groupSpaceSum = mChart.getBarData().getGroupSpace() * (float) steps;

		float baseNoSpace = (float) yVal - groupSpaceSum;
		return baseNoSpace;
	}
}
