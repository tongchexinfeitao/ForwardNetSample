package com.bw.forwardnetsample.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.forwardnetsample.R;
import com.bw.forwardnetsample.base.BaseActivity;
import com.bw.forwardnetsample.contract.ICartContract;
import com.bw.forwardnetsample.model.bean.CartBean;
import com.bw.forwardnetsample.presenter.CartPresenter;
import com.bw.forwardnetsample.view.adapter.CartAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * v层
 * 1、需要 实现 IView
 * 2、需要 继承 BaseActivity
 * 3、需要给 BaseActivity 指定 泛型 P , P 对应的是 CartPresenter
 */
public class CartActivity extends BaseActivity<CartPresenter> implements ICartContract.IView {

    @BindView(R.id.lv)
    ExpandableListView mLv;
    @BindView(R.id.cb_cart_all_select)
    CheckBox mCbCartAllSelect;
    @BindView(R.id.tv_cart_total_price)
    TextView mTvCartTotalPrice;
    @BindView(R.id.btn_cart_pay)
    Button mBtnCartPay;

    @Override
    protected void initData() {
        // TODO: 2020/2/5 需要在这里通过 presenter 去发起请求
        mPresenter.getCartData(27822, "158095582260527822");
    }

    @Override
    protected void initView() {

    }

    /**
     * 必须提供 presenter，不能为null
     */
    @Override
    protected CartPresenter providePresenter() {
        return new CartPresenter();
    }

    /**
     * 必须提供 布局id ，不能为0
     */
    @Override
    protected int layoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public void onCartSuccess(CartBean cartBean) {
        //商家的集合
        List<CartBean.ResultBean> resultBeanList = cartBean.getResult();
        //构造一个适配器
        CartAdapter cartAdapter = new CartAdapter(resultBeanList);
        //设置适配器
        mLv.setAdapter(cartAdapter);

        //遍历商家集合
        for (int i = 0; i < resultBeanList.size(); i++) {
            //展开第几个商家，     展开第几个分组
            mLv.expandGroup(i);
        }

        Toast.makeText(CartActivity.this, "请求购物车成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartFailure(Throwable throwable) {
        Toast.makeText(CartActivity.this, "请求购物车失败" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.cb_cart_all_select)
    public void onViewClicked() {
    }
}
