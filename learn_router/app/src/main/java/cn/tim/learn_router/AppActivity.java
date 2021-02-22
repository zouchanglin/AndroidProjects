package cn.tim.learn_router;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.tim.common.Router;

//import cn.tim.find.FindActivity;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
    }

    public void toFindActivity(View view) {
//        startActivity(new Intent(this, FindActivity.class));
        Router.getInstance().startActivity(this, "/find/main");
    }
}