package edu.jas.util;

import java.util.LinkedList;
import org.apache.log4j.Logger;

public class ThreadPool {
    static final int DEFAULT_SIZE = 3;
    private static boolean debug;
    private static final Logger logger;
    protected int idleworkers;
    protected LinkedList<Runnable> jobstack;
    protected volatile boolean shutdown;
    final int size;
    protected StrategyEnumeration strategy;
    protected PoolThread[] workers;

    static {
        logger = Logger.getLogger(ThreadPool.class);
        debug = logger.isDebugEnabled();
    }

    public ThreadPool() {
        this(StrategyEnumeration.FIFO, DEFAULT_SIZE);
    }

    public ThreadPool(StrategyEnumeration strategy) {
        this(strategy, DEFAULT_SIZE);
    }

    public ThreadPool(int size) {
        this(StrategyEnumeration.FIFO, size);
    }

    public ThreadPool(StrategyEnumeration strategy, int size) {
        this.idleworkers = 0;
        this.shutdown = false;
        this.strategy = StrategyEnumeration.LIFO;
        this.size = size;
        this.strategy = strategy;
        this.jobstack = new LinkedList();
        this.workers = new PoolThread[0];
    }

    public void init() {
        if (this.workers == null || this.workers.length == 0) {
            this.workers = new PoolThread[this.size];
            for (int i = 0; i < this.workers.length; i++) {
                this.workers[i] = new PoolThread(this);
                this.workers[i].start();
            }
            logger.info("size = " + this.size + ", strategy = " + this.strategy);
        }
        if (debug) {
            Thread.dumpStack();
        }
    }

    public String toString() {
        return "ThreadPool( size=" + getNumber() + ", idle=" + this.idleworkers + ", " + getStrategy() + ", jobs=" + this.jobstack.size() + ")";
    }

    public int getNumber() {
        return this.size;
    }

    public StrategyEnumeration getStrategy() {
        return this.strategy;
    }

    public void terminate() {
        while (hasJobs()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (int i = 0; i < this.workers.length; i++) {
            while (this.workers[i].isAlive()) {
                try {
                    this.workers[i].interrupt();
                    this.workers[i].join(100);
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public int cancel() {
        this.shutdown = true;
        int s = this.jobstack.size();
        if (hasJobs()) {
            synchronized (this) {
                logger.info("jobs canceled: " + this.jobstack);
                this.jobstack.clear();
                notifyAll();
            }
        }
        for (int i = 0; i < this.workers.length; i++) {
            if (this.workers[i] != null) {
                while (this.workers[i].isAlive()) {
                    try {
                        synchronized (this) {
                            this.shutdown = true;
                            notifyAll();
                            this.workers[i].interrupt();
                        }
                        this.workers[i].join(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        return s;
    }

    public synchronized void addJob(Runnable job) {
        if (this.workers == null || this.workers.length < this.size) {
            init();
        }
        this.jobstack.addLast(job);
        logger.debug("adding job");
        if (this.idleworkers > 0) {
            logger.debug("notifying a jobless worker");
            notifyAll();
        }
    }

    protected synchronized Runnable getJob() throws InterruptedException {
        Runnable runnable;
        do {
            if (this.jobstack.isEmpty()) {
                this.idleworkers++;
                logger.debug("waiting");
                wait(1000);
                this.idleworkers--;
            } else if (this.strategy == StrategyEnumeration.LIFO) {
                runnable = (Runnable) this.jobstack.removeLast();
            } else {
                runnable = (Runnable) this.jobstack.removeFirst();
            }
        } while (!this.shutdown);
        throw new InterruptedException("shutdown in getJob");
        return runnable;
    }

    public boolean hasJobs() {
        if (this.jobstack.size() > 0) {
            return true;
        }
        int i = 0;
        while (i < this.workers.length) {
            if (this.workers[i] != null && this.workers[i].isWorking) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean hasJobs(int n) {
        int j = this.jobstack.size();
        if (j > 0 && this.workers.length + j > n) {
            return true;
        }
        int x = 0;
        int i = 0;
        while (i < this.workers.length) {
            if (this.workers[i] != null && this.workers[i].isWorking) {
                x++;
            }
            i++;
        }
        if (j + x <= n) {
            return false;
        }
        return true;
    }
}
