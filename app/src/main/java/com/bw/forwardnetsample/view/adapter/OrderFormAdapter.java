package com.bw.forwardnetsample.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.forwardnetsample.R;
import com.bw.forwardnetsample.model.bean.OrderFormBean;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单列表的适配器
 */
public class OrderFormAdapter extends RecyclerView.Adapter<OrderFormAdapter.OrderFormViewholder> {

    //订单集合
    private List<OrderFormBean.OrderListBean> orderList;

    public OrderFormAdapter(List<OrderFormBean.OrderListBean> orderList) {

        this.orderList = orderList;
    }

    //复用
    @NonNull
    @Override
    public OrderFormViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_form, viewGroup, false);
        return new OrderFormViewholder(inflate);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull OrderFormViewholder orderFormViewholder, int i) {
        //1、拿到当前订单的数据
        OrderFormBean.OrderListBean orderListBean = orderList.get(i);

        //订单号
        orderFormViewholder.mTvOrderId.setText("订单号  " + orderListBean.getOrderId());
        //订单时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = simpleDateFormat.format(orderListBean.getOrderTime());
        orderFormViewholder.mTvTime.setText(format);

        //根据订单状态做判断
        int orderStatus = orderListBean.getOrderStatus();

        //根据订单的状态，判断右下角是显示时间还是显示按钮
        if (orderStatus == 1) {
            //待支付有去支付按钮
            orderFormViewholder.mBtnPayOrReceive.setVisibility(View.VISIBLE);
            orderFormViewholder.mBtnPayOrReceive.setText("去支付");
        } else if (orderStatus == 2) {
            //待收货有确认收货按钮
            orderFormViewholder.mBtnPayOrReceive.setVisibility(View.VISIBLE);
            orderFormViewholder.mBtnPayOrReceive.setText("确认收货");
        } else if (orderStatus == 3) {
            orderFormViewholder.mBtnPayOrReceive.setVisibility(View.GONE);
        } else if (orderStatus == 9) {
            orderFormViewholder.mBtnPayOrReceive.setVisibility(View.GONE);
        }

        // TODO: 2020/2/12 给商品列表赋值   给holder中的recyclerview设置 布局管理器和适配器
        //1、拿到该订单的商品集合
        List<OrderFormBean.OrderListBean.DetailListBean> detailList = orderListBean.getDetailList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(orderFormViewholder.itemView.getContext());
        orderFormViewholder.mRecyclerCommidity.setLayoutManager(linearLayoutManager);
        //将商品列表、订单状态，传给商品列表适配器
        OrderFormCommodityAdapter orderFormCommodityAdapter = new OrderFormCommodityAdapter(detailList, orderStatus);
        orderFormViewholder.mRecyclerCommidity.setAdapter(orderFormCommodityAdapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderFormViewholder extends RecyclerView.ViewHolder {
        //订单号
        @BindView(R.id.tv_order_id)
        TextView mTvOrderId;
        //商品的列表
        @BindView(R.id.recycler_commidity)
        RecyclerView mRecyclerCommidity;
        //时间
        @BindView(R.id.tv_time)
        TextView mTvTime;
        //按钮
        @BindView(R.id.btn_pay_or_receive)
        Button mBtnPayOrReceive;

        //ViewHolder的父类中，有一个itemView控件，就是我们item条目布局
        public OrderFormViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
