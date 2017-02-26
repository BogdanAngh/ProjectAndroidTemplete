package com.example.duy.calculator.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView.BufferType;
import com.example.duy.calculator.R;
import com.example.duy.calculator.math_eval.BaseModule;
import com.example.duy.calculator.math_eval.Constants;
import com.example.duy.calculator.math_eval.FormatExpression;
import com.example.duy.calculator.math_eval.Solver;
import com.example.duy.calculator.utils.TextUtil;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CalculatorEditText extends AppCompatEditText {
    public static final String TAG;
    private boolean formatText;
    private boolean mDebug;
    private FormatExpression mFormatExpression;
    private boolean mIsInserting;
    private List<String> mKeywords;
    private ArrayList<Translate> mReplacement;
    private Solver mSolver;
    private final TextWatcher mTextWatcher;
    private final Set<TextWatcher> mTextWatchers;
    private boolean mTextWatchersEnabled;

    class 1 implements TextWatcher {
        1() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (CalculatorEditText.this.mTextWatchersEnabled && CalculatorEditText.this.getSelectionStart() != -1) {
                CalculatorEditText.this.mTextWatchersEnabled = false;
                CalculatorEditText.this.onFormat(s);
                CalculatorEditText.this.mTextWatchersEnabled = true;
            }
        }
    }

    public class MutableInteger {
        private int value;

        public MutableInteger(int value) {
            this.value = value;
        }

        public void set(int value) {
            this.value = value;
        }

        public void add(int value) {
            this.value += value;
        }

        public int intValue() {
            return this.value;
        }

        public String toString() {
            return Integer.toString(this.value);
        }
    }

    class Translate {
        String source;
        String view;

        Translate(String source, String view) {
            this.source = source;
            this.view = view;
        }

        public String getSource() {
            return this.source;
        }

        public String getView() {
            return this.view;
        }
    }

    static {
        TAG = CalculatorEditText.class.getName();
    }

    public CalculatorEditText(Context context) {
        super(context);
        this.mTextWatchers = new HashSet();
        this.mDebug = false;
        this.mTextWatchersEnabled = true;
        this.mReplacement = new ArrayList();
        this.formatText = true;
        this.mTextWatcher = new 1();
        setUp(context, null);
    }

    public CalculatorEditText(Context context, AttributeSet attr) {
        super(context, attr);
        this.mTextWatchers = new HashSet();
        this.mDebug = false;
        this.mTextWatchersEnabled = true;
        this.mReplacement = new ArrayList();
        this.formatText = true;
        this.mTextWatcher = new 1();
        setUp(context, attr);
    }

    private void setUp(Context context, AttributeSet attrs) {
        this.mFormatExpression = new FormatExpression();
        addTextChangedListener(this.mTextWatcher);
        generalReplacement(context);
        invalidateKeywords(context);
    }

    public void invalidateKeywords(Context context) {
        this.mKeywords = Arrays.asList(new String[]{context.getString(R.string.fun_arcsin) + "(", context.getString(R.string.fun_arccos) + "(", context.getString(R.string.fun_arctan) + "(", context.getString(R.string.arcsin) + "(", context.getString(R.string.arccos) + "(", context.getString(R.string.arctan) + "(", context.getString(R.string.fun_sin) + "(", context.getString(R.string.fun_cos) + "(", context.getString(R.string.fun_tan) + "(", context.getString(R.string.sin) + "(", context.getString(R.string.cos) + "(", context.getString(R.string.tan) + "(", context.getString(R.string.fun_arccsc) + "(", context.getString(R.string.fun_arcsec) + "(", context.getString(R.string.fun_arccot) + "(", context.getString(R.string.fun_csc) + "(", context.getString(R.string.fun_sec) + "(", context.getString(R.string.fun_cot) + "(", context.getString(R.string.fun_log) + "(", context.getString(R.string.mod) + "(", context.getString(R.string.fun_ln) + "(", context.getString(R.string.op_cbrt) + "(", context.getString(R.string.tanh) + "(", context.getString(R.string.cosh) + "(", context.getString(R.string.sinh) + "(", context.getString(R.string.log2) + "(", context.getString(R.string.log10) + "(", context.getString(R.string.abs) + "(", context.getString(R.string.sgn) + "(", context.getString(R.string.floor) + "(", context.getString(R.string.ceil) + "(", context.getString(R.string.atanh) + "(", context.getString(R.string.sum) + "(", context.getString(R.string.diff) + "(", context.getString(R.string.avg) + "(", context.getString(R.string.vari) + "(", context.getString(R.string.stdi) + "(", context.getString(R.string.mini) + "(", context.getString(R.string.maxi) + "(", context.getString(R.string.min) + "(", context.getString(R.string.max) + "(", context.getString(R.string.std) + "(", context.getString(R.string.mean) + "(", context.getString(R.string.sqrt_sym) + "(", context.getString(R.string.log2) + "(", context.getString(R.string.lg) + "(", context.getString(R.string.cot) + "(", context.getString(R.string.exp) + "(", context.getString(R.string.sign) + "(", context.getString(R.string.arg) + "(", context.getString(R.string.gcd_up) + "(", context.getString(R.string.log2) + "(", context.getString(R.string.ln) + "(", context.getString(R.string.ln) + "(", context.getString(R.string.log2) + "(", context.getString(R.string.asinh) + "(", context.getString(R.string.acosh) + "(", context.getString(R.string.atanh) + "(", context.getString(R.string.op_cbrt) + "(", context.getString(R.string.permutations) + "(", context.getString(R.string.binomial) + "(", context.getString(R.string.trunc) + "(", context.getString(R.string.max) + "(", context.getString(R.string.min) + "(", context.getString(R.string.mod) + "(", context.getString(R.string.gcd) + "(", context.getString(R.string.lcm) + "(", context.getString(R.string.sign) + "(", context.getString(R.string.rnd) + "(", context.getString(R.string.ans), context.getString(R.string.mtrue), context.getString(R.string.mfalse), context.getString(R.string.eq)});
    }

    private void generalReplacement(Context context) {
        if (this.formatText) {
            this.mReplacement.add(new Translate(context.getString(R.string.binomial) + "(", "<b>C</b>("));
            this.mReplacement.add(new Translate(context.getString(R.string.permutations) + "(", "<b>P</b>("));
            this.mReplacement.add(new Translate(context.getString(R.string.asinh), "<i><b>asinh</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.acosh), "<i><b>acosh</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.atanh), "<i><b>atanh</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.sinh), "<i><b>sinh</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.cosh), "<i><b>cosh</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.tanh), "<i><b>tanh</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.arcsin), "<i><b>asin</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.arccos), "<i><b>acos</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.arctan), "<i><b>atan</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.sin), "<i><b>sin</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.cos), "<i><b>cos</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.tan), "<i><b>tan</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.lg), "<i><b>log</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.ln), "<i><b>ln</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.op_cbrt), BuildConfig.FLAVOR + context.getString(R.string.op_cbrt) + BuildConfig.FLAVOR));
            this.mReplacement.add(new Translate(context.getString(R.string.abs), "<i><b>abs</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.sgn), "<i><b>sign</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.floor), "<i><b>floor</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.ceil), "<i><b>ceil</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.min), "<i><b>min</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.max), "<i><b>max</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.mean), "<i><b>mean</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.exp), "<i><b>exp</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.sign), "<i><b>sign</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.arg), "<i><b>arg</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.gcd), "<i><b>" + context.getString(R.string.gcd) + "</b></i>"));
            this.mReplacement.add(new Translate(context.getString(R.string.lcm), "<i><b>" + context.getString(R.string.lcm) + "</b></i>"));
        }
    }

    protected void onFormat(Editable s) {
        String text = removeFormatting(s.toString());
        MutableInteger selectionHandle = new MutableInteger(getSelectionStart());
        Log.d(TAG, "onFormat: " + text);
        if (this.formatText) {
            setText(Html.fromHtml(formatText(text, selectionHandle)));
        } else {
            setText(formatText(text, selectionHandle));
        }
        setSelection(selectionHandle.intValue());
    }

    public void addTextChangedListener(TextWatcher watcher) {
        if (watcher.equals(this.mTextWatcher) || this.mTextWatchers == null) {
            super.addTextChangedListener(watcher);
        } else {
            this.mTextWatchers.add(watcher);
        }
    }

    public void setText(CharSequence text, BufferType type) {
        if (this.mTextWatchersEnabled) {
            for (TextWatcher textWatcher : this.mTextWatchers) {
                textWatcher.beforeTextChanged(getCleanText(), 0, 0, 0);
            }
        }
        super.setText(text, type);
        if (!(text == null || this.mIsInserting)) {
            setSelection(getText().length());
        }
        if (this.mTextWatchersEnabled) {
            for (TextWatcher textWatcher2 : this.mTextWatchers) {
                textWatcher2.afterTextChanged(getEditableText());
                textWatcher2.onTextChanged(getCleanText(), 0, 0, 0);
            }
        }
    }

    public String getCleanText() {
        return TextUtil.getCleanText(this, getSolver());
    }

    public void insert(String delta) {
        String currentText = getText().toString();
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        String textBeforeInsertionHandle = currentText.substring(0, selectionStart);
        String textAfterInsertionHandle = currentText.substring(selectionEnd, currentText.length());
        if (delta.length() == 1) {
            char prevChar;
            char text = delta.charAt(0);
            if (text == Constants.DECIMAL_POINT) {
                int p = selectionStart - 1;
                while (p >= 0 && Solver.isDigit(getText().charAt(p))) {
                    if (getText().charAt(p) != Constants.DECIMAL_POINT) {
                        p--;
                    } else {
                        return;
                    }
                }
                p = selectionStart;
                while (p < getText().length() && Solver.isDigit(getText().charAt(p))) {
                    if (getText().charAt(p) != Constants.DECIMAL_POINT) {
                        p++;
                    } else {
                        return;
                    }
                }
            }
            if (selectionStart > 0) {
                prevChar = getText().charAt(selectionStart - 1);
            } else {
                prevChar = '\u0000';
            }
            if (text != Constants.MINUS_UNICODE || prevChar != Constants.MINUS_UNICODE) {
                if (selectionStart != 0 || !Solver.isOperator(text) || text == Constants.MINUS_UNICODE) {
                    if (Solver.isOperator(text) && text != Constants.MINUS_UNICODE) {
                        while (Solver.isOperator(prevChar)) {
                            if (selectionStart != 1) {
                                selectionStart--;
                                if (selectionStart > 0) {
                                    prevChar = getText().charAt(selectionStart - 1);
                                } else {
                                    prevChar = '\u0000';
                                }
                                textBeforeInsertionHandle = textBeforeInsertionHandle.substring(0, selectionStart);
                            } else {
                                return;
                            }
                        }
                    }
                }
                return;
            }
            return;
        }
        this.mIsInserting = true;
        setText(textBeforeInsertionHandle + delta + BaseModule.SELECTION_HANDLE + textAfterInsertionHandle);
        this.mIsInserting = false;
    }

    public void clear() {
        setText(null);
    }

    public boolean isCursorModified() {
        return getSelectionStart() != getText().length();
    }

    public void next() {
        if (getSelectionStart() == getText().length()) {
            setSelection(0);
        } else {
            setSelection(getSelectionStart() + 1);
        }
    }

    public void backspace() {
        String text = getText().toString();
        int selectionHandle = getSelectionStart();
        String textBeforeInsertionHandle = text.substring(0, selectionHandle);
        String textAfterInsertionHandle = text.substring(selectionHandle, text.length());
        for (String s : this.mKeywords) {
            if (textBeforeInsertionHandle.endsWith(s)) {
                int deletionLength = s.length();
                setText(textBeforeInsertionHandle.substring(0, textBeforeInsertionHandle.length() - deletionLength) + textAfterInsertionHandle);
                setSelection(selectionHandle - deletionLength);
                return;
            }
        }
        if (selectionHandle != 0) {
            setText(textBeforeInsertionHandle.substring(0, textBeforeInsertionHandle.length() - 1) + textAfterInsertionHandle);
            if (getText().length() == text.length() - 2) {
                selectionHandle -= 2;
            } else {
                selectionHandle--;
            }
            setSelection(selectionHandle);
        }
    }

    public void setSelection(int index) {
        super.setSelection(Math.max(0, Math.min(getText().length(), index)));
    }

    public int getSelectionStart() {
        return Math.max(0, super.getSelectionStart());
    }

    protected String removeFormatting(String input) {
        return input.replace(Constants.POWER_PLACEHOLDER, Constants.POWER_UNICODE);
    }

    protected String formatText(String input, MutableInteger selectionHandle) {
        int i = 0;
        Iterator it = this.mReplacement.iterator();
        while (it.hasNext()) {
            Translate key = (Translate) it.next();
            input = input.replace(key.getSource(), key.getView());
        }
        int customHandle = input.indexOf(9760);
        if (customHandle >= 0) {
            selectionHandle.set(customHandle);
            input = input.replace(Character.toString(BaseModule.SELECTION_HANDLE), BuildConfig.FLAVOR);
        }
        if (this.mSolver == null) {
            return input;
        }
        String grouped = this.mFormatExpression.addComas(this.mSolver, input, selectionHandle.intValue());
        if (!grouped.contains(String.valueOf(BaseModule.SELECTION_HANDLE))) {
            return grouped;
        }
        String[] temp = grouped.split(String.valueOf(BaseModule.SELECTION_HANDLE));
        selectionHandle.set(temp[0].length());
        input = BuildConfig.FLAVOR;
        int length = temp.length;
        while (i < length) {
            input = input + temp[i];
            i++;
        }
        return input;
    }

    public Solver getSolver() {
        return this.mSolver;
    }

    public void setSolver(Solver solver) {
        this.mSolver = solver;
    }

    public void setFormatText(boolean formatText) {
        this.formatText = formatText;
        if (formatText) {
            generalReplacement(getContext());
        } else {
            this.mReplacement.clear();
        }
    }
}
