package cn.tim.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";
    private SQLiteDatabase sqLiteDatabase;
    private UriMatcher matcher;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "delete: ");
        int matchCode = matcher.match(uri);
        switch (matchCode){
            case 1001:
                Log.i(TAG, "delete: 匹配到路径是/hello");
                break;
            case 1002:
                Log.i(TAG, "delete: 匹配cn.tim.myprovider/hello/任意数字");
                break;
            case 1003:
                Log.i(TAG, "delete: 匹配cn.tim.myprovider/world/任意字符串");
                break;
            default:
                Log.i(TAG, "delete: 执行删除数据库内容操作");
                return sqLiteDatabase.delete("stu_info", selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        if(values.size() > 0){
            Log.i(TAG, "insert: ");
            // 参数解释：操作表的名称、可以为空的列、参数
            id = sqLiteDatabase.insert("stu_info", null, values);
        }else {
            String authority = uri.getAuthority();
            String path = uri.getPath();
            String query = uri.getQuery();
            String name = uri.getQueryParameter("name");
            String age = uri.getQueryParameter("age");
            String gender = uri.getQueryParameter("gender");
            Log.i(TAG, "insert:->主机名:" + authority
                    + "，路径:" + path + "，查询数据:" + query + "，姓名:" + name
            + "，age:" + age + "，gender:" + gender);
            values.put("name", name);
            values.put("age", age);
            values.put("gender", gender);
            id = sqLiteDatabase.insert("stu_info", null, values);
        }
        return ContentUris.withAppendedId(uri, id);
    }

    // 在ContentProvider创建时调用
    @Override
    public boolean onCreate() {
        SQLiteOpenHelper helper = new SQLiteOpenHelper(getContext(), "stu.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("create table stu_info (id integer primary key autoincrement," +
                        " name varchar(30) not null, age integer," +
                        " gender varchar(2) not null)");
                Log.i(TAG, "onCreate: 数据库创建成功");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        sqLiteDatabase = helper.getWritableDatabase();

        // 参数代表无法匹配
        // content://cn.tim.myprovider/hello
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Authority 、路径、匹配码
        matcher.addURI("cn.tim.myprovider", "hello", 1001);
        // 匹配 cn.tim.myprovider/hello/任意数字
        matcher.addURI("cn.tim.myprovider", "hello/#", 1002);
        // 匹配 cn.tim.myprovider/world/任意字符串
        matcher.addURI("cn.tim.myprovider", "world/*", 1003);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query: ");
        return sqLiteDatabase.query("stu_info", projection, selection, selectionArgs, sortOrder, null, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.i(TAG, "update: ");
        return sqLiteDatabase.update("stu_info", values, selection, selectionArgs);
    }
}
