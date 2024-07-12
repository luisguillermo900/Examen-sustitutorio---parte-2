package com.example.exa_susti_parte2.poligono;

import android.content.Context;
import android.graphics.PointF;
import android.util.TypedValue;

import java.util.List;

public class Poligono {
    public String name;
    public List<PointF> vertices;
    public List<Cuadro> cuadros;

    public Poligono(String name, List<PointF> vertices, List<Cuadro> cuadros) {
        this.name = name;
        this.vertices = vertices;
        this.cuadros = cuadros;
    }

    public void convertToDp(Context context) {
        for (PointF vertex : vertices) {
            vertex.x = convertPixelsToDp(vertex.x, context);
            vertex.y = convertPixelsToDp(vertex.y, context);
        }
        for (Cuadro cuadro : cuadros) {
            cuadro.x = convertPixelsToDp(cuadro.x, context);
            cuadro.y = convertPixelsToDp(cuadro.y, context);
        }
    }

    private float convertPixelsToDp(float px, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }

    public boolean contains(float x, float y) {
        int crossings = 0;
        for (int i = 0; i < vertices.size(); i++) {
            PointF a = vertices.get(i);
            PointF b = vertices.get((i + 1) % vertices.size());
            if (((a.y > y) != (b.y > y)) &&
                    (x < (b.x - a.x) * (y - a.y) / (b.y - a.y) + a.x)) {
                crossings++;
            }
        }
        return (crossings % 2 == 1);
    }
}
