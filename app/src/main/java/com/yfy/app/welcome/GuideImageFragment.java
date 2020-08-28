package com.yfy.app.welcome;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.yfy.app.welcome.utils.v4.FragmentPagerItem;
import com.yfy.rebirth.R;

public class GuideImageFragment extends Fragment {
    ImageView iv_image;
    Bitmap bitmap;
    int fragmentPagerItemPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = inflater.from(getActivity()).inflate(R.layout.initial_fragment_guide, container, false);
        iv_image=contentView.findViewById(R.id.guide_iv_image);//
        fragmentPagerItemPosition = FragmentPagerItem.getPosition(getArguments());
        if (fragmentPagerItemPosition == 0) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);//initial one pager
        } else if (fragmentPagerItemPosition == 1) {
            bitmap = BitmapFactory.decodeResource(getResources(),  R.mipmap.ic_launcher);//initial two pager
        } else if (fragmentPagerItemPosition == 2) {
            bitmap = BitmapFactory.decodeResource(getResources(),  R.mipmap.ic_launcher);//initial three pager
        }
        if (Utils.isNotNull(bitmap)) {
            ViewTools.setImageBitmap(iv_image, bitmap);
        }

        return contentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewTools.releaseImageView(iv_image);
        iv_image = null;
        ViewTools.releaseBitmap(bitmap);
        bitmap = null;
    }

}
