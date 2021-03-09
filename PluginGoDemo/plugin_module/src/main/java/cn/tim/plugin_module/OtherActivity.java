package cn.tim.plugin_module;


import android.os.Bundle;

import cn.tim.plugin_lib.PluginBaseActivity;

public class OtherActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }
}