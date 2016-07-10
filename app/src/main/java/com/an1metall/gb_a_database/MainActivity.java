package com.an1metall.gb_a_database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHandler db;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RVCursorAdapter rvCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);
        db.open();
        bindActivity();
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rvCursorAdapter = new RVCursorAdapter(this, db.getAllData());
        recyclerView.setAdapter(rvCursorAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void bindActivity(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
}
