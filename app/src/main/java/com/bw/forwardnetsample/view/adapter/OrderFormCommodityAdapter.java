package com.bw.forwardnetsample.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.forwardnetsample.R;
import com.bw.forwardnetsample.model.bean.OrderFormBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单商品列表的适配器
 */
public class OrderFormCommodityAdapter extends RecyclerView.Adapter<OrderFormCommodityAdapter.OrderFormCommodityViewHolder> {

    //商品列表
    private List<OrderFormBean.OrderListBean.DetailListBean> detailList;
    //订单状态
    private int orderStatus;

    public OrderFormCommodityAdapter(List<OrderFormBean.OrderListBean.DetailListBean> detailList, int orderStatus) {
        this.detailList = detailList;
        this.orderStatus = orderStatus;
    }

    @NonNull
    @Override
    public OrderFormCommodityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_form_commodity, viewGroup, false);
        return new OrderFormCommodityViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFormCommodityViewHolder orderFormCommodityViewHolder, int i) {
        //1、当前商品的对象
        OrderFormBean.OrderListBean.DetailListBean detailListBean = detailList.get(i);

        //商品名字
        orderFormCommodityViewHolder.mProductTitleNameTv.setText(detailListBean.getCommodityName());
        //商品价格
        orderFormCommodityViewHolder.mProductPriceTv.setText("￥" + detailListBean.getCommodityPrice());
        //头像   多个图片地址用 逗号 拼起来的
        String commodityPic = detailListBean.getCommodityPic();
        //将这个字符串用 逗号 分割
        String[] split = commodityPic.split(",");
        //第0个图片地址
        String photoUrl = split[0];
        Glide.with(orderFormCommodityViewHolder.mProductIconIv).load(photoUrl)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(orderFormCommodityViewHolder.mProductIconIv);

        //拿到订单状态，如果订单状态是 3，说是是待评价 ，需要展示去评价按钮
        if (orderStatus == 3) {
            orderFormCommodityViewHolder.mBtnEvaluate.setVisibility(View.VISIBLE);
        } else {
            orderFormCommodityViewHolder.mBtnEvaluate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class OrderFormCommodityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_icon_iv)
        ImageView mProductIconIv;
        @BindView(R.id.product_title_name_tv)
        TextView mProductTitleNameTv;
        @BindView(R.id.product_price_tv)
        TextView mProductPriceTv;
        @BindView(R.id.btn_evaluate)
        Button mBtnEvaluate;

        public OrderFormCommodityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
