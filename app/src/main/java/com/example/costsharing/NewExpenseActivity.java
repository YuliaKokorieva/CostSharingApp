package com.example.costsharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewExpenseActivity extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String ID_KEY = "idKey";
    private TextView tripInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        long id = getIntent().getLongExtra(ID_KEY, -1);

        CostSharingDbHelper dbHelper = CostSharingDbHelper.getInstance(this);
        tripInfo = findViewById(R.id.tv_TripInfo);
        String textTrip = "Trip name: " + dbHelper.getTripNameByID(id) + "\r\nParticipants names: ";
        List<Participant> partList = dbHelper.getParticipantsForTrip(id);
        List<String> sPartList = new ArrayList<String>();
        for (Participant part : partList) {
            sPartList.add(part.getName());
            textTrip += part.getName() + ", ";
        }
        tripInfo.setText(textTrip);

//        final Spinner spinner = (Spinner) findViewById(R.id.spinner_currency);
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
//
//        List<String> currencies = new ArrayList<String>();
//        currencies.add("EUR");
//        currencies.add("USD");
//        currencies.add("UAH");
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);


        final Spinner spinnerPayer = (Spinner) findViewById(R.id.spinner_paidby);
        spinnerPayer.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayAdapter<String> dataAdapterPart = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sPartList);
        dataAdapterPart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayer.setAdapter(dataAdapterPart);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast toast = Toast.makeText(getApplicationContext(),
                "selected: " + item,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Select payer!",
                Toast.LENGTH_SHORT);
        toast.show();

    }

    public void BackToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public static void openActivity(long id, Context context){
        Intent i = new Intent(context, NewExpenseActivity.class);
        i.putExtra(ID_KEY, id);
        context.startActivity(i);
    }

    @Override
    public void onClick(View v) {

    }


}