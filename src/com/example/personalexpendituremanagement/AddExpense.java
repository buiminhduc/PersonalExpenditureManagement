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

public class AddExpense extends FragmentActivity {
	private DateFormat dateFormat = DateFormat.getInstance();
	private Calendar calendar = Calendar.getInstance();
	private TextView txtExpenseDate;
	private ImageButton btnPickExpenseDate;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.add_expense);
		txtExpenseDate = (TextView)findViewById(R.id.txtExpenseDate);
		btnPickExpenseDate =(ImageButton)findViewById(R.id.btnPickExpenseDate);
		btnPickExpenseDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDate();
			}
		});
		updateDate();
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
}
