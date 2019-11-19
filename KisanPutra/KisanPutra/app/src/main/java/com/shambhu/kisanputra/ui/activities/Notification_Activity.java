package com.shambhu.kisanputra.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.My_subscription_Model;
import com.shambhu.kisanputra.data.models.notification_model;
import com.shambhu.kisanputra.ui.adapters.My_subscription_item_Adapter;
import com.shambhu.kisanputra.ui.adapters.Notification_item_Adapter;

import java.util.ArrayList;

public class Notification_Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbar_title, toolbar_clear_all;
    RecyclerView recycler_noti;
    Notification_item_Adapter notification_item_adapter;
    ArrayList<notification_model> notification_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_clear_all = (TextView) findViewById(R.id.toolbar_clear_all);
        recycler_noti = (RecyclerView) findViewById(R.id.recycler_noti);
        recycler_noti.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        settempdata();
        listener();
    }


    private void settempdata()
    {
        notification_list=new ArrayList<>();
        for(int i=0;i<2;i++)
        {
            notification_list.add(new notification_model("http://ec2-54-89-26-9.compute-1.amazonaws.com/uploads/products/product_gallery/grains.jpg",
                    "test notification name"+i+1,
                    ""+i+1+"/"+"10"+"/"+"2019",
                    "Rasna Masla Orange",
                    "Chatpta",
                    "vidhansabha road,stya vihar,jaipur,Rajasthan"));
        }
    }

    private void listener() {
        notification_item_adapter=new Notification_item_Adapter(this,notification_list);
        recycler_noti.setAdapter(notification_item_adapter);
        toolbar_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_list.clear();
                recycler_noti.getAdapter().notifyDataSetChanged();
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
}
