package com.example.costsharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewExpenseActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private static final String ID_KEY = "idKey";
    private String payerName;
    long id;
    CostSharingDbHelper dbHelper = CostSharingDbHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        id = getIntent().getLongExtra(ID_KEY, -1);

        List<Participant> partList = dbHelper.getParticipantsForTrip(id);
        List<String> sPartList = new ArrayList<String>();
        for (Participant part : partList) {
            sPartList.add(part.getName());
        }

        Spinner spinnerPayer = findViewById(R.id.spinner_paidby);

        spinnerPayer.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayAdapter<String> dataAdapterPart = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sPartList);
        dataAdapterPart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayer.setAdapter(dataAdapterPart);

        Button bSaveExp = findViewById(R.id.b_save_exp);
        bSaveExp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveExpense();
                TripActivity.openActivity(id, NewExpenseActivity.this);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        payerName = parent.getItemAtPosition(position).toString();

        Toast toast = Toast.makeText(getApplicationContext(),
                "selected: " + payerName,
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

    public void saveExpense() {
        EditText etExpName = findViewById(R.id.et_name);
        EditText etExpValue = findViewById(R.id.et_value);

        if (etExpName.getText().toString().trim().length() ==0 || etExpValue.getText().toString().trim().length() ==0 )  {

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Enter name and value of the expense!",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String expName = etExpName.getText().toString();
        double expValue = Double.parseDouble(etExpValue.getText().toString());

        Expense exp = new Expense();
        exp.setName(expName);
        exp.setValue(expValue);
        exp.setTripID(id);
        exp.setPartID(dbHelper.getPayerIDbyName(payerName));
        dbHelper.addExpense(exp);
    }
    public void back(View v) {
        onBackPressed();
    }
}