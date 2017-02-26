package com.example.duy.calculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import com.example.duy.calculator.data.Database;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Constants;
import com.example.duy.calculator.math_eval.LogicEvaluator.EvaluateCallback;
import com.example.duy.calculator.utils.AnimationUtil;
import com.google.android.gms.R;
import io.github.kexanie.library.BuildConfig;
import io.github.kexanie.library.MathView;
import java.util.Random;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class MatrixCalculatorActivity extends AbstractNavDrawer implements OnNavigationItemSelectedListener, OnClickListener, EvaluateCallback {
    private static final int MAX_COL = 100;
    private static final int MAX_ROW = 100;
    private static final String TAG;
    private Button btnAdd;
    private Button btnCreA;
    private Button btnCreB;
    private Button btnMinus;
    private Button btnMul;
    private Button btnSubmit;
    private int currentColumnA;
    private int currentColumnB;
    private int currentRowA;
    private int currentRowB;
    private Database database;
    private EditText editColA;
    private EditText editColB;
    private EditText editRowA;
    private EditText editRowB;
    BigEvaluator evaluator;
    private boolean isDebug;
    private boolean isStart;
    private RelativeLayout mContainerA;
    private RelativeLayout mContainerB;
    private MathView mResult;
    private String matrixA;
    private String matrixB;
    private Random random;
    private Spinner spinOperator;

    class 1 implements DialogInterface.OnClickListener {
        1() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                MatrixCalculatorActivity.this.currentRowA = Integer.parseInt(MatrixCalculatorActivity.this.editRowA.getText().toString());
                MatrixCalculatorActivity.this.currentColumnA = Integer.parseInt(MatrixCalculatorActivity.this.editColA.getText().toString());
                MatrixCalculatorActivity.this.createLayoutMatrix(MatrixCalculatorActivity.this.currentColumnA, MatrixCalculatorActivity.this.currentRowA, MatrixCalculatorActivity.this.mContainerA);
            } catch (NumberFormatException e) {
                MatrixCalculatorActivity.this.showDialog(MatrixCalculatorActivity.this.getResources().getString(R.string.error), MatrixCalculatorActivity.this.getResources().getString(R.string.error_input_num) + " " + e.getMessage());
            } catch (Exception e2) {
                MatrixCalculatorActivity.this.showDialog(null, e2.getMessage());
            }
        }
    }

    class 2 implements DialogInterface.OnClickListener {
        2() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    class 3 implements DialogInterface.OnClickListener {
        3() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                MatrixCalculatorActivity.this.currentRowB = Integer.parseInt(MatrixCalculatorActivity.this.editRowB.getText().toString());
                MatrixCalculatorActivity.this.currentColumnB = Integer.parseInt(MatrixCalculatorActivity.this.editColB.getText().toString());
                MatrixCalculatorActivity.this.createLayoutMatrix(MatrixCalculatorActivity.this.currentColumnB, MatrixCalculatorActivity.this.currentRowB, MatrixCalculatorActivity.this.mContainerB);
            } catch (NumberFormatException e) {
                MatrixCalculatorActivity.this.showDialog(MatrixCalculatorActivity.this.getResources().getString(R.string.error), MatrixCalculatorActivity.this.getResources().getString(R.string.error_input_num) + " " + e.getMessage());
            } catch (Exception e2) {
                MatrixCalculatorActivity.this.showDialog(null, e2.getMessage());
            }
        }
    }

    class 4 implements DialogInterface.OnClickListener {
        4() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    static /* synthetic */ class 5 {
        static final /* synthetic */ int[] $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt;

        static {
            $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt = new int[MatrixOpt.values().length];
            try {
                $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[MatrixOpt.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[MatrixOpt.SUB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[MatrixOpt.MUL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[MatrixOpt.INVERSE_A.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[MatrixOpt.INVERSE_B.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[MatrixOpt.TRANSOPE_A.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[MatrixOpt.TRANSOPE_B.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public enum MatrixOpt {
        ADD,
        SUB,
        MUL,
        INVERSE_A,
        INVERSE_B,
        TRANSOPE_A,
        TRANSOPE_B
    }

    public MatrixCalculatorActivity() {
        this.currentRowA = 0;
        this.currentColumnA = 0;
        this.currentRowB = 0;
        this.currentColumnB = 0;
        this.isStart = false;
        this.isDebug = false;
        this.random = new Random(231378);
    }

    static {
        TAG = MatrixCalculatorActivity.class.getName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_matrix_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.editRowA = (EditText) findViewById(R.id.edit_row_a);
        this.editColA = (EditText) findViewById(R.id.edit_colums_a);
        this.btnCreA = (Button) findViewById(R.id.button_create_mA);
        this.btnCreA.setOnClickListener(this);
        this.editRowB = (EditText) findViewById(R.id.edit_row_b);
        this.editColB = (EditText) findViewById(R.id.edit_colums_b);
        this.btnCreB = (Button) findViewById(R.id.button_create_mB);
        this.btnCreB.setOnClickListener(this);
        this.mContainerA = (RelativeLayout) findViewById(R.id.rl_matrixA);
        this.mContainerB = (RelativeLayout) findViewById(R.id.rl_matrixB);
        this.mResult = (MathView) findViewById(R.id.rl_matrixC);
        this.mResult.setText(BuildConfig.FLAVOR);
        this.database = new Database(this);
        initSpinOperator();
        this.btnSubmit = (Button) findViewById(R.id.btn_submit);
        this.btnSubmit.setOnClickListener(this);
        this.evaluator = ((CalcApplication) getApplicationContext()).getEvaluator();
    }

    private void initSpinOperator() {
        this.spinOperator = (Spinner) findViewById(R.id.spin_matrix_op);
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this, 17367043, getResources().getStringArray(R.array.matrix_operator));
        mAdapter.setDropDownViewResource(17367050);
        this.spinOperator.setAdapter(mAdapter);
    }

    public void onHelpFunction(String s) {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit /*2131689607*/:
                String s = this.spinOperator.getSelectedItem().toString();
                Log.w(TAG, s);
                if (s.equals(getString(R.string.matrix_op_add))) {
                    doEval(MatrixOpt.ADD);
                } else if (s.equals(getString(R.string.matrix_op_sub))) {
                    doEval(MatrixOpt.SUB);
                } else if (s.equals(getString(R.string.matrix_op_mul))) {
                    doEval(MatrixOpt.MUL);
                } else if (s.equals(getString(R.string.matrix_op_inverse_a))) {
                    doEval(MatrixOpt.INVERSE_A);
                } else if (s.equals(getString(R.string.matrix_op_inverse_b))) {
                    doEval(MatrixOpt.INVERSE_B);
                } else if (s.equals(getString(R.string.matrix_op_tranpose_a))) {
                    doEval(MatrixOpt.TRANSOPE_A);
                } else if (s.equals(getString(R.string.matrix_op_tranpose_b))) {
                    doEval(MatrixOpt.TRANSOPE_B);
                }
            case R.id.button_create_mA /*2131689669*/:
                Builder builder = new Builder(this);
                builder.setMessage((int) R.string.msg_delete_matrix);
                builder.setNegativeButton(getString(R.string.yes), new 1());
                builder.setPositiveButton(getString(R.string.close), new 2());
                builder.create().show();
            case R.id.button_create_mB /*2131689673*/:
                Builder builder2 = new Builder(this);
                builder2.setMessage((int) R.string.msg_delete_matrix);
                builder2.setNegativeButton(getString(R.string.yes), new 3());
                builder2.setPositiveButton(getString(R.string.close), new 4());
                builder2.create().show();
            default:
        }
    }

    private void createLayoutMatrix(int col, int row, ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        int index = 1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                AppCompatEditText editText = new AppCompatEditText(this);
                editText.setHint("[" + i + "," + j + "]");
                LayoutParams params = new LayoutParams(MAX_ROW, -2);
                editText.setSingleLine(true);
                editText.setInputType(AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
                editText.setId(index);
                if (this.isDebug) {
                    editText.setText(String.valueOf(this.random.nextInt(AnimationUtil.DEFAULT_SHRINK_GROW_DURATION) - 100));
                }
                if (j == 0) {
                    params.addRule(9, -1);
                } else {
                    params.addRule(1, index - 1);
                }
                if (i == 0) {
                    params.addRule(10, -1);
                } else {
                    params.addRule(3, index - col);
                }
                editText.setLayoutParams(params);
                viewGroup.addView(editText);
                index++;
            }
        }
    }

    public String getMatrixA() {
        StringBuilder res = new StringBuilder();
        int col = Integer.parseInt(this.editColA.getText().toString());
        int row = Integer.parseInt(this.editRowA.getText().toString());
        int index = 1;
        res.append(VectorFormat.DEFAULT_PREFIX);
        for (int i = 0; i < row; i++) {
            res.append(VectorFormat.DEFAULT_PREFIX);
            for (int j = 0; j < col; j++) {
                String s = ((EditText) this.mContainerA.findViewById(index)).getText().toString();
                if (s.isEmpty()) {
                    s = Constants.ZERO;
                }
                index++;
                if (j == col - 1) {
                    res.append(s);
                } else {
                    res.append(s).append(",");
                }
            }
            if (i != row - 1) {
                res.append(VectorFormat.DEFAULT_SUFFIX).append(",");
            } else {
                res.append(VectorFormat.DEFAULT_SUFFIX);
            }
        }
        res.append(VectorFormat.DEFAULT_SUFFIX);
        Log.d(TAG, "getMatrixA: " + res.toString());
        return res.toString();
    }

    public String getMatrixB() {
        StringBuilder res = new StringBuilder();
        res.append(VectorFormat.DEFAULT_PREFIX);
        int col = Integer.parseInt(this.editColB.getText().toString());
        int row = Integer.parseInt(this.editRowB.getText().toString());
        int index = 1;
        for (int i = 0; i < row; i++) {
            res.append(VectorFormat.DEFAULT_PREFIX);
            for (int j = 0; j < col; j++) {
                String s = ((EditText) this.mContainerB.findViewById(index)).getText().toString();
                if (s.isEmpty()) {
                    s = Constants.ZERO;
                }
                index++;
                if (j == col - 1) {
                    res.append(s);
                } else {
                    res.append(s).append(",");
                }
            }
            if (i != row - 1) {
                res.append(VectorFormat.DEFAULT_SUFFIX).append(",");
            } else {
                res.append(VectorFormat.DEFAULT_SUFFIX);
            }
        }
        res.append(VectorFormat.DEFAULT_SUFFIX);
        Log.d(TAG, "getMatrixB: " + res.toString());
        return res.toString();
    }

    public void doEval(MatrixOpt opt) {
        String expr;
        switch (5.$SwitchMap$com$example$duy$calculator$MatrixCalculatorActivity$MatrixOpt[opt.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
            case IExpr.DOUBLEID /*2*/:
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                try {
                    this.matrixA = getMatrixA();
                    this.matrixB = getMatrixB();
                    if (opt == MatrixOpt.ADD) {
                        expr = this.matrixA + " + " + this.matrixB;
                    } else if (opt == MatrixOpt.MUL) {
                        expr = this.matrixA + " * " + this.matrixB;
                    } else {
                        expr = this.matrixA + " - " + this.matrixB;
                    }
                    Log.d(TAG, "doEval: " + expr);
                    this.evaluator.getResultAsTex(expr, this);
                } catch (Exception e) {
                    e.printStackTrace();
                    showDialog(getString(R.string.error), e.getMessage());
                }
            case IExpr.DOUBLECOMPLEXID /*4*/:
                try {
                    expr = "Inverse(" + getMatrixA() + ")";
                    Log.d(TAG, "doEval: " + expr);
                    this.evaluator.getResultAsTex(expr, this);
                } catch (NumberFormatException e2) {
                    showDialog(getResources().getString(R.string.error), getResources().getString(R.string.error_input_num) + " " + e2.getMessage());
                } catch (Exception e3) {
                    showDialog(null, e3.getMessage());
                }
            case ValueServer.CONSTANT_MODE /*5*/:
                try {
                    expr = "Inverse(" + getMatrixB() + ")";
                    Log.d(TAG, "doEval: " + expr);
                    this.evaluator.getResultAsTex(expr, this);
                } catch (NumberFormatException e22) {
                    showDialog(getResources().getString(R.string.error), getResources().getString(R.string.error_input_num) + " " + e22.getMessage());
                } catch (Exception e32) {
                    showDialog(null, e32.getMessage());
                }
            case io.github.kexanie.library.R.styleable.Toolbar_contentInsetEnd /*6*/:
                try {
                    expr = "Transpose(" + getMatrixA() + ")";
                    Log.d(TAG, "doEval: " + expr);
                    this.evaluator.getResultAsTex(expr, this);
                } catch (NumberFormatException e222) {
                    showDialog(getResources().getString(R.string.error), getResources().getString(R.string.error_input_num) + " " + e222.getMessage());
                } catch (Exception e322) {
                    showDialog(null, e322.getMessage());
                }
            case io.github.kexanie.library.R.styleable.Toolbar_contentInsetLeft /*7*/:
                try {
                    expr = "Transpose(" + getMatrixB() + ")";
                    this.evaluator.getResultAsTex(expr, this);
                    Log.d(TAG, "doEval: " + expr);
                } catch (NumberFormatException e2222) {
                    showDialog(getResources().getString(R.string.error), getResources().getString(R.string.error_input_num) + " " + e2222.getMessage());
                } catch (Exception e3222) {
                    showDialog(null, e3222.getMessage());
                }
            default:
        }
    }

    private void setText(String tex) {
        this.mResult.setText(tex);
    }

    public void onEvaluate(String expr, String result, int resultId) {
        Log.d(TAG, "onEvaluate: " + expr + " = " + result);
        if (resultId == -1) {
            setText(getString(R.string.error));
        } else if (resultId == 1) {
            setText("$$" + result + "$$");
        } else if (resultId == 2) {
            setText("$$NCALC+$$");
        }
    }
}
