package edu.jas.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

public class TaggedSocketChannel extends Thread {
    private static final String DONE = "TaggedSocketChannel Done";
    private static final boolean debug;
    private static final Logger logger;
    private final AtomicInteger blockedCount;
    private volatile boolean isRunning;
    protected final Map<Integer, BlockingQueue> queues;
    protected final SocketChannel sc;

    static {
        logger = Logger.getLogger(TaggedSocketChannel.class);
        debug = logger.isDebugEnabled();
    }

    public TaggedSocketChannel(SocketChannel s) {
        this.isRunning = false;
        this.sc = s;
        this.blockedCount = new AtomicInteger(0);
        this.queues = new HashMap();
    }

    public void init() {
        synchronized (this.queues) {
            if (!this.isRunning) {
                start();
                this.isRunning = true;
            }
        }
        logger.info("TaggedSocketChannel at " + this.sc);
    }

    public SocketChannel getSocket() {
        return this.sc;
    }

    public void send(Integer tag, Object v) throws IOException {
        if (tag == null) {
            throw new IllegalArgumentException("tag null not allowed");
        } else if (v instanceof Exception) {
            throw new IllegalArgumentException("message " + v + " not allowed");
        } else {
            this.sc.send(new TaggedMessage(tag, v));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object receive(java.lang.Integer r10) throws java.lang.InterruptedException, java.io.IOException, java.lang.ClassNotFoundException {
        /*
        r9 = this;
        r3 = 0;
        r2 = 0;
    L_0x0002:
        r6 = r9.queues;
        monitor-enter(r6);
        r5 = r9.queues;	 Catch:{ all -> 0x002e }
        r5 = r5.get(r10);	 Catch:{ all -> 0x002e }
        r0 = r5;
        r0 = (java.util.concurrent.BlockingQueue) r0;	 Catch:{ all -> 0x002e }
        r3 = r0;
        if (r3 != 0) goto L_0x005a;
    L_0x0011:
        r5 = r9.isRunning;	 Catch:{ all -> 0x002e }
        if (r5 != 0) goto L_0x0031;
    L_0x0015:
        r5 = new java.io.IOException;	 Catch:{ all -> 0x002e }
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x002e }
        r7.<init>();	 Catch:{ all -> 0x002e }
        r8 = "receiver not running for ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x002e }
        r7 = r7.append(r9);	 Catch:{ all -> 0x002e }
        r7 = r7.toString();	 Catch:{ all -> 0x002e }
        r5.<init>(r7);	 Catch:{ all -> 0x002e }
        throw r5;	 Catch:{ all -> 0x002e }
    L_0x002e:
        r5 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x002e }
        throw r5;
    L_0x0031:
        r5 = logger;	 Catch:{ InterruptedException -> 0x0075 }
        r7 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x0075 }
        r7.<init>();	 Catch:{ InterruptedException -> 0x0075 }
        r8 = "receive wait, tag = ";
        r7 = r7.append(r8);	 Catch:{ InterruptedException -> 0x0075 }
        r7 = r7.append(r10);	 Catch:{ InterruptedException -> 0x0075 }
        r7 = r7.toString();	 Catch:{ InterruptedException -> 0x0075 }
        r5.debug(r7);	 Catch:{ InterruptedException -> 0x0075 }
        r5 = r9.blockedCount;	 Catch:{ InterruptedException -> 0x0075 }
        r2 = r5.incrementAndGet();	 Catch:{ InterruptedException -> 0x0075 }
        r5 = r9.queues;	 Catch:{ InterruptedException -> 0x0075 }
        r5.wait();	 Catch:{ InterruptedException -> 0x0075 }
        r5 = r9.blockedCount;	 Catch:{ all -> 0x002e }
        r2 = r5.decrementAndGet();	 Catch:{ all -> 0x002e }
    L_0x005a:
        monitor-exit(r6);	 Catch:{ all -> 0x002e }
        if (r3 == 0) goto L_0x0002;
    L_0x005d:
        r4 = 0;
        r5 = r9.blockedCount;	 Catch:{ all -> 0x00a1 }
        r2 = r5.incrementAndGet();	 Catch:{ all -> 0x00a1 }
        r4 = r3.take();	 Catch:{ all -> 0x00a1 }
        r5 = r9.blockedCount;
        r2 = r5.decrementAndGet();
        r5 = r4 instanceof java.io.IOException;
        if (r5 == 0) goto L_0x00a9;
    L_0x0072:
        r4 = (java.io.IOException) r4;
        throw r4;
    L_0x0075:
        r1 = move-exception;
        r5 = logger;	 Catch:{ all -> 0x0099 }
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0099 }
        r7.<init>();	 Catch:{ all -> 0x0099 }
        r8 = "receive wait exception, tag = ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x0099 }
        r7 = r7.append(r10);	 Catch:{ all -> 0x0099 }
        r8 = ", blockedCount = ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x0099 }
        r7 = r7.append(r2);	 Catch:{ all -> 0x0099 }
        r7 = r7.toString();	 Catch:{ all -> 0x0099 }
        r5.info(r7);	 Catch:{ all -> 0x0099 }
        throw r1;	 Catch:{ all -> 0x0099 }
    L_0x0099:
        r5 = move-exception;
        r7 = r9.blockedCount;	 Catch:{ all -> 0x002e }
        r2 = r7.decrementAndGet();	 Catch:{ all -> 0x002e }
        throw r5;	 Catch:{ all -> 0x002e }
    L_0x00a1:
        r5 = move-exception;
        r6 = r9.blockedCount;
        r2 = r6.decrementAndGet();
        throw r5;
    L_0x00a9:
        r5 = r4 instanceof java.lang.ClassNotFoundException;
        if (r5 == 0) goto L_0x00b0;
    L_0x00ad:
        r4 = (java.lang.ClassNotFoundException) r4;
        throw r4;
    L_0x00b0:
        r5 = r4 instanceof java.lang.Exception;
        if (r5 == 0) goto L_0x00be;
    L_0x00b4:
        r5 = new java.io.IOException;
        r6 = r4.toString();
        r5.<init>(r6);
        throw r5;
    L_0x00be:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.util.TaggedSocketChannel.receive(java.lang.Integer):java.lang.Object");
    }

    public void close() {
        terminate();
    }

    public String toString() {
        return "socketChannel(" + this.sc + ", tags = " + this.queues.keySet() + ")";
    }

    public int tagSize() {
        return this.queues.size();
    }

    public int messages() {
        int m = 0;
        synchronized (this.queues) {
            for (BlockingQueue tq : this.queues.values()) {
                m += tq.size();
            }
        }
        return m;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r14 = this;
        r11 = 0;
        r10 = r14.sc;
        if (r10 != 0) goto L_0x0008;
    L_0x0005:
        r14.isRunning = r11;
    L_0x0007:
        return;
    L_0x0008:
        r10 = 1;
        r14.isRunning = r10;
    L_0x000b:
        r10 = r14.isRunning;
        if (r10 == 0) goto L_0x022c;
    L_0x000f:
        r6 = 0;
        r10 = logger;	 Catch:{ IOException -> 0x00a8, ClassNotFoundException -> 0x00ac, Exception -> 0x00b0 }
        r11 = "waiting for tagged object";
        r10.debug(r11);	 Catch:{ IOException -> 0x00a8, ClassNotFoundException -> 0x00ac, Exception -> 0x00b0 }
        r10 = r14.sc;	 Catch:{ IOException -> 0x00a8, ClassNotFoundException -> 0x00ac, Exception -> 0x00b0 }
        r6 = r10.receive();	 Catch:{ IOException -> 0x00a8, ClassNotFoundException -> 0x00ac, Exception -> 0x00b0 }
        r10 = r14.isInterrupted();	 Catch:{ IOException -> 0x00a8, ClassNotFoundException -> 0x00ac, Exception -> 0x00b0 }
        if (r10 == 0) goto L_0x0026;
    L_0x0023:
        r10 = 0;
        r14.isRunning = r10;	 Catch:{ IOException -> 0x00a8, ClassNotFoundException -> 0x00ac, Exception -> 0x00b0 }
    L_0x0026:
        r10 = logger;	 Catch:{ InterruptedException -> 0x0060 }
        r11 = "object recieved";
        r10.debug(r11);	 Catch:{ InterruptedException -> 0x0060 }
        r10 = r6 instanceof edu.jas.util.TaggedMessage;	 Catch:{ InterruptedException -> 0x0060 }
        if (r10 == 0) goto L_0x00b7;
    L_0x0031:
        r0 = r6;
        r0 = (edu.jas.util.TaggedMessage) r0;	 Catch:{ InterruptedException -> 0x0060 }
        r7 = r0;
        r8 = 0;
        r11 = r14.queues;	 Catch:{ InterruptedException -> 0x0060 }
        monitor-enter(r11);	 Catch:{ InterruptedException -> 0x0060 }
        r10 = r14.queues;	 Catch:{ all -> 0x00b4 }
        r12 = r7.tag;	 Catch:{ all -> 0x00b4 }
        r10 = r10.get(r12);	 Catch:{ all -> 0x00b4 }
        r0 = r10;
        r0 = (java.util.concurrent.BlockingQueue) r0;	 Catch:{ all -> 0x00b4 }
        r8 = r0;
        if (r8 != 0) goto L_0x0059;
    L_0x0047:
        r9 = new java.util.concurrent.LinkedBlockingQueue;	 Catch:{ all -> 0x00b4 }
        r9.<init>();	 Catch:{ all -> 0x00b4 }
        r10 = r14.queues;	 Catch:{ all -> 0x0290 }
        r12 = r7.tag;	 Catch:{ all -> 0x0290 }
        r10.put(r12, r9);	 Catch:{ all -> 0x0290 }
        r10 = r14.queues;	 Catch:{ all -> 0x0290 }
        r10.notifyAll();	 Catch:{ all -> 0x0290 }
        r8 = r9;
    L_0x0059:
        monitor-exit(r11);	 Catch:{ all -> 0x00b4 }
        r10 = r7.msg;	 Catch:{ InterruptedException -> 0x0060 }
        r8.put(r10);	 Catch:{ InterruptedException -> 0x0060 }
        goto L_0x000b;
    L_0x0060:
        r2 = move-exception;
        r10 = debug;
        if (r10 == 0) goto L_0x007d;
    L_0x0065:
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "exception ";
        r11 = r11.append(r12);
        r11 = r11.append(r2);
        r11 = r11.toString();
        r10.debug(r11);
    L_0x007d:
        r11 = r14.queues;
        monitor-enter(r11);
        r10 = 0;
        r14.isRunning = r10;	 Catch:{ all -> 0x0229 }
        r10 = r14.queues;	 Catch:{ all -> 0x0229 }
        r10 = r10.values();	 Catch:{ all -> 0x0229 }
        r4 = r10.iterator();	 Catch:{ all -> 0x0229 }
    L_0x008d:
        r10 = r4.hasNext();	 Catch:{ all -> 0x0229 }
        if (r10 == 0) goto L_0x0221;
    L_0x0093:
        r5 = r4.next();	 Catch:{ all -> 0x0229 }
        r5 = (java.util.concurrent.BlockingQueue) r5;	 Catch:{ all -> 0x0229 }
        r10 = r14.blockedCount;	 Catch:{ InterruptedException -> 0x021e }
        r1 = r10.get();	 Catch:{ InterruptedException -> 0x021e }
        r3 = 0;
    L_0x00a0:
        if (r3 > r1) goto L_0x0202;
    L_0x00a2:
        r5.put(r2);	 Catch:{ InterruptedException -> 0x021e }
        r3 = r3 + 1;
        goto L_0x00a0;
    L_0x00a8:
        r2 = move-exception;
        r6 = r2;
        goto L_0x0026;
    L_0x00ac:
        r2 = move-exception;
        r6 = r2;
        goto L_0x0026;
    L_0x00b0:
        r2 = move-exception;
        r6 = r2;
        goto L_0x0026;
    L_0x00b4:
        r10 = move-exception;
    L_0x00b5:
        monitor-exit(r11);	 Catch:{ all -> 0x00b4 }
        throw r10;	 Catch:{ InterruptedException -> 0x0060 }
    L_0x00b7:
        r10 = r6 instanceof java.lang.Exception;	 Catch:{ InterruptedException -> 0x0060 }
        if (r10 == 0) goto L_0x0128;
    L_0x00bb:
        r10 = debug;	 Catch:{ InterruptedException -> 0x0060 }
        if (r10 == 0) goto L_0x00d7;
    L_0x00bf:
        r10 = logger;	 Catch:{ InterruptedException -> 0x0060 }
        r11 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x0060 }
        r11.<init>();	 Catch:{ InterruptedException -> 0x0060 }
        r12 = "exception ";
        r11 = r11.append(r12);	 Catch:{ InterruptedException -> 0x0060 }
        r11 = r11.append(r6);	 Catch:{ InterruptedException -> 0x0060 }
        r11 = r11.toString();	 Catch:{ InterruptedException -> 0x0060 }
        r10.debug(r11);	 Catch:{ InterruptedException -> 0x0060 }
    L_0x00d7:
        r11 = r14.queues;	 Catch:{ InterruptedException -> 0x0060 }
        monitor-enter(r11);	 Catch:{ InterruptedException -> 0x0060 }
        r10 = 0;
        r14.isRunning = r10;	 Catch:{ all -> 0x011d }
        r10 = r14.queues;	 Catch:{ all -> 0x011d }
        r10 = r10.values();	 Catch:{ all -> 0x011d }
        r4 = r10.iterator();	 Catch:{ all -> 0x011d }
    L_0x00e7:
        r10 = r4.hasNext();	 Catch:{ all -> 0x011d }
        if (r10 == 0) goto L_0x0120;
    L_0x00ed:
        r5 = r4.next();	 Catch:{ all -> 0x011d }
        r5 = (java.util.concurrent.BlockingQueue) r5;	 Catch:{ all -> 0x011d }
        r10 = r14.blockedCount;	 Catch:{ all -> 0x011d }
        r1 = r10.get();	 Catch:{ all -> 0x011d }
        r3 = 0;
    L_0x00fa:
        if (r3 > r1) goto L_0x0102;
    L_0x00fc:
        r5.put(r6);	 Catch:{ all -> 0x011d }
        r3 = r3 + 1;
        goto L_0x00fa;
    L_0x0102:
        if (r1 <= 0) goto L_0x00e7;
    L_0x0104:
        r10 = logger;	 Catch:{ all -> 0x011d }
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011d }
        r12.<init>();	 Catch:{ all -> 0x011d }
        r13 = "put exception to queue, blockedCount = ";
        r12 = r12.append(r13);	 Catch:{ all -> 0x011d }
        r12 = r12.append(r1);	 Catch:{ all -> 0x011d }
        r12 = r12.toString();	 Catch:{ all -> 0x011d }
        r10.debug(r12);	 Catch:{ all -> 0x011d }
        goto L_0x00e7;
    L_0x011d:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x011d }
        throw r10;	 Catch:{ InterruptedException -> 0x0060 }
    L_0x0120:
        r10 = r14.queues;	 Catch:{ all -> 0x011d }
        r10.notifyAll();	 Catch:{ all -> 0x011d }
        monitor-exit(r11);	 Catch:{ all -> 0x011d }
        goto L_0x000b;
    L_0x0128:
        r10 = debug;	 Catch:{ InterruptedException -> 0x0060 }
        if (r10 == 0) goto L_0x0144;
    L_0x012c:
        r10 = logger;	 Catch:{ InterruptedException -> 0x0060 }
        r11 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x0060 }
        r11.<init>();	 Catch:{ InterruptedException -> 0x0060 }
        r12 = "no tagged message and no exception ";
        r11 = r11.append(r12);	 Catch:{ InterruptedException -> 0x0060 }
        r11 = r11.append(r6);	 Catch:{ InterruptedException -> 0x0060 }
        r11 = r11.toString();	 Catch:{ InterruptedException -> 0x0060 }
        r10.debug(r11);	 Catch:{ InterruptedException -> 0x0060 }
    L_0x0144:
        r11 = r14.queues;	 Catch:{ InterruptedException -> 0x0060 }
        monitor-enter(r11);	 Catch:{ InterruptedException -> 0x0060 }
        r10 = 0;
        r14.isRunning = r10;	 Catch:{ all -> 0x01c6 }
        r10 = "TaggedSocketChannel Done";
        r10 = r6.equals(r10);	 Catch:{ all -> 0x01c6 }
        if (r10 == 0) goto L_0x017e;
    L_0x0152:
        r2 = new java.lang.Exception;	 Catch:{ all -> 0x01c6 }
        r10 = "DONE message";
        r2.<init>(r10);	 Catch:{ all -> 0x01c6 }
    L_0x0159:
        r10 = r14.queues;	 Catch:{ all -> 0x01c6 }
        r10 = r10.values();	 Catch:{ all -> 0x01c6 }
        r4 = r10.iterator();	 Catch:{ all -> 0x01c6 }
    L_0x0163:
        r10 = r4.hasNext();	 Catch:{ all -> 0x01c6 }
        if (r10 == 0) goto L_0x01c9;
    L_0x0169:
        r5 = r4.next();	 Catch:{ all -> 0x01c6 }
        r5 = (java.util.concurrent.BlockingQueue) r5;	 Catch:{ all -> 0x01c6 }
        r10 = r14.blockedCount;	 Catch:{ all -> 0x01c6 }
        r1 = r10.get();	 Catch:{ all -> 0x01c6 }
        r3 = 0;
    L_0x0176:
        if (r3 > r1) goto L_0x019d;
    L_0x0178:
        r5.put(r2);	 Catch:{ all -> 0x01c6 }
        r3 = r3 + 1;
        goto L_0x0176;
    L_0x017e:
        r2 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x01c6 }
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01c6 }
        r10.<init>();	 Catch:{ all -> 0x01c6 }
        r12 = "no tagged message and no exception '";
        r10 = r10.append(r12);	 Catch:{ all -> 0x01c6 }
        r10 = r10.append(r6);	 Catch:{ all -> 0x01c6 }
        r12 = "'";
        r10 = r10.append(r12);	 Catch:{ all -> 0x01c6 }
        r10 = r10.toString();	 Catch:{ all -> 0x01c6 }
        r2.<init>(r10);	 Catch:{ all -> 0x01c6 }
        goto L_0x0159;
    L_0x019d:
        if (r1 <= 0) goto L_0x0163;
    L_0x019f:
        r10 = logger;	 Catch:{ all -> 0x01c6 }
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01c6 }
        r12.<init>();	 Catch:{ all -> 0x01c6 }
        r13 = "put '";
        r12 = r12.append(r13);	 Catch:{ all -> 0x01c6 }
        r13 = r2.toString();	 Catch:{ all -> 0x01c6 }
        r12 = r12.append(r13);	 Catch:{ all -> 0x01c6 }
        r13 = "' to queue, blockedCount = ";
        r12 = r12.append(r13);	 Catch:{ all -> 0x01c6 }
        r12 = r12.append(r1);	 Catch:{ all -> 0x01c6 }
        r12 = r12.toString();	 Catch:{ all -> 0x01c6 }
        r10.debug(r12);	 Catch:{ all -> 0x01c6 }
        goto L_0x0163;
    L_0x01c6:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x01c6 }
        throw r10;	 Catch:{ InterruptedException -> 0x0060 }
    L_0x01c9:
        r10 = r14.queues;	 Catch:{ all -> 0x01c6 }
        r10.notifyAll();	 Catch:{ all -> 0x01c6 }
        monitor-exit(r11);	 Catch:{ all -> 0x01c6 }
        r10 = "TaggedSocketChannel Done";
        r10 = r6.equals(r10);	 Catch:{ InterruptedException -> 0x0060 }
        if (r10 == 0) goto L_0x000b;
    L_0x01d7:
        r10 = logger;	 Catch:{ InterruptedException -> 0x0060 }
        r11 = "run terminating by request";
        r10.info(r11);	 Catch:{ InterruptedException -> 0x0060 }
        r10 = r14.sc;	 Catch:{ IOException -> 0x01e7 }
        r11 = "TaggedSocketChannel Done";
        r10.send(r11);	 Catch:{ IOException -> 0x01e7 }
        goto L_0x0007;
    L_0x01e7:
        r2 = move-exception;
        r10 = logger;	 Catch:{ InterruptedException -> 0x0060 }
        r11 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x0060 }
        r11.<init>();	 Catch:{ InterruptedException -> 0x0060 }
        r12 = "send other done failed ";
        r11 = r11.append(r12);	 Catch:{ InterruptedException -> 0x0060 }
        r11 = r11.append(r2);	 Catch:{ InterruptedException -> 0x0060 }
        r11 = r11.toString();	 Catch:{ InterruptedException -> 0x0060 }
        r10.warn(r11);	 Catch:{ InterruptedException -> 0x0060 }
        goto L_0x0007;
    L_0x0202:
        if (r1 <= 0) goto L_0x008d;
    L_0x0204:
        r10 = logger;	 Catch:{ InterruptedException -> 0x021e }
        r12 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x021e }
        r12.<init>();	 Catch:{ InterruptedException -> 0x021e }
        r13 = "put interrupted to queue, blockCount = ";
        r12 = r12.append(r13);	 Catch:{ InterruptedException -> 0x021e }
        r12 = r12.append(r1);	 Catch:{ InterruptedException -> 0x021e }
        r12 = r12.toString();	 Catch:{ InterruptedException -> 0x021e }
        r10.debug(r12);	 Catch:{ InterruptedException -> 0x021e }
        goto L_0x008d;
    L_0x021e:
        r10 = move-exception;
        goto L_0x008d;
    L_0x0221:
        r10 = r14.queues;	 Catch:{ all -> 0x0229 }
        r10.notifyAll();	 Catch:{ all -> 0x0229 }
        monitor-exit(r11);	 Catch:{ all -> 0x0229 }
        goto L_0x000b;
    L_0x0229:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x0229 }
        throw r10;
    L_0x022c:
        r10 = r14.isInterrupted();
        if (r10 == 0) goto L_0x0284;
    L_0x0232:
        r2 = new java.lang.InterruptedException;
        r10 = "terminating via interrupt";
        r2.<init>(r10);
        r11 = r14.queues;
        monitor-enter(r11);
        r10 = r14.queues;	 Catch:{ all -> 0x028d }
        r10 = r10.values();	 Catch:{ all -> 0x028d }
        r4 = r10.iterator();	 Catch:{ all -> 0x028d }
    L_0x0246:
        r10 = r4.hasNext();	 Catch:{ all -> 0x028d }
        if (r10 == 0) goto L_0x027e;
    L_0x024c:
        r5 = r4.next();	 Catch:{ all -> 0x028d }
        r5 = (java.util.concurrent.BlockingQueue) r5;	 Catch:{ all -> 0x028d }
        r10 = r14.blockedCount;	 Catch:{ InterruptedException -> 0x027c }
        r1 = r10.get();	 Catch:{ InterruptedException -> 0x027c }
        r3 = 0;
    L_0x0259:
        if (r3 > r1) goto L_0x0261;
    L_0x025b:
        r5.put(r2);	 Catch:{ InterruptedException -> 0x027c }
        r3 = r3 + 1;
        goto L_0x0259;
    L_0x0261:
        if (r1 <= 0) goto L_0x0246;
    L_0x0263:
        r10 = logger;	 Catch:{ InterruptedException -> 0x027c }
        r12 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x027c }
        r12.<init>();	 Catch:{ InterruptedException -> 0x027c }
        r13 = "put terminating via interrupt to queue, blockCount = ";
        r12 = r12.append(r13);	 Catch:{ InterruptedException -> 0x027c }
        r12 = r12.append(r1);	 Catch:{ InterruptedException -> 0x027c }
        r12 = r12.toString();	 Catch:{ InterruptedException -> 0x027c }
        r10.debug(r12);	 Catch:{ InterruptedException -> 0x027c }
        goto L_0x0246;
    L_0x027c:
        r10 = move-exception;
        goto L_0x0246;
    L_0x027e:
        r10 = r14.queues;	 Catch:{ all -> 0x028d }
        r10.notifyAll();	 Catch:{ all -> 0x028d }
        monitor-exit(r11);	 Catch:{ all -> 0x028d }
    L_0x0284:
        r10 = logger;
        r11 = "run terminated";
        r10.info(r11);
        goto L_0x0007;
    L_0x028d:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x028d }
        throw r10;
    L_0x0290:
        r10 = move-exception;
        r8 = r9;
        goto L_0x00b5;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.util.TaggedSocketChannel.run():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void terminate() {
        /*
        r10 = this;
        r6 = 0;
        r10.isRunning = r6;
        r10.interrupt();
        r6 = r10.sc;
        if (r6 == 0) goto L_0x002b;
    L_0x000a:
        r6 = r10.sc;	 Catch:{ IOException -> 0x0093 }
        r7 = "TaggedSocketChannel Done";
        r6.send(r7);	 Catch:{ IOException -> 0x0093 }
    L_0x0011:
        r6 = logger;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r10.sc;
        r7 = r7.append(r8);
        r8 = " not yet closed";
        r7 = r7.append(r8);
        r7 = r7.toString();
        r6.debug(r7);
    L_0x002b:
        r10.interrupt();
        r7 = r10.queues;
        monitor-enter(r7);
        r6 = 0;
        r10.isRunning = r6;	 Catch:{ all -> 0x00d9 }
        r6 = r10.queues;	 Catch:{ all -> 0x00d9 }
        r6 = r6.entrySet();	 Catch:{ all -> 0x00d9 }
        r3 = r6.iterator();	 Catch:{ all -> 0x00d9 }
    L_0x003e:
        r6 = r3.hasNext();	 Catch:{ all -> 0x00d9 }
        if (r6 == 0) goto L_0x00dc;
    L_0x0044:
        r5 = r3.next();	 Catch:{ all -> 0x00d9 }
        r5 = (java.util.Map.Entry) r5;	 Catch:{ all -> 0x00d9 }
        r4 = r5.getValue();	 Catch:{ all -> 0x00d9 }
        r4 = (java.util.concurrent.BlockingQueue) r4;	 Catch:{ all -> 0x00d9 }
        r6 = r4.size();	 Catch:{ all -> 0x00d9 }
        if (r6 == 0) goto L_0x007c;
    L_0x0056:
        r6 = logger;	 Catch:{ all -> 0x00d9 }
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00d9 }
        r8.<init>();	 Catch:{ all -> 0x00d9 }
        r9 = "queue for tag ";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00d9 }
        r9 = r5.getKey();	 Catch:{ all -> 0x00d9 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x00d9 }
        r9 = " not empty ";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00d9 }
        r8 = r8.append(r4);	 Catch:{ all -> 0x00d9 }
        r8 = r8.toString();	 Catch:{ all -> 0x00d9 }
        r6.info(r8);	 Catch:{ all -> 0x00d9 }
    L_0x007c:
        r0 = 0;
        r6 = r10.blockedCount;	 Catch:{ InterruptedException -> 0x00ae }
        r0 = r6.get();	 Catch:{ InterruptedException -> 0x00ae }
        r2 = 0;
    L_0x0084:
        if (r2 > r0) goto L_0x00af;
    L_0x0086:
        r6 = new java.io.IOException;	 Catch:{ InterruptedException -> 0x00ae }
        r8 = "queue terminate";
        r6.<init>(r8);	 Catch:{ InterruptedException -> 0x00ae }
        r4.put(r6);	 Catch:{ InterruptedException -> 0x00ae }
        r2 = r2 + 1;
        goto L_0x0084;
    L_0x0093:
        r1 = move-exception;
        r6 = logger;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "send done failed ";
        r7 = r7.append(r8);
        r7 = r7.append(r1);
        r7 = r7.toString();
        r6.warn(r7);
        goto L_0x0011;
    L_0x00ae:
        r6 = move-exception;
    L_0x00af:
        if (r0 <= 0) goto L_0x003e;
    L_0x00b1:
        r6 = logger;	 Catch:{ all -> 0x00d9 }
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00d9 }
        r8.<init>();	 Catch:{ all -> 0x00d9 }
        r9 = "put IO-end to queue for tag ";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00d9 }
        r9 = r5.getKey();	 Catch:{ all -> 0x00d9 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x00d9 }
        r9 = ", blockCount = ";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00d9 }
        r8 = r8.append(r0);	 Catch:{ all -> 0x00d9 }
        r8 = r8.toString();	 Catch:{ all -> 0x00d9 }
        r6.debug(r8);	 Catch:{ all -> 0x00d9 }
        goto L_0x003e;
    L_0x00d9:
        r6 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x00d9 }
        throw r6;
    L_0x00dc:
        r6 = r10.queues;	 Catch:{ all -> 0x00d9 }
        r6.notifyAll();	 Catch:{ all -> 0x00d9 }
        monitor-exit(r7);	 Catch:{ all -> 0x00d9 }
        r10.join();	 Catch:{ InterruptedException -> 0x00ed }
    L_0x00e5:
        r6 = logger;
        r7 = "terminated";
        r6.info(r7);
        return;
    L_0x00ed:
        r6 = move-exception;
        goto L_0x00e5;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.util.TaggedSocketChannel.terminate():void");
    }
}
