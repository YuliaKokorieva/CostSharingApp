package com.example.costsharing;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NewTripActivity extends AppCompatActivity {
    private SQLiteDatabase trDatabase;
    private EditText tripName;
    private EditText part1Name;
    private Button bSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        bSave = findViewById(R.id.b_save);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        tripName = findViewById(R.id.et_name);
        part1Name = findViewById(R.id.et_part1);

        int tripID;

        CostSharingDbHelper dbHelper = CostSharingDbHelper.getInstance(this);

        if (tripName.getText().toString().trim().length() ==0 || part1Name.getText().toString().trim().length() ==0 )  {

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Enter name of the trip and at least one participant!",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        String sTripName = tripName.getText().toString();

        Trip newTrip = new Trip(sTripName);
        dbHelper.addTrip(newTrip);
        tripID = dbHelper.getTripIdByName(sTripName);


        for (int i=1; i<=5; i++) {
            String partID = "et_part" + i;
            int resID = getResources().getIdentifier(partID, "id", getPackageName());

            EditText etPart = findViewById(resID);

            if (etPart.getText().toString().trim().length() !=0) {
                String newPartName = etPart.getText().toString();

                Participant newPart = new Participant(newPartName, tripID);
                dbHelper.addParticipant(newPart);

            }
        }

        NewExpenseActivity.openActivity(tripID, NewTripActivity.this);

    }

    public void BackToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    public void back(View v) {
        onBackPressed();
    }
}
