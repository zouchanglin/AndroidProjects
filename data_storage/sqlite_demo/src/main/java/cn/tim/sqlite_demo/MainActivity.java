package cn.tim.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText etId;
    private EditText etName;
    private EditText etAge;
    private String sex;
    private SQLiteDatabase sqLiteDatabase;
    private ListView lvData;

    // 申请权限请求码
    private static final int REQUEST_EXTERNAL_STORAGE = 1001;

    // 检查权限，这种写法主要是针对比较新的Android6.0及以后的版本
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);

        // 单选框组件
        RadioGroup rgSex = findViewById(R.id.rg_sex);
        lvData = findViewById(R.id.lv_data);
        verifyStoragePermissions(this);
        /*
         * 构造参数：
         * 1、上下文
         * 2、数据库名称，默认位置应用的私有目录（内部存储的database文件夹）
         * 3、CursorFactory类型
         * 4、数据库版本
         */
        String path = Environment.getExternalStorageDirectory() + "/sqlite_demo.db";
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this, path, null, 1){
            // 创建数据库
            @Override
            public void onCreate(SQLiteDatabase db) {
                Toast.makeText(MainActivity.this, "数据库创建", Toast.LENGTH_SHORT).show();
                // 如果事先没有数据库的话，创建表的操作就可以在这里进行
                db.execSQL("create table stu_info (id integer primary key autoincrement, name varchar(30) not null, age integer,gender varchar(2) not null)");
            }
            // 版本号变化之后会调用这个方法
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Toast.makeText(MainActivity.this, "数据库升级", Toast.LENGTH_SHORT).show();
            }
        };
        // 获取数据库对象
        sqLiteDatabase = helper.getWritableDatabase();
        // 设置单选框的监听
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_female:
                        sex = "女";
                        break;
                    case R.id.rb_male:
                        sex = "男";
                        break;
                }
            }
        });
        flushStuData();
    }

    private void flushStuData() {
        List<StudentInfo> stuList = new ArrayList<>();
        // 参数解释：表名、要查询的字段、列条件、列条件参数、GroupBy、having、orderBy
        Cursor cursor = sqLiteDatabase.query("stu_info", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                String sex = cursor.getString(3);
                stuList.add(new StudentInfo(id, name, age, sex));
            } while (cursor.moveToNext());
        }
        cursor.close();
        lvData.setAdapter(new StuInfoAdapter(this, stuList));
    }


    public void operatorData(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn_add:
                // 参数解释：操作表的名称、可以为空的列、参数
                ContentValues values = new ContentValues();
                // Key - value
                values.put("name", etName.getText().toString());
                values.put("age", Integer.parseInt(etAge.getText().toString()));
                values.put("gender", sex);
                // 插入成功，返回数据的ID
                long infoId = sqLiteDatabase.insert("stu_info", null, values);
                // 刷新数据展示
                flushStuData();
                Toast.makeText(MainActivity.this, "添加成功，Id = " + infoId, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                String idStr = etId.getText().toString();
                ContentValues updateValues = new ContentValues();
                // Key - value
                updateValues.put("name", etName.getText().toString());
                updateValues.put("age", Integer.parseInt(etAge.getText().toString()));
                updateValues.put("gender", sex);
                int info = sqLiteDatabase.update("stu_info", updateValues, "id=?", new String[]{idStr});
                // 刷新数据展示
                flushStuData();
                Toast.makeText(MainActivity.this, "更新成功，影响行数：" + info, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                String deleteIdStr = etId.getText().toString();
                int delete = sqLiteDatabase.delete("stu_info", "id=?", new String[]{deleteIdStr});
                // 刷新数据展示
                flushStuData();
                Toast.makeText(MainActivity.this, "删除成功，影响行数：" + delete, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
