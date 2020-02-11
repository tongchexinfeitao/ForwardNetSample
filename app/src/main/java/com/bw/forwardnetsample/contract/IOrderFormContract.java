package com.bw.forwardnetsample.contract;

import com.bw.forwardnetsample.model.bean.OrderFormBean;

public interface IOrderFormContract {
    interface IView {
        void onOrderFormSuccess(OrderFormBean orderFormBean);

        void onOrderFormFailure(Throwable throwable);
    }

    interface IPresenter {
        void getOrderFormData(int userId, String sessionId, int status, int page, int count);
    }

    interface IModel {
        void getOrderFormData(int userId, String sessionId, int status, int page, int count, IModelCallback iModelCallback);

        interface IModelCallback {
            void onOrderFormSuccess(OrderFormBean orderFormBean);

            void onOrderFormFailure(Throwable throwable);
        }
    }
}
