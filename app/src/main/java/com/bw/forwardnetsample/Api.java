package com.bw.forwardnetsample;

import com.bw.forwardnetsample.model.bean.CartBean;
import com.bw.forwardnetsample.model.bean.OrderFormBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Retrofit 将一个一个的http请求，映射成了一个一个的 api接口中的方法
 */
public interface Api {
    // Observable 导包一定要是 io. 下面的才行
    @GET("small/order/verify/v1/findShoppingCart")
    Observable<CartBean> getCartData(@Header("userId") int userId, @Header("sessionId") String sessionId);

    //查询订单    0 1 2 3 9
    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<OrderFormBean> getOrderFormData(@Header("userId") int userId,
                                               @Header("sessionId") String sessionId,
                                               @Query("status") int status,
                                               @Query("page") int page,
                                               @Query("count") int count);

}
