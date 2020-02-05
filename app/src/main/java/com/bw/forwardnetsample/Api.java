package com.bw.forwardnetsample;

import com.bw.forwardnetsample.model.bean.CartBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Api {
    @GET("small/order/verify/v1/findShoppingCart")
    Observable<CartBean> getCartData(@Header("userId") int userId, @Header("sessionId") String sessionId);
}
