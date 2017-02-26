package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class HorizontalBarBuffer extends BarBuffer {
    public HorizontalBarBuffer(int size, int dataSetCount, boolean containsStacks) {
        super(size, dataSetCount, containsStacks);
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
                    float bottom;
                    float top;
                    float left;
                    float right;
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
                            bottom = x - barWidthHalf;
                            top = x + barWidthHalf;
                            if (this.mInverted) {
                                if (y >= yStart) {
                                    left = y;
                                } else {
                                    left = yStart;
                                }
                                if (y <= yStart) {
                                    right = y;
                                } else {
                                    right = yStart;
                                }
                            } else {
                                if (y >= yStart) {
                                    right = y;
                                } else {
                                    right = yStart;
                                }
                                if (y <= yStart) {
                                    left = y;
                                } else {
                                    left = yStart;
                                }
                            }
                            addBar(left * this.phaseY, top, right * this.phaseY, bottom);
                            k++;
                        }
                    } else {
                        bottom = x - barWidthHalf;
                        top = x + barWidthHalf;
                        if (this.mInverted) {
                            left = y >= 0.0f ? y : 0.0f;
                            right = y <= 0.0f ? y : 0.0f;
                        } else {
                            right = y >= 0.0f ? y : 0.0f;
                            left = y <= 0.0f ? y : 0.0f;
                        }
                        if (right > 0.0f) {
                            right *= this.phaseY;
                        } else {
                            left *= this.phaseY;
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
