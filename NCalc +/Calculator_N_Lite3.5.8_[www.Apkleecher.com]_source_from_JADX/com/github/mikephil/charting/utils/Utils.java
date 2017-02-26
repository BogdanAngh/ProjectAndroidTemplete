package com.github.mikephil.charting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.example.duy.calculator.math_eval.Constants;
import edu.jas.vector.GenVectorModul;
import java.util.List;
import org.apache.commons.math4.dfp.Dfp;
import org.apache.commons.math4.distribution.PoissonDistribution;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.random.EmpiricalDistribution;

public abstract class Utils {
    public static final double DEG2RAD = 0.017453292519943295d;
    public static final float FDEG2RAD = 0.017453292f;
    private static final int[] POW_10;
    private static Rect mCalcTextHeightRect;
    private static Rect mCalcTextSizeRect;
    private static Rect mDrawTextRectBuffer;
    private static FontMetrics mFontMetricsBuffer;
    private static int mMaximumFlingVelocity;
    private static DisplayMetrics mMetrics;
    private static int mMinimumFlingVelocity;

    static {
        mMinimumFlingVelocity = 50;
        mMaximumFlingVelocity = 8000;
        mCalcTextHeightRect = new Rect();
        mCalcTextSizeRect = new Rect();
        POW_10 = new int[]{1, 10, 100, EmpiricalDistribution.DEFAULT_BIN_COUNT, Dfp.RADIX, 100000, 1000000, PoissonDistribution.DEFAULT_MAX_ITERATIONS, 100000000, 1000000000};
        mDrawTextRectBuffer = new Rect();
        mFontMetricsBuffer = new FontMetrics();
    }

