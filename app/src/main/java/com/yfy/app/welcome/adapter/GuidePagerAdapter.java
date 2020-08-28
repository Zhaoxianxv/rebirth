package com.yfy.app.welcome.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Aj Liao on 2016/2/17.
 *
 */
public class GuidePagerAdapter extends PagerAdapter {

    List<ImageView> viewContainter;

    public GuidePagerAdapter(List<ImageView> viewContainter) {
        this.viewContainter = viewContainter;
    }

    @Override
    public int getCount() {
        return viewContainter.size();
    }

    //滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(viewContainter.get(position));
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewContainter.get(position));
        return viewContainter.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
