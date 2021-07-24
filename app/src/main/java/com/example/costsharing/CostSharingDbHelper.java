package com.example.costsharing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.costsharing.CostSharingContract.*;
import java.util.ArrayList;
import java.util.List;

public class CostSharingDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CostSharing.db";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public CostSharingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_TRIPS_TABLE = "CREATE TABLE " +
                TripsTable.TABLE_NAME + " (" +
                TripsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TripsTable.COLUMN_TripName + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_TRIPS_TABLE);
//        fillTripsTable();


        final String SQL_CREATE_PARTICIPANTS_TABLE = "CREATE TABLE " +
                ParticipantsTable.TABLE_NAME + " (" +
                ParticipantsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ParticipantsTable.COLUMN_PartName + " TEXT," +
                ParticipantsTable.COLUMN_TripID + " INTEGER," +
                " FOREIGN KEY ("+ParticipantsTable.COLUMN_TripID+") REFERENCES "+TripsTable.TABLE_NAME+"("+TripsTable._ID+"));";

        db.execSQL(SQL_CREATE_PARTICIPANTS_TABLE);


        final String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " +
                ExpensesTable.TABLE_NAME + " (" +
                ExpensesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ExpensesTable.COLUMN_ExpName + " TEXT," +
                ExpensesTable.COLUMN_ExpValue + " REAL," +
                ExpensesTable.COLUMN_TripID + " INTEGER," +
                ExpensesTable.COLUMN_PartID + " INTEGER," +
                " FOREIGN KEY ("+ExpensesTable.COLUMN_TripID+") REFERENCES "+TripsTable.TABLE_NAME+"("+TripsTable._ID+")," +
                " FOREIGN KEY ("+ExpensesTable.COLUMN_PartID+") REFERENCES "+ParticipantsTable.TABLE_NAME+"("+ParticipantsTable._ID+"));";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TripsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ParticipantsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExpensesTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
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

    private void addExpense( Expense exp) {
        ContentValues cv = new ContentValues();
        cv.put(ExpensesTable.COLUMN_ExpName, exp.getName());
        cv.put(ExpensesTable.COLUMN_ExpValue, exp.getValue());
        cv.put(ExpensesTable.COLUMN_TripID, exp.getTripID());
        cv.put(ExpensesTable.COLUMN_PartID, exp.getPartID());
        db.insert(ExpensesTable.TABLE_NAME, null, cv);
    }

    private void addParticipant(Participant part) {
        ContentValues cv = new ContentValues();
        cv.put(ParticipantsTable.COLUMN_PartName, part.getName());
        cv.put(ParticipantsTable.COLUMN_TripID, part.getTripID());
        db.insert(ParticipantsTable.TABLE_NAME, null, cv);
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

    public List<Expense> getExpenses(int TripID) {
        ArrayList<Expense> expList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c  = db.rawQuery("SELECT * FROM " + ExpensesTable.TABLE_NAME +
                " WHERE " + ExpensesTable.COLUMN_TripID + " =?", SELECTIONARGS);

        if (c.moveToFirst()) {
            do {
                Expense exp = new Expense();
                exp.setId(c.getInt(c.getColumnIndex(ExpensesTable._ID)));
                exp.setName(c.getString(c.getColumnIndex(ExpensesTable.COLUMN_ExpName)));
                exp.setValue(c.getDouble(c.getColumnIndex(ExpensesTable.COLUMN_ExpValue)));
                exp.setPartID(c.getInt(c.getColumnIndex(ExpensesTable.COLUMN_PartID)));
                exp.setTripID(c.getInt(c.getColumnIndex(ExpensesTable.COLUMN_TripID)));
                expList.add(exp);
            } while (c.moveToNext());
        }
        c.close();
        return expList;
    }

}