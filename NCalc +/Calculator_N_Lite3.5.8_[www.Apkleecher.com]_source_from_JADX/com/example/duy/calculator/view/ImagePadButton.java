package com.example.duy.calculator.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.example.duy.calculator.MainCalculatorActivity;

public class ImagePadButton extends ImageView {
    MainCalculatorActivity activity;
    EventListener mListener;

    public ImagePadButton(Context context) {
        super(context);
        init(context);
    }

    public ImagePadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImagePadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            this.activity = (MainCalculatorActivity) context;
            this.mListener = this.activity.mListener;
            setOnClickListener(this.mListener);
            setOnLongClickListener(this.mListener);
            setOnDragListener(this.mListener);
        }
    }
}
