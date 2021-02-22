package cn.tim.textviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String content = "    在安卓开发中，随着业务的丰富，在列表数据展示中，布局多样式很常见，如下图所示： 第一步：拿到UI页面之后不要慌，分...在安卓开发中，随着业务的丰富，在列表数据展示中，布局多样式很常见";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.main_tv);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xx);
        SpannableStringBuilder sp = new SpannableStringBuilder();
        sp.append(content);
        SuperImageSpan imageSpan = SuperImageSpan.getInstance(this, bitmap,
                0, SizeUtils.dp2px(10),
                SizeUtils.dp2px(14), SizeUtils.dp2px(32));
        sp.setSpan(imageSpan, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(sp);

        showTVOne();
        showTVTwo();
        showTVThree();
    }

    private void showTVOne() {
        TextView tv = findViewById(R.id.main_tv2);
        String content = "In Android development, with the enrichment of business, layout multi style is very common in list data display, as shown in the figure below: Step 1: don't panic after getting the UI page. In Android development, with the enrichment of business, layout multi style is very common in list data display.";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xx);
        SpannableStringBuilder sp = new SpannableStringBuilder();
        sp.append(content);
        SuperImageSpan imageSpan = SuperImageSpan.getInstance(this, bitmap,
                SizeUtils.dp2px(5), SizeUtils.dp2px(5),
                SizeUtils.dp2px(14), SizeUtils.dp2px(32));
        sp.setSpan(imageSpan, 10, 15, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }

    private void showTVTwo() {
        TextView tv = findViewById(R.id.main_tv3);
        String content = "    In Android development, with the enrichment of business, layout multi style is very common in list data display, as shown in the figure below: Step 1: don't panic after getting the UI page. In Android development, with the enrichment of business, layout multi style is very common in list data display.";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xx);
        SpannableStringBuilder sp = new SpannableStringBuilder();
        sp.append(content);
        SuperImageSpan imageSpan = SuperImageSpan.getInstance(this, bitmap,
                0, SizeUtils.dp2px(10),
                SizeUtils.dp2px(14), SizeUtils.dp2px(32));
        sp.setSpan(imageSpan, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }

    private void showTVThree() {
        TextView tv = findViewById(R.id.main_tv4);
        String content = "     【原生ImageSpan】在安卓开发中，随着业务的丰富，在列表数据展示中，布局多样式很常见，如下图所示： 第一步：拿到UI页面之后不要慌，分...在安卓开发中，随着业务的丰富，在列表数据展示中，布局多样式很常见";
        SpannableStringBuilder sp = new SpannableStringBuilder();
        sp.append(content);
        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.xx);
        sp.setSpan(imageSpan, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }
}