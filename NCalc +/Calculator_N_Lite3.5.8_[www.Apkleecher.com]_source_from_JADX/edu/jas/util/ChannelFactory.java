package edu.jas.util;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;

public class ChannelFactory extends Thread {
    public static final int DEFAULT_PORT = 4711;
    private static final Logger logger;
    private final BlockingQueue<SocketChannel> buf;
    private final boolean debug;
    private final int port;
    private volatile ServerSocket srv;
    private volatile boolean srvrun;
    private volatile boolean srvstart;

    static {
        logger = Logger.getLogger(ChannelFactory.class);
    }

    public ChannelFactory() {
        this(DEFAULT_PORT);
    }

    public ChannelFactory(int p) {
        this.debug = logger.isDebugEnabled();
        this.srvrun = false;
        this.srvstart = false;
        this.buf = new LinkedBlockingQueue();
        if (p <= 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = p;
        }
        try {
            this.srv = new ServerSocket(this.port);
            logger.info("server bound to port " + this.port);
        } catch (BindException e) {
            this.srv = null;
            logger.warn("server not started, port used " + this.port);
            if (this.debug) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            logger.debug("IOException " + e2);
            if (logger.isDebugEnabled()) {
                e2.printStackTrace();
            }
        }
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.srv + ", buf = " + this.buf.size() + ")";
    }

    public void init() {
        if (this.srv != null && !this.srvstart) {
            start();
            this.srvstart = true;
            logger.info("ChannelFactory at " + this.srv);
        }
    }

    public SocketChannel getChannel() throws InterruptedException {
        if (this.srv == null) {
            if (this.srvrun) {
                throw new IllegalArgumentException("dont call when no server listens");
            }
        } else if (!this.srvstart) {
            init();
        }
        return (SocketChannel) this.buf.take();
    }

    public SocketChannel getChannel(String h) throws IOException {
        return getChannel(h, DEFAULT_PORT);
    }

    public SocketChannel getChannel(String h, int p) throws IOException {
        if (p <= 0) {
            p = this.port;
        }
        int i = 0;
        int delay = 5;
        logger.debug("connecting to " + h);
        SocketChannel c = null;
        while (c == null) {
            try {
                c = new SocketChannel(new Socket(h, p));
            } catch (IOException e) {
                i++;
                if (i % 50 == 0) {
                    delay += delay;
                    logger.info("Server on " + h + ":" + p + " not ready in " + delay + "ms");
                }
                Thread.sleep((long) delay);
                if (i % 50 == 0 && this.debug) {
                    throw new Exception("time wait, host = " + h + ", port = " + this.port);
                }
            } catch (InterruptedException w) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted during IO wait " + w);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        logger.debug("connected, iter = " + i);
        return c;
    }

    public void run() {
        if (this.srv != null) {
            Socket s;
            this.srvrun = true;
            while (true) {
                try {
                    logger.info("waiting for connection on " + this.srv);
                    s = this.srv.accept();
                    if (isInterrupted()) {
                        break;
                    }
                    logger.debug("connection accepted");
                    this.buf.put(new SocketChannel(s));
                } catch (IOException e) {
                    this.srvrun = false;
                    return;
                } catch (InterruptedException e2) {
                    this.srvrun = false;
                    return;
                }
            }
            this.srvrun = false;
            if (s != null) {
                s.close();
            }
        }
    }

    public void terminate() {
        if (this.srvstart) {
            interrupt();
            try {
                if (this.srv != null) {
                    this.srv.close();
                    this.srvrun = false;
                }
                interrupt();
                while (!this.buf.isEmpty()) {
                    logger.debug("closing unused SocketChannel");
                    SocketChannel c = (SocketChannel) this.buf.poll();
                    if (c != null) {
                        c.close();
                    }
                }
            } catch (IOException e) {
            }
            try {
                join();
            } catch (InterruptedException e2) {
            }
            logger.debug("ChannelFactory terminated");
            return;
        }
        logger.debug("server not started");
    }
}
