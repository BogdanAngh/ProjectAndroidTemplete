package com.frodidev.FloppyBird;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import im.delight.apprater.AppRater;

public class MainActivity extends AndroidApplication implements ActionResolver, GameHelperListener {
    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-2618900425296104/7706806472";
    private static final int REQUEST_CODE_UNUSED = 9002;
    final int INTERADSEVERY;
    private GameHelper _gameHelper;
    private int interAdsPeriod;
    private InterstitialAd interstitial;
    InterstitialAd interstitialAd;
    private boolean interstitialHasInited;
    private AdListener interstitialListener;
    AlertDialog mAlertDialog;
    private CountDownTimer mCountDownTimer;

    /* renamed from: com.frodidev.FloppyBird.MainActivity.3 */
    class C00673 implements Runnable {
        C00673() {
        }

        public void run() {
            MainActivity.this._gameHelper.beginUserInitiatedSignIn();
        }
    }

    /* renamed from: com.frodidev.FloppyBird.MainActivity.4 */
    class C00684 implements Runnable {
        C00684() {
        }

        public void run() {
            MainActivity.this._gameHelper.signOut();
        }
    }

    /* renamed from: com.frodidev.FloppyBird.MainActivity.5 */
    class C00695 implements Runnable {
        C00695() {
        }

        public void run() {
            MainActivity.this.showAdmobInterstitial();
        }
    }

    /* renamed from: com.frodidev.FloppyBird.MainActivity.6 */
    class C00706 implements Runnable {
        C00706() {
        }

        public void run() {
            MainActivity.this.loadAdmobInterstitial();
        }
    }

    /* renamed from: com.frodidev.FloppyBird.MainActivity.7 */
    class C00717 implements OnClickListener {
        C00717() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            MainActivity.this.finish();
            MainActivity.this.finish();
        }
    }

    /* renamed from: com.frodidev.FloppyBird.MainActivity.8 */
    class C00728 implements OnClickListener {
        C00728() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private class AdmobInterstitialsTask extends AsyncTask<Void, Void, AdRequest> {
        private AdmobInterstitialsTask() {
        }

        protected AdRequest doInBackground(Void... arg0) {
            Gdx.app.log("Floppy Bird", "init Interstitial");
            AdRequest adRequest = new Builder().addTestDevice(MainActivity.this.getString(C0073R.string.testDevice)).build();
            if (MainActivity.this.interstitial == null) {
                MainActivity.this.interstitial = new InterstitialAd(MainActivity.this);
                MainActivity.this.interstitial.setAdUnitId(MainActivity.INTERSTITIAL_UNIT_ID);
                MainActivity.this.interstitial.setAdListener(MainActivity.this.interstitialListener);
            }
            return adRequest;
        }

        protected void onPostExecute(AdRequest result) {
            if (result != null) {
                MainActivity.this.interstitial.loadAd(result);
            }
        }
    }

    /* renamed from: com.frodidev.FloppyBird.MainActivity.1 */
    class C03821 extends AdListener {
        C03821() {
        }

        public void onAdFailedToLoad(int errorCode) {
            MainActivity.this.interstitialHasInited = false;
            Gdx.app.log("Floppy Bird", "interstitial failed, code = " + errorCode);
        }

        public void onAdLoaded() {
            Gdx.app.log("Floppy Bird", "interstitial loaded");
        }
    }

    /* renamed from: com.frodidev.FloppyBird.MainActivity.2 */
    class C03832 implements GameHelperListener {
        C03832() {
        }

        public void onSignInSucceeded() {
        }

        public void onSignInFailed() {
        }
    }

    public MainActivity() {
        this.interAdsPeriod = 0;
        this.INTERADSEVERY = 3;
        this.interstitialHasInited = false;
        this.interstitialListener = new C03821();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppRater appRater = new AppRater(this);
        appRater.setDaysBeforePrompt(0);
        appRater.setLaunchesBeforePrompt(3);
        appRater.setPhrases("Rate this app", "Do you like Floppy.Rate ;)", "Rate now", "Later", "No, thanks");
        appRater.setTargetUri("https://play.google.com/store/apps/details?id=com.frodidev.FloppyBird");
        appRater.setPreferenceKeys("app_rater", "flag_dont_show", "launch_count", "first_launch_time");
        this.mAlertDialog = appRater.show();
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        RelativeLayout layout = new RelativeLayout(this);
        requestWindowFeature(1);
        getWindow().setFlags(Place.TYPE_SUBLOCALITY_LEVEL_2, Place.TYPE_SUBLOCALITY_LEVEL_2);
        getWindow().clearFlags(GL10.GL_EXP);
        View gameView = initializeForView(new ZBGame(this), cfg);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setBackgroundColor(0);
        adView.setAdUnitId("ca-app-pub-2618900425296104/9722458474");
        adView.loadAd(new Builder().build());
        layout.addView(gameView);
        LayoutParams adParams = new LayoutParams(-2, -2);
        adParams.addRule(12);
        adParams.addRule(14);
        layout.addView(adView, adParams);
        setContentView(layout);
        this._gameHelper = new GameHelper(this, 1);
        this._gameHelper.enableDebugLog(false);
        this._gameHelper.setup(new C03832());
    }

    protected void onStart() {
        super.onStart();
        this._gameHelper.onStart(this);
    }

    protected void onStop() {
        super.onStop();
        this._gameHelper.onStop();
    }

    public void signIn() {
        try {
            runOnUiThread(new C00673());
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    public void signOut() {
        try {
            runOnUiThread(new C00684());
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    public boolean isSignedIn() {
        return this._gameHelper.isSignedIn();
    }

    public void onSignInFailed() {
    }

    public void onSignInSucceeded() {
    }

    public void submitScore(long score) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(this._gameHelper.getApiClient(), "CgkIrpvQ0fQMEAIQAQ", score);
        }
    }

    public void showScores() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(this._gameHelper.getApiClient()), REQUEST_CODE_UNUSED);
        } else {
            signIn();
        }
    }

    public void showAchievement() {
        if (isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(this._gameHelper.getApiClient()), REQUEST_CODE_UNUSED);
        } else {
            signIn();
        }
    }

    public void unlockAchievementGPGS(String string) {
        if (isSignedIn()) {
            Games.Achievements.unlock(this._gameHelper.getApiClient(), string);
        }
    }

    private void loadAdmobInterstitial() {
        if (!this.interstitialHasInited) {
            this.interstitialHasInited = true;
            new AdmobInterstitialsTask().execute(new Void[0]);
        }
    }

    private void showAdmobInterstitial() {
        if (this.interstitial != null) {
            Gdx.app.log("Floppy Bird", "show interstitials");
            if (this.interstitial.isLoaded()) {
                this.interstitial.show();
                this.interstitialHasInited = false;
                return;
            }
            Gdx.app.log("Floppy Bird", "... not loaded yet, skip it");
        }
    }

    public void showInterAds() {
        if (this.interAdsPeriod == 3) {
            runOnUiThread(new C00695());
            this.interAdsPeriod = 0;
            return;
        }
        this.interAdsPeriod++;
    }

    public void loadInterAds() {
        runOnUiThread(new C00706());
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Close Game");
        builder.setMessage("Do you really want to exit?");
        builder.setPositiveButton("YES", new C00717());
        builder.setNegativeButton("NO", new C00728());
        builder.create().show();
    }
}
