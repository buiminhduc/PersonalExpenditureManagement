package com.example.personalexpendituremanagement;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;


public class AddIncome extends FragmentActivity implements OnItemSelectedListener {
	private DateFormat dateFormat = DateFormat.getInstance();
	private Calendar calendar = Calendar.getInstance();
	private TextView txtIncomeDate;
	private ImageButton btnPickIncomeDate;
	private Button btnSaveIncome;
	private EditText txtIncomeAmount,txtIncomeName,txtNoteIncome;
	private Spinner spIncomeCategory;
	SpendingDBAdapter DBAdapter;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_income);
		
		txtIncomeAmount = (EditText)findViewById(R.id.txtIncomeAmount);
		txtIncomeName = (EditText)findViewById(R.id.txtIncomeName);
		txtNoteIncome = (EditText)findViewById(R.id.txtNoteIncome);
		spIncomeCategory =(Spinner)findViewById(R.id.spIncomeCategory);
		//initialize DB adapter
		DBAdapter = new SpendingDBAdapter(this);
		spIncomeCategory.setOnItemSelectedListener(this);
		//load spinner data
		loadSpinnerData();
		
		
		txtIncomeDate = (TextView)findViewById(R.id.txtIncomeDate);
		txtIncomeDate.setOnTouchListener(new OnTouchListener() {
			
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				setDate();
				return false;
			}
		});
		btnPickIncomeDate =(ImageButton)findViewById(R.id.btnPickIncomeDate);
		btnPickIncomeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDate();
			}
		});
		//save income
		btnSaveIncome = (Button)findViewById(R.id.btnSaveIncome);
		btnSaveIncome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				saveIncome();
			}
		});
		updateDate();
	}
	public void saveIncome(){
		if(txtIncomeAmount.getText().toString().trim().length()!=0){
			final int incomeAmount = Integer.parseInt(txtIncomeAmount.getText().toString());
			final String incomeName = txtIncomeName.getText().toString();
			final String incomeNote = txtNoteIncome.getText().toString();
			final String incomeDate = txtIncomeDate.getText().toString();
			final int incomeCategoryId = ((SpinnerObject)spIncomeCategory.getSelectedItem()).getId();
			DBAdapter.open();
			DBAdapter.insert_Income(incomeCategoryId, incomeDate, incomeAmount, incomeNote, incomeName);
			DBAdapter.close();
			this.finish();
		}else{
			//Toast.makeText(this, "Invalid amount",Toast.LENGTH_SHORT).show();
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
	
	//get date from date picker
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
		txtIncomeDate.setText(dateFormat.format(calendar.getTime()));
		
	}
	 /**
     * Getting all income categories
     * returns list of income categories
     * */
    public List<SpinnerObject> getArrayIncomeCategories(){
    	
    	DBAdapter.open();
        List<SpinnerObject> incomeCategories = new ArrayList<SpinnerObject>();
        Cursor cursor = DBAdapter.getAllIncomeCategories();
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	incomeCategories.add(new SpinnerObject(cursor.getInt(0),cursor.getString(1)));
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        DBAdapter.close();
         
        // returning income categories
        return incomeCategories;
    }
	/**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // Spinner Drop down elements
        List<SpinnerObject> incomeCategories = getArrayIncomeCategories();
 
        // Creating adapter for spinner
        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,
                android.R.layout.simple_spinner_item, incomeCategories);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spIncomeCategory.setAdapter(dataAdapter);
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