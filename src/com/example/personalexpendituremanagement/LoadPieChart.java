package com.example.personalexpendituremanagement;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;


import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

public class LoadPieChart extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pie_chart);
		Integer income =40;
		Integer expense =60;
		createPieChart(income, expense);
	}
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
}
