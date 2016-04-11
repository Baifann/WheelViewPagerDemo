package com.baifan.gridviewandviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baifan.gridviewandviewpager.adapter.ImageViewPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baifan on 16/4/11.
 */
public class ViewPagerFragment extends BaseFragment implements ImageViewPagerAdapter.OnViewPagerChooseListener {
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

    /**
     * 指示器集合 存放指示器
     */
    private List<ImageView> mIndictorList = new ArrayList<ImageView>();

    public static ViewPagerFragment newInstance() {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        return viewPagerFragment;
    }

    public static ViewPagerFragment newInstance(int page, String title) {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        viewPagerFragment.setArguments(args);
        return viewPagerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_demo, null);
        Log.i("hehe", "执行了");
        initViews();
        initEvents();
        return mView;
    }

    @Override
    protected void initViews() {
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_demo);
        //指示器布局
        mLyIndictor = (LinearLayout) mView.findViewById(R.id.ly_indictor);
    }

    @Override
    protected void initEvents() {
        imagePager();
        mOnPageListener = new MyOnPageListener();
        mViewPager.setOnPageChangeListener(mOnPageListener);
        if (isMaxWheel) {
            mHandler.sendEmptyMessageDelayed(0, HANDLER_TIME);
        }
        mOnPageListener.onPageSelected(FIRST_ITEM);
    }

    @Override
    protected void init() {

    }

    /**
     * 初始化viewpager
     */
    private void initViewpagerList(List<String> imgUrlList) {
        if (imgUrlList.size() > 1) {
            isMaxWheel = true;
            FIRST_ITEM = 1;
            MAX_ITEM = imgUrlList.size();
            imgUrlList.add(0, imgUrlList.get(MAX_ITEM - 1));
            imgUrlList.add(imgUrlList.get(FIRST_ITEM));
        } else {
            isMaxWheel = false;
        }
    }


    /**
     * handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mOnPageListener != null) {
                mViewPager.setCurrentItem(page);

                mHandler.sendEmptyMessageDelayed(0, HANDLER_TIME);
            }
        }
    };

    /**
     * 初始化图像pager
     */
    private void imagePager() {

        mStrList.add("http://img2.duitang.com/uploads/item/201206/03/20120603170827_RAZTG.jpeg");
        mStrList.add("http://img3.douban.com/img/musician/large/14322.jpg");
        mStrList.add("http://d04.res.meilishuo.net/pic/_o/1e/7a/699da3092c81f92111c28ab1dabc_750_630.c1.jpg");
//        mStrList.add("http://i5.3conline.com/images/piclib/201108/11/batch/1/105357/1312996317340bm800qdj67_medium.jpg");

        // 格式化下图片地址集合
        initViewpagerList(mStrList);

        for (String imgPath : mStrList) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageLoader.displayImage(imgPath, imageView);
            mImgList.add(imageView);
        }

        ImageViewPagerAdapter pagerAdapter = new ImageViewPagerAdapter(mImgList);
        mViewPager.setAdapter(pagerAdapter);
        pagerAdapter.setOnViewPagerChooseListener(this);
        mViewPager.setCurrentItem(FIRST_ITEM, false);
        // 初始化指示器
        if(isMaxWheel){
            initIndictor();
        }
    }

    @Override
    public void onItemChoose(int position) {
        Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
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
            if (isMaxWheel) {
                if (position == 0) {
                    mCurrentPosition = MAX_ITEM;
                } else if (position > MAX_ITEM) {
                    //如果选择滑动了大于list
                    mCurrentPosition = FIRST_ITEM;
                } else {
                    mCurrentPosition = position;
                }
            }

            page = mCurrentPosition + 1;
            // 设置选中的指示器
            setSelectIndictor(page - 2);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
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
    private void initIndictor() {
        mLyIndictor.removeAllViews();
        for (int i = 0; i < mStrList.size() - 2; i++) {
            // 设置指示器
            ImageView imageView = new ImageView(getActivity());
            if (i == 0) {
                imageView.setImageResource(R.drawable.icon_point_pre);
            } else {
                imageView.setImageResource(R.drawable.icon_point);
            }
            mIndictorList.add(imageView);
            mLyIndictor.addView(imageView);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            imageView.setLayoutParams(lp);
        }
    }

    /**
     * 设置被选中的颜色
     *
     * @param selectPosition
     */
    private void setSelectIndictor(int selectPosition) {
        for (int i = 0; i < mIndictorList.size(); i++) {
            ImageView imageView = mIndictorList.get(i);
            imageView.setImageResource(R.drawable.icon_point);
            if (i == selectPosition) {
                imageView.setImageResource(R.drawable.icon_point_pre);
            }
        }
    }
}
