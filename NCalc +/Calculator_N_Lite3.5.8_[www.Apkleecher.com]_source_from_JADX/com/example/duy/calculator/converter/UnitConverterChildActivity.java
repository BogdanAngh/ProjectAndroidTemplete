package com.example.duy.calculator.converter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.duy.calculator.AbstractNavDrawer;
import com.example.duy.calculator.R;
import com.example.duy.calculator.converter.utils.AreaStrategy;
import com.example.duy.calculator.converter.utils.BitrateStrategy;
import com.example.duy.calculator.converter.utils.EnergyStrategy;
import com.example.duy.calculator.converter.utils.LengthStrategy;
import com.example.duy.calculator.converter.utils.PowerStrategy;
import com.example.duy.calculator.converter.utils.Strategy;
import com.example.duy.calculator.converter.utils.TemperatureStrategy;
import com.example.duy.calculator.converter.utils.TimeStratery;
import com.example.duy.calculator.converter.utils.VelocityStrategy;
import com.example.duy.calculator.converter.utils.VolumeStrategy;
import com.example.duy.calculator.converter.utils.WeightStrategy;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class UnitConverterChildActivity extends AbstractNavDrawer {
    String TAG;
    private String[] arrUnit;
    private EditText editText;
    RecyclerView mRecycleView;
    private UnitAdapter mUnitAdapter;
    private Spinner spinner;
    private Strategy strategy;

    class 1 implements OnItemSelectedListener {
        1() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            UnitConverterChildActivity.this.updateListView();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class 2 implements TextWatcher {
        2() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            UnitConverterChildActivity.this.updateListView();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public UnitConverterChildActivity() {
        this.TAG = UnitConverterChildActivity.class.getName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_unit_converter_child);
        Bundle bundle = getIntent().getBundleExtra("data");
        int pos = bundle.getInt("POS");
        Toolbar toolbar = (Toolbar) findViewById(com.google.android.gms.R.id.toolbar);
        toolbar.setTitle(bundle.getString("NAME"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        setUpSpinnerAndStratery(pos);
    }

    public void onHelpFunction(String s) {
    }

    private void setUpSpinnerAndStratery(int pos) {
        this.arrUnit = new String[0];
        switch (pos) {
            case ValueServer.DIGEST_MODE /*0*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.temp);
                setStrategy(new TemperatureStrategy(getApplicationContext()));
                break;
            case ValueServer.REPLAY_MODE /*1*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.weight);
                setStrategy(new WeightStrategy(getApplicationContext()));
                break;
            case IExpr.DOUBLEID /*2*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.length);
                setStrategy(new LengthStrategy(getApplicationContext()));
                break;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.power);
                setStrategy(new PowerStrategy(getApplicationContext()));
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.energy);
                setStrategy(new EnergyStrategy(getApplicationContext()));
                break;
            case ValueServer.CONSTANT_MODE /*5*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.velocity);
                setStrategy(new VelocityStrategy(getApplicationContext()));
                break;
            case io.github.kexanie.library.R.styleable.Toolbar_contentInsetEnd /*6*/:
                setStrategy(new AreaStrategy(getApplicationContext()));
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.area);
                break;
            case io.github.kexanie.library.R.styleable.Toolbar_contentInsetLeft /*7*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.volume);
                setStrategy(new VolumeStrategy(getApplicationContext()));
                break;
            case IExpr.INTEGERID /*8*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.bitrate);
                setStrategy(new BitrateStrategy(getApplicationContext()));
                break;
            case io.github.kexanie.library.R.styleable.TextAppearance_textAllCaps /*9*/:
                this.arrUnit = getApplicationContext().getResources().getStringArray(R.array.time);
                setStrategy(new TimeStratery(getApplicationContext()));
                break;
        }
        ArrayAdapter<String> adapterUnit = new ArrayAdapter(this, 17367043, this.arrUnit);
        adapterUnit.setDropDownViewResource(17367055);
        this.spinner.setAdapter(adapterUnit);
        this.spinner.setOnItemSelectedListener(new 1());
    }

    private void initView() {
        this.editText = (EditText) findViewById(R.id.editInput);
        this.editText.addTextChangedListener(new 2());
        this.spinner = (Spinner) findViewById(R.id.spinner_unit);
        this.mRecycleView = (RecyclerView) findViewById(R.id.listview);
        this.mRecycleView.setHasFixedSize(true);
        this.mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        this.mUnitAdapter = new UnitAdapter(this, new ArrayList());
        this.mRecycleView.setAdapter(this.mUnitAdapter);
    }

    private void updateListView() {
        int i = 0;
        String currentUnit = this.spinner.getSelectedItem().toString();
        String defaultUnit = this.strategy.getUnitDefault();
        Log.e("currentUnit", currentUnit);
        ArrayList<ItemUnitConverter> list = new ArrayList();
        double defaultValue;
        String[] strArr;
        int length;
        String anArrUnit;
        double res;
        ItemUnitConverter itemUnitConverter;
        if (this.editText.getText().toString().equals(BuildConfig.FLAVOR)) {
            defaultValue = this.strategy.Convert(currentUnit, defaultUnit, 0.0d);
            Log.e("INP", String.valueOf(0.0d));
            Log.e("DEF", String.valueOf(defaultValue));
            strArr = this.arrUnit;
            length = strArr.length;
            while (i < length) {
                anArrUnit = strArr[i];
                res = this.strategy.Convert(defaultUnit, anArrUnit, defaultValue);
                itemUnitConverter = new ItemUnitConverter();
                itemUnitConverter.setTitle(anArrUnit);
                itemUnitConverter.setRes(String.valueOf(res));
                list.add(itemUnitConverter);
                i++;
            }
        } else {
            double input = Double.parseDouble(this.editText.getText().toString());
            defaultValue = this.strategy.Convert(currentUnit, defaultUnit, input);
            Log.e("INP", String.valueOf(input));
            Log.e("DEF", String.valueOf(defaultValue));
            strArr = this.arrUnit;
            length = strArr.length;
            while (i < length) {
                anArrUnit = strArr[i];
                res = this.strategy.Convert(defaultUnit, anArrUnit, defaultValue);
                itemUnitConverter = new ItemUnitConverter();
                itemUnitConverter.setTitle(anArrUnit);
                itemUnitConverter.setRes(String.valueOf(res));
                list.add(itemUnitConverter);
                i++;
            }
        }
        this.mUnitAdapter.clear();
        this.mUnitAdapter.addAll(list);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
