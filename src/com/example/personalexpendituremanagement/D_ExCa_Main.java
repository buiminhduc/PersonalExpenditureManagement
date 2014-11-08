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


public class D_ExCa_Main extends Activity {

    Button addExCa_bt;
    ListView lv;
    SpendingDBAdapter dbcon;
    TextView ExCaID_tv, ExCaName_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_exca_main);
        dbcon = new SpendingDBAdapter(this);
        dbcon.open();
        addExCa_bt = (Button) findViewById(R.id.addExca_bt_id);
        lv = (ListView) findViewById(R.id.ExCaList_id);
        // onClickListiner for add exca  Button
        addExCa_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent add_ExCa = new Intent(D_ExCa_Main.this, D_Add_ExCa.class);
                startActivity(add_ExCa);
            }
        });

        // Attach The Data From DataBase Into ListView Using Cursor Adapter
        Cursor cursor = dbcon.readExpenCa();
        String[] from = new String[] { SpendingDBAdapter.KEY_ExpenseCID, SpendingDBAdapter.KEY_ExpenseCName };
        int[] to = new int[] { R.id.ExCa_id, R.id.ExCa_name };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                D_ExCa_Main.this, R.layout.view_exca_entry, cursor, from, to);

        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);

        // OnCLickListiner For List Items
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ExCaID_tv = (TextView) view.findViewById(R.id.ExCa_id);
                ExCaName_tv = (TextView) view.findViewById(R.id.ExCa_name);

                String excaID_val = ExCaID_tv.getText().toString();
                String excaName_val = ExCaName_tv.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(),
                        D_Modify_Exca.class);
                modify_intent.putExtra("excaName", excaName_val);
                modify_intent.putExtra("excaID", excaID_val);
                startActivity(modify_intent);
            }
        });

    } // create method end
}
