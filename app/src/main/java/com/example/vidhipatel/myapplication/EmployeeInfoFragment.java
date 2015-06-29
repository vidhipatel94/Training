package com.example.vidhipatel.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class EmployeeInfoFragment extends Fragment {

    @Bind(R.id.empname) TextView textView;
    @Bind(R.id.empdesignation) TextView textView2;
    @Bind(R.id.empemail) TextView textView3;
    public static String NAME="NAME";
    public static String USERNAME="USERNAME";
    public static String EMAIL="EMAIL";

    public static EmployeeInfoFragment newInstance(User mUser) {
        EmployeeInfoFragment fragment = new EmployeeInfoFragment();
        Bundle args = new Bundle();
        args.putString(NAME, mUser.getName());
        args.putString(USERNAME, mUser.getUsername());
        args.putString(EMAIL, mUser.getEmail());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_employee_info, container, false);
        ButterKnife.bind(this,v);

        textView.setText(getArguments().getString(NAME));
        textView2.setText(getArguments().getString(USERNAME));
        textView3.setText(getArguments().getString(EMAIL));

        return v;
    }


}
