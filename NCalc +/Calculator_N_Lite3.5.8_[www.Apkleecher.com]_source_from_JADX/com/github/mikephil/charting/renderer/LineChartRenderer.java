package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet.Mode;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class LineChartRenderer extends LineRadarRenderer {
    protected Path cubicFillPath;
    protected Path cubicPath;
    protected Canvas mBitmapCanvas;
    protected Config mBitmapConfig;
    protected LineDataProvider mChart;
    protected Paint mCirclePaintInner;
    private Path mCirclePathBuffer;
    private float[] mCirclesBuffer;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mGenerateFilledPathBuffer;
    private HashMap<IDataSet, DataSetImageCache> mImageCaches;
    private float[] mLineBuffer;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode;

        static {
            $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode = new int[Mode.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[Mode.LINEAR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[Mode.STEPPED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[Mode.CUBIC_BEZIER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[Mode.HORIZONTAL_BEZIER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private class DataSetImageCache {
        private Bitmap[] circleBitmaps;
        private int[] circleColors;

        private DataSetImageCache() {
        }

        private void ensureCircleCache(int size) {
            int i;
            if (this.circleBitmaps == null) {
                this.circleBitmaps = new Bitmap[size];
            } else if (this.circleBitmaps.length < size) {
                Bitmap[] tmp = new Bitmap[size];
                for (i = 0; i < this.circleBitmaps.length; i++) {
                    tmp[i] = this.circleBitmaps[size];
                }
                this.circleBitmaps = tmp;
            }
            if (this.circleColors == null) {
                this.circleColors = new int[size];
            } else if (this.circleColors.length < size) {
                int[] tmp2 = new int[size];
                for (i = 0; i < this.circleColors.length; i++) {
                    tmp2[i] = this.circleColors[size];
                }
                this.circleColors = tmp2;
            }
        }
    }

    public LineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mBitmapConfig = Config.ARGB_8888;
        this.cubicPath = new Path();
        this.cubicFillPath = new Path();
        this.mLineBuffer = new float[4];
        this.mGenerateFilledPathBuffer = new Path();
        this.mCirclePathBuffer = new Path();
        this.mCirclesBuffer = new float[2];
        this.mImageCaches = new HashMap();
        this.mChart = chart;
        this.mCirclePaintInner = new Paint(1);
        this.mCirclePaintInner.setStyle(Style.FILL);
        this.mCirclePaintInner.setColor(-1);
    }

    public void initBuffers() {
    }

    public void drawData(Canvas c) {
        int width = (int) this.mViewPortHandler.getChartWidth();
        int height = (int) this.mViewPortHandler.getChartHeight();
        if (!(this.mDrawBitmap != null && ((Bitmap) this.mDrawBitmap.get()).getWidth() == width && ((Bitmap) this.mDrawBitmap.get()).getHeight() == height)) {
            if (width > 0 && height > 0) {
                this.mDrawBitmap = new WeakReference(Bitmap.createBitmap(width, height, this.mBitmapConfig));
                this.mBitmapCanvas = new Canvas((Bitmap) this.mDrawBitmap.get());
            } else {
                return;
            }
        }
        ((Bitmap) this.mDrawBitmap.get()).eraseColor(0);
        LineData lineData = this.mChart.getLineData();
        int setCount = lineData.getDataSets().size();
        for (int i = 0; i < setCount; i++) {
            ILineDataSet set = (ILineDataSet) lineData.getDataSets().get(i);
            if (set.isVisible() && set.getEntryCount() > 0) {
                drawDataSet(c, set);
            }
        }
        c.drawBitmap((Bitmap) this.mDrawBitmap.get(), 0.0f, 0.0f, this.mRenderPaint);
    }

    protected void drawDataSet(Canvas c, ILineDataSet dataSet) {
        if (dataSet.getEntryCount() >= 1) {
            this.mRenderPaint.setStrokeWidth(dataSet.getLineWidth());
            this.mRenderPaint.setPathEffect(dataSet.getDashPathEffect());
            switch (1.$SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[dataSet.getMode().ordinal()]) {
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    drawCubicBezier(dataSet);
                    break;
                case IExpr.DOUBLECOMPLEXID /*4*/:
                    drawHorizontalBezier(dataSet);
                    break;
                default:
                    drawLinear(c, dataSet);
                    break;
            }
            this.mRenderPaint.setPathEffect(null);
        }
    }

    protected void drawHorizontalBezier(ILineDataSet dataSet) {
        float phaseY = this.mAnimator.getPhaseY();
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, dataSet);
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            Entry cur = dataSet.getEntryForIndex(this.mXBounds.min);
            this.cubicPath.moveTo(cur.getX(), cur.getY() * phaseY);
            for (int j = this.mXBounds.min + 1; j <= this.mXBounds.range + this.mXBounds.min; j++) {
                Entry prev = dataSet.getEntryForIndex(j - 1);
                cur = dataSet.getEntryForIndex(j);
                float cpx = prev.getX() + ((cur.getX() - prev.getX()) / 2.0f);
                this.cubicPath.cubicTo(cpx, prev.getY() * phaseY, cpx, cur.getY() * phaseY, cur.getX(), cur.getY() * phaseY);
            }
        }
        if (dataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, dataSet, this.cubicFillPath, trans, this.mXBounds);
        }
        this.mRenderPaint.setColor(dataSet.getColor());
        this.mRenderPaint.setStyle(Style.STROKE);
        trans.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    protected void drawCubicBezier(ILineDataSet dataSet) {
        float phaseX = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, dataSet);
        float intensity = dataSet.getCubicIntensity();
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            Entry cur;
            Entry prev = dataSet.getEntryForIndex(this.mXBounds.min);
            Entry next = dataSet.getEntryForIndex(this.mXBounds.min + 1);
            this.cubicPath.moveTo(cur.getX(), cur.getY() * phaseY);
            int j = this.mXBounds.min + 1;
            while (j <= this.mXBounds.range + this.mXBounds.min) {
                Entry prevPrev = dataSet.getEntryForIndex(j == 1 ? 0 : j - 2);
                prev = dataSet.getEntryForIndex(j - 1);
                cur = dataSet.getEntryForIndex(j);
                if (this.mXBounds.max > j + 1) {
                    next = dataSet.getEntryForIndex(j + 1);
                } else {
                    next = cur;
                }
                this.cubicPath.cubicTo(prev.getX() + ((cur.getX() - prevPrev.getX()) * intensity), (prev.getY() + ((cur.getY() - prevPrev.getY()) * intensity)) * phaseY, cur.getX() - ((next.getX() - prev.getX()) * intensity), (cur.getY() - ((next.getY() - prev.getY()) * intensity)) * phaseY, cur.getX(), cur.getY() * phaseY);
                j++;
            }
        }
        if (dataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, dataSet, this.cubicFillPath, trans, this.mXBounds);
        }
        this.mRenderPaint.setColor(dataSet.getColor());
        this.mRenderPaint.setStyle(Style.STROKE);
        trans.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    protected void drawCubicFill(Canvas c, ILineDataSet dataSet, Path spline, Transformer trans, XBounds bounds) {
        float fillMin = dataSet.getFillFormatter().getFillLinePosition(dataSet, this.mChart);
        spline.lineTo((float) (bounds.min + bounds.range), fillMin);
        spline.lineTo((float) bounds.min, fillMin);
        spline.close();
        trans.pathValueToPixel(spline);
        Drawable drawable = dataSet.getFillDrawable();
        if (drawable != null) {
            drawFilledPath(c, spline, drawable);
        } else {
            drawFilledPath(c, spline, dataSet.getFillColor(), dataSet.getFillAlpha());
        }
    }

    protected void drawLinear(Canvas c, ILineDataSet dataSet) {
        Canvas canvas;
        int entryCount = dataSet.getEntryCount();
        boolean isDrawSteppedEnabled = dataSet.isDrawSteppedEnabled();
        int pointsPerEntryPair = isDrawSteppedEnabled ? 4 : 2;
        Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        this.mRenderPaint.setStyle(Style.STROKE);
        if (dataSet.isDashedLineEnabled()) {
            canvas = this.mBitmapCanvas;
        } else {
            canvas = c;
        }
        this.mXBounds.set(this.mChart, dataSet);
        int j;
        if (dataSet.getColors().size() > 1) {
            int length = this.mLineBuffer.length;
            if (r0 <= pointsPerEntryPair * 2) {
                this.mLineBuffer = new float[(pointsPerEntryPair * 4)];
            }
            j = this.mXBounds.min;
            while (true) {
                if (j > this.mXBounds.range + this.mXBounds.min) {
                    break;
                }
                Entry e = dataSet.getEntryForIndex(j);
                if (e != null) {
                    this.mLineBuffer[0] = e.getX();
                    this.mLineBuffer[1] = e.getY() * phaseY;
                    length = this.mXBounds.max;
                    if (j < r0) {
                        e = dataSet.getEntryForIndex(j + 1);
                        if (e == null) {
                            break;
                        } else if (isDrawSteppedEnabled) {
                            this.mLineBuffer[2] = e.getX();
                            this.mLineBuffer[3] = this.mLineBuffer[1];
                            this.mLineBuffer[4] = this.mLineBuffer[2];
                            this.mLineBuffer[5] = this.mLineBuffer[3];
                            this.mLineBuffer[6] = e.getX();
                            this.mLineBuffer[7] = e.getY() * phaseY;
                        } else {
                            this.mLineBuffer[2] = e.getX();
                            this.mLineBuffer[3] = e.getY() * phaseY;
                        }
                    } else {
                        this.mLineBuffer[2] = this.mLineBuffer[0];
                        this.mLineBuffer[3] = this.mLineBuffer[1];
                    }
                    trans.pointValuesToPixel(this.mLineBuffer);
                    if (!this.mViewPortHandler.isInBoundsRight(this.mLineBuffer[0])) {
                        break;
                    }
                    if (this.mViewPortHandler.isInBoundsLeft(this.mLineBuffer[2])) {
                        if (!this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1])) {
                            if (!this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3])) {
                            }
                        }
                        if (!this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1])) {
                            if (!this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3])) {
                            }
                        }
                        this.mRenderPaint.setColor(dataSet.getColor(j));
                        canvas.drawLines(this.mLineBuffer, 0, pointsPerEntryPair * 2, this.mRenderPaint);
                    }
                }
                j++;
            }
        } else {
            if (this.mLineBuffer.length < Math.max(entryCount * pointsPerEntryPair, pointsPerEntryPair) * 2) {
                this.mLineBuffer = new float[(Math.max(entryCount * pointsPerEntryPair, pointsPerEntryPair) * 4)];
            }
            if (dataSet.getEntryForIndex(this.mXBounds.min) != null) {
                j = 0;
                int x = this.mXBounds.min;
                while (true) {
                    if (x > this.mXBounds.range + this.mXBounds.min) {
                        break;
                    }
                    Entry e1 = dataSet.getEntryForIndex(x == 0 ? 0 : x - 1);
                    Entry e2 = dataSet.getEntryForIndex(x);
                    if (!(e1 == null || e2 == null)) {
                        int i = j + 1;
                        this.mLineBuffer[j] = e1.getX();
                        j = i + 1;
                        this.mLineBuffer[i] = e1.getY() * phaseY;
                        if (isDrawSteppedEnabled) {
                            i = j + 1;
                            this.mLineBuffer[j] = e2.getX();
                            j = i + 1;
                            this.mLineBuffer[i] = e1.getY() * phaseY;
                            i = j + 1;
                            this.mLineBuffer[j] = e2.getX();
                            j = i + 1;
                            this.mLineBuffer[i] = e1.getY() * phaseY;
                        }
                        i = j + 1;
                        this.mLineBuffer[j] = e2.getX();
                        j = i + 1;
                        this.mLineBuffer[i] = e2.getY() * phaseY;
                    }
                    x++;
                }
                if (j > 0) {
                    trans.pointValuesToPixel(this.mLineBuffer);
                    int size = Math.max((this.mXBounds.range + 1) * pointsPerEntryPair, pointsPerEntryPair) * 2;
                    this.mRenderPaint.setColor(dataSet.getColor());
                    canvas.drawLines(this.mLineBuffer, 0, size, this.mRenderPaint);
                }
            }
        }
        this.mRenderPaint.setPathEffect(null);
        if (dataSet.isDrawFilledEnabled() && entryCount > 0) {
            drawLinearFill(c, dataSet, trans, this.mXBounds);
        }
    }

    protected void drawLinearFill(Canvas c, ILineDataSet dataSet, Transformer trans, XBounds bounds) {
        Path filled = this.mGenerateFilledPathBuffer;
        int startingIndex = bounds.min;
        int endingIndex = bounds.range + bounds.min;
        int iterations = 0;
        int currentStartIndex;
        int currentEndIndex;
        do {
            currentStartIndex = startingIndex + (iterations * IExpr.SYMBOLID);
            currentEndIndex = currentStartIndex + IExpr.SYMBOLID;
            if (currentEndIndex > endingIndex) {
                currentEndIndex = endingIndex;
            }
            if (currentStartIndex <= currentEndIndex) {
                generateFilledPath(dataSet, currentStartIndex, currentEndIndex, filled);
                trans.pathValueToPixel(filled);
                Drawable drawable = dataSet.getFillDrawable();
                if (drawable != null) {
                    drawFilledPath(c, filled, drawable);
                } else {
                    drawFilledPath(c, filled, dataSet.getFillColor(), dataSet.getFillAlpha());
                }
            }
            iterations++;
        } while (currentStartIndex <= currentEndIndex);
    }

    private void generateFilledPath(ILineDataSet dataSet, int startIndex, int endIndex, Path outputPath) {
        float fillMin = dataSet.getFillFormatter().getFillLinePosition(dataSet, this.mChart);
        float phaseY = this.mAnimator.getPhaseY();
        boolean isDrawSteppedEnabled = dataSet.getMode() == Mode.STEPPED;
        Path filled = outputPath;
        filled.reset();
        Entry entry = dataSet.getEntryForIndex(startIndex);
        filled.moveTo(entry.getX(), fillMin);
        filled.lineTo(entry.getX(), entry.getY() * phaseY);
        Entry currentEntry = null;
        Entry previousEntry = null;
        for (int x = startIndex + 1; x <= endIndex; x++) {
            currentEntry = dataSet.getEntryForIndex(x);
            if (isDrawSteppedEnabled && previousEntry != null) {
                filled.lineTo(currentEntry.getX(), previousEntry.getY() * phaseY);
            }
            filled.lineTo(currentEntry.getX(), currentEntry.getY() * phaseY);
            previousEntry = currentEntry;
        }
        if (currentEntry != null) {
            filled.lineTo(currentEntry.getX(), fillMin);
        }
        filled.close();
    }

    public void drawValues(Canvas c) {
        if (isDrawingValuesAllowed(this.mChart)) {
            List<ILineDataSet> dataSets = this.mChart.getLineData().getDataSets();
            for (int i = 0; i < dataSets.size(); i++) {
                ILineDataSet dataSet = (ILineDataSet) dataSets.get(i);
                if (dataSet.isDrawValuesEnabled() && dataSet.getEntryCount() != 0) {
                    applyValueTextStyle(dataSet);
                    Transformer trans = this.mChart.getTransformer(dataSet.getAxisDependency());
                    int valOffset = (int) (dataSet.getCircleRadius() * 1.75f);
                    if (!dataSet.isDrawCirclesEnabled()) {
                        valOffset /= 2;
                    }
                    this.mXBounds.set(this.mChart, dataSet);
                    float[] positions = trans.generateTransformedValuesLine(dataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
                    for (int j = 0; j < positions.length; j += 2) {
                        float x = positions[j];
                        float y = positions[j + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(x)) {
                            break;
                        }
                        if (this.mViewPortHandler.isInBoundsLeft(x) && this.mViewPortHandler.isInBoundsY(y)) {
                            Entry entry = dataSet.getEntryForIndex((j / 2) + this.mXBounds.min);
                            drawValue(c, dataSet.getValueFormatter(), entry.getY(), entry, i, x, y - ((float) valOffset), dataSet.getValueTextColor(j / 2));
                        }
                    }
                }
            }
        }
    }

    public void drawExtras(Canvas c) {
        drawCircles(c);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void drawCircles(android.graphics.Canvas r31) {
        /*
        r30 = this;
        r0 = r30;
        r0 = r0.mRenderPaint;
        r24 = r0;
        r25 = android.graphics.Paint.Style.FILL;
        r24.setStyle(r25);
        r0 = r30;
        r0 = r0.mAnimator;
        r24 = r0;
        r20 = r24.getPhaseY();
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r24 = r0;
        r25 = 0;
        r26 = 0;
        r24[r25] = r26;
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r24 = r0;
        r25 = 1;
        r26 = 0;
        r24[r25] = r26;
        r0 = r30;
        r0 = r0.mChart;
        r24 = r0;
        r24 = r24.getLineData();
        r13 = r24.getDataSets();
        r17 = 0;
    L_0x003d:
        r24 = r13.size();
        r0 = r17;
        r1 = r24;
        if (r0 >= r1) goto L_0x0296;
    L_0x0047:
        r0 = r17;
        r12 = r13.get(r0);
        r12 = (com.github.mikephil.charting.interfaces.datasets.ILineDataSet) r12;
        r0 = r30;
        r0 = r0.mImageCaches;
        r24 = r0;
        r0 = r24;
        r24 = r0.containsKey(r12);
        if (r24 == 0) goto L_0x008b;
    L_0x005d:
        r0 = r30;
        r0 = r0.mImageCaches;
        r24 = r0;
        r0 = r24;
        r18 = r0.get(r12);
        r18 = (com.github.mikephil.charting.renderer.LineChartRenderer.DataSetImageCache) r18;
    L_0x006b:
        r24 = r12.getCircleColorCount();
        r0 = r18;
        r1 = r24;
        r0.ensureCircleCache(r1);
        r24 = r12.isVisible();
        if (r24 == 0) goto L_0x0088;
    L_0x007c:
        r24 = r12.isDrawCirclesEnabled();
        if (r24 == 0) goto L_0x0088;
    L_0x0082:
        r24 = r12.getEntryCount();
        if (r24 != 0) goto L_0x00a6;
    L_0x0088:
        r17 = r17 + 1;
        goto L_0x003d;
    L_0x008b:
        r18 = new com.github.mikephil.charting.renderer.LineChartRenderer$DataSetImageCache;
        r24 = 0;
        r0 = r18;
        r1 = r30;
        r2 = r24;
        r0.<init>(r2);
        r0 = r30;
        r0 = r0.mImageCaches;
        r24 = r0;
        r0 = r24;
        r1 = r18;
        r0.put(r12, r1);
        goto L_0x006b;
    L_0x00a6:
        r0 = r30;
        r0 = r0.mCirclePaintInner;
        r24 = r0;
        r25 = r12.getCircleHoleColor();
        r24.setColor(r25);
        r0 = r30;
        r0 = r0.mChart;
        r24 = r0;
        r25 = r12.getAxisDependency();
        r23 = r24.getTransformer(r25);
        r0 = r30;
        r0 = r0.mXBounds;
        r24 = r0;
        r0 = r30;
        r0 = r0.mChart;
        r25 = r0;
        r0 = r24;
        r1 = r25;
        r0.set(r1, r12);
        r9 = r12.getCircleRadius();
        r8 = r12.getCircleHoleRadius();
        r24 = r12.isDrawCircleHoleEnabled();
        if (r24 == 0) goto L_0x019b;
    L_0x00e2:
        r24 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1));
        if (r24 >= 0) goto L_0x019b;
    L_0x00e6:
        r24 = 0;
        r24 = (r8 > r24 ? 1 : (r8 == r24 ? 0 : -1));
        if (r24 <= 0) goto L_0x019b;
    L_0x00ec:
        r14 = 1;
    L_0x00ed:
        if (r14 == 0) goto L_0x019e;
    L_0x00ef:
        r24 = r12.getCircleHoleColor();
        r25 = 1122867; // 0x112233 float:1.573472E-39 double:5.5477E-318;
        r0 = r24;
        r1 = r25;
        if (r0 != r1) goto L_0x019e;
    L_0x00fc:
        r15 = 1;
    L_0x00fd:
        r0 = r30;
        r0 = r0.mXBounds;
        r24 = r0;
        r0 = r24;
        r0 = r0.range;
        r24 = r0;
        r0 = r30;
        r0 = r0.mXBounds;
        r25 = r0;
        r0 = r25;
        r0 = r0.min;
        r25 = r0;
        r4 = r24 + r25;
        r0 = r30;
        r0 = r0.mXBounds;
        r24 = r0;
        r0 = r24;
        r0 = r0.min;
        r19 = r0;
    L_0x0123:
        r0 = r19;
        if (r0 > r4) goto L_0x0088;
    L_0x0127:
        r0 = r19;
        r16 = r12.getEntryForIndex(r0);
        if (r16 == 0) goto L_0x0088;
    L_0x012f:
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r24 = r0;
        r25 = 0;
        r26 = r16.getX();
        r24[r25] = r26;
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r24 = r0;
        r25 = 1;
        r26 = r16.getY();
        r26 = r26 * r20;
        r24[r25] = r26;
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r24 = r0;
        r23.pointValuesToPixel(r24);
        r0 = r30;
        r0 = r0.mViewPortHandler;
        r24 = r0;
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r25 = r0;
        r26 = 0;
        r25 = r25[r26];
        r24 = r24.isInBoundsRight(r25);
        if (r24 == 0) goto L_0x0088;
    L_0x016c:
        r0 = r30;
        r0 = r0.mViewPortHandler;
        r24 = r0;
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r25 = r0;
        r26 = 0;
        r25 = r25[r26];
        r24 = r24.isInBoundsLeft(r25);
        if (r24 == 0) goto L_0x0198;
    L_0x0182:
        r0 = r30;
        r0 = r0.mViewPortHandler;
        r24 = r0;
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r25 = r0;
        r26 = 1;
        r25 = r25[r26];
        r24 = r24.isInBoundsY(r25);
        if (r24 != 0) goto L_0x01a1;
    L_0x0198:
        r19 = r19 + 1;
        goto L_0x0123;
    L_0x019b:
        r14 = 0;
        goto L_0x00ed;
    L_0x019e:
        r15 = 0;
        goto L_0x00fd;
    L_0x01a1:
        r0 = r19;
        r7 = r12.getCircleColor(r0);
        r0 = r30;
        r0 = r0.mRenderPaint;
        r24 = r0;
        r0 = r24;
        r0.setColor(r7);
        r6 = 0;
        r10 = 0;
    L_0x01b4:
        r24 = r18.circleColors;
        r0 = r24;
        r0 = r0.length;
        r24 = r0;
        r0 = r24;
        if (r10 >= r0) goto L_0x01d3;
    L_0x01c1:
        r24 = r18.circleColors;
        r22 = r24[r10];
        r24 = r18.circleBitmaps;
        r21 = r24[r10];
        r0 = r22;
        if (r0 != r7) goto L_0x0277;
    L_0x01d1:
        r6 = r21;
    L_0x01d3:
        if (r6 != 0) goto L_0x024a;
    L_0x01d5:
        r11 = android.graphics.Bitmap.Config.ARGB_8888;
        r0 = (double) r9;
        r24 = r0;
        r26 = 4611911198408756429; // 0x4000cccccccccccd float:-1.07374184E8 double:2.1;
        r24 = r24 * r26;
        r0 = r24;
        r0 = (int) r0;
        r24 = r0;
        r0 = (double) r9;
        r26 = r0;
        r28 = 4611911198408756429; // 0x4000cccccccccccd float:-1.07374184E8 double:2.1;
        r26 = r26 * r28;
        r0 = r26;
        r0 = (int) r0;
        r25 = r0;
        r0 = r24;
        r1 = r25;
        r6 = android.graphics.Bitmap.createBitmap(r0, r1, r11);
        r5 = new android.graphics.Canvas;
        r5.<init>(r6);
        r24 = r18.circleBitmaps;
        r24[r10] = r6;
        r24 = r18.circleColors;
        r24[r10] = r7;
        if (r15 == 0) goto L_0x027d;
    L_0x0210:
        r0 = r30;
        r0 = r0.mCirclePathBuffer;
        r24 = r0;
        r24.reset();
        r0 = r30;
        r0 = r0.mCirclePathBuffer;
        r24 = r0;
        r25 = android.graphics.Path.Direction.CW;
        r0 = r24;
        r1 = r25;
        r0.addCircle(r9, r9, r9, r1);
        r0 = r30;
        r0 = r0.mCirclePathBuffer;
        r24 = r0;
        r25 = android.graphics.Path.Direction.CCW;
        r0 = r24;
        r1 = r25;
        r0.addCircle(r9, r9, r8, r1);
        r0 = r30;
        r0 = r0.mCirclePathBuffer;
        r24 = r0;
        r0 = r30;
        r0 = r0.mRenderPaint;
        r25 = r0;
        r0 = r24;
        r1 = r25;
        r5.drawPath(r0, r1);
    L_0x024a:
        if (r6 == 0) goto L_0x0198;
    L_0x024c:
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r24 = r0;
        r25 = 0;
        r24 = r24[r25];
        r24 = r24 - r9;
        r0 = r30;
        r0 = r0.mCirclesBuffer;
        r25 = r0;
        r26 = 1;
        r25 = r25[r26];
        r25 = r25 - r9;
        r0 = r30;
        r0 = r0.mRenderPaint;
        r26 = r0;
        r0 = r31;
        r1 = r24;
        r2 = r25;
        r3 = r26;
        r0.drawBitmap(r6, r1, r2, r3);
        goto L_0x0198;
    L_0x0277:
        if (r21 == 0) goto L_0x01d3;
    L_0x0279:
        r10 = r10 + 1;
        goto L_0x01b4;
    L_0x027d:
        r0 = r30;
        r0 = r0.mRenderPaint;
        r24 = r0;
        r0 = r24;
        r5.drawCircle(r9, r9, r9, r0);
        if (r14 == 0) goto L_0x024a;
    L_0x028a:
        r0 = r30;
        r0 = r0.mCirclePaintInner;
        r24 = r0;
        r0 = r24;
        r5.drawCircle(r9, r9, r8, r0);
        goto L_0x024a;
    L_0x0296:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LineChartRenderer.drawCircles(android.graphics.Canvas):void");
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        LineData lineData = this.mChart.getLineData();
        for (Highlight high : indices) {
            ILineDataSet set = (ILineDataSet) lineData.getDataSetByIndex(high.getDataSetIndex());
            if (set != null && set.isHighlightEnabled()) {
                Entry e = set.getEntryForXPos(high.getX());
                if (isInBoundsX(e, set)) {
                    MPPointD pix = this.mChart.getTransformer(set.getAxisDependency()).getPixelsForValues(e.getX(), e.getY() * this.mAnimator.getPhaseY());
                    high.setDraw((float) pix.x, (float) pix.y);
                    drawHighlightLines(c, (float) pix.x, (float) pix.y, set);
                }
            }
        }
    }

    public void setBitmapConfig(Config config) {
        this.mBitmapConfig = config;
        releaseBitmap();
    }

    public Config getBitmapConfig() {
        return this.mBitmapConfig;
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
