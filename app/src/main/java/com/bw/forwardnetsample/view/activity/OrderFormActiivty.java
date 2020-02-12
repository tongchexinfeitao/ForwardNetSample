package com.bw.forwardnetsample.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

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
    @BindView(R.id.rg)
    RadioGroup mRg;

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
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_all:
                        mVp.setCurrentItem(0);
                        break;
                    case R.id.rbtn_wait_pay:
                        mVp.setCurrentItem(1);
                        break;
                    case R.id.rbtn_wait_receive:
                        mVp.setCurrentItem(2);
                        break;
                    case R.id.rbtn_wait_evaluate:
                        mVp.setCurrentItem(3);
                        break;
                    case R.id.rbtn_complete:
                        mVp.setCurrentItem(9);
                        break;
                }

            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mRg.check(mRg.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
