package com.example.duy.calculator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import com.example.duy.calculator.data.Database;
import com.example.duy.calculator.data.Settings;
import com.example.duy.calculator.math_eval.Base;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.LogicEvaluator.EvaluateCallback;
import com.example.duy.calculator.math_eval.NumberBaseManager;
import com.example.duy.calculator.view.AnimationFinishedListener;
import com.example.duy.calculator.view.CalculatorEditText;
import com.example.duy.calculator.view.PadButtonBaseCalculator;
import com.example.duy.calculator.view.RevealView;
import com.getkeepsafe.taptargetview.R;
import io.codetail.animation.ViewAnimationUtils;
import io.github.kexanie.library.BuildConfig;
import org.matheclipse.core.interfaces.IExpr;

public class BaseCalculatorActivity extends AbstractCalculator implements EvaluateCallback {
    private static final String TAG;
    private NumberBaseManager mBaseManager;
    private View mCurrentButton;
    private CalculatorState mCurrentState;
    private ViewGroup mDisplayForeground;
    private BigEvaluator mEvaluator;
    private final OnKeyListener mFormulaOnKeyListener;
    private final TextWatcher mFormulaTextWatcher;
    private CalculatorEditText mInputDisplay;
    private final LayoutParams mLayoutParams;
    private TextView txtResult;

    class 10 extends AnimationFinishedListener {
        final /* synthetic */ String val$errorResourceId;

        10(String str) {
            this.val$errorResourceId = str;
        }

        public void onAnimationFinished() {
            BaseCalculatorActivity.this.setState(CalculatorState.ERROR);
            BaseCalculatorActivity.this.txtResult.setText(this.val$errorResourceId);
        }
    }

    class 11 extends AnimationFinishedListener {
        11() {
        }

        public void onAnimationFinished() {
            BaseCalculatorActivity.this.setState(CalculatorState.INPUT);
            BaseCalculatorActivity.this.txtResult.setText(BuildConfig.FLAVOR);
            BaseCalculatorActivity.this.mInputDisplay.clear();
        }
    }

