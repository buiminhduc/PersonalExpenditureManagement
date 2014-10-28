package com.example.personalexpendituremanagement;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

public class SampleChart extends Activity {
	private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    private PieChart pie;

    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pie_chart);
		
		// initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.mySimplePieChart);
        // detect segment clicks:
        pie.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				PointF click = new PointF(MotionEvent.AXIS_X,MotionEvent.AXIS_Y);
				if(pie.getPieWidget().containsPoint(click)){
					Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click...for now, just print
                        // the clicked segment's title to the console:
                        System.out.println("Clicked Segment: " + segment.getTitle());
                    }
				}
				return false;
			}
		});
      //test button
        Button btnClick = (Button)findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iLoadPieChart = new Intent(getApplicationContext(),LoadPieChart.class);
				startActivity(iLoadPieChart);
			}
		});
        /*donutSizeSeekBar = (SeekBar) findViewById(R.id.donutSizeSeekBar);
        donutSizeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//pie.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress()/100f,
                //        PieRenderer.DonutMode.PERCENT);
                //pie.redraw();
                //updateDonutText();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});*/
        //donutSizeTextView = (TextView) findViewById(R.id.donutSizeTextView);
        //updateDonutText();
        
        s1 = new Segment("s1", 10);
        s2 = new Segment("s2", 1);
        s3 = new Segment("s3", 10);
        s4 = new Segment("s4", 10);
        Segment s5 = new Segment("s5", 10);
        //EmbossMaskFilter emf = new EmbossMaskFilter(
        //        new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);

        //sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);

        //sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);

        //sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter();
        sf4.configure(getApplicationContext(), R.xml.pie_segment_formatter4);

        //sf4.getFillPaint().setMaskFilter(emf);

        pie.addSeries(s1, sf1);
        pie.addSeries(s2, sf2);
        pie.addSeries(s3, sf3);
        pie.addSeries(s4, sf4);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        pie.getRenderer(PieRenderer.class).setDonutSize(0,PieRenderer.DonutMode.PERCENT);
	}
	protected void updateDonutText() {
        donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
    }
}
