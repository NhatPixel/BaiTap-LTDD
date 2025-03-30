package com.example.fragment_tablayout_viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.fragment_tablayout_viewpager.data.api.ApiService;
import com.example.fragment_tablayout_viewpager.data.api.RetrofitClient;
import com.example.fragment_tablayout_viewpager.data.model.Images;
import com.example.fragment_tablayout_viewpager.data.model.ImagesSlider;
import com.example.fragment_tablayout_viewpager.data.model.MessageModel;
import com.example.fragment_tablayout_viewpager.ui.adapter.ExerciseAdapter;
import com.example.fragment_tablayout_viewpager.ui.adapter.ImagesViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseActivity extends AppCompatActivity {
    ApiService apiService;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<ImagesSlider> imagesList;
    private Handler handler = new Handler();
    MessageModel message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.viewpage);
        circleIndicator = findViewById(R.id.circle_indicator);
        loadImages(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int position0ffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void loadImages(int position) {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        apiService.LoadImageSlider(position).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.isSuccessful()) {
                    message = response.body();
                    imagesList = message.getResult();
                    ExerciseAdapter adapter = new ExerciseAdapter(imagesList);
                    viewPager.setAdapter(adapter);
                    circleIndicator.setViewPager(viewPager);
                    handler.postDelayed(runnable, 3000);
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == imagesList.size() - 1){
                viewPager.setCurrentItem(0);
            }else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

            }
        }
    };
}