package com.bw.forwardnetsample.model;

import com.bw.forwardnetsample.contract.IOrderFormContract;
import com.bw.forwardnetsample.model.bean.OrderFormBean;
import com.bw.forwardnetsample.util.NetUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderFormModel implements IOrderFormContract.IModel {
    @Override
    public void getOrderFormData(int userId, String sessionId, int status, int page, int count, IModelCallback iModelCallback) {
        NetUtil.getInstance().getApi()
                .getOrderFormData(userId, sessionId, status, page, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderFormBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderFormBean orderFormBean) {
                        if ("0000".equals(orderFormBean.getStatus())) {
                            iModelCallback.onOrderFormSuccess(orderFormBean);
                        } else {
                            iModelCallback.onOrderFormFailure(new Exception(orderFormBean.getMessage()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModelCallback.onOrderFormFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
