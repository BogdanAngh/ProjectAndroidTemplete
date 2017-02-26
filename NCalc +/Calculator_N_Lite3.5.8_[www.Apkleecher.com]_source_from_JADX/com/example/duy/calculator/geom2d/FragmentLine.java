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
import com.example.duy.calculator.geom2d.line.Line2D;
import com.example.duy.calculator.geom2d.line.StraightLine2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.ArrayList;

public class FragmentLine extends Fragment implements Geometric, TextWatcher {
    private EditText editXa;
    private EditText editXb;
    private EditText editXc;
    private EditText editXd;
    private EditText editXm;
    private EditText editYa;
    private EditText editYb;
    private EditText editYc;
    private EditText editYd;
    private EditText editYm;
    private boolean isLine;
    private boolean isLine2;
    private boolean isPoint;
    private StraightLine2D mLine;
    private StraightLine2D mLine2;
    private Point2D mPoint;
    private TextView tatGeneralEquation;
    private TextView txtAngle;
    private TextView txtAngleOx;
    private TextView txtDistancePoint;
    private TextView txtEquationParam;
    private TextView txtInfo;
    private TextView txtInfo2;
    private TextView txtLenght;
    private TextView txtMidPoint;
    private TextView txtPositon;
    private TextView txtVectorPhoenix;
    private TextView txtVectorTangent;

    public FragmentLine() {
        this.isLine = false;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_geom_line, container, false);
        this.editXa = (EditText) view.findViewById(R.id.editXa);
        this.editXa.addTextChangedListener(this);
        this.editYa = (EditText) view.findViewById(R.id.editYa);
        this.editYa.addTextChangedListener(this);
        this.editXb = (EditText) view.findViewById(R.id.editXb);
        this.editXb.addTextChangedListener(this);
        this.editYb = (EditText) view.findViewById(R.id.editYb);
        this.editYb.addTextChangedListener(this);
        this.editXc = (EditText) view.findViewById(R.id.editXc);
        this.editXc.addTextChangedListener(this);
        this.editYc = (EditText) view.findViewById(R.id.editYc);
        this.editYc.addTextChangedListener(this);
        this.editXd = (EditText) view.findViewById(R.id.editXd);
        this.editXd.addTextChangedListener(this);
        this.editYd = (EditText) view.findViewById(R.id.editYd);
        this.editYd.addTextChangedListener(this);
        this.editXm = (EditText) view.findViewById(R.id.editXm);
        this.editXm.addTextChangedListener(this);
        this.editYm = (EditText) view.findViewById(R.id.editYm);
        this.editYm.addTextChangedListener(this);
        this.txtLenght = (TextView) view.findViewById(R.id.txtLength);
        this.txtMidPoint = (TextView) view.findViewById(R.id.txtMid);
        this.txtVectorPhoenix = (TextView) view.findViewById(R.id.txtVectorLine);
        this.txtVectorTangent = (TextView) view.findViewById(R.id.txtVectorTangent);
        this.tatGeneralEquation = (TextView) view.findViewById(R.id.txtGenaralEquation);
        this.txtAngleOx = (TextView) view.findViewById(R.id.txtAngleOx);
        this.txtDistancePoint = (TextView) view.findViewById(R.id.txtDistancePoint);
        this.txtEquationParam = (TextView) view.findViewById(R.id.txtParameter);
        this.txtInfo2 = (TextView) view.findViewById(R.id.txtInfor2);
        this.txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        this.txtAngle = (TextView) view.findViewById(R.id.txtAngle);
        this.txtPositon = (TextView) view.findViewById(R.id.txtPositon);
        return view;
    }

    public void onPrepare() {
        try {
            if (this.editXa.getText().toString().isEmpty() || this.editYa.getText().toString().isEmpty() || this.editXb.getText().toString().isEmpty() || this.editYb.getText().toString().isEmpty()) {
                this.isLine = false;
            } else {
                try {
                    this.mLine = new StraightLine2D(Double.parseDouble(this.editXa.getText().toString()), Double.parseDouble(this.editYa.getText().toString()), Double.parseDouble(this.editXb.getText().toString()), Double.parseDouble(this.editYb.getText().toString()));
                    this.isLine = true;
                    this.txtInfo.setText(null);
                } catch (RuntimeException e) {
                    this.txtInfo.setText(getResources().getString(R.string.not_line));
                }
            }
            if (this.editXc.getText().toString().isEmpty() || this.editYc.getText().toString().isEmpty() || this.editXd.getText().toString().isEmpty() || this.editYd.getText().toString().isEmpty()) {
                this.isLine2 = false;
            } else {
                try {
                    this.mLine2 = new StraightLine2D(Double.parseDouble(this.editXc.getText().toString()), Double.parseDouble(this.editYc.getText().toString()), Double.parseDouble(this.editXd.getText().toString()), Double.parseDouble(this.editYd.getText().toString()));
                    this.isLine2 = true;
                    this.txtInfo2.setText(null);
                } catch (RuntimeException e2) {
                    this.txtInfo2.setText(getResources().getString(R.string.not_line));
                }
            }
            if (this.editXm.getText().toString().isEmpty() || this.editYm.getText().toString().isEmpty()) {
                this.isPoint = false;
                return;
            }
            this.mPoint = new Point2D(Double.parseDouble(this.editXm.getText().toString()), Double.parseDouble(this.editYm.getText().toString()));
            this.isPoint = true;
        } catch (NumberFormatException e3) {
            e3.printStackTrace();
        }
    }

    public void onResult() {
        if (this.isLine) {
            Line2D line2D = new Line2D(this.mLine);
            double length = new Line2D(this.mLine).length();
            this.txtLenght.setText(String.valueOf(length));
            this.txtAngleOx.setText(String.valueOf(Angle2D.horizontalAngle(this.mLine)));
            Point2D midP = line2D.getMidPoint();
            this.txtMidPoint.setText(midP.toString());
            Vector2D vectorPhoenix = new Vector2D(line2D.getPoint1(), line2D.getPoint2());
            this.txtVectorPhoenix.setText(vectorPhoenix.toString());
            String equationParam = line2D.getEquationParameter();
            this.txtEquationParam.setText(equationParam);
            String generalEqual = line2D.getGeneralEquation();
            this.tatGeneralEquation.setText(generalEqual);
            Vector2D vecTangent = vectorPhoenix.getOrthogonal();
            this.txtVectorTangent.setText(vecTangent.toString());
            if (this.isPoint) {
                double distance = this.mLine.distance(this.mPoint);
                this.txtDistancePoint.setText(String.valueOf(distance));
            } else {
                this.txtDistancePoint.setText(null);
            }
            if (this.isLine2) {
                if (this.mLine.isColinear(this.mLine2)) {
                    this.txtPositon.setText(getResources().getString(R.string.collinear));
                } else {
                    if (this.mLine.isParallel(this.mLine2)) {
                        this.txtPositon.setText(getResources().getString(R.string.parallel));
                    } else {
                        ArrayList<Point2D> list = new ArrayList(this.mLine.intersections(this.mLine2));
                        this.txtPositon.setText(getResources().getString(R.string.interaction) + "\n" + ((Point2D) list.get(0)).toString());
                    }
                }
                this.txtAngle.setText(String.valueOf(Angle2D.angle(this.mLine, this.mLine2)));
                return;
            }
            this.txtAngle.setText(null);
            this.txtPositon.setText(null);
        }
    }

    public void onError() {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onPrepare();
        onResult();
    }

    public void afterTextChanged(Editable editable) {
    }
}
