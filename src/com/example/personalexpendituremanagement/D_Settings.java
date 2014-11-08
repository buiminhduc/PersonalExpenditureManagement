package com.example.personalexpendituremanagement;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class D_Settings extends Activity {

    Button ExCa_bt, InCa_bt, Date_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_settings);

        ExCa_bt = (Button) findViewById(R.id.btn_ExCa);
        InCa_bt = (Button) findViewById(R.id.btn_InCa);
        Date_bt = (Button) findViewById(R.id.btn_Report);
        ExCa_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent View_ExCa = new Intent(D_Settings.this, D_ExCa_Main.class);
                startActivity(View_ExCa);
            }
        });

        InCa_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent View_InCa = new Intent(D_Settings.this, D_InCa_Main.class);
                startActivity(View_InCa);
            }
        });

        Date_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent View_Report = new Intent(D_Settings.this, D_Report.class);
                startActivity(View_Report);
            }
        });
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


}
