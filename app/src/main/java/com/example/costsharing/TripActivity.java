package com.example.costsharing;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripActivity extends AppCompatActivity {

    private ExpensesAdapter mAdapter;
    RecyclerView rvExpenses;
    private static final String ID_KEY = "idKey";
    private TextView tripHeader;
    private Button bNewExp;
    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_view);
        long id = getIntent().getLongExtra(ID_KEY, -1);

        CostSharingDbHelper dbHelper = CostSharingDbHelper.getInstance(this);

        tripHeader = findViewById(R.id.tv_trip_header);
        tripHeader.setText("Trip: " + dbHelper.getTripNameByID(id));

        rvExpenses = findViewById(R.id.rv_expenses);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExpensesAdapter(this, dbHelper.getExpensesForTripCursor(id), dbHelper);
        rvExpenses.setAdapter(mAdapter);

        bNewExp = findViewById(R.id.b_new_expense);
        bNewExp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NewExpenseActivity.openActivity(id, TripActivity.this);
            }
        });

        tvSummary = findViewById(R.id.tv_trip_summary);
        tvSummary.setText(dbHelper.countSummary(id));
    }

    public void BackToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public static void openActivity(long id, Context context){
        Intent i = new Intent(context, TripActivity.class);
        i.putExtra(ID_KEY, id);
        context.startActivity(i);
    }

    public void back(View v) {
        onBackPressed();
    }
}