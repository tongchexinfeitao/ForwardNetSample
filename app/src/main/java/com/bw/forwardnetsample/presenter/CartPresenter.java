package com.bw.forwardnetsample.presenter;

import com.bw.forwardnetsample.base.BasePresenter;
import com.bw.forwardnetsample.contract.ICartContract;
import com.bw.forwardnetsample.model.CartModel;
import com.bw.forwardnetsample.model.bean.CartBean;

/**
 * presenter层 对应 IPresenter，需要 implements IPresenter
 * <p>
 * 1、需要 实现  IPresenter
 * 2、需要 继承  BasePresenter
 * 3、需要给 BasePresenter 指定 泛型 V ， V 是 IView
 */
public class CartPresenter extends BasePresenter<ICartContract.IView> implements ICartContract.IPresenter {

    //model对象
    private CartModel cartModel;

    @Override
    protected void initModel() {
        cartModel = new CartModel();
    }

    @Override
    public void getCartData(int userId, String sessionId) {
        // TODO: 2020/2/5 要通过 cartModel 去联网
        cartModel.getCartData(userId, sessionId, new ICartContract.IModel.IModelCallback() {
            @Override
            public void onCartSuccess(CartBean cartBean) {
                // TODO: 2020/2/5  成功之后，通过父类中的 view 接口 来通知
                view.onCartSuccess(cartBean);
            }

            @Override
            public void onCartFailure(Throwable throwable) {
                view.onCartFailure(throwable);
            }
        });
    }
}
