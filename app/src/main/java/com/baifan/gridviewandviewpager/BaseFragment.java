package com.baifan.gridviewandviewpager;

import android.view.MotionEvent;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment{
    protected Activity mActivity;
    protected Context mContext;
    protected View mView;
    private String TAG = this.getClass().toString();
    protected float downY, upY;
    protected boolean canSwip;

    @Override
    public void onStart() {
        super.onStart();
    }

    public BaseFragment() {
        super();
    }

    public BaseFragment(Activity activity, Context context) {
        mActivity = activity;
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViews();
        initEvents();
        init();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
//		changeTouchListener = (OnChangeTouchListener) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void hideKeyBoard() {
        ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    protected void showKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected abstract void initViews();

    protected abstract void initEvents();

    protected abstract void init();

    public View findViewById(int id) {
        return mView.findViewById(id);
    }



    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


}
