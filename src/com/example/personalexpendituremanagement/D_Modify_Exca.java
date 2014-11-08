package com.example.personalexpendituremanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class D_Modify_Exca extends Activity implements View.OnClickListener {

    EditText et;
    Button edit_bt, delete_bt;

    long Exca_id;

    SpendingDBAdapter dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_modify_exca);

        dbcon = new SpendingDBAdapter(this);
        dbcon.open();

        et = (EditText) findViewById(R.id.edit_ExCa_id);
        edit_bt = (Button) findViewById(R.id.update_bt_id);
        delete_bt = (Button) findViewById(R.id.delete_bt_id);

        Intent i = getIntent();
        String ExcaID = i.getStringExtra("excaID");
        String ExcaName = i.getStringExtra("excaName");

        Exca_id = Long.parseLong(ExcaID);

        et.setText(ExcaName);

        edit_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.update_bt_id:
                String excaName_upd = et.getText().toString();
                dbcon.updateExpen(Exca_id, excaName_upd);
                this.returnHome();
                break;

            case R.id.delete_bt_id:
                dbcon.deleteExpen(Exca_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                D_ExCa_Main.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }

}