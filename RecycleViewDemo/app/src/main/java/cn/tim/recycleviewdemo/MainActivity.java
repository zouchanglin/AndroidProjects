package cn.tim.recycleviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        // 设置线性布局
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyRecycleViewAdapter(this, recyclerView);
        adapter.setListener(position -> Toast.makeText(MainActivity.this, "第" + position + "数据被点击", Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(adapter);

    }

    public void onClickAddData(View view) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("第" + i + "条数据");
        }
        adapter.setDataSource(data);
    }

    public void onClickHorizontal(View view) {
        linearLayoutManager.setReverseLayout(false);
        // 横向排列ItemView
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    public void onClickReverse(View view) {
        // 数据反向展示
        linearLayoutManager.setReverseLayout(true);
        // 数据纵向排列
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void onChangeLayout(View view) {
        switch (view.getId()){
            case R.id.btn_linear_layout:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            case R.id.btn_grid_layout:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                break;
            case R.id.btn_staggered_grid_layout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
        }
    }

    // 插入一条数据
    public void onInsertDataClick (View v) {
        adapter.addData(1);
    }

    // 删除一条数据
    public void onRemoveDataClick (View v) {
        adapter.removeData(1);
    }
}