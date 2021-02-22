package cn.tim.roundprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class RoundProgressBar extends View {
    private int mRadius;
    private int mColor;
    private int mLineWidth;
    private int mTextSize;
    private int mProcess;
    private Paint mPaint;

    private RectF rectF;
    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        mRadius = (int)typedArray.getDimension(R.styleable.RoundProgressBar_radius, dp2px(30));
        mColor = typedArray.getColor(R.styleable.RoundProgressBar_color, 0xffff0000);
        mLineWidth = (int) typedArray.getDimension(R.styleable.RoundProgressBar_line_width, dp2px(6));
        mTextSize = (int) typedArray.getDimension(R.styleable.RoundProgressBar_android_textSize, dp2px(32));
        mProcess = typedArray.getInt(R.styleable.RoundProgressBar_android_process, 0);
        typedArray.recycle();
        initPaint();
    }


    private float dp2px(int dpValue) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private void initPaint() {
        mPaint = new Paint();
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
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
        width = Math.min(width, height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0f, 0f, w - getPaddingLeft() * 2f, h - getPaddingLeft() * 2f);
    }

    // 根据显示的内容去计算
    private int measureHeight() {
        return mRadius * 2;
    }

    // 根据显示的内容去计算
    private int measureWidth() {
        return mRadius * 2;
    }

    // OnDraw里面尽量不要new对象
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth * 1.0f/4);
        int width = getWidth();
        int height = getHeight();

        canvas.drawCircle(width / 2f, height / 2f,
                width / 2f - getPaddingLeft() - mPaint.getStrokeWidth()/2f, mPaint);
        mPaint.setStrokeWidth(mLineWidth);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        float angle = mProcess / 100.0f * 360;
        canvas.drawArc(rectF, 0, angle, false, mPaint);
        canvas.restore();

        String text = mProcess + "%";
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        // 测量字体高度
        int y = getWidth() / 2 + getPaddingTop();
        Rect rectBound = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rectBound);
        int textHeight = rectBound.height();
        canvas.drawText(text, 0, text.length(),
                getWidth() / 2f,
                y + textHeight/2f  - mPaint.descent() / 2 - getPaddingTop()/2f,
                mPaint);

        // 基准线绘制
        //canvas.drawLine(width/2f, 0, width/2f, height, mPaint);
        //canvas.drawLine(0, height/2f, width, height/2f, mPaint);
    }

    public void setProgress(int progress){
        mProcess = progress;
        invalidate();
    }

    public int getProcess(){
        return mProcess;
    }


    // UI重建后数据的保存
    private static final String INSTANCE = "instance";
    private static final String KEY_PROGRESS = "key_progress";
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PROGRESS, mProcess);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            mProcess = bundle.getInt(KEY_PROGRESS);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
