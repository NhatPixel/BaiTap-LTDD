package com.example.uploadfile.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            // Tạo interceptor để log request/response (dành cho debug)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Tạo OkHttpClient với timeout & interceptor
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS) // Timeout kết nối
                    .readTimeout(30, TimeUnit.SECONDS)   // Timeout đọc dữ liệu
                    .writeTimeout(30, TimeUnit.SECONDS)  // Timeout ghi dữ liệu
                    .build();

            // Tạo Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://app.iotstar.vn:8081/appfoods/") // Đảm bảo có dấu `/`
                    .client(okHttpClient) // Thêm OkHttpClient vào Retrofit
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON -> Object
                    .build();
        }
        return retrofit;
    }
}
