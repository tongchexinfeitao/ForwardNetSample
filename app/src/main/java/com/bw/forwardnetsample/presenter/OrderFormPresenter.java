package com.bw.forwardnetsample.presenter;

import com.bw.forwardnetsample.base.BasePresenter;
import com.bw.forwardnetsample.contract.IOrderFormContract;
import com.bw.forwardnetsample.model.OrderFormModel;
import com.bw.forwardnetsample.model.bean.OrderFormBean;

public class OrderFormPresenter extends BasePresenter<IOrderFormContract.IView> implements IOrderFormContract.IPresenter {

    private OrderFormModel orderFormModel;

    @Override
    protected void initModel() {
        orderFormModel = new OrderFormModel();
    }

    @Override
    public void getOrderFormData(int userId, String sessionId, int status, int page, int count) {
        orderFormModel.getOrderFormData(userId, sessionId, status, page, count, new IOrderFormContract.IModel.IModelCallback() {
            @Override
            public void onOrderFormSuccess(OrderFormBean orderFormBean) {
                //view  来自于父类  BasePresenter
                view.onOrderFormSuccess(orderFormBean);
            }

            @Override
            public void onOrderFormFailure(Throwable throwable) {
                view.onOrderFormFailure(throwable);
            }
        });
    }
}
