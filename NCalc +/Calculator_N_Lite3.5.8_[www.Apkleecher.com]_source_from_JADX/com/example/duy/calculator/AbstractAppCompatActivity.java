package com.example.duy.calculator;

import android.animation.Animator;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.example.duy.calculator.data.Database;
import com.example.duy.calculator.data.Settings;
import com.example.duy.calculator.utils.ThemeUtil;
import com.example.duy.calculator.view.AnimationFinishedListener;
import io.github.kexanie.library.BuildConfig;
import org.matheclipse.core.interfaces.IExpr;

public abstract class AbstractAppCompatActivity extends AppCompatActivity implements OnSharedPreferenceChangeListener {
    protected String TAG;
    protected Database database;
    protected final LayoutParams mLayoutParams;
    protected SharedPreferences mPreferences;
    protected Settings setting;

    class 1 extends AnimationFinishedListener {
        1() {
        }

        public void onAnimationFinished() {
        }
    }

    class 2 implements OnClickListener {
        2() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    public AbstractAppCompatActivity() {
        this.mLayoutParams = new LayoutParams(-1, -1);
        this.TAG = AbstractAppCompatActivity.class.getName();
    }

    protected void play(Animator animator) {
        animator.addListener(new 1());
        animator.start();
    }

    public Settings getSetting() {
        return this.setting;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(IExpr.ASTID, IExpr.ASTID);
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.mPreferences.registerOnSharedPreferenceChangeListener(this);
        setTheme(false);
        this.database = new Database(this);
        this.setting = new Settings(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void setTheme(boolean recreate) {
        int themeId = new ThemeUtil(getApplicationContext()).getTheme(this.mPreferences.getString(getResources().getString(R.string.key_pref_theme), BuildConfig.FLAVOR));
        if (themeId != -1) {
            super.setTheme(themeId);
            if (recreate) {
                recreate();
            }
            Log.d(this.TAG, "Set theme ok");
            return;
        }
        Log.d(this.TAG, "Theme not found");
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.w(this.TAG, s);
        if (s.equals(getResources().getString(R.string.key_pref_theme))) {
            setTheme(true);
        }
    }

    protected void sendMail() {
        Intent email = new Intent("android.intent.action.SEND");
        email.putExtra("android.intent.extra.EMAIL", new String[]{getString(R.string.dev_mail)});
        email.putExtra("android.intent.extra.SUBJECT", getString(R.string.feedback));
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    protected void shareApp() {
    }

    protected void showDialog(String title, String msg) {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) title).setMessage((CharSequence) msg);
        builder.setNegativeButton(getString(R.string.close), new 2());
        builder.create().show();
    }

    protected void showDialog(String msg) {
        showDialog(BuildConfig.FLAVOR, msg);
    }

    public void showDialogUpdate() {
        if (Settings.isUpdate(this)) {
            showDialog(getString(R.string.what_new), getString(R.string.update_new));
            Settings.setUpdate(this, true);
        }
    }

    public void rateApp() {
        Intent goToMarket = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.duy.calculator.free"));
        goToMarket.addFlags(1208483840);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.duy.calculator.free")));
        }
    }

    protected void hideKeyboard(EditText editText) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
