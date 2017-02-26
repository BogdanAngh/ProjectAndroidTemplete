package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;

public class YAxisRendererRadarChart extends YAxisRenderer {
    private RadarChart mChart;
    private Path mRenderLimitLinesPathBuffer;

    public YAxisRendererRadarChart(ViewPortHandler viewPortHandler, YAxis yAxis, RadarChart chart) {
        super(viewPortHandler, yAxis, null);
        this.mRenderLimitLinesPathBuffer = new Path();
        this.mChart = chart;
    }

    protected void computeAxisValues(float min, float max) {
        float yMin = min;
        float yMax = max;
        int labelCount = this.mAxis.getLabelCount();
        double range = (double) Math.abs(yMax - yMin);
        if (labelCount == 0 || range <= 0.0d) {
            this.mAxis.mEntries = new float[0];
            this.mAxis.mEntryCount = 0;
            return;
        }
        int length;
        int i;
        double interval = (double) Utils.roundToNextSignificant(range / ((double) labelCount));
        if (this.mAxis.isGranularityEnabled()) {
            if (interval < ((double) this.mAxis.getGranularity())) {
                interval = (double) this.mAxis.getGranularity();
            }
        }
        double intervalMagnitude = (double) Utils.roundToNextSignificant(Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, (double) ((int) Math.log10(interval))));
        if (((int) (interval / intervalMagnitude)) > 5) {
            interval = Math.floor(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS * intervalMagnitude);
        }
        boolean centeringEnabled = this.mAxis.isCenterAxisLabelsEnabled();
        int n = centeringEnabled ? 1 : 0;
        if (this.mAxis.isForceLabelsEnabled()) {
            float step = ((float) range) / ((float) (labelCount - 1));
            this.mAxis.mEntryCount = labelCount;
            length = this.mAxis.mEntries.length;
            if (r0 < labelCount) {
                this.mAxis.mEntries = new float[labelCount];
            }
            float v = min;
            for (i = 0; i < labelCount; i++) {
                this.mAxis.mEntries[i] = v;
                v += step;
            }
            n = labelCount;
        } else {
            double last;
            double f;
            double first = interval == 0.0d ? 0.0d : Math.ceil(((double) yMin) / interval) * interval;
            if (centeringEnabled) {
                first -= interval;
            }
            if (interval == 0.0d) {
                last = 0.0d;
            } else {
                last = Utils.nextUp(Math.floor(((double) yMax) / interval) * interval);
            }
            if (interval != 0.0d) {
                for (f = first; f <= last; f += interval) {
                    n++;
                }
            }
            n++;
            this.mAxis.mEntryCount = n;
            length = this.mAxis.mEntries.length;
            if (r0 < n) {
                this.mAxis.mEntries = new float[n];
            }
            f = first;
            for (i = 0; i < n; i++) {
                if (f == 0.0d) {
                    f = 0.0d;
                }
                this.mAxis.mEntries[i] = (float) f;
                f += interval;
            }
        }
        if (interval < 1.0d) {
            this.mAxis.mDecimals = (int) Math.ceil(-Math.log10(interval));
        } else {
            this.mAxis.mDecimals = 0;
        }
        if (centeringEnabled) {
            length = this.mAxis.mCenteredEntries.length;
            if (r0 < n) {
                this.mAxis.mCenteredEntries = new float[n];
            }
            float offset = (this.mAxis.mEntries[1] - this.mAxis.mEntries[0]) / 2.0f;
            for (i = 0; i < n; i++) {
                this.mAxis.mCenteredEntries[i] = this.mAxis.mEntries[i] + offset;
            }
        }
        this.mAxis.mAxisMinimum = this.mAxis.mEntries[0];
        this.mAxis.mAxisMaximum = this.mAxis.mEntries[n - 1];
        this.mAxis.mAxisRange = Math.abs(this.mAxis.mAxisMaximum - this.mAxis.mAxisMinimum);
    }

    public void renderAxisLabels(Canvas c) {
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            MPPointF center = this.mChart.getCenterOffsets();
            MPPointF pOut = MPPointF.getInstance(0.0f, 0.0f);
            float factor = this.mChart.getFactor();
            int labelCount = this.mYAxis.mEntryCount;
            int j = 0;
            while (j < labelCount && (j != labelCount - 1 || this.mYAxis.isDrawTopYLabelEntryEnabled())) {
                Utils.getPosition(center, (this.mYAxis.mEntries[j] - this.mYAxis.mAxisMinimum) * factor, this.mChart.getRotationAngle(), pOut);
                c.drawText(this.mYAxis.getFormattedLabel(j), pOut.x + 10.0f, pOut.y, this.mAxisLabelPaint);
                j++;
            }
            MPPointF.recycleInstance(center);
            MPPointF.recycleInstance(pOut);
        }
    }

    public void renderLimitLines(Canvas c) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines != null) {
            float sliceangle = this.mChart.getSliceAngle();
            float factor = this.mChart.getFactor();
            MPPointF center = this.mChart.getCenterOffsets();
            MPPointF pOut = MPPointF.getInstance(0.0f, 0.0f);
            for (int i = 0; i < limitLines.size(); i++) {
                LimitLine l = (LimitLine) limitLines.get(i);
                if (l.isEnabled()) {
                    this.mLimitLinePaint.setColor(l.getLineColor());
                    this.mLimitLinePaint.setPathEffect(l.getDashPathEffect());
                    this.mLimitLinePaint.setStrokeWidth(l.getLineWidth());
                    float r = (l.getLimit() - this.mChart.getYChartMin()) * factor;
                    Path limitPath = this.mRenderLimitLinesPathBuffer;
                    limitPath.reset();
                    for (int j = 0; j < ((IRadarDataSet) ((RadarData) this.mChart.getData()).getMaxEntryCountSet()).getEntryCount(); j++) {
                        Utils.getPosition(center, r, (((float) j) * sliceangle) + this.mChart.getRotationAngle(), pOut);
                        if (j == 0) {
                            limitPath.moveTo(pOut.x, pOut.y);
                        } else {
                            limitPath.lineTo(pOut.x, pOut.y);
                        }
                    }
                    limitPath.close();
                    c.drawPath(limitPath, this.mLimitLinePaint);
                }
            }
            MPPointF.recycleInstance(center);
            MPPointF.recycleInstance(pOut);
        }
    }
}
