package com.baifan.gridviewandviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baifan.gridviewandviewpager.adapter.GriMenuAdapter;
import com.baifan.gridviewandviewpager.adapter.GriMenuViewPagerAdapter;
import com.baifan.gridviewandviewpager.adapter.ImageViewPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baifan on 16/2/18.
 */
public class DemoActivity extends Activity implements AdapterView.OnItemClickListener{
    private ViewPager mViewPager;

    private final static int pageSize = 16;
    /**
     * 存放数据的集合
     */
    private List<String> mStrList = new ArrayList<>();
    /**
     * 存放gri的集合
     */
    private List<GridView> mGriList = new ArrayList<>();

    private List<ImageView> mImgList = new ArrayList<>();
    /**
     * 指示器布局
     */
    private LinearLayout mLyIndictor;
    /**
     * onpage监听
     */
    private MyOnPageListener mOnPageListener;
    /**
     * pager页数
     */
    private int page;
    /**
     * 滚动间隔时间
     */
    private static final int HANDLER_TIME = 2000;
    /**
     * 是否无限滚动
     */
    private boolean isMaxWheel;
    /**
     * 第一项viewPager
     */
    private int FIRST_ITEM = 1;
    /**
     * 可以选择的最大item
     */
    private int MAX_ITEM = 0;

    private ImageLoader mImageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        //初始化控件
        initViews();
        //初始化事件
        initEvents();
    }

    /**
     * 初始化事件
     */
    private void initEvents() {
//        initGri();

        imagePager();

        mOnPageListener = new MyOnPageListener();

        mViewPager.setOnPageChangeListener(mOnPageListener);

        if(isMaxWheel){
            mHandler.sendEmptyMessageDelayed(0, HANDLER_TIME);
        }

        mOnPageListener.onPageSelected(FIRST_ITEM);
    }

    /**
     * handler
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mOnPageListener != null){
                mViewPager.setCurrentItem(page);

                mHandler.sendEmptyMessageDelayed(0, HANDLER_TIME);
            }
        }
    };

    /**
     * 初始化图像pager
     */
    private void imagePager(){

        mStrList.add("http://img2.duitang.com/uploads/item/201206/03/20120603170827_RAZTG.jpeg");
        mStrList.add("http://img3.douban.com/img/musician/large/14322.jpg");
        mStrList.add("http://d04.res.meilishuo.net/pic/_o/1e/7a/699da3092c81f92111c28ab1dabc_750_630.c1.jpg");
        mStrList.add("http://i5.3conline.com/images/piclib/201108/11/batch/1/105357/1312996317340bm800qdj67_medium.jpg");

        //格式化下图片地址集合
        initViewpagerList(mStrList);

        for(String imgPath : mStrList){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageLoader.displayImage(imgPath, imageView);
            mImgList.add(imageView);
        }

        ImageViewPagerAdapter pagerAdapter = new ImageViewPagerAdapter(mImgList);
        mViewPager.setAdapter(pagerAdapter);

        mViewPager.setCurrentItem(FIRST_ITEM, false);
    }

    /**
     * 初始化gri
     */
    private void initGri(){
        for(int i = 0 ; i < 100 ; i++){
            mStrList.add(i + "事件");
        }

        int pageCount = (int)Math.ceil(mStrList.size()/pageSize);
        for(int i = 0 ; i < pageCount ; i++){
            GriMenuAdapter adapter = new GriMenuAdapter(this);
            GridView gridView = new GridView(this);
            gridView.setNumColumns(4);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);
            adapter.setList(mStrList);
            mGriList.add(gridView);
        }

        GriMenuViewPagerAdapter pagerAdapter = new GriMenuViewPagerAdapter(this, mGriList);
        mViewPager.setAdapter(pagerAdapter);
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vp_demo);
        //指示器布局
        mLyIndictor = (LinearLayout) findViewById(R.id.ly_indictor);
    }

    /**
     * 初始化viewpager
     */
    private void initViewpagerList(List<String> imgUrlList){
        if(imgUrlList.size() > 1){
            isMaxWheel = true;
            FIRST_ITEM = 1;
            MAX_ITEM = imgUrlList.size();
            imgUrlList.add(0, imgUrlList.get(MAX_ITEM - 1));
            imgUrlList.add(imgUrlList.get(FIRST_ITEM));
        }else{
            isMaxWheel = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
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
            if(isMaxWheel){
                if(position == 0){
                    mCurrentPosition = MAX_ITEM;
                } else if(position > MAX_ITEM){
                    //如果选择滑动了大于list
                    mCurrentPosition = FIRST_ITEM;
                } else {
                    mCurrentPosition = position;
                }
            }

            page = mCurrentPosition + 1;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(state == ViewPager.SCROLL_STATE_IDLE){
                mViewPager.setCurrentItem(mCurrentPosition, false);
            }
        }
    }

    /**
     * 选中的真正的位置
     */
    private int mCurrentPosition;

    /**
     * 初始化指示器
     */
    private void initIndictor(){
        mLyIndictor.removeAllViews();
        for(int i = 0 ; i < mStrList.size() ; i++){
            //设置指示器

        }
    }
}
