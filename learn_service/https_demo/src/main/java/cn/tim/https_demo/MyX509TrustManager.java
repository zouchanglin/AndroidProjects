package cn.tim.https_demo;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager {
    private static final String TAG = "MyX509TrustManager";
    // 证书对象
    private X509Certificate serverCert;

    public MyX509TrustManager(X509Certificate serverCert) {
        this.serverCert = serverCert;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // 遍历证书
        for (X509Certificate certificate: chain){
            // 校验合法性与是否过期
            certificate.checkValidity();
            try {
                // 校验公钥
                PublicKey publicKey = serverCert.getPublicKey();
                Log.i(TAG, "checkServerTrusted: " + publicKey.getFormat());
                Log.i(TAG, "checkServerTrusted: " + publicKey.toString());
                certificate.verify(publicKey);
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
