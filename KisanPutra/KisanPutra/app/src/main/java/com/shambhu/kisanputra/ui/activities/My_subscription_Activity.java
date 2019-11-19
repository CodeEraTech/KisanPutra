package com.shambhu.kisanputra.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.My_subscription_Model;
import com.shambhu.kisanputra.ui.adapters.My_subscription_item_Adapter;

import java.util.ArrayList;

public class My_subscription_Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbar_noti_new;
    RecyclerView recycler_my_subscription;
    My_subscription_item_Adapter my_subscription_item_adapter;
    ArrayList<My_subscription_Model> My_subscription_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscription);
        init();
        settempdata();
        listener();
    }

    private void settempdata()
    {
        My_subscription_list=new ArrayList<>();
        for(int i=0;i<2;i++)
        {
            My_subscription_list.add(new My_subscription_Model("http://ec2-54-89-26-9.compute-1.amazonaws.com/uploads/products/product_gallery/grains.jpg",
                    "test item "+i,
                    ""+i+1+" Kg",
                    ""+i+1*3,
                    ""+i+1+" july 2019",
                    ""+i+3+" july 2019",
                    ""+i+5+" Days",
                    "Daily"));
        }
    }

    private void listener() {
        my_subscription_item_adapter=new My_subscription_item_Adapter(this,My_subscription_list);
        recycler_my_subscription.setAdapter(my_subscription_item_adapter);

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_noti_new = (TextView) findViewById(R.id.toolbar_noti_new);
        recycler_my_subscription = (RecyclerView) findViewById(R.id.recycler_my_subscription);
        recycler_my_subscription.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
