package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.duy.calculator.math_eval.Tokenizer;
import com.example.duy.calculator.view.ResizingEditText;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;
import com.google.android.gms.R;
import edu.jas.ps.UnivPowerSeriesRing;
import io.github.kexanie.library.BuildConfig;
import io.github.kexanie.library.MathView;
import java.io.StringWriter;
import java.util.ArrayList;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.interfaces.IExpr;

public class SystemEquationActivity extends AbstractNavDrawer {
    private static final String STARTED;
    private OnClickListener btnAddClick;
    private OnClickListener btnSolveClick;
    private OnClickListener clearClick;
    private EditText editParams;
    private LinearLayout mContainer;
    private int mCountView;
    private MathView mathView;
    SharedPreferences preferences;
    public ProgressBar progressBar;
    private TextView txtResult;

    class 1 implements OnClickListener {
        1() {
        }

        public void onClick(View view) {
            if (SystemEquationActivity.this.mContainer.getChildCount() == 0) {
                Toast.makeText(SystemEquationActivity.this, R.string.not_input_equation, 0).show();
                return;
            }
            new ThreadSolve(SystemEquationActivity.this).execute(new Void[0]);
            if (SystemEquationActivity.this.mContainer.getChildCount() > 0) {
                try {
                    SystemEquationActivity.this.hideKeyboard(SystemEquationActivity.this.mContainer.getChildAt(0));
                } catch (Exception e) {
                }
            }
        }
    }

    class 2 implements OnClickListener {
        2() {
        }

        public void onClick(View view) {
            if (SystemEquationActivity.this.mCountView > 0) {
                SystemEquationActivity.this.mContainer.removeViewAt(SystemEquationActivity.this.mCountView - 1);
                SystemEquationActivity.this.mathView.setText("$$NCalc+$$");
                SystemEquationActivity.this.mCountView = SystemEquationActivity.this.mCountView - 1;
            }
        }
    }

    class 3 implements OnClickListener {
        3() {
        }

        public void onClick(View view) {
            SystemEquationActivity.this.addParams();
        }
    }

    class 4 implements OnFocusChangeListener {
        4() {
        }

        public void onFocusChange(View view, boolean b) {
            if (b) {
                ((EditText) view).selectAll();
                return;
            }
            ResizingEditText v = (ResizingEditText) view;
            String s = v.getText().toString();
            if (!s.contains("=")) {
                v.setText(s + "=0");
            }
        }
    }

    class 5 implements Runnable {
        final /* synthetic */ TranslateAnimation val$animation;
        final /* synthetic */ ResizingEditText val$editText;

        5(ResizingEditText resizingEditText, TranslateAnimation translateAnimation) {
            this.val$editText = resizingEditText;
            this.val$animation = translateAnimation;
        }

        public void run() {
            this.val$editText.startAnimation(this.val$animation);
        }
    }

    class 6 implements Listener {
        final /* synthetic */ Editor val$editor;

        6(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(SystemEquationActivity.STARTED, true);
            this.val$editor.apply();
            new ThreadSolve(SystemEquationActivity.this).execute(new Void[0]);
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
            new ThreadSolve(SystemEquationActivity.this).execute(new Void[0]);
        }
    }

    class ThreadSolve extends AsyncTask<Void, Void, String> {
        private SystemEquationActivity activity;
        private ArrayList<String> arrayList;
        private StringBuilder equation;
        private boolean isOk;
        private Tokenizer tokenizer;

