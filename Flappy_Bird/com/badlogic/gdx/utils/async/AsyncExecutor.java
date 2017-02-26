package com.badlogic.gdx.utils.async;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class AsyncExecutor implements Disposable {
    private final ExecutorService executor;

    /* renamed from: com.badlogic.gdx.utils.async.AsyncExecutor.1 */
    class C00651 implements ThreadFactory {
        C00651() {
        }

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "AsynchExecutor-Thread");
            thread.setDaemon(true);
            return thread;
        }
    }

    /* renamed from: com.badlogic.gdx.utils.async.AsyncExecutor.2 */
    class C00662 implements Callable<T> {
        final /* synthetic */ AsyncTask val$task;

        C00662(AsyncTask asyncTask) {
            this.val$task = asyncTask;
        }

        public T call() throws Exception {
            return this.val$task.call();
        }
    }

    public AsyncExecutor(int maxConcurrent) {
        this.executor = Executors.newFixedThreadPool(maxConcurrent, new C00651());
    }

    public <T> AsyncResult<T> submit(AsyncTask<T> task) {
        return new AsyncResult(this.executor.submit(new C00662(task)));
    }

    public void dispose() {
        this.executor.shutdown();
        try {
            this.executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new GdxRuntimeException("Couldn't shutdown loading thread", e);
        }
    }
}