    class 1 implements OnKeyListener {
        1() {
        }

        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            switch (keyCode) {
                case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /*66*/:
                case 160:
                    if (keyEvent.getAction() != 1) {
                        return true;
                    }
                    BaseCalculatorActivity.this.onEqual();
                    return true;
                default:
                    return false;
            }
        }
    }

    class 2 implements TextWatcher {
        2() {
        }

        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        public void afterTextChanged(Editable editable) {
            BaseCalculatorActivity.this.setState(CalculatorState.INPUT);
            Log.d(BaseCalculatorActivity.TAG, BaseCalculatorActivity.this.mInputDisplay.getCleanText());
            BaseCalculatorActivity.this.mEvaluator.evaluateBase(BaseCalculatorActivity.this.mInputDisplay.getCleanText(), BaseCalculatorActivity.this);
        }
    }

    class 3 implements EvaluateCallback {
        3() {
        }

        public void onEvaluate(String expr, String result, int errorResourceId) {
            if (errorResourceId == -1) {
                BaseCalculatorActivity.this.onError(BaseCalculatorActivity.this.getResources().getString(R.string.error));
                return;
            }
            BaseCalculatorActivity.this.txtResult.setText(result);
            BaseCalculatorActivity.this.onResult(result);
            BaseCalculatorActivity.this.setState(CalculatorState.INPUT);
        }
    }

    class 4 implements Runnable {
        final /* synthetic */ boolean val$isDisable;
        final /* synthetic */ View val$view;

        4(View view, boolean z) {
            this.val$view = view;
            this.val$isDisable = z;
        }

        public void run() {
            this.val$view.setEnabled(!this.val$isDisable);
        }
    }

    class 5 implements AnimatorUpdateListener {
        5() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            BaseCalculatorActivity.this.txtResult.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    class 6 extends AnimationFinishedListener {
        final /* synthetic */ String val$result;
        final /* synthetic */ int val$resultTextColor;

        6(int i, String str) {
            this.val$resultTextColor = i;
            this.val$result = str;
        }

        public void onAnimationFinished() {
            BaseCalculatorActivity.this.txtResult.setPivotY((float) (BaseCalculatorActivity.this.txtResult.getHeight() / 2));
            BaseCalculatorActivity.this.txtResult.setTextColor(this.val$resultTextColor);
            BaseCalculatorActivity.this.txtResult.setScaleX(1.0f);
            BaseCalculatorActivity.this.txtResult.setScaleY(1.0f);
            BaseCalculatorActivity.this.txtResult.setTranslationX(0.0f);
            BaseCalculatorActivity.this.txtResult.setTranslationY(0.0f);
            BaseCalculatorActivity.this.mInputDisplay.setTranslationY(0.0f);
            BaseCalculatorActivity.this.mInputDisplay.setText(this.val$result);
            BaseCalculatorActivity.this.setState(CalculatorState.RESULT);
        }
    }

    class 7 extends AnimationFinishedListener {
        7() {
        }

        public void onAnimationFinished() {
        }
    }

    class 8 extends AnimationFinishedListener {
        final /* synthetic */ RevealView val$revealView;

        8(RevealView revealView) {
            this.val$revealView = revealView;
        }

        public void onAnimationFinished() {
            BaseCalculatorActivity.this.mDisplayForeground.removeView(this.val$revealView);
        }
    }

    class 9 extends AnimationFinishedListener {
        final /* synthetic */ Animator val$alphaAnimator;

        9(Animator animator) {
            this.val$alphaAnimator = animator;
        }

        public void onAnimationFinished() {
            BaseCalculatorActivity.this.play(this.val$alphaAnimator);
        }
    }

    public enum CalculatorState {
        INPUT,
        EVALUATE,
        RESULT,
        GRAPHING,
        ERROR
    }

    public BaseCalculatorActivity() {
        this.mLayoutParams = new LayoutParams(-1, -1);
        this.mCurrentButton = null;
        this.mCurrentState = CalculatorState.INPUT;
        this.mFormulaOnKeyListener = new 1();
        this.mFormulaTextWatcher = new 2();
    }

    static {
        TAG = BaseCalculatorActivity.class.getName();
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_base_calculator);
        this.database = new Database(this);
        this.mEvaluator = ((CalcApplication) getApplicationContext()).getEvaluator();
        this.mDisplayForeground = (ViewGroup) findViewById(R.id.the_clear_animation);
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.mInputDisplay = (CalculatorEditText) findViewById(R.id.txtDisplay);
        if (VERSION.SDK_INT >= 21) {
            this.mInputDisplay.setShowSoftInputOnFocus(false);
        }
        this.mInputDisplay.addTextChangedListener(this.mFormulaTextWatcher);
        this.mInputDisplay.setOnKeyListener(this.mFormulaOnKeyListener);
        this.mInputDisplay.setFormatText(false);
        this.txtResult = (TextView) findViewById(R.id.txtResult);
        this.mBaseManager = new NumberBaseManager(this.mEvaluator.getSolver().getBase());
    }

    void setState(CalculatorState state) {
        this.mCurrentState = state;
    }

    protected void onResume() {
        super.onResume();
        Settings preferences = new Settings(this);
        int base = preferences.getInt(Settings.BASE);
        this.mInputDisplay.setText(preferences.getString(Settings.INPUT_BASE));
        this.txtResult.setText(preferences.getString(Settings.RESULT_BASE));
        switch (base) {
            case IExpr.DOUBLEID /*2*/:
                setBase(Base.BINARY);
            case IExpr.INTEGERID /*8*/:
                setBase(Base.OCTAL);
            case io.github.kexanie.library.R.styleable.SwitchCompat_switchMinWidth /*10*/:
                setBase(Base.DECIMAL);
            case IExpr.FRACTIONID /*16*/:
                setBase(Base.HEXADECIMAL);
            default:
        }
    }

    protected void onPause() {
        super.onPause();
        Settings preferences = new Settings(this);
        preferences.put(Settings.BASE, this.mEvaluator.getSolver().getBaseModule().getBaseNumber(this.mEvaluator.getSolver().getBase()));
        preferences.put(Settings.INPUT_BASE, this.mInputDisplay.getCleanText());
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear /*2131689661*/:
                onClear();
            case R.id.dec /*2131689880*/:
                setBase(Base.DECIMAL);
            case R.id.hex /*2131689881*/:
                setBase(Base.HEXADECIMAL);
            case R.id.bin /*2131689882*/:
                setBase(Base.BINARY);
            case R.id.oct /*2131689883*/:
                setBase(Base.OCTAL);
            case R.id.op_and /*2131689884*/:
            case R.id.op_or /*2131689885*/:
            case R.id.op_neg /*2131689886*/:
            case R.id.op_equal /*2131689887*/:
            case R.id.btn_geq /*2131689888*/:
            case R.id.btn_leq /*2131689889*/:
            case R.id.btn_lt /*2131689898*/:
            case R.id.btn_gt /*2131689899*/:
            case R.id.op_xor /*2131689918*/:
                insertText(" " + ((Button) view).getText().toString() + " ");
            case R.id.btnDelete /*2131689892*/:
                onDelete();
            case R.id.btnPlus /*2131689893*/:
            case R.id.btnMinus /*2131689894*/:
            case R.id.btnMul /*2131689895*/:
            case R.id.btnDiv /*2131689919*/:
                insertText(" " + ((Button) view).getText().toString() + " ");
            case R.id.btnEqual /*2131689902*/:
                onEqual();
            default:
                if (view instanceof PadButtonBaseCalculator) {
                    insertText(((Button) view).getText().toString());
                }
        }
    }

    private void setBase(Base base) {
        this.mBaseManager.setNumberBase(base);
        this.mEvaluator.setBase(this.mInputDisplay.getCleanText(), base, new 3());
        updateUI();
    }

    private void updateUI() {
        setSelectionButton(this.mEvaluator.getSolver().getBase());
        for (Integer intValue : this.mBaseManager.getViewIds()) {
            int id = intValue.intValue();
            View view = findViewById(id);
            if (view != null) {
                view.post(new 4(view, this.mBaseManager.isViewDisabled(id)));
            }
        }
    }

    private void setSelectionButton(Base base) {
        findViewById(R.id.hex).setSelected(base.equals(Base.HEXADECIMAL));
        findViewById(R.id.bin).setSelected(base.equals(Base.BINARY));
        findViewById(R.id.dec).setSelected(base.equals(Base.DECIMAL));
        findViewById(R.id.oct).setSelected(base.equals(Base.OCTAL));
    }

    public void setTextDisplay(String s) {
        this.mInputDisplay.setText(s);
    }

    public void insertText(String string) {
        this.mInputDisplay.insert(string);
    }

    public void insertOperator(String s) {
    }

    public String getTextClean() {
        return null;
    }

    public void onHelpFunction(String s) {
    }

    public void onResult(String result) {
        Log.d(TAG, "doEval " + result);
        float resultScale = this.mInputDisplay.getTextSize() / this.txtResult.getTextSize();
        float resultTranslationX = (1.0f - resultScale) * ((((float) this.txtResult.getWidth()) / 2.0f) - ((float) this.txtResult.getPaddingRight()));
        float resultTranslationY = ((((float) (-this.mInputDisplay.getHeight())) - (((float) this.txtResult.getPaddingTop()) * resultScale)) + ((float) this.mInputDisplay.getPaddingTop())) + ((((float) ((this.mInputDisplay.getHeight() - this.mInputDisplay.getPaddingTop()) - this.mInputDisplay.getPaddingBottom())) - (resultScale * ((float) ((this.txtResult.getHeight() - this.txtResult.getPaddingTop()) - this.txtResult.getPaddingBottom())))) / 2.0f);
        float formulaTranslationY = (float) (-this.mInputDisplay.getBottom());
        int resultTextColor = this.txtResult.getCurrentTextColor();
        int formulaTextColor = this.mInputDisplay.getCurrentTextColor();
        ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{Integer.valueOf(resultTextColor), Integer.valueOf(formulaTextColor)}).addUpdateListener(new 5());
        this.txtResult.setText(result);
        this.txtResult.setPivotX((float) (this.txtResult.getWidth() / 2));
        this.txtResult.setPivotY(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        r12 = new Animator[6];
        r12[1] = ObjectAnimator.ofFloat(this.txtResult, View.SCALE_X, new float[]{resultScale});
        r12[2] = ObjectAnimator.ofFloat(this.txtResult, View.SCALE_Y, new float[]{resultScale});
        r12[3] = ObjectAnimator.ofFloat(this.txtResult, View.TRANSLATION_X, new float[]{resultTranslationX});
        r12[4] = ObjectAnimator.ofFloat(this.txtResult, View.TRANSLATION_Y, new float[]{resultTranslationY});
        r12[5] = ObjectAnimator.ofFloat(this.mInputDisplay, View.TRANSLATION_Y, new float[]{formulaTranslationY});
        animatorSet.playTogether(r12);
        animatorSet.setDuration((long) getResources().getInteger(17694720));
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new 6(resultTextColor, result));
        play(animatorSet);
    }

    protected void play(Animator animator) {
        animator.addListener(new 7());
        animator.start();
    }

    private void reveal(View sourceView, int color, AnimatorListener listener) {
        RevealView revealView = new RevealView(this);
        revealView.setLayoutParams(this.mLayoutParams);
        revealView.setRevealColor(color);
        this.mDisplayForeground.addView(revealView);
        int[] clearLocation = new int[2];
        if (sourceView != null) {
            sourceView.getLocationInWindow(clearLocation);
            clearLocation[0] = clearLocation[0] + (sourceView.getWidth() / 2);
            clearLocation[1] = clearLocation[1] + (sourceView.getHeight() / 2);
        } else {
            clearLocation[0] = this.mDisplayForeground.getWidth() / 2;
            clearLocation[1] = this.mDisplayForeground.getHeight() / 2;
        }
        int revealCenterX = clearLocation[0] - revealView.getLeft();
        int revealCenterY = clearLocation[1] - revealView.getTop();
        double x1_2 = Math.pow((double) (revealView.getLeft() - revealCenterX), 2.0d);
        double x2_2 = Math.pow((double) (revealView.getRight() - revealCenterX), 2.0d);
        double y_2 = Math.pow((double) (revealView.getTop() - revealCenterY), 2.0d);
        Animator revealAnimator = ViewAnimationUtils.createCircularReveal(revealView, revealCenterX, revealCenterY, 0.0f, (float) Math.max(Math.sqrt(x1_2 + y_2), Math.sqrt(x2_2 + y_2)));
        revealAnimator.setDuration((long) getResources().getInteger(17694720));
        revealAnimator.addListener(listener);
        Animator alphaAnimator = ObjectAnimator.ofFloat(revealView, View.ALPHA, new float[]{0.0f});
        alphaAnimator.setDuration((long) getResources().getInteger(17694720));
        alphaAnimator.addListener(new 8(revealView));
        revealAnimator.addListener(new 9(alphaAnimator));
        play(revealAnimator);
    }

    public void onError(String errorResourceId) {
        if (this.mCurrentState != CalculatorState.EVALUATE) {
            this.txtResult.setText(errorResourceId);
            return;
        }
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorResultError, typedValue, true);
        reveal(this.mCurrentButton, typedValue.data, new 10(errorResourceId));
    }

    public void onDelete() {
        this.mInputDisplay.backspace();
    }

    public void onClear() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorClearScreen, typedValue, true);
        reveal(this.mCurrentButton, typedValue.data, new 11());
    }

    public void onEqual() {
        String text = this.mInputDisplay.getCleanText();
        if (this.mCurrentState == CalculatorState.INPUT) {
            setState(CalculatorState.EVALUATE);
            this.mEvaluator.evaluateBase(text, this);
        }
    }

    public void onInputVoice() {
    }

    public void onEvaluate(String expr, String result, int resultId) {
        Log.d(TAG, "onEvaluate " + expr + " = " + result + " with error " + resultId);
        if (resultId == -1) {
            if (this.mCurrentState == CalculatorState.INPUT) {
                this.txtResult.setText(null);
            } else if (this.mCurrentState == CalculatorState.EVALUATE) {
                onError(result);
            }
        } else if (resultId == 1) {
            if (this.mCurrentState == CalculatorState.EVALUATE) {
                onResult(result);
            } else if (this.mCurrentState != CalculatorState.INPUT) {
            } else {
                if (result == null) {
                    this.txtResult.setText(null);
                } else {
                    this.txtResult.setText(result);
                }
            }
        } else if (resultId != 2) {
        }
    }
}
