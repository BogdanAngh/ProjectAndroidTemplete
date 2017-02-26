package com.getkeepsafe.taptargetview;

import android.app.Activity;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TapTargetSequence {
    private final Activity activity;
    boolean continueOnCancel;
    Listener listener;
    private boolean started;
    private final com.getkeepsafe.taptargetview.TapTargetView.Listener tapTargetListener;
    private final Queue<TapTarget> targets;

    public interface Listener {
        void onSequenceCanceled(TapTarget tapTarget);

        void onSequenceFinish();
    }

    class 1 extends com.getkeepsafe.taptargetview.TapTargetView.Listener {
        1() {
        }

        public void onTargetClick(TapTargetView view) {
            super.onTargetClick(view);
            TapTargetSequence.this.showNext();
        }

        public void onTargetCancel(TapTargetView view) {
            super.onTargetCancel(view);
            if (TapTargetSequence.this.continueOnCancel) {
                TapTargetSequence.this.showNext();
            } else if (TapTargetSequence.this.listener != null) {
                TapTargetSequence.this.listener.onSequenceCanceled(view.target);
            }
        }
    }

    public TapTargetSequence(Activity activity) {
        this.tapTargetListener = new 1();
        if (activity == null) {
            throw new IllegalArgumentException("Activity is null");
        }
        this.activity = activity;
        this.targets = new LinkedList();
    }

    public TapTargetSequence targets(List<TapTarget> targets) {
        this.targets.addAll(targets);
        return this;
    }

    public TapTargetSequence targets(TapTarget... targets) {
        Collections.addAll(this.targets, targets);
        return this;
    }

    public TapTargetSequence target(TapTarget target) {
        this.targets.add(target);
        return this;
    }

    public TapTargetSequence continueOnCancel(boolean status) {
        this.continueOnCancel = status;
        return this;
    }

    public TapTargetSequence listener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public void start() {
        if (!this.targets.isEmpty() && !this.started) {
            this.started = true;
            showNext();
        }
    }

    void showNext() {
        try {
            TapTargetView.showFor(this.activity, (TapTarget) this.targets.remove(), this.tapTargetListener);
        } catch (NoSuchElementException e) {
            if (this.listener != null) {
                this.listener.onSequenceFinish();
            }
        }
    }
}
