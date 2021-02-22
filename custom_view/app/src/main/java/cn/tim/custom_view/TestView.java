package cn.tim.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TestView extends View {
    private Paint paint;

    private static final String TAG = "TestView";
    private String stringTest = "Tim";
    // 此方式不推荐使用
//    public TestView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        String stringTest = "MyString";
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
//        boolean booleanTest = typedArray.getBoolean(R.styleable.TestView_test_bool, false);
//        int integerTest = typedArray.getInteger(R.styleable.TestView_test_integer, -1);
//        stringTest = typedArray.getString(R.styleable.TestView_test_string);
//        int enumTest = typedArray.getInt(R.styleable.TestView_test_enum, 0);
//        float dimensionTest = typedArray.getDimension(R.styleable.TestView_test_dimension, 0);
//        Log.i(TAG, "TestView: booleanTest=" + booleanTest);
//        Log.i(TAG, "TestView: integerTest=" + integerTest);
//        Log.i(TAG, "TestView: stringTest=" + stringTest);
//        Log.i(TAG, "TestView: enumTest=" + enumTest);
//        Log.i(TAG, "TestView: dimensionTest=" + dimensionTest);
//        typedArray.recycle();
//    }


    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        boolean booleanTest = false;
        int integerTest = 0;
        int enumTest = 0;
        float dimensionTest = 0;
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.TestView_test_bool:
                    booleanTest = typedArray.getBoolean(R.styleable.TestView_test_bool, false);
                    break;
                case R.styleable.TestView_test_integer:
                    integerTest = typedArray.getInteger(R.styleable.TestView_test_integer, -1);
                    break;
                case R.styleable.TestView_test_string:
                    stringTest = typedArray.getString(R.styleable.TestView_test_string);
                    break;
                case R.styleable.TestView_test_enum:
                    enumTest = typedArray.getInt(R.styleable.TestView_test_enum, 0);
                    break;
                case R.styleable.TestView_test_dimension:
                    dimensionTest = typedArray.getDimension(R.styleable.TestView_test_dimension, 0);
                    break;
            }
        }
        typedArray.recycle();

        Log.i(TAG, "TestView: booleanTest=" + booleanTest);
        Log.i(TAG, "TestView: integerTest=" + integerTest);
        Log.i(TAG, "TestView: stringTest=" + stringTest);
        Log.i(TAG, "TestView: enumTest=" + enumTest);
        Log.i(TAG, "TestView: dimensionTest=" + dimensionTest);

        // 初始化Paint
        initPaint();
    }


    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            //int needWidth = measureWidth();
            // 想要支持Padding
            int needWidth = measureWidth() + getPaddingLeft() + getPaddingRight();
            if(widthMode == MeasureSpec.AT_MOST){
                width = Math.min(needWidth, widthSize);
            }else { //MeasureSpec.UNSPECIFIED 无限制
                width = widthSize;
            }
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            int needHeight = measureHeight() + getPaddingTop() + getPaddingBottom();
            if(heightMode == MeasureSpec.AT_MOST){
                height = Math.min(needHeight, heightSize);
            }else { //MeasureSpec.UNSPECIFIED 无限制
                height = heightSize;
            }
        }
        setMeasuredDimension(width, height);
    }

    // 根据显示的内容去计算
    private int measureHeight() {
        return 0;
    }

    // 根据显示的内容去计算
    private int measureWidth() {
        return 0;
    }

    // 初始化Paint
    private void initPaint() {
        paint = new Paint();
        // 绘制实心图形
        paint.setStyle(Paint.Style.STROKE);
        // 画笔的宽度
        paint.setStrokeWidth(6);
        paint.setColor(0XFFFF0000);
        // 设置抗锯齿
        paint.setAntiAlias(true);
    }

    // 绘制
    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制圆
//        canvas.drawCircle(getWidth()/2, getHeight()/2, getHeight()/2 - paint.getStrokeWidth()/2, paint);
//        // 重新设置画笔宽度
//        paint.setStrokeWidth(2);
//        // 绘制直线
//        canvas.drawLine(0, getWidth()/2, getWidth(), getHeight()/2, paint);
//        canvas.drawLine(getWidth()/2, 0, getWidth()/2, getHeight(), paint);
        // 绘制文本
        paint.setTextSize(72);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        canvas.drawText(stringTest, 0, stringTest.length(), 0, getHeight(), paint);
    }

    // 为了演示重建后的数据恢复
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        stringTest = "Jone";
        invalidate();
        return true;
    }

    // UI重建后数据的保存
    private static final String INSTANCE = "instance";
    private static final String KEY_TEXT = "key_text";
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEXT, stringTest);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            stringTest = bundle.getString(KEY_TEXT);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
