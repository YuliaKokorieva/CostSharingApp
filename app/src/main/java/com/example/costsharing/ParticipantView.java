package com.example.costsharing;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ParticipantView extends FrameLayout {

    OnNameTypedListener listener;

    TextView participantNumber;
    EditText nameET;

    public ParticipantView(Context context) {
        super(context);
        init();
    }

    public ParticipantView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParticipantView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ParticipantView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
       View view =  inflate(getContext(), R.layout.list_item_participant, this);
       nameET = view.findViewById(R.id.name);
            nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onNameTyped(s.toString());
            }
        });
    }

   public void  inflateView(Participant participant, int number){
        nameET.setText(participant.getName());
        participantNumber.setText("Participant " + number + ":");
   }

   public void setNameTypedListener(OnNameTypedListener listener){
        this.listener = listener;
   }

}
