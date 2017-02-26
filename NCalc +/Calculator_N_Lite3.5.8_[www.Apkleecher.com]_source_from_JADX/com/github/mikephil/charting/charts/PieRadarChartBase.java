package com.github.mikephil.charting.charts;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.animation.Easing.EasingOption;
import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment;
import com.github.mikephil.charting.components.Legend.LegendOrientation;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public abstract class PieRadarChartBase<T extends ChartData<? extends IDataSet<? extends Entry>>> extends Chart<T> {
    protected float mMinOffset;
    private float mRawRotationAngle;
    protected boolean mRotateEnabled;
    private float mRotationAngle;

    class 1 implements AnimatorUpdateListener {
        1() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            PieRadarChartBase.this.postInvalidate();
        }
    }

    static /* synthetic */ class 2 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment;

        static {
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation = new int[LegendOrientation.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[LegendOrientation.VERTICAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[LegendOrientation.HORIZONTAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment = new int[LegendHorizontalAlignment.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[LegendHorizontalAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[LegendHorizontalAlignment.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[LegendHorizontalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment = new int[LegendVerticalAlignment.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[LegendVerticalAlignment.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[LegendVerticalAlignment.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public abstract int getIndexForAngle(float f);

    public abstract float getRadius();

    protected abstract float getRequiredBaseOffset();

    protected abstract float getRequiredLegendOffset();

    public PieRadarChartBase(Context context) {
        super(context);
        this.mRotationAngle = 270.0f;
        this.mRawRotationAngle = 270.0f;
        this.mRotateEnabled = true;
        this.mMinOffset = 0.0f;
    }

    public PieRadarChartBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRotationAngle = 270.0f;
        this.mRawRotationAngle = 270.0f;
        this.mRotateEnabled = true;
        this.mMinOffset = 0.0f;
    }

    public PieRadarChartBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mRotationAngle = 270.0f;
        this.mRawRotationAngle = 270.0f;
        this.mRotateEnabled = true;
        this.mMinOffset = 0.0f;
    }

    protected void init() {
        super.init();
        this.mChartTouchListener = new PieRadarChartTouchListener(this);
    }

    protected void calcMinMax() {
    }

    public int getMaxVisibleCount() {
        return this.mData.getEntryCount();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mTouchEnabled || this.mChartTouchListener == null) {
            return super.onTouchEvent(event);
        }
        return this.mChartTouchListener.onTouch(this, event);
    }

    public void computeScroll() {
        if (this.mChartTouchListener instanceof PieRadarChartTouchListener) {
            ((PieRadarChartTouchListener) this.mChartTouchListener).computeScroll();
        }
    }

    public void notifyDataSetChanged() {
        if (this.mData != null) {
            calcMinMax();
            if (this.mLegend != null) {
                this.mLegendRenderer.computeLegend(this.mData);
            }
            calculateOffsets();
        }
    }

    public void calculateOffsets() {
        float minOffset;
        float legendLeft = 0.0f;
        float legendRight = 0.0f;
        float legendBottom = 0.0f;
        float legendTop = 0.0f;
        if (this.mLegend != null) {
            if (this.mLegend.isEnabled()) {
                if (!this.mLegend.isDrawInsideEnabled()) {
                    float fullLegendWidth = (Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent()) + this.mLegend.getFormSize()) + this.mLegend.getFormToTextSpace();
                    switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mLegend.getOrientation().ordinal()]) {
                        case ValueServer.REPLAY_MODE /*1*/:
                            float xLegendOffset = 0.0f;
                            if (this.mLegend.getHorizontalAlignment() == LegendHorizontalAlignment.LEFT || this.mLegend.getHorizontalAlignment() == LegendHorizontalAlignment.RIGHT) {
                                if (this.mLegend.getVerticalAlignment() == LegendVerticalAlignment.CENTER) {
                                    xLegendOffset = fullLegendWidth + Utils.convertDpToPixel(13.0f);
                                } else {
                                    float bottomX;
                                    float legendWidth = fullLegendWidth + Utils.convertDpToPixel(8.0f);
                                    float legendHeight = this.mLegend.mNeededHeight + this.mLegend.mTextHeightMax;
                                    MPPointF center = getCenter();
                                    if (this.mLegend.getHorizontalAlignment() == LegendHorizontalAlignment.RIGHT) {
                                        bottomX = (((float) getWidth()) - legendWidth) + 15.0f;
                                    } else {
                                        bottomX = legendWidth - 15.0f;
                                    }
                                    float bottomY = legendHeight + 15.0f;
                                    float distLegend = distanceToCenter(bottomX, bottomY);
                                    MPPointF reference = getPosition(center, getRadius(), getAngleForPoint(bottomX, bottomY));
                                    float distReference = distanceToCenter(reference.x, reference.y);
                                    minOffset = Utils.convertDpToPixel(5.0f);
                                    if (bottomY >= center.y) {
                                        if (((float) getHeight()) - legendWidth > ((float) getWidth())) {
                                            xLegendOffset = legendWidth;
                                            MPPointF.recycleInstance(center);
                                            MPPointF.recycleInstance(reference);
                                        }
                                    }
                                    if (distLegend < distReference) {
                                        xLegendOffset = minOffset + (distReference - distLegend);
                                    }
                                    MPPointF.recycleInstance(center);
                                    MPPointF.recycleInstance(reference);
                                }
                            }
                            switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[this.mLegend.getHorizontalAlignment().ordinal()]) {
                                case ValueServer.REPLAY_MODE /*1*/:
                                    legendLeft = xLegendOffset;
                                    break;
                                case IExpr.DOUBLEID /*2*/:
                                    legendRight = xLegendOffset;
                                    break;
                                case ValueServer.EXPONENTIAL_MODE /*3*/:
                                    switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()]) {
                                        case ValueServer.REPLAY_MODE /*1*/:
                                            legendTop = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                            break;
                                        case IExpr.DOUBLEID /*2*/:
                                            legendBottom = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                            break;
                                        default:
                                            break;
                                    }
                                default:
                                    break;
                            }
                        case IExpr.DOUBLEID /*2*/:
                            if (this.mLegend.getVerticalAlignment() == LegendVerticalAlignment.TOP || this.mLegend.getVerticalAlignment() == LegendVerticalAlignment.BOTTOM) {
                                float yOffset = getRequiredLegendOffset();
                                float yLegendOffset = Math.min(this.mLegend.mNeededHeight + yOffset, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()]) {
                                    case ValueServer.REPLAY_MODE /*1*/:
                                        legendTop = yLegendOffset;
                                        break;
                                    case IExpr.DOUBLEID /*2*/:
                                        legendBottom = yLegendOffset;
                                        break;
                                    default:
                                        break;
                                }
                            }
                    }
                    legendLeft += getRequiredBaseOffset();
                    legendRight += getRequiredBaseOffset();
                    legendTop += getRequiredBaseOffset();
                    legendBottom += getRequiredBaseOffset();
                }
            }
        }
        minOffset = Utils.convertDpToPixel(this.mMinOffset);
        if (this instanceof RadarChart) {
            XAxis x = getXAxis();
            if (x.isEnabled() && x.isDrawLabelsEnabled()) {
                minOffset = Math.max(minOffset, (float) x.mLabelRotatedWidth);
            }
        }
        legendTop += getExtraTopOffset();
        legendRight += getExtraRightOffset();
        legendBottom += getExtraBottomOffset();
        float offsetLeft = Math.max(minOffset, legendLeft + getExtraLeftOffset());
        float offsetTop = Math.max(minOffset, legendTop);
        float offsetRight = Math.max(minOffset, legendRight);
        float offsetBottom = Math.max(minOffset, Math.max(getRequiredBaseOffset(), legendBottom));
        this.mViewPortHandler.restrainViewPort(offsetLeft, offsetTop, offsetRight, offsetBottom);
        if (this.mLogEnabled) {
            Log.i(Chart.LOG_TAG, "offsetLeft: " + offsetLeft + ", offsetTop: " + offsetTop + ", offsetRight: " + offsetRight + ", offsetBottom: " + offsetBottom);
        }
    }

    public float getAngleForPoint(float x, float y) {
        MPPointF c = getCenterOffsets();
        double tx = (double) (x - c.x);
        double ty = (double) (y - c.y);
        float angle = (float) Math.toDegrees(Math.acos(ty / Math.sqrt((tx * tx) + (ty * ty))));
        if (x > c.x) {
            angle = 360.0f - angle;
        }
        angle += 90.0f;
        if (angle > 360.0f) {
            angle -= 360.0f;
        }
        MPPointF.recycleInstance(c);
        return angle;
    }

    public MPPointF getPosition(MPPointF center, float dist, float angle) {
        MPPointF p = MPPointF.getInstance(0.0f, 0.0f);
        getPosition(center, dist, angle, p);
        return p;
    }

    public void getPosition(MPPointF center, float dist, float angle, MPPointF outputPoint) {
        outputPoint.x = (float) (((double) center.x) + (((double) dist) * Math.cos(Math.toRadians((double) angle))));
        outputPoint.y = (float) (((double) center.y) + (((double) dist) * Math.sin(Math.toRadians((double) angle))));
    }

    public float distanceToCenter(float x, float y) {
        float xDist;
        float yDist;
        MPPointF c = getCenterOffsets();
        if (x > c.x) {
            xDist = x - c.x;
        } else {
            xDist = c.x - x;
        }
        if (y > c.y) {
            yDist = y - c.y;
        } else {
            yDist = c.y - y;
        }
        float dist = (float) Math.sqrt(Math.pow((double) xDist, 2.0d) + Math.pow((double) yDist, 2.0d));
        MPPointF.recycleInstance(c);
        return dist;
    }

    public void setRotationAngle(float angle) {
        this.mRawRotationAngle = angle;
        this.mRotationAngle = Utils.getNormalizedAngle(this.mRawRotationAngle);
    }

    public float getRawRotationAngle() {
        return this.mRawRotationAngle;
    }

    public float getRotationAngle() {
        return this.mRotationAngle;
    }

    public void setRotationEnabled(boolean enabled) {
        this.mRotateEnabled = enabled;
    }

    public boolean isRotationEnabled() {
        return this.mRotateEnabled;
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    public void setMinOffset(float minOffset) {
        this.mMinOffset = minOffset;
    }

    public float getDiameter() {
        RectF content = this.mViewPortHandler.getContentRect();
        content.left += getExtraLeftOffset();
        content.top += getExtraTopOffset();
        content.right -= getExtraRightOffset();
        content.bottom -= getExtraBottomOffset();
        return Math.min(content.width(), content.height());
    }

    public float getYChartMax() {
        return 0.0f;
    }

    public float getYChartMin() {
        return 0.0f;
    }

    @SuppressLint({"NewApi"})
    public void spin(int durationmillis, float fromangle, float toangle, EasingOption easing) {
        if (VERSION.SDK_INT >= 11) {
            setRotationAngle(fromangle);
            ObjectAnimator spinAnimator = ObjectAnimator.ofFloat(this, "rotationAngle", new float[]{fromangle, toangle});
            spinAnimator.setDuration((long) durationmillis);
            spinAnimator.setInterpolator(Easing.getEasingFunctionFromOption(easing));
            spinAnimator.addUpdateListener(new 1());
            spinAnimator.start();
        }
    }
}
