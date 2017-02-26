package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.renderer.scatter.ChevronDownShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.ChevronUpShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.CircleShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.CrossShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.ShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.SquareShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.TriangleShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.XShapeRenderer;
import java.util.HashMap;

public final class ShapeRendererHandler {
    protected HashMap<String, ShapeRenderer> shapeRendererList;

    public ShapeRendererHandler() {
        initShapeRenderers();
    }

    public ShapeRenderer getShapeRenderer(ScatterShape shape) {
        return (ShapeRenderer) this.shapeRendererList.get(shape.toString());
    }

    protected void initShapeRenderers() {
        this.shapeRendererList = new HashMap();
        this.shapeRendererList.put(ScatterShape.SQUARE.toString(), new SquareShapeRenderer());
        this.shapeRendererList.put(ScatterShape.CIRCLE.toString(), new CircleShapeRenderer());
        this.shapeRendererList.put(ScatterShape.TRIANGLE.toString(), new TriangleShapeRenderer());
        this.shapeRendererList.put(ScatterShape.CROSS.toString(), new CrossShapeRenderer());
        this.shapeRendererList.put(ScatterShape.X.toString(), new XShapeRenderer());
        this.shapeRendererList.put(ScatterShape.CHEVRON_UP.toString(), new ChevronUpShapeRenderer());
        this.shapeRendererList.put(ScatterShape.CHEVRON_DOWN.toString(), new ChevronDownShapeRenderer());
    }
}
