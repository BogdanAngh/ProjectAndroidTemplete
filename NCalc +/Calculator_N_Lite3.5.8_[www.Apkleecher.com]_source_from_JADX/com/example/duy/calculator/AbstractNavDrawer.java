package com.example.duy.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import com.example.duy.calculator.converter.UnitConverterParentAcitvity;
import com.example.duy.calculator.geom2d.GeometryDescartesActivity;
import com.example.duy.calculator.graph.GraphActivity;
import com.example.duy.calculator.helper.AppAboutActivity;
import com.example.duy.calculator.helper.HelperActivity;
import com.example.duy.calculator.view.EventListener;

public abstract class AbstractNavDrawer extends AbstractAppCompatActivity implements OnNavigationItemSelectedListener {
    private boolean debug;
    public EventListener mListener;

    public abstract void onHelpFunction(String str);

    public AbstractNavDrawer() {
        this.mListener = new EventListener();
        this.debug = false;
    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            super.onBackPressed();
        } else if (drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mListener.setHandler(this);
    }

    public void closeDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen((int) GravityCompat.END)) {
            drawerLayout.closeDrawer((int) GravityCompat.END);
        }
        if (drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout.closeDrawer((int) GravityCompat.START);
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        closeDrawer();
        if (id == R.id.nav_sci_calc) {
            startActivity(new Intent(getApplicationContext(), MainCalculatorActivity.class));
        } else if (id == R.id.nav_graph) {
            startActivity(new Intent(getApplicationContext(), GraphActivity.class));
        } else if (id == R.id.nav_unit) {
            startActivity(new Intent(getApplicationContext(), UnitConverterParentAcitvity.class));
        } else if (id == R.id.nav_coso) {
            startActivity(new Intent(getApplicationContext(), BaseCalculatorActivity.class));
        } else if (id == R.id.nav_geometric_descartes) {
            startActivity(new Intent(getApplicationContext(), GeometryDescartesActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        } else if (id == R.id.nav_about_app) {
            startActivity(new Intent(getApplicationContext(), AppAboutActivity.class));
        } else if (id == R.id.nav_mail) {
            sendMail();
        } else if (id == R.id.nav_matrix) {
            startActivity(new Intent(getApplicationContext(), MatrixCalculatorActivity.class));
        } else if (id == R.id.nav_liner_system) {
            startActivity(new Intent(getApplicationContext(), SystemEquationActivity.class));
        } else if (id == R.id.nav_solve_equation) {
            startActivity(new Intent(getApplicationContext(), SolveEquationActivity.class));
        } else if (id == R.id.nav_simplify_equation) {
            startActivity(new Intent(getApplicationContext(), SimplifyEquationActivity.class));
        } else if (id == R.id.nav_factor_equation) {
            startActivity(new Intent(getApplicationContext(), FactorExpressionActivity.class));
        } else if (id == R.id.nav_derivitive) {
            startActivity(new Intent(getApplicationContext(), DerivativeActivity.class));
        } else if (id == R.id.nav_table) {
            startActivity(new Intent(getApplicationContext(), StatisticActivity.class));
        } else if (id == R.id.nav_more) {
            startActivity(new Intent(getApplicationContext(), MoreActivity.class));
        } else if (id == R.id.nav_expand_binomial) {
            startActivity(new Intent(getApplicationContext(), ExpandAllExpressionActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(getApplicationContext(), HelperActivity.class));
        } else if (id == R.id.nav_limit) {
            startActivity(new Intent(getApplicationContext(), LimitActivity.class));
        } else if (id == R.id.nav_integrate) {
            startActivity(new Intent(getApplicationContext(), IntegrateActivity.class));
        } else if (id == R.id.nav_primitive) {
            startActivity(new Intent(getApplicationContext(), PrimitiveActivity.class));
        } else if (id == R.id.nav_rate) {
            rateApp();
        }
        return true;
    }

    protected void openMenuDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout != null && !drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout.openDrawer((int) GravityCompat.START);
        }
    }
}
