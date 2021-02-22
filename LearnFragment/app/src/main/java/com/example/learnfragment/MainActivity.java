package com.example.learnfragment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ListFragment.OnTittleListener {
    ListFragment leftFragment = null;
    boolean leftDisplay = false;
    ListFragment rightFragment = null;
    boolean rightDisplay = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1、container 2、fragment 3、fragment -> container
        leftFragment = ListFragment.getInstance("Left Fragment!");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.list_container, leftFragment)
                .commit();
        leftDisplay = true;
        leftFragment.setOnTittleListener(this);

    }

    @Override
    public void onClick(String tittle) {
        setTitle(tittle);
    }

    // 静态加载
    public void toStaticLoadActivity(View view) {
        startActivity(new Intent(MainActivity.this, StaticLoadActivity.class));
    }

    public void dynamicLoad(View view) {
        int id = view.getId();
        switch (id){
            case R.id.load_left:
                if(leftFragment == null) {
                    leftFragment = ListFragment.getInstance("Left Fragment!");
                }
                if(!leftDisplay){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.list_container, leftFragment)
                            .commit();
                    leftDisplay = true;
                }
                break;
            case R.id.load_right:
                if(rightFragment == null) {
                    rightFragment = ListFragment.getInstance("Right Fragment!");
                }
                if(!rightDisplay) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.detail_container, rightFragment)
                            .commit();
                    rightDisplay = true;
                }
                break;
            case R.id.remove_left:
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(leftFragment)
                        .commit();
                leftDisplay = false;
                break;
            case R.id.remove_right:
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(rightFragment)
                        .commit();
                rightDisplay = false;
                break;
        }
    }
}