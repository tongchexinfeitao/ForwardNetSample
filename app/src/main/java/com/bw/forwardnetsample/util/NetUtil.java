package com.bw.forwardnetsample.util;

import com.bw.forwardnetsample.Api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络工具类
 * <p>
 * 1、静态内部类单例
 * 2、私有构造器中   构造日志拦截器、构造okhttp平台、构造Retrofit对象、构造api对象
 * 3、提供一个getApi()方法，供外部使用
 */
public class NetUtil {

    //公共的Url部分    以斜杠结尾
    private static final String BASE_URL = "http://mobile.bwstudent.com/";
    private final Api api;


    //提供单例对象
    public static NetUtil getInstance() {
        return SingleHolder.NET_UTIL;
    }

    //静态内部类的形式实现单例   这种方式也是懒汉式也是线程安全的
    private static final class SingleHolder {
        private static final NetUtil NET_UTIL = new NetUtil();
    }


    //私有的构造器
    private NetUtil() {
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //构造 OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .connectTimeout(8, TimeUnit.SECONDS)
                //当我们请求和服务器响应的时候，需要通过拦截器，所以可以在拦截器中可以对请求和响应做一些处理
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //接口公共的url部分
                .baseUrl(BASE_URL)
                //数据转换  Gson
                .addConverterFactory(GsonConverterFactory.create())
                //支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    //给外界提供一个Api对象
    public Api getApi() {
        return api;
    }
}
