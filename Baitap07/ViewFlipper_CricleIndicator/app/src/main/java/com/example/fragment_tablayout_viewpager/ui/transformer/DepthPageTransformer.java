package com.example.fragment_tablayout_viewpager.ui.transformer;
import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.75f; // Kích thước nhỏ nhất khi trang co lại

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // Trang này nằm ngoài màn hình bên trái
            view.setAlpha(0f);

        } else if (position <= 0) { // Trang đang ở vị trí hiện tại hoặc trượt sang trái
            view.setAlpha(1f);
            view.setTranslationX(0f);
            view.setTranslationZ(0f);
            view.setScaleX(1f);
            view.setScaleY(1f);

        } else if (position <= 1) { // Trang sắp xuất hiện từ bên phải
            view.setAlpha(1 - position); // Mờ dần
            view.setTranslationX(pageWidth * -position); // Dịch sang trái
            view.setTranslationZ(-1f); // Đưa trang này ra sau trang hiện tại

            // Thu nhỏ trang dần dần
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // Trang nằm ngoài màn hình bên phải
            view.setAlpha(0f);
        }
    }
}
