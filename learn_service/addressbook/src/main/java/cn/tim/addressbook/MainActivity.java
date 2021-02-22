package cn.tim.addressbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // 申请权限请求码
    private static final int REQUEST_READ_SMS = 1001;

    // 检查权限，这种写法主要是针对比较新的Android6.0及以后的版本
    public static void verifyStoragePermissions(Activity activity) {
        int smsPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        int contactsPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
        int writeContactsPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS);

        if (smsPermission != PackageManager.PERMISSION_GRANTED
                || contactsPermission != PackageManager.PERMISSION_GRANTED
                || writeContactsPermission != PackageManager.PERMISSION_GRANTED) {
            // 如果没有权限需要动态地去申请权限
            ActivityCompat.requestPermissions(
                    activity,
                    // 权限数组
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS,  Manifest.permission.WRITE_CONTACTS},
                    // 权限请求码
                    REQUEST_READ_SMS
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
    }

    public void visitMessage(View view) {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while(cursor != null && cursor.moveToNext()){
            int addressIndex = cursor.getColumnIndex("address");
            int bodyIndex = cursor.getColumnIndex("body");
            String address = cursor.getString(addressIndex);
            String body = cursor.getString(bodyIndex);
            Log.i(TAG, "visitMessage:" + address + ":" + body);
        }
        if(cursor != null) cursor.close();
    }

    public void visitAddressBook(View view) {
        ContentResolver resolver = getContentResolver();
        //联系人姓名 + Id
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //联系人电话
        Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while(cursor!= null && cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Log.i(TAG, "visitAddressBook: name = " + name + ", id = " + contactId);
            String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" +contactId;
            Cursor phoneCursor = resolver.query(uriPhone,null, selection, null, null);
            while (phoneCursor != null && phoneCursor.moveToNext()){
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.i(TAG, "visitAddressBook: name = " + name + ", phone = " + phone);
            }
            if(phoneCursor != null) phoneCursor.close();
        }
        if(cursor != null) cursor.close();
    }

    public void addAddressBook(View view) {
        //1、往一个ContentProvider中插入一条空数据，获取新生成的Id
        //2、利用刚刚生成的Id分别组合姓名和电话号码往另一个ContentProvider中插入数据
        ContentValues values = new ContentValues();
        ContentResolver resolver = getContentResolver();
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        if(uri == null) throw new RuntimeException("插入新联系人失败");
        values.clear();
        long id = ContentUris.parseId(uri);
        // 插入姓名
        values.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID, id);
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "Mike");
        values.put(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        uri = resolver.insert(ContactsContract.Data.CONTENT_URI, values);
        if(uri != null) Log.i(TAG, "addAddressBook: 插入姓名,id = " + ContentUris.parseId(uri));
        //插入电话信息
        values.clear();
        values.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, id);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "15720918678"); //添加号码
        values.put(ContactsContract.CommonDataKinds.Phone.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE); //添加号码类型
        uri = resolver.insert(ContactsContract.Data.CONTENT_URI, values);
        if(uri != null) Log.i(TAG, "addAddressBook: 插入电话号码,id = " + ContentUris.parseId(uri));
    }
}