    public static void init(Context context) {
        if (context == null) {
            mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            Log.e("MPChartLib-Utils", "Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
            return;
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMetrics = context.getResources().getDisplayMetrics();
    }

    @Deprecated
    public static void init(Resources res) {
        mMetrics = res.getDisplayMetrics();
        mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
        mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
    }

    public static float convertDpToPixel(float dp) {
        if (mMetrics != null) {
            return dp * (((float) mMetrics.densityDpi) / 160.0f);
        }
        Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
        return dp;
    }

    public static float convertPixelsToDp(float px) {
        if (mMetrics != null) {
            return px / (((float) mMetrics.densityDpi) / 160.0f);
        }
        Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertPixelsToDp(...). Otherwise conversion does not take place.");
        return px;
    }

    public static int calcTextWidth(Paint paint, String demoText) {
        return (int) paint.measureText(demoText);
    }

    public static int calcTextHeight(Paint paint, String demoText) {
        Rect r = mCalcTextHeightRect;
        r.set(0, 0, 0, 0);
        paint.getTextBounds(demoText, 0, demoText.length(), r);
        return r.height();
    }

    public static float getLineHeight(Paint paint) {
        return getLineHeight(paint, new FontMetrics());
    }

    public static float getLineHeight(Paint paint, FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static float getLineSpacing(Paint paint) {
        return getLineSpacing(paint, new FontMetrics());
    }

    public static float getLineSpacing(Paint paint, FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return (fontMetrics.ascent - fontMetrics.top) + fontMetrics.bottom;
    }

    public static FSize calcTextSize(Paint paint, String demoText) {
        FSize result = FSize.getInstance(0.0f, 0.0f);
        calcTextSize(paint, demoText, result);
        return result;
    }

    public static void calcTextSize(Paint paint, String demoText, FSize outputFSize) {
        Rect r = mCalcTextSizeRect;
        r.set(0, 0, 0, 0);
        paint.getTextBounds(demoText, 0, demoText.length(), r);
        outputFSize.width = (float) r.width();
        outputFSize.height = (float) r.height();
    }

    public static String formatNumber(float number, int digitCount, boolean separateThousands) {
        return formatNumber(number, digitCount, separateThousands, '.');
    }

    public static String formatNumber(float number, int digitCount, boolean separateThousands, char separateChar) {
        char[] out = new char[35];
        boolean neg = false;
        if (number == 0.0f) {
            return Constants.ZERO;
        }
        boolean zero = false;
        if (number < 1.0f && number > -1.0f) {
            zero = true;
        }
        if (number < 0.0f) {
            neg = true;
            number = -number;
        }
        if (digitCount > POW_10.length) {
            digitCount = POW_10.length - 1;
        }
        long lval = (long) Math.round(number * ((float) POW_10[digitCount]));
        int charCount = 0;
        boolean decimalPointAdded = false;
        int ind = out.length - 1;
        while (true) {
            if (lval == 0 && charCount >= digitCount + 1) {
                break;
            }
            lval /= 10;
            int ind2 = ind - 1;
            out[ind] = (char) (((int) (lval % 10)) + 48);
            charCount++;
            if (charCount == digitCount) {
                ind = ind2 - 1;
                out[ind2] = Letters.COMMA;
                charCount++;
                decimalPointAdded = true;
                ind2 = ind;
            } else if (separateThousands && lval != 0 && charCount > digitCount) {
                if (decimalPointAdded) {
                    if ((charCount - digitCount) % 4 == 0) {
                        ind = ind2 - 1;
                        out[ind2] = separateChar;
                        charCount++;
                        ind2 = ind;
                    }
                } else if ((charCount - digitCount) % 4 == 3) {
                    ind = ind2 - 1;
                    out[ind2] = separateChar;
                    charCount++;
                    ind2 = ind;
                }
            }
            ind = ind2;
        }
        if (zero) {
            ind2 = ind - 1;
            out[ind] = '0';
            charCount++;
            ind = ind2;
        }
        if (neg) {
            ind2 = ind - 1;
            out[ind] = Constants.MINUS_UNICODE;
            charCount++;
        }
        int start = out.length - charCount;
        return String.valueOf(out, start, out.length - start);
    }

    public static float roundToNextSignificant(double number) {
        double d;
        if (number < 0.0d) {
            d = -number;
        } else {
            d = number;
        }
        float magnitude = (float) Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, (double) (1 - ((int) ((float) Math.ceil((double) ((float) Math.log10(d)))))));
        return ((float) Math.round(((double) magnitude) * number)) / magnitude;
    }

    public static int getDecimals(float number) {
        return ((int) Math.ceil(-Math.log10((double) roundToNextSignificant((double) number)))) + 2;
    }

    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        copyIntegers(integers, ret);
        return ret;
    }

    public static void copyIntegers(List<Integer> from, int[] to) {
        int count = to.length < from.size() ? to.length : from.size();
        for (int i = 0; i < count; i++) {
            to[i] = ((Integer) from.get(i)).intValue();
        }
    }

    public static String[] convertStrings(List<String> strings) {
        String[] ret = new String[strings.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (String) strings.get(i);
        }
        return ret;
    }

    public static void copyStrings(List<String> from, String[] to) {
        int count = to.length < from.size() ? to.length : from.size();
        for (int i = 0; i < count; i++) {
            to[i] = (String) from.get(i);
        }
    }

    public static double nextUp(double d) {
        if (d == Double.POSITIVE_INFINITY) {
            return d;
        }
        d += 0.0d;
        return Double.longBitsToDouble((d >= 0.0d ? 1 : -1) + Double.doubleToRawLongBits(d));
    }

    public static MPPointF getPosition(MPPointF center, float dist, float angle) {
        MPPointF p = MPPointF.getInstance(0.0f, 0.0f);
        getPosition(center, dist, angle, p);
        return p;
    }

    public static void getPosition(MPPointF center, float dist, float angle, MPPointF outputPoint) {
        outputPoint.x = (float) (((double) center.x) + (((double) dist) * Math.cos(Math.toRadians((double) angle))));
        outputPoint.y = (float) (((double) center.y) + (((double) dist) * Math.sin(Math.toRadians((double) angle))));
    }

    public static void velocityTrackerPointerUpCleanUpIfNecessary(MotionEvent ev, VelocityTracker tracker) {
        tracker.computeCurrentVelocity(EmpiricalDistribution.DEFAULT_BIN_COUNT, (float) mMaximumFlingVelocity);
        int upIndex = ev.getActionIndex();
        int id1 = ev.getPointerId(upIndex);
        float x1 = tracker.getXVelocity(id1);
        float y1 = tracker.getYVelocity(id1);
        int count = ev.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (i != upIndex) {
                int id2 = ev.getPointerId(i);
                if ((x1 * tracker.getXVelocity(id2)) + (y1 * tracker.getYVelocity(id2)) < 0.0f) {
                    tracker.clear();
                    return;
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static void postInvalidateOnAnimation(View view) {
        if (VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation();
        } else {
            view.postInvalidateDelayed(10);
        }
    }

    public static int getMinimumFlingVelocity() {
        return mMinimumFlingVelocity;
    }

    public static int getMaximumFlingVelocity() {
        return mMaximumFlingVelocity;
    }

    public static float getNormalizedAngle(float angle) {
        while (angle < 0.0f) {
            angle += 360.0f;
        }
        return angle % 360.0f;
    }

    public static void drawXAxisValue(Canvas c, String text, float x, float y, Paint paint, MPPointF anchor, float angleDegrees) {
        float lineHeight = paint.getFontMetrics(mFontMetricsBuffer);
        paint.getTextBounds(text, 0, text.length(), mDrawTextRectBuffer);
        float drawOffsetX = 0.0f - ((float) mDrawTextRectBuffer.left);
        float drawOffsetY = 0.0f + (-mFontMetricsBuffer.ascent);
        Align originalTextAlign = paint.getTextAlign();
        paint.setTextAlign(Align.LEFT);
        if (angleDegrees != 0.0f) {
            drawOffsetX -= ((float) mDrawTextRectBuffer.width()) * GenVectorModul.DEFAULT_DENSITY;
            drawOffsetY -= GenVectorModul.DEFAULT_DENSITY * lineHeight;
            float translateX = x;
            float translateY = y;
            if (!(anchor.x == GenVectorModul.DEFAULT_DENSITY && anchor.y == GenVectorModul.DEFAULT_DENSITY)) {
                FSize rotatedSize = getSizeOfRotatedRectangleByDegrees((float) mDrawTextRectBuffer.width(), lineHeight, angleDegrees);
                translateX -= rotatedSize.width * (anchor.x - GenVectorModul.DEFAULT_DENSITY);
                translateY -= rotatedSize.height * (anchor.y - GenVectorModul.DEFAULT_DENSITY);
                FSize.recycleInstance(rotatedSize);
            }
            c.save();
            c.translate(translateX, translateY);
            c.rotate(angleDegrees);
            c.drawText(text, drawOffsetX, drawOffsetY, paint);
            c.restore();
        } else {
            if (!(anchor.x == 0.0f && anchor.y == 0.0f)) {
                drawOffsetX -= ((float) mDrawTextRectBuffer.width()) * anchor.x;
                drawOffsetY -= anchor.y * lineHeight;
            }
            c.drawText(text, drawOffsetX + x, drawOffsetY + y, paint);
        }
        paint.setTextAlign(originalTextAlign);
    }

    public static void drawMultilineText(Canvas c, StaticLayout textLayout, float x, float y, TextPaint paint, MPPointF anchor, float angleDegrees) {
        float drawWidth = (float) textLayout.getWidth();
        float drawHeight = ((float) textLayout.getLineCount()) * paint.getFontMetrics(mFontMetricsBuffer);
        float drawOffsetX = 0.0f - ((float) mDrawTextRectBuffer.left);
        float drawOffsetY = 0.0f + drawHeight;
        Align originalTextAlign = paint.getTextAlign();
        paint.setTextAlign(Align.LEFT);
        if (angleDegrees != 0.0f) {
            drawOffsetX -= GenVectorModul.DEFAULT_DENSITY * drawWidth;
            drawOffsetY -= GenVectorModul.DEFAULT_DENSITY * drawHeight;
            float translateX = x;
            float translateY = y;
            if (!(anchor.x == GenVectorModul.DEFAULT_DENSITY && anchor.y == GenVectorModul.DEFAULT_DENSITY)) {
                FSize rotatedSize = getSizeOfRotatedRectangleByDegrees(drawWidth, drawHeight, angleDegrees);
                translateX -= rotatedSize.width * (anchor.x - GenVectorModul.DEFAULT_DENSITY);
                translateY -= rotatedSize.height * (anchor.y - GenVectorModul.DEFAULT_DENSITY);
                FSize.recycleInstance(rotatedSize);
            }
            c.save();
            c.translate(translateX, translateY);
            c.rotate(angleDegrees);
            c.translate(drawOffsetX, drawOffsetY);
            textLayout.draw(c);
            c.restore();
        } else {
            if (!(anchor.x == 0.0f && anchor.y == 0.0f)) {
                drawOffsetX -= anchor.x * drawWidth;
                drawOffsetY -= anchor.y * drawHeight;
            }
            drawOffsetX += x;
            drawOffsetY += y;
            c.save();
            c.translate(drawOffsetX, drawOffsetY);
            textLayout.draw(c);
            c.restore();
        }
        paint.setTextAlign(originalTextAlign);
    }

    public static void drawMultilineText(Canvas c, String text, float x, float y, TextPaint paint, FSize constrainedToSize, MPPointF anchor, float angleDegrees) {
        drawMultilineText(c, new StaticLayout(text, 0, text.length(), paint, (int) Math.max(Math.ceil((double) constrainedToSize.width), 1.0d), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false), x, y, paint, anchor, angleDegrees);
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(FSize rectangleSize, float degrees) {
        return getSizeOfRotatedRectangleByRadians(rectangleSize.width, rectangleSize.height, degrees * FDEG2RAD);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(FSize rectangleSize, float radians) {
        return getSizeOfRotatedRectangleByRadians(rectangleSize.width, rectangleSize.height, radians);
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(float rectangleWidth, float rectangleHeight, float degrees) {
        return getSizeOfRotatedRectangleByRadians(rectangleWidth, rectangleHeight, degrees * FDEG2RAD);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(float rectangleWidth, float rectangleHeight, float radians) {
        return FSize.getInstance(Math.abs(((float) Math.cos((double) radians)) * rectangleWidth) + Math.abs(((float) Math.sin((double) radians)) * rectangleHeight), Math.abs(((float) Math.sin((double) radians)) * rectangleWidth) + Math.abs(((float) Math.cos((double) radians)) * rectangleHeight));
    }

    public static int getSDKInt() {
        return VERSION.SDK_INT;
    }

    public static double granularity(float range, int labelCount) {
        double interval = (double) roundToNextSignificant((double) (range / ((float) labelCount)));
        double intervalMagnitude = (double) roundToNextSignificant(Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, (double) ((int) Math.log10(interval))));
        if (((int) (interval / intervalMagnitude)) > 5) {
            interval = Math.floor(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS * intervalMagnitude);
        }
        return 0.1d * interval;
    }
}
