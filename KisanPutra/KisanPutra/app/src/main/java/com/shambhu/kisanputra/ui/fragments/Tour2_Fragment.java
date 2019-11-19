package com.shambhu.kisanputra.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.databinding.Tour2;

public class Tour2_Fragment extends Fragment {

    Tour2 binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= Tour2.inflate(inflater, container, false);

        binding.tourNxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tour3_Fragment tour3_fragment= new Tour3_Fragment();
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();

                transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);

                transaction.replace(R.id.tourContainer,tour3_fragment)
                        .addToBackStack(tour3_fragment.getClass().getSimpleName());


                transaction.commit();
            }
        });

        return binding.getRoot();
    }
}
