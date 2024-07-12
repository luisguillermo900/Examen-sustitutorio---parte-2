package com.example.exa_susti_parte2.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.exa_susti_parte2.R;

public class FragmentDescripcion extends Fragment {

    private static final String ARG_POLYGON_NAME = "polygon_name";
    private String polygonName;

    public FragmentDescripcion() {
        // Required empty public constructor
    }

    public static FragmentDescripcion newInstance(String polygonName) {
        FragmentDescripcion fragment = new FragmentDescripcion();
        Bundle args = new Bundle();
        args.putString(ARG_POLYGON_NAME, polygonName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            polygonName = getArguments().getString(ARG_POLYGON_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_descripcion, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(polygonName);
        return view;
    }
}
