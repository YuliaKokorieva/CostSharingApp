package com.example.costsharing;

import android.provider.BaseColumns;

public class ParticipantContract {

    private ParticipantContract() {};

    public static class ParticipantsTable {
        public static final String TABLE_NAME = "Participants";
        public static final String COLUMN_PartID = "ParticipantID";
        public static final String COLUMN_PartName = "PartName";
        public static final String COLUMN_TripID = "TripID";

    }
}

