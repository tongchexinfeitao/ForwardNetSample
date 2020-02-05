package com.bw.forwardnetsample.base;

/**
 * P层的抽象基类
 * <p>
 * 1、需要声明一个泛型 V
 * 2、attach方法
 * 3、detach方法
 * 4、无参构造器，构造器中调用 initModel()
 * 5、生成initMoldel()方法
 *
 * 精简的BasePresenter
 * 1、v的绑定和解绑
 * 2、构造器中初始化model
 *
 * @param <V>
 */
public abstract class BasePresenter<V> {
    //第一步  声明 V层的接口
    protected V view;

    //第二步 绑定
    public void attach(V view) {
        this.view = view;
    }

    //解绑
    public void detach() {
        view = null;
    }

    //构造器，在构造器中调用初始化model的方法 initModel
    public BasePresenter() {
        //初始化model
        initModel();
    }
    //初始化model
    protected abstract void initModel();
}
