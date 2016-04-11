package com.baifan.gridviewandviewpager.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by baifan on 16/2/22.
 */
public class ImageViewPagerAdapter extends PagerAdapter {
    /**
     * 图像集合
     */
    private List<ImageView> mImageList;

    public interface OnViewPagerChooseListener{
        void onItemChoose(int position);
    }

    public void setOnViewPagerChooseListener(OnViewPagerChooseListener listener){
        mListener = listener;
    }

    private OnViewPagerChooseListener mListener;

    public ImageViewPagerAdapter(List<ImageView> list){
        mImageList = list;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ((ViewPager) container).addView(mImageList.get(position), 0);
        mImageList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemChoose(position);
                }
            }
        });
        return mImageList.get(position);
    }
}
