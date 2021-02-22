package cn.tim.https_demo;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private static Handler mUIHandler = new Handler(Looper.getMainLooper());

    interface HttpListener {
        void onSuccess(String content);

        void onFail(Exception e);
    }

    public static void doGet(String urlStr, HttpListener listener) {
        new Thread(() -> {
            Looper.prepare();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();

                try (InputStream is = conn.getInputStream();
                     InputStreamReader reader = new InputStreamReader(is)
                ) {
                    char[] buf = new char[4096];
                    int len;
                    StringBuilder sb = new StringBuilder();
                    while ((len = reader.read(buf)) != -1) {
                        sb.append(new String(buf, 0, len));
                    }
                    mUIHandler.post(() -> listener.onSuccess(sb.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFail(e);
                }
            }catch (IOException e){
                e.printStackTrace();
                listener.onFail(e);
            }
        }).start();
    }
}
