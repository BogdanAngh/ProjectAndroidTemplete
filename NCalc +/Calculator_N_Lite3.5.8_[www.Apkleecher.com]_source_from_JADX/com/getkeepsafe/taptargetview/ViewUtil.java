package com.getkeepsafe.taptargetview;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class ViewUtil {

    static class 1 implements OnGlobalLayoutListener {
        final /* synthetic */ ViewTreeObserver val$observer;
        final /* synthetic */ Runnable val$runnable;
        final /* synthetic */ View val$view;

        1(ViewTreeObserver viewTreeObserver, View view, Runnable runnable) {
            this.val$observer = viewTreeObserver;
            this.val$view = view;
            this.val$runnable = runnable;
        }

        public void onGlobalLayout() {
            ViewTreeObserver trueObserver;
            if (this.val$observer.isAlive()) {
                trueObserver = this.val$observer;
            } else {
                trueObserver = this.val$view.getViewTreeObserver();
            }
            if (VERSION.SDK_INT >= 16) {
                trueObserver.removeOnGlobalLayoutListener(this);
            } else {
                trueObserver.removeGlobalOnLayoutListener(this);
            }
            this.val$runnable.run();
        }
    }

    ViewUtil() {
    }

    static boolean isLaidOut(View view) {
        if (VERSION.SDK_INT >= 19) {
            return view.isLaidOut();
        }
        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    static void onLaidOut(View view, Runnable runnable) {
        if (isLaidOut(view)) {
            runnable.run();
            return;
        }
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new 1(observer, view, runnable));
    }
}
