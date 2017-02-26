package edu.jas.kern;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

public class StringUtil {
    public static String nextString(Reader r) {
        char buffer;
        int i;
        StringWriter sw = new StringWriter();
        do {
            try {
                i = r.read();
                if (i <= -1) {
                    break;
                }
                buffer = (char) i;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (Character.isWhitespace(buffer));
        sw.write(buffer);
        while (true) {
            i = r.read();
            if (i <= -1) {
                break;
            }
            buffer = (char) i;
            if (Character.isWhitespace(buffer)) {
                break;
            }
            sw.write(buffer);
        }
        return sw.toString();
    }

    public static String nextString(Reader r, char c) {
        StringWriter sw = new StringWriter();
        while (true) {
            int i = r.read();
            if (i <= -1) {
                break;
            }
            char buffer = (char) i;
            if (buffer == c) {
                break;
            }
            try {
                sw.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sw.toString();
    }

    public static String nextPairedString(Reader r, char b, char c) {
        StringWriter sw = new StringWriter();
        int level = 0;
        while (true) {
            try {
                int i = r.read();
                if (i <= -1) {
                    break;
                }
                char buffer = (char) i;
                if (buffer == b) {
                    level++;
                }
                if (buffer == c) {
                    level--;
                    if (level < 0) {
                        break;
                    }
                }
                sw.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sw.toString();
    }

    public static String selectStackTrace(String expr) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stack.length; i++) {
            String s = stack[i].toString();
            if (s.indexOf("selectStackTrace") < 0 && s.matches(expr)) {
                sb.append("\nstack[" + i + "] = ");
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
