package cn.tim.study_module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.tim.annotation.ARouter;
import cn.tim.router_api.RouterManager;


@ARouter(path = "/app/MainActivity", group = "app")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if(BuildConfig.isRelease){
            Log.i(TAG, "onCreate: 集成化环境");
        }else{
            Log.i(TAG, "onCreate: 组件化环境");
        }
    }

    public void toOrderModule(View view) {
        RouterManager.getInstance()
                .build("/order/OrderMainActivity")
                .withInt("age", 18)
                .withString("name", "Tim")
                .withBoolean("xx", false)
                .navigation(this);
    }
}