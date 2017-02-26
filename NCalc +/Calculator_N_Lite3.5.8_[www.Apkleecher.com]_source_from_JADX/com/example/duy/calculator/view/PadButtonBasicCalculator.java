package com.example.duy.calculator.view;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import com.example.duy.calculator.MainCalculatorActivity;

public class PadButtonBasicCalculator extends AppCompatButton implements OnLongClickListener {
    MainCalculatorActivity activity;
    EventListener mListener;

    public PadButtonBasicCalculator(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(context);
        }
    }

    public PadButtonBasicCalculator(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context);
        }
    }

    public PadButtonBasicCalculator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context);
        }
    }

    private void init(Context context) {
        this.activity = (MainCalculatorActivity) context;
        this.mListener = this.activity.mListener;
        setOnClickListener(this.mListener);
        setOnLongClickListener(this);
        setOnDragListener(this.mListener);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    public boolean onLongClick(View v) {
        String[] mimeTypes = new String[]{"text/plain"};
        v.startDrag(new ClipData(((TextView) v).getText().toString(), mimeTypes, new Item(((TextView) v).getText().toString())), new DragShadowBuilder(v), v, 0);
        return false;
    }
}
