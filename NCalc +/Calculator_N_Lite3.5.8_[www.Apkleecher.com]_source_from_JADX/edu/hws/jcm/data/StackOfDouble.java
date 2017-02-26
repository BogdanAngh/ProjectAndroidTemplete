package edu.hws.jcm.data;

import java.io.Serializable;
import java.util.EmptyStackException;

public class StackOfDouble implements Serializable {
    private double[] data;
    private int top;

    public StackOfDouble() {
        this.data = new double[1];
    }

    public StackOfDouble(int initialSize) {
        if (initialSize <= 0) {
            initialSize = 1;
        }
        this.data = new double[initialSize];
    }

    public void push(double x) {
        if (this.top >= this.data.length) {
            double[] temp = new double[(this.data.length * 2)];
            System.arraycopy(this.data, 0, temp, 0, this.data.length);
            this.data = temp;
        }
        double[] dArr = this.data;
        int i = this.top;
        this.top = i + 1;
        dArr[i] = x;
    }

    public double pop() {
        if (this.top == 0) {
            throw new EmptyStackException();
        }
        double[] dArr = this.data;
        int i = this.top - 1;
        this.top = i;
        return dArr[i];
    }

    public boolean isEmpty() {
        return this.top == 0;
    }

    public void makeEmpty() {
        this.top = 0;
    }

    public int size() {
        return this.top;
    }
}
