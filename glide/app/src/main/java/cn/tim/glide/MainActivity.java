package cn.tim.glide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String imageUrl = "http://res.lgdsunday.club/big_img.jpg";
//    private static final String imageUrl = "https://img.zouchanglin.cn/20201122140138.png";
    private ImageView imageView;
    private Handler handler;
    private static final int LOAD_IMG = 1001;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv_main);
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == LOAD_IMG){
                    imageView.setImageBitmap(imageCompressL((Bitmap) msg.obj));
                }
            }
        };
    }

    private static Bitmap imageCompressL(Bitmap bitmap) {
        double targetWidth = Math.sqrt(64.00 * 1000);
        if (bitmap.getWidth() > targetWidth || bitmap.getHeight() > targetWidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            float x = (float) Math.max(targetWidth / bitmap.getWidth(), targetWidth / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale(x, x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    public void nativeLoadImage(View view) {
        imageView.setImageResource(R.drawable.ic_loading);
//        imageView.setImageResource(R.drawable.big_img);
        new Thread(()->{
            try {
                Log.i(TAG, "nativeLoadImage: start");
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message message = handler.obtainMessage();
                message.what = LOAD_IMG;
                message.obj = bitmap;
                handler.sendMessage(message);
            } catch (IOException e) {
                Log.e(TAG, "nativeLoadImage: net work error!");
            }
        }).start();
    }

    public void glideLoadImage(View view) {
//        imageView.setImageResource(R.drawable.ic_loading);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_loading)
                .circleCrop()
                .error(R.drawable.ic_load_failed);

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    public void glideAppLoadImage(View view) {
        GlideApp.with(this)
                .load(imageUrl)
                .injectOptions()
                .into(imageView);
    }

    public void glideAppLoadCircleImage(View view) {
        GlideApp.with(this)
                .load(imageUrl)
                .circleOptions()
                .into(imageView);
    }

    public void compressLoadImage(View view) {
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        Log.i(TAG, "compressLoadImage: width = " + width + ", height = " + height);
        Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.big_img, width/15, height/15);
        String size = bitmap.getByteCount() / 1024 + "kb";
        Log.i(TAG, "compressLoadImage: bitmap size = " + size);
        Toast.makeText(this, size, Toast.LENGTH_SHORT).show();

//        imageView.setImageBitmap(bitmap);
        Bitmap bitmapL = imageCompressL(bitmap);
        imageView.setImageBitmap(bitmapL);
        String sizeL = bitmapL.getByteCount() / 1024 + "kb";
        Toast.makeText(this, sizeL, Toast.LENGTH_SHORT).show();

    }

    public void compressLoadImageError(View view) {

        Bitmap bitmapL = imageCompressL(BitmapFactory.decodeResource(getResources(), R.drawable.big_img));
        imageView.setImageBitmap(bitmapL);
        String sizeL = bitmapL.getByteCount() / 1024 + "kb";
        Toast.makeText(this, sizeL, Toast.LENGTH_SHORT).show();

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}