package com.example.duy.calculator.converter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.duy.calculator.AbstractNavDrawer;
import com.example.duy.calculator.R;
import com.example.duy.calculator.converter.UnitConverterCategoryAdapter.OnItemClickListener;
import java.util.ArrayList;

public class UnitConverterParentAcitvity extends AbstractNavDrawer implements OnNavigationItemSelectedListener {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected static final String TAG;

    class 1 implements OnItemClickListener {
        1() {
        }

        public void onItemClick(int pos, String text) {
            UnitConverterParentAcitvity.this.startActivity(pos, text);
        }

        public void onItemLongClick() {
        }
    }

    static {
        $assertionsDisabled = !UnitConverterParentAcitvity.class.desiredAssertionStatus();
        TAG = UnitConverterParentAcitvity.class.getName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_unit_converter_acitvity);
        Toolbar toolbar = (Toolbar) findViewById(com.google.android.gms.R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(R.drawable.ic_temp));
        arrayList.add(Integer.valueOf(R.drawable.ic_weight));
        arrayList.add(Integer.valueOf(R.drawable.ic_length));
        arrayList.add(Integer.valueOf(R.drawable.ic_power));
        arrayList.add(Integer.valueOf(R.drawable.ic_power));
        arrayList.add(Integer.valueOf(R.drawable.ic_speed));
        arrayList.add(Integer.valueOf(R.drawable.ic_area));
        arrayList.add(Integer.valueOf(R.drawable.ic_cubic));
        arrayList.add(Integer.valueOf(R.drawable.ic_bitrate));
        arrayList.add(Integer.valueOf(R.drawable.ic_time));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
        UnitConverterCategoryAdapter adapter = new UnitConverterCategoryAdapter(arrayList, this);
        adapter.setListener(new 1());
        recyclerView.setAdapter(adapter);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (!$assertionsDisabled && drawer == null) {
            throw new AssertionError();
        } else if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onHelpFunction(String s) {
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        super.onNavigationItemSelected(item);
        return true;
    }

    void startActivity(int position, String text) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.key_pos), position);
        bundle.putString(getString(R.string.key_name), text);
        Intent intent = new Intent(this, UnitConverterChildActivity.class);
        intent.putExtra(getString(R.string.key_data), bundle);
        startActivity(intent);
    }
}
