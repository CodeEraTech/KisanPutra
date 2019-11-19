package com.shambhu.kisanputra.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.shambhu.kisanputra.databinding.Tour3;
import com.shambhu.kisanputra.ui.activities.LoginActivity;

public class Tour3_Fragment extends Fragment {

    Tour3 binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= Tour3.inflate(inflater, container, false);

        binding.tourNxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return binding.getRoot();
    }


}
