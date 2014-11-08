package com.example.personalexpendituremanagement;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;


public class D_Report extends Activity implements View.OnClickListener {

    SpendingDBAdapter SPDatabase;
    DateFormat format=DateFormat.getDateInstance();
    Calendar calender=Calendar.getInstance();
    TextView txtFrom,txtTo,txtRPIncomeTotal,txtRPExpenseTotal,txtRPBalance;
    Button btn_RP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_report);

        btn_RP=(Button)findViewById(R.id.btnRPView);
        txtFrom=(TextView)findViewById(R.id.txtFrom);
        txtTo=(TextView)findViewById(R.id.txtTo);
        txtRPIncomeTotal=(TextView)findViewById(R.id.txtRPIncome);
        txtRPExpenseTotal=(TextView)findViewById(R.id.txtRPExpense);
        txtRPBalance=(TextView)findViewById(R.id.txtRPBalance);
        //initialize DB adapter
        SPDatabase = new SpendingDBAdapter(this);
        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateFrom();
            }
        });
        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTo();

            }
        });

        btn_RP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBalanceReport();
            }
        });
       // getBalanceReport();
        createPieChart(0,0);

        //TabDisplay();

        //SPDatabase=new SpendingDBAdapter(getApplicationContext());
        //System.out.println("bool1");
        //insert();
        //DisplayAll_IncomeCategories();
    }



    //get total income with date range
    // this method will be used to display pie chart
    private int getTotalIncomeWithDateRange(String startDate,String endDate){
        int totalIncome = 0;
        SPDatabase.open();
        try{
            Cursor c=SPDatabase.getAllIncomeWithDateRange(startDate, endDate);
            if(c.moveToFirst()){
                do {
                    totalIncome += Integer.parseInt(c.getString(3).toString());
                }
                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
        SPDatabase.close();
        return totalIncome;

    }

    private int getTotalExpenseWithDateRange(String startDate,String endDate){
        int totalExpense = 0;
        SPDatabase.open();
        try{
            Cursor c=SPDatabase.getAllExpenseWithDateRange(startDate, endDate);
            if(c.moveToFirst()){
                do {
                    totalExpense += Integer.parseInt(c.getString(3).toString());
                }
                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
        SPDatabase.close();
        return totalExpense;
    }
  //Create pie chart 
  	public void createPieChart(float percentIncome, float percentExpense){
  		PieChart pie = (PieChart)findViewById(R.id.reportPieChart);
  		Segment segIncome = new Segment("",percentIncome);
  		Segment segExpense = new Segment("",percentExpense);
  		
  		SegmentFormatter sfIncome = new SegmentFormatter();
  		sfIncome.configure(getApplicationContext(), R.xml.pie_segment_formatter2);
  		
  		SegmentFormatter sfExpense = new SegmentFormatter();
  		sfExpense.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
  		
  		pie.clear();
  		pie.addSeries(segIncome, sfIncome);
  		pie.addSeries(segExpense, sfExpense);
  		
  		pie.getBorderPaint().setColor(Color.TRANSPARENT);
          pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
          pie.getRenderer(PieRenderer.class).setDonutSize(0,PieRenderer.DonutMode.PERCENT);
          pie.redraw();
  	}
    private void getBalanceReport(){
    	
        String startDate = txtFrom.getText().toString()+" 00:00";
        String endDate = txtTo.getText().toString()+" 23:59";
        int totalIncome = getTotalIncomeWithDateRange(startDate,endDate);
        int totalExpense = getTotalExpenseWithDateRange(startDate, endDate);
        int RPBalance=0;
        RPBalance = totalIncome-totalExpense;
        createPieChart(totalIncome, totalExpense);
        //txtMode.setText(R.string.today);
        //txtDate.setText(getCurrentDate());
        //update total today's income and expense text-views
        txtRPIncomeTotal.setText(String.valueOf(totalIncome));
        txtRPExpenseTotal.setText(String.valueOf(totalExpense));
        txtRPBalance.setText(String.valueOf(RPBalance));
    }

    //Set Date for txt From Date
    public void FromDateUpdate()
    {
    	String year = String.valueOf(calender.get(Calendar.YEAR));
    	String month;
    	if(calender.get(Calendar.MONTH)<10){
    		month = "0"+String.valueOf(calender.get(Calendar.MONTH)+1);
    	}else{
    		month = String.valueOf(calender.get(Calendar.MONTH)+1);
    	}
    	
    	String day;
    	if(calender.get(Calendar.DAY_OF_MONTH)<10){
    		 day="0"+String.valueOf(calender.get(Calendar.DAY_OF_MONTH));
    	}else{
    		 day=String.valueOf(calender.get(Calendar.DAY_OF_MONTH));
    	}
    	
        //txtFrom.setText(format.format(calender.getTime()));
    	txtFrom.setText(day+"/"+month+"/"+year);
        
    }

    public void setDateFrom()
    {
        new DatePickerDialog(D_Report.this,DateFrom,
                calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener DateFrom=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {

            calender.set(Calendar.YEAR,year);
            calender.set(Calendar.MONTH,month);
            calender.set(Calendar.DAY_OF_MONTH,day);
            FromDateUpdate();
        }
    };


    //Set Date for txt To Date
    public void ToDateUpdate()
    {
    	String year = String.valueOf(calender.get(Calendar.YEAR));
    	String month;
    	if(calender.get(Calendar.MONTH)<10){
    		month = "0"+String.valueOf(calender.get(Calendar.MONTH)+1);
    	}else{
    		month = String.valueOf(calender.get(Calendar.MONTH)+1);
    	}
    	
    	String day;
    	if(calender.get(Calendar.DAY_OF_MONTH)<10){
    		 day="0"+String.valueOf(calender.get(Calendar.DAY_OF_MONTH));
    	}else{
    		 day=String.valueOf(calender.get(Calendar.DAY_OF_MONTH));
    	}
    	
        //txtFrom.setText(format.format(calender.getTime()));
    	txtTo.setText(day+"/"+month+"/"+year);
        //txtTo.setText(format.format(calender.getTime()));
    }

    public void setDateTo()
    {
        new DatePickerDialog(D_Report.this,DateTo,
                calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener DateTo=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {

            calender.set(Calendar.YEAR,year);
            calender.set(Calendar.MONTH,month);
            calender.set(Calendar.DAY_OF_MONTH,day);
            ToDateUpdate();
        }
    };


    // Test Insert Income Categories
    private void insert(){
        SPDatabase.open();
        //long id;
        SPDatabase.insertIncome_Categories("Genaral Salary");
        SPDatabase.insertIncome_Categories("Bonus Salary");
        SPDatabase.close();
    }

    private  void DisplayAll_IncomeCategories(){
        SPDatabase.open();
        try{
            Cursor c=SPDatabase.getAllIncomeCategories();
            if(c.moveToFirst()){
                do {
                    DisplayIncome_Categories(c);}

                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void DisplayIncome_Categories(Cursor c){
        Toast.makeText(this, "IncomeCID: " + c.getString(0) + "\n" + "CategoryName: "
                        + c.getString(1),
                Toast.LENGTH_LONG).show();
    }




    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
		/*menu.add("Account")
        .setIcon(R.drawable.ic_action_accounts)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT)
        ;

		menu.add("Report")
        .setIcon(R.drawable.ic_action_view_as_list)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Settings")
        .setIcon(R.drawable.ic_action_settings)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);*/
	}
  

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.account) {
			Intent iAccount = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(iAccount);
			return true;
		}
		if(id==R.id.report){
			Intent iReport = new Intent(getApplicationContext(),D_Report.class);
			startActivity(iReport);
			return true;
		}
		if(id==R.id.settings){
			Intent iSettings = new Intent(getApplicationContext(),D_Settings.class);
			startActivity(iSettings);
			return true;
		}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View arg0) {

    }


}
