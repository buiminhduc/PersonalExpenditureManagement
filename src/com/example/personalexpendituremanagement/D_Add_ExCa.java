package com.example.personalexpendituremanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class D_Add_ExCa extends Activity implements View.OnClickListener {

    EditText et;
    Button add_bt;
    SpendingDBAdapter dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_add_exca);
        et = (EditText) findViewById(R.id.ExCa_et_id);
        add_bt = (Button) findViewById(R.id.add_bt_id);

        dbcon = new SpendingDBAdapter(this);
        dbcon.open();
        add_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_bt_id:
                String exca_name = et.getText().toString();
                dbcon.insertExpenCa(exca_name);
                Intent main = new Intent(D_Add_ExCa.this, D_ExCa_Main.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(main);
                break;

            default:
                break;
        }
    }
}
