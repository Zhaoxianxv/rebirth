/**
 * 
 */
package com.yfy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author yfy1
 * @version 1.0
 * @Desprition
 */
public class ListViewForScroll extends ListView {

	public ListViewForScroll(Context context) {
		super(context);
	}

	public ListViewForScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScroll(Context context, AttributeSet attrs,
                             int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
