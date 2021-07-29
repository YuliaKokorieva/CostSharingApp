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

        CostSharingDbHelper dbHelper = CostSharingDbHelper.getInstance(this);
        mDatabase = dbHelper.getReadableDatabase();

        RecyclerView recyclerView = findViewById(R.id.rv_list_of_trips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TripAdapter(this, getAllItems(), new TripAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {

            }
        });
        recyclerView.setAdapter(mAdapter);

    }
    private void removeItem(long id) {
        mDatabase.delete(CostSharingContract.TripsTable.TABLE_NAME,
                CostSharingContract.TripsTable._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    public void onNewTripClick(View v){
        Intent i = new Intent(this, NewTripActivity.class);
        startActivity(i);
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                CostSharingContract.TripsTable.TABLE_NAME,
                null, null, null, null, null, CostSharingContract.TripsTable.COLUMN_TripName + " ASC"
        );
    }


}