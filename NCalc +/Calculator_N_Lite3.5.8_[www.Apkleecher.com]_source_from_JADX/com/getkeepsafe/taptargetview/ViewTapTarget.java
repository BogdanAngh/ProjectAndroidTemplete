package com.getkeepsafe.taptargetview;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.view.View;

public class ViewTapTarget extends TapTarget {
    final View view;

    class 1 implements Runnable {
        final /* synthetic */ Runnable val$runnable;

        1(Runnable runnable) {
            this.val$runnable = runnable;
        }

        public void run() {
            int[] location = new int[2];
            ViewTapTarget.this.view.getLocationOnScreen(location);
            ViewTapTarget.this.bounds = new Rect(location[0], location[1], location[0] + ViewTapTarget.this.view.getWidth(), location[1] + ViewTapTarget.this.view.getHeight());
            Bitmap viewBitmap = Bitmap.createBitmap(ViewTapTarget.this.view.getWidth(), ViewTapTarget.this.view.getHeight(), Config.ARGB_8888);
            ViewTapTarget.this.view.draw(new Canvas(viewBitmap));
            ViewTapTarget.this.icon = new BitmapDrawable(ViewTapTarget.this.view.getContext().getResources(), viewBitmap);
            ViewTapTarget.this.icon.setBounds(0, 0, ViewTapTarget.this.icon.getIntrinsicWidth(), ViewTapTarget.this.icon.getIntrinsicHeight());
            this.val$runnable.run();
        }
    }

    protected ViewTapTarget(View view, CharSequence title, @Nullable CharSequence description) {
        super(title, description);
        if (view == null) {
            throw new IllegalArgumentException("Given null view to target");
        }
        this.view = view;
    }

    public void onReady(Runnable runnable) {
        ViewUtil.onLaidOut(this.view, new 1(runnable));
    }
}
