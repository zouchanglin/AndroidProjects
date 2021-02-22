package cn.tim.https_demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class HttpsUtils {
    private static Handler mUIHandler = new Handler(Looper.getMainLooper());

    interface HttpListener {
        void onSuccess(String content);

        void onFail(Exception e);
    }

    public static void doGet(Context context, String urlStr, HttpListener listener) {
        new Thread(() -> {
            Looper.prepare();
            try {
                URL url = new URL(urlStr);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                SSLContext sslContext = SSLContext.getInstance("TLS");
                X509Certificate serverCert = getCert(context);
                TrustManager[] trustManagers = {new MyX509TrustManager(serverCert)};
                sslContext.init(null, trustManagers, new SecureRandom());
                conn.setSSLSocketFactory(sslContext.getSocketFactory());
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
            }catch (Exception e){
                e.printStackTrace();
                listener.onFail(e);
            }
        }).start();
    }

    private static X509Certificate getCert(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("zouchanglin.cn.cer");
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            return (X509Certificate) factory.generateCertificate(inputStream);
        } catch (IOException | CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
