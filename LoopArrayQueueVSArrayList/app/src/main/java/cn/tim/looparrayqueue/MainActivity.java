package cn.tim.looparrayqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SeekBar totalSeekBar;
    private SeekBar cacheSeekBar;
    private SeekBar threadSeekBar;
    private TextView argsTextView;

    private int totalNum = 50;
    private int cacheNum = 5;
    private int threadNum;

    private TextView resultTextView;

    private long loopArrayQueueTime;
    private long arrayListTime;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalSeekBar = findViewById(R.id.total_sb);
        cacheSeekBar = findViewById(R.id.cache_sb);
        threadSeekBar = findViewById(R.id.thread_sb);
        argsTextView = findViewById(R.id.args_tv);
        resultTextView = findViewById(R.id.result_tv);
        recyclerView = findViewById(R.id.recycler_view);
        // 设置线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        addListener();
        showArgs();
    }

    public void loopArrayQueueVSArrayList(View view) {
        long start = System.currentTimeMillis();
        testArrayList();
        long end = System.currentTimeMillis();
        arrayListTime = end - start;
        Log.i(TAG, "loopArrayQueueVSArrayList: ArrayList耗时 " + arrayListTime);

        start = System.currentTimeMillis();
        testLoopArrayQueue();
        end = System.currentTimeMillis();
        Log.i(TAG, "loopArrayQueueVSArrayList: LoopArrayQueue耗时 " + (end - start));
        loopArrayQueueTime = end - start;

        showResult();
    }

    public void concurrentLoopArrayQueueVSArrayList(View view) {
        long start = System.currentTimeMillis();
        testVector();
        long end = System.currentTimeMillis();
        Log.i(TAG, "concurrentLoopArrayQueueVSArrayList: ArrayList耗时 " + (end - start));
        arrayListTime = end - start;

        start = System.currentTimeMillis();
        testConcurrentLoopArrayQueue();
        end = System.currentTimeMillis();
        Log.i(TAG, "concurrentLoopArrayQueueVSArrayList: LoopArrayQueue耗时 " + (end - start));
        loopArrayQueueTime = end - start;

        showResult();
    }


    private void testArrayList(){
        List<Integer> list = new ArrayList<>(cacheNum);
        for(int i = 0; i < totalNum; i++){
            if(list.size() == cacheNum){
                list.remove(0);
            }
            list.add(i);
        }
    }

    private void testVector(){
        ArrayList<Integer> listOld = new ArrayList<>(cacheNum);
        Collection<Integer> list = Collections.synchronizedCollection(listOld);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        Runnable run = () -> {
            for(int i = 0; i < totalNum; i++){
                if(list.size() == cacheNum){
                    list.remove(0);
                }
                list.add(i);
            }
            countDownLatch.countDown();
        };
        for (int i = 0; i < threadNum; i++) {
            new Thread(run).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testConcurrentLoopArrayQueue(){
        ConcurrentLoopArrayQueue<Integer> queue = new ConcurrentLoopArrayQueue<>(cacheNum);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        Runnable run = () -> {
            for(int i = 0; i < totalNum; i++){
                queue.enqueue(i);
            }
            countDownLatch.countDown();
        };
        for (int i = 0; i < threadNum; i++) {
            new Thread(run).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testLoopArrayQueue(){
        LoopArrayQueue<Integer> queue = new LoopArrayQueue<>(cacheNum);
        for(int i = 0; i < totalNum; i++){
            queue.enqueue(i);
        }
    }

    private void showArgs(){
        argsTextView.setText(new StringBuilder("测试总数据量 ").append(totalNum).append("\n")
                .append("当前缓存量(容器大小) ").append(cacheNum).append("\n")
                .append("测试线程数 ").append(threadNum)
        );
    }
    private void showResult(){
        resultTextView.setText(new StringBuilder("LoopArrayQueue耗时: ").append(loopArrayQueueTime).append("ms\n")
                .append("ArrayList/Vector耗时:").append(arrayListTime).append("ms")
        );
    }

    private void addListener(){
        totalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                totalNum = progress;
                showArgs();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cacheSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cacheNum = progress;
                showArgs();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        threadSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                threadNum = progress;
                showArgs();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void testLoopArrayQueueInsert(View view) {
        LoopArrayQueue<Integer> queue = new LoopArrayQueue<>(cacheNum);
        MyAdapter myAdapter = new MyAdapter(this, queue);
        recyclerView.setAdapter(myAdapter);
        new Thread(()->{
            for (int i = 0; i < totalNum; i++) {
                final int x = i;
                runOnUiThread(() -> {
                    myAdapter.addData(x);
                });
                Log.i(TAG, "testLoopArrayQueueInsert: i = " + i);
            }
        }).start();
    }
}