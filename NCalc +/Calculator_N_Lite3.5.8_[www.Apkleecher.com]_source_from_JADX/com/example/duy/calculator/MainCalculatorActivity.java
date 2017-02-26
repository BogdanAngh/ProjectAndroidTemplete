package com.example.duy.calculator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.example.duy.calculator.data.Settings;
import com.example.duy.calculator.define.DefineVariableActivity;
import com.example.duy.calculator.graph.GraphActivity;
import com.example.duy.calculator.helper.HelperActivity;
import com.example.duy.calculator.history.HistoryAcitivity;
import com.example.duy.calculator.history.HistoryAdapter;
import com.example.duy.calculator.history.ItemHistory;
import com.example.duy.calculator.math_eval.Base;
import com.example.duy.calculator.math_eval.LogicEvaluator;
import com.example.duy.calculator.math_eval.LogicEvaluator.EvaluateCallback;
import com.example.duy.calculator.math_eval.Solver;
import com.example.duy.calculator.math_eval.TaskSolve;
import com.example.duy.calculator.utils.MyClipboard;
import com.example.duy.calculator.utils.VoiceUtils;
import com.example.duy.calculator.view.AnimationFinishedListener;
import com.example.duy.calculator.view.CalculatorEditText;
import com.example.duy.calculator.view.RevealView;
import com.getkeepsafe.taptargetview.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import io.github.kexanie.library.BuildConfig;
import io.github.kexanie.library.MathView;
import java.util.ArrayList;
import java.util.Locale;

public class MainCalculatorActivity extends AbstractCalculator implements EvaluateCallback {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final String DATA = "DATA_BUNDLE";
    private static final int REQ_CODE_DEFINE_VAR = 1234;
    private static final int REQ_CODE_HISTORY = 1111;
    public static final String TAG;
    private final int MY_REQUEST_CODE;
    private final int REQ_CODE_SPEECH_INPUT;
    private boolean debug;
    public RevealFrameLayout mAnimateSolve;
    private FrameLayout mContainerSolve;
    private View mCurrentButton;
    private CalculatorState mCurrentState;
    private ViewGroup mDisplayForeground;
    private FloatingActionButton mFabCloseMathView;
    private final OnKeyListener mFormulaOnKeyListener;
    private final TextWatcher mFormulaTextWatcher;
    private HistoryAdapter mHistoryAdapter;
    private CalculatorEditText mInputDisplay;
    private InputState mInputState;
    public MathView mMathView;
    private PagerState mPageState;
    public ProgressBar mProgress;
    private MathView mReview;
    private FrameLayout mTool;
    private SwitchCompat swCmplx;
    private SwitchCompat txtInfo;
    private EditText txtResult;

