package com.badlogic.gdx.utils;

public class PauseableThread extends Thread {
    boolean exit;
    boolean paused;
    final Runnable runnable;

    public PauseableThread(Runnable runnable) {
        this.paused = false;
        this.exit = false;
        this.runnable = runnable;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r2 = this;
    L_0x0000:
        monitor-enter(r2);
    L_0x0001:
        r1 = r2.paused;	 Catch:{ InterruptedException -> 0x0009 }
        if (r1 == 0) goto L_0x000d;
    L_0x0005:
        r2.wait();	 Catch:{ InterruptedException -> 0x0009 }
        goto L_0x0001;
    L_0x0009:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0013 }
    L_0x000d:
        monitor-exit(r2);	 Catch:{ all -> 0x0013 }
        r1 = r2.exit;
        if (r1 == 0) goto L_0x0016;
    L_0x0012:
        return;
    L_0x0013:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0013 }
        throw r1;
    L_0x0016:
        r1 = r2.runnable;
        r1.run();
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.PauseableThread.run():void");
    }

    public void onPause() {
        this.paused = true;
    }

    public void onResume() {
        synchronized (this) {
            this.paused = false;
            notifyAll();
        }
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void stopThread() {
        this.exit = true;
        if (this.paused) {
            onResume();
        }
    }
}
