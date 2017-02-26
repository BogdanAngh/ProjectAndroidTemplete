package com.example.duy.calculator;

public interface Calculator {
    void onClear();

    void onDelete();

    void onEqual();

    void onError(String str);

    void onInputVoice();

    void onResult(String str);
}
