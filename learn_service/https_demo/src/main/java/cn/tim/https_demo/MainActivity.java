package cn.tim.https_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUrl = findViewById(R.id.et_url);
        tvShow = findViewById(R.id.tv_show);
    }

    public void loadContent(View view) {
        String url = etUrl.getText().toString();
        Https2Utils.doGet(this, url, new Https2Utils.HttpListener() {
            @Override
            public void onSuccess(String content) {
                tvShow.setText(content);
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(MainActivity.this, "Failed！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
