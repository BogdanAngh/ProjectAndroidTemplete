package edu.jas.util;

import java.util.concurrent.Semaphore;
import org.apache.log4j.Logger;

public class Terminator {
    private static final Logger logger;
    private boolean done;
    private final Semaphore fin;
    private int idler;
    private final int workers;

    static {
        logger = Logger.getLogger(Terminator.class);
    }

    public Terminator(int workers) {
        this.workers = workers;
        this.fin = new Semaphore(0);
        this.done = false;
        this.idler = 0;
        logger.info("constructor, workers = " + workers);
    }

    public String toString() {
        return "Terminator(" + this.done + ",workers=" + this.workers + ",idler=" + this.idler + ")";
    }

    public synchronized void beIdle() {
        this.idler++;
        logger.info("beIdle, idler = " + this.idler);
        if (this.idler >= this.workers) {
            this.done = true;
            this.fin.release();
        }
    }

    public synchronized void initIdle(int i) {
        this.idler += i;
        logger.info("initIdle, idler = " + this.idler);
        if (this.idler > this.workers) {
            if (this.done) {
                this.idler = this.workers;
            } else {
                throw new RuntimeException("idler > workers");
            }
        }
    }

    public synchronized void beIdle(int i) {
        this.idler += i;
        logger.info("beIdle, idler = " + this.idler);
        if (this.idler >= this.workers) {
            this.done = true;
            this.fin.release();
        }
    }

    public synchronized void allIdle() {
        this.idler = this.workers;
        logger.info("allIdle");
        this.done = true;
        this.fin.release();
    }

    public synchronized void notIdle() {
        this.idler--;
        logger.info("notIdle, idler = " + this.idler);
        if (this.idler < 0) {
            throw new RuntimeException("idler < 0");
        }
    }

    public synchronized int getJobs() {
        return this.workers - this.idler;
    }

    public synchronized boolean hasJobs() {
        return this.idler < this.workers;
    }

    public synchronized void release() {
        logger.info("release = " + this);
        if (this.idler >= this.workers) {
            this.done = true;
            this.fin.release();
        }
    }

    public void waitDone() {
        try {
            this.fin.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("waitDone " + this);
    }
}
