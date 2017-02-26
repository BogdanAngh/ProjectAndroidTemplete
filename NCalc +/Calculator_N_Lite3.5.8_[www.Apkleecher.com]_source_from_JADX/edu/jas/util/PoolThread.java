package edu.jas.util;

import edu.jas.kern.PreemptingException;
import org.apache.log4j.Logger;

/* compiled from: ThreadPool */
class PoolThread extends Thread {
    private static boolean debug;
    private static final Logger logger;
    volatile boolean isWorking;
    ThreadPool pool;

    static {
        logger = Logger.getLogger(PoolThread.class);
        debug = logger.isDebugEnabled();
    }

    public PoolThread(ThreadPool pool) {
        this.isWorking = false;
        this.pool = pool;
    }

    public void run() {
        logger.info("ready");
        int done = 0;
        long time = 0;
        boolean running = true;
        while (running) {
            try {
                logger.debug("looking for a job");
                Runnable job = this.pool.getJob();
                if (job == null) {
                    break;
                }
                if (debug) {
                    logger.info("working");
                }
                long t = System.currentTimeMillis();
                this.isWorking = true;
                job.run();
                this.isWorking = false;
                time += System.currentTimeMillis() - t;
                done++;
                if (debug) {
                    logger.info("done");
                }
                if (Thread.currentThread().isInterrupted()) {
                    running = false;
                    this.isWorking = false;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
                this.isWorking = false;
            } catch (PreemptingException e2) {
                logger.debug("catched " + e2);
            } catch (RuntimeException e3) {
                logger.warn("catched " + e3);
                e3.printStackTrace();
            }
        }
        this.isWorking = false;
        logger.info("terminated, done " + done + " jobs in " + time + " milliseconds");
    }
}
