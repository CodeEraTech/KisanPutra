package com.shambhu.kisanputra.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shambhu.kisanputra.R;

public class Add_to_Money_Avtivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_cart_amount, txt_Wallet_amount;
    EditText edt_select_rs;
    TextView txt_add_500, txt_add_1000, txt_add_2000, txt_add_2500;
    Button btn_recharge_place_order;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_proceed);
        init();
        listener();
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_cart_amount = findViewById(R.id.txt_cart_amount);
        txt_Wallet_amount = findViewById(R.id.txt_Wallet_amount);
        edt_select_rs = findViewById(R.id.edt_select_rs);
        txt_add_500 = findViewById(R.id.txt_add_500);
        txt_add_1000 = findViewById(R.id.txt_add_1000);
        txt_add_2000 = findViewById(R.id.txt_add_2000);
        txt_add_2500 = findViewById(R.id.txt_add_2500);
        btn_recharge_place_order = findViewById(R.id.btn_recharge_place_order);
    }

    private void listener() {
        txt_add_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_select_rs.setText("500");
            }
        });
        txt_add_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_select_rs.setText("1000");
            }
        });
        txt_add_2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_select_rs.setText("2000");
            }
        });
        txt_add_2500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_select_rs.setText("2500");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_recharge_place_order){

        }
    }
}
