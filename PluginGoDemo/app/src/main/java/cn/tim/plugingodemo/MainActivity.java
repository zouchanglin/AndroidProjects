package cn.tim.plugingodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.tim.plugin_lib.PluginManager;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView activityList;
    private static final String dexPath = "/sdcard/output.dex";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        // 加载sdcard中的dex文件 && 合并dexElements
        loadClass(this);

        activityList = findViewById(R.id.lv_activity);
    }

    public void loadTestPlugin(View view) {
        try {
            Class<?> clazz = Class.forName("cn.tim.plugin.Test");
            Object instance = clazz.newInstance();
            Method method = clazz.getMethod("print");
            method.invoke(instance);
            //method.invoke(null);
            Toast.makeText(this, "Load dex success!", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 加载dex -> loadTestPlugin
    public void onlyLoadTestPlugin(View view) {
        PathClassLoader pathClassLoader = new PathClassLoader(dexPath,null);
        try {
            Class<?> clazz = pathClassLoader.loadClass("cn.tim.plugin.Test");
            Method method = clazz.getMethod("print");
            method.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 合并dexElements
    public static void loadClass(Context context) {
        try {
            // 获取BaseDexClassLoader中的pathList（DexPathList）
            Class<?> clazz = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = clazz.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            // 获取DexPathList中的dexElements数组
            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            // 宿主的类加载器
            ClassLoader pathClassLoader = context.getClassLoader();
            // DexPathList类的对象
            Object hostPathList = pathListField.get(pathClassLoader);
            // 宿主的dexElements
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);

            String apkPath = "/sdcard/output.dex";
            // 插件的类加载器
            ClassLoader dexClassLoader = new DexClassLoader(
                    apkPath, context.getCacheDir().getAbsolutePath(),
                    null, pathClassLoader);

            // DexPathList类的对象
            Object pluginPathList = pathListField.get(dexClassLoader);
            // 宿主的dexElements
            Object[] pluginElements = (Object[]) dexElementsField.get(pluginPathList);

            // 创建一个新数组
            Object[] newDexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType()
                    , hostDexElements.length + pluginElements.length);
            System.arraycopy(hostDexElements, 0, newDexElements, 0, hostDexElements.length);
            System.arraycopy(pluginElements, 0, newDexElements, hostDexElements.length, pluginElements.length);
            // 赋值
            dexElementsField.set(hostPathList, newDexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 先加载插件
    public void loadPlugin(View view) {
        try {
            String path = AssetUtil.copyAssetToCache(MainActivity.this, "plugin_module-debug.apk");
            PluginManager.getInstance().loadPluginApk(path);

            PackageInfo packageInfo = PluginManager.getInstance().getPackageInfo();
            List<String> activityNameList = new ArrayList<>();
            for(ActivityInfo activityInfo: packageInfo.activities){
                Log.i(TAG, "loadPlugin: activity name = " + activityInfo.name);
                activityNameList.add(activityInfo.name);
            }

            ArrayAdapter<String> arrayAdapter= new ArrayAdapter<> (
                    MainActivity.this, android.R.layout.simple_list_item_1, activityNameList);
            activityList.setAdapter(arrayAdapter);
            activityList.setOnItemClickListener((parent, view1, position, id) ->
                    PluginManager.getInstance().gotoActivity(MainActivity.this, activityNameList.get(position)));
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void startPlugin(View view) {
        // 先跳到代理Activity，由代理Activity展示真正的Activity内容
        PluginManager.getInstance().gotoActivity(this,
                PluginManager.getInstance().getPackageInfo().activities[0].name);
    }






    // 申请权限请求码
    private static final int REQUEST_EXTERNAL_STORAGE = 1001;
    public static void verifyStoragePermissions(Activity activity) {
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED
                || readPermission != PackageManager.PERMISSION_GRANTED) {
            // 如果没有权限需要动态地去申请权限
            ActivityCompat.requestPermissions(
                    activity,
                    // 权限数组
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    // 权限请求码
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}