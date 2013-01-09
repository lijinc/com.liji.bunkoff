package com.liji.bunkoff;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;

public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

    public static int CURRENTPAGE;

    @Override
    public void onPageSelected(int position) {
        CURRENTPAGE = position;
    }

    public int getCurrentPage() {
        return CURRENTPAGE;
    }
}
