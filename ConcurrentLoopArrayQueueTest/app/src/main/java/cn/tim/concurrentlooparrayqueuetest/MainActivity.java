package cn.tim.concurrentlooparrayqueuetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //static Random random = new Random();

    // 最多存储的消息数目
    private static final int MESSAGE_MAX_COUNT = 1000;

    // 插入的消息数量
    private static final int MESSAGE_COUNT = 500000;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
    }

    public void loopArrayQueueVSArrayList(View view) {
        long start = System.currentTimeMillis();
        testArrayList();
        long end = System.currentTimeMillis();
        Log.i(TAG, "loopArrayQueueVSArrayList: testArrayList " + (end - start));
        tv1.setText("ArrayList耗时：" + (end - start));

        start = System.currentTimeMillis();
        testLoopArrayQueue();
        end = System.currentTimeMillis();
        Log.i(TAG, "loopArrayQueueVSArrayList: testLoopArrayQueue " + (end - start));
        tv2.setText("LoopArrayQueue耗时：" + (end - start));
    }

    public void concurrentLoopArrayQueueVSArrayList(View view) {
        long start = System.currentTimeMillis();
        testArrayList();
        long end = System.currentTimeMillis();
        Log.i(TAG, "concurrentLoopArrayQueueVSArrayList: testArrayList " + (end - start));
        tv1.setText("ArrayList耗时：" + (end - start));

        start = System.currentTimeMillis();
        testConcurrentLoopArrayQueue();
        end = System.currentTimeMillis();
        Log.i(TAG, "concurrentLoopArrayQueueVSArrayList: testLoopArrayQueue " + (end - start));
        tv2.setText("LoopArrayQueue耗时：" + (end - start));
    }

    private static void testArrayList(){
        List<Integer> list = new ArrayList<>(MESSAGE_MAX_COUNT);
        for(int i = 0; i < MESSAGE_COUNT; i++){
            if(list.size() == MESSAGE_MAX_COUNT){
                list.remove(0);
            }
            list.add(i);
        }
    }

    private static void testConcurrentLoopArrayQueue(){
        ConcurrentLoopArrayQueue<Integer> queue = new ConcurrentLoopArrayQueue<>(MESSAGE_MAX_COUNT);
        for(int i = 0; i < MESSAGE_COUNT; i++){
            queue.enqueue(i);
        }
    }

    private static void testLoopArrayQueue(){
        LoopArrayQueue<Integer> queue = new LoopArrayQueue<>(MESSAGE_MAX_COUNT);
        for(int i = 0; i < MESSAGE_COUNT; i++){
            queue.enqueue(i);
        }
    }
}