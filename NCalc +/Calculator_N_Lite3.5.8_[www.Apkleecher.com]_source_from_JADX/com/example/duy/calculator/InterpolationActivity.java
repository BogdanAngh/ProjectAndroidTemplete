package com.example.duy.calculator;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.duy.calculator.adapters.PointAdapter;
import com.google.android.gms.R;
import io.github.kexanie.library.MathView;
import java.io.StringWriter;
import org.apache.commons.math4.geometry.VectorFormat;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

@Deprecated
public class InterpolationActivity extends AbstractNavDrawer {
    private Button btnAdd;
    private Button btnSolve;
    protected SwitchCompat ckbTex;
    private Handler handler;
    private PointAdapter mAdapter;
    private RecyclerView mContainerPoint;
    private ContentLoadingProgressBar mProgress;
    protected MathView mathView;
    protected TextView txtResult;

    class 1 extends SimpleCallback {
        1(int x0, int x1) {
            super(x0, x1);
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            return false;
        }

        public void onSwiped(ViewHolder viewHolder, int direction) {
            InterpolationActivity.this.mAdapter.remove(viewHolder.getAdapterPosition());
        }
    }

    class 2 implements OnClickListener {
        2() {
        }

        public void onClick(View view) {
            InterpolationActivity.this.mAdapter.addPoint();
            InterpolationActivity.this.mContainerPoint.scrollToPosition(InterpolationActivity.this.mAdapter.getItemCount() - 1);
        }
    }

    class 3 implements OnClickListener {
        3() {
        }

        public void onClick(View view) {
            InterpolationActivity.this.doSolve();
        }
    }

    class 4 implements Runnable {
        final /* synthetic */ String val$dataPoint;
        final /* synthetic */ double[] val$x;
        final /* synthetic */ double[] val$y;

        class 1 implements Runnable {
            1() {
            }

            public void run() {
                InterpolationActivity.this.mProgress.setVisibility(0);
            }
        }

        class 2 implements Runnable {
            final /* synthetic */ String val$finalCoefficent;
            final /* synthetic */ StringWriter val$finalStringWriter;

            2(StringWriter stringWriter, String str) {
                this.val$finalStringWriter = stringWriter;
                this.val$finalCoefficent = str;
            }

            public void run() {
                InterpolationActivity.this.txtResult.setVisibility(8);
                InterpolationActivity.this.mathView.setVisibility(0);
                InterpolationActivity.this.mathView.setText("$$y = " + this.val$finalStringWriter.toString() + ";$$" + "$$" + this.val$finalCoefficent + "$$");
            }
        }

        class 3 implements Runnable {
            final /* synthetic */ String val$finalTex;

            3(String str) {
                this.val$finalTex = str;
            }

            public void run() {
                InterpolationActivity.this.mathView.setVisibility(8);
                InterpolationActivity.this.txtResult.setVisibility(0);
                InterpolationActivity.this.txtResult.setText(this.val$finalTex);
            }
        }

        class 4 implements Runnable {
            4() {
            }

            public void run() {
                InterpolationActivity.this.mProgress.setIndeterminate(false);
                InterpolationActivity.this.btnSolve.setEnabled(true);
                InterpolationActivity.this.mProgress.setVisibility(8);
            }
        }

        class 5 implements Runnable {
            final /* synthetic */ SyntaxError val$e;

            5(SyntaxError syntaxError) {
                this.val$e = syntaxError;
            }

            public void run() {
                InterpolationActivity.this.mProgress.setVisibility(8);
                InterpolationActivity.this.btnSolve.setEnabled(true);
                InterpolationActivity.this.mProgress.setIndeterminate(false);
                InterpolationActivity.this.showDialog("Syntax error", this.val$e.getMessage());
            }
        }

        class 6 implements Runnable {
            final /* synthetic */ MathException val$me;

            6(MathException mathException) {
                this.val$me = mathException;
            }

            public void run() {
                InterpolationActivity.this.mProgress.setVisibility(8);
                InterpolationActivity.this.btnSolve.setEnabled(true);
                InterpolationActivity.this.mProgress.setIndeterminate(false);
                InterpolationActivity.this.showDialog("Math error", this.val$me.getMessage());
            }
        }

        class 7 implements Runnable {
            final /* synthetic */ Exception val$e;

            7(Exception exception) {
                this.val$e = exception;
            }

            public void run() {
                InterpolationActivity.this.mProgress.setVisibility(8);
                InterpolationActivity.this.showDialog("Math error", this.val$e.getMessage());
                InterpolationActivity.this.mProgress.setIndeterminate(false);
                InterpolationActivity.this.btnSolve.setEnabled(true);
            }
        }

