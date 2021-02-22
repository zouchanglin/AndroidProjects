package cn.tim.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private List<String> appNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        ListView listView = findViewById(R.id.app_lv);
        appNameList = Arrays.asList("QQ", "微信", "慕课网", "牛客", "招商银行", "支付宝");

        // 增加头Header
        listView.addHeaderView(getLayoutInflater().inflate(R.layout.header_app_list, null));

        //listView.setAdapter(new AppListAdapterBase());
        List<ResolveInfo> resolveInfoList = getAppInfo();
        listView.setAdapter(new AppListAdapter(resolveInfoList));

        // 点击事件写法二
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ResolveInfo resolveInfo = resolveInfoList.get(position);
            String packageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;
            ComponentName componentName = new ComponentName(packageName, className);
            Intent intent = new Intent();
            intent.setComponent(componentName);
            startActivity(intent);
        });

        // 长按的事件
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确定删除吗？");
            builder.setPositiveButton("确定", (dialog, which) -> {
                resolveInfoList.remove(position);
                listView.setAdapter(new AppListAdapter(resolveInfoList));
            });
            builder.setNegativeButton("取消", null);
            builder.show();
            return false;
        });
    }

    // 获取所有的应用信息
    private List<ResolveInfo> getAppInfo(){
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
    }

//    public class AppListAdapter extends  BaseAdapter {
//        private List<ResolveInfo> resolveInfoList;
//
//        public AppListAdapter(List<ResolveInfo> appInfo) {
//            this.resolveInfoList = appInfo;
//        }
//
//        @Override
//        public int getCount() {
//            return resolveInfoList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return resolveInfoList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if(convertView == null){
//                LayoutInflater layoutInflater = getLayoutInflater();
//                convertView = layoutInflater.inflate(R.layout.item_app_list, null);
//            }
//            ImageView iv = convertView.findViewById(R.id.app_icon_iv);
//            TextView tv = convertView.findViewById(R.id.app_name_tv);
//            ResolveInfo resolveInfo = resolveInfoList.get(position);
//            tv.setText(resolveInfo.activityInfo.loadLabel(getPackageManager()));
//            iv.setImageDrawable(resolveInfo.activityInfo.loadIcon(getPackageManager()));
//            // 点击事件写法一
////            convertView.setOnClickListener((v) -> {
////                String packageName = resolveInfo.activityInfo.packageName;
////                String className = resolveInfo.activityInfo.name;
////                ComponentName componentName = new ComponentName(packageName, className);
////                Intent intent = new Intent();
////                intent.setComponent(componentName);
////                startActivity(intent);
////            });
//            return convertView;
//        }
//    }

    public class AppListAdapter extends  BaseAdapter {
        private List<ResolveInfo> resolveInfoList;

        public AppListAdapter(List<ResolveInfo> appInfo) {
            this.resolveInfoList = appInfo;
        }

        @Override
        public int getCount() {
            return resolveInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return resolveInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                LayoutInflater layoutInflater = getLayoutInflater();
                convertView = layoutInflater.inflate(R.layout.item_app_list, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = convertView.findViewById(R.id.app_icon_iv);
                viewHolder.textView = convertView.findViewById(R.id.app_name_tv);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ResolveInfo resolveInfo = resolveInfoList.get(position);
            viewHolder.textView.setText(resolveInfo.activityInfo.loadLabel(getPackageManager()));
            viewHolder.imageView.setImageDrawable(resolveInfo.activityInfo.loadIcon(getPackageManager()));
            return convertView;
        }
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

    public class AppListAdapterBase extends BaseAdapter {

        @Override
        public int getCount() {
            return appNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return appNameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.item_app_list, null);
            ImageView iv = convertView.findViewById(R.id.app_icon_iv);
            TextView tv = convertView.findViewById(R.id.app_name_tv);
            tv.setText(appNameList.get(position));
            return convertView;
        }
    }
}