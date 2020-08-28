/**
 * 
 */
package com.yfy.base;

import android.view.View;

/**
 * @author yfy1
 * @version 1.0
 * @Desprition
 */
public interface ViewAdapter {
	
	public void holdProperty(int position, View view);

	public void selectItem(int position, View view);

	public void unSelectItem(int position, View view);
	
	public void setViewTag(View view);
	
}
