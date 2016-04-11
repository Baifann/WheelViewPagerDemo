package com.baifan.gridviewandviewpager.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.baifan.gridviewandviewpager.adapter.ImageViewPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baifan on 16/2/22.
 */
public class ADViewPager extends ViewPager {
    /**
     * 加载图片
     */
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    /**
     * onpage监听
     */
    private MyOnPageListener mOnPageListener;
    /**
     * pager页数
     */
    private int page;
    /**
     * 图片路径集合
     */
    private List<String> mImgUrlList;
    /**
     * 存放ImageView集合
     */
    private List<ImageView> mImageViewList = new ArrayList<>();

    /**
     * 构造方法
     * @param context
     * @param imgUrlList
     */
    public ADViewPager(Context context, List<String> imgUrlList) {
        super(context);
        mImgUrlList = imgUrlList;
    }

    /**
     * 构造方法
     * @param context
     * @param attrs
     * @param imgUrlList
     */
    public ADViewPager(Context context, AttributeSet attrs, List<String> imgUrlList) {
        super(context, attrs);
        mImgUrlList = imgUrlList;
    }

    /**
     * 设置图片url集合
     * @param imgUrlList
     */
    public void setUrlList(List<String> imgUrlList){
        mImgUrlList = imgUrlList;
        initAdapter();
    }

    /**
     * 自定义实现onpager接口
     */
    class MyOnPageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 得到处理的page
     * @return
     */
    private void getHandlePage(){
        page++;
        if(page > mImgUrlList.size()){
            page = 0;
        }
    }

    /**
     * handler
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mOnPageListener != null){
                //处理下页数
                getHandlePage();

                setCurrentItem(page);
                mOnPageListener.onPageSelected(page);

                mHandler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };

    /**
     * 初始化adapter
     */
    private void initAdapter(){
        if(mImgUrlList == null || mImgUrlList.size() == 0){
            return;
        }

        //装载imageView
        for(String imgPath : mImgUrlList){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageLoader.displayImage(imgPath, imageView);
            mImageViewList.add(imageView);
        }

        //初始化adapter
        ImageViewPagerAdapter pagerAdapter = new ImageViewPagerAdapter(mImageViewList);
        setAdapter(pagerAdapter);

        mOnPageListener = new MyOnPageListener();
        setOnPageChangeListener(mOnPageListener);

        if(mImgUrlList.size() > 1){
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

}
