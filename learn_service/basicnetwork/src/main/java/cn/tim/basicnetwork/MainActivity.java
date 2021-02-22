package cn.tim.basicnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    private EditText etInput;
    private TextView textView;
    private EditText udpServerET;
    private EditText tcpServerET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = findViewById(R.id.et_content);
        textView = findViewById(R.id.tv_show);
        udpServerET = findViewById(R.id.et_udp_server);
        tcpServerET = findViewById(R.id.et_tcp_server);
    }

    public void sendTcpMessage(View view) {
        String[] tcpInfo = tcpServerET.getText().toString().split(":");
        String inputContent = etInput.getText().toString();
        new Thread(()->{
            try (Socket socket = new Socket(tcpInfo[0], Integer.parseInt(tcpInfo[1]))){
                OutputStream os = socket.getOutputStream();
                os.write(inputContent.getBytes());
                InputStream is = socket.getInputStream();
                byte[] readBuf = new byte[1024];
                String recv = new String(readBuf, 0, is.read(readBuf));
                InetAddress address = socket.getInetAddress();
                String ret = String.format("%s-%s-%s", df.format(new Date()), address, recv);
                runOnUiThread(()-> textView.setText(ret));
            }catch (IOException e){
                Log.e(TAG, "sendTcpMessage: Error!");
            }
        }).start();
    }

    public void sendUdpMessage(View view) {
        String[] udpInfo = udpServerET.getText().toString().split(":");
        String inputContent = etInput.getText().toString();
        new Thread(()->{
            try {
                DatagramSocket socket = new DatagramSocket();
                byte[] bytes = inputContent.getBytes();
                InetAddress address = InetAddress.getByName(udpInfo[0]);
                int serverPort = Integer.parseInt(udpInfo[1]);
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, serverPort);
                socket.send(packet);
                byte[] recvBuf = new byte[1024];
                DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(recvPacket);
                String ret = String.format("%s-%s-%s", df.format(new Date()), address, new String(recvBuf));
                runOnUiThread(()-> textView.setText(ret));
            }catch (IOException e){
                Log.e(TAG, "sendUdpMessage: Error!");
            }
        }).start();
    }
}