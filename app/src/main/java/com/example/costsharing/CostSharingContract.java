package com.example.costsharing;

import android.provider.BaseColumns;

public final class CostSharingContract {
    private CostSharingContract() {}

    public static class ExpensesTable implements BaseColumns {
        public static final String TABLE_NAME = "Expenses";
        public static final String COLUMN_ExpName = "ExpName";
        public static final String COLUMN_ExpValue = "ExpValue";
        public static final String COLUMN_TripID = "TripID";
        public static final String COLUMN_PartID = "PartID";
    }

    public static class TripsTable implements BaseColumns {
        public static final String TABLE_NAME = "Trips";
        public static final String COLUMN_TripName = "TripName";
    }

    public static class ParticipantsTable implements BaseColumns {
        public static final String TABLE_NAME = "Participants";
        public static final String COLUMN_PartName = "PartName";
        public static final String COLUMN_TripID = "TripID";

    }
}
