package com.example.personalexpendituremanagement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class D_InCa_Main extends Activity {

    Button addInCa_bt;
    ListView lv;
    SpendingDBAdapter dbcon;
    TextView InCaID_tv, InCaName_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d__inca_main);
        dbcon = new SpendingDBAdapter(this);
        dbcon.open();
        addInCa_bt = (Button) findViewById(R.id.addInCa_bt_id);
        lv = (ListView) findViewById(R.id.InCaList_id);
        // onClickListiner for addmember Button
        addInCa_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent add_InCa = new Intent(D_InCa_Main.this, D_Add_InCa.class);
                startActivity(add_InCa);
            }
        });

        // Attach The Data From DataBase Into ListView Using Crusor Adapter
        Cursor cursor = dbcon.readInCa();
        String[] from = new String[] { SpendingDBAdapter.KEY_IncomeCID, SpendingDBAdapter.KEY_IncomeCName };
        int[] to = new int[] { R.id.InCa_id, R.id.InCa_name };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                D_InCa_Main.this, R.layout.view_inca_entry, cursor, from, to);

        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);

        // OnCLickListiner For List Items
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                InCaID_tv = (TextView) view.findViewById(R.id.InCa_id);
                InCaName_tv = (TextView) view.findViewById(R.id.InCa_name);

                String incaID_val = InCaID_tv.getText().toString();
                String incaName_val = InCaName_tv.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(),
                        D_Modify_InCa.class);
                modify_intent.putExtra("incaName", incaName_val);
                modify_intent.putExtra("incaID", incaID_val);
                startActivity(modify_intent);
            }
        });

    } // create method end
}