package com.bw.forwardnetsample.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.forwardnetsample.R;
import com.bw.forwardnetsample.model.bean.CartBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends BaseExpandableListAdapter {


    //商家的集合      ------->    ResultBean是商家bean类
    private List<CartBean.ResultBean> resultBeanList;

    public CartAdapter(List<CartBean.ResultBean> resultBeanList) {

        this.resultBeanList = resultBeanList;
    }

    /**
     * 返回商家的数量
     */
    @Override
    public int getGroupCount() {
        return resultBeanList.size();
    }

    /**
     * 返回第groupPosition个商家的商品数量
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return resultBeanList.get(groupPosition).getShoppingCartList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 商家布局     checkbox和 商家名字textview
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_parent, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        //拿到当前商家
        CartBean.ResultBean resultBean = resultBeanList.get(groupPosition);
        // TODO: 开始绑定数据
        //给商家名字赋值
        groupViewHolder.mSellerNameTv.setText(resultBean.getCategoryName());

        // TODO: 2020/2/6  计算商家的cb赋值
        //1、拿到该商家下的所有的商品
        List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.getShoppingCartList();

        //2、默认商家是选中状态
        boolean groupIsChecked = true;

        //3、遍历所有商品，计算商品的选中状态
        for (int i = 0; i < shoppingCartList.size(); i++) {
            // TODO: 2020/2/6 只要有一个商品的状态是未选中，就可以断定商家未选中，就可以终止循环
            //第i个商品
            CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(i);
            //4、只要有一个商品未选中，商家的状态就是未选中
            if (shoppingCartListBean.isChecked() == false) {
                groupIsChecked = false;
                break;  //终止整个循环
            }
        }
        //5、给商家的cb赋值
        groupViewHolder.mSellerCb.setChecked(groupIsChecked);

        // TODO: 2020/2/6 给商家设置点击监听
        boolean finalGroupIsChecked = groupIsChecked;
        groupViewHolder.mSellerCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、拿到商家旧状态
                boolean oldIsChecked = finalGroupIsChecked;
                //2、取反得到商家新状态
                oldIsChecked = !oldIsChecked;
                //3、修改该商家下所有商品的状态
                for (int i = 0; i < shoppingCartList.size(); i++) {
                    //第i个商品
                    CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = shoppingCartList.get(i);
                    //修改商品的状态为新状态
                    shoppingCartListBean.setChecked(oldIsChecked);
                }
                //4、刷新适配器
                notifyDataSetChanged();
                //5、通过接口通知外部重新计算状态、价格、数量

            }
        });


        return convertView;
    }

    /**
     * 商品布局     checkbox和 商品名字textview 、商品价格textview、商品图片Imageview 、加减器
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //当前的商品
        CartBean.ResultBean.ShoppingCartListBean shoppingCartListBean = resultBeanList.get(groupPosition).getShoppingCartList().get(childPosition);

        //绑定数据
        //名字
        childViewHolder.mProductTitleNameTv.setText(shoppingCartListBean.getCommodityName());
        //价格
        // TODO: 2020/2/6 1、价格必须转换成字符串类型才能设置给 textview
        // TODO: 2020/2/6 2、bean类中的price字段，绝对不能是int的类型，换成double
        childViewHolder.mProductPriceTv.setText("" + shoppingCartListBean.getPrice());
        //头像
        Glide.with(childViewHolder.mProductIconIv).load(shoppingCartListBean.getPic()).into(childViewHolder.mProductIconIv);
        //商品的cb的选中状态
        childViewHolder.mChildCb.setChecked(shoppingCartListBean.isChecked());
        // TODO: 2020/2/6 给商品的cb设置点击监听
        childViewHolder.mChildCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2020/2/6 在点击事件中绝对不能使用checkbox
                //1、拿到商品旧状态
                boolean checked = shoppingCartListBean.isChecked();
                //2、取反得到商品新状态
                checked = !checked;
                //3、修改数据
                shoppingCartListBean.setChecked(checked);
                //4、刷新适配器
                notifyDataSetChanged();
                //5、通过接口通知外部重新计算状态、价格、数量
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 商家的viewholder
     */
    static class GroupViewHolder {
        @BindView(R.id.seller_cb)
        CheckBox mSellerCb;
        @BindView(R.id.seller_name_tv)
        TextView mSellerNameTv;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.child_cb)
        CheckBox mChildCb;
        @BindView(R.id.product_icon_iv)
        ImageView mProductIconIv;
        @BindView(R.id.product_title_name_tv)
        TextView mProductTitleNameTv;
        @BindView(R.id.product_price_tv)
        TextView mProductPriceTv;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
