package com.example.costsharing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripActivity extends AppCompatActivity {

    RecyclerView rvExpenses;
    private static final String ID_KEY = "idKey";

    ExpensesAdapter adapter = new ExpensesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long id = getIntent().getLongExtra(ID_KEY, -1);

        rvExpenses = findViewById(R.id.rv_expenses);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.setAdapter(adapter);
    }

    public void BackToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onAllExpensesGot(List<Expense> expenses){
        adapter.setExpenses(expenses);
    }

    public static void openActivity(long id, Context context){
        Intent i = new Intent(context, TripActivity.class);
        i.putExtra(ID_KEY, id);
        context.startActivity(i);
    }

    
}