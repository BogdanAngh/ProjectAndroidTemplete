package com.example.duy.calculator.converter.utils;

public interface Strategy {
    double Convert(String str, String str2, double d);

    String getUnitDefault();
}
