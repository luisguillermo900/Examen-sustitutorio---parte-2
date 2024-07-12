package com.example.exa_susti_parte2.util;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.exa_susti_parte2.R;
import com.example.exa_susti_parte2.poligono.Cuadro;
import com.example.exa_susti_parte2.poligono.Poligono;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderUtil {

    private static final String TAG = "CSVReaderUtil";

    public interface CSVReaderCallback {
        void onPolygonsRead(List<Poligono> polygons);
        void onPaintingsRead();
    }

    public static List<Poligono> readPolygons(Context context) {
        List<Poligono> polygons = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.polygons);
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            List<String[]> lines = reader.readAll();

            if (!lines.isEmpty()) {
                lines.remove(0);  // Remueve la cabecera
            }

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float density = displayMetrics.density;

            for (String[] line : lines) {
                String name = line[0];
                List<PointF> vertices = new ArrayList<>();
                for (int i = 1; i < line.length; i += 2) {
                    float x = Float.parseFloat(line[i]) * density;
                    float y = Float.parseFloat(line[i + 1]) * density;
                    vertices.add(new PointF(x, y));
                }
                polygons.add(new Poligono(name, vertices, new ArrayList<>()));
                Log.d(TAG, "Poligono: " + name);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
        }
        return polygons;
    }

    public static void readPaintings(Context context, List<Poligono> polygons) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.paintings);
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            List<String[]> lines = reader.readAll();

            if (!lines.isEmpty()) {
                lines.remove(0);
            }

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float density = displayMetrics.density;

            for (String[] line : lines) {
                String name = line[0];
                float x = Float.parseFloat(line[2]) * density;
                float y = Float.parseFloat(line[3]) * density;
                Cuadro cuadro = new Cuadro(x, y);
                for (Poligono polygon : polygons) {
                    if (polygon.name.equals(name)) {
                        polygon.cuadros.add(cuadro);
                        Log.d(TAG, "Cuadro" + line[1] + " poligono " + name);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
        }
    }

    public static void readPolygonsAsync(Context context, CSVReaderCallback callback) {
        new Thread(() -> {
            List<Poligono> polygons = readPolygons(context);
            if (callback != null) {
                callback.onPolygonsRead(polygons);
            }
        }).start();
    }

    public static void readPaintingsAsync(Context context, List<Poligono> polygons, CSVReaderCallback callback) {
        new Thread(() -> {
            readPaintings(context, polygons);
            if (callback != null) {
                callback.onPaintingsRead();
            }
        }).start();
    }
}
