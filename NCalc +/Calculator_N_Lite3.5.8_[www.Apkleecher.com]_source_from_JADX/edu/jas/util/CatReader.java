package edu.jas.util;

import java.io.IOException;
import java.io.Reader;

public class CatReader extends Reader {
    private boolean doFirst;
    private final Reader first;
    private final Reader second;

    public CatReader(Reader f, Reader s) {
        this.first = f;
        this.second = s;
        this.doFirst = true;
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        if (!this.doFirst) {
            return this.second.read(cbuf, off, len);
        }
        int i = this.first.read(cbuf, off, len);
        if (i >= 0) {
            return i;
        }
        this.doFirst = false;
        return this.second.read(cbuf, off, len);
    }

    public void close() throws IOException {
        try {
            this.first.close();
        } finally {
            this.second.close();
        }
    }
}
