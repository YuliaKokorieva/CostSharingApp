package com.example.costsharing;

import android.provider.BaseColumns;

public class ExpenseContract {
    private ExpenseContract() {};

    public static class ExpensesTable {
        public static final String TABLE_NAME = "Expenses";
        public static final String COLUMN_ExpID = "ExpID";
        public static final String COLUMN_ExpName = "ExpName";
        public static final String COLUMN_ExpValue = "ExpValue";
        public static final String COLUMN_TripID = "TripID";
        public static final String COLUMN_PartID = "PartID";
    }
}


