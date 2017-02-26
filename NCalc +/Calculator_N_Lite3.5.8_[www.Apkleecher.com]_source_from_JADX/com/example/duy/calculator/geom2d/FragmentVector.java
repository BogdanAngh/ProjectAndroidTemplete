package com.example.duy.calculator.geom2d;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.example.duy.calculator.R;
import com.example.duy.calculator.geom2d.util.Angle2D;
import java.text.DecimalFormat;

public class FragmentVector extends Fragment implements Geometric, TextWatcher {
    private String TAG;
    private CheckBox ckbCollinear;
    private CheckBox ckbOrthogonal;
    private EditText editK;
    private boolean isNumK;
    private boolean isVectorA;
    private boolean isVectorB;
    private Vector2D mVectorA;
    private Vector2D mVectorB;
    private EditText mXa;
    private EditText mXb;
    private EditText mYa;
    private EditText mYb;
    private TextView txtAngle;
    private TextView txtAngleA;
    private TextView txtAngleB;
    private TextView txtMinus;
    private TextView txtPlus;
    private TextView txtScalar;
    private TextView txtTimeA;
    private TextView txtTimeB;
    private double xa;
    private double xb;
    private double ya;
    private double yb;

    public FragmentVector() {
        this.isVectorA = false;
        this.isVectorB = false;
        this.TAG = FragmentVector.class.getName();
        this.isNumK = false;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_geom_vecter, container, false);
        this.mXa = (EditText) view.findViewById(R.id.editVectoXa);
        this.mYa = (EditText) view.findViewById(R.id.editYa);
        this.mXb = (EditText) view.findViewById(R.id.editXb);
        this.mYb = (EditText) view.findViewById(R.id.editYb);
        this.mXa.addTextChangedListener(this);
        this.mXb.addTextChangedListener(this);
        this.mYa.addTextChangedListener(this);
        this.mYb.addTextChangedListener(this);
        this.ckbCollinear = (CheckBox) view.findViewById(R.id.ckbColinear);
        this.ckbOrthogonal = (CheckBox) view.findViewById(R.id.ckbOrthoganal);
        this.txtAngle = (TextView) view.findViewById(R.id.txtAngle);
        this.txtPlus = (TextView) view.findViewById(R.id.txtPlus);
        this.txtMinus = (TextView) view.findViewById(R.id.txtMinus);
        this.txtAngleA = (TextView) view.findViewById(R.id.txtAngleA);
        this.txtAngleB = (TextView) view.findViewById(R.id.txtAngelB);
        this.txtTimeA = (TextView) view.findViewById(R.id.txtMulA);
        this.txtTimeB = (TextView) view.findViewById(R.id.txtMulB);
        this.txtScalar = (TextView) view.findViewById(R.id.txtScalar);
        this.editK = (EditText) view.findViewById(R.id.editK);
        this.editK.addTextChangedListener(this);
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
            if (this.mXa.getText().toString().isEmpty() || this.mYa.getText().toString().isEmpty()) {
                this.isVectorA = false;
            } else {
                this.xa = Double.parseDouble(this.mXa.getText().toString());
                this.ya = Double.parseDouble(this.mYa.getText().toString());
                Log.d(this.TAG, String.valueOf(this.xa));
                Log.d(this.TAG, String.valueOf(this.ya));
                this.mVectorA = new Vector2D(this.xa, this.ya);
                this.isVectorA = true;
            }
            if (this.mXb.getText().toString().isEmpty() || this.mYb.getText().toString().isEmpty()) {
                this.isVectorB = false;
            } else {
                this.xb = Double.parseDouble(this.mXb.getText().toString());
                this.yb = Double.parseDouble(this.mYb.getText().toString());
                Log.d(this.TAG, String.valueOf(this.xb));
                Log.d(this.TAG, String.valueOf(this.yb));
                this.mVectorB = new Vector2D(this.xb, this.yb);
                this.isVectorB = true;
            }
            if (this.editK.getText().toString().isEmpty()) {
                this.isNumK = false;
            } else {
                this.isNumK = true;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void onResult() {
        DecimalFormat format = new DecimalFormat("#.###");
        if (this.isVectorA && this.isVectorB) {
            boolean isCollinear = this.mVectorA.isColinear(this.mVectorB);
            boolean isOrthoganol = this.mVectorA.isOrthogonal(this.mVectorB);
            this.ckbCollinear.setChecked(isCollinear);
            this.ckbOrthogonal.setChecked(isOrthoganol);
            double angle = Angle2D.angle(this.mVectorA, this.mVectorB);
            this.txtAngle.setText(format.format(angle) + " (RAD)\n " + format.format(Math.toDegrees(angle)) + " (DEG)");
            this.txtPlus.setText(this.mVectorA.plus(this.mVectorB).toString());
            this.txtMinus.setText(this.mVectorA.minus(this.mVectorB).toString());
            double angleA = Angle2D.horizontalAngle(this.mVectorA);
            double angleB = Angle2D.horizontalAngle(this.mVectorB);
            this.txtAngleA.setText(format.format(angleA) + " (RAD)\n" + format.format(Math.toDegrees(angleA)) + " (DEG)");
            this.txtAngleB.setText(format.format(angleB) + " (RAD)\n" + format.format(Math.toDegrees(angleB)) + " (DEG)");
            if (this.isNumK) {
                this.txtTimeA.setText(this.mVectorA.times(Double.parseDouble(this.editK.getText().toString())).toString());
                this.txtTimeB.setText(this.mVectorB.times(Double.parseDouble(this.editK.getText().toString())).toString());
            } else {
                this.txtTimeB.setText(null);
                this.txtTimeA.setText(null);
            }
            this.txtScalar.setText(String.valueOf(this.mVectorA.getScalar(this.mVectorB)));
        } else if (this.isVectorA || this.isVectorB) {
            this.txtAngleA.setText(null);
            this.txtAngleB.setText(null);
            this.txtAngle.setText(null);
            this.txtPlus.setText(null);
            this.txtMinus.setText(null);
            Log.d(this.TAG, String.valueOf(this.isVectorA));
            if (this.isVectorA) {
                this.txtAngleA.setText(String.valueOf(Angle2D.horizontalAngle(this.mVectorA)));
            } else if (this.isVectorB) {
                this.txtAngleB.setText(String.valueOf(Angle2D.horizontalAngle(this.mVectorB)));
            }
        }
    }

    public void onError() {
    }
}
