package cn.tim.custom_view;

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


public class RoundProgressBar extends View {

    private int mRadius;
    private int mColor;
    private int mLineWidth;
    private int mTextSize;
    private int mProgress;

    private Paint mPaint;

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

        mRadius = (int) ta.getDimension(R.styleable.RoundProgressBar_radius, dp2px(30));
        mColor = ta.getColor(R.styleable.RoundProgressBar_color, 0xffff0000);
        mLineWidth = (int) ta.getDimension(R.styleable.RoundProgressBar_line_width, dp2px(3));
        mTextSize = (int) ta.getDimension(R.styleable.RoundProgressBar_android_textSize, dp2px(36));
        mProgress = ta.getInt(R.styleable.RoundProgressBar_android_progress, 30);

        ta.recycle();

        initPaint();
    }

    private float dp2px(int dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int width = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int needWidth = measureWidth() + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(needWidth, widthSize);
            } else {
                width = needWidth;
            }
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else {
            int needHeight = measureHeight() + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(needHeight, heightSize);
            } else { //MeasureSpec.UNSPECIFIED
                height = needHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    private int measureHeight()
    {
        return mRadius * 2;
    }

    private int measureWidth()
    {
        return mRadius * 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth * 1.0f / 4);

        int width = getWidth();
        int height = getHeight();

        mPaint.setStrokeWidth(mLineWidth);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        float angle = mProgress * 1.0f / 100 * 360;
        canvas.drawArc(new RectF(0, 0, width - getPaddingLeft() * 2, height - getPaddingLeft() * 2), 0, angle, false, mPaint);
        canvas.restore();

        String text = mProgress + "%";
//        text = "Tim";
        mPaint.setStrokeWidth(0);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mTextSize);
        int y = getHeight() / 2;
        Rect bound = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bound);
        int textHeight = bound.height();
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, 0, text.length(), getWidth() / 2, y + textHeight / 2, mPaint);

        mPaint.setStrokeWidth(0);
    }


    private static final String INSTANCE = "instance";
    private static final String KEY_PROGRESS = "key_progress";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PROGRESS, mProgress);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            mProgress = bundle.getInt(KEY_PROGRESS);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
