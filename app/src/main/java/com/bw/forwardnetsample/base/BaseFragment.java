package com.bw.forwardnetsample.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.forwardnetsample.R;

import butterknife.ButterKnife;

/**
 * BaseFragment 和 BaseActiivty不同之处
 * 1、initData方法 在BaseFragment中需要放到 onActivityCreated声明周期中
 * 2、BaseFragment 设置布局id的方式， 是 inflater.inflate(layoutId(), null)
 * 3、在 BaseFragment 中   ButterKnife.bind(this, inflate) ，两个参数
 *
 * 坑点：
 * onCreateView方法 必须返回  inlate，千万不要返回 super.onCreateView()
 *
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(layoutId(), null);
        mPresenter = providePresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
        //注意在fragment中需要把 布局inflate 传给 Butterknife
        ButterKnife.bind(this, inflate);
        initView(inflate);
        return inflate;
    }

    protected abstract void initView(View inflate);

    protected abstract P providePresenter();

    protected abstract int layoutId();

    /**
     * 当activity创建好之后回调该方法
     * 一般用来 接受activity给fragment的传参， 用来请求数据
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
