package com.shambhu.kisanputra.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.base.BaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dagger.android.support.AndroidSupportInjection;

public class My_wallet_Fragment extends Fragment {
    EditText edt_select_rs;
    TextView txt_add_500, txt_add_1000, txt_add_2000, txt_add_2500;
    Button btn_add_money;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_my_wallet, container, false);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_select_rs = view.findViewById(R.id.edt_select_rs);
        txt_add_500 = view.findViewById(R.id.txt_add_500);
        txt_add_1000 = view.findViewById(R.id.txt_add_1000);
        txt_add_2000 = view.findViewById(R.id.txt_add_2000);
        txt_add_2500 = view.findViewById(R.id.txt_add_2500);
        btn_add_money = view.findViewById(R.id.btn_add_money);

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

        btn_add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(), "Add Money Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
