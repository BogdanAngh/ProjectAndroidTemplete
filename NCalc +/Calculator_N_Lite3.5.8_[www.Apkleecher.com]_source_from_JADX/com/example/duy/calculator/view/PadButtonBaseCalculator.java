package com.example.duy.calculator.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import edu.jas.vector.GenVectorModul;

public class PadButtonBaseCalculator extends Button {
    private Context context;

    public PadButtonBaseCalculator(Context context) {
        super(context);
        init(context);
    }

    public PadButtonBaseCalculator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PadButtonBaseCalculator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        this.context = context;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            animate().alpha(1.0f).start();
        } else {
            animate().alpha(GenVectorModul.DEFAULT_DENSITY).start();
        }
        super.setEnabled(enabled);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            animate().alpha(1.0f).start();
        } else {
            animate().alpha(0.3f).start();
        }
        super.setSelected(selected);
    }
}
