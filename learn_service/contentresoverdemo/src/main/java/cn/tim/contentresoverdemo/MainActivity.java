package cn.tim.contentresoverdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ContentResolver contentResolver;
    private EditText etId;
    private EditText etName;
    private EditText etAge;
    private String sex = "男";
    private ListView lvData;

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

        // 获取ContentResolver对象
        contentResolver = getContentResolver();

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
        Uri uri = Uri.parse("content://cn.tim.myprovider");
        List<StudentInfo> stuList = new ArrayList<>();
        // 参数解释：表名、要查询的字段、列条件、列条件参数、GroupBy、having、orderBy
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if(cursor !=null && cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                String sex = cursor.getString(3);
                stuList.add(new StudentInfo(id, name, age, sex));
            } while (cursor.moveToNext());
            cursor.close();
        }
        lvData.setAdapter(new StuInfoAdapter(this, stuList));
    }


    public void operatorData(View view) {
        Uri uri = Uri.parse("content://cn.tim.myprovider");
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn_add:
                // 参数解释：操作表的名称、可以为空的列、参数
                ContentValues values = new ContentValues();
                values.put("name", etName.getText().toString());
                values.put("age", Integer.parseInt(etAge.getText().toString()));
                values.put("gender", sex);
                contentResolver.insert(uri, values);
                // 刷新数据展示
                flushStuData();
                Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                String idStr = etId.getText().toString();
                ContentValues updateValues = new ContentValues();
                // Key - value
                updateValues.put("name", etName.getText().toString());
                updateValues.put("age", Integer.parseInt(etAge.getText().toString()));
                updateValues.put("gender", sex);
                contentResolver.update(uri, updateValues, "id=?", new String[]{idStr});
                Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                flushStuData();
                break;
            case R.id.btn_delete:
                String deleteIdStr = etId.getText().toString();
                contentResolver.delete(uri, "id=?", new String[]{deleteIdStr});
                // 刷新数据展示
                flushStuData();
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void matchUri(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_matcher:
                contentResolver.delete(Uri.parse("content://cn.tim.myprovider/hello"), null, null);
                contentResolver.delete(Uri.parse("content://cn.tim.myprovider/hello/1234"), null, null);
                contentResolver.delete(Uri.parse("content://cn.tim.myprovider/world/xxxx"), null, null);
                break;
            case R.id.btn_uri:
                Uri insertUri = Uri.parse("content://cn.tim.myprovider/whatever?name=Tim&age=22&gender=男");
                Uri uri = contentResolver.insert(insertUri, new ContentValues());
                long newId = ContentUris.parseId(uri);
                Toast.makeText(this, "添加成功: Id" + newId, Toast.LENGTH_SHORT).show();
                flushStuData();
                break;
        }
    }
}

