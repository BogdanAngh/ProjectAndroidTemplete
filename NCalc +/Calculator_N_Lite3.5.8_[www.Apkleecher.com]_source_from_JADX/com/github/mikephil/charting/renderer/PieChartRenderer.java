package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.getkeepsafe.taptargetview.R;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet.ValuePosition;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.List;
import org.apache.commons.math4.distribution.AbstractRealDistribution;

public class PieChartRenderer extends DataRenderer {
    protected Canvas mBitmapCanvas;
    private RectF mCenterTextLastBounds;
    private CharSequence mCenterTextLastValue;
    private StaticLayout mCenterTextLayout;
    private TextPaint mCenterTextPaint;
    protected PieChart mChart;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mDrawCenterTextPathBuffer;
    protected RectF mDrawHighlightedRectF;
    private Paint mEntryLabelsPaint;
    private Path mHoleCirclePath;
    protected Paint mHolePaint;
    private RectF mInnerRectBuffer;
    private Path mPathBuffer;
    private RectF[] mRectBuffer;
    protected Paint mTransparentCirclePaint;
    protected Paint mValueLinePaint;

    public PieChartRenderer(PieChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mCenterTextLastBounds = new RectF();
        this.mRectBuffer = new RectF[]{new RectF(), new RectF(), new RectF()};
        this.mPathBuffer = new Path();
        this.mInnerRectBuffer = new RectF();
        this.mHoleCirclePath = new Path();
        this.mDrawCenterTextPathBuffer = new Path();
        this.mDrawHighlightedRectF = new RectF();
        this.mChart = chart;
        this.mHolePaint = new Paint(1);
        this.mHolePaint.setColor(-1);
        this.mHolePaint.setStyle(Style.FILL);
        this.mTransparentCirclePaint = new Paint(1);
        this.mTransparentCirclePaint.setColor(-1);
        this.mTransparentCirclePaint.setStyle(Style.FILL);
        this.mTransparentCirclePaint.setAlpha(R.styleable.AppCompatTheme_checkedTextViewStyle);
        this.mCenterTextPaint = new TextPaint(1);
        this.mCenterTextPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValuePaint.setColor(-1);
        this.mValuePaint.setTextAlign(Align.CENTER);
        this.mEntryLabelsPaint = new Paint(1);
        this.mEntryLabelsPaint.setColor(-1);
        this.mEntryLabelsPaint.setTextAlign(Align.CENTER);
        this.mEntryLabelsPaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValueLinePaint = new Paint(1);
        this.mValueLinePaint.setStyle(Style.STROKE);
    }

    public Paint getPaintHole() {
        return this.mHolePaint;
    }

    public Paint getPaintTransparentCircle() {
        return this.mTransparentCirclePaint;
    }

    public TextPaint getPaintCenterText() {
        return this.mCenterTextPaint;
    }

    public Paint getPaintEntryLabels() {
        return this.mEntryLabelsPaint;
    }

    public void initBuffers() {
    }

    public void drawData(Canvas c) {
        int width = (int) this.mViewPortHandler.getChartWidth();
        int height = (int) this.mViewPortHandler.getChartHeight();
        if (!(this.mDrawBitmap != null && ((Bitmap) this.mDrawBitmap.get()).getWidth() == width && ((Bitmap) this.mDrawBitmap.get()).getHeight() == height)) {
            if (width > 0 && height > 0) {
                this.mDrawBitmap = new WeakReference(Bitmap.createBitmap(width, height, Config.ARGB_4444));
                this.mBitmapCanvas = new Canvas((Bitmap) this.mDrawBitmap.get());
            } else {
                return;
            }
        }
        ((Bitmap) this.mDrawBitmap.get()).eraseColor(0);
        PieData pieData = (PieData) this.mChart.getData();
        int setCount = pieData.getDataSets().size();
        List<IPieDataSet> dataSet = pieData.getDataSets();
        for (int i = 0; i < setCount; i++) {
            IPieDataSet set = (IPieDataSet) dataSet.get(i);
            if (set.isVisible() && set.getEntryCount() > 0) {
                drawDataSet(c, set);
            }
        }
    }

