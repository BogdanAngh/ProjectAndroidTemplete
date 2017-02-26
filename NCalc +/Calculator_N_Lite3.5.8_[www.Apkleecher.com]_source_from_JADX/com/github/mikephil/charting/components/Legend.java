package com.github.mikephil.charting.components;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import io.github.kexanie.library.R;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class Legend extends ComponentBase {
    protected ArrayList<FSize> calculatedLineSizesForCalculateDimensions;
    protected FontMetrics fontMetricsForCalculateDimensions;
    private boolean isCalculatedLineSizesArrayListResized;
    private Boolean[] mCalculatedLabelBreakPoints;
    private FSize[] mCalculatedLabelSizes;
    private FSize[] mCalculatedLineSizes;
    private int[] mColors;
    private LegendDirection mDirection;
    private boolean mDrawInside;
    private int[] mExtraColors;
    private String[] mExtraLabels;
    private float mFormSize;
    private float mFormToTextSpace;
    private LegendHorizontalAlignment mHorizontalAlignment;
    private boolean mIsLegendCustom;
    private String[] mLabels;
    private float mMaxSizePercent;
    public float mNeededHeight;
    public float mNeededWidth;
    private LegendOrientation mOrientation;
    private LegendForm mShape;
    private float mStackSpace;
    public float mTextHeightMax;
    public float mTextWidthMax;
    private LegendVerticalAlignment mVerticalAlignment;
    private boolean mWordWrapEnabled;
    private float mXEntrySpace;
    private float mYEntrySpace;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition;

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
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition = new int[LegendPosition.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.LEFT_OF_CHART.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.LEFT_OF_CHART_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.LEFT_OF_CHART_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.RIGHT_OF_CHART.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.RIGHT_OF_CHART_INSIDE.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.RIGHT_OF_CHART_CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.ABOVE_CHART_LEFT.ordinal()] = 7;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.ABOVE_CHART_CENTER.ordinal()] = 8;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.ABOVE_CHART_RIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.BELOW_CHART_LEFT.ordinal()] = 10;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.BELOW_CHART_CENTER.ordinal()] = 11;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.BELOW_CHART_RIGHT.ordinal()] = 12;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[LegendPosition.PIECHART_CENTER.ordinal()] = 13;
            } catch (NoSuchFieldError e15) {
            }
        }
    }

    public enum LegendDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public enum LegendForm {
        SQUARE,
        CIRCLE,
        LINE
    }

    public enum LegendHorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum LegendOrientation {
        HORIZONTAL,
        VERTICAL
    }

    public enum LegendPosition {
        RIGHT_OF_CHART,
        RIGHT_OF_CHART_CENTER,
        RIGHT_OF_CHART_INSIDE,
        LEFT_OF_CHART,
        LEFT_OF_CHART_CENTER,
        LEFT_OF_CHART_INSIDE,
        BELOW_CHART_LEFT,
        BELOW_CHART_RIGHT,
        BELOW_CHART_CENTER,
        ABOVE_CHART_LEFT,
        ABOVE_CHART_RIGHT,
        ABOVE_CHART_CENTER,
        PIECHART_CENTER
    }

    public enum LegendVerticalAlignment {
        TOP,
        CENTER,
        BOTTOM
    }

    public Legend() {
        this.mIsLegendCustom = false;
        this.mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
        this.mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
        this.mOrientation = LegendOrientation.HORIZONTAL;
        this.mDrawInside = false;
        this.mDirection = LegendDirection.LEFT_TO_RIGHT;
        this.mShape = LegendForm.SQUARE;
        this.mFormSize = 8.0f;
        this.mXEntrySpace = 6.0f;
        this.mYEntrySpace = 0.0f;
        this.mFormToTextSpace = 5.0f;
        this.mStackSpace = 3.0f;
        this.mMaxSizePercent = 0.95f;
        this.mNeededWidth = 0.0f;
        this.mNeededHeight = 0.0f;
        this.mTextHeightMax = 0.0f;
        this.mTextWidthMax = 0.0f;
        this.mWordWrapEnabled = false;
        this.isCalculatedLineSizesArrayListResized = true;
        this.mCalculatedLabelSizes = new FSize[0];
        this.mCalculatedLabelBreakPoints = new Boolean[0];
        this.mCalculatedLineSizes = new FSize[0];
        this.fontMetricsForCalculateDimensions = new FontMetrics();
        this.calculatedLineSizesForCalculateDimensions = new ArrayList();
        this.mFormSize = Utils.convertDpToPixel(8.0f);
        this.mXEntrySpace = Utils.convertDpToPixel(6.0f);
        this.mYEntrySpace = Utils.convertDpToPixel(0.0f);
        this.mFormToTextSpace = Utils.convertDpToPixel(5.0f);
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mStackSpace = Utils.convertDpToPixel(3.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(3.0f);
    }

    public Legend(int[] colors, String[] labels) {
        this();
        if (colors == null || labels == null) {
            throw new IllegalArgumentException("colors array or labels array is NULL");
        } else if (colors.length != labels.length) {
            throw new IllegalArgumentException("colors array and labels array need to be of same size");
        } else {
            this.mColors = colors;
            this.mLabels = labels;
        }
    }

    public Legend(List<Integer> colors, List<String> labels) {
        this();
        if (colors == null || labels == null) {
            throw new IllegalArgumentException("colors array or labels array is NULL");
        } else if (colors.size() != labels.size()) {
            throw new IllegalArgumentException("colors array and labels array need to be of same size");
        } else {
            setComputedColors(colors);
            setComputedLabels(labels);
        }
    }

    public void setComputedColors(List<Integer> colors) {
        if (this.mColors == null || colors.size() != this.mColors.length) {
            this.mColors = Utils.convertIntegers(colors);
        } else {
            Utils.copyIntegers(colors, this.mColors);
        }
    }

    public void setComputedLabels(List<String> labels) {
        if (this.mLabels == null || this.mLabels.length != labels.size()) {
            this.mLabels = Utils.convertStrings(labels);
        } else {
            Utils.copyStrings(labels, this.mLabels);
        }
    }

    public float getMaximumEntryWidth(Paint p) {
        float max = 0.0f;
        for (int i = 0; i < this.mLabels.length; i++) {
            if (this.mLabels[i] != null) {
                float length = (float) Utils.calcTextWidth(p, this.mLabels[i]);
                if (length > max) {
                    max = length;
                }
            }
        }
        return (this.mFormSize + max) + this.mFormToTextSpace;
    }

    public float getMaximumEntryHeight(Paint p) {
        float max = 0.0f;
        for (int i = 0; i < this.mLabels.length; i++) {
            if (this.mLabels[i] != null) {
                float length = (float) Utils.calcTextHeight(p, this.mLabels[i]);
                if (length > max) {
                    max = length;
                }
            }
        }
        return max;
    }

    public int[] getColors() {
        return this.mColors;
    }

    public String[] getLabels() {
        return this.mLabels;
    }

    public String getLabel(int index) {
        return this.mLabels[index];
    }

    public int[] getExtraColors() {
        return this.mExtraColors;
    }

    public String[] getExtraLabels() {
        return this.mExtraLabels;
    }

    public void setExtra(List<Integer> colors, List<String> labels) {
        if (this.mExtraColors == null || this.mExtraColors.length != colors.size()) {
            this.mExtraColors = Utils.convertIntegers(colors);
        } else {
            Utils.copyIntegers(colors, this.mExtraColors);
        }
        if (this.mExtraLabels == null || this.mExtraLabels.length != labels.size()) {
            this.mExtraLabels = Utils.convertStrings(labels);
        } else {
            Utils.copyStrings(labels, this.mExtraLabels);
        }
    }

    public void setExtra(int[] colors, String[] labels) {
        this.mExtraColors = colors;
        this.mExtraLabels = labels;
    }

    public void setCustom(int[] colors, String[] labels) {
        if (colors.length != labels.length) {
            throw new IllegalArgumentException("colors array and labels array need to be of same size");
        }
        this.mLabels = labels;
        this.mColors = colors;
        this.mIsLegendCustom = true;
    }

    public void setCustom(List<Integer> colors, List<String> labels) {
        if (colors.size() != labels.size()) {
            throw new IllegalArgumentException("colors array and labels array need to be of same size");
        }
        setComputedColors(colors);
        setComputedLabels(labels);
        this.mIsLegendCustom = true;
    }

    public void resetCustom() {
        this.mIsLegendCustom = false;
    }

    public boolean isLegendCustom() {
        return this.mIsLegendCustom;
    }

    public LegendPosition getPosition() {
        if (this.mOrientation == LegendOrientation.VERTICAL && this.mHorizontalAlignment == LegendHorizontalAlignment.CENTER && this.mVerticalAlignment == LegendVerticalAlignment.CENTER) {
            return LegendPosition.PIECHART_CENTER;
        }
        if (this.mOrientation == LegendOrientation.HORIZONTAL) {
            if (this.mVerticalAlignment == LegendVerticalAlignment.TOP) {
                if (this.mHorizontalAlignment == LegendHorizontalAlignment.LEFT) {
                    return LegendPosition.ABOVE_CHART_LEFT;
                }
                return this.mHorizontalAlignment == LegendHorizontalAlignment.RIGHT ? LegendPosition.ABOVE_CHART_RIGHT : LegendPosition.ABOVE_CHART_CENTER;
            } else if (this.mHorizontalAlignment == LegendHorizontalAlignment.LEFT) {
                return LegendPosition.BELOW_CHART_LEFT;
            } else {
                return this.mHorizontalAlignment == LegendHorizontalAlignment.RIGHT ? LegendPosition.BELOW_CHART_RIGHT : LegendPosition.BELOW_CHART_CENTER;
            }
        } else if (this.mHorizontalAlignment == LegendHorizontalAlignment.LEFT) {
            if (this.mVerticalAlignment == LegendVerticalAlignment.TOP && this.mDrawInside) {
                return LegendPosition.LEFT_OF_CHART_INSIDE;
            }
            return this.mVerticalAlignment == LegendVerticalAlignment.CENTER ? LegendPosition.LEFT_OF_CHART_CENTER : LegendPosition.LEFT_OF_CHART;
        } else if (this.mVerticalAlignment == LegendVerticalAlignment.TOP && this.mDrawInside) {
            return LegendPosition.RIGHT_OF_CHART_INSIDE;
        } else {
            return this.mVerticalAlignment == LegendVerticalAlignment.CENTER ? LegendPosition.RIGHT_OF_CHART_CENTER : LegendPosition.RIGHT_OF_CHART;
        }
    }

    public void setPosition(LegendPosition newValue) {
        LegendHorizontalAlignment legendHorizontalAlignment;
        switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[newValue.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
            case IExpr.DOUBLEID /*2*/:
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                this.mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
                this.mVerticalAlignment = newValue == LegendPosition.LEFT_OF_CHART_CENTER ? LegendVerticalAlignment.CENTER : LegendVerticalAlignment.TOP;
                this.mOrientation = LegendOrientation.VERTICAL;
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
            case ValueServer.CONSTANT_MODE /*5*/:
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                this.mHorizontalAlignment = LegendHorizontalAlignment.RIGHT;
                this.mVerticalAlignment = newValue == LegendPosition.RIGHT_OF_CHART_CENTER ? LegendVerticalAlignment.CENTER : LegendVerticalAlignment.TOP;
                this.mOrientation = LegendOrientation.VERTICAL;
                break;
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
            case IExpr.INTEGERID /*8*/:
            case R.styleable.TextAppearance_textAllCaps /*9*/:
                legendHorizontalAlignment = newValue == LegendPosition.ABOVE_CHART_LEFT ? LegendHorizontalAlignment.LEFT : newValue == LegendPosition.ABOVE_CHART_RIGHT ? LegendHorizontalAlignment.RIGHT : LegendHorizontalAlignment.CENTER;
                this.mHorizontalAlignment = legendHorizontalAlignment;
                this.mVerticalAlignment = LegendVerticalAlignment.TOP;
                this.mOrientation = LegendOrientation.HORIZONTAL;
                break;
            case R.styleable.SwitchCompat_switchMinWidth /*10*/:
            case R.styleable.Toolbar_popupTheme /*11*/:
            case R.styleable.Toolbar_titleTextAppearance /*12*/:
                legendHorizontalAlignment = newValue == LegendPosition.BELOW_CHART_LEFT ? LegendHorizontalAlignment.LEFT : newValue == LegendPosition.BELOW_CHART_RIGHT ? LegendHorizontalAlignment.RIGHT : LegendHorizontalAlignment.CENTER;
                this.mHorizontalAlignment = legendHorizontalAlignment;
                this.mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
                this.mOrientation = LegendOrientation.HORIZONTAL;
                break;
            case R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                this.mHorizontalAlignment = LegendHorizontalAlignment.CENTER;
                this.mVerticalAlignment = LegendVerticalAlignment.CENTER;
                this.mOrientation = LegendOrientation.VERTICAL;
                break;
        }
        boolean z = newValue == LegendPosition.LEFT_OF_CHART_INSIDE || newValue == LegendPosition.RIGHT_OF_CHART_INSIDE;
        this.mDrawInside = z;
    }

    public LegendHorizontalAlignment getHorizontalAlignment() {
        return this.mHorizontalAlignment;
    }

    public void setHorizontalAlignment(LegendHorizontalAlignment value) {
        this.mHorizontalAlignment = value;
    }

    public LegendVerticalAlignment getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    public void setVerticalAlignment(LegendVerticalAlignment value) {
        this.mVerticalAlignment = value;
    }

    public LegendOrientation getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(LegendOrientation value) {
        this.mOrientation = value;
    }

    public boolean isDrawInsideEnabled() {
        return this.mDrawInside;
    }

    public void setDrawInside(boolean value) {
        this.mDrawInside = value;
    }

    public LegendDirection getDirection() {
        return this.mDirection;
    }

    public void setDirection(LegendDirection pos) {
        this.mDirection = pos;
    }

    public LegendForm getForm() {
        return this.mShape;
    }

    public void setForm(LegendForm shape) {
        this.mShape = shape;
    }

    public void setFormSize(float size) {
        this.mFormSize = Utils.convertDpToPixel(size);
    }

    public float getFormSize() {
        return this.mFormSize;
    }

    public float getXEntrySpace() {
        return this.mXEntrySpace;
    }

    public void setXEntrySpace(float space) {
        this.mXEntrySpace = Utils.convertDpToPixel(space);
    }

    public float getYEntrySpace() {
        return this.mYEntrySpace;
    }

    public void setYEntrySpace(float space) {
        this.mYEntrySpace = Utils.convertDpToPixel(space);
    }

    public float getFormToTextSpace() {
        return this.mFormToTextSpace;
    }

    public void setFormToTextSpace(float space) {
        this.mFormToTextSpace = Utils.convertDpToPixel(space);
    }

    public float getStackSpace() {
        return this.mStackSpace;
    }

    public void setStackSpace(float space) {
        this.mStackSpace = space;
    }

    public float getFullWidth(Paint labelpaint) {
        float width = 0.0f;
        for (int i = 0; i < this.mLabels.length; i++) {
            if (this.mLabels[i] != null) {
                if (this.mColors[i] != 1122868) {
                    width += this.mFormSize + this.mFormToTextSpace;
                }
                width += (float) Utils.calcTextWidth(labelpaint, this.mLabels[i]);
                if (i < this.mLabels.length - 1) {
                    width += this.mXEntrySpace;
                }
            } else {
                width += this.mFormSize;
                if (i < this.mLabels.length - 1) {
                    width += this.mStackSpace;
                }
            }
        }
        return width;
    }

    public float getFullHeight(Paint labelpaint) {
        float height = 0.0f;
        for (int i = 0; i < this.mLabels.length; i++) {
            if (this.mLabels[i] != null) {
                height += (float) Utils.calcTextHeight(labelpaint, this.mLabels[i]);
                if (i < this.mLabels.length - 1) {
                    height += this.mYEntrySpace;
                }
            }
        }
        return height;
    }

    public void setWordWrapEnabled(boolean enabled) {
        this.mWordWrapEnabled = enabled;
    }

    public boolean isWordWrapEnabled() {
        return this.mWordWrapEnabled;
    }

    public float getMaxSizePercent() {
        return this.mMaxSizePercent;
    }

    public void setMaxSizePercent(float maxSize) {
        this.mMaxSizePercent = maxSize;
    }

    public FSize[] getCalculatedLabelSizes() {
        return this.mCalculatedLabelSizes;
    }

    public Boolean[] getCalculatedLabelBreakPoints() {
        return this.mCalculatedLabelBreakPoints;
    }

    public FSize[] getCalculatedLineSizes() {
        if (this.mCalculatedLineSizes == null || this.isCalculatedLineSizesArrayListResized) {
            this.mCalculatedLineSizes = (FSize[]) this.calculatedLineSizesForCalculateDimensions.toArray(new FSize[this.calculatedLineSizesForCalculateDimensions.size()]);
            this.isCalculatedLineSizesArrayListResized = false;
        }
        return this.mCalculatedLineSizes;
    }

    public void calculateDimensions(Paint labelpaint, ViewPortHandler viewPortHandler) {
        this.mTextWidthMax = getMaximumEntryWidth(labelpaint);
        this.mTextHeightMax = getMaximumEntryHeight(labelpaint);
        float labelLineHeight;
        int count;
        int i;
        boolean drawingForm;
        switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mOrientation.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                float maxWidth = 0.0f;
                float maxHeight = 0.0f;
                float width = 0.0f;
                labelLineHeight = Utils.getLineHeight(labelpaint, this.fontMetricsForCalculateDimensions);
                count = this.mLabels.length;
                boolean wasStacked = false;
                for (i = 0; i < count; i++) {
                    drawingForm = this.mColors[i] != 1122868;
                    if (!wasStacked) {
                        width = 0.0f;
                    }
                    if (drawingForm) {
                        if (wasStacked) {
                            width += this.mStackSpace;
                        }
                        width += this.mFormSize;
                    }
                    if (this.mLabels[i] != null) {
                        if (drawingForm && !wasStacked) {
                            width += this.mFormToTextSpace;
                        } else if (wasStacked) {
                            maxWidth = Math.max(maxWidth, width);
                            maxHeight += this.mYEntrySpace + labelLineHeight;
                            width = 0.0f;
                            wasStacked = false;
                        }
                        width += (float) Utils.calcTextWidth(labelpaint, this.mLabels[i]);
                        if (i < count - 1) {
                            maxHeight += this.mYEntrySpace + labelLineHeight;
                        }
                    } else {
                        wasStacked = true;
                        width += this.mFormSize;
                        if (i < count - 1) {
                            width += this.mStackSpace;
                        }
                    }
                    maxWidth = Math.max(maxWidth, width);
                }
                this.mNeededWidth = maxWidth;
                this.mNeededHeight = maxHeight;
            case IExpr.DOUBLEID /*2*/:
                int labelCount = this.mLabels.length;
                labelLineHeight = Utils.getLineHeight(labelpaint, this.fontMetricsForCalculateDimensions);
                float labelLineSpacing = Utils.getLineSpacing(labelpaint, this.fontMetricsForCalculateDimensions) + this.mYEntrySpace;
                float contentWidth = viewPortHandler.contentWidth() * this.mMaxSizePercent;
                int length = this.mCalculatedLabelSizes.length;
                if (r0 != labelCount) {
                    FSize[] temp = new FSize[labelCount];
                    count = this.mCalculatedLabelSizes.length;
                    i = 0;
                    while (i < count && i < labelCount) {
                        temp[i] = this.mCalculatedLabelSizes[i];
                        i++;
                    }
                    while (count > labelCount) {
                        count--;
                        FSize.recycleInstance(this.mCalculatedLabelSizes[count]);
                    }
                    this.mCalculatedLabelSizes = temp;
                }
                int calculatedLabelSizesIndex = 0;
                length = this.mCalculatedLabelBreakPoints.length;
                if (r0 != labelCount) {
                    this.mCalculatedLabelBreakPoints = new Boolean[labelCount];
                }
                int calculatedLabelBreakIndex = 0;
                ArrayList<FSize> calculatedLineSizes = this.calculatedLineSizesForCalculateDimensions;
                FSize.recycleInstances(calculatedLineSizes);
                calculatedLineSizes.clear();
                float maxLineWidth = 0.0f;
                float currentLineWidth = 0.0f;
                float requiredWidth = 0.0f;
                int stackedStartIndex = -1;
                i = 0;
                while (i < labelCount) {
                    drawingForm = this.mColors[i] != 1122868;
                    this.mCalculatedLabelBreakPoints[calculatedLabelBreakIndex] = Boolean.valueOf(false);
                    calculatedLabelBreakIndex++;
                    if (stackedStartIndex == -1) {
                        requiredWidth = 0.0f;
                    } else {
                        requiredWidth += this.mStackSpace;
                    }
                    if (this.mLabels[i] != null) {
                        if (this.mCalculatedLabelSizes[calculatedLabelSizesIndex] == null) {
                            this.mCalculatedLabelSizes[calculatedLabelSizesIndex] = Utils.calcTextSize(labelpaint, this.mLabels[i]);
                        } else {
                            Utils.calcTextSize(labelpaint, this.mLabels[i], this.mCalculatedLabelSizes[calculatedLabelSizesIndex]);
                        }
                        FSize labelSize = this.mCalculatedLabelSizes[calculatedLabelSizesIndex];
                        calculatedLabelSizesIndex++;
                        requiredWidth = (requiredWidth + (drawingForm ? this.mFormToTextSpace + this.mFormSize : 0.0f)) + labelSize.width;
                    } else {
                        if (this.mCalculatedLabelSizes[calculatedLabelSizesIndex] == null) {
                            this.mCalculatedLabelSizes[calculatedLabelSizesIndex] = FSize.getInstance(0.0f, 0.0f);
                        } else {
                            this.mCalculatedLabelSizes[calculatedLabelSizesIndex].width = 0.0f;
                            this.mCalculatedLabelSizes[calculatedLabelSizesIndex].height = 0.0f;
                        }
                        calculatedLabelSizesIndex++;
                        requiredWidth += drawingForm ? this.mFormSize : 0.0f;
                        if (stackedStartIndex == -1) {
                            stackedStartIndex = i;
                        }
                    }
                    if (this.mLabels[i] != null || i == labelCount - 1) {
                        float requiredSpacing;
                        if (currentLineWidth == 0.0f) {
                            requiredSpacing = 0.0f;
                        } else {
                            requiredSpacing = this.mXEntrySpace;
                        }
                        if (!this.mWordWrapEnabled || currentLineWidth == 0.0f || contentWidth - currentLineWidth >= requiredSpacing + requiredWidth) {
                            currentLineWidth += requiredSpacing + requiredWidth;
                        } else {
                            calculatedLineSizes.add(FSize.getInstance(currentLineWidth, labelLineHeight));
                            maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                            Boolean[] boolArr = this.mCalculatedLabelBreakPoints;
                            if (stackedStartIndex > -1) {
                                length = stackedStartIndex;
                            } else {
                                length = i;
                            }
                            boolArr[length] = Boolean.valueOf(true);
                            currentLineWidth = requiredWidth;
                        }
                        if (i == labelCount - 1) {
                            calculatedLineSizes.add(FSize.getInstance(currentLineWidth, labelLineHeight));
                            maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                        }
                    }
                    if (this.mLabels[i] != null) {
                        stackedStartIndex = -1;
                    }
                    i++;
                }
                if (calculatedLineSizes.size() != this.mCalculatedLineSizes.length) {
                    this.isCalculatedLineSizesArrayListResized = true;
                } else {
                    i = 0;
                    while (true) {
                        length = this.mCalculatedLineSizes.length;
                        if (i < r0) {
                            this.mCalculatedLineSizes[i] = (FSize) calculatedLineSizes.get(i);
                            i++;
                        }
                    }
                }
                this.mNeededWidth = maxLineWidth;
                float length2 = labelLineHeight * ((float) this.mCalculatedLineSizes.length);
                if (this.mCalculatedLineSizes.length == 0) {
                    length = 0;
                } else {
                    length = this.mCalculatedLineSizes.length - 1;
                }
                this.mNeededHeight = (((float) length) * labelLineSpacing) + length2;
            default:
        }
    }
}
