package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class HorizontalBarChartRenderer extends BarChartRenderer {
    public HorizontalBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        this.mValuePaint.setTextAlign(Align.LEFT);
    }

    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new HorizontalBarBuffer[barData.getDataSetCount()];
        for (int i = 0; i < this.mBarBuffers.length; i++) {
            IBarDataSet set = (IBarDataSet) barData.getDataSetByIndex(i);
            this.mBarBuffers[i] = new HorizontalBarBuffer((set.isStacked() ? set.getStackSize() : 1) * (set.getEntryCount() * 4), barData.getDataSetCount(), set.isStacked());
        }
    }

    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        this.mShadowPaint.setColor(dataSet.getBarShadowColor());
        this.mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));
        boolean drawBorder = dataSet.getBarBorderWidth() > 0.0f;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        BarBuffer buffer = this.mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(this.mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        buffer.feed(dataSet);
        trans.pointValuesToPixel(buffer.buffer);
        int j = 0;
        while (j < buffer.size() && this.mViewPortHandler.isInBoundsTop(buffer.buffer[j + 3])) {
            if (this.mViewPortHandler.isInBoundsBottom(buffer.buffer[j + 1])) {
                if (this.mChart.isDrawBarShadowEnabled()) {
                    c.drawRect(this.mViewPortHandler.contentLeft(), buffer.buffer[j + 1], this.mViewPortHandler.contentRight(), buffer.buffer[j + 3], this.mShadowPaint);
                }
                this.mRenderPaint.setColor(dataSet.getColor(j / 4));
                c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], this.mRenderPaint);
                if (drawBorder) {
                    c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], this.mBarBorderPaint);
                }
            }
            j += 4;
        }
    }

    public void drawValues(Canvas c) {
        if (isDrawingValuesAllowed(this.mChart)) {
            List<IBarDataSet> dataSets = this.mChart.getBarData().getDataSets();
            float valueOffsetPlus = Utils.convertDpToPixel(5.0f);
            boolean drawValueAboveBar = this.mChart.isDrawValueAboveBarEnabled();
            for (int i = 0; i < this.mChart.getBarData().getDataSetCount(); i++) {
                IBarDataSet dataSet = (IBarDataSet) dataSets.get(i);
                if (dataSet.isDrawValuesEnabled() && dataSet.getEntryCount() != 0) {
                    boolean isInverted = this.mChart.isInverted(dataSet.getAxisDependency());
                    applyValueTextStyle(dataSet);
                    float halfTextHeight = ((float) Utils.calcTextHeight(this.mValuePaint, "10")) / 2.0f;
                    ValueFormatter formatter = dataSet.getValueFormatter();
                    BarBuffer buffer = this.mBarBuffers[i];
                    float y;
                    BarEntry e;
                    float val;
                    String formattedValue;
                    float valueTextWidth;
                    float posOffset;
                    float negOffset;
                    float f;
                    float f2;
                    if (!dataSet.isStacked()) {
                        int j = 0;
                        while (((float) j) < ((float) buffer.buffer.length) * this.mAnimator.getPhaseX()) {
                            y = (buffer.buffer[j + 1] + buffer.buffer[j + 3]) / 2.0f;
                            if (!this.mViewPortHandler.isInBoundsTop(buffer.buffer[j + 1])) {
                                break;
                            }
                            if (this.mViewPortHandler.isInBoundsX(buffer.buffer[j]) && this.mViewPortHandler.isInBoundsBottom(buffer.buffer[j + 1])) {
                                e = (BarEntry) dataSet.getEntryForIndex(j / 4);
                                val = e.getY();
                                formattedValue = formatter.getFormattedValue(val, e, i, this.mViewPortHandler);
                                valueTextWidth = (float) Utils.calcTextWidth(this.mValuePaint, formattedValue);
                                posOffset = drawValueAboveBar ? valueOffsetPlus : -(valueTextWidth + valueOffsetPlus);
                                if (drawValueAboveBar) {
                                    negOffset = -(valueTextWidth + valueOffsetPlus);
                                } else {
                                    negOffset = valueOffsetPlus;
                                }
                                if (isInverted) {
                                    posOffset = (-posOffset) - valueTextWidth;
                                    negOffset = (-negOffset) - valueTextWidth;
                                }
                                f = buffer.buffer[j + 2];
                                if (val >= 0.0f) {
                                    f2 = posOffset;
                                } else {
                                    f2 = negOffset;
                                }
                                drawValue(c, formattedValue, f + f2, y + halfTextHeight, dataSet.getValueTextColor(j / 2));
                            }
                            j += 4;
                        }
                    } else {
                        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
                        int bufferIndex = 0;
                        int index = 0;
                        while (((float) index) < ((float) dataSet.getEntryCount()) * this.mAnimator.getPhaseX()) {
                            e = (BarEntry) dataSet.getEntryForIndex(index);
                            int color = dataSet.getValueTextColor(index);
                            float[] vals = e.getYVals();
                            if (vals == null) {
                                if (!this.mViewPortHandler.isInBoundsTop(buffer.buffer[bufferIndex + 1])) {
                                    break;
                                } else if (this.mViewPortHandler.isInBoundsX(buffer.buffer[bufferIndex]) && this.mViewPortHandler.isInBoundsBottom(buffer.buffer[bufferIndex + 1])) {
                                    formattedValue = formatter.getFormattedValue(e.getY(), e, i, this.mViewPortHandler);
                                    valueTextWidth = (float) Utils.calcTextWidth(this.mValuePaint, formattedValue);
                                    posOffset = drawValueAboveBar ? valueOffsetPlus : -(valueTextWidth + valueOffsetPlus);
                                    if (drawValueAboveBar) {
                                        negOffset = -(valueTextWidth + valueOffsetPlus);
                                    } else {
                                        negOffset = valueOffsetPlus;
                                    }
                                    if (isInverted) {
                                        posOffset = (-posOffset) - valueTextWidth;
                                        negOffset = (-negOffset) - valueTextWidth;
                                    }
                                    f = buffer.buffer[bufferIndex + 2];
                                    if (e.getY() >= 0.0f) {
                                        f2 = posOffset;
                                    } else {
                                        f2 = negOffset;
                                    }
                                    drawValue(c, formattedValue, f + f2, buffer.buffer[bufferIndex + 1] + halfTextHeight, color);
                                }
                            } else {
                                float[] transformed = new float[(vals.length * 2)];
                                float posY = 0.0f;
                                float negY = -e.getNegativeSum();
                                int k = 0;
                                int idx = 0;
                                while (k < transformed.length) {
                                    float value = vals[idx];
                                    if (value >= 0.0f) {
                                        posY += value;
                                        y = posY;
                                    } else {
                                        y = negY;
                                        negY -= value;
                                    }
                                    transformed[k] = this.mAnimator.getPhaseY() * y;
                                    k += 2;
                                    idx++;
                                }
                                trans.pointValuesToPixel(transformed);
                                for (k = 0; k < transformed.length; k += 2) {
                                    val = vals[k / 2];
                                    formattedValue = formatter.getFormattedValue(val, e, i, this.mViewPortHandler);
                                    valueTextWidth = (float) Utils.calcTextWidth(this.mValuePaint, formattedValue);
                                    posOffset = drawValueAboveBar ? valueOffsetPlus : -(valueTextWidth + valueOffsetPlus);
                                    if (drawValueAboveBar) {
                                        negOffset = -(valueTextWidth + valueOffsetPlus);
                                    } else {
                                        negOffset = valueOffsetPlus;
                                    }
                                    if (isInverted) {
                                        posOffset = (-posOffset) - valueTextWidth;
                                        negOffset = (-negOffset) - valueTextWidth;
                                    }
                                    f = transformed[k];
                                    if (val >= 0.0f) {
                                        f2 = posOffset;
                                    } else {
                                        f2 = negOffset;
                                    }
                                    float x = f + f2;
                                    y = (buffer.buffer[bufferIndex + 1] + buffer.buffer[bufferIndex + 3]) / 2.0f;
                                    if (!this.mViewPortHandler.isInBoundsTop(y)) {
                                        break;
                                    }
                                    if (this.mViewPortHandler.isInBoundsX(x) && this.mViewPortHandler.isInBoundsBottom(y)) {
                                        drawValue(c, formattedValue, x, y + halfTextHeight, color);
                                    }
                                }
                            }
                            bufferIndex = vals == null ? bufferIndex + 4 : bufferIndex + (vals.length * 4);
                            index++;
                        }
                    }
                }
            }
        }
    }

    protected void drawValue(Canvas c, String valueText, float x, float y, int color) {
        this.mValuePaint.setColor(color);
        c.drawText(valueText, x, y, this.mValuePaint);
    }

    protected void prepareBarHighlight(float x, float y1, float y2, float barWidthHalf, Transformer trans) {
        float left = y1;
        float right = y2;
        this.mBarRect.set(left, x - barWidthHalf, right, x + barWidthHalf);
        trans.rectToPixelPhaseHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
    }

    protected void setHighlightDrawPos(Highlight high, RectF bar) {
        high.setDraw(bar.centerY(), bar.right);
    }

    protected boolean isDrawingValuesAllowed(ChartInterface chart) {
        return ((float) chart.getData().getEntryCount()) < ((float) chart.getMaxVisibleCount()) * this.mViewPortHandler.getScaleY();
    }
}
