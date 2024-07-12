package com.example.exa_susti_parte2.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.exa_susti_parte2.R;
import com.example.exa_susti_parte2.poligono.Poligono;
import com.example.exa_susti_parte2.util.CSVReaderUtil;
import com.example.exa_susti_parte2.view.CustomView;

import java.util.List;

public class FragmentPoligono extends Fragment {

    private CustomView customView;

    public FragmentPoligono() {
        // Required empty public constructor
    }

    public static FragmentPoligono newInstance() {
        return new FragmentPoligono();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poligono, container, false);
        customView = view.findViewById(R.id.customView);

        CSVReaderUtil.readPolygonsAsync(getContext(), new CSVReaderUtil.CSVReaderCallback() {
            @Override
            public void onPolygonsRead(List<Poligono> polygons) {
                CSVReaderUtil.readPaintingsAsync(getContext(), polygons, new CSVReaderUtil.CSVReaderCallback() {
                    @Override
                    public void onPolygonsRead(List<Poligono> polygons) {

                    }

                    @Override
                    public void onPaintingsRead() {
                        customView.setPolygons(polygons);
                    }
                });
            }

            @Override
            public void onPaintingsRead() {

            }
        });

        customView.setOnPolygonClickListener(polygonName -> {
            FragmentDescripcion fragmentDescripcion = FragmentDescripcion.newInstance(polygonName);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragmentDescripcion)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
