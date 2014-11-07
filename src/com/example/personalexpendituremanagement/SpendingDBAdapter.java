package com.example.personalexpendituremanagement;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SpendingDBAdapter  {
	//Database path
	private static final String DATABASE_PATH ="/data/data/com.example.personalexpendituremanagement/databases/";
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
    private static final String KEY_IncomeName = "IncomeName";

    //Columns Name - tbl_Expense
    private static final String KEY_ExpenseID = "ExpenseID";
    private static final String KEY_tbExpense_ExpenseCID = "ExpenseCID";
    private static final String KEY_ExpenseDate = "ExpenseDate";
    private static final String KEY_ExpenseValue = "ExpenseValue";
    private static final String KEY_ExpenseDescriptions = "ExpenseDescriptions";
    private static final String KEY_ExpenseName = "ExpenseName";

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



    // get All Income Categories Records
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

    // get All Expense Categories Records
    public Cursor getAllExpenseCategories()
    {
        return db.query(KEY_TBNAME_tbl_ExpenseCategories, new String[] {
                        KEY_ExpenseCID,
                        KEY_ExpenseCName,
                },
                null,
                null,
                null,
                null,
                null);
    }

    // get All Income records
    public Cursor getAllIncome()
    {
        return db.query(KEY_TBNAME_tbl_Income, new String[] {
                        KEY_IncomeID,
                        KEY_tbIncome_IncomeCID,
                        KEY_IncomeDate,
                        KEY_IncomeValue,
                        KEY_IncomeDescriptions,
                        KEY_IncomeName
                },
                null,
                null,
                null,
                null,
                null);
    }
    //get all income with date range
    public Cursor getAllIncomeWithDateRange(String startDate,String endDate)
    {
    	String selection = KEY_IncomeDate +">="+ "'"+startDate+"'" +" and "+KEY_IncomeDate +"<="+ "'"+endDate+"'";
        return db.query(KEY_TBNAME_tbl_Income, new String[] {
                        KEY_IncomeID,
                        KEY_tbIncome_IncomeCID,
                        KEY_IncomeDate,
                        KEY_IncomeValue,
                        KEY_IncomeDescriptions,
                        KEY_IncomeName
                },
                selection,
                null,
                null,
                null,
                null);
        
    }

    // get All Expense records
    public Cursor getAllExpense()
    {
        return db.query(KEY_TBNAME_tbl_Expense, new String[] {
                        KEY_ExpenseID,
                        KEY_tbExpense_ExpenseCID,
                        KEY_ExpenseDate,
                        KEY_ExpenseValue,
                        KEY_ExpenseDescriptions,
                        KEY_ExpenseName
                },
                null,
                null,
                null,
                null,
                null);
    }
    //get all expense with date range
    public Cursor getAllExpenseWithDateRange(String startDate,String endDate)
    {
    	String selection = KEY_ExpenseDate + ">="+ "'"+startDate+"'" +" and " + KEY_ExpenseDate + "<="+ "'"+endDate+"'";
        return db.query(KEY_TBNAME_tbl_Expense, new String[] {
                        KEY_ExpenseID,
                        KEY_tbExpense_ExpenseCID,
                        KEY_ExpenseDate,
                        KEY_ExpenseValue,
                        KEY_ExpenseDescriptions,
                        KEY_ExpenseName
                },
                selection,
                null,
                null,
                null,
                null);
    }

    // Insert table Income Categories
    public long insertIncome_Categories( String IncomeCName)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IncomeCName, IncomeCName);
        return db.insert(KEY_TBNAME_tbl_IncomeCategories, null, initialValues);
    }

    // Insert table Expense Categories
    public long insertExpense_Categories( String IncomeCName)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ExpenseCName, IncomeCName);
        return db.insert(KEY_TBNAME_tbl_ExpenseCategories, null, initialValues);
    }

    // Insert table Expense
    public long insert_Income( long IncomeCID, String IncomeDate, long IncomeValue, String  ICDesct, String IncomeName)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_tbIncome_IncomeCID, IncomeCID);
        initialValues.put(KEY_IncomeDate, IncomeDate);
        initialValues.put(KEY_IncomeValue, IncomeValue);
        initialValues.put(KEY_IncomeDescriptions, ICDesct);
        initialValues.put(KEY_IncomeName, IncomeName);
        return db.insert(KEY_TBNAME_tbl_Income, null, initialValues);
    }

    // Insert table Expense
    public long insert_Expense( long ExpenseCID, String ExpenseDate, long ExpenseValue, String  ECDesct, String ExpenseName)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_tbExpense_ExpenseCID, ExpenseCID);
        initialValues.put(KEY_ExpenseDate, ExpenseDate);
        initialValues.put(KEY_ExpenseValue, ExpenseValue);
        initialValues.put(KEY_ExpenseDescriptions, ECDesct);
        initialValues.put(KEY_ExpenseName, ExpenseName);
        return db.insert(KEY_TBNAME_tbl_Expense, null, initialValues);
    }

    // Update an Expense record
    public boolean Update_Expense( long  ExpenseID, long ExpenseCID, String ExpenseDate, long ExpenseValue, String  ECDesct, String ExpenseName)
    {
        ContentValues Values = new ContentValues();
        Values.put(KEY_tbExpense_ExpenseCID, ExpenseCID);
        Values.put(KEY_ExpenseDate, ExpenseDate);
        Values.put(KEY_ExpenseValue, ExpenseValue);
        Values.put(KEY_ExpenseDescriptions, ECDesct);
        Values.put(KEY_ExpenseName, ExpenseName);
        return db.update(KEY_TBNAME_tbl_Expense, Values, KEY_ExpenseID + "=" + ExpenseID,null )>0;
    }

    // Update an Income record
    public boolean Update_Income( long  IncomeID, long IncomeCID, String IncomeDate, long IncomeValue, String  ICDesct, String IncomeName)
    {
        ContentValues Values = new ContentValues();
        Values.put(KEY_tbIncome_IncomeCID, IncomeCID);
        Values.put(KEY_IncomeDate, IncomeDate);
        Values.put(KEY_IncomeValue, IncomeValue);
        Values.put(KEY_IncomeDescriptions, ICDesct);
        Values.put(KEY_IncomeName, IncomeName);
        return db.update(KEY_TBNAME_tbl_Income, Values, KEY_IncomeID + "=" + IncomeID,null )>0;
    }

    // Update an Income Category record
    public boolean Update_IncomeCategory( long  IncomeCID, String IncomeCName)
    {
        ContentValues Values = new ContentValues();
        Values.put(KEY_IncomeCName, IncomeCName);

        return db.update(KEY_TBNAME_tbl_IncomeCategories, Values, KEY_IncomeCID + "=" + IncomeCID,null )>0;
    }

    // Update an Expense Category record
    public boolean Update_ExpenseCategory( long  ExpenseCID, String ExpenseCName)
    {
        ContentValues Values = new ContentValues();
        Values.put(KEY_ExpenseCName, ExpenseCName);

        return db.update(KEY_TBNAME_tbl_ExpenseCategories, Values, KEY_ExpenseCID + "=" + ExpenseCID,null )>0;
    }


    // Delete an Expense Category record
    public boolean Delete_ExpenseCategory( long  ExpenseCID)
    {
        return db.delete(KEY_TBNAME_tbl_ExpenseCategories, KEY_ExpenseCID + "=" + ExpenseCID,null )>0;
    }

    // Delete an Income Category record
    public boolean Delete_IncomeCategory( long IncomeCID)
    {
        return db.delete(KEY_TBNAME_tbl_IncomeCategories, KEY_IncomeCID + "=" + IncomeCID,null )>0;
    }

    // Delete an Income record
    public boolean Delete_Income( long IncomeID)
    {
        return db.delete(KEY_TBNAME_tbl_Income, KEY_IncomeID + "=" + IncomeID,null )>0;
    }

    // Delete an Expense record
    public boolean Delete_Expense( long ExpenseID)
    {
        return db.delete(KEY_TBNAME_tbl_Expense, KEY_ExpenseID + "=" + ExpenseID,null )>0;
    }

    //in case we would like to use a prepared database
    //copy database from assets folder to database folder
    public void copyDatabase() throws IOException{
    	//open local database as the input stream
    	InputStream myInputStream = context.getAssets().open(DATABASE_NAME);
    	//path to the newly created empty database
    	String outFileName = DATABASE_PATH + DATABASE_NAME;
    	//open the empty database as the output stream
    	OutputStream myOutputStream = new FileOutputStream(outFileName);
    	//transfer bytes from input file to output file
    	byte[] buffer = new byte[1024];
    	int length;
    	while((length=myInputStream.read(buffer))>0){
    		myOutputStream.write(buffer,0,length);
    	}
    	//close the stream
    	myOutputStream.flush();
    	myOutputStream.close();
    	myInputStream.close();
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
                + KEY_IncomeDescriptions + " TEXT,"
                + KEY_IncomeName + " TEXT"
                + ")";

        // tbl_Expense table create statement
        private static final String CREATE_TABLE_tbl_Expense = "CREATE TABLE "
                + KEY_TBNAME_tbl_Expense + "("
                + KEY_ExpenseID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_tbExpense_ExpenseCID + " INTEGER,"
                + KEY_ExpenseDate + " DATETIME,"
                + KEY_ExpenseValue + " INTEGER,"
                + KEY_ExpenseDescriptions + " TEXT,"
                + KEY_ExpenseName + " TEXT"
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


        // Get current Local Date
        private String getDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }
        @SuppressWarnings("null")
    	public boolean checkDatabase(){
        	
        	SQLiteDatabase checkDB =null;
        	try{
        		String myDBPath= DATABASE_PATH + DATABASE_NAME;
        		checkDB = SQLiteDatabase.openDatabase(myDBPath, null, SQLiteDatabase.OPEN_READONLY);
        	}catch(SQLiteException e){
        		
        	}
        	if(checkDB==null){
        		checkDB.close();
        	}
        	return checkDB !=null ? true:false;
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
        	/*boolean dbExist = checkDatabase();
        	if(dbExist){
        		
        	}
        	else{
        		
        	}*/
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
