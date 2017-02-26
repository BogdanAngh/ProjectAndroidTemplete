package com.example.duy.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.duy.calculator.data.Settings;
import com.example.duy.calculator.utils.ColorUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.R;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import org.apache.commons.math4.stat.descriptive.DescriptiveStatistics;

@Deprecated
public class StatisticActivity extends AbstractNavDrawer {
    private Button btnSubmit;
    private BarChart mBarChart;
    private EditText mInput;
    private LineChart mLineChart;
    private PieChart mPieChart;
    private SwitchCompat swAuto;

    class 1 implements TextWatcher {
        1() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (StatisticActivity.this.swAuto.isChecked()) {
                StatisticActivity.this.doSubmit();
            }
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    class 2 implements OnClickListener {
        2() {
        }

        public void onClick(View view) {
            StatisticActivity.this.doSubmit();
        }
    }

    class 3 implements Runnable {
        final /* synthetic */ String[] val$input;

        3(String[] strArr) {
            this.val$input = strArr;
        }

        public void run() {
            StatisticActivity.this.drawLineChart(this.val$input);
            StatisticActivity.this.drawBarChart(this.val$input);
            StatisticActivity.this.drawPieChart(this.val$input);
        }
    }

    protected void onPause() {
        super.onPause();
        Settings.put((Context) this, Settings.INPUT_STATISTIC, this.mInput.getText().toString());
    }

    protected void onPostResume() {
        super.onPostResume();
        this.mInput.setText(Settings.getString(this, Settings.INPUT_STATISTIC));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_statistic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.swAuto = (SwitchCompat) findViewById(R.id.sw_auto_process);
        this.mInput = (EditText) findViewById(R.id.edit_input);
        this.mInput.addTextChangedListener(new 1());
        this.btnSubmit = (Button) findViewById(R.id.btn_submit);
        this.btnSubmit.setOnClickListener(new 2());
        this.mPieChart = (PieChart) findViewById(R.id.pie_chart);
        this.mLineChart = (LineChart) findViewById(R.id.line_chart);
        this.mBarChart = (BarChart) findViewById(R.id.bar_chart);
    }

    private void doSubmit() {
        try {
            DescriptiveStatistics mStatistics = new DescriptiveStatistics();
            String[] input = this.mInput.getText().toString().split(",");
            for (String parseDouble : input) {
                mStatistics.addValue(Double.parseDouble(parseDouble));
            }
            ((TextView) findViewById(R.id.txt_geo_mean)).setText(String.valueOf(mStatistics.getGeometricMean()));
            ((TextView) findViewById(R.id.txtKurtosis)).setText(String.valueOf(mStatistics.getKurtosis()));
            ((TextView) findViewById(R.id.txt_maximum)).setText(String.valueOf(mStatistics.getMax()));
            ((TextView) findViewById(R.id.txt_minimum)).setText(String.valueOf(mStatistics.getMin()));
            ((TextView) findViewById(R.id.txt_arithmetic_mean)).setText(String.valueOf(mStatistics.getMean()));
            ((TextView) findViewById(R.id.txt_population_variance)).setText(String.valueOf(mStatistics.getPopulationVariance()));
            ((TextView) findViewById(R.id.txt_quadratic_mean)).setText(String.valueOf(mStatistics.getQuadraticMean()));
            ((TextView) findViewById(R.id.txt_skewness)).setText(String.valueOf(mStatistics.getSkewness()));
            ((TextView) findViewById(R.id.txt_sum)).setText(String.valueOf(mStatistics.getSum()));
            ((TextView) findViewById(R.id.txt_variance)).setText(String.valueOf(mStatistics.getVariance()));
            runOnUiThread(new 3(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawLineChart(String[] data) {
        this.mLineChart.setData(getLineData(data));
        this.mLineChart.setDescription(BuildConfig.FLAVOR);
        this.mLineChart.setDrawGridBackground(false);
        XAxis xAxis = this.mLineChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        YAxis leftAxis = this.mLineChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinValue(0.0f);
        YAxis rightAxis = this.mLineChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinValue(0.0f);
        this.mLineChart.animateX(750);
    }

    private void drawBarChart(String[] data) {
        this.mBarChart.setDescription(BuildConfig.FLAVOR);
        this.mBarChart.setDrawGridBackground(false);
        this.mBarChart.setDrawBarShadow(false);
        XAxis xAxis = this.mBarChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        YAxis leftAxis = this.mBarChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20.0f);
        leftAxis.setAxisMinValue(0.0f);
        YAxis rightAxis = this.mBarChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20.0f);
        rightAxis.setAxisMinValue(0.0f);
        this.mBarChart.setData(getBarData(data));
        this.mBarChart.setFitBars(true);
        this.mBarChart.animateY(700);
    }

    private LineData getLineData(String[] data) {
        ArrayList<Entry> mList = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            mList.add(new Entry((float) i, Float.parseFloat(data[i])));
        }
        LineDataSet lineDataSet = new LineDataSet(mList, BuildConfig.FLAVOR);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleRadius(4.5f);
        lineDataSet.setHighLightColor(ColorUtil.ORANGE);
        lineDataSet.setDrawValues(true);
        return new LineData(lineDataSet);
    }

    private BarData getBarData(String[] data) {
        ArrayList<BarEntry> mList = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            mList.add(new BarEntry((float) i, Float.parseFloat(data[i])));
        }
        BarDataSet d = new BarDataSet(mList, BuildConfig.FLAVOR);
        d.setColors(ColorUtil.COLOR);
        d.setHighLightAlpha(MotionEventCompat.ACTION_MASK);
        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private void drawPieChart(String[] data) {
        PieData mChartData = getPieData(data);
        this.mPieChart.setDescription(BuildConfig.FLAVOR);
        this.mPieChart.setHoleRadius(52.0f);
        this.mPieChart.setTransparentCircleRadius(57.0f);
        this.mPieChart.setCenterText("PieChart");
        this.mPieChart.setCenterTextSize(9.0f);
        this.mPieChart.setUsePercentValues(false);
        this.mPieChart.setExtraOffsets(5.0f, 10.0f, 50.0f, 10.0f);
        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTextSize(11.0f);
        mChartData.setValueTextColor(-1);
        this.mPieChart.setData(mChartData);
        Legend l = this.mPieChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_RIGHT);
        l.setYEntrySpace(0.0f);
        l.setYOffset(0.0f);
        this.mPieChart.animateY(900);
    }

    private PieData getPieData(String[] data) {
        ArrayList<PieEntry> mList = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            mList.add(new PieEntry((float) i, Float.valueOf(Float.parseFloat(data[i]))));
        }
        PieDataSet d = new PieDataSet(mList, BuildConfig.FLAVOR);
        d.setSliceSpace(2.0f);
        d.setColors(ColorUtil.COLOR);
        d.setDrawValues(true);
        return new PieData(d);
    }

    public void onHelpFunction(String s) {
    }
}