    protected float calculateMinimumRadiusForSpacedSlice(MPPointF center, float radius, float angle, float arcStartPointX, float arcStartPointY, float startAngle, float sweepAngle) {
        float angleMiddle = startAngle + (sweepAngle / 2.0f);
        float arcEndPointX = center.x + (((float) Math.cos((double) ((startAngle + sweepAngle) * Utils.FDEG2RAD))) * radius);
        float arcEndPointY = center.y + (((float) Math.sin((double) ((startAngle + sweepAngle) * Utils.FDEG2RAD))) * radius);
        return (float) (((double) (radius - ((float) ((Math.sqrt(Math.pow((double) (arcEndPointX - arcStartPointX), 2.0d) + Math.pow((double) (arcEndPointY - arcStartPointY), 2.0d)) / 2.0d) * Math.tan(((180.0d - ((double) angle)) / 2.0d) * Utils.DEG2RAD))))) - Math.sqrt(Math.pow((double) ((center.x + (((float) Math.cos((double) (Utils.FDEG2RAD * angleMiddle))) * radius)) - ((arcEndPointX + arcStartPointX) / 2.0f)), 2.0d) + Math.pow((double) ((center.y + (((float) Math.sin((double) (Utils.FDEG2RAD * angleMiddle))) * radius)) - ((arcEndPointY + arcStartPointY) / 2.0f)), 2.0d)));
    }

    protected float getSliceSpace(IPieDataSet dataSet) {
        return dataSet.getSliceSpace() / this.mViewPortHandler.getSmallestContentExtension() > (dataSet.getYMin() / ((PieData) this.mChart.getData()).getYValueSum()) * 2.0f ? 0.0f : dataSet.getSliceSpace();
    }

