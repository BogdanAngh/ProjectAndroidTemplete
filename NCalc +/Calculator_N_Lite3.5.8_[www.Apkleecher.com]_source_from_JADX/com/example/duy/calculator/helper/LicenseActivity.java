package com.example.duy.calculator.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import com.example.duy.calculator.AbstractAppCompatActivity;
import com.example.duy.calculator.R;

public class LicenseActivity extends AbstractAppCompatActivity {

    class 1 implements OnItemClickListener {
        final /* synthetic */ String[] val$arr;
        final /* synthetic */ String[] val$lics;

        1(String[] strArr, String[] strArr2) {
            this.val$lics = strArr;
            this.val$arr = strArr2;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(LicenseActivity.this, InforLicenseActivity.class);
            intent.putExtra("data", this.val$lics[i]);
            intent.putExtra("title", this.val$arr[i]);
            LicenseActivity.this.startActivity(intent);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.license_layout);
        setSupportActionBar((Toolbar) findViewById(com.google.android.gms.R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String[] arr = getResources().getStringArray(R.array.libs);
        String[] lics = getResources().getStringArray(R.array.lics);
        ListViewCompat listViewCompat = (ListViewCompat) findViewById(R.id.rc_view);
        listViewCompat.setAdapter(new ArrayAdapter(this, 17367043, arr));
        listViewCompat.setOnItemClickListener(new 1(lics, arr));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
