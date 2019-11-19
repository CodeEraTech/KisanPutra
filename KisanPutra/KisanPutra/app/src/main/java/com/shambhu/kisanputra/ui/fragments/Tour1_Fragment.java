package com.shambhu.kisanputra.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.databinding.Tour1;

public class Tour1_Fragment extends Fragment {

    Tour1 binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= Tour1.inflate(inflater,container,false);

        binding.tourNxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Tour2_Fragment tour2_fragment= new Tour2_Fragment();
               FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);

                transaction.replace(R.id.tourContainer,tour2_fragment)
                        .addToBackStack(tour2_fragment.getClass().getSimpleName());


                        transaction.commit();
            }
        });

        return binding.getRoot();
    }
}
