package cn.tim.plugin_module;

import android.content.Intent;
import android.os.Bundle;

import cn.tim.plugin_lib.PluginBaseActivity;

public class PluginModuleActivityA extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_module_a);

        // FIXME 这里的Context需要使用proxy代理对象
        findViewById(R.id.to_module_activityb_btn).setOnClickListener(
                e -> startActivity(new Intent(proxy, PluginModuleActivityB.class)));
    }
}