package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendDirection;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment;
import com.github.mikephil.charting.components.Legend.LegendOrientation;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class LegendRenderer extends Renderer {
    protected ArrayList<Integer> colorsForComputeLegend;
    protected FontMetrics fontMetricsForRenderLegent;
    protected ArrayList<String> labelsForComputeLegend;
    protected Legend mLegend;
    protected Paint mLegendFormPaint;
    protected Paint mLegendLabelPaint;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment;

        static {
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm = new int[LegendForm.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[LegendForm.CIRCLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[LegendForm.SQUARE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[LegendForm.LINE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation = new int[LegendOrientation.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[LegendOrientation.HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[LegendOrientation.VERTICAL.ordinal()] = 2;
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
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[LegendVerticalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e8) {
            }
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment = new int[LegendHorizontalAlignment.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[LegendHorizontalAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[LegendHorizontalAlignment.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[LegendHorizontalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    public LegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler);
        this.labelsForComputeLegend = new ArrayList(16);
        this.colorsForComputeLegend = new ArrayList(16);
        this.fontMetricsForRenderLegent = new FontMetrics();
        this.mLegend = legend;
        this.mLegendLabelPaint = new Paint(1);
        this.mLegendLabelPaint.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mLegendLabelPaint.setTextAlign(Align.LEFT);
        this.mLegendFormPaint = new Paint(1);
        this.mLegendFormPaint.setStyle(Style.FILL);
        this.mLegendFormPaint.setStrokeWidth(3.0f);
    }

    public Paint getLabelPaint() {
        return this.mLegendLabelPaint;
    }

    public Paint getFormPaint() {
        return this.mLegendFormPaint;
    }

    public void computeLegend(ChartData<?> data) {
        if (!this.mLegend.isLegendCustom()) {
            ArrayList<String> labels = this.labelsForComputeLegend;
            ArrayList<Integer> colors = this.colorsForComputeLegend;
            labels.clear();
            colors.clear();
            for (int i = 0; i < data.getDataSetCount(); i++) {
                IDataSet dataSet = data.getDataSetByIndex(i);
                List<Integer> clrs = dataSet.getColors();
                int entryCount = dataSet.getEntryCount();
                int j;
                if ((dataSet instanceof IBarDataSet) && ((IBarDataSet) dataSet).isStacked()) {
                    IBarDataSet bds = (IBarDataSet) dataSet;
                    String[] sLabels = bds.getStackLabels();
                    j = 0;
                    while (j < clrs.size() && j < bds.getStackSize()) {
                        labels.add(sLabels[j % sLabels.length]);
                        colors.add(clrs.get(j));
                        j++;
                    }
                    if (bds.getLabel() != null) {
                        colors.add(Integer.valueOf(ColorTemplate.COLOR_SKIP));
                        labels.add(bds.getLabel());
                    }
                } else if (dataSet instanceof IPieDataSet) {
                    IPieDataSet pds = (IPieDataSet) dataSet;
                    j = 0;
                    while (j < clrs.size() && j < entryCount) {
                        labels.add(((PieEntry) pds.getEntryForIndex(j)).getLabel());
                        colors.add(clrs.get(j));
                        j++;
                    }
                    if (pds.getLabel() != null) {
                        colors.add(Integer.valueOf(ColorTemplate.COLOR_SKIP));
                        labels.add(pds.getLabel());
                    }
                } else if (!(dataSet instanceof ICandleDataSet) || ((ICandleDataSet) dataSet).getDecreasingColor() == 1122867) {
                    j = 0;
                    while (j < clrs.size() && j < entryCount) {
                        if (j >= clrs.size() - 1 || j >= entryCount - 1) {
                            labels.add(data.getDataSetByIndex(i).getLabel());
                        } else {
                            labels.add(null);
                        }
                        colors.add(clrs.get(j));
                        j++;
                    }
                } else {
                    colors.add(Integer.valueOf(((ICandleDataSet) dataSet).getDecreasingColor()));
                    colors.add(Integer.valueOf(((ICandleDataSet) dataSet).getIncreasingColor()));
                    labels.add(null);
                    labels.add(dataSet.getLabel());
                }
            }
            if (this.mLegend.getExtraColors() != null) {
                if (this.mLegend.getExtraLabels() != null) {
                    for (int color : this.mLegend.getExtraColors()) {
                        colors.add(Integer.valueOf(color));
                    }
                    Collections.addAll(labels, this.mLegend.getExtraLabels());
                }
            }
            this.mLegend.setComputedColors(colors);
            this.mLegend.setComputedLabels(labels);
        }
        Typeface tf = this.mLegend.getTypeface();
        if (tf != null) {
            this.mLegendLabelPaint.setTypeface(tf);
        }
        this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
        this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
        this.mLegend.calculateDimensions(this.mLegendLabelPaint, this.mViewPortHandler);
    }

    public void renderLegend(Canvas c) {
        if (this.mLegend.isEnabled()) {
            Typeface tf = this.mLegend.getTypeface();
            if (tf != null) {
                this.mLegendLabelPaint.setTypeface(tf);
            }
            this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
            this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
            float labelLineHeight = Utils.getLineHeight(this.mLegendLabelPaint, this.fontMetricsForRenderLegent);
            float labelLineSpacing = Utils.getLineSpacing(this.mLegendLabelPaint, this.fontMetricsForRenderLegent) + this.mLegend.getYEntrySpace();
            float formYOffset = labelLineHeight - (((float) Utils.calcTextHeight(this.mLegendLabelPaint, "ABC")) / 2.0f);
            String[] labels = this.mLegend.getLabels();
            int[] colors = this.mLegend.getColors();
            float formToTextSpace = this.mLegend.getFormToTextSpace();
            float xEntrySpace = this.mLegend.getXEntrySpace();
            LegendOrientation orientation = this.mLegend.getOrientation();
            LegendHorizontalAlignment horizontalAlignment = this.mLegend.getHorizontalAlignment();
            LegendVerticalAlignment verticalAlignment = this.mLegend.getVerticalAlignment();
            LegendDirection direction = this.mLegend.getDirection();
            float formSize = this.mLegend.getFormSize();
            float stackSpace = this.mLegend.getStackSpace();
            float yoffset = this.mLegend.getYOffset();
            float xoffset = this.mLegend.getXOffset();
            float originPosX = 0.0f;
            switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[horizontalAlignment.ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    if (orientation == LegendOrientation.VERTICAL) {
                        originPosX = xoffset;
                    } else {
                        originPosX = this.mViewPortHandler.contentLeft() + xoffset;
                    }
                    if (direction == LegendDirection.RIGHT_TO_LEFT) {
                        originPosX += this.mLegend.mNeededWidth;
                        break;
                    }
                    break;
                case IExpr.DOUBLEID /*2*/:
                    if (orientation == LegendOrientation.VERTICAL) {
                        originPosX = this.mViewPortHandler.getChartWidth() - xoffset;
                    } else {
                        originPosX = this.mViewPortHandler.contentRight() - xoffset;
                    }
                    if (direction == LegendDirection.LEFT_TO_RIGHT) {
                        originPosX -= this.mLegend.mNeededWidth;
                        break;
                    }
                    break;
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    if (orientation == LegendOrientation.VERTICAL) {
                        originPosX = this.mViewPortHandler.getChartWidth() / 2.0f;
                    } else {
                        originPosX = this.mViewPortHandler.contentLeft() + (this.mViewPortHandler.contentWidth() / 2.0f);
                    }
                    originPosX += direction == LegendDirection.LEFT_TO_RIGHT ? xoffset : -xoffset;
                    if (orientation == LegendOrientation.VERTICAL) {
                        double d;
                        double d2 = (double) originPosX;
                        if (direction == LegendDirection.LEFT_TO_RIGHT) {
                            d = (((double) (-this.mLegend.mNeededWidth)) / 2.0d) + ((double) xoffset);
                        } else {
                            d = (((double) this.mLegend.mNeededWidth) / 2.0d) - ((double) xoffset);
                        }
                        originPosX = (float) (d + d2);
                        break;
                    }
                    break;
            }
            float posX;
            float posY;
            int i;
            switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[orientation.ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    FSize[] calculatedLineSizes = this.mLegend.getCalculatedLineSizes();
                    FSize[] calculatedLabelSizes = this.mLegend.getCalculatedLabelSizes();
                    Boolean[] calculatedLabelBreakPoints = this.mLegend.getCalculatedLabelBreakPoints();
                    posX = originPosX;
                    posY = 0.0f;
                    switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[verticalAlignment.ordinal()]) {
                        case ValueServer.REPLAY_MODE /*1*/:
                            posY = yoffset;
                            break;
                        case IExpr.DOUBLEID /*2*/:
                            posY = (this.mViewPortHandler.getChartHeight() - yoffset) - this.mLegend.mNeededHeight;
                            break;
                        case ValueServer.EXPONENTIAL_MODE /*3*/:
                            posY = ((this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0f) + yoffset;
                            break;
                    }
                    int lineIndex = 0;
                    i = 0;
                    int count = labels.length;
                    while (i < count) {
                        if (i < calculatedLabelBreakPoints.length && calculatedLabelBreakPoints[i].booleanValue()) {
                            posX = originPosX;
                            posY += labelLineHeight + labelLineSpacing;
                        }
                        if (posX == originPosX && horizontalAlignment == LegendHorizontalAlignment.CENTER && lineIndex < calculatedLineSizes.length) {
                            posX += (direction == LegendDirection.RIGHT_TO_LEFT ? calculatedLineSizes[lineIndex].width : -calculatedLineSizes[lineIndex].width) / 2.0f;
                            lineIndex++;
                        }
                        boolean drawingForm = colors[i] != 1122868;
                        boolean isStacked = labels[i] == null;
                        if (drawingForm) {
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                posX -= formSize;
                            }
                            drawForm(c, posX, posY + formYOffset, i, this.mLegend);
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX += formSize;
                            }
                        }
                        if (isStacked) {
                            posX += direction == LegendDirection.RIGHT_TO_LEFT ? -stackSpace : stackSpace;
                        } else {
                            float f;
                            if (drawingForm) {
                                if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                    f = -formToTextSpace;
                                } else {
                                    f = formToTextSpace;
                                }
                                posX += f;
                            }
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                posX -= calculatedLabelSizes[i].width;
                            }
                            drawLabel(c, posX, posY + labelLineHeight, labels[i]);
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX += calculatedLabelSizes[i].width;
                            }
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                f = -xEntrySpace;
                            } else {
                                f = xEntrySpace;
                            }
                            posX += f;
                        }
                        i++;
                    }
                case IExpr.DOUBLEID /*2*/:
                    float stack = 0.0f;
                    boolean wasStacked = false;
                    posY = 0.0f;
                    switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[verticalAlignment.ordinal()]) {
                        case ValueServer.REPLAY_MODE /*1*/:
                            if (horizontalAlignment == LegendHorizontalAlignment.CENTER) {
                                posY = 0.0f;
                            } else {
                                posY = this.mViewPortHandler.contentTop();
                            }
                            posY += yoffset;
                            break;
                        case IExpr.DOUBLEID /*2*/:
                            if (horizontalAlignment == LegendHorizontalAlignment.CENTER) {
                                posY = this.mViewPortHandler.getChartHeight();
                            } else {
                                posY = this.mViewPortHandler.contentBottom();
                            }
                            posY -= this.mLegend.mNeededHeight + yoffset;
                            break;
                        case ValueServer.EXPONENTIAL_MODE /*3*/:
                            posY = ((this.mViewPortHandler.getChartHeight() / 2.0f) - (this.mLegend.mNeededHeight / 2.0f)) + this.mLegend.getYOffset();
                            break;
                    }
                    for (i = 0; i < labels.length; i++) {
                        Boolean drawingForm2 = Boolean.valueOf(colors[i] != 1122868);
                        posX = originPosX;
                        if (drawingForm2.booleanValue()) {
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX += stack;
                            } else {
                                posX -= formSize - stack;
                            }
                            drawForm(c, posX, posY + formYOffset, i, this.mLegend);
                            if (direction == LegendDirection.LEFT_TO_RIGHT) {
                                posX += formSize;
                            }
                        }
                        if (labels[i] != null) {
                            if (drawingForm2.booleanValue() && !wasStacked) {
                                posX += direction == LegendDirection.LEFT_TO_RIGHT ? formToTextSpace : -formToTextSpace;
                            } else if (wasStacked) {
                                posX = originPosX;
                            }
                            if (direction == LegendDirection.RIGHT_TO_LEFT) {
                                posX -= (float) Utils.calcTextWidth(this.mLegendLabelPaint, labels[i]);
                            }
                            if (wasStacked) {
                                posY += labelLineHeight + labelLineSpacing;
                                drawLabel(c, posX, posY + labelLineHeight, labels[i]);
                            } else {
                                drawLabel(c, posX, posY + labelLineHeight, labels[i]);
                            }
                            posY += labelLineHeight + labelLineSpacing;
                            stack = 0.0f;
                        } else {
                            stack += formSize + stackSpace;
                            wasStacked = true;
                        }
                    }
                default:
            }
        }
    }

    protected void drawForm(Canvas c, float x, float y, int index, Legend legend) {
        if (legend.getColors()[index] != 1122868) {
            this.mLegendFormPaint.setColor(legend.getColors()[index]);
            float formsize = legend.getFormSize();
            float half = formsize / 2.0f;
            switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[legend.getForm().ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    c.drawCircle(x + half, y, half, this.mLegendFormPaint);
                case IExpr.DOUBLEID /*2*/:
                    c.drawRect(x, y - half, x + formsize, y + half, this.mLegendFormPaint);
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    c.drawLine(x, y, x + formsize, y, this.mLegendFormPaint);
                default:
            }
        }
    }

    protected void drawLabel(Canvas c, float x, float y, String label) {
        c.drawText(label, x, y, this.mLegendLabelPaint);
    }
}
