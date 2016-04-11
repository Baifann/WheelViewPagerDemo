package com.baifan.gridviewandviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

/**
 * Created by baifan on 16/4/11.
 */
public class MainActivity extends FragmentActivity {
    private FrameLayout mLyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initViews() {
        mLyContainer = (FrameLayout) findViewById(R.id.ly_container);
    }

    private void initEvents() {
        initFragment();
    }

    private void initFragment(){
        ViewPagerFragment newFragment = ViewPagerFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.ly_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
