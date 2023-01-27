package com.example.snapdf;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public static TextView nameText, uvText, deptText;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        nameText = view.findViewById(R.id.aboutNameText);
        uvText = view.findViewById(R.id.aboutUvText);
        deptText = view.findViewById(R.id.aboutDeptText);

        JSONData jsonData = new JSONData();
        jsonData.execute();

        return view;
    }

}
