package com.example.costsharing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.costsharing.CostSharingContract.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CostSharingDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CostSharing.db";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private static CostSharingDbHelper instance;

    private CostSharingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    static public CostSharingDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CostSharingDbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_TRIPS_TABLE = "CREATE TABLE " +
                TripsTable.TABLE_NAME + " (" +
                TripsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TripsTable.COLUMN_TripName + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TRIPS_TABLE);

        final String SQL_CREATE_PARTICIPANTS_TABLE = "CREATE TABLE " +
                ParticipantsTable.TABLE_NAME + " (" +
                ParticipantsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ParticipantsTable.COLUMN_PartName + " TEXT," +
                ParticipantsTable.COLUMN_TripID + " INTEGER," +
                " FOREIGN KEY (" + ParticipantsTable.COLUMN_TripID + ") REFERENCES " + TripsTable.TABLE_NAME + "("+TripsTable._ID+"));";

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

    public void addTrip(Trip trip) {
        ContentValues cv = new ContentValues();
        cv.put(TripsTable.COLUMN_TripName, trip.getName());
        db.insert(TripsTable.TABLE_NAME, null, cv);
    }

    public int getTripIdByName(String tripName) {
        String query = "SELECT * FROM " + TripsTable.TABLE_NAME + " WHERE " + TripsTable.COLUMN_TripName + " =?";
        Cursor c = db.rawQuery(query, new String[] {tripName});
        int id = -1;
        try {
            if (c.moveToFirst()){
                id = c.getInt(c.getColumnIndexOrThrow("_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c!=null) c.close();
        }
        return id;
    }

    public void addExpense( Expense exp) {
        ContentValues cv = new ContentValues();
        cv.put(ExpensesTable.COLUMN_ExpName, exp.getName());
        cv.put(ExpensesTable.COLUMN_ExpValue, exp.getValue());
        cv.put(ExpensesTable.COLUMN_TripID, exp.getTripID());
        cv.put(ExpensesTable.COLUMN_PartID, exp.getPartID());
        db.insert(ExpensesTable.TABLE_NAME, null, cv);
    }

    public void addParticipant(Participant part) {
        ContentValues cv = new ContentValues();
        cv.put(ParticipantsTable.COLUMN_PartName, part.getName());
        cv.put(ParticipantsTable.COLUMN_TripID, part.getTripID());
        db.insert(ParticipantsTable.TABLE_NAME, null, cv);
    }

    public List<Participant> getParticipantsForTrip(long TripID) {
        ArrayList<Participant> partList = new ArrayList<>();
        db = getReadableDatabase();
        String query = "SELECT * FROM " + ParticipantsTable.TABLE_NAME + " WHERE " + ParticipantsTable.COLUMN_TripID + " =?";
        Cursor c = db.rawQuery(query, new String[] {Long.toString(TripID)});
        if (c.moveToFirst()) {
            do {
                Participant part = new Participant();
                part.setId(c.getInt(c.getColumnIndex(ParticipantsTable._ID)));
                part.setName(c.getString(c.getColumnIndex(ParticipantsTable.COLUMN_PartName)));
                part.setTripID(c.getInt(c.getColumnIndex(ParticipantsTable.COLUMN_TripID)));
                partList.add(part);
            } while (c.moveToNext());
        }
        c.close();
        return partList;
    }

    public String getPayerNameByID(long PartID) {
        db = getReadableDatabase();
        String query = "SELECT " + ParticipantsTable.COLUMN_PartName + " FROM " +  ParticipantsTable.TABLE_NAME + " WHERE " + ParticipantsTable._ID + " =? ";
        Cursor c = db.rawQuery(query, new String[] {Long.toString(PartID)});

        String payerName = "";
        try {
            if (c.moveToFirst()){
                payerName = c.getString(c.getColumnIndexOrThrow(ParticipantsTable.COLUMN_PartName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c!=null) c.close();
        }
        return payerName;
    }

    public long getPayerIDbyName(String partName) {
        db = getReadableDatabase();
        String query = "SELECT " + ParticipantsTable._ID + " FROM " + ParticipantsTable.TABLE_NAME +
                " WHERE "  + ParticipantsTable.COLUMN_PartName + " =?";
        Cursor c = db.rawQuery(query, new String[] {partName});

        long id = 0;
        try {
            if (c.moveToFirst()){
                id = c.getLong(c.getColumnIndexOrThrow(ParticipantsTable._ID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c!=null) c.close();
        }
        return id;
    }

    public String getTripNameByID(long tripID) {
        String query = "SELECT * FROM " + TripsTable.TABLE_NAME + " WHERE " + TripsTable._ID + " =?";
        Cursor c = db.rawQuery(query, new String[] {Long.toString(tripID)});
        String tripName = "";
        try {
            if (c.moveToFirst()){
                tripName = c.getString(c.getColumnIndexOrThrow(TripsTable.COLUMN_TripName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c!=null) c.close();
        }
        return tripName;
    }
    public Cursor getExpensesForTripCursor(long tripID) {
        String query = "SELECT * FROM " + CostSharingContract.ExpensesTable.TABLE_NAME +
                " WHERE " + CostSharingContract.ExpensesTable.COLUMN_TripID + " =?";

        Cursor c  = db.rawQuery(query, new String[] { Long.toString(tripID) });
        return c;
    }

    public String countSummary(long tripID) {
        String summaryText ="";
        String partName;
        Double partPaid;
        Double total = 0.0;
        Map<String, Double> paidByParticipant = new TreeMap<String, Double>();

        List<Participant> partList = getParticipantsForTrip(tripID);
        for ( Participant part : partList) {
            long partID = part.getId();
            partName = part.getName();
            String query = "SELECT SUM(" + ExpensesTable.COLUMN_ExpValue + ") FROM " + ExpensesTable.TABLE_NAME + " WHERE " + ExpensesTable.COLUMN_TripID + " =? AND " + ExpensesTable.COLUMN_PartID + " =?";
            Cursor c = db.rawQuery(query, new String[] {Long.toString(tripID), Long.toString(partID)});
            try {
                if (c.moveToFirst()){
                    partPaid = c.getDouble(0);
                    paidByParticipant.put(partName, partPaid);
                    total+=partPaid;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (c!=null) c.close();
            }
        }
        summaryText+= "Total is " + total + "\r\n\n";

        int num = paidByParticipant.size();
        Double equalShare = total / num;

        for (Map.Entry m : paidByParticipant.entrySet()) {
            partPaid = (Double) m.getValue();
            Double result = partPaid - equalShare;
            if (result>0) {
                summaryText+=(String) m.getKey() + " is owed " + result + "\r\n";
            } else if (result<0) {
                summaryText+=(String) m.getKey() + " owes " + -result + "\r\n";
            } else {
                summaryText+=(String) m.getKey() + " doesn't owe and is not owed anything\n";
            }
        }

        return summaryText.substring(0, summaryText.length()-2);
    }
}
