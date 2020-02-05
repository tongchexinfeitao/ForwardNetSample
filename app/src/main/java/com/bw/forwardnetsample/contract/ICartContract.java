package com.bw.forwardnetsample.contract;

import com.bw.forwardnetsample.model.bean.CartBean;

import retrofit2.http.Header;

/**
 * 购物车的锲约接口
 * <p>
 * mvp一共三层
 * view层、presenter层、model层
 * <p>
 * 对应三个接口
 * IView 、IPresenter 、IModel
 */
public interface ICartContract {
    /**
     * 用来接受 成功和失败
     */
    interface IView {
        void onCartSuccess(CartBean cartBean);

        void onCartFailure(Throwable throwable);
    }

    /**
     * 用来请求数据
     * <p>
     * p层需要一个接口，将成功失败通知v层，那这个接口通过 attach的方式传递
     */
    interface IPresenter {
        void getCartData(int userId, String sessionId);
    }

    /**
     * 真正用来请求数据
     * <p>
     * model层需要一个接口，将成功和是失败通知 presenter层
     */
    interface IModel {
        void getCartData(int userId, String sessionId, IModelCallback iModelCallback);

        interface IModelCallback {
            void onCartSuccess(CartBean cartBean);

            void onCartFailure(Throwable throwable);
        }
    }
}
