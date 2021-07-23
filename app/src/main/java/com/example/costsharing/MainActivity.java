package com.example.costsharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;

    private TripAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CostSharingDbHelper dbHelper = new CostSharingDbHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        RecyclerView recyclerView = findViewById(R.id.rv_list_of_trips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TripAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

    }
    private void removeItem(long id) {
        mDatabase.delete(TripContract.TripsTable.TABLE_NAME,
                TripContract.TripsTable.COLUMN_TripID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    public void onNewTripClick(View v){
        Intent i = new Intent(this, NewTripActivity.class);
        startActivity(i);
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                TripContract.TripsTable.TABLE_NAME,
                null, null, null, null, null, TripContract.TripsTable.COLUMN_TripName + " ASC"
        );
    }


}