        public ThreadSolve(SystemEquationActivity context) {
            int i;
            this.arrayList = new ArrayList();
            this.equation = new StringBuilder();
            this.isOk = true;
            this.activity = context;
            this.activity.progressBar.setVisibility(0);
            this.tokenizer = new Tokenizer(context);
            EvalUtilities utilities = new EvalUtilities(true, true);
            for (i = 0; i < SystemEquationActivity.this.mContainer.getChildCount(); i++) {
                ResizingEditText editText = (ResizingEditText) SystemEquationActivity.this.mContainer.getChildAt(i);
                String exp = replaceEqualSymbol(this.tokenizer.getNormalExpression(editText.getCleanText()));
                try {
                    Log.d(SystemEquationActivity.this.TAG, exp + " ->> " + utilities.evaluate(exp).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    editText.setError(this.activity.getString(R.string.error));
                    this.isOk = false;
                }
                if (!exp.isEmpty()) {
                    this.arrayList.add(exp);
                }
            }
            this.equation.append("Solve({");
            for (i = 0; i < this.arrayList.size(); i++) {
                String s = replaceEqualSymbol((String) this.arrayList.get(i));
                if (i != this.arrayList.size() - 1) {
                    this.equation.append(s);
                    this.equation.append(",");
                } else {
                    this.equation.append(s);
                }
            }
            this.equation.append(VectorFormat.DEFAULT_SUFFIX);
            this.equation.append(",");
            this.equation.append(VectorFormat.DEFAULT_PREFIX).append(SystemEquationActivity.this.editParams.getText().toString()).append(VectorFormat.DEFAULT_SUFFIX);
            this.equation.append(")");
        }

        private String replaceEqualSymbol(String s) {
            if (!s.contains("=")) {
                s = s + "==0";
            }
            if (!s.contains("==")) {
                s = s.replace("=", "==");
            }
            while (s.contains("===")) {
                s = s.replace("===", "==");
            }
            return s;
        }

        protected String doInBackground(Void... voids) {
            if (!this.isOk) {
                return BuildConfig.FLAVOR;
            }
            String input = this.equation.toString();
            Log.d(SystemEquationActivity.this.TAG, input);
            try {
                IExpr expr = new EvalUtilities(new EvalEngine(true), false, true).evaluate(input);
                Log.d(SystemEquationActivity.this.TAG, expr.toString());
                TeXUtilities teXUtilities = new TeXUtilities(new EvalEngine(true), true);
                StringWriter stringWriter = new StringWriter();
                teXUtilities.toTeX(expr, stringWriter);
                return stringWriter.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return this.activity.getString(R.string.error);
            }
        }

        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            this.activity.progressBar.setVisibility(8);
            this.activity.setSolveResult(aVoid);
        }
    }

    public SystemEquationActivity() {
        this.mCountView = 0;
        this.btnSolveClick = new 1();
        this.clearClick = new 2();
        this.btnAddClick = new 3();
    }

    static {
        STARTED = SystemEquationActivity.class.getName() + "started";
    }

    private void addParams() {
        addParams(null);
    }

    private void addParams(String text) {
        ResizingEditText editText = new ResizingEditText(this);
        editText.setLayoutParams(new LayoutParams(-1, -2));
        editText.setHint(getString(R.string.input_equation));
        editText.setOnFocusChangeListener(new 4());
        this.mCountView++;
        editText.setId(this.mCountView);
        this.mContainer.addView(editText);
        editText.requestFocus();
        editText.setText(text);
        editText.post(new 5(editText, new TranslateAnimation((float) editText.getWidth(), 0.0f, 0.0f, 0.0f)));
        notifyParams();
    }

    private void hideKeyboard(View et) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    private void notifyParams() {
        switch (this.mCountView) {
            case ValueServer.REPLAY_MODE /*1*/:
                this.editParams.setText(UnivPowerSeriesRing.DEFAULT_NAME);
            case IExpr.DOUBLEID /*2*/:
                this.editParams.setText("x, y");
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                this.editParams.setText("x, y, z");
            default:
                Toast.makeText(this, "Nh\u1eadp c\u00e1c \u1ea9n c\u1ee7a h\u1ec7 ph\u01b0\u01a1ng tr\u00ecnh", 0).show();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_linear_system);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.mContainer = (LinearLayout) findViewById(R.id.container);
        this.mathView = (MathView) findViewById(R.id.math_view);
        this.txtResult = (TextView) findViewById(R.id.txt_result);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        this.editParams = (EditText) findViewById(R.id.edit_params);
        findViewById(R.id.btn_add).setOnClickListener(this.btnAddClick);
        findViewById(R.id.btn_solve).setOnClickListener(this.btnSolveClick);
        findViewById(R.id.btn_clear).setOnClickListener(this.clearClick);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            showHelp();
        }
    }

    private void showHelp() {
        Editor editor = this.preferences.edit();
        View btnAdd = findViewById(R.id.btn_add);
        View btnSolve = findViewById(R.id.btn_solve);
        View btnClear = findViewById(R.id.btn_clear);
        TapTarget.forView(btnAdd, getString(R.string.add_equation)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(btnClear, getString(R.string.remove_equation)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(this.editParams, getString(R.string.enter_params)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(btnSolve, getString(R.string.solve_system_equation)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target, target2, target3, target4);
        sequence.listener(new 6(editor));
        addParams("2x - y = 2");
        addParams("3x + 2y = 0");
        sequence.start();
    }

    public void onHelpFunction(String s) {
    }

    private void setSolveResult(String aVoid) {
        if (aVoid.toLowerCase().contains("solve")) {
            this.mathView.setText(getString(R.string.no_root));
        }
        this.mathView.setText("$$ " + aVoid + " $$");
    }
}
