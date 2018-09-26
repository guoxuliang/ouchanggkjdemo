package com.example.ouc.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.utils.ToastHelper;

public class Fragment2 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.fragment2, container, false);
        ImageView iv=view.findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show(getActivity(),"敬请期待");
            }
        });
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
