package com.example.retrofit2.data.api;

import com.example.retrofit2.data.model.Category;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();
}
