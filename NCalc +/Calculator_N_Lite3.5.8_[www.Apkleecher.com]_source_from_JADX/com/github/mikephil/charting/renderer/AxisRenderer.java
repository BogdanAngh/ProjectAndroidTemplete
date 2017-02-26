package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewCompat;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;

public abstract class AxisRenderer extends Renderer {
    protected AxisBase mAxis;
    protected Paint mAxisLabelPaint;
    protected Paint mAxisLinePaint;
    protected Paint mGridPaint;
    protected Paint mLimitLinePaint;
    protected Transformer mTrans;

    public abstract void renderAxisLabels(Canvas canvas);

    public abstract void renderAxisLine(Canvas canvas);

    public abstract void renderGridLines(Canvas canvas);

    public abstract void renderLimitLines(Canvas canvas);

    public AxisRenderer(ViewPortHandler viewPortHandler, Transformer trans, AxisBase axis) {
        super(viewPortHandler);
        this.mTrans = trans;
        this.mAxis = axis;
        if (this.mViewPortHandler != null) {
            this.mAxisLabelPaint = new Paint(1);
            this.mGridPaint = new Paint();
            this.mGridPaint.setColor(-7829368);
            this.mGridPaint.setStrokeWidth(1.0f);
            this.mGridPaint.setStyle(Style.STROKE);
            this.mGridPaint.setAlpha(90);
            this.mAxisLinePaint = new Paint();
            this.mAxisLinePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.mAxisLinePaint.setStrokeWidth(1.0f);
            this.mAxisLinePaint.setStyle(Style.STROKE);
            this.mLimitLinePaint = new Paint(1);
            this.mLimitLinePaint.setStyle(Style.STROKE);
        }
    }

    public Paint getPaintAxisLabels() {
        return this.mAxisLabelPaint;
    }

    public Paint getPaintGrid() {
        return this.mGridPaint;
    }

    public Paint getPaintAxisLine() {
        return this.mAxisLinePaint;
    }

    public Transformer getTransformer() {
        return this.mTrans;
    }

    public void computeAxis(float min, float max, boolean inverted) {
        if (!(this.mViewPortHandler == null || this.mViewPortHandler.contentWidth() <= 10.0f || this.mViewPortHandler.isFullyZoomedOutY())) {
            MPPointD p1 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            MPPointD p2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
            if (inverted) {
                min = (float) p1.y;
                max = (float) p2.y;
            } else {
                min = (float) p2.y;
                max = (float) p1.y;
            }
            MPPointD.recycleInstance(p1);
            MPPointD.recycleInstance(p2);
        }
        computeAxisValues(min, max);
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
    }
}
