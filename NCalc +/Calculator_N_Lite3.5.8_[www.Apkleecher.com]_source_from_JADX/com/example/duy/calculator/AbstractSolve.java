package com.example.duy.calculator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.LogicEvaluator.EvaluateCallback;
import com.example.duy.calculator.math_eval.TaskSolve;
import com.example.duy.calculator.math_eval.Tokenizer;
import com.example.duy.calculator.view.AnimationFinishedListener;
import com.example.duy.calculator.view.MathViewActivity;
import com.example.duy.calculator.view.ResizingEditText;
import com.example.duy.calculator.view.RevealView;
import io.codetail.animation.ViewAnimationUtils;
import io.github.kexanie.library.MathView;
import io.github.kexanie.library.R;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

public abstract class AbstractSolve extends AbstractNavDrawer implements OnClickListener {
    protected static final int DERIVATIVE = 0;
    protected static final int NONE = -1;
    protected static final int SIMPLIFY = 2;
    protected static final int SOLVE = 1;
    protected String TAG;
    protected Button btnClear;
    protected Button btnSolve;
    protected SwitchCompat ckbTex;
    protected EditText editFrom;
    protected EditText editParam;
    protected EditText editTo;
    protected Handler handler;
    protected ViewGroup mDisplayForeground;
    protected BigEvaluator mEvaluator;
    protected FloatingActionButton mFull;
    protected ResizingEditText mInputDisplay;
    protected LinearLayout mLayoutLimit;
    protected MathView mMathView;
    protected ProgressBar mProgress;
    protected AppCompatSpinner mSpinner;
    protected Tokenizer mTokenizer;
    private int status;
    protected TextView txtTitle;

    class 1 extends AnimationFinishedListener {
        1() {
        }

        public void onAnimationFinished() {
        }
    }

    class 2 extends AnimationFinishedListener {
        final /* synthetic */ ViewGroup val$foreground;
        final /* synthetic */ RevealView val$revealView;

        2(ViewGroup viewGroup, RevealView revealView) {
            this.val$foreground = viewGroup;
            this.val$revealView = revealView;
        }

        public void onAnimationFinished() {
            this.val$foreground.removeView(this.val$revealView);
        }
    }

    class 3 extends AnimationFinishedListener {
        final /* synthetic */ Animator val$alphaAnimator;

        3(Animator animator) {
            this.val$alphaAnimator = animator;
        }

        public void onAnimationFinished() {
            AbstractSolve.this.play(this.val$alphaAnimator);
        }
    }

