package com.example.costsharing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class ParticipantView extends View {

    OnNameTypedListener listener;

    EditText nameET;

    public ParticipantView(Context context) {
        super(context);
    }

    public ParticipantView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        listener.onSomethingHappened();
    }

    public ParticipantView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ParticipantView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

   public void  inflateView(Participant participant){

   }

   public void setNameTypedListener(OnNameTypedListener listener){
        this.listener = listener;
   }

}
