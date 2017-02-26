package com.badlogic.gdx.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;

public class Timer {
    private static final int CANCELLED = -1;
    private static final int FOREVER = -2;
    static Timer instance;
    static final Array<Timer> instances;
    static TimerThread thread;
    private final Array<Task> tasks;

    public static abstract class Task implements Runnable {
        long executeTimeMillis;
        long intervalMillis;
        int repeatCount;

        public abstract void run();

        public Task() {
            this.repeatCount = Timer.CANCELLED;
        }

        public void cancel() {
            this.executeTimeMillis = 0;
            this.repeatCount = Timer.CANCELLED;
        }

        public boolean isScheduled() {
            return this.repeatCount != Timer.CANCELLED;
        }
    }

    static class TimerThread implements Runnable, LifecycleListener {
        private Application app;
        private long pauseMillis;

        public TimerThread() {
            Gdx.app.addLifecycleListener(this);
            resume();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r14 = this;
        L_0x0000:
            r8 = com.badlogic.gdx.utils.Timer.instances;
            monitor-enter(r8);
            r3 = r14.app;	 Catch:{ all -> 0x0056 }
            r9 = com.badlogic.gdx.Gdx.app;	 Catch:{ all -> 0x0056 }
            if (r3 == r9) goto L_0x000b;
        L_0x0009:
            monitor-exit(r8);	 Catch:{ all -> 0x0056 }
        L_0x000a:
            return;
        L_0x000b:
            r10 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0056 }
            r12 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
            r4 = r10 / r12;
            r6 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
            r1 = 0;
            r3 = com.badlogic.gdx.utils.Timer.instances;	 Catch:{ all -> 0x0056 }
            r2 = r3.size;	 Catch:{ all -> 0x0056 }
        L_0x001b:
            if (r1 >= r2) goto L_0x0059;
        L_0x001d:
            r3 = com.badlogic.gdx.utils.Timer.instances;	 Catch:{ Throwable -> 0x002c }
            r3 = r3.get(r1);	 Catch:{ Throwable -> 0x002c }
            r3 = (com.badlogic.gdx.utils.Timer) r3;	 Catch:{ Throwable -> 0x002c }
            r6 = r3.update(r4, r6);	 Catch:{ Throwable -> 0x002c }
            r1 = r1 + 1;
            goto L_0x001b;
        L_0x002c:
            r0 = move-exception;
            r9 = new com.badlogic.gdx.utils.GdxRuntimeException;	 Catch:{ all -> 0x0056 }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0056 }
            r3.<init>();	 Catch:{ all -> 0x0056 }
            r10 = "Task failed: ";
            r10 = r3.append(r10);	 Catch:{ all -> 0x0056 }
            r3 = com.badlogic.gdx.utils.Timer.instances;	 Catch:{ all -> 0x0056 }
            r3 = r3.get(r1);	 Catch:{ all -> 0x0056 }
            r3 = (com.badlogic.gdx.utils.Timer) r3;	 Catch:{ all -> 0x0056 }
            r3 = r3.getClass();	 Catch:{ all -> 0x0056 }
            r3 = r3.getName();	 Catch:{ all -> 0x0056 }
            r3 = r10.append(r3);	 Catch:{ all -> 0x0056 }
            r3 = r3.toString();	 Catch:{ all -> 0x0056 }
            r9.<init>(r3, r0);	 Catch:{ all -> 0x0056 }
            throw r9;	 Catch:{ all -> 0x0056 }
        L_0x0056:
            r3 = move-exception;
            monitor-exit(r8);	 Catch:{ all -> 0x0056 }
            throw r3;
        L_0x0059:
            r3 = r14.app;	 Catch:{ all -> 0x0056 }
            r9 = com.badlogic.gdx.Gdx.app;	 Catch:{ all -> 0x0056 }
            if (r3 == r9) goto L_0x0061;
        L_0x005f:
            monitor-exit(r8);	 Catch:{ all -> 0x0056 }
            goto L_0x000a;
        L_0x0061:
            r10 = 0;
            r3 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
            if (r3 <= 0) goto L_0x006c;
        L_0x0067:
            r3 = com.badlogic.gdx.utils.Timer.instances;	 Catch:{ InterruptedException -> 0x006e }
            r3.wait(r6);	 Catch:{ InterruptedException -> 0x006e }
        L_0x006c:
            monitor-exit(r8);	 Catch:{ all -> 0x0056 }
            goto L_0x0000;
        L_0x006e:
            r3 = move-exception;
            goto L_0x006c;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.Timer.TimerThread.run():void");
        }

        public void resume() {
            long delayMillis = (System.nanoTime() / 1000000) - this.pauseMillis;
            synchronized (Timer.instances) {
                int n = Timer.instances.size;
                for (int i = 0; i < n; i++) {
                    ((Timer) Timer.instances.get(i)).delay(delayMillis);
                }
            }
            this.app = Gdx.app;
            new Thread(this, "Timer").start();
        }

        public void pause() {
            this.pauseMillis = System.nanoTime() / 1000000;
            synchronized (Timer.instances) {
                this.app = null;
                Timer.wake();
            }
        }

        public void dispose() {
            pause();
            Gdx.app.removeLifecycleListener(this);
            Timer.thread = null;
            Timer.instances.clear();
            Timer.instance = null;
        }
    }

    static {
        instances = new Array(1);
        instance = new Timer();
    }

    public static Timer instance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    public Timer() {
        this.tasks = new Array(false, 8);
        start();
    }

    public void postTask(Task task) {
        scheduleTask(task, 0.0f, 0.0f, 0);
    }

    public void scheduleTask(Task task, float delaySeconds) {
        scheduleTask(task, delaySeconds, 0.0f, 0);
    }

    public void scheduleTask(Task task, float delaySeconds, float intervalSeconds) {
        scheduleTask(task, delaySeconds, intervalSeconds, FOREVER);
    }

    public void scheduleTask(Task task, float delaySeconds, float intervalSeconds, int repeatCount) {
        if (task.repeatCount != CANCELLED) {
            throw new IllegalArgumentException("The same task may not be scheduled twice.");
        }
        task.executeTimeMillis = (System.nanoTime() / 1000000) + ((long) (delaySeconds * 1000.0f));
        task.intervalMillis = (long) (intervalSeconds * 1000.0f);
        task.repeatCount = repeatCount;
        synchronized (this.tasks) {
            this.tasks.add(task);
        }
        wake();
    }

    public void stop() {
        synchronized (instances) {
            instances.removeValue(this, true);
        }
    }

    public void start() {
        synchronized (instances) {
            if (instances.contains(this, true)) {
                return;
            }
            instances.add(this);
            if (thread == null) {
                thread = new TimerThread();
            }
            wake();
        }
    }

    public void clear() {
        synchronized (this.tasks) {
            int n = this.tasks.size;
            for (int i = 0; i < n; i++) {
                ((Task) this.tasks.get(i)).cancel();
            }
            this.tasks.clear();
        }
    }

    long update(long timeMillis, long waitMillis) {
        synchronized (this.tasks) {
            int i = 0;
            int n = this.tasks.size;
            while (i < n) {
                Task task = (Task) this.tasks.get(i);
                if (task.executeTimeMillis > timeMillis) {
                    waitMillis = Math.min(waitMillis, task.executeTimeMillis - timeMillis);
                } else {
                    if (task.repeatCount != CANCELLED) {
                        if (task.repeatCount == 0) {
                            task.repeatCount = CANCELLED;
                        }
                        Gdx.app.postRunnable(task);
                    }
                    if (task.repeatCount == CANCELLED) {
                        this.tasks.removeIndex(i);
                        i += CANCELLED;
                        n += CANCELLED;
                    } else {
                        task.executeTimeMillis = task.intervalMillis + timeMillis;
                        waitMillis = Math.min(waitMillis, task.intervalMillis);
                        if (task.repeatCount > 0) {
                            task.repeatCount += CANCELLED;
                        }
                    }
                }
                i++;
            }
        }
        return waitMillis;
    }

    public void delay(long delayMillis) {
        synchronized (this.tasks) {
            int n = this.tasks.size;
            for (int i = 0; i < n; i++) {
                Task task = (Task) this.tasks.get(i);
                task.executeTimeMillis += delayMillis;
            }
        }
    }

    static void wake() {
        synchronized (instances) {
            instances.notifyAll();
        }
    }

    public static void post(Task task) {
        instance().postTask(task);
    }

    public static void schedule(Task task, float delaySeconds) {
        instance().scheduleTask(task, delaySeconds);
    }

    public static void schedule(Task task, float delaySeconds, float intervalSeconds) {
        instance().scheduleTask(task, delaySeconds, intervalSeconds);
    }

    public static void schedule(Task task, float delaySeconds, float intervalSeconds, int repeatCount) {
        instance().scheduleTask(task, delaySeconds, intervalSeconds, repeatCount);
    }
}
