package com.example.personalexpendituremanagement;




import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;


public class MainActivity extends Activity  {
	SpendingDBAdapter DBAdapter;
	TextView txtCurrentIncome;
	TextView txtCurrentExpense;
	TextView txtBalance;
	TextView txtBalanceCurrency;
	TextView txtDate;
	TextView txtMode;
	TextView txtIncomeByDate;
	TextView txtExpenseByDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.account);
		
		Button AddIncome = (Button)findViewById(R.id.btnAddIncome);
		Button AddExpense = (Button)findViewById(R.id.btnAddExpense);
		Button ReportDay = (Button)findViewById(R.id.btnToday);
		Button ReportWeek = (Button)findViewById(R.id.btnWeek);
		Button ReportMonth = (Button)findViewById(R.id.btnMonth);
		Button ReportYear = (Button)findViewById(R.id.btnYear);
		txtCurrentIncome = (TextView)findViewById(R.id.txtCurrentIncome);
		txtCurrentExpense = (TextView)findViewById(R.id.txtCurrentExpense);
		txtBalance = (TextView)findViewById(R.id.txtBalance);
		txtBalanceCurrency=(TextView)findViewById(R.id.txtBalanceCurrency);
		txtIncomeByDate=(TextView)findViewById(R.id.txtIncomeByDate);
		txtExpenseByDate=(TextView)findViewById(R.id.txtExpenseByDate);
		//initialize DB adapter
		DBAdapter = new SpendingDBAdapter(this);
		//Test DB
		//insert();
        //DisplayAll_IncomeCategories();
        //load current income
        int currentIncome = getTotalIncome();
        txtCurrentIncome.setText(String.valueOf(currentIncome));
        //load current expense
        int currentExpense = getTotalExpense();
        txtCurrentExpense.setText(String.valueOf(currentExpense));
        //load balance
        int currentBalance=currentIncome-currentExpense;
        txtBalance.setText(String.valueOf(currentBalance));
        //if balance <0, text will be turned to red
        if(currentBalance<0){
        	txtBalance.setTextAppearance(getApplicationContext(), R.style.ExpenseFormat);
        	txtBalanceCurrency.setTextAppearance(getApplicationContext(), R.style.ExpenseFormat);
        }else{
        	txtBalance.setTextAppearance(getApplicationContext(), R.style.IncomeFormat);
        	txtBalanceCurrency.setTextAppearance(getApplicationContext(), R.style.IncomeFormat);
        }
        //load pie chart with income and expense data
        /*float percentIncome = (float)(currentIncome*100.0f)/(currentExpense+currentIncome);
        float percentExpense=(float)(currentExpense*100.0f)/(currentIncome+currentExpense);
      	createPieChart(percentIncome, percentExpense);*/
      	
      	createPieChart(currentIncome, currentExpense);
        
		txtMode = (TextView)findViewById(R.id.txtMode);
		txtMode.setText(R.string.today);
		txtDate = (TextView)findViewById(R.id.txtDate);
		
		//open add income screen
		AddIncome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iAddIncome = new Intent(getApplicationContext(),AddIncome.class);
				startActivity(iAddIncome);
			}
		});
		//open add expense screen
		AddExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iAddExpense = new Intent(getApplicationContext(),AddExpense.class);
				startActivity(iAddExpense);
			}
		});
		//brief reports
		//report income, expense today
		ReportDay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				briefReportToday();
				
			}
		});
		//report income, expense in a week
		ReportWeek.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				briefReportWeek();
			}
		});
		//report income, expense in a month
		ReportMonth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				briefReportMonth();
			}
		});
		
		//report income, expense in a year
		ReportYear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				briefReportYear();
			}
		});
		briefReportToday();
	}