    class 10 implements OnCheckedChangeListener {
        10() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            MainCalculatorActivity.this.setModeFraction(b);
        }
    }

    class 11 implements Listener {
        final /* synthetic */ Editor val$editor;

        11(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean("app_started34", true);
            this.val$editor.apply();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
        }
    }

    class 12 implements AnimationListener {
        12() {
        }

        public void onAnimationStart(Animation animation) {
            MainCalculatorActivity.this.txtResult.setVisibility(8);
            MainCalculatorActivity.this.mTool.setVisibility(0);
        }

        public void onAnimationEnd(Animation animation) {
            Log.d(MainCalculatorActivity.TAG, "onAnimationEnd: ");
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class 13 implements Runnable {
        final /* synthetic */ AlphaAnimation val$alphaAnimation;

        13(AlphaAnimation alphaAnimation) {
            this.val$alphaAnimation = alphaAnimation;
        }

        public void run() {
            MainCalculatorActivity.this.mTool.startAnimation(this.val$alphaAnimation);
        }
    }

    class 14 implements OnClickListener {
        14() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(MainCalculatorActivity.this, SolveEquationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(MainCalculatorActivity.DATA, MainCalculatorActivity.this.mInputDisplay.getCleanText());
            intent.putExtra(MainCalculatorActivity.DATA, bundle);
            MainCalculatorActivity.this.startActivity(intent);
        }
    }

    class 15 implements OnClickListener {
        15() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(MainCalculatorActivity.this, GraphActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(GraphActivity.FUNC, MainCalculatorActivity.this.mInputDisplay.getCleanText());
            intent.putExtra(GraphActivity.DATA, bundle);
            MainCalculatorActivity.this.startActivity(intent);
        }
    }

    class 16 implements OnClickListener {
        16() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(MainCalculatorActivity.this, FactorExpressionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(MainCalculatorActivity.DATA, MainCalculatorActivity.this.mInputDisplay.getCleanText());
            intent.putExtra(MainCalculatorActivity.DATA, bundle);
            MainCalculatorActivity.this.startActivity(intent);
        }
    }

    class 17 implements OnClickListener {
        17() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(MainCalculatorActivity.this, SimplifyEquationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(MainCalculatorActivity.DATA, MainCalculatorActivity.this.mInputDisplay.getCleanText());
            intent.putExtra(MainCalculatorActivity.DATA, bundle);
            MainCalculatorActivity.this.startActivity(intent);
        }
    }

    class 18 implements OnClickListener {
        18() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(MainCalculatorActivity.this, ExpandAllExpressionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(MainCalculatorActivity.DATA, MainCalculatorActivity.this.mInputDisplay.getCleanText());
            intent.putExtra(MainCalculatorActivity.DATA, bundle);
            MainCalculatorActivity.this.startActivity(intent);
        }
    }

    class 19 implements OnClickListener {
        19() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(MainCalculatorActivity.this, DerivativeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(MainCalculatorActivity.DATA, MainCalculatorActivity.this.mInputDisplay.getCleanText());
            intent.putExtra(MainCalculatorActivity.DATA, bundle);
            MainCalculatorActivity.this.startActivity(intent);
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
                    MainCalculatorActivity.this.onEqual();
                    return true;
                default:
                    return MainCalculatorActivity.$assertionsDisabled;
            }
        }
    }

    class 20 implements Runnable {
        final /* synthetic */ String val$finalRes;

        20(String str) {
            this.val$finalRes = str;
        }

        public void run() {
            MainCalculatorActivity.this.mInputDisplay.setText(this.val$finalRes);
        }
    }

    class 21 implements Runnable {
        final /* synthetic */ ItemHistory val$history;

        21(ItemHistory itemHistory) {
            this.val$history = itemHistory;
        }

        public void run() {
            MainCalculatorActivity.this.mInputDisplay.setText(this.val$history.getMath());
        }
    }

    class 22 implements DialogInterface.OnClickListener {
        22() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    class 23 extends AnimationFinishedListener {
        final /* synthetic */ String val$error;

        23(String str) {
            this.val$error = str;
        }

        public void onAnimationFinished() {
            MainCalculatorActivity.this.setState(CalculatorState.ERROR);
            MainCalculatorActivity.this.setTextError(this.val$error);
        }
    }

    class 24 implements Runnable {
        final /* synthetic */ String val$result;

        24(String str) {
            this.val$result = str;
        }

        public void run() {
            MainCalculatorActivity.this.txtResult.setText(this.val$result);
        }
    }

    class 25 implements Runnable {
        final /* synthetic */ String val$result;

        25(String str) {
            this.val$result = str;
        }

        public void run() {
            MainCalculatorActivity.this.txtResult.setText(this.val$result);
        }
    }

    class 26 extends AnimationFinishedListener {
        final /* synthetic */ ViewGroup val$foreground;
        final /* synthetic */ RevealView val$revealView;

        26(ViewGroup viewGroup, RevealView revealView) {
            this.val$foreground = viewGroup;
            this.val$revealView = revealView;
        }

        public void onAnimationFinished() {
            this.val$foreground.removeView(this.val$revealView);
        }
    }

    class 27 extends AnimationFinishedListener {
        final /* synthetic */ Animator val$alphaAnimator;

        27(Animator animator) {
            this.val$alphaAnimator = animator;
        }

        public void onAnimationFinished() {
            MainCalculatorActivity.this.play(this.val$alphaAnimator);
        }
    }

    class 28 extends AnimationFinishedListener {
        28() {
        }

        public void onAnimationFinished() {
            MainCalculatorActivity.this.setState(CalculatorState.INPUT);
            MainCalculatorActivity.this.txtResult.setText(BuildConfig.FLAVOR);
            MainCalculatorActivity.this.mInputDisplay.clear();
        }
    }

    class 29 implements Runnable {
        final /* synthetic */ String val$textDisplay;

        29(String str) {
            this.val$textDisplay = str;
        }

        public void run() {
            MainCalculatorActivity.this.mInputDisplay.setText(this.val$textDisplay);
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
            MainCalculatorActivity.this.setState(CalculatorState.INPUT);
            MainCalculatorActivity.this.mEvaluator.evaluate(MainCalculatorActivity.this.mInputDisplay.getCleanText(), MainCalculatorActivity.this);
        }
    }

    class 30 extends AnimationFinishedListener {
        30() {
        }

        public void onAnimationFinished() {
            MainCalculatorActivity.this.mContainerSolve.setVisibility(0);
            new TaskSolve((CalcApplication) MainCalculatorActivity.this.getApplicationContext(), MainCalculatorActivity.this.mProgress, MainCalculatorActivity.this.mMathView, MainCalculatorActivity.this.mInputDisplay.getCleanText()).execute(new Void[0]);
            MainCalculatorActivity.this.setInputState(InputState.RESULT_VIEW);
        }
    }

    class 31 extends AnimationFinishedListener {
        final /* synthetic */ RevealView val$revealView;

        31(RevealView revealView) {
            this.val$revealView = revealView;
        }

        public void onAnimationFinished() {
            MainCalculatorActivity.this.mAnimateSolve.removeView(this.val$revealView);
            MainCalculatorActivity.this.setInputState(InputState.PAD);
        }
    }

    class 32 extends AnimationFinishedListener {
        32() {
        }

        public void onAnimationFinished() {
            MainCalculatorActivity.this.mContainerSolve.setVisibility(0);
            MainCalculatorActivity.this.setInputState(InputState.RESULT_VIEW);
        }
    }

    class 33 implements EvaluateCallback {
        33() {
        }

        public void onEvaluate(String expr, String result, int errorResourceId) {
            if (errorResourceId != 1 || result.toLowerCase().contains("factorinteger")) {
                Log.d(MainCalculatorActivity.TAG, "onEvaluate: " + result);
                MainCalculatorActivity.this.mMathView.setText(MainCalculatorActivity.this.getString(R.string.cannot_factor));
                return;
            }
            MainCalculatorActivity.this.mMathView.setText("$$" + result + "$$");
        }
    }

    class 3 implements OnCheckedChangeListener {
        3() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Settings.put(MainCalculatorActivity.this, Settings.USE_RADIANS, b);
            MainCalculatorActivity.this.txtInfo.setText(b ? MainCalculatorActivity.this.getResources().getString(R.string.radians) : MainCalculatorActivity.this.getResources().getString(R.string.degrees));
            MainCalculatorActivity.this.mInputDisplay.setText(MainCalculatorActivity.this.mInputDisplay.getCleanText());
            MainCalculatorActivity.this.mEvaluator.evaluate(MainCalculatorActivity.this.mInputDisplay.getCleanText(), MainCalculatorActivity.this);
        }
    }

    class 4 implements OnCheckedChangeListener {
        4() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Settings.put(MainCalculatorActivity.this, Settings.CMPLX_ON, b);
            MainCalculatorActivity.this.mEvaluator.evaluate(MainCalculatorActivity.this.mInputDisplay.getCleanText(), MainCalculatorActivity.this);
        }
    }

    class 5 implements OnClickListener {
        5() {
        }

        public void onClick(View view) {
            MainCalculatorActivity.this.startActivityForResult(new Intent(MainCalculatorActivity.this, HistoryAcitivity.class), MainCalculatorActivity.REQ_CODE_HISTORY);
        }
    }

    class 6 implements OnClickListener {
        6() {
        }

        public void onClick(View view) {
            ((DrawerLayout) MainCalculatorActivity.this.findViewById(R.id.drawer_layout)).openDrawer((int) GravityCompat.START);
        }
    }

    class 7 implements OnFocusChangeListener {
        7() {
        }

        public void onFocusChange(View view, boolean b) {
            MainCalculatorActivity.this.hideKeyboard(MainCalculatorActivity.this.mInputDisplay);
        }
    }

    class 8 implements OnClickListener {
        8() {
        }

        public void onClick(View view) {
            MainCalculatorActivity.this.closeSolveResult();
        }
    }

    class 9 implements OnClickListener {
        9() {
        }

        public void onClick(View view) {
            MainCalculatorActivity.this.onHelpFunction();
        }
    }

    public enum CalculatorState {
        INPUT,
        EVALUATE,
        RESULT,
        ERROR
    }

    static {
        $assertionsDisabled = !MainCalculatorActivity.class.desiredAssertionStatus() ? true : $assertionsDisabled;
        TAG = MainCalculatorActivity.class.getName();
    }

    public MainCalculatorActivity() {
        this.REQ_CODE_SPEECH_INPUT = 1235;
        this.MY_REQUEST_CODE = 1236;
        this.mCurrentButton = null;
        this.mCurrentState = CalculatorState.INPUT;
        this.mFormulaOnKeyListener = new 1();
        this.mFormulaTextWatcher = new 2();
        this.mInputState = InputState.PAD;
        this.debug = $assertionsDisabled;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        ButterKnife.bind((Activity) this);
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.txtInfo = (SwitchCompat) findViewById(R.id.txt_info);
        this.txtInfo.setText(Settings.useRadians(this) ? getResources().getString(R.string.radians) : getResources().getString(R.string.degrees));
        this.txtInfo.setChecked(Settings.useRadians(this));
        this.txtInfo.setOnCheckedChangeListener(new 3());
        this.swCmplx = (SwitchCompat) findViewById(R.id.txt_cmplx);
        this.swCmplx.setChecked(Settings.useComplex(this));
        this.swCmplx.setOnCheckedChangeListener(new 4());
        findViewById(R.id.btn_show_history).setOnClickListener(new 5());
        findViewById(R.id.btn_show_nav).setOnClickListener(new 6());
        this.mDisplayForeground = (ViewGroup) findViewById(R.id.the_clear_animation);
        this.mInputDisplay = (CalculatorEditText) findViewById(R.id.txtDisplay);
        if (VERSION.SDK_INT >= 21) {
            this.mInputDisplay.setShowSoftInputOnFocus($assertionsDisabled);
        }
        this.mInputDisplay.addTextChangedListener(this.mFormulaTextWatcher);
        this.mInputDisplay.setOnKeyListener(this.mFormulaOnKeyListener);
        this.mInputDisplay.setOnFocusChangeListener(new 7());
        this.txtResult = (EditText) findViewById(R.id.txtResult);
        if (VERSION.SDK_INT >= 21) {
            this.txtResult.setShowSoftInputOnFocus($assertionsDisabled);
        }
        this.mReview = (MathView) findViewById(R.id.math_view);
        this.mAnimateSolve = (RevealFrameLayout) findViewById(R.id.animation_pad).findViewById(R.id.result_animation);
        this.mMathView = (MathView) findViewById(R.id.math_result);
        this.mProgress = (ProgressBar) findViewById(com.google.android.gms.R.id.progressBar);
        this.mContainerSolve = (FrameLayout) findViewById(R.id.container_solve);
        this.mFabCloseMathView = (FloatingActionButton) this.mContainerSolve.findViewById(R.id.fab_close);
        this.mFabCloseMathView.setOnClickListener(new 8());
        findViewById(R.id.btn_help).setOnClickListener(new 9());
        SwitchCompat swFraction = (SwitchCompat) findViewById(R.id.ckb_fraction);
        swFraction.setChecked(Settings.useFraction(this));
        setModeFraction(Settings.useFraction(this));
        swFraction.setOnCheckedChangeListener(new 10());
        openMenuDrawer();
        setInputState(InputState.PAD);
        setState(CalculatorState.INPUT);
        showDialogUpdate();
    }

    private void setModeFraction(boolean b) {
        Settings.setFraction(this, b);
        this.mEvaluator.setFraction(b);
        if (b) {
            this.txtResult.setVisibility(8);
            this.mReview.setVisibility(0);
        } else {
            this.mReview.setVisibility(8);
            this.txtResult.setVisibility(0);
        }
        this.mEvaluator.evaluate(this.mInputDisplay.getCleanText(), this);
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        Settings preferences = new Settings(this);
        String math = preferences.getString(Settings.INPUT_MATH);
        String res = preferences.getString(Settings.RESULT_MATH);
        this.mInputDisplay.setText(math);
        this.mEvaluator.evaluate(math, this);
        this.txtResult.setText(res);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if ("android.intent.action.SEND".equals(action) && type != null && "text/plain".equals(type)) {
            this.mInputDisplay.setText(intent.getStringExtra("android.intent.extra.TEXT"));
        }
        this.mEvaluator.getSolver().setBase(Base.DECIMAL);
    }

    private void showHelp() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = preferences.edit();
        if (!preferences.getBoolean("app_started34", $assertionsDisabled)) {
            TapTarget target0 = TapTarget.forView(findViewById(R.id.btn_show_nav), "C\u00e1c ch\u1ee9c n\u0103ng", "Nh\u1ea5n v\u00e0o \u0111\u00e2y \u0111\u1ec3 m\u1edf c\u00e1c t\u00ednh n\u0103ng c\u1ee7a m\u00e1y t\u00ednh").cancelable(true).drawShadow(true).targetCircleColor(R.color.colorAccent).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
            TapTarget target1 = TapTarget.forView(findViewById(R.id.btn_show_history), "L\u1ecbch s\u1eed", "Nh\u1ea5n v\u00e0o \u0111\u00e2y \u0111\u1ec3 m\u1edf l\u1ecbch s\u1eed").cancelable(true).drawShadow(true).targetCircleColor(R.color.colorAccent).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
            TapTargetSequence sequence = new TapTargetSequence(this).targets(target0, target1).listener(new 11(editor));
        }
    }

    private void showToolEquation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration((long) getResources().getInteger(17694722));
        alphaAnimation.setAnimationListener(new 12());
        this.mTool.post(new 13(alphaAnimation));
    }

    private void hideToolEquation() {
        if (this.mTool.isShown()) {
            int cx = (this.mTool.getLeft() + this.mTool.getRight()) / 2;
            int cy = (this.mTool.getTop() + this.mTool.getBottom()) / 2;
            float finalRadius = (float) Math.hypot((double) Math.max(cx, this.mTool.getWidth() - cx), (double) Math.max(cy, this.mTool.getHeight() - cy));
            this.mTool.setVisibility(8);
            this.txtResult.setVisibility(0);
        }
    }

    private void initToolEquation() {
        this.mTool = (FrameLayout) findViewById(R.id.container_tool);
        if (this.mTool == null) {
            Log.d(TAG, "initToolEquation: null object");
            return;
        }
        this.mTool.findViewById(R.id.btn_solve).setOnClickListener(new 14());
        this.mTool.findViewById(R.id.btn_graph).setOnClickListener(new 15());
        this.mTool.findViewById(R.id.btn_factor).setOnClickListener(new 16());
        this.mTool.findViewById(R.id.btn_simplfy).setOnClickListener(new 17());
        this.mTool.findViewById(R.id.btn_expand).setOnClickListener(new 18());
        this.mTool.findViewById(R.id.btn_derivative).setOnClickListener(new 19());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_HISTORY /*1111*/:
                Log.d(TAG, "onActivityResult: history");
                if (resultCode == -1) {
                    ItemHistory history = (ItemHistory) data.getBundleExtra(DATA).getSerializable(DATA);
                    Log.d(TAG, "onActivityResult: " + history.getMath() + " " + history.getResult());
                    this.mInputDisplay.post(new 21(history));
                }
            case REQ_CODE_DEFINE_VAR /*1234*/:
                this.mEvaluator.evaluate(this.mInputDisplay.getCleanText(), this);
            case 1235:
                if (resultCode == -1 && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                    Toast.makeText(this, (CharSequence) result.get(0), 0).show();
                    this.mInputDisplay.post(new 20(VoiceUtils.replace((String) result.get(0))));
                }
            default:
        }
    }

    private void setInputState(InputState pad) {
        this.mInputState = pad;
    }

    private void setPageState(PagerState state) {
        this.mPageState = state;
    }

    public void onHelpFunction() {
        startActivity(new Intent(this, HelperActivity.class));
    }

    public void onHelpFunction(String text) {
        String help = text;
        Builder builder = new Builder(this);
        builder.setView((int) R.layout.layout_helper);
        builder.setTitle((int) R.string.help);
        builder.setNegativeButton(getString(R.string.close), new 22());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        MathView mathView = (MathView) alertDialog.findViewById(R.id.help_content);
        if ($assertionsDisabled || mathView != null) {
            mathView.setText(help);
            return;
        }
        throw new AssertionError();
    }

    public void define(String var, double value) {
        this.mEvaluator.define(var, value);
    }

    public void define(String var, String value) {
        this.mEvaluator.define(var, value);
    }

    private void updateValueVariable(String var, String result) {
    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout != null && drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            super.onBackPressed();
        } else if (this.mInputState == InputState.RESULT_VIEW) {
            closeSolveResult();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        return super.onNavigationItemSelected(item);
    }

    public void onDelete() {
        this.mInputDisplay.backspace();
    }

    public void onError(String error) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorResultError, typedValue, true);
        animateRipple(this.mDisplayForeground, this.mCurrentButton, typedValue.data, new 23(error), true);
    }

    public void setTextResult(String result) {
        if (this.mReview.isShown()) {
            this.mReview.setText(this.mEvaluator.convertToTex(result));
        }
        if (this.txtResult.isShown()) {
            this.txtResult.post(new 24(result));
        }
    }

    public void setTextError(String result) {
        if (this.mReview.isShown()) {
            this.mReview.setText("<h2>" + result + "</h2>");
        }
        if (this.txtResult.isShown()) {
            this.txtResult.post(new 25(result));
        }
    }

    public void onResult(String result) {
        Log.d(TAG, "doEval " + result);
        setTextDisplay(result);
        setTextResult(BuildConfig.FLAVOR);
    }

    private void animateRipple(ViewGroup foreground, View sourceView, int color, AnimatorListener listener, boolean out) {
        Animator revealAnimator;
        if (color == -1) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(io.github.kexanie.library.R.attr.colorAccent, typedValue, true);
            color = typedValue.data;
        }
        RevealView revealView = new RevealView(this);
        revealView.setLayoutParams(this.mLayoutParams);
        revealView.setRevealColor(color);
        if (foreground == null) {
            Log.d(TAG, "animateRipple:  foreground null");
        }
        if (revealView == null) {
            Log.d(TAG, "animateRipple: reveal null");
        }
        foreground.addView(revealView);
        int[] clearLocation = new int[2];
        if (sourceView != null) {
            sourceView.getLocationInWindow(clearLocation);
            clearLocation[0] = clearLocation[0] + (sourceView.getWidth() / 2);
            clearLocation[1] = clearLocation[1] + (sourceView.getHeight() / 2);
        } else {
            clearLocation[0] = foreground.getWidth() / 2;
            clearLocation[1] = foreground.getHeight() / 2;
        }
        int revealCenterX = clearLocation[0] - revealView.getLeft();
        int revealCenterY = clearLocation[1] - revealView.getTop();
        double x1_2 = Math.pow((double) (revealView.getLeft() - revealCenterX), 2.0d);
        double x2_2 = Math.pow((double) (revealView.getRight() - revealCenterX), 2.0d);
        double y_2 = Math.pow((double) (revealView.getTop() - revealCenterY), 2.0d);
        float revealRadius = (float) Math.max(Math.sqrt(x1_2 + y_2), Math.sqrt(x2_2 + y_2));
        if (out) {
            revealAnimator = ViewAnimationUtils.createCircularReveal(revealView, revealCenterX, revealCenterY, 0.0f, revealRadius);
        } else {
            revealAnimator = ViewAnimationUtils.createCircularReveal(revealView, revealCenterX, revealCenterY, revealRadius, 0.0f);
        }
        revealAnimator.setDuration((long) getResources().getInteger(17694720));
        revealAnimator.addListener(listener);
        Animator alphaAnimator = ObjectAnimator.ofFloat(revealView, View.ALPHA, new float[]{0.0f});
        alphaAnimator.setDuration((long) getResources().getInteger(17694720));
        alphaAnimator.addListener(new 26(foreground, revealView));
        revealAnimator.addListener(new 27(alphaAnimator));
        play(revealAnimator);
    }

    public void insertText(String text) {
        boolean b = this.mInputDisplay.getSelectionStart() == this.mInputDisplay.getCleanText().length() + 1 ? true : $assertionsDisabled;
        if (this.mCurrentState == CalculatorState.RESULT && !Solver.isOperator(text) && b) {
            this.mInputDisplay.clear();
        }
        this.mInputDisplay.insert(text);
    }

    public void onClear() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorClearScreen, typedValue, true);
        animateRipple(this.mDisplayForeground, this.mCurrentButton, typedValue.data, new 28(), true);
    }

    public void onEqual() {
        String text = this.mInputDisplay.getCleanText();
        Log.d(TAG, "onEqual: " + text + " " + (this.mCurrentState == CalculatorState.INPUT ? " input" : " result"));
        setState(CalculatorState.EVALUATE);
        this.mEvaluator.evaluate(text, this);
    }

    public void onInputVoice() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
        intent.putExtra("android.speech.extra.PROMPT", getString(R.string.speak_expression));
        try {
            startActivityForResult(intent, 1235);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), 0).show();
        }
    }

    void setState(CalculatorState state) {
        this.mCurrentState = state;
    }

    public void insertOperator(String opt) {
        if (this.mCurrentState != CalculatorState.INPUT && this.mCurrentState == CalculatorState.RESULT) {
        }
        insertText(opt);
    }

    public String getTextClean() {
        return this.mInputDisplay.getCleanText();
    }

    public void setTextDisplay(String textDisplay) {
        this.mInputDisplay.post(new 29(textDisplay));
    }

    protected void onPause() {
        super.onPause();
        String math = this.mInputDisplay.getCleanText();
        String result = this.txtResult.getText().toString().trim();
        Settings preferences = new Settings(this);
        preferences.put(Settings.INPUT_MATH, math);
        preferences.put(Settings.RESULT_MATH, result);
    }

    public void onEvaluate(String expr, String result, int resultId) {
        result = this.mTokenizer.getLocalizedExpression(result);
        if (resultId == -1) {
            if (this.mCurrentState == CalculatorState.INPUT) {
                setTextResult(BuildConfig.FLAVOR);
            } else if (this.mCurrentState == CalculatorState.EVALUATE) {
                onError(getResources().getString(R.string.error));
                saveHistory(expr, getResources().getString(R.string.error), true);
            }
        } else if (resultId == 3) {
            if (this.mCurrentState == CalculatorState.INPUT) {
                setTextResult(BuildConfig.FLAVOR);
            } else if (this.mCurrentState == CalculatorState.EVALUATE) {
                int indexError = result.indexOf(LogicEvaluator.ERROR_INDEX_STRING);
                this.mInputDisplay.setText(result);
                if (indexError >= 0) {
                    this.mInputDisplay.setSelection(indexError, indexError + 1);
                }
                onError(getResources().getString(R.string.error));
                saveHistory(expr, getResources().getString(R.string.error), true);
            }
        } else if (resultId == 1) {
            if (this.mCurrentState == CalculatorState.EVALUATE) {
                onResult(result);
                saveHistory(expr, result, true);
                this.mEvaluator.define("ans", result);
            } else if (this.mCurrentState != CalculatorState.INPUT) {
            } else {
                if (result == null) {
                    setTextResult(BuildConfig.FLAVOR);
                    return;
                }
                result = this.mTokenizer.getLocalizedExpression(result);
                String finalResult = result;
                setTextResult(result);
            }
        } else if (resultId != 2) {
        }
    }

    public void copyText() {
        MyClipboard.setClipboard(this, this.mInputDisplay.getCleanText());
    }

    public void pasteText() {
        this.mInputDisplay.setText(MyClipboard.getClipboard(this));
    }

    public void shareText() {
        String s = this.mInputDisplay.getCleanText();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", s);
        intent.setType("text/plain");
        startActivity(intent);
    }

    public void solve() {
        animateRipple(this.mAnimateSolve, this.mFabCloseMathView, -1, new 30(), true);
    }

    private void closeSolveResult() {
        Log.d(TAG, "closeSolveResult: ");
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(io.github.kexanie.library.R.attr.colorAccent, typedValue, true);
        int color = typedValue.data;
        RevealView revealView = new RevealView(this);
        revealView.setLayoutParams(this.mLayoutParams);
        revealView.setRevealColor(color);
        this.mAnimateSolve.addView(revealView);
        clearLocation = new int[2];
        clearLocation[0] = this.mAnimateSolve.getWidth() / 2;
        clearLocation[1] = this.mAnimateSolve.getHeight() / 2;
        int dx_fab = this.mFabCloseMathView.getPaddingRight() + (this.mFabCloseMathView.getWidth() / 2);
        int revealCenterX = this.mAnimateSolve.getWidth() - dx_fab;
        int revealCenterY = this.mAnimateSolve.getHeight() - (this.mFabCloseMathView.getPaddingBottom() + (this.mFabCloseMathView.getHeight() / 2));
        double x1_2 = Math.pow((double) (revealView.getLeft() - revealCenterX), 2.0d);
        double x2_2 = Math.pow((double) (revealView.getRight() - revealCenterX), 2.0d);
        double y_2 = Math.pow((double) (revealView.getTop() - revealCenterY), 2.0d);
        Animator revealAnimator = ViewAnimationUtils.createCircularReveal(revealView, revealCenterX, revealCenterY, (float) Math.max(Math.sqrt(x1_2 + y_2), Math.sqrt(x2_2 + y_2)), 0.0f);
        revealAnimator.setDuration((long) getResources().getInteger(17694720));
        revealAnimator.addListener(new 31(revealView));
        this.mMathView.setText(BuildConfig.FLAVOR);
        this.mContainerSolve.setVisibility(8);
        play(revealAnimator);
    }

    protected boolean saveHistory(String expr, String result, boolean ensureResult) {
        this.database.saveHistory(new ItemHistory(expr, result));
        return $assertionsDisabled;
    }

    public void onDefineAndCalc() {
        Intent intent = new Intent(this, DefineVariableActivity.class);
        intent.putExtra(DATA, this.mInputDisplay.getCleanText());
        startActivityForResult(intent, REQ_CODE_DEFINE_VAR);
    }

    public void factorInteger() {
        this.mEvaluator.setFraction(true);
        animateRipple(this.mAnimateSolve, this.mFabCloseMathView, -1, new 32(), true);
        this.mEvaluator.getResultAsTex("FactorInteger(" + this.mInputDisplay.getCleanText() + ")", new 33());
    }

    private boolean isUseFraction() {
        return this.mEvaluator.isFraction();
    }
}
