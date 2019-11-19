package com.shambhu.kisanputra.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.base.BaseActivity;
import com.shambhu.kisanputra.ui.fragments.Tour1_Fragment;

public class TourMain extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour1);



        checkLocation();

        Tour1_Fragment tour1_fragment= new Tour1_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.tourContainer,tour1_fragment).commit();




    }
}