    protected void drawDataSet(Canvas c, IPieDataSet dataSet) {
        int j;
        float sliceSpace;
        float angle = 0.0f;
        float rotationAngle = this.mChart.getRotationAngle();
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        RectF circleBox = this.mChart.getCircleBox();
        int entryCount = dataSet.getEntryCount();
        float[] drawAngles = this.mChart.getDrawAngles();
        MPPointF center = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        boolean drawInnerArc = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float userInnerRadius = drawInnerArc ? radius * (this.mChart.getHoleRadius() / 100.0f) : 0.0f;
        int visibleAngleCount = 0;
        for (j = 0; j < entryCount; j++) {
            if (((double) Math.abs(((PieEntry) dataSet.getEntryForIndex(j)).getY())) > AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY) {
                visibleAngleCount++;
            }
        }
        if (visibleAngleCount <= 1) {
            sliceSpace = 0.0f;
        } else {
            sliceSpace = getSliceSpace(dataSet);
        }
        j = 0;
        while (j < entryCount) {
            float sliceAngle = drawAngles[j];
            float innerRadius = userInnerRadius;
            if (((double) Math.abs(dataSet.getEntryForIndex(j).getY())) > AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY && !this.mChart.needsHighlight(j)) {
                float sliceSpaceAngleOuter;
                boolean accountForSliceSpacing = sliceSpace > 0.0f && sliceAngle <= 180.0f;
                this.mRenderPaint.setColor(dataSet.getColor(j));
                if (visibleAngleCount == 1) {
                    sliceSpaceAngleOuter = 0.0f;
                } else {
                    sliceSpaceAngleOuter = sliceSpace / (Utils.FDEG2RAD * radius);
                }
                float startAngleOuter = rotationAngle + (((sliceSpaceAngleOuter / 2.0f) + angle) * phaseY);
                float sweepAngleOuter = (sliceAngle - sliceSpaceAngleOuter) * phaseY;
                if (sweepAngleOuter < 0.0f) {
                    sweepAngleOuter = 0.0f;
                }
                this.mPathBuffer.reset();
                float arcStartPointX = 0.0f;
                float arcStartPointY = 0.0f;
                if (sweepAngleOuter % 360.0f == 0.0f) {
                    this.mPathBuffer.addCircle(center.x, center.y, radius, Direction.CW);
                } else {
                    arcStartPointX = center.x + (((float) Math.cos((double) (Utils.FDEG2RAD * startAngleOuter))) * radius);
                    arcStartPointY = center.y + (((float) Math.sin((double) (Utils.FDEG2RAD * startAngleOuter))) * radius);
                    this.mPathBuffer.moveTo(arcStartPointX, arcStartPointY);
                    this.mPathBuffer.arcTo(circleBox, startAngleOuter, sweepAngleOuter);
                }
                this.mInnerRectBuffer.set(center.x - innerRadius, center.y - innerRadius, center.x + innerRadius, center.y + innerRadius);
                if (drawInnerArc && (innerRadius > 0.0f || accountForSliceSpacing)) {
                    float sliceSpaceAngleInner;
                    if (accountForSliceSpacing) {
                        float minSpacedRadius = calculateMinimumRadiusForSpacedSlice(center, radius, sliceAngle * phaseY, arcStartPointX, arcStartPointY, startAngleOuter, sweepAngleOuter);
                        if (minSpacedRadius < 0.0f) {
                            minSpacedRadius = -minSpacedRadius;
                        }
                        innerRadius = Math.max(innerRadius, minSpacedRadius);
                    }
                    if (visibleAngleCount == 1 || innerRadius == 0.0f) {
                        sliceSpaceAngleInner = 0.0f;
                    } else {
                        sliceSpaceAngleInner = sliceSpace / (Utils.FDEG2RAD * innerRadius);
                    }
                    float startAngleInner = rotationAngle + (((sliceSpaceAngleInner / 2.0f) + angle) * phaseY);
                    float sweepAngleInner = (sliceAngle - sliceSpaceAngleInner) * phaseY;
                    if (sweepAngleInner < 0.0f) {
                        sweepAngleInner = 0.0f;
                    }
                    float endAngleInner = startAngleInner + sweepAngleInner;
                    if (sweepAngleOuter % 360.0f == 0.0f) {
                        this.mPathBuffer.addCircle(center.x, center.y, innerRadius, Direction.CCW);
                    } else {
                        this.mPathBuffer.lineTo(center.x + (((float) Math.cos((double) (Utils.FDEG2RAD * endAngleInner))) * innerRadius), center.y + (((float) Math.sin((double) (Utils.FDEG2RAD * endAngleInner))) * innerRadius));
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, endAngleInner, -sweepAngleInner);
                    }
                } else if (sweepAngleOuter % 360.0f != 0.0f) {
                    if (accountForSliceSpacing) {
                        float angleMiddle = startAngleOuter + (sweepAngleOuter / 2.0f);
                        float sliceSpaceOffset = calculateMinimumRadiusForSpacedSlice(center, radius, sliceAngle * phaseY, arcStartPointX, arcStartPointY, startAngleOuter, sweepAngleOuter);
                        this.mPathBuffer.lineTo(center.x + (((float) Math.cos((double) (Utils.FDEG2RAD * angleMiddle))) * sliceSpaceOffset), center.y + (((float) Math.sin((double) (Utils.FDEG2RAD * angleMiddle))) * sliceSpaceOffset));
                    } else {
                        this.mPathBuffer.lineTo(center.x, center.y);
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            }
            angle += sliceAngle * phaseX;
            j++;
        }
        MPPointF.recycleInstance(center);
    }

    public void drawValues(Canvas c) {
        MPPointF center = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        float rotationAngle = this.mChart.getRotationAngle();
        float[] drawAngles = this.mChart.getDrawAngles();
        float[] absoluteAngles = this.mChart.getAbsoluteAngles();
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float holeRadiusPercent = this.mChart.getHoleRadius() / 100.0f;
        float labelRadiusOffset = (radius / 10.0f) * 3.6f;
        if (this.mChart.isDrawHoleEnabled()) {
            labelRadiusOffset = (radius - (radius * holeRadiusPercent)) / 2.0f;
        }
        float labelRadius = radius - labelRadiusOffset;
        PieData data = (PieData) this.mChart.getData();
        List<IPieDataSet> dataSets = data.getDataSets();
        float yValueSum = data.getYValueSum();
        boolean drawEntryLabels = this.mChart.isDrawEntryLabelsEnabled();
        int xIndex = 0;
        c.save();
        for (int i = 0; i < dataSets.size(); i++) {
            IPieDataSet dataSet = (IPieDataSet) dataSets.get(i);
            boolean drawValues = dataSet.isDrawValuesEnabled();
            if (drawValues || drawEntryLabels) {
                ValuePosition xValuePosition = dataSet.getXValuePosition();
                ValuePosition yValuePosition = dataSet.getYValuePosition();
                applyValueTextStyle(dataSet);
                float lineHeight = ((float) Utils.calcTextHeight(this.mValuePaint, "Q")) + Utils.convertDpToPixel(4.0f);
                ValueFormatter formatter = dataSet.getValueFormatter();
                int entryCount = dataSet.getEntryCount();
                this.mValueLinePaint.setColor(dataSet.getValueLineColor());
                this.mValueLinePaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getValueLineWidth()));
                float offset = Utils.convertDpToPixel(5.0f);
                float sliceSpace = getSliceSpace(dataSet);
                for (int j = 0; j < entryCount; j++) {
                    float angle;
                    float value;
                    Entry entry = (PieEntry) dataSet.getEntryForIndex(j);
                    if (xIndex == 0) {
                        angle = 0.0f;
                    } else {
                        angle = absoluteAngles[xIndex - 1] * phaseX;
                    }
                    float transformedAngle = rotationAngle + ((angle + ((drawAngles[xIndex] - ((sliceSpace / (Utils.FDEG2RAD * labelRadius)) / 2.0f)) / 2.0f)) * phaseY);
                    if (this.mChart.isUsePercentValuesEnabled()) {
                        value = (entry.getY() / yValueSum) * 100.0f;
                    } else {
                        value = entry.getY();
                    }
                    float sliceXBase = (float) Math.cos((double) (Utils.FDEG2RAD * transformedAngle));
                    float sliceYBase = (float) Math.sin((double) (Utils.FDEG2RAD * transformedAngle));
                    boolean drawXOutside = drawEntryLabels && xValuePosition == ValuePosition.OUTSIDE_SLICE;
                    boolean drawYOutside = drawValues && yValuePosition == ValuePosition.OUTSIDE_SLICE;
                    boolean drawXInside = drawEntryLabels && xValuePosition == ValuePosition.INSIDE_SLICE;
                    boolean drawYInside = drawValues && yValuePosition == ValuePosition.INSIDE_SLICE;
                    if (drawXOutside || drawYOutside) {
                        float line1Radius;
                        float pt2x;
                        float pt2y;
                        float labelPtx;
                        float labelPty;
                        float valueLineLength1 = dataSet.getValueLinePart1Length();
                        float valueLineLength2 = dataSet.getValueLinePart2Length();
                        float valueLinePart1OffsetPercentage = dataSet.getValueLinePart1OffsetPercentage() / 100.0f;
                        if (this.mChart.isDrawHoleEnabled()) {
                            line1Radius = ((radius - (radius * holeRadiusPercent)) * valueLinePart1OffsetPercentage) + (radius * holeRadiusPercent);
                        } else {
                            line1Radius = radius * valueLinePart1OffsetPercentage;
                        }
                        float polyline2Width = dataSet.isValueLineVariableLength() ? (labelRadius * valueLineLength2) * ((float) Math.abs(Math.sin((double) (Utils.FDEG2RAD * transformedAngle)))) : labelRadius * valueLineLength2;
                        float pt0x = (line1Radius * sliceXBase) + center.x;
                        float pt0y = (line1Radius * sliceYBase) + center.y;
                        float pt1x = (((1.0f + valueLineLength1) * labelRadius) * sliceXBase) + center.x;
                        float pt1y = (((1.0f + valueLineLength1) * labelRadius) * sliceYBase) + center.y;
                        if (((double) transformedAngle) % 360.0d < 90.0d || ((double) transformedAngle) % 360.0d > 270.0d) {
                            pt2x = pt1x + polyline2Width;
                            pt2y = pt1y;
                            this.mValuePaint.setTextAlign(Align.LEFT);
                            labelPtx = pt2x + offset;
                            labelPty = pt2y;
                        } else {
                            pt2x = pt1x - polyline2Width;
                            pt2y = pt1y;
                            this.mValuePaint.setTextAlign(Align.RIGHT);
                            labelPtx = pt2x - offset;
                            labelPty = pt2y;
                        }
                        if (dataSet.getValueLineColor() != ColorTemplate.COLOR_NONE) {
                            c.drawLine(pt0x, pt0y, pt1x, pt1y, this.mValueLinePaint);
                            c.drawLine(pt1x, pt1y, pt2x, pt2y, this.mValueLinePaint);
                        }
                        if (drawXOutside && drawYOutside) {
                            drawValue(c, formatter, value, entry, 0, labelPtx, labelPty, dataSet.getValueTextColor(j));
                            if (j < data.getEntryCount() && entry.getLabel() != null) {
                                drawEntryLabel(c, entry.getLabel(), labelPtx, labelPty + lineHeight);
                            }
                        } else if (drawXOutside) {
                            if (j < data.getEntryCount() && entry.getLabel() != null) {
                                drawEntryLabel(c, entry.getLabel(), labelPtx, (lineHeight / 2.0f) + labelPty);
                            }
                        } else if (drawYOutside) {
                            drawValue(c, formatter, value, entry, 0, labelPtx, labelPty + (lineHeight / 2.0f), dataSet.getValueTextColor(j));
                        }
                    }
                    if (drawXInside || drawYInside) {
                        float x = (labelRadius * sliceXBase) + center.x;
                        float y = (labelRadius * sliceYBase) + center.y;
                        this.mValuePaint.setTextAlign(Align.CENTER);
                        if (drawXInside && drawYInside) {
                            drawValue(c, formatter, value, entry, 0, x, y, dataSet.getValueTextColor(j));
                            if (j < data.getEntryCount() && entry.getLabel() != null) {
                                drawEntryLabel(c, entry.getLabel(), x, y + lineHeight);
                            }
                        } else if (drawXInside) {
                            if (j < data.getEntryCount() && entry.getLabel() != null) {
                                drawEntryLabel(c, entry.getLabel(), x, (lineHeight / 2.0f) + y);
                            }
                        } else if (drawYInside) {
                            drawValue(c, formatter, value, entry, 0, x, y + (lineHeight / 2.0f), dataSet.getValueTextColor(j));
                        }
                    }
                    xIndex++;
                }
            }
        }
        MPPointF.recycleInstance(center);
        c.restore();
    }

    protected void drawEntryLabel(Canvas c, String label, float x, float y) {
        c.drawText(label, x, y, this.mEntryLabelsPaint);
    }

    public void drawExtras(Canvas c) {
        drawHole(c);
        c.drawBitmap((Bitmap) this.mDrawBitmap.get(), 0.0f, 0.0f, null);
        drawCenterText(c);
    }

    protected void drawHole(Canvas c) {
        if (this.mChart.isDrawHoleEnabled() && this.mBitmapCanvas != null) {
            float radius = this.mChart.getRadius();
            float holeRadius = radius * (this.mChart.getHoleRadius() / 100.0f);
            MPPointF center = this.mChart.getCenterCircleBox();
            if (Color.alpha(this.mHolePaint.getColor()) > 0) {
                this.mBitmapCanvas.drawCircle(center.x, center.y, holeRadius, this.mHolePaint);
            }
            if (Color.alpha(this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
                int alpha = this.mTransparentCirclePaint.getAlpha();
                float secondHoleRadius = radius * (this.mChart.getTransparentCircleRadius() / 100.0f);
                this.mTransparentCirclePaint.setAlpha((int) ((((float) alpha) * this.mAnimator.getPhaseX()) * this.mAnimator.getPhaseY()));
                this.mHoleCirclePath.reset();
                this.mHoleCirclePath.addCircle(center.x, center.y, secondHoleRadius, Direction.CW);
                this.mHoleCirclePath.addCircle(center.x, center.y, holeRadius, Direction.CCW);
                this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
                this.mTransparentCirclePaint.setAlpha(alpha);
            }
            MPPointF.recycleInstance(center);
        }
    }

    protected void drawCenterText(Canvas c) {
        CharSequence centerText = this.mChart.getCenterText();
        if (this.mChart.isDrawCenterTextEnabled() && centerText != null) {
            float innerRadius;
            MPPointF center = this.mChart.getCenterCircleBox();
            MPPointF offset = this.mChart.getCenterTextOffset();
            float x = center.x + offset.x;
            float y = center.y + offset.y;
            if (!this.mChart.isDrawHoleEnabled() || this.mChart.isDrawSlicesUnderHoleEnabled()) {
                innerRadius = this.mChart.getRadius();
            } else {
                innerRadius = this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0f);
            }
            RectF holeRect = this.mRectBuffer[0];
            holeRect.left = x - innerRadius;
            holeRect.top = y - innerRadius;
            holeRect.right = x + innerRadius;
            holeRect.bottom = y + innerRadius;
            RectF boundingRect = this.mRectBuffer[1];
            boundingRect.set(holeRect);
            float radiusPercent = this.mChart.getCenterTextRadiusPercent() / 100.0f;
            if (((double) radiusPercent) > 0.0d) {
                boundingRect.inset((boundingRect.width() - (boundingRect.width() * radiusPercent)) / 2.0f, (boundingRect.height() - (boundingRect.height() * radiusPercent)) / 2.0f);
            }
            if (!(centerText.equals(this.mCenterTextLastValue) && boundingRect.equals(this.mCenterTextLastBounds))) {
                this.mCenterTextLastBounds.set(boundingRect);
                this.mCenterTextLastValue = centerText;
                this.mCenterTextLayout = new StaticLayout(centerText, 0, centerText.length(), this.mCenterTextPaint, (int) Math.max(Math.ceil((double) this.mCenterTextLastBounds.width()), 1.0d), Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            }
            float layoutHeight = (float) this.mCenterTextLayout.getHeight();
            c.save();
            if (VERSION.SDK_INT >= 18) {
                Path path = this.mDrawCenterTextPathBuffer;
                path.reset();
                path.addOval(holeRect, Direction.CW);
                c.clipPath(path);
            }
            c.translate(boundingRect.left, boundingRect.top + ((boundingRect.height() - layoutHeight) / 2.0f));
            this.mCenterTextLayout.draw(c);
            c.restore();
            MPPointF.recycleInstance(center);
            MPPointF.recycleInstance(offset);
        }
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float rotationAngle = this.mChart.getRotationAngle();
        float[] drawAngles = this.mChart.getDrawAngles();
        float[] absoluteAngles = this.mChart.getAbsoluteAngles();
        MPPointF center = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        boolean drawInnerArc = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float userInnerRadius = drawInnerArc ? radius * (this.mChart.getHoleRadius() / 100.0f) : 0.0f;
        RectF highlightedCircleBox = this.mDrawHighlightedRectF;
        highlightedCircleBox.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int i = 0; i < indices.length; i++) {
            int index = (int) indices[i].getX();
            if (index < drawAngles.length) {
                IPieDataSet set = ((PieData) this.mChart.getData()).getDataSetByIndex(indices[i].getDataSetIndex());
                if (set != null && set.isHighlightEnabled()) {
                    float angle;
                    float sliceSpaceAngleShifted;
                    int entryCount = set.getEntryCount();
                    int visibleAngleCount = 0;
                    for (int j = 0; j < entryCount; j++) {
                        if (((double) Math.abs(((PieEntry) set.getEntryForIndex(j)).getY())) > AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY) {
                            visibleAngleCount++;
                        }
                    }
                    if (index == 0) {
                        angle = 0.0f;
                    } else {
                        angle = absoluteAngles[index - 1] * phaseX;
                    }
                    float sliceSpace = visibleAngleCount <= 1 ? 0.0f : set.getSliceSpace();
                    float sliceAngle = drawAngles[index];
                    float innerRadius = userInnerRadius;
                    float shift = set.getSelectionShift();
                    float highlightedRadius = radius + shift;
                    highlightedCircleBox.set(this.mChart.getCircleBox());
                    highlightedCircleBox.inset(-shift, -shift);
                    boolean accountForSliceSpacing = sliceSpace > 0.0f && sliceAngle <= 180.0f;
                    this.mRenderPaint.setColor(set.getColor(index));
                    float sliceSpaceAngleOuter = visibleAngleCount == 1 ? 0.0f : sliceSpace / (Utils.FDEG2RAD * radius);
                    if (visibleAngleCount == 1) {
                        sliceSpaceAngleShifted = 0.0f;
                    } else {
                        sliceSpaceAngleShifted = sliceSpace / (Utils.FDEG2RAD * highlightedRadius);
                    }
                    float startAngleOuter = rotationAngle + (((sliceSpaceAngleOuter / 2.0f) + angle) * phaseY);
                    float sweepAngleOuter = (sliceAngle - sliceSpaceAngleOuter) * phaseY;
                    if (sweepAngleOuter < 0.0f) {
                        sweepAngleOuter = 0.0f;
                    }
                    float startAngleShifted = rotationAngle + (((sliceSpaceAngleShifted / 2.0f) + angle) * phaseY);
                    float sweepAngleShifted = (sliceAngle - sliceSpaceAngleShifted) * phaseY;
                    if (sweepAngleShifted < 0.0f) {
                        sweepAngleShifted = 0.0f;
                    }
                    this.mPathBuffer.reset();
                    if (sweepAngleOuter % 360.0f == 0.0f) {
                        this.mPathBuffer.addCircle(center.x, center.y, highlightedRadius, Direction.CW);
                    } else {
                        this.mPathBuffer.moveTo(center.x + (((float) Math.cos((double) (Utils.FDEG2RAD * startAngleShifted))) * highlightedRadius), center.y + (((float) Math.sin((double) (Utils.FDEG2RAD * startAngleShifted))) * highlightedRadius));
                        this.mPathBuffer.arcTo(highlightedCircleBox, startAngleShifted, sweepAngleShifted);
                    }
                    float sliceSpaceRadius = 0.0f;
                    if (accountForSliceSpacing) {
                        sliceSpaceRadius = calculateMinimumRadiusForSpacedSlice(center, radius, sliceAngle * phaseY, (((float) Math.cos((double) (Utils.FDEG2RAD * startAngleOuter))) * radius) + center.x, (((float) Math.sin((double) (Utils.FDEG2RAD * startAngleOuter))) * radius) + center.y, startAngleOuter, sweepAngleOuter);
                    }
                    this.mInnerRectBuffer.set(center.x - innerRadius, center.y - innerRadius, center.x + innerRadius, center.y + innerRadius);
                    if (drawInnerArc && (innerRadius > 0.0f || accountForSliceSpacing)) {
                        float sliceSpaceAngleInner;
                        if (accountForSliceSpacing) {
                            float minSpacedRadius = sliceSpaceRadius;
                            if (minSpacedRadius < 0.0f) {
                                minSpacedRadius = -minSpacedRadius;
                            }
                            innerRadius = Math.max(innerRadius, minSpacedRadius);
                        }
                        if (visibleAngleCount == 1 || innerRadius == 0.0f) {
                            sliceSpaceAngleInner = 0.0f;
                        } else {
                            sliceSpaceAngleInner = sliceSpace / (Utils.FDEG2RAD * innerRadius);
                        }
                        float startAngleInner = rotationAngle + (((sliceSpaceAngleInner / 2.0f) + angle) * phaseY);
                        float sweepAngleInner = (sliceAngle - sliceSpaceAngleInner) * phaseY;
                        if (sweepAngleInner < 0.0f) {
                            sweepAngleInner = 0.0f;
                        }
                        float endAngleInner = startAngleInner + sweepAngleInner;
                        if (sweepAngleOuter % 360.0f == 0.0f) {
                            this.mPathBuffer.addCircle(center.x, center.y, innerRadius, Direction.CCW);
                        } else {
                            this.mPathBuffer.lineTo(center.x + (((float) Math.cos((double) (Utils.FDEG2RAD * endAngleInner))) * innerRadius), center.y + (((float) Math.sin((double) (Utils.FDEG2RAD * endAngleInner))) * innerRadius));
                            this.mPathBuffer.arcTo(this.mInnerRectBuffer, endAngleInner, -sweepAngleInner);
                        }
                    } else if (sweepAngleOuter % 360.0f != 0.0f) {
                        if (accountForSliceSpacing) {
                            float angleMiddle = startAngleOuter + (sweepAngleOuter / 2.0f);
                            this.mPathBuffer.lineTo(center.x + (((float) Math.cos((double) (Utils.FDEG2RAD * angleMiddle))) * sliceSpaceRadius), center.y + (((float) Math.sin((double) (Utils.FDEG2RAD * angleMiddle))) * sliceSpaceRadius));
                        } else {
                            this.mPathBuffer.lineTo(center.x, center.y);
                        }
                    }
                    this.mPathBuffer.close();
                    this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
                }
            }
        }
        MPPointF.recycleInstance(center);
    }

    protected void drawRoundedSlices(Canvas c) {
        if (this.mChart.isDrawRoundedSlicesEnabled()) {
            IPieDataSet dataSet = ((PieData) this.mChart.getData()).getDataSet();
            if (dataSet.isVisible()) {
                float phaseX = this.mAnimator.getPhaseX();
                float phaseY = this.mAnimator.getPhaseY();
                MPPointF center = this.mChart.getCenterCircleBox();
                float r = this.mChart.getRadius();
                float circleRadius = (r - ((this.mChart.getHoleRadius() * r) / 100.0f)) / 2.0f;
                float[] drawAngles = this.mChart.getDrawAngles();
                float angle = this.mChart.getRotationAngle();
                for (int j = 0; j < dataSet.getEntryCount(); j++) {
                    float sliceAngle = drawAngles[j];
                    if (((double) Math.abs(dataSet.getEntryForIndex(j).getY())) > AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY) {
                        double d = (double) center.x;
                        float x = (float) ((((double) (r - circleRadius)) * Math.cos(Math.toRadians((double) ((angle + sliceAngle) * phaseY)))) + r0);
                        d = (double) center.y;
                        float y = (float) ((((double) (r - circleRadius)) * Math.sin(Math.toRadians((double) ((angle + sliceAngle) * phaseY)))) + r0);
                        this.mRenderPaint.setColor(dataSet.getColor(j));
                        this.mBitmapCanvas.drawCircle(x, y, circleRadius, this.mRenderPaint);
                    }
                    angle += sliceAngle * phaseX;
                }
                MPPointF.recycleInstance(center);
            }
        }
    }

    public void releaseBitmap() {
        if (this.mBitmapCanvas != null) {
            this.mBitmapCanvas.setBitmap(null);
            this.mBitmapCanvas = null;
        }
        if (this.mDrawBitmap != null) {
            ((Bitmap) this.mDrawBitmap.get()).recycle();
            this.mDrawBitmap.clear();
            this.mDrawBitmap = null;
        }
    }
}