//End onCreate
	
	//Create pie chart 
	public void createPieChart(float percentIncome, float percentExpense){
		PieChart pie = (PieChart)findViewById(R.id.mySimplePieChart);
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
	// Test Insert Income Categories
    private void insert(){
        DBAdapter.open();
        //long id;
        DBAdapter.insertIncome_Categories("Genaral Salary");
        DBAdapter.insertIncome_Categories("Bonus Salary");
        DBAdapter.insertExpense_Categories("Buy iPad");
        DBAdapter.insertExpense_Categories("School fee");
        DBAdapter.close();
    }

    private  void DisplayAll_IncomeCategories(){
    	DBAdapter.open();
        try{
            Cursor c=DBAdapter.getAllIncomeCategories();
            if(c.moveToFirst()){
                do {
                    DisplayIncome_Categories(c);}

                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
        DBAdapter.close();
    }

    private void DisplayIncome_Categories(Cursor c){
        Toast.makeText(this, "IncomeCID: " + c.getString(0) + "\n" + "CategoryName: "+ c.getString(1),Toast.LENGTH_LONG).show();
    }
    private int getTotalIncome(){
    	int totalIncome = 0;
    	DBAdapter.open();
    	try{
            Cursor c=DBAdapter.getAllIncome();
            if(c.moveToFirst()){
                do {
                    totalIncome += Integer.parseInt(c.getString(3).toString());
                }
                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
        DBAdapter.close();
        return totalIncome;
    }
    private int getTotalExpense(){
    	int totalExpense = 0;
    	DBAdapter.open();
    	try{
            Cursor c=DBAdapter.getAllExpense();
            if(c.moveToFirst()){
                do {
                	totalExpense += Integer.parseInt(c.getString(3).toString());
                }
                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
        DBAdapter.close();
    	return totalExpense;
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	//load current income
        int currentIncome = getTotalIncome();
        txtCurrentIncome.setText(String.valueOf(currentIncome));
        //load current expense
        int currentExpense = getTotalExpense();
        txtCurrentExpense.setText(String.valueOf(currentExpense));
        //load balance
        int currentBalance=currentIncome-currentExpense;
        txtBalance.setText(String.valueOf(currentBalance));
        if(currentBalance<0){
        	txtBalance.setTextAppearance(getApplicationContext(), R.style.ExpenseFormat);
        	txtBalanceCurrency.setTextAppearance(getApplicationContext(), R.style.ExpenseFormat);
        }else{
        	txtBalance.setTextAppearance(getApplicationContext(), R.style.IncomeFormat);
        	txtBalanceCurrency.setTextAppearance(getApplicationContext(), R.style.IncomeFormat);
        }
        
        //reload today's brief report
        briefReportToday();
    }
    public String getCurrentDate(){
    	Time today = new Time(Time.getCurrentTimezone());
    	today.setToNow();
    	String currentDate = today.format("%d/%m/%Y");
		return currentDate;
    }
    public String getFirstDayOfWeek(){
    	Calendar c1 = Calendar.getInstance();

        //first day of week
        c1.set(Calendar.DAY_OF_WEEK,c1.getFirstDayOfWeek());

        String year1 = String.valueOf(c1.get(Calendar.YEAR));
        String month1;
        if(c1.get(Calendar.MONTH)+1<10){
        	 month1 = "0"+String.valueOf(c1.get(Calendar.MONTH)+1);
        }else{
        	 month1 = String.valueOf(c1.get(Calendar.MONTH)+1);
        }
        String day1;
        if(c1.get(Calendar.DAY_OF_MONTH)<10){
        	 day1 = "0"+String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }else{
        	 day1 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }
        
        String firstDayOfWeek=day1+"/"+month1+"/"+year1;
    	return firstDayOfWeek;
    }
    public String getLastDayOfWeek(){
    	Calendar c1 = Calendar.getInstance();
    	//last day of week
        c1.set(Calendar.DAY_OF_WEEK, 1);
         
        String year7 = String.valueOf(c1.get(Calendar.YEAR));
        String month7;
        if(c1.get(Calendar.MONTH)+1<10){
        	month7 = "0"+String.valueOf(c1.get(Calendar.MONTH)+1);
        }else{
        	month7 = String.valueOf(c1.get(Calendar.MONTH)+1);
        }
        
        String day7;
        if(c1.get(Calendar.DAY_OF_MONTH)<10){
        	day7 = "0"+String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }else{
        	day7 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }
        
        String lastDayOfWeek=day7+"/"+month7+"/"+year7;
    	return lastDayOfWeek;
    }
    public String getFirstDayOfMonth(){
    	Calendar c1 = Calendar.getInstance();
    	
        c1.set(Calendar.DAY_OF_MONTH, 1);

        String year = String.valueOf(c1.get(Calendar.YEAR));
        String month;
        if(c1.get(Calendar.MONTH)+1<10){
        	 month = "0"+String.valueOf(c1.get(Calendar.MONTH)+1);
        }else{
        	 month = String.valueOf(c1.get(Calendar.MONTH)+1);
        }
        String day;
        if(c1.get(Calendar.DAY_OF_MONTH) <10){
        	 day = "0"+String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }else{
        	 day = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }
        
        String firstDayOfMonth=day+"/"+month+"/"+year;
        
    	return firstDayOfMonth;
    	
    }
    public String getLastDayOfMonth(){
    	Calendar c1 = Calendar.getInstance();
    	
        c1.set(Calendar.DAY_OF_MONTH,c1.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        String year = String.valueOf(c1.get(Calendar.YEAR));
        
        String month;
        if(c1.get(Calendar.MONTH)+1<10){
        	 month = "0"+String.valueOf(c1.get(Calendar.MONTH)+1);
        }else{
        	 month = String.valueOf(c1.get(Calendar.MONTH)+1);
        }
        String day;
        if(c1.get(Calendar.DAY_OF_MONTH) <10){
        	 day = "0"+String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }else{
        	 day = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }
        String lastDayOfMonth=day+"/"+month+"/"+year;
        
    	return lastDayOfMonth;
    	
    }
    
    //get total income with date range
    // this method will be used to display pie chart
    private int getTotalIncomeWithDateRange(String startDate,String endDate){
    	int totalIncome = 0;
    	DBAdapter.open();
    	try{
            Cursor c=DBAdapter.getAllIncomeWithDateRange(startDate, endDate);
            if(c.moveToFirst()){
                do {
                    totalIncome += Integer.parseInt(c.getString(3).toString());
                }
                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
        DBAdapter.close();
        return totalIncome;
    	
    }
    //get total income with date range
    // this method will be used to display pie chart
    private int getTotalExpenseWithDateRange(String startDate,String endDate){
    	int totalExpense = 0;
    	DBAdapter.open();
    	try{
            Cursor c=DBAdapter.getAllExpenseWithDateRange(startDate, endDate);
            if(c.moveToFirst()){
                do {
                	totalExpense += Integer.parseInt(c.getString(3).toString());
                }
                while (c.moveToNext());}
        }catch (Exception e){
            System.out.println(e);
        }
        DBAdapter.close();
    	return totalExpense;
    }
    /**
    * brief report of today's income and expense
    * we will get total today's income and expense
    * then draw a pie chart with that information
    **/
    private void briefReportToday(){
    	String startDate = getCurrentDate()+" 00:00";
    	String endDate = getCurrentDate()+" 23:59";
    	int totalIncome = getTotalIncomeWithDateRange(startDate,endDate);
    	//System.out.println( "income with date range: " + totalIncome );
    	int totalExpense = getTotalExpenseWithDateRange(startDate, endDate);
    	//System.out.println( "income with date range: " + totalExpense );
    	createPieChart(totalIncome, totalExpense);
    	txtMode.setText(R.string.today);
    	txtDate.setText(getCurrentDate());
    	//update total today's income and expense text-views
    	txtIncomeByDate.setText(String.valueOf(totalIncome));
    	txtExpenseByDate.setText(String.valueOf(totalExpense));
    }
    /*
     * weekly brief report of income and expense
     * 
     * */
    private void briefReportWeek(){
    	String firstDayOfWeek = getFirstDayOfWeek()+" 00:00";
    	String lastDayOfWeek = getLastDayOfWeek()+" 23:59";
    	int totalIncome = getTotalIncomeWithDateRange(firstDayOfWeek,lastDayOfWeek);
    	//System.out.println( "income with date range: " + totalIncome );
    	int totalExpense = getTotalExpenseWithDateRange(firstDayOfWeek,lastDayOfWeek);
    	//System.out.println( "income with date range: " + totalExpense );
    	createPieChart(totalIncome, totalExpense);
    	txtMode.setText(R.string.week);
    	txtDate.setText(getFirstDayOfWeek() +" -- "+getLastDayOfWeek());
    	//update total income and expense text-views
    	txtIncomeByDate.setText(String.valueOf(totalIncome));
    	txtExpenseByDate.setText(String.valueOf(totalExpense));
    }
    /*
     * Monthly brief report of income and expense
     *
     * */
    private void briefReportMonth(){
    	String firstDayOfMonth = getFirstDayOfMonth()+" 00:00";
    	String lastDayOfMonth = getLastDayOfMonth()+" 23:59";
    	int totalIncome = getTotalIncomeWithDateRange(firstDayOfMonth,lastDayOfMonth);
    	int totalExpense = getTotalExpenseWithDateRange(firstDayOfMonth,lastDayOfMonth);
    	createPieChart(totalIncome, totalExpense);
    	txtMode.setText(R.string.month);
    	txtDate.setText(getFirstDayOfMonth() +" -- "+getLastDayOfMonth());
    	//update total income and expense text-views
    	txtIncomeByDate.setText(String.valueOf(totalIncome));
    	txtExpenseByDate.setText(String.valueOf(totalExpense));
    }
    /*
     * Yearly brief report of income and expense
     * */
    private void briefReportYear(){
    	Time today = new Time(Time.getCurrentTimezone());
    	today.setToNow();
    	String year = String.valueOf(today.year);
    	String firstDayOfYear="01/01/"+year+" 00:00";
    	String lastDayOfYear = "31/12"+year+" 23:59";
    	int totalIncome = getTotalIncomeWithDateRange(firstDayOfYear,lastDayOfYear);
    	int totalExpense = getTotalExpenseWithDateRange(firstDayOfYear,lastDayOfYear);
    	createPieChart(totalIncome, totalExpense);
    	txtMode.setText(R.string.year);
    	txtDate.setText(year);
    	//update total income and expense text-views
    	txtIncomeByDate.setText(String.valueOf(totalIncome));
    	txtExpenseByDate.setText(String.valueOf(totalExpense));
    	
    }
}
