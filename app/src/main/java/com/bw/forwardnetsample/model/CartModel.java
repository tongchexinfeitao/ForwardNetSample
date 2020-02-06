package com.bw.forwardnetsample.model;

import com.bw.forwardnetsample.contract.ICartContract;
import com.bw.forwardnetsample.model.bean.CartBean;
import com.bw.forwardnetsample.util.NetUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * model层 对应 IModel接口
 * 1、需要 implements IModel接口
 */
public class CartModel implements ICartContract.IModel {
    @Override
    public void getCartData(int userId, String sessionId, IModelCallback iModelCallback) {
        // TODO: 2020/2/5 model 层还能怎么联网呀，只能通过  NetUtil呀
        NetUtil.getInstance()
                .getApi()
                .getCartData(userId, sessionId)
                //切换到子线程联网
                .subscribeOn(Schedulers.io())
                //在主线程观察，接受结果
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    //onNext只代表 协议的状态码是 200 ，不代表返回的status 是 0000
                    @Override
                    public void onNext(CartBean cartBean) {
                        // onNext中不是真正的成功，只是协议成功，但不代表返回了购物车数据
                        //有可能返回的是  {"message":"请先登陆","status":"1001"}
                        String status = cartBean.getStatus();
                        if ("0000".equals(status)) {
                            iModelCallback.onCartSuccess(cartBean);
                        } else {
                            //http的状态码是 200 ，但是 status状态 不是 0000 ，也是失败
                            iModelCallback.onCartFailure(new Exception(cartBean.getMessage()));
                        }
                    }

                    //400 500 303
                    @Override
                    public void onError(Throwable e) {
                        //http的协议状态码不是 200
                        iModelCallback.onCartFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
