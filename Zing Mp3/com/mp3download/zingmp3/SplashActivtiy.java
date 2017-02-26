package com.mp3download.zingmp3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivtiy extends Activity {

    /* renamed from: com.mp3download.zingmp3.SplashActivtiy.1 */
    class C15701 extends TimerTask {
        C15701() {
        }

        public void run() {
            Intent i = new Intent(SplashActivtiy.this, MainActivity.class);
            i.setFlags(268468224);
            SplashActivtiy.this.startActivity(i);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1569R.layout.activtiy_splash);
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        new Timer().schedule(new C15701(), 2000);
    }
}
