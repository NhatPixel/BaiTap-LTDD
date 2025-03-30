package com.example.fragment_tablayout_viewpager.ui.transformer;
import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

public class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.85f; // Kích thước nhỏ nhất khi thu nhỏ
    private static final float MIN_ALPHA = 0.5f;  // Độ mờ nhỏ nhất khi chuyển trang

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // Trang này nằm ngoài màn hình bên trái
            view.setAlpha(0f);

        } else if (position <= 1) { // Trang đang hiển thị hoặc chuẩn bị vào màn hình
            // Tính toán tỷ lệ thu nhỏ
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;

            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Áp dụng tỷ lệ thu nhỏ
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Làm mờ trang khi thu nhỏ
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // Trang này nằm ngoài màn hình bên phải
            view.setAlpha(0f);
        }
    }
}
