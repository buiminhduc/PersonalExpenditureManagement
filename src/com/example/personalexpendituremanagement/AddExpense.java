package com.example.personalexpendituremanagement;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class AddExpense extends FragmentActivity implements OnItemSelectedListener {
	private DateFormat dateFormat = DateFormat.getInstance();
	private Calendar calendar = Calendar.getInstance();
	private TextView txtExpenseDate;
	private ImageButton btnPickExpenseDate;
	private Spinner spExpenseCategory;
	private EditText txtExpenseAmount,txtExpenseName, txtNoteExpense;
	private Button btnSaveExpense;
	SpendingDBAdapter DBAdapter;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.add_expense);
		
		//initialize DB adapter
		DBAdapter = new SpendingDBAdapter(this);
		
		txtExpenseDate = (TextView)findViewById(R.id.txtExpenseDate);
		txtExpenseAmount=(EditText)findViewById(R.id.txtExpenseAmount);
		txtExpenseName=(EditText)findViewById(R.id.txtExpenseName);
		txtNoteExpense=(EditText)findViewById(R.id.txtNoteExpense);
		
		spExpenseCategory = (Spinner)findViewById(R.id.spExpenseCategory);
		spExpenseCategory.setOnItemSelectedListener(this);
		
		txtExpenseDate = (TextView)findViewById(R.id.txtExpenseDate);
		txtExpenseDate.setOnTouchListener(new OnTouchListener() {
			
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				setDate();
				return false;
			}
		});
		
		btnPickExpenseDate =(ImageButton)findViewById(R.id.btnPickExpenseDate);
		btnPickExpenseDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDate();
			}
		});
		//save expense
		btnSaveExpense = (Button)findViewById(R.id.btnSaveExpense);
		btnSaveExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				saveExpense();
			}
		});
		
		//load spinner data
		loadSpinnerData();
		//update date
		updateDate();
	}
	public void saveExpense(){
		if(txtExpenseAmount.getText().toString().trim().length() != 0){
			final int expenseAmount = Integer.parseInt(txtExpenseAmount.getText().toString());
			final String expenseName=txtExpenseName.getText().toString().trim();
			final String expenseNote=txtNoteExpense.getText().toString().trim();
			final String expenseDate=txtExpenseDate.getText().toString().trim();
			final int expenseCategoryId = ((SpinnerObject)spExpenseCategory.getSelectedItem()).getId();
			
			DBAdapter.open();
			DBAdapter.insert_Expense(expenseCategoryId, expenseDate, expenseAmount, expenseNote, expenseName);
			DBAdapter.close();
			this.finish();
		}else{
			new AlertDialog.Builder(this)
		    .setTitle(R.string.error)
		    .setMessage(R.string.invalid_amount)
		    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with save
		        }
		     })
		    /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     }
		    )*/
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
		
		
	}
	DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			calendar.set(Calendar.YEAR,year);
			calendar.set(Calendar.MONTH,monthOfYear);
			calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
			updateDate();
		}
	};
	public void setDate(){
		new DatePickerDialog(this, dateDialog, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
	}
	public void updateDate(){
		txtExpenseDate.setText(dateFormat.format(calendar.getTime()));
	}
	 /**
     * Getting all income categories
     * returns list of income categories
     * */
    public List<SpinnerObject> getArrayIncomeCategories(){
    	
    	DBAdapter.open();
        List<SpinnerObject> expenseCategories = new ArrayList<SpinnerObject>();
        Cursor cursor = DBAdapter.getAllExpenseCategories();
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	expenseCategories.add(new SpinnerObject(cursor.getInt(0),cursor.getString(1)));
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        DBAdapter.close();
         
        // returning income categories
        return expenseCategories;
    }
	/**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // Spinner Drop down elements
        List<SpinnerObject> expenseCategories = getArrayIncomeCategories();
 
        // Creating adapter for spinner
        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,android.R.layout.simple_spinner_item, expenseCategories);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spExpenseCategory.setAdapter(dataAdapter);
    }
 
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        // On selecting a spinner item
        //String label = parent.getItemAtPosition(position).toString();
    	//SpinnerObject so= (SpinnerObject)parent.getItemAtPosition(position);
    	
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "You selected: " + so.getId(),Toast.LENGTH_LONG).show();
 
    }
 
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
 
    }
    
}
