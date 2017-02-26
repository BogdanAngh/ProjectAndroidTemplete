package com.example.duy.calculator.geom2d;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.duy.calculator.AbstractNavDrawer;
import com.example.duy.calculator.R;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class GeometryDescartesActivity extends AbstractNavDrawer implements OnNavigationItemSelectedListener, OnClickListener, OnPageChangeListener {
    private static final String ACTION_CIRCLE = "com.example.duy.org.solovyev.android.calculator.geometry.circle";
    private static final String ACTION_CORNER = "com.example.duy.org.solovyev.android.calculator.geometry.corner";
    private static final String ACTION_ELLIPSE = "com.example.duy.org.solovyev.android.calculator.geometry.ellipse";
    private static final String ACTION_POINT = "com.example.duy.org.solovyev.android.calculator.geometry.point";
    private static final String ACTION_POLYGON = "com.example.duy.org.solovyev.android.calculator.geometry.polygon";
    private static final String ACTION_VEXTER = "com.example.duy.org.solovyev.android.calculator.geometry.vexter";
    private static final int mCount = 3;
    private ViewPager mPager;
    private TabLayout mTab;

    static class PagerApdater extends FragmentPagerAdapter {
        private final Context context;

        public PagerApdater(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        public Fragment getItem(int position) {
            switch (position) {
                case ValueServer.DIGEST_MODE /*0*/:
                    return new FragmentVector();
                case ValueServer.REPLAY_MODE /*1*/:
                    return new FragmentLine();
                case IExpr.DOUBLEID /*2*/:
                    return new FragmentCircle();
                case GeometryDescartesActivity.mCount /*3*/:
                    return new FragmentPolygon();
                case IExpr.DOUBLECOMPLEXID /*4*/:
                    return new FragmentEllipse();
                default:
                    return null;
            }
        }

        public int getCount() {
            return GeometryDescartesActivity.mCount;
        }

        public CharSequence getPageTitle(int position) {
            Resources resources = this.context.getResources();
            switch (position) {
                case ValueServer.DIGEST_MODE /*0*/:
                    return resources.getString(R.string.vector);
                case ValueServer.REPLAY_MODE /*1*/:
                    return resources.getString(R.string.line);
                case IExpr.DOUBLEID /*2*/:
                    return resources.getString(R.string.circle);
                case GeometryDescartesActivity.mCount /*3*/:
                    return resources.getString(R.string.polygon);
                case IExpr.DOUBLECOMPLEXID /*4*/:
                    return resources.getString(R.string.ellipse);
                default:
                    return null;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_geometry_descartes);
        Toolbar toolbar = (Toolbar) findViewById(com.google.android.gms.R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.mPager = (ViewPager) findViewById(R.id.viewpager);
        this.mPager.setOffscreenPageLimit(mCount);
        this.mPager.setAdapter(new PagerApdater(getSupportFragmentManager(), this));
        this.mPager.addOnPageChangeListener(this);
        this.mTab = (TabLayout) findViewById(R.id.tab);
        this.mTab.setupWithViewPager(this.mPager, true);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onHelpFunction(String s) {
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        super.onNavigationItemSelected(item);
        return true;
    }

    public void onClick(View view) {
        int id = view.getId();
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
    }

    public void onPageScrollStateChanged(int state) {
    }
}
