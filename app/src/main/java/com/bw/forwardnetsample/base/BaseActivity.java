package com.bw.forwardnetsample.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * BaseActivity 抽象的activity基类
 * 1、声明泛型P  P extends BasePresenter
 * 2、重写 onCreate方法，该方法容易选错，注意 onCreate 方法参数是一个
 * 3、在oncreate方法中， 提供布局ID、构造mpresenter、给mpresenter绑定v、butterknife初始化控件、初始化view和监听、初始化数据
 * 3、在onDestroy方法中，解绑
 *
 * @param <P>
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mPresenter;

    /**
     * 注意onCreate方法是一个参数，不要写错了
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提供布局id
        setContentView(layoutId());
        //提供presenter
        mPresenter = providePresenter();
        //给p层绑定接口     必须判空，为了防止某些子类不需要 p，提供的 p 为 null
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
        //通过butterknife findViewById();
        ButterKnife.bind(this);
        //特殊情况下的view设置特殊的监听
        initView();
        //初始化数据
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract P providePresenter();

    protected abstract int layoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
