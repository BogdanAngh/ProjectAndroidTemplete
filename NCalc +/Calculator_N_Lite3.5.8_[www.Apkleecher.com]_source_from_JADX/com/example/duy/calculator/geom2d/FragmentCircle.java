package com.example.duy.calculator.geom2d;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.duy.calculator.R;
import com.example.duy.calculator.geom2d.conic.Circle2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;

public class FragmentCircle extends Fragment implements Geometric, TextWatcher {
    private String TAG;
    private EditText editR1;
    private EditText editR2;
    private EditText editX1;
    private EditText editX2;
    private EditText editXa;
    private EditText editXb;
    private EditText editXc;
    private EditText editXm;
    private EditText editY1;
    private EditText editY2;
    private EditText editYa;
    private EditText editYb;
    private EditText editYc;
    private EditText editYm;
    private boolean isCircle;
    private boolean isCircle2;
    private boolean isLine;
    private boolean isPoint;
    private boolean isPointTangent;
    private Circle2D mCircle;
    private Circle2D mCircle2;
    private LineSegment2D mLine;
    private Point2D mPointC;
    private Point2D mPointM;
    private TextView txtArea;
    private TextView txtEquationCircle;
    private TextView txtEquationTangent;
    private TextView txtInteractionCircle;
    private TextView txtInteractionLine;
    private TextView txtInteractionPoint;
    private TextView txtLength;

