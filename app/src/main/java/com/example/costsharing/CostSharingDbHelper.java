package com.example.costsharing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.costsharing.TripContract.*;
import com.example.costsharing.ParticipantContract.*;
import com.example.costsharing.ExpenseContract.*;

import java.util.ArrayList;
import java.util.List;

public class CostSharingDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CostSharing.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public CostSharingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_TRIPS_TABLE = "CREATE TABLE " +
                TripsTable.TABLE_NAME + " (" +
                TripsTable.COLUMN_TripID + " INTEGER PRIMARY KEY, " +
                TripsTable.COLUMN_TripName + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_TRIPS_TABLE);
//        fillTripsTable();


        final String SQL_CREATE_PARTICIPANTS_TABLE = "CREATE TABLE " +
                ParticipantsTable.TABLE_NAME + " (" +
                ParticipantsTable.COLUMN_PartID + " INTEGER PRIMARY KEY, " +
                ParticipantsTable.COLUMN_PartName + " TEXT," +
                ParticipantsTable.COLUMN_TripID + " INTEGER," +
                " FOREIGN KEY ("+ParticipantsTable.COLUMN_TripID+") REFERENCES "+TripsTable.TABLE_NAME+"("+TripsTable.COLUMN_TripID+"));";

        db.execSQL(SQL_CREATE_PARTICIPANTS_TABLE);


        final String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " +
                ExpensesTable.TABLE_NAME + " (" +
                ExpensesTable.COLUMN_ExpID + " INTEGER PRIMARY KEY, " +
                ExpensesTable.COLUMN_ExpName + " TEXT," +
                ExpensesTable.COLUMN_ExpValue + " REAL," +
                ExpensesTable.COLUMN_TripID + " INTEGER," +
                ExpensesTable.COLUMN_PartID + " INTEGER," +
                " FOREIGN KEY ("+ExpensesTable.COLUMN_TripID+") REFERENCES "+TripsTable.TABLE_NAME+"("+TripsTable.COLUMN_TripID+")," +
                " FOREIGN KEY ("+ExpensesTable.COLUMN_PartID+") REFERENCES "+ParticipantsTable.TABLE_NAME+"("+ParticipantsTable.COLUMN_PartID+"));";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TripsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillTripsTable() {
        Trip t1 = new Trip("Croatia 2020");
        addTrip(t1);
    }

    private void addTrip(Trip trip) {
        ContentValues cv = new ContentValues();
        cv.put(TripsTable.COLUMN_TripName, trip.getName());
        db.insert(TripsTable.TABLE_NAME, null, cv);
    }

    public List<Trip> getAllTrips() {
        List<Trip> tripsList = new ArrayList<>();
        db=getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TripsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Trip trip = new Trip();
                trip.setName(c.getString(c.getColumnIndex(TripsTable.COLUMN_TripName)));
                tripsList.add(trip);
            } while (c.moveToNext());
        }
        c.close();
        return tripsList;

    }
}
