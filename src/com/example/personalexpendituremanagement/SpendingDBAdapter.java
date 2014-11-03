package com.example.personalexpendituremanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpendingDBAdapter {
	//Database Name
    private static final String DATABASE_NAME = "SpendingDB";

    //Table Name
    private static final String KEY_TBNAME_tbl_Income = "tbl_Income";
    private static final String KEY_TBNAME_tbl_Expense = "tbl_Expense";
    private static final String KEY_TBNAME_tbl_IncomeCategories = "tbl_IncomeCategories";
    private static final String KEY_TBNAME_tbl_ExpenseCategories = "tbl_ExpenseCategories";

    //Columns Name - tbl_Income
    private static final String KEY_IncomeID = "IncomeID";
    private static final String KEY_tbIncome_IncomeCID = "IncomeCID";
    private static final String KEY_IncomeDate = "IncomeDate";
    private static final String KEY_IncomeValue = "IncomeValue";
    private static final String KEY_IncomeDescriptions = "IncomeDescriptions";

    //Columns Name - tbl_Expense
    private static final String KEY_ExpenseID = "ExpenseID";
    private static final String KEY_tbExpense_ExpenseCID = "ExpenseCID";
    private static final String KEY_ExpenseDate = "ExpenseDate";
    private static final String KEY_ExpenseValue = "ExpenseValue";
    private static final String KEY_ExpenseDescriptions = "ExpenseDescriptions";

    //Columns Name - tbl_IncomeCategories
    private static final String KEY_IncomeCID = "IncomeCID";
    private static final String KEY_IncomeCName = "IncomeCName";

    //Columns Name - tbl_ExpenseCategories
    private static final String KEY_ExpenseCID = "ExpenseCID";
    private static final String KEY_ExpenseCName = "ExpenseCName";

    private static final String TAG = "_DBAdapter";


    private static final int DATABASE_VERSION = 1;


    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper DBHelper;

    public  SpendingDBAdapter(Context ctx){
        this.context=ctx;
        DBHelper=new DatabaseHelper(context);
    }

    //---opens the database---
    public SpendingDBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    // Insert table Income Categories
    public long insertIncome_Categories( String IncomeCName)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IncomeCName, IncomeCName);
        return db.insert(KEY_TBNAME_tbl_IncomeCategories, null, initialValues);
    }

    // get All Categories
    public Cursor getAllIncomeCategories()
    {
        return db.query(KEY_TBNAME_tbl_IncomeCategories, new String[] {
                        KEY_IncomeCID,
                        KEY_IncomeCName,
                        },
                null,
                null,
                null,
                null,
                null);
    }


    // Database Helper Class
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        // tbl_Income table create statement
        private static final String CREATE_TABLE_tbl_Income = "CREATE TABLE "
                + KEY_TBNAME_tbl_Income + "("
                + KEY_IncomeID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_tbIncome_IncomeCID + " INTEGER,"
                + KEY_IncomeDate + " DATETIME,"
                + KEY_IncomeValue + " INTEGER,"
                + KEY_IncomeDescriptions + " TEXT"
                + ")";

        // tbl_Expense table create statement
        private static final String CREATE_TABLE_tbl_Expense = "CREATE TABLE "
                + KEY_TBNAME_tbl_Expense + "("
                + KEY_ExpenseID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_tbExpense_ExpenseCID + " INTEGER,"
                + KEY_ExpenseDate + " DATETIME,"
                + KEY_ExpenseValue + " INTEGER,"
                + KEY_ExpenseDescriptions + " TEXT"
                + ")";

        // tbl_IncomeCategories table create statement
        private static final String CREATE_TABLE_tbl_IncomeCategories = "CREATE TABLE "
                + KEY_TBNAME_tbl_IncomeCategories + "("
                + KEY_IncomeCID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_IncomeCName + " TEXT"
                + ")";

        // tbl_IncomeCategories table create statement
        private static final String CREATE_TABLE_tbl_ExpenseCategories = "CREATE TABLE "
                + KEY_TBNAME_tbl_ExpenseCategories + "("
                + KEY_ExpenseCID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_ExpenseCName + " TEXT"
                + ")";



        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // creating required tables
            db.execSQL(CREATE_TABLE_tbl_Income);
            db.execSQL(CREATE_TABLE_tbl_Expense);
            db.execSQL(CREATE_TABLE_tbl_IncomeCategories);
            db.execSQL(CREATE_TABLE_tbl_ExpenseCategories);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // on upgrade drop older tables
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_tbl_Income);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_tbl_Expense);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_tbl_IncomeCategories);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_tbl_ExpenseCategories);

            // create new tables
            onCreate(db);
        }
    }
}
