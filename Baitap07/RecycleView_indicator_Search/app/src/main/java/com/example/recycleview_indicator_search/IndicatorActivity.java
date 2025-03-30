package com.example.recycleview_indicator_search;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview_indicator_search.data.model.IconModel;
import com.example.recycleview_indicator_search.ui.adapter.IconAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndicatorActivity extends AppCompatActivity {
    private IconAdapter iconAdapter;
    private ArrayList<IconModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.indicator_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView rcIcon = findViewById(R.id.rclcon);
        arrayList = new ArrayList<IconModel>();
        arrayList.add(new IconModel(R.drawable.icon1, "icon1"));
        arrayList.add(new IconModel(R.drawable.icon2, "icon2"));
        arrayList.add(new IconModel(R.drawable.icon3, "icon3"));
        arrayList.add(new IconModel(R.drawable.icon4, "icon4"));
        arrayList.add(new IconModel(R.drawable.icon5, "icon5"));
        arrayList.add(new IconModel(R.drawable.icon6, "icon6"));
        arrayList.add(new IconModel(R.drawable.icon7, "icon7"));
        arrayList.add(new IconModel(R.drawable.icon8, "icon8"));
        arrayList.add(new IconModel(R.drawable.icon9, "icon9"));
        arrayList.add(new IconModel(R.drawable.icon10, "icon10"));
        arrayList.add(new IconModel(R.drawable.icon11, "icon11"));
        arrayList.add(new IconModel(R.drawable.icon12, "icon12"));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(IndicatorActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(gridLayoutManager);
        iconAdapter = new IconAdapter(this, arrayList);
        rcIcon.setAdapter(iconAdapter);
        rcIcon.addItemDecoration(new LinePagerIndicatorDecoration());
        SearchView searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });
    }

    private void filterListener(String text) {
        List<IconModel> list = new ArrayList<>();
        for (IconModel iconModel:arrayList) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                list.add(iconModel);
                if (list.isEmpty()) {
                    Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).
                    show();
                } else {
                    iconAdapter.setListenerList(list);
                }
            }
        }
    }

    class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
        private final int indicatorHeight = 10;
        private final float indicatorStrokeWidth = 5f;
        private final float indicatorItemPadding = 10f;
        private final Paint paintActive = new Paint();
        private final Paint paintInactive = new Paint();

        public LinePagerIndicatorDecoration() {
            paintActive.setStrokeCap(Paint.Cap.ROUND);
            paintActive.setStrokeWidth(indicatorStrokeWidth);
            paintActive.setStyle(Paint.Style.STROKE);
            paintActive.setAntiAlias(true);
            paintActive.setColor(0xFF000000);

            paintInactive.setStrokeCap(Paint.Cap.ROUND);
            paintInactive.setStrokeWidth(indicatorStrokeWidth);
            paintInactive.setStyle(Paint.Style.STROKE);
            paintInactive.setAntiAlias(true);
            paintInactive.setColor(0x33000000);
        }

        @Override
        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int itemCount = parent.getAdapter().getItemCount();
            int itemsPerPage = 1;
            if (parent.getChildCount() > 0) {
                View child = parent.getChildAt(0);
                int childWidth = child.getWidth();
                itemsPerPage = parent.getWidth() / childWidth;
                if (itemsPerPage == 0) itemsPerPage = 1;
            }
            int pageCount = itemCount - itemsPerPage;

            float totalLength = (indicatorStrokeWidth + indicatorItemPadding) * pageCount;
            float startX = (parent.getWidth() - totalLength) / 2f;
            float yPosition = parent.getHeight() - indicatorHeight;

            drawInactiveIndicators(canvas, startX, yPosition, pageCount);
            drawActiveIndicator(canvas, startX, yPosition, parent);
        }

        private void drawInactiveIndicators(Canvas canvas, float startX, float yPosition, int pageCount) {
            for (int i = 0; i < pageCount; i++) {
                float x = startX + (i * (indicatorStrokeWidth + indicatorItemPadding));
                canvas.drawLine(x, yPosition, x + indicatorStrokeWidth, yPosition, paintInactive);
            }
        }

        private void drawActiveIndicator(Canvas canvas, float startX, float yPosition, RecyclerView parent) {
            int activePosition = ((GridLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }
            float highlightX = startX + (activePosition * (indicatorStrokeWidth + indicatorItemPadding));
            canvas.drawLine(highlightX, yPosition, highlightX + indicatorStrokeWidth, yPosition, paintActive);
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = indicatorHeight;
        }
    }
}