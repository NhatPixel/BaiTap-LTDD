package com.example.apiserverandviewpager2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("getvideos.php")
    Call<MessageVideoModel> getVideos();
}
