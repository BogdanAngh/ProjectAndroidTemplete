package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import edu.jas.vector.GenVectorModul;
import java.util.List;

public class BubbleChartRenderer extends BarLineScatterCandleBubbleRenderer {
    private float[] _hsvBuffer;
    protected BubbleDataProvider mChart;
    private float[] pointBuffer;
    private float[] sizeBuffer;

    public BubbleChartRenderer(BubbleDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.sizeBuffer = new float[4];
        this.pointBuffer = new float[2];
        this._hsvBuffer = new float[3];
        this.mChart = chart;
        this.mRenderPaint.setStyle(Style.FILL);
        this.mHighlightPaint.setStyle(Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5f));
    }

    public void initBuffers() {
    }

    public void drawData(Canvas c) {
        List<IBubbleDataSet> dataSets = this.mChart.getBubbleData().getDataSets();
        int setCount = dataSets.size();
        for (int i = 0; i < setCount; i++) {
            IBubbleDataSet set = (IBubbleDataSet) dataSets.get(i);
            if (set.isVisible() && set.getEntryCount() > 0) {
                drawDataSet(c, set);
            }
        }
    }

    protected float getShapeSize(float entrySize, float maxSize, float reference, boolean normalizeSize) {
        float factor = normalizeSize ? maxSize == 0.0f ? 1.0f : (float) Math.sqrt((double) (entrySize / maxSize)) : entrySize;
        return reference * factor;
    }

    protected void drawDataSet(Canvas c, IBubbleDataSet dataSet) {
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        this.mXBounds.set(this.mChart, dataSet);
        this.sizeBuffer[0] = 0.0f;
        this.sizeBuffer[2] = 1.0f;
        trans.pointValuesToPixel(this.sizeBuffer);
        boolean normalizeSize = dataSet.isNormalizeSizeEnabled();
        float referenceSize = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]));
        for (int j = this.mXBounds.min; j <= this.mXBounds.range + this.mXBounds.min; j++) {
            BubbleEntry entry = (BubbleEntry) dataSet.getEntryForIndex(j);
            this.pointBuffer[0] = entry.getX();
            this.pointBuffer[1] = entry.getY() * phaseY;
            trans.pointValuesToPixel(this.pointBuffer);
            float shapeHalf = getShapeSize(entry.getSize(), dataSet.getMaxSize(), referenceSize, normalizeSize) / 2.0f;
            if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeHalf) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeHalf) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + shapeHalf)) {
                if (this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - shapeHalf)) {
                    this.mRenderPaint.setColor(dataSet.getColor((int) entry.getX()));
                    c.drawCircle(this.pointBuffer[0], this.pointBuffer[1], shapeHalf, this.mRenderPaint);
                } else {
                    return;
                }
            }
        }
    }

    public void drawValues(Canvas c) {
        BubbleData bubbleData = this.mChart.getBubbleData();
        if (bubbleData != null) {
            if (isDrawingValuesAllowed(this.mChart)) {
                List<IBubbleDataSet> dataSets = bubbleData.getDataSets();
                float lineHeight = (float) Utils.calcTextHeight(this.mValuePaint, "1");
                for (int i = 0; i < dataSets.size(); i++) {
                    IBubbleDataSet dataSet = (IBubbleDataSet) dataSets.get(i);
                    if (dataSet.isDrawValuesEnabled() && dataSet.getEntryCount() != 0) {
                        float alpha;
                        applyValueTextStyle(dataSet);
                        float phaseX = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
                        float phaseY = this.mAnimator.getPhaseY();
                        this.mXBounds.set(this.mChart, dataSet);
                        float[] positions = this.mChart.getTransformer(dataSet.getAxisDependency()).generateTransformedValuesBubble(dataSet, phaseY, this.mXBounds.min, this.mXBounds.max);
                        if (phaseX == 1.0f) {
                            alpha = phaseY;
                        } else {
                            alpha = phaseX;
                        }
                        for (int j = 0; j < positions.length; j += 2) {
                            int valueTextColor = dataSet.getValueTextColor((j / 2) + this.mXBounds.min);
                            valueTextColor = Color.argb(Math.round(255.0f * alpha), Color.red(valueTextColor), Color.green(valueTextColor), Color.blue(valueTextColor));
                            float x = positions[j];
                            float y = positions[j + 1];
                            if (!this.mViewPortHandler.isInBoundsRight(x)) {
                                break;
                            }
                            if (this.mViewPortHandler.isInBoundsLeft(x) && this.mViewPortHandler.isInBoundsY(y)) {
                                BubbleEntry entry = (BubbleEntry) dataSet.getEntryForIndex((j / 2) + this.mXBounds.min);
                                Canvas canvas = c;
                                drawValue(canvas, dataSet.getValueFormatter(), entry.getSize(), entry, i, x, y + (GenVectorModul.DEFAULT_DENSITY * lineHeight), valueTextColor);
                            }
                        }
                    }
                }
            }
        }
    }

    public void drawExtras(Canvas c) {
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        BubbleData bubbleData = this.mChart.getBubbleData();
        float phaseY = this.mAnimator.getPhaseY();
        for (Highlight high : indices) {
            IBubbleDataSet set = (IBubbleDataSet) bubbleData.getDataSetByIndex(high.getDataSetIndex());
            if (set != null && set.isHighlightEnabled()) {
                BubbleEntry entry = (BubbleEntry) set.getEntryForXPos(high.getX());
                if (isInBoundsX(entry, set)) {
                    Transformer trans = this.mChart.getTransformer(set.getAxisDependency());
                    this.sizeBuffer[0] = 0.0f;
                    this.sizeBuffer[2] = 1.0f;
                    trans.pointValuesToPixel(this.sizeBuffer);
                    boolean normalizeSize = set.isNormalizeSizeEnabled();
                    float referenceSize = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]));
                    this.pointBuffer[0] = entry.getX();
                    this.pointBuffer[1] = entry.getY() * phaseY;
                    trans.pointValuesToPixel(this.pointBuffer);
                    high.setDraw(this.pointBuffer[0], this.pointBuffer[1]);
                    float shapeHalf = getShapeSize(entry.getSize(), set.getMaxSize(), referenceSize, normalizeSize) / 2.0f;
                    if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeHalf)) {
                        if (this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeHalf)) {
                            if (this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + shapeHalf)) {
                                if (this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - shapeHalf)) {
                                    int originalColor = set.getColor((int) entry.getX());
                                    Color.RGBToHSV(Color.red(originalColor), Color.green(originalColor), Color.blue(originalColor), this._hsvBuffer);
                                    float[] fArr = this._hsvBuffer;
                                    fArr[2] = fArr[2] * GenVectorModul.DEFAULT_DENSITY;
                                    this.mHighlightPaint.setColor(Color.HSVToColor(Color.alpha(originalColor), this._hsvBuffer));
                                    this.mHighlightPaint.setStrokeWidth(set.getHighlightCircleWidth());
                                    c.drawCircle(this.pointBuffer[0], this.pointBuffer[1], shapeHalf, this.mHighlightPaint);
                                } else {
                                    return;
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
    }
}
