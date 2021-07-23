package com.example.costsharing;

public final class TripContract {

    private TripContract() {}

    public static class TripsTable {
        public static final String TABLE_NAME = "Trips";
        public static final String COLUMN_TripID = "TripID";
        public static final String COLUMN_TripName = "TripName";
    }
}
