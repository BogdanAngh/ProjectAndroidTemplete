package edu.jas.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketChannel {
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Socket soc;

    public SocketChannel(Socket s) throws IOException {
        this.soc = s;
        this.out = new ObjectOutputStream(s.getOutputStream());
        this.out.flush();
        this.in = new ObjectInputStream(s.getInputStream());
    }

    public Socket getSocket() {
        return this.soc;
    }

    public void send(Object v) throws IOException {
        synchronized (this.out) {
            this.out.writeObject(v);
            this.out.flush();
        }
    }

    public Object receive() throws IOException, ClassNotFoundException {
        Object v;
        synchronized (this.in) {
            v = this.in.readObject();
        }
        return v;
    }

    public void close() {
        if (this.in != null) {
            try {
                this.in.close();
            } catch (IOException e) {
            }
        }
        if (this.out != null) {
            try {
                this.out.close();
            } catch (IOException e2) {
            }
        }
        if (this.soc != null) {
            try {
                this.soc.close();
            } catch (IOException e3) {
            }
        }
    }

    public String toString() {
        return "socketChannel(" + this.soc + ")";
    }
}
