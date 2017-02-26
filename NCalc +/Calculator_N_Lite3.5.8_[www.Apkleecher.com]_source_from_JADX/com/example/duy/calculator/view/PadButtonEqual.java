package com.example.duy.calculator.view;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.widget.ImageView.ScaleType;
import com.example.duy.calculator.MainCalculatorActivity;

public class PadButtonEqual extends FloatingActionButton implements OnLongClickListener {
    MainCalculatorActivity activity;
    EventListener mListener;

    public PadButtonEqual(Context context) {
        super(context);
        setup(context, null, -1);
    }

    public PadButtonEqual(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, -1);
    }

    public PadButtonEqual(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    void setup(Context context, AttributeSet attributeSet, int def) {
        if (!isInEditMode()) {
            this.activity = (MainCalculatorActivity) context;
            this.mListener = this.activity.mListener;
            setOnClickListener(this.mListener);
            setOnLongClickListener(this);
            setOnDragListener(this.mListener);
            setScaleType(ScaleType.CENTER_INSIDE);
        }
    }

    public boolean onLongClick(View v) {
        String[] mimeTypes = new String[]{"text/plain"};
        v.startDrag(new ClipData("=", mimeTypes, new Item("=")), new DragShadowBuilder(v), v, 0);
        return false;
    }
}
