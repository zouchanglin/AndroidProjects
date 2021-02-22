package cn.tim.litepal_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 创建数据库
        SQLiteDatabase database = LitePal.getDatabase();

        // 添加数据
        Book book = new Book("Think In Java", "Tim", 58);
        boolean saveRet = book.save();
        Log.i(TAG, "onCreate: saveRet = " + saveRet);
        new Book("Think In C/C++", "Tom", 38).save();
        Log.i(TAG, "onCreate: 添加数据成功");

        // 查询数据
        List<Book> bookList = DataSupport.findAll(Book.class);
        Book[] books = new Book[bookList.size()];
        bookList.toArray(books);
        Log.i(TAG, "onCreate: books = " + Arrays.toString(books));

        // 删除数据
        int delete = DataSupport.delete(Book.class, books[0].getId());
        Log.i(TAG, "onCreate: 删除数据成功，delete = " + delete);

        // 查询数据
        bookList = DataSupport.findAll(Book.class);
        books = new Book[bookList.size()];
        bookList.toArray(books);
        Log.i(TAG, "onCreate: books = " + Arrays.toString(books));

        // 修改数据
        Book cppBook = new Book("Think In C/C++", "Tom", 28);
        int update = cppBook.update(2);
        Log.i(TAG, "onCreate: 修改数据成功，update = " + update);

        // 查询数据
        bookList = DataSupport.findAll(Book.class);
        books = new Book[bookList.size()];
        bookList.toArray(books);
        Log.i(TAG, "onCreate: books = " + Arrays.toString(books));
    }
}
