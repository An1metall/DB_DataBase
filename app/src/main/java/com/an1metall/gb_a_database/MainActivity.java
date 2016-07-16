package com.an1metall.gb_a_database;

import android.databinding.ObservableList;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import co.dift.ui.SwipeToAction;

public class MainActivity extends AppCompatActivity {

    private DBHandler db;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RVAdapter rvAdapter;
    private TextView totalCostText;
    private ObservableList<Purchase> items;
    private SwipeToAction swipeToAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this);
        db.open();
        bindActivity();
        setSupportActionBar(toolbar);
        initItems();
        setTotalCostText();
        setupRecyclerView();
        initSwipeToAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void bindActivity() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        totalCostText = (TextView) findViewById(R.id.activity_main_total_cost_text);
    }

    private void setTotalCostText(){
        int result = 0;
        for (Purchase item : items) {
            result = result + item.get_cost();
        }
        totalCostText.setText(String.valueOf(result));
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new RVAdapter(items);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initItems(){
        items = db.getAllData();
        items.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Purchase>>() {
            @Override
            public void onChanged(ObservableList<Purchase> purchases) {
                setTotalCostText();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Purchase> purchases, int i, int i1) {
                rvAdapter.notifyItemRangeChanged(i, i1);
                setTotalCostText();
            }

            @Override
            public void onItemRangeInserted(ObservableList<Purchase> purchases, int i, int i1) {
                rvAdapter.notifyItemRangeInserted(i, i1);
                setTotalCostText();
            }

            @Override
            public void onItemRangeMoved(ObservableList<Purchase> purchases, int i, int i1, int i2) {
                setTotalCostText();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Purchase> purchases, int i, int i1) {
                rvAdapter.notifyItemRangeRemoved(i, i1);
                setTotalCostText();
            }
        });
    }

    private void initSwipeToAction(){
        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Purchase>() {
            @Override
            public boolean swipeLeft(Purchase itemData) {
                if (db.deleteEntry(itemData.get_id())) items.remove(itemData);
                return false;
            }

            @Override
            public boolean swipeRight(Purchase itemData) {
                return false;
            }

            @Override
            public void onClick(Purchase itemData) {
            }

            @Override
            public void onLongClick(Purchase itemData) {
            }
        });
    }

    public void onClickMenuItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_action_add:
                for (int i = 0; i < 10; i++) {
                    db.insertEntry("Test " + i, 1000 + i, 0);
                    items.add(db.getInsertedData());
                }
                break;
            case R.id.menu_main_action_wipe:
                items.clear();
                items.addAll(db.wipeAllData());
                break;
            case R.id.menu_main_action_update_data:
                rvAdapter.notifyDataSetChanged();
                break;
        }
    }
}
