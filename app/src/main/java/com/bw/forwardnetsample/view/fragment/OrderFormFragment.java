package com.bw.forwardnetsample.view.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.forwardnetsample.R;
import com.bw.forwardnetsample.base.BaseFragment;
import com.bw.forwardnetsample.contract.IOrderFormContract;
import com.bw.forwardnetsample.model.bean.OrderFormBean;
import com.bw.forwardnetsample.presenter.OrderFormPresenter;
import com.bw.forwardnetsample.view.adapter.OrderFormAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 该fragment只需要实现一个订单列表就行
 * <p>
 * 布局很简单就是一个 recyclerView
 */
public class OrderFormFragment extends BaseFragment<OrderFormPresenter> implements IOrderFormContract.IView {

    //是一个订单列表，  item布局就是一个订单的布局
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    Unbinder unbinder;
    private int status = 0;

    @Override
    protected void initView(View inflate) {

    }

    @Override
    protected OrderFormPresenter providePresenter() {
        return new OrderFormPresenter();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_order_form;
    }

    @Override
    protected void initData() {
        // TODO: 2020/2/11 必须在intData中接受 activity传过来的状态值，并且发起请求
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getInt("status");
        }
        mPresenter.getOrderFormData(27822, "158148992577727822", status, 1, 10);
    }


    /**
     * 动态创建framgent
     */
    public static OrderFormFragment getInstance(int status) {
        OrderFormFragment orderFormFragment = new OrderFormFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        orderFormFragment.setArguments(bundle);
        return orderFormFragment;
    }

    @Override
    public void onOrderFormSuccess(OrderFormBean orderFormBean) {
        //1、拿到订单集合
        List<OrderFormBean.OrderListBean> orderList = orderFormBean.getOrderList();
        //2、设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(linearLayoutManager);
        //3、设置适配器
        OrderFormAdapter orderFormAdapter = new OrderFormAdapter(orderList);
        mRecycler.setAdapter(orderFormAdapter);
    }

    @Override
    public void onOrderFormFailure(Throwable throwable) {
        Toast.makeText(getActivity(), "请求订单失败" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
