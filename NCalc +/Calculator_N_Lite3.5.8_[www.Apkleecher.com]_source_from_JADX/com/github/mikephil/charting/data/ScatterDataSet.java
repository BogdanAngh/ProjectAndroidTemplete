package com.github.mikephil.charting.data;

import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.scatter.ShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.SquareShapeRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ShapeRendererHandler;
import java.util.ArrayList;
import java.util.List;

public class ScatterDataSet extends LineScatterCandleRadarDataSet<Entry> implements IScatterDataSet {
    private int mScatterShapeHoleColor;
    private float mScatterShapeHoleRadius;
    protected ShapeRenderer mShapeRenderer;
    private float mShapeSize;

    public ScatterDataSet(List<Entry> yVals, String label) {
        super(yVals, label);
        this.mShapeSize = 15.0f;
        this.mShapeRenderer = new SquareShapeRenderer();
        this.mScatterShapeHoleRadius = 0.0f;
        this.mScatterShapeHoleColor = ColorTemplate.COLOR_NONE;
    }

    public DataSet<Entry> copy() {
        List<Entry> yVals = new ArrayList();
        for (int i = 0; i < this.mValues.size(); i++) {
            yVals.add(((Entry) this.mValues.get(i)).copy());
        }
        ScatterDataSet copied = new ScatterDataSet(yVals, getLabel());
        copied.mDrawValues = this.mDrawValues;
        copied.mValueColors = this.mValueColors;
        copied.mColors = this.mColors;
        copied.mShapeSize = this.mShapeSize;
        copied.mShapeRenderer = this.mShapeRenderer;
        copied.mScatterShapeHoleRadius = this.mScatterShapeHoleRadius;
        copied.mScatterShapeHoleColor = this.mScatterShapeHoleColor;
        copied.mHighlightLineWidth = this.mHighlightLineWidth;
        copied.mHighLightColor = this.mHighLightColor;
        copied.mHighlightDashPathEffect = this.mHighlightDashPathEffect;
        return copied;
    }

    public void setScatterShapeSize(float size) {
        this.mShapeSize = size;
    }

    public float getScatterShapeSize() {
        return this.mShapeSize;
    }

    public void setScatterShape(ScatterShape shape) {
        this.mShapeRenderer = new ShapeRendererHandler().getShapeRenderer(shape);
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.mShapeRenderer = shapeRenderer;
    }

    public ShapeRenderer getShapeRenderer() {
        return this.mShapeRenderer;
    }

    public void setScatterShapeHoleRadius(float holeRadius) {
        this.mScatterShapeHoleRadius = holeRadius;
    }

    public float getScatterShapeHoleRadius() {
        return this.mScatterShapeHoleRadius;
    }

    public void setScatterShapeHoleColor(int holeColor) {
        this.mScatterShapeHoleColor = holeColor;
    }

    public int getScatterShapeHoleColor() {
        return this.mScatterShapeHoleColor;
    }
}
