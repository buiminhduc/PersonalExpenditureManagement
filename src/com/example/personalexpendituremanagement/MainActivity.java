package com.example.personalexpendituremanagement;




import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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
	private SpendingDBAdapter SPDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.account);
		Integer income =40;
		Integer expense =60;
		createPieChart(income, expense);
		Button AddIncome = (Button)findViewById(R.id.btnAddIncome);
		Button AddExpense = (Button)findViewById(R.id.btnAddExpense);
		Button ReportDay = (Button)findViewById(R.id.btnToday);
		Button ReportWeek = (Button)findViewById(R.id.btnWeek);
		Button ReportMonth = (Button)findViewById(R.id.btnMonth);
		Button ReportQuarter = (Button)findViewById(R.id.btnQuarter);
		Button ReportYear = (Button)findViewById(R.id.btnYear);
		
		final TextView txtMode = (TextView)findViewById(R.id.txtMode);
		txtMode.setText("Day");
		final TextView txtDate = (TextView)findViewById(R.id.txtDate);
		AddIncome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iAddIncome = new Intent(getApplicationContext(),AddIncome.class);
				startActivity(iAddIncome);
			}
		});
		AddExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iAddExpense = new Intent(getApplicationContext(),AddExpense.class);
				startActivity(iAddExpense);
			}
		});
		ReportDay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtMode.setText("Day");
				
			}
		});
		ReportWeek.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtMode.setText("Week");
			}
		});
		ReportMonth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtMode.setText("Month");
			}
		});
		ReportQuarter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtMode.setText("Quarter");
			}
		});
		ReportYear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtMode.setText("Year");
			}
		});
		//insert sample data
		insert();
		//test display all categories
		DisplayAll_IncomeCategories();
	}
	//Create pie chart 
	public void createPieChart(Integer income, Integer expense){
		PieChart pie = (PieChart)findViewById(R.id.mySimplePieChart);
		Segment segIncome = new Segment("",income);
		Segment segExpense = new Segment("",expense);
		
		SegmentFormatter sfIncome = new SegmentFormatter();
		sfIncome.configure(getApplicationContext(), R.xml.pie_segment_formatter2);
		
		SegmentFormatter sfExpense = new SegmentFormatter();
		sfExpense.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
		
		pie.addSeries(segIncome, sfIncome);
		pie.addSeries(segExpense, sfExpense);
		
		pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        pie.getRenderer(PieRenderer.class).setDonutSize(0,PieRenderer.DonutMode.PERCENT);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		//return true;
		menu.add("Account")
        .setIcon(R.drawable.ic_action_accounts)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Report")
        .setIcon(R.drawable.ic_action_view_as_list)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Settings")
        .setIcon(R.drawable.ic_action_settings)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
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
        SPDatabase.close();
    }
    private void DisplayIncome_Categories(Cursor c){
        Toast.makeText(this, "IncomeCID: " + c.getString(0) + "\n" + "CategoryName: "
                        + c.getString(1),
                Toast.LENGTH_LONG).show();
    }
}
