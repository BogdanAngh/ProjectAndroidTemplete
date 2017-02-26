package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarBuffer extends AbstractBuffer<IBarDataSet> {
    protected float mBarWidth;
    protected boolean mContainsStacks;
    protected int mDataSetCount;
    protected int mDataSetIndex;
    protected boolean mInverted;

    public BarBuffer(int size, int dataSetCount, boolean containsStacks) {
        super(size);
        this.mDataSetIndex = 0;
        this.mDataSetCount = 1;
        this.mContainsStacks = false;
        this.mInverted = false;
        this.mBarWidth = 1.0f;
        this.mDataSetCount = dataSetCount;
        this.mContainsStacks = containsStacks;
    }

    public void setBarWidth(float barWidth) {
        this.mBarWidth = barWidth;
    }

    public void setDataSet(int index) {
        this.mDataSetIndex = index;
    }

    public void setInverted(boolean inverted) {
        this.mInverted = inverted;
    }

    protected void addBar(float left, float top, float right, float bottom) {
        float[] fArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        fArr[i] = left;
        fArr = this.buffer;
        i = this.index;
        this.index = i + 1;
        fArr[i] = top;
        fArr = this.buffer;
        i = this.index;
        this.index = i + 1;
        fArr[i] = right;
        fArr = this.buffer;
        i = this.index;
        this.index = i + 1;
        fArr[i] = bottom;
    }

    public void feed(IBarDataSet data) {
        float size = ((float) data.getEntryCount()) * this.phaseX;
        float barWidthHalf = this.mBarWidth / 2.0f;
        int i = 0;
        while (true) {
            if (((float) i) < size) {
                BarEntry e = (BarEntry) data.getEntryForIndex(i);
                if (e != null) {
                    float x = e.getX();
                    float y = e.getY();
                    float[] vals = e.getYVals();
                    float left;
                    float right;
                    float bottom;
                    float top;
                    if (this.mContainsStacks && vals != null) {
                        float posY = 0.0f;
                        float negY = -e.getNegativeSum();
                        int k = 0;
                        while (true) {
                            int length = vals.length;
                            if (k >= r0) {
                                break;
                            }
                            float yStart;
                            float value = vals[k];
                            if (value >= 0.0f) {
                                y = posY;
                                yStart = posY + value;
                                posY = yStart;
                            } else {
                                y = negY;
                                yStart = negY + Math.abs(value);
                                negY += Math.abs(value);
                            }
                            left = x - barWidthHalf;
                            right = x + barWidthHalf;
                            if (this.mInverted) {
                                if (y >= yStart) {
                                    bottom = y;
                                } else {
                                    bottom = yStart;
                                }
                                if (y <= yStart) {
                                    top = y;
                                } else {
                                    top = yStart;
                                }
                            } else {
                                if (y >= yStart) {
                                    top = y;
                                } else {
                                    top = yStart;
                                }
                                if (y <= yStart) {
                                    bottom = y;
                                } else {
                                    bottom = yStart;
                                }
                            }
                            addBar(left, top * this.phaseY, right, bottom * this.phaseY);
                            k++;
                        }
                    } else {
                        left = x - barWidthHalf;
                        right = x + barWidthHalf;
                        if (this.mInverted) {
                            bottom = y >= 0.0f ? y : 0.0f;
                            top = y <= 0.0f ? y : 0.0f;
                        } else {
                            top = y >= 0.0f ? y : 0.0f;
                            bottom = y <= 0.0f ? y : 0.0f;
                        }
                        if (top > 0.0f) {
                            top *= this.phaseY;
                        } else {
                            bottom *= this.phaseY;
                        }
                        addBar(left, top, right, bottom);
                    }
                }
                i++;
            } else {
                reset();
                return;
            }
        }
    }
}
