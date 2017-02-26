package com.example.duy.calculator.history;

import java.io.Serializable;
import java.util.Date;

public class ItemHistory implements Serializable {
    public static final int TYPE_COMPLEX = 2;
    public static final int TYPE_GRAPH = 3;
    public static final int TYPE_LINEAR_SYSTEM = 3;
    public static final int TYPE_LOGIC = 1;
    public static final int TYPE_MAXTRIX = 5;
    public static final int TYPE_SCIENCE = 0;
    public static final int TYPE_SOLVE_ROOT = 4;
    public static final long serialVersionUID = 4;
    private int color;
    private String math;
    private String result;
    private long time;
    private int type;

    public ItemHistory(String math, String res) {
        this(math, res, TYPE_SCIENCE, new Date().getTime());
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ItemHistory(String math, String result, long time) {
        this(math, result, TYPE_SCIENCE, time);
    }

    public ItemHistory(String math, String result, int color, long time) {
        this(math, result, color, time, TYPE_SCIENCE);
    }

    public ItemHistory(String math, String result, int color, long time, int type) {
        this.type = TYPE_SCIENCE;
        this.color = TYPE_SCIENCE;
        this.math = math;
        this.result = result;
        this.color = color;
        this.time = time;
        this.type = type;
    }

    public String getMath() {
        return this.math;
    }

    public void setMath(String math) {
        this.math = math;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String toString() {
        return this.math + "  = " + this.result;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