    class 4 implements OnCheckedChangeListener {
        4() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        }
    }

    class 5 implements OnClickListener {
        5() {
        }

        public void onClick(View v) {
            AbstractSolve.this.expandResult();
        }
    }

    class 6 implements OnClickListener {
        6() {
        }

        public void onClick(View view) {
            AbstractSolve.this.showHelp();
        }
    }

    class 7 implements Runnable {
        final /* synthetic */ String val$finalCommand;

        class 1 implements Runnable {
            1() {
            }

            public void run() {
                AbstractSolve.this.mProgress.setVisibility(AbstractSolve.DERIVATIVE);
                AbstractSolve.this.btnSolve.setEnabled(false);
            }
        }

        class 2 implements EvaluateCallback {

            class 1 implements Runnable {
                final /* synthetic */ int val$errorResourceId;
                final /* synthetic */ String val$finalResult;

                1(int i, String str) {
                    this.val$errorResourceId = i;
                    this.val$finalResult = str;
                }

                public void run() {
                    if (this.val$errorResourceId == AbstractSolve.SOLVE) {
                        AbstractSolve.this.mMathView.setText(this.val$finalResult);
                    } else {
                        AbstractSolve.this.mMathView.setText("<h3>" + AbstractSolve.this.getString(R.string.error) + "</h3>" + this.val$finalResult);
                    }
                    AbstractSolve.this.btnSolve.setEnabled(true);
                    AbstractSolve.this.mProgress.setVisibility(8);
                }
            }

            2() {
            }

            public void onEvaluate(String expr, String result, int errorResourceId) {
                AbstractSolve.this.handler.post(new 1(errorResourceId, result));
            }
        }

        class 3 implements EvaluateCallback {

            class 1 implements Runnable {
                final /* synthetic */ int val$errorResourceId;
                final /* synthetic */ String val$finalResult;

                1(int i, String str) {
                    this.val$errorResourceId = i;
                    this.val$finalResult = str;
                }

                public void run() {
                    if (this.val$errorResourceId == AbstractSolve.SOLVE) {
                        AbstractSolve.this.mMathView.setText(this.val$finalResult);
                    } else {
                        AbstractSolve.this.mMathView.setText("<h3>" + AbstractSolve.this.getString(R.string.error) + "</h3>" + this.val$finalResult);
                    }
                    AbstractSolve.this.btnSolve.setEnabled(true);
                    AbstractSolve.this.mProgress.setVisibility(8);
                }
            }

            3() {
            }

            public void onEvaluate(String expr, String result, int errorResourceId) {
                AbstractSolve.this.handler.post(new 1(errorResourceId, result));
            }
        }

        class 4 implements EvaluateCallback {

            class 1 implements Runnable {
                final /* synthetic */ int val$errorResourceId;
                final /* synthetic */ String val$finalResult;

                1(int i, String str) {
                    this.val$errorResourceId = i;
                    this.val$finalResult = str;
                }

                public void run() {
                    if (this.val$errorResourceId == AbstractSolve.SOLVE) {
                        AbstractSolve.this.mMathView.setText("$$" + this.val$finalResult + "$$");
                    } else {
                        AbstractSolve.this.mMathView.setText("<h3>" + AbstractSolve.this.getString(R.string.error) + "</h3>" + this.val$finalResult);
                    }
                    AbstractSolve.this.btnSolve.setEnabled(true);
                    AbstractSolve.this.mProgress.setVisibility(8);
                }
            }

            4() {
            }

            public void onEvaluate(String expr, String result, int errorResourceId) {
                AbstractSolve.this.handler.post(new 1(errorResourceId, result));
            }
        }

        class 5 implements Runnable {
            final /* synthetic */ SyntaxError val$e;

            5(SyntaxError syntaxError) {
                this.val$e = syntaxError;
            }

            public void run() {
                AbstractSolve.this.mProgress.setVisibility(8);
                AbstractSolve.this.btnSolve.setEnabled(true);
                AbstractSolve.this.showDialog(AbstractSolve.this.getString(R.string.error), AbstractSolve.this.getString(R.string.not_expression) + "\n" + this.val$e.getMessage());
            }
        }

        class 6 implements Runnable {
            final /* synthetic */ MathException val$me;

            6(MathException mathException) {
                this.val$me = mathException;
            }

            public void run() {
                AbstractSolve.this.mProgress.setVisibility(8);
                AbstractSolve.this.btnSolve.setEnabled(true);
                AbstractSolve.this.showDialog(AbstractSolve.this.getString(R.string.error), this.val$me.getMessage());
            }
        }

        class 7 implements Runnable {
            final /* synthetic */ Exception val$e;

            7(Exception exception) {
                this.val$e = exception;
            }

            public void run() {
                AbstractSolve.this.mProgress.setVisibility(8);
                AbstractSolve.this.showDialog(AbstractSolve.this.getString(R.string.error), this.val$e.getMessage());
                AbstractSolve.this.btnSolve.setEnabled(true);
            }
        }

        7(String str) {
            this.val$finalCommand = str;
        }

        public void run() {
            try {
                AbstractSolve.this.handler.post(new 1());
                AbstractSolve.this.mEvaluator.getUtil().evaluate(this.val$finalCommand);
                AbstractSolve.this.mEvaluator.setFraction(true);
                switch (AbstractSolve.this.status) {
                    case AbstractSolve.DERIVATIVE /*0*/:
                        AbstractSolve.this.mEvaluator.getDerivative(this.val$finalCommand, new 2());
                    case AbstractSolve.SIMPLIFY /*2*/:
                        AbstractSolve.this.mEvaluator.simplifyExpression(this.val$finalCommand, new 3());
                    default:
                        AbstractSolve.this.mEvaluator.getResultAsTex(this.val$finalCommand, new 4());
                }
            } catch (SyntaxError e) {
                e.printStackTrace();
                AbstractSolve.this.handler.post(new 5(e));
            } catch (MathException me) {
                AbstractSolve.this.handler.post(new 6(me));
            } catch (Exception e2) {
                AbstractSolve.this.handler.post(new 7(e2));
            }
        }
    }

    public abstract int getIdStringHelp();

    public abstract void showHelp();

    public AbstractSolve() {
        this.handler = new Handler();
        this.TAG = AbstractSolve.class.getName();
        this.status = NONE;
    }

    protected void onAnimate() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        animateRipple(this.mDisplayForeground, this.btnSolve, typedValue.data, new 1(), true);
    }

    private void animateRipple(ViewGroup foreground, View sourceView, int color, AnimatorListener listener, boolean out) {
        Animator revealAnimator;
        RevealView revealView = new RevealView(this);
        revealView.setLayoutParams(this.mLayoutParams);
        revealView.setRevealColor(color);
        if (foreground == null) {
            Log.d(this.TAG, "animateRipple:  foreground null");
        }
        if (revealView == null) {
            Log.d(this.TAG, "animateRipple: reveal null");
        }
        foreground.addView(revealView);
        int[] clearLocation = new int[SIMPLIFY];
        if (sourceView != null) {
            sourceView.getLocationInWindow(clearLocation);
            clearLocation[DERIVATIVE] = clearLocation[DERIVATIVE] + (sourceView.getWidth() / SIMPLIFY);
            clearLocation[SOLVE] = clearLocation[SOLVE] + (sourceView.getHeight() / SIMPLIFY);
        } else {
            clearLocation[DERIVATIVE] = foreground.getWidth() / SIMPLIFY;
            clearLocation[SOLVE] = foreground.getHeight() / SIMPLIFY;
        }
        int revealCenterX = clearLocation[DERIVATIVE] - revealView.getLeft();
        int revealCenterY = clearLocation[SOLVE] - revealView.getTop();
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
        Property property = View.ALPHA;
        float[] fArr = new float[SOLVE];
        fArr[DERIVATIVE] = DERIVATIVE;
        Animator alphaAnimator = ObjectAnimator.ofFloat(revealView, property, fArr);
        alphaAnimator.setDuration((long) getResources().getInteger(17694720));
        alphaAnimator.addListener(new 2(foreground, revealView));
        revealAnimator.addListener(new 3(alphaAnimator));
        play(revealAnimator);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_solve_equation);
        CalcApplication application = (CalcApplication) getApplicationContext();
        this.mTokenizer = application.getTokenizer();
        this.mEvaluator = application.getEvaluator();
        Toolbar toolbar = (Toolbar) findViewById(com.google.android.gms.R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.btnSolve = (Button) findViewById(R.id.btn_solve);
        this.mInputDisplay = (ResizingEditText) findViewById(R.id.edit_input);
        this.mInputDisplay.setHint(getString(R.string.input_expression) + "...");
        this.mMathView = (MathView) findViewById(R.id.math_view);
        this.mMathView.setText(getString(getIdStringHelp()));
        findViewById(R.id.btn_clear).setOnClickListener(this);
        this.btnSolve.setOnClickListener(this);
        this.mDisplayForeground = (ViewGroup) findViewById(R.id.the_clear_animation);
        this.mProgress = (ProgressBar) findViewById(com.google.android.gms.R.id.progressBar);
        this.ckbTex = (SwitchCompat) findViewById(R.id.ckbTex);
        this.ckbTex.setOnCheckedChangeListener(new 4());
        this.txtTitle = (TextView) findViewById(R.id.txtTitle);
        this.mSpinner = (AppCompatSpinner) findViewById(R.id.spinner);
        this.btnClear = (Button) findViewById(R.id.btn_clear);
        this.editParam = (EditText) findViewById(R.id.edit_params);
        this.mFull = (FloatingActionButton) findViewById(R.id.fab);
        this.mFull.setOnClickListener(new 5());
        ((FloatingActionButton) findViewById(R.id.fab_help)).setOnClickListener(new 6());
        this.editFrom = (EditText) findViewById(R.id.edit_from);
        this.editFrom.setHint(getString(R.string.from));
        this.editTo = (EditText) findViewById(R.id.edit_to);
        this.editTo.setHint(getString(R.string.to));
        this.mLayoutLimit = (LinearLayout) findViewById(R.id.layout_limit);
        this.mLayoutLimit.setVisibility(8);
    }

    public void onResult(String command) {
        onResult(command, true);
    }

    public void onResult(String command, boolean b) {
        Log.d(this.TAG, "onResult: " + command);
        if (b) {
            onAnimate();
        }
        if (command.isEmpty()) {
            this.mInputDisplay.setError(getString(R.string.not_input));
            this.mInputDisplay.requestFocus();
            return;
        }
        String finalCommand = this.mTokenizer.getNormalExpression(command);
        hideKeyboard(this.mInputDisplay);
        if (this.status == SOLVE) {
            new TaskSolve((CalcApplication) getApplicationContext(), this.mProgress, this.mMathView, command).execute(new Void[DERIVATIVE]);
        } else {
            new Thread(new 7(finalCommand)).start();
        }
    }

    private void showKeyboard() {
    }

    public void onClear() {
        onAnimate();
        this.mInputDisplay.setText(null);
        this.mMathView.setText(getString(getIdStringHelp()));
        showKeyboard();
        if (this.editFrom.isShown()) {
            this.editFrom.setText(null);
        }
        if (this.editTo.isShown()) {
            this.editTo.setText(null);
        }
    }

    protected void expandResult() {
        Intent intent = new Intent(this, MathViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MathViewActivity.DATA, this.mMathView.getText());
        intent.putExtra(MathViewActivity.DATA, bundle);
        startActivity(intent);
    }

    public void insertTextDisplay(String text) {
        this.mInputDisplay.insert(text);
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
