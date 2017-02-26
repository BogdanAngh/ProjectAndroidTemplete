package com.example.duy.calculator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.example.duy.calculator.R;
import com.example.duy.calculator.math_eval.Constants;
import com.example.duy.calculator.utils.TextUtil;

public class ResizingEditText extends AppCompatEditText {
    private int mHeightConstraint;
    private boolean mIsInserting;
    private float mMaximumTextSize;
    private float mMinimumTextSize;
    private OnTextSizeChangeListener mOnTextSizeChangeListener;
    private float mStepTextSize;
    private final Paint mTempPaint;
    private int mWidthConstraint;

    public interface OnTextSizeChangeListener {
        void onTextSizeChanged(TextView textView, float f);
    }

    public ResizingEditText(Context context) {
        super(context);
        this.mTempPaint = new TextPaint();
        this.mWidthConstraint = -1;
        this.mHeightConstraint = -1;
        this.mIsInserting = false;
        setUp(context, null);
    }

    public ResizingEditText(Context context, AttributeSet attr) {
        super(context, attr);
        this.mTempPaint = new TextPaint();
        this.mWidthConstraint = -1;
        this.mHeightConstraint = -1;
        this.mIsInserting = false;
        setUp(context, attr);
    }

    private void setUp(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ResizingEditText, 0, 0);
            this.mMaximumTextSize = a.getDimension(1, getTextSize());
            this.mMinimumTextSize = a.getDimension(0, getTextSize());
            this.mStepTextSize = a.getDimension(2, (this.mMaximumTextSize - this.mMinimumTextSize) / 3.0f);
            a.recycle();
            setTextSize(0, this.mMaximumTextSize);
            setMinimumHeight((((int) (((double) this.mMaximumTextSize) * 1.2d)) + getPaddingBottom()) + getPaddingTop());
        }
    }

    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        invalidateTextSize();
    }

    protected void invalidateTextSize() {
        float oldTextSize = getTextSize();
        float newTextSize = getVariableTextSize(getText().toString());
        if (oldTextSize != newTextSize) {
            setTextSize(0, newTextSize);
        }
    }

    public void setTextSize(int unit, float size) {
        float oldTextSize = getTextSize();
        super.setTextSize(unit, size);
        if (this.mOnTextSizeChangeListener != null && getTextSize() != oldTextSize) {
            this.mOnTextSizeChangeListener.onTextSizeChanged(this, oldTextSize);
        }
    }

    public void setOnTextSizeChangeListener(OnTextSizeChangeListener listener) {
        this.mOnTextSizeChangeListener = listener;
    }

    public float getVariableTextSize(String text) {
        if (this.mWidthConstraint < 0 || this.mMaximumTextSize <= this.mMinimumTextSize) {
            return getTextSize();
        }
        int exponents = TextUtil.countOccurrences(text, Constants.POWER_UNICODE);
        float lastFitTextSize = this.mMinimumTextSize;
        while (lastFitTextSize < this.mMaximumTextSize) {
            float nextSize = Math.min(this.mStepTextSize + lastFitTextSize, this.mMaximumTextSize);
            this.mTempPaint.setTextSize(nextSize);
            if (this.mTempPaint.measureText(text) > ((float) this.mWidthConstraint) || ((((float) exponents) * nextSize) / 2.0f) + nextSize > ((float) this.mHeightConstraint)) {
                return lastFitTextSize;
            }
            lastFitTextSize = nextSize;
        }
        return lastFitTextSize;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidthConstraint = (MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()) - getPaddingRight();
        this.mHeightConstraint = (MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
        setTextSize(0, getVariableTextSize(getText().toString()));
    }

    public String getCleanText() {
        return getText().toString();
    }

    public void insert(String delta) {
        String currentText = getText().toString();
        int selectionHandle = getSelectionStart();
        String textBeforeInsertionHandle = currentText.substring(0, selectionHandle);
        setText(textBeforeInsertionHandle + delta + currentText.substring(selectionHandle, currentText.length()));
        setSelection(delta.length() + selectionHandle);
    }

    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        invalidateTextSize();
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}
