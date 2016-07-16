package com.an1metall.gb_a_database;

import android.databinding.ObservableList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DBHandler db;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RVAdapter rvAdapter;
    private TextView totalCostText;
    private ObservableList<Purchase> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);
        db.open();

        bindActivity();
        setSupportActionBar(toolbar);

        items = db.getAllData();
        setTotalCostText();
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rvAdapter = new RVAdapter(items);
        recyclerView.setAdapter(rvAdapter);
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
