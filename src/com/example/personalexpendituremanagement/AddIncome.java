package com.example.personalexpendituremanagement;


import java.text.DateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;


public class AddIncome extends FragmentActivity  {
	private DateFormat dateFormat = DateFormat.getInstance();
	private Calendar calendar = Calendar.getInstance();
	private TextView txtIncomeDate;
	private ImageButton btnPickIncomeDate;
	SpendingDBAdapter DBAdapter;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_income);
		
		//initialize DB adapter
		DBAdapter = new SpendingDBAdapter(this);
		
		txtIncomeDate = (TextView)findViewById(R.id.txtIncomeDate);
		btnPickIncomeDate =(ImageButton)findViewById(R.id.btnPickIncomeDate);
		btnPickIncomeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDate();
			}
		});
		updateDate();
	}
	/*public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}*/
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
	
}