package com.example.duy.calculator.math_eval;

public enum Base {
    BINARY(2),
    OCTAL(8),
    DECIMAL(10),
    HEXADECIMAL(16);
    
    int quickSerializable;

    private Base(int num) {
        this.quickSerializable = num;
    }

    public int getQuickSerializable() {
        return this.quickSerializable;
    }
}
