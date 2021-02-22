package cn.tim.viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPagerAdapter extends AppCompatActivity {
    private static final int INIT_POSITION = 0;
    private int[] myLayouts = {
            R.layout.view_first, R.layout.view_second, R.layout.view_third,
    };
    private List<View> views;

    private List<ImageView> myDotViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pager_adapter);
        ViewPager viewPager = findViewById(R.id.viewpager);
        views = new ArrayList<>();
        ViewGroup myDotViewGroup = findViewById(R.id.dot_layout);
        myDotViews = new ArrayList<>();
        for (int layoutId : myLayouts) {
            //views.add(getLayoutInflater().inflate(layoutId, null));
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            views.add(imageView);

            ImageView dot = new ImageView(this);
            dot.setImageResource(R.mipmap.ic_launcher);
            dot.setMaxHeight(100);
            dot.setMaxWidth(100);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80, 80);
            layoutParams.leftMargin = 20;
            dot.setLayoutParams(layoutParams);
            dot.setEnabled(false);
            myDotViewGroup.addView(dot);
            myDotViews.add(dot);
        }
        viewPager.setAdapter(mPageAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
        changeImageItem(INIT_POSITION);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    
            }

            @Override
            public void onPageSelected(int position) {
                changeImageItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeImageItem(int position) {
        for (int i = 0; i < myDotViews.size(); i++) {
            myDotViews.get(i).setImageResource(position == i ? R.drawable.diglett:R.mipmap.ic_launcher);
        }
    }

    PagerAdapter mPageAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return myLayouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View child = views.get(position);
            container.addView(child);
            return child;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
    };
}