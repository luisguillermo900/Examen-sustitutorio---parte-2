
package com.example.exa_susti_parte2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.exa_susti_parte2.poligono.Cuadro;
import com.example.exa_susti_parte2.poligono.Poligono;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {

    private Paint paintPolygon;
    private Paint paintCircle;
    private List<Poligono> polygons;
    private OnPolygonClickListener listener;

    private static final String TAG = "CustomView";

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintPolygon = new Paint();
        paintPolygon.setStyle(Paint.Style.STROKE);
        paintPolygon.setStrokeWidth(5);
        paintPolygon.setColor(Color.BLACK);

        paintCircle = new Paint();
        paintCircle.setColor(Color.BLUE);
        paintCircle.setStyle(Paint.Style.FILL);

        polygons = new ArrayList<>();
    }

    public void setPolygons(List<Poligono> polygons) {
        this.polygons = polygons;
        Log.d(TAG, "Count poligono: " + polygons.size());
        invalidate();
    }

    public void setOnPolygonClickListener(OnPolygonClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;

        for (Poligono polygon : polygons) {
            Path path = new Path();
            path.moveTo(polygon.vertices.get(0).x * density, polygon.vertices.get(0).y * density);
            for (int i = 1; i < polygon.vertices.size(); i++) {
                path.lineTo(polygon.vertices.get(i).x * density, polygon.vertices.get(i).y * density);
            }
            path.close();
            canvas.drawPath(path, paintPolygon);
            Log.d(TAG, "Dibujo poligono: " + polygon.name);

            for (Cuadro cuadro : polygon.cuadros) {
                canvas.drawCircle(cuadro.x * density, cuadro.y * density, 20 * density, paintCircle);
                Log.d(TAG, "Dibujo pintado en: " + cuadro.x + ", " + cuadro.y);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Poligono polygon : polygons) {
                if (polygon.contains(event.getX() / density, event.getY() / density)) {
                    if (listener != null) {
                        listener.onPolygonClick(polygon.name);
                    }
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public interface OnPolygonClickListener {
        void onPolygonClick(String polygonName);
    }
}
