package com.example.duy.calculator.graph;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.example.duy.calculator.AbstractAppCompatActivity;
import com.example.duy.calculator.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;
import edu.jas.vector.GenVectorModul;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class GraphActivity extends AbstractAppCompatActivity implements OnSharedPreferenceChangeListener {
    public static final String DATA;
    public static final String FUNC;
    private static final String GRAPH_STATED = "GRAPH_STATED";
    private static final int REQUEST_CODE = 1212;
    private boolean deriv;
    public Graph3DView graph3D;
    public GraphView graphView;
    private SwitchCompat imgDervi;
    private SwitchCompat imgTrace;
    private LinearLayout mContainer;
    private SwitchCompat mModeGraph;
    private int mode;
    private LayoutParams params;
    private SharedPreferences preferences;
    private boolean trace;

    class 1 implements OnCheckedChangeListener {
        1() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            GraphActivity.this.trace = b;
            GraphActivity.this.deriv = false;
            boolean result = GraphActivity.this.graphView.setTrace(GraphActivity.this.trace);
            GraphActivity.this.graphView.setDeriv(false);
            if (!result) {
                GraphActivity.this.trace = false;
                Toast.makeText(GraphActivity.this, GraphActivity.this.getString(R.string.noFunDisp), 1).show();
            } else if (GraphActivity.this.trace) {
                Toast.makeText(GraphActivity.this, GraphActivity.this.getString(R.string.tapFun), 1).show();
            }
        }
    }

    class 2 implements OnCheckedChangeListener {
        2() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            GraphActivity.this.trace = false;
            GraphActivity.this.deriv = b;
            boolean result = GraphActivity.this.graphView.setDeriv(GraphActivity.this.deriv);
            GraphActivity.this.graphView.setTrace(false);
            if (!result) {
                GraphActivity.this.deriv = false;
                Toast.makeText(GraphActivity.this, GraphActivity.this.getString(R.string.noFunDisp), 1).show();
            } else if (GraphActivity.this.deriv) {
                Toast.makeText(GraphActivity.this, GraphActivity.this.getString(R.string.tapFun), 1).show();
            }
        }
    }

    class 3 implements OnCheckedChangeListener {
        3() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            GraphActivity.this.invalidate(b);
        }
    }

    class 4 implements OnClickListener {
        4() {
        }

        public void onClick(View view) {
            GraphActivity.this.startActivityForResult(new Intent(GraphActivity.this, GraphAddFunction.class), GraphActivity.REQUEST_CODE);
        }
    }

    class 5 implements OnClickListener {
        5() {
        }

        public void onClick(View view) {
            Log.d(GraphActivity.this.TAG, "onClick: " + GraphActivity.this.mode);
            if (GraphActivity.this.mode != 3) {
                GraphActivity.this.graphView.zoom(-0.5f);
            } else {
                GraphActivity.this.graph3D.zoom(-0.5f);
            }
        }
    }

    class 6 implements OnClickListener {
        6() {
        }

        public void onClick(View view) {
            Log.d(GraphActivity.this.TAG, "onClick: " + GraphActivity.this.mode);
            if (GraphActivity.this.mode != 3) {
                GraphActivity.this.graphView.zoom(GenVectorModul.DEFAULT_DENSITY);
            } else {
                GraphActivity.this.graph3D.zoom(GenVectorModul.DEFAULT_DENSITY);
            }
        }
    }

    class 7 implements OnClickListener {
        7() {
        }

        public void onClick(View view) {
            try {
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    Date d = new Date();
                    GraphActivity.this.graphView.getBitmap().compress(CompressFormat.PNG, 100, new FileOutputStream(new File(root, "com/duy/example/calculator/graph/" + d.getMonth() + d.getDay() + d.getHours() + d.getMinutes() + d.getSeconds() + ".png")));
                    return;
                }
                Toast.makeText(GraphActivity.this, GraphActivity.this.getString(R.string.cannotwrite), 1).show();
            } catch (Exception e) {
                Toast.makeText(GraphActivity.this, e.getMessage(), 1).show();
            }
        }
    }

    class 8 implements OnClickListener {
        8() {
        }

        public void onClick(View view) {
            GraphActivity.this.showHelp();
        }
    }

    class 9 implements Listener {
        9() {
        }

        public void onSequenceFinish() {
            GraphActivity.this.preferences.edit().putBoolean(GraphActivity.GRAPH_STATED, true).apply();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
        }
    }

    public GraphActivity() {
        this.trace = false;
        this.deriv = false;
    }

    static {
        DATA = GraphActivity.class.getName();
        FUNC = GraphActivity.class.getName();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.graph_activity);
        ButterKnife.bind((Activity) this);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (this.preferences.getBoolean(GRAPH_STATED, false)) {
            Editor editor = this.preferences.edit();
            editor.putString("f1", "x^2");
            editor.apply();
            editor.putBoolean(GRAPH_STATED, true);
        }
        receiveData();
        this.preferences.registerOnSharedPreferenceChangeListener(this);
        this.imgTrace = (SwitchCompat) findViewById(R.id.img_trace);
        this.imgTrace.setOnCheckedChangeListener(new 1());
        this.imgDervi = (SwitchCompat) findViewById(R.id.btn_der);
        this.imgDervi.setOnCheckedChangeListener(new 2());
        this.mContainer = (LinearLayout) findViewById(R.id.container_graph);
        this.params = new LayoutParams(-1, -1);
        this.mModeGraph = (SwitchCompat) findViewById(R.id.sw_mode);
        this.mModeGraph.setChecked(this.preferences.getBoolean("is2d", false));
        this.mModeGraph.setOnCheckedChangeListener(new 3());
        findViewById(R.id.img_add_fun).setOnClickListener(new 4());
        findViewById(R.id.img_zoom_in).setOnClickListener(new 5());
        findViewById(R.id.img_zoom_out).setOnClickListener(new 6());
        findViewById(R.id.btn_save).setOnClickListener(new 7());
        invalidate(this.mModeGraph.isChecked());
        findViewById(R.id.btn_help).setOnClickListener(new 8());
        if (!this.preferences.getBoolean(GRAPH_STATED, false)) {
            showHelp();
        }
    }

    private void showHelp() {
        TapTarget target1 = TapTarget.forView(findViewById(R.id.img_add_fun), getString(R.string.add_function), getString(R.string.input_graph_here)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget target2 = TapTarget.forView(findViewById(R.id.sw_mode), "2D/3D", getString(R.string.choose_mode)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target1, target2);
        sequence.listener(new 9());
        sequence.start();
    }

    private void invalidate(boolean is2d) {
        if (is2d) {
            this.mContainer.removeAllViews();
            this.graphView = new GraphView(0, this);
            this.graphView.setLayoutParams(this.params);
            this.mContainer.addView(this.graphView);
            this.imgTrace.setVisibility(0);
            this.imgDervi.setVisibility(0);
            this.mode = 0;
            return;
        }
        this.graph3D = new Graph3DView(this);
        this.graph3D.setLayoutParams(this.params);
        this.mContainer.removeAllViews();
        this.mContainer.addView(this.graph3D);
        this.imgTrace.setVisibility(8);
        this.imgDervi.setVisibility(8);
        this.mode = 3;
    }

    private void invalidate() {
        invalidate(true);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (this.mModeGraph != null) {
            if (this.mModeGraph.isChecked()) {
                this.mode = 0;
            } else {
                this.mode = 3;
            }
        }
    }

    private void receiveData() {
        Bundle bundle = getIntent().getBundleExtra(DATA);
        if (bundle != null) {
            String fx = bundle.getString(FUNC);
            if (!fx.isEmpty()) {
                this.preferences.edit().putString("f1", fx).apply();
            }
            Log.d(this.TAG, "receiveData: " + fx);
            Log.d(this.TAG, "onResume: ok");
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            invalidate(this.mode == 0);
        }
    }
}
