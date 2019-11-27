package com.yfy.app.net;


import com.yfy.base.Base;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Retrofit变量初始化
 */
public class RetrofitGenerator {
    private static final String TAG = "zxx";
    //基址
    public final static String BASE_URL = Base.RETROFIT_URI;
    //接口
    public static InterfaceApi weatherInterfaceApi;

    private static Strategy strategy = new AnnotationStrategy();
    private static Serializer serializer = new Persister(strategy);

    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

   private static Retrofit.Builder retrofitBuilder =  new Retrofit.Builder()
            .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
            .baseUrl(BASE_URL);

    public static <S> S createService(Class<S> serviceClass) {
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original
                        .newBuilder()
                        .header("Content-Type", "text/xml;charset=UTF-8")   // 对于SOAP 1.1， 如果是soap1.2 应是Content-Type: application/soap+xml; charset=utf-8
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
//                Log.e(TAG, "intercept: " +request.tag());
                return chain.proceed(request);
            }
        });

        OkHttpClient client = okHttpClient
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = retrofitBuilder.client(client).build();

//        Log.e(TAG, "createService: "+client.newBuilder() );
        return retrofit.create(serviceClass);
    }

    public static InterfaceApi getWeatherInterfaceApi() {
        if(weatherInterfaceApi == null) {
            weatherInterfaceApi = createService(InterfaceApi.class);
        }
        return weatherInterfaceApi;
    }

}
