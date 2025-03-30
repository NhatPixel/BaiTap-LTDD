package com.example.fragment_tablayout_viewpager;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fragment_tablayout_viewpager.data.model.Images;
import com.example.fragment_tablayout_viewpager.ui.adapter.ImagesViewPager2Adapter;
import com.example.fragment_tablayout_viewpager.ui.transformer.DepthPageTransformer;
import com.example.fragment_tablayout_viewpager.ui.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class CircleIndicator3ViewPager2Activity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Images> imagesList1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_circle_indicator3_view_pager2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager2 = findViewById(R.id.viewpage2);
        circleIndicator3 = findViewById(R.id.circle_indicator3);
        imagesList1 = getListImages() ;
        ImagesViewPager2Adapter adapter1 = new ImagesViewPager2Adapter(imagesList1);
        viewPager2.setAdapter(adapter1);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
        viewPager2.setPageTransformer(new DepthPageTransformer());
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
    }
    private List<Images> getListImages(){
        List<Images> list = new ArrayList<>();
        list.add(new Images(R.drawable.anh1));
        list.add(new Images(R.drawable.anh2));
        list.add(new Images(R.drawable.anh3));
        list.add(new Images(R.drawable.anh5));
        return list;
    }
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem() == imagesList1.size() - 1){
                viewPager2.setCurrentItem(0);
            }else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);

            }
        }
    };
}