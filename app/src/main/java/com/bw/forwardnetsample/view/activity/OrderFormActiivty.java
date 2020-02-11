package com.bw.forwardnetsample.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bw.forwardnetsample.R;
import com.bw.forwardnetsample.base.BaseActivity;
import com.bw.forwardnetsample.base.BasePresenter;
import com.bw.forwardnetsample.view.fragment.OrderFormFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 该activity不需要联网，不用指定 P 类型
 */
public class OrderFormActiivty extends BaseActivity {


    @BindView(R.id.vp)
    ViewPager mVp;

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void initData() {
        //1、动态构造5个fragment
        OrderFormFragment orderFormFragment0 = OrderFormFragment.getInstance(0);
        OrderFormFragment orderFormFragment1 = OrderFormFragment.getInstance(1);
        OrderFormFragment orderFormFragment2 = OrderFormFragment.getInstance(2);
        OrderFormFragment orderFormFragment3 = OrderFormFragment.getInstance(3);
        OrderFormFragment orderFormFragment9 = OrderFormFragment.getInstance(9);

        fragmentList.add(orderFormFragment0);
        fragmentList.add(orderFormFragment1);
        fragmentList.add(orderFormFragment2);
        fragmentList.add(orderFormFragment3);
        fragmentList.add(orderFormFragment9);

        //1、给viewpager设置适配器
        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_order_form;
    }
}
