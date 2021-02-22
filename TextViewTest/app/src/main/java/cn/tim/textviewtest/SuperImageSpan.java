package cn.tim.textviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.style.ImageSpan;

/**
 * 文字与图片对齐、支持MarginLeft\MarginRight
 */
public class SuperImageSpan extends ImageSpan {
    private int marginLeft;
    private int marginRight;

    public SuperImageSpan(@NonNull Context context, int resourceId) {
        super(context, resourceId);
    }

    /**
     * 图片资源可指定大小插入
     * @param context context
     * @param bitmap Bitmap
     * @param marginLeft marginLeft
     * @param marginRight marginRight
     */
    public SuperImageSpan(@NonNull Context context, @NonNull Bitmap bitmap, int marginLeft, int marginRight) {
        super(context, bitmap);
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
    }

    /**
     * 传入的BitMap可以按照新宽高拉伸
     * @param context context
     * @param bitmap bitmap
     * @param marginLeft marginLeft px
     * @param marginRight marginRight px
     * @param height height px
     * @param width width px
     * @return SuperImageSpan
     */
    public static SuperImageSpan getInstance(@NonNull Context context, @NonNull Bitmap bitmap, int marginLeft, int marginRight, int height, int width) {
        Bitmap newBitmap = zoomImg(bitmap, width, height);
        return new SuperImageSpan(context, newBitmap, marginLeft, marginRight);
    }

    /**
     * 图片资源以原始大小插入
     * @param context context
     * @param resourceId resID
     * @param marginLeft marginLeft
     * @param marginRight marginRight
     */
    public SuperImageSpan(@NonNull Context context, int resourceId, int marginLeft, int marginRight) {
        super(context, resourceId);
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        return marginLeft + super.getSize(paint, text, start, end, fm) + marginRight;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        // image to draw
        Drawable b = getDrawable();
        // font metrics of text to be replaced
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2
                - b.getBounds().bottom / 2;

        canvas.save();
        canvas.translate(x + marginLeft, transY);
        b.draw(canvas);
        canvas.restore();
    }

    private static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }
}
