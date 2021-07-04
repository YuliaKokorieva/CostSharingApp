package com.example.costsharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NewTrip extends AppCompatActivity {

    private List<Participant> participants = new ArrayList();

    LinearLayout participantsFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        participantsFrame = findViewById(R.id.participants_frame);
    }

    public void inflateList(){

    }

    public void onAddNewParticipant(){
        Participant p = new Participant();
        participants.add(p);
        ParticipantView view = new ParticipantView(this);
        view.setNameTypedListener(new OnNameTypedListener() {
            @Override
            public void onNameTyped(String name) {
                String id = p.getId();
                int i = -1;
                for (int j = 0; j < participants.size(); j++){
                    if (participants.get(j).getId().equals(id))
                        i = j;
                }
                if (i!=-1){
                   Participant participantToEdit = participants.get(i);
                   participantToEdit.setName(name);
                   participants.set(i, participantToEdit);
                }
            }
        });
        view.inflateView(p);
        participantsFrame.addView(view);
    }

    public void onSaveClick(){
      //ToDo:  participants.saveToDb
    }

    public void BackToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}