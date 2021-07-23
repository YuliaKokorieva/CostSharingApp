package com.example.costsharing;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private EditText part2Name;
    private EditText part3Name;
    private EditText part4Name;
    private EditText part5Name;


//    private List<Participant> participants = new ArrayList();
//
//    LinearLayout participantsFrame;
//
//    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);


        CostSharingDbHelper dbHelper = new CostSharingDbHelper(this);
        trDatabase = dbHelper.getWritableDatabase();

        tripName = findViewById(R.id.et_name);
        part1Name = findViewById(R.id.et_part1);
        part2Name = findViewById(R.id.et_part2);
        part3Name = findViewById(R.id.et_part3);
        part4Name = findViewById(R.id.et_part4);
        part5Name = findViewById(R.id.et_part5);
        Button bSave = findViewById(R.id.b_save);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        if (tripName.getText().toString().trim().length() ==0 )  {
//            || part1Name.getText().toString().trim().length() ==0
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Enter name of the trip (and at least one participant)!",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        ContentValues cvTrip = new ContentValues();

        String name = tripName.getText().toString();
        cvTrip.put(TripContract.TripsTable.COLUMN_TripName, name);
        trDatabase.insert(TripContract.TripsTable.TABLE_NAME, null, cvTrip);


        String participant1 = part1Name.getText().toString();

        if (part2Name.getText().toString().trim().length() !=0) {
            String participant2 = part2Name.getText().toString();
        }
        if (part3Name.getText().toString().trim().length() !=0) {
            String participant3 = part3Name.getText().toString();
        }
        if (part4Name.getText().toString().trim().length() !=0) {
            String participant4 = part4Name.getText().toString();
        }
        if (part5Name.getText().toString().trim().length() !=0) {
            String participant5 = part5Name.getText().toString();
        }





    }




    public void BackToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}



//        participantsFrame = findViewById(R.id.participants_frame);


//    public void inflateList(){
//
//    }

//    public void onAddNewParticipant(View v){
//        Participant p = new Participant();
//        participants.add(p);
//        ParticipantView view = new ParticipantView(this);
//        view.setNameTypedListener(new OnNameTypedListener() {
//            @Override
//            public void onNameTyped(String name) {
//                String id = p.getId();
//                int i = -1;
//                for (int j = 0; j < participants.size(); j++){
//                    if (participants.get(j).getId().equals(id))
//                        i = j;
//                }
//                if (i!=-1){
//                   Participant participantToEdit = participants.get(i);
//                   participantToEdit.setName(name);
//                   participants.set(i, participantToEdit);
//                }
//            }
//        });
//        view.inflateView(p, ++count);
//        participantsFrame.addView(view);
//    }

