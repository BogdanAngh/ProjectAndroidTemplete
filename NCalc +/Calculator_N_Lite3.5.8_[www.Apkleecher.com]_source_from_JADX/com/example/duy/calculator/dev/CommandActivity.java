package com.example.duy.calculator.dev;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.duy.calculator.R;
import com.example.duy.calculator.math_eval.BigEvaluator;

public class CommandActivity extends AppCompatActivity {
    private BigEvaluator evaluator;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.command);
    }
}