        4(String str, double[] dArr, double[] dArr2) {
            this.val$dataPoint = str;
            this.val$x = dArr;
            this.val$y = dArr2;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r20 = this;
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r17 = r17.handler;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r18 = new com.example.duy.calculator.InterpolationActivity$4$1;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r18;
            r1 = r20;
            r0.<init>();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17.post(r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r16 = new org.matheclipse.core.eval.EvalUtilities;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = 0;
            r18 = 1;
            r16.<init>(r17, r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r20;
            r0 = r0.val$dataPoint;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r6 = r16.evaluate(r17);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r0 = r17;
            r0 = r0.TAG;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r0 = r20;
            r0 = r0.val$dataPoint;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r18 = r0;
            android.util.Log.d(r17, r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r0 = r17;
            r0 = r0.TAG;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r18 = r6.toString();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            android.util.Log.d(r17, r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r3 = "y = ";
            r10 = new org.apache.commons.math4.analysis.polynomials.PolynomialFunctionLagrangeForm;	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r0 = r20;
            r0 = r0.val$x;	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r17 = r0;
            r0 = r20;
            r0 = r0.val$y;	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r18 = r0;
            r0 = r17;
            r1 = r18;
            r10.<init>(r0, r1);	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r2 = r10.getCoefficients();	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r0 = r2.length;	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r17 = r0;
            r13 = r17 + -1;
            r11 = 0;
        L_0x0072:
            r0 = r2.length;	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r17 = r0;
            r0 = r17;
            if (r11 >= r0) goto L_0x00a9;
        L_0x0079:
            r17 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r17.<init>();	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r0 = r17;
            r17 = r0.append(r3);	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r18 = "+";
            r17 = r17.append(r18);	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r18 = r2[r11];	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r17 = r17.append(r18);	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r18 = "x^";
            r17 = r17.append(r18);	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r0 = r17;
            r17 = r0.append(r13);	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r3 = r17.toString();	 Catch:{ Exception -> 0x00a5, SyntaxError -> 0x0132, MathException -> 0x014d }
            r13 = r13 + -1;
            r11 = r11 + 1;
            goto L_0x0072;
        L_0x00a5:
            r4 = move-exception;
            r4.printStackTrace();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
        L_0x00a9:
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r0 = r17;
            r0 = r0.ckbTex;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r17 = r17.isChecked();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            if (r17 == 0) goto L_0x0117;
        L_0x00bb:
            r14 = new java.io.StringWriter;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r14.<init>();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r5 = new org.matheclipse.core.eval.EvalEngine;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = 0;
            r0 = r17;
            r5.<init>(r0);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r15 = new org.matheclipse.core.eval.TeXUtilities;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = 0;
            r0 = r17;
            r15.<init>(r5, r0);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r15.toTeX(r6, r14);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r0 = r17;
            r0 = r0.TAG;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r18 = r14.toString();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            android.util.Log.d(r17, r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r8 = r14;
            r7 = r3;
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r17 = r17.handler;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r18 = new com.example.duy.calculator.InterpolationActivity$4$2;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r18;
            r1 = r20;
            r0.<init>(r8, r7);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17.post(r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
        L_0x0100:
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r17 = r17.handler;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r18 = new com.example.duy.calculator.InterpolationActivity$4$4;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r18;
            r1 = r20;
            r0.<init>();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17.post(r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
        L_0x0116:
            return;
        L_0x0117:
            r9 = r6.toString();	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17 = r0;
            r17 = r17.handler;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r18 = new com.example.duy.calculator.InterpolationActivity$4$3;	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r0 = r18;
            r1 = r20;
            r0.<init>(r9);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            r17.post(r18);	 Catch:{ SyntaxError -> 0x0132, MathException -> 0x014d, Exception -> 0x0165 }
            goto L_0x0100;
        L_0x0132:
            r4 = move-exception;
            r4.printStackTrace();
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;
            r17 = r0;
            r17 = r17.handler;
            r18 = new com.example.duy.calculator.InterpolationActivity$4$5;
            r0 = r18;
            r1 = r20;
            r0.<init>(r4);
            r17.post(r18);
            goto L_0x0116;
        L_0x014d:
            r12 = move-exception;
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;
            r17 = r0;
            r17 = r17.handler;
            r18 = new com.example.duy.calculator.InterpolationActivity$4$6;
            r0 = r18;
            r1 = r20;
            r0.<init>(r12);
            r17.post(r18);
            goto L_0x0116;
        L_0x0165:
            r4 = move-exception;
            r0 = r20;
            r0 = com.example.duy.calculator.InterpolationActivity.this;
            r17 = r0;
            r17 = r17.handler;
            r18 = new com.example.duy.calculator.InterpolationActivity$4$7;
            r0 = r18;
            r1 = r20;
            r0.<init>(r4);
            r17.post(r18);
            goto L_0x0116;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.example.duy.calculator.InterpolationActivity.4.run():void");
        }
    }

    public InterpolationActivity() {
        this.handler = new Handler();
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_interpolate);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.mAdapter = new PointAdapter(this);
        this.mContainerPoint = (RecyclerView) findViewById(R.id.rc_point);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        this.mContainerPoint.setHasFixedSize(true);
        this.mContainerPoint.setLayoutManager(linearLayoutManager);
        this.mContainerPoint.setAdapter(this.mAdapter);
        new ItemTouchHelper(new 1(0, 12)).attachToRecyclerView(this.mContainerPoint);
        this.btnAdd = (Button) findViewById(R.id.btn_add);
        this.btnAdd.setOnClickListener(new 2());
        this.btnSolve = (Button) findViewById(R.id.btn_solve);
        this.btnSolve.setOnClickListener(new 3());
        this.mProgress = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        this.ckbTex = (SwitchCompat) findViewById(R.id.ckbTex);
        this.mathView = (MathView) findViewById(R.id.math_view);
        this.txtResult = (TextView) findViewById(R.id.txt_result);
    }

    private void doSolve() {
        double[] x = this.mAdapter.getListPointX();
        double[] y = this.mAdapter.getListPointY();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InterpolatingPolynomial({");
        for (int i = 0; i < this.mAdapter.getItemCount(); i++) {
            stringBuilder.append(VectorFormat.DEFAULT_PREFIX);
            stringBuilder.append(x[i]);
            stringBuilder.append(",");
            stringBuilder.append(y[i]);
            stringBuilder.append(VectorFormat.DEFAULT_SUFFIX);
            if (i != this.mAdapter.getItemCount() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("},x)");
        Log.d(this.TAG, stringBuilder.toString());
        solve(stringBuilder.toString(), x, y);
    }

    public void solve(String dataPoint, double[] x, double[] y) {
        new Thread(new 4(dataPoint, x, y)).start();
    }

    public void onHelpFunction(String s) {
    }
}
