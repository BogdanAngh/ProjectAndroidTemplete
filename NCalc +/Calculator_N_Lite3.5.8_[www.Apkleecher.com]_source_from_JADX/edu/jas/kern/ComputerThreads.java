package edu.jas.kern;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.log4j.Logger;

public class ComputerThreads {
    public static boolean NO_THREADS;
    public static final int N_CPUS;
    public static final int N_THREADS;
    private static final Logger logger;
    static ExecutorService pool;

    static {
        int i = 3;
        logger = Logger.getLogger(ComputerThreads.class);
        NO_THREADS = true;
        N_CPUS = Runtime.getRuntime().availableProcessors();
        if (N_CPUS >= 3) {
            i = N_CPUS + (N_CPUS / 2);
        }
        N_THREADS = i;
        pool = null;
    }

    private ComputerThreads() {
    }

    public static synchronized boolean isRunning() {
        boolean z = false;
        synchronized (ComputerThreads.class) {
            if (pool != null) {
                if (!(pool.isTerminated() || pool.isShutdown())) {
                    z = true;
                }
            }
        }
        return z;
    }

    public static synchronized ExecutorService getPool() {
        ExecutorService executorService;
        synchronized (ComputerThreads.class) {
            if (pool == null) {
                pool = Executors.newCachedThreadPool();
            }
            executorService = pool;
        }
        return executorService;
    }

    public static synchronized void terminate() {
        synchronized (ComputerThreads.class) {
            if (pool != null) {
                if (pool instanceof ThreadPoolExecutor) {
                    ThreadPoolExecutor tpe = (ThreadPoolExecutor) pool;
                    logger.info("number of CPUs            " + N_CPUS);
                    logger.info("core number of threads    " + N_THREADS);
                    logger.info("current number of threads " + tpe.getPoolSize());
                    logger.info("maximal number of threads " + tpe.getLargestPoolSize());
                    BlockingQueue<Runnable> workpile = tpe.getQueue();
                    if (workpile != null) {
                        logger.info("queued tasks              " + workpile.size());
                    }
                    List<Runnable> r = tpe.shutdownNow();
                    if (r.size() != 0) {
                        logger.info("unfinished tasks          " + r.size());
                    }
                    logger.info("number of sheduled tasks  " + tpe.getTaskCount());
                    logger.info("number of completed tasks " + tpe.getCompletedTaskCount());
                }
                pool = null;
            }
        }
    }

    public static synchronized void setNoThreads() {
        synchronized (ComputerThreads.class) {
            NO_THREADS = true;
        }
    }

    public static synchronized void setThreads() {
        synchronized (ComputerThreads.class) {
            NO_THREADS = false;
        }
    }
}