    public FragmentCircle() {
        this.isCircle = false;
        this.isLine = false;
        this.isPoint = false;
        this.isCircle2 = false;
        this.TAG = FragmentCircle.class.getName();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_geom_circle, container, false);
        this.editX1 = (EditText) view.findViewById(R.id.editX1);
        this.editX1.addTextChangedListener(this);
        this.editX2 = (EditText) view.findViewById(R.id.editX2);
        this.editX2.addTextChangedListener(this);
        this.editY1 = (EditText) view.findViewById(R.id.editY1);
        this.editY1.addTextChangedListener(this);
        this.editY2 = (EditText) view.findViewById(R.id.editY2);
        this.editY2.addTextChangedListener(this);
        this.editYa = (EditText) view.findViewById(R.id.editYa);
        this.editYa.addTextChangedListener(this);
        this.editYb = (EditText) view.findViewById(R.id.editYb);
        this.editYb.addTextChangedListener(this);
        this.editXa = (EditText) view.findViewById(R.id.editXa);
        this.editXa.addTextChangedListener(this);
        this.editXb = (EditText) view.findViewById(R.id.editXb);
        this.editXb.addTextChangedListener(this);
        this.editXc = (EditText) view.findViewById(R.id.editXc);
        this.editXc.addTextChangedListener(this);
        this.editYc = (EditText) view.findViewById(R.id.editYc);
        this.editYc.addTextChangedListener(this);
        this.editR1 = (EditText) view.findViewById(R.id.editR1);
        this.editR1.addTextChangedListener(this);
        this.editR2 = (EditText) view.findViewById(R.id.editR2);
        this.editR2.addTextChangedListener(this);
        this.txtInteractionCircle = (TextView) view.findViewById(R.id.txtInteractionCircle);
        this.txtInteractionLine = (TextView) view.findViewById(R.id.txtInteractionLine);
        this.txtInteractionPoint = (TextView) view.findViewById(R.id.txtInteractionPoint);
        this.txtArea = (TextView) view.findViewById(R.id.txtArea);
        this.txtLength = (TextView) view.findViewById(R.id.txtLength);
        this.txtEquationTangent = (TextView) view.findViewById(R.id.txtEquationTangent);
        this.txtEquationCircle = (TextView) view.findViewById(R.id.txtEquaCircle);
        this.editXm = (EditText) view.findViewById(R.id.editXm);
        this.editXm.addTextChangedListener(this);
        this.editYm = (EditText) view.findViewById(R.id.editYm);
        this.editYm.addTextChangedListener(this);
        return view;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onPrepare();
        onResult();
    }

    public void afterTextChanged(Editable editable) {
    }

    public void onPrepare() {
        try {
            if (this.editX1.getText().toString().isEmpty() || this.editY1.getText().toString().isEmpty() || this.editR1.getText().toString().isEmpty()) {
                this.isCircle = false;
            } else {
                this.mCircle = new Circle2D(Double.parseDouble(this.editX1.getText().toString()), Double.parseDouble(this.editY1.getText().toString()), Double.parseDouble(this.editR1.getText().toString()));
                this.isCircle = true;
            }
            if (this.editX2.getText().toString().isEmpty() || this.editY2.getText().toString().isEmpty() || this.editR2.getText().toString().isEmpty()) {
                this.isCircle2 = false;
            } else {
                this.mCircle2 = new Circle2D(Double.parseDouble(this.editX2.getText().toString()), Double.parseDouble(this.editY2.getText().toString()), Double.parseDouble(this.editR2.getText().toString()));
                this.isCircle2 = true;
            }
            if (this.editXa.getText().toString().isEmpty() || this.editYa.getText().toString().isEmpty() || this.editXb.getText().toString().isEmpty() || this.editYb.getText().toString().isEmpty()) {
                this.isLine = false;
            } else {
                this.mLine = new LineSegment2D(Double.parseDouble(this.editXa.getText().toString()), Double.parseDouble(this.editYa.getText().toString()), Double.parseDouble(this.editXb.getText().toString()), Double.parseDouble(this.editYb.getText().toString()));
                this.isLine = true;
            }
            if (this.editXc.getText().toString().isEmpty() || this.editYc.getText().toString().isEmpty()) {
                this.isPoint = false;
            } else {
                this.mPointC = new Point2D(Double.parseDouble(this.editXc.getText().toString()), Double.parseDouble(this.editYc.getText().toString()));
                this.isPoint = true;
            }
            if (this.editXm.getText().toString().isEmpty() && this.editYm.getText().toString().isEmpty()) {
                this.isPointTangent = false;
                return;
            }
            this.mPointM = new Point2D(Double.parseDouble(this.editXm.getText().toString()), Double.parseDouble(this.editYm.getText().toString()));
            this.isPointTangent = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onResult() {
        /*
        r15 = this;
        r11 = r15.TAG;
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = r15.isCircle;
        r13 = java.lang.String.valueOf(r13);
        r12 = r12.append(r13);
        r13 = r15.isLine;
        r13 = java.lang.String.valueOf(r13);
        r12 = r12.append(r13);
        r13 = r15.isPoint;
        r13 = java.lang.String.valueOf(r13);
        r12 = r12.append(r13);
        r12 = r12.toString();
        android.util.Log.d(r11, r12);
        r11 = r15.isCircle;
        if (r11 == 0) goto L_0x0254;
    L_0x0030:
        r11 = r15.isCircle2;	 Catch:{ RuntimeException -> 0x010b }
        if (r11 == 0) goto L_0x0161;
    L_0x0034:
        r11 = r15.mCircle;	 Catch:{ RuntimeException -> 0x010b }
        r12 = r15.mCircle2;	 Catch:{ RuntimeException -> 0x010b }
        r9 = r11.intersections(r12);	 Catch:{ RuntimeException -> 0x010b }
        r8 = new java.util.ArrayList;	 Catch:{ RuntimeException -> 0x010b }
        r8.<init>(r9);	 Catch:{ RuntimeException -> 0x010b }
        r11 = r8.size();	 Catch:{ RuntimeException -> 0x010b }
        if (r11 != 0) goto L_0x00d0;
    L_0x0047:
        r11 = r15.txtInteractionCircle;	 Catch:{ RuntimeException -> 0x010b }
        r12 = r15.getResources();	 Catch:{ RuntimeException -> 0x010b }
        r13 = 2131230989; // 0x7f08010d float:1.8078046E38 double:1.052968015E-314;
        r12 = r12.getString(r13);	 Catch:{ RuntimeException -> 0x010b }
        r11.setText(r12);	 Catch:{ RuntimeException -> 0x010b }
    L_0x0057:
        r11 = r15.isLine;	 Catch:{ RuntimeException -> 0x017b }
        if (r11 == 0) goto L_0x020b;
    L_0x005b:
        r11 = r15.mCircle;	 Catch:{ RuntimeException -> 0x017b }
        r12 = r15.mLine;	 Catch:{ RuntimeException -> 0x017b }
        r9 = r11.intersections(r12);	 Catch:{ RuntimeException -> 0x017b }
        r8 = new java.util.ArrayList;	 Catch:{ RuntimeException -> 0x017b }
        r8.<init>(r9);	 Catch:{ RuntimeException -> 0x017b }
        r11 = r8.size();	 Catch:{ RuntimeException -> 0x017b }
        switch(r11) {
            case 0: goto L_0x0169;
            case 1: goto L_0x018e;
            case 2: goto L_0x01c2;
            default: goto L_0x006f;
        };
    L_0x006f:
        r11 = r15.isPoint;
        if (r11 == 0) goto L_0x0239;
    L_0x0073:
        r11 = r15.mCircle;
        r12 = r15.mPointC;
        r4 = r11.contains(r12);
        r11 = r15.mCircle;
        r12 = r15.mPointC;
        r5 = r11.isInside(r12);
        if (r4 == 0) goto L_0x0213;
    L_0x0085:
        r11 = r15.txtInteractionPoint;
        r12 = r15.getResources();
        r13 = 2131230743; // 0x7f080017 float:1.8077547E38 double:1.0529678935E-314;
        r12 = r12.getString(r13);
        r11.setText(r12);
    L_0x0095:
        r11 = r15.isPointTangent;
        if (r11 == 0) goto L_0x00a6;
    L_0x0099:
        r11 = r15.mCircle;	 Catch:{ RuntimeException -> 0x0241 }
        r12 = r15.mPointM;	 Catch:{ RuntimeException -> 0x0241 }
        r10 = r11.getEquationTangent(r12);	 Catch:{ RuntimeException -> 0x0241 }
        r11 = r15.txtEquationTangent;	 Catch:{ RuntimeException -> 0x0241 }
        r11.setText(r10);	 Catch:{ RuntimeException -> 0x0241 }
    L_0x00a6:
        r11 = r15.mCircle;
        r6 = r11.length();
        r11 = r15.txtLength;
        r12 = java.lang.String.valueOf(r6);
        r11.setText(r12);
        r11 = r15.mCircle;
        r0 = r11.getArea();
        r11 = r15.txtArea;
        r12 = java.lang.String.valueOf(r0);
        r11.setText(r12);
        r11 = r15.mCircle;
        r3 = r11.getEquationCircle();
        r11 = r15.txtEquationCircle;
        r11.setText(r3);
    L_0x00cf:
        return;
    L_0x00d0:
        r11 = r8.size();	 Catch:{ RuntimeException -> 0x010b }
        r12 = 1;
        if (r11 != r12) goto L_0x0111;
    L_0x00d7:
        r12 = r15.txtInteractionCircle;	 Catch:{ RuntimeException -> 0x010b }
        r11 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x010b }
        r11.<init>();	 Catch:{ RuntimeException -> 0x010b }
        r13 = r15.getResources();	 Catch:{ RuntimeException -> 0x010b }
        r14 = 2131230776; // 0x7f080038 float:1.8077614E38 double:1.05296791E-314;
        r13 = r13.getString(r14);	 Catch:{ RuntimeException -> 0x010b }
        r11 = r11.append(r13);	 Catch:{ RuntimeException -> 0x010b }
        r13 = "\n";
        r13 = r11.append(r13);	 Catch:{ RuntimeException -> 0x010b }
        r11 = 0;
        r11 = r8.get(r11);	 Catch:{ RuntimeException -> 0x010b }
        r11 = (com.example.duy.calculator.geom2d.Point2D) r11;	 Catch:{ RuntimeException -> 0x010b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x010b }
        r11 = r13.append(r11);	 Catch:{ RuntimeException -> 0x010b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x010b }
        r12.setText(r11);	 Catch:{ RuntimeException -> 0x010b }
        goto L_0x0057;
    L_0x010b:
        r2 = move-exception;
        r2.getStackTrace();
        goto L_0x0057;
    L_0x0111:
        r11 = r9.size();	 Catch:{ RuntimeException -> 0x010b }
        r12 = 2;
        if (r11 != r12) goto L_0x0057;
    L_0x0118:
        r12 = r15.txtInteractionCircle;	 Catch:{ RuntimeException -> 0x010b }
        r11 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x010b }
        r11.<init>();	 Catch:{ RuntimeException -> 0x010b }
        r13 = r15.getResources();	 Catch:{ RuntimeException -> 0x010b }
        r14 = 2131230775; // 0x7f080037 float:1.8077612E38 double:1.0529679093E-314;
        r13 = r13.getString(r14);	 Catch:{ RuntimeException -> 0x010b }
        r11 = r11.append(r13);	 Catch:{ RuntimeException -> 0x010b }
        r13 = "\n";
        r13 = r11.append(r13);	 Catch:{ RuntimeException -> 0x010b }
        r11 = 0;
        r11 = r8.get(r11);	 Catch:{ RuntimeException -> 0x010b }
        r11 = (com.example.duy.calculator.geom2d.Point2D) r11;	 Catch:{ RuntimeException -> 0x010b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x010b }
        r11 = r13.append(r11);	 Catch:{ RuntimeException -> 0x010b }
        r13 = "\n";
        r13 = r11.append(r13);	 Catch:{ RuntimeException -> 0x010b }
        r11 = 1;
        r11 = r8.get(r11);	 Catch:{ RuntimeException -> 0x010b }
        r11 = (com.example.duy.calculator.geom2d.Point2D) r11;	 Catch:{ RuntimeException -> 0x010b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x010b }
        r11 = r13.append(r11);	 Catch:{ RuntimeException -> 0x010b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x010b }
        r12.setText(r11);	 Catch:{ RuntimeException -> 0x010b }
        goto L_0x0057;
    L_0x0161:
        r11 = r15.txtInteractionCircle;	 Catch:{ RuntimeException -> 0x010b }
        r12 = 0;
        r11.setText(r12);	 Catch:{ RuntimeException -> 0x010b }
        goto L_0x0057;
    L_0x0169:
        r11 = r15.txtInteractionLine;	 Catch:{ RuntimeException -> 0x017b }
        r12 = r15.getResources();	 Catch:{ RuntimeException -> 0x017b }
        r13 = 2131230989; // 0x7f08010d float:1.8078046E38 double:1.052968015E-314;
        r12 = r12.getString(r13);	 Catch:{ RuntimeException -> 0x017b }
        r11.setText(r12);	 Catch:{ RuntimeException -> 0x017b }
        goto L_0x006f;
    L_0x017b:
        r2 = move-exception;
        r11 = r15.txtInteractionLine;
        r12 = r15.getResources();
        r13 = 2131230990; // 0x7f08010e float:1.8078048E38 double:1.0529680155E-314;
        r12 = r12.getString(r13);
        r11.setText(r12);
        goto L_0x006f;
    L_0x018e:
        r12 = r15.txtInteractionLine;	 Catch:{ RuntimeException -> 0x017b }
        r11 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017b }
        r11.<init>();	 Catch:{ RuntimeException -> 0x017b }
        r13 = r15.getResources();	 Catch:{ RuntimeException -> 0x017b }
        r14 = 2131231061; // 0x7f080155 float:1.8078192E38 double:1.0529680506E-314;
        r13 = r13.getString(r14);	 Catch:{ RuntimeException -> 0x017b }
        r11 = r11.append(r13);	 Catch:{ RuntimeException -> 0x017b }
        r13 = "\n";
        r13 = r11.append(r13);	 Catch:{ RuntimeException -> 0x017b }
        r11 = 0;
        r11 = r8.get(r11);	 Catch:{ RuntimeException -> 0x017b }
        r11 = (com.example.duy.calculator.geom2d.Point2D) r11;	 Catch:{ RuntimeException -> 0x017b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x017b }
        r11 = r13.append(r11);	 Catch:{ RuntimeException -> 0x017b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x017b }
        r12.setText(r11);	 Catch:{ RuntimeException -> 0x017b }
        goto L_0x006f;
    L_0x01c2:
        r12 = r15.txtInteractionLine;	 Catch:{ RuntimeException -> 0x017b }
        r11 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017b }
        r11.<init>();	 Catch:{ RuntimeException -> 0x017b }
        r13 = r15.getResources();	 Catch:{ RuntimeException -> 0x017b }
        r14 = 2131230931; // 0x7f0800d3 float:1.8077929E38 double:1.0529679864E-314;
        r13 = r13.getString(r14);	 Catch:{ RuntimeException -> 0x017b }
        r11 = r11.append(r13);	 Catch:{ RuntimeException -> 0x017b }
        r13 = "\n";
        r13 = r11.append(r13);	 Catch:{ RuntimeException -> 0x017b }
        r11 = 0;
        r11 = r8.get(r11);	 Catch:{ RuntimeException -> 0x017b }
        r11 = (com.example.duy.calculator.geom2d.Point2D) r11;	 Catch:{ RuntimeException -> 0x017b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x017b }
        r11 = r13.append(r11);	 Catch:{ RuntimeException -> 0x017b }
        r13 = "\n";
        r13 = r11.append(r13);	 Catch:{ RuntimeException -> 0x017b }
        r11 = 1;
        r11 = r8.get(r11);	 Catch:{ RuntimeException -> 0x017b }
        r11 = (com.example.duy.calculator.geom2d.Point2D) r11;	 Catch:{ RuntimeException -> 0x017b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x017b }
        r11 = r13.append(r11);	 Catch:{ RuntimeException -> 0x017b }
        r11 = r11.toString();	 Catch:{ RuntimeException -> 0x017b }
        r12.setText(r11);	 Catch:{ RuntimeException -> 0x017b }
        goto L_0x006f;
    L_0x020b:
        r11 = r15.txtInteractionLine;	 Catch:{ RuntimeException -> 0x017b }
        r12 = 0;
        r11.setText(r12);	 Catch:{ RuntimeException -> 0x017b }
        goto L_0x006f;
    L_0x0213:
        if (r5 == 0) goto L_0x0227;
    L_0x0215:
        r11 = r15.txtInteractionPoint;
        r12 = r15.getResources();
        r13 = 2131230929; // 0x7f0800d1 float:1.8077925E38 double:1.0529679854E-314;
        r12 = r12.getString(r13);
        r11.setText(r12);
        goto L_0x0095;
    L_0x0227:
        r11 = r15.txtInteractionPoint;
        r12 = r15.getResources();
        r13 = 2131230998; // 0x7f080116 float:1.8078065E38 double:1.0529680195E-314;
        r12 = r12.getString(r13);
        r11.setText(r12);
        goto L_0x0095;
    L_0x0239:
        r11 = r15.txtInteractionPoint;
        r12 = 0;
        r11.setText(r12);
        goto L_0x0095;
    L_0x0241:
        r2 = move-exception;
        r11 = r15.txtEquationTangent;
        r12 = r15.getResources();
        r13 = 2131230985; // 0x7f080109 float:1.8078038E38 double:1.052968013E-314;
        r12 = r12.getString(r13);
        r11.setText(r12);
        goto L_0x00a6;
    L_0x0254:
        r11 = r15.txtArea;
        r12 = 0;
        r11.setText(r12);
        r11 = r15.txtLength;
        r12 = 0;
        r11.setText(r12);
        r11 = r15.txtEquationCircle;
        r12 = 0;
        r11.setText(r12);
        goto L_0x00cf;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.duy.calculator.geom2d.FragmentCircle.onResult():void");
    }

    public void onError() {
    }
}
