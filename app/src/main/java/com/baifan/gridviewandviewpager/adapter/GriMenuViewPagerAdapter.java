package com.baifan.gridviewandviewpager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

/**
 * 九宫格菜单的viewPager
 * Created by baifan on 16/2/18.
 */
public class GriMenuViewPagerAdapter extends PagerAdapter{
    private List<GridView> mGriList;

    public GriMenuViewPagerAdapter(Context context, List<GridView> griList){
        mGriList = griList;
    }

    @Override
    public int getCount() {
        return mGriList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(mGriList.get(position));
        return mGriList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mGriList.get(position));
    }
}
