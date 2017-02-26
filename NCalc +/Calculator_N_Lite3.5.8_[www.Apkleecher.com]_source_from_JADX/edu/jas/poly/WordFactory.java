package edu.jas.poly;

import edu.jas.kern.StringUtil;
import edu.jas.structure.MonoidFactory;
import io.github.kexanie.library.BuildConfig;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public final class WordFactory implements MonoidFactory<Word> {
    private static final WordComparator horder;
    private static final Logger logger;
    private static final WordComparator lorder;
    private static final Random random;
    public static final String transRef = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public final Word ONE;
    final String alphabet;
    public final String[] translation;

    public static abstract class WordComparator implements Comparator<Word>, Serializable {
        public abstract int compare(Word word, Word word2);
    }

    static class 1 extends WordComparator {
        1() {
        }

        public int compare(Word e1, Word e2) {
            return e1.gradInvlexCompareTo(e2);
        }
    }

    static class 2 extends WordComparator {
        2() {
        }

        public int compare(Word e1, Word e2) {
            return -e1.gradInvlexCompareTo(e2);
        }
    }

    static {
        random = new Random();
        logger = Logger.getLogger(WordFactory.class);
        horder = new 1();
        lorder = new 2();
    }

    public WordFactory() {
        this(BuildConfig.FLAVOR);
    }

    public WordFactory(String s) {
        if (s == null) {
            throw new IllegalArgumentException("null string not allowed");
        }
        this.alphabet = cleanSpace(s);
        this.translation = null;
        this.ONE = new Word(this, BuildConfig.FLAVOR, false);
    }

    public WordFactory(String[] S) {
        String[] V = cleanAll(S);
        if (isSingleLetters(V)) {
            this.alphabet = concat(V);
            this.translation = null;
        } else {
            this.alphabet = transRef.substring(0, V.length);
            this.translation = V;
            logger.info("alphabet = " + this.alphabet + ", translation = " + Arrays.toString(this.translation));
        }
        this.ONE = new Word(this, BuildConfig.FLAVOR, false);
    }

    public boolean isFinite() {
        if (this.alphabet.length() == 0) {
            return true;
        }
        return false;
    }

    public boolean isCommutative() {
        if (this.alphabet.length() == 0) {
            return true;
        }
        return false;
    }

    public boolean isAssociative() {
        return true;
    }

    public Word getONE() {
        return this.ONE;
    }

    public Word copy(Word w) {
        return new Word(this, w.getVal(), false);
    }

    public int length() {
        return this.alphabet.length();
    }

    String getVal() {
        return this.alphabet;
    }

    String[] getTrans() {
        return this.translation;
    }

    public char getVal(int i) {
        return this.alphabet.charAt(i);
    }

    public String[] getVars() {
        String[] vars = new String[this.alphabet.length()];
        int i;
        if (this.translation == null) {
            for (i = 0; i < this.alphabet.length(); i++) {
                vars[i] = String.valueOf(getVal(i));
            }
        } else {
            for (i = 0; i < this.alphabet.length(); i++) {
                vars[i] = this.translation[i];
            }
        }
        return vars;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("\"");
        int i;
        if (this.translation == null) {
            for (i = 0; i < this.alphabet.length(); i++) {
                if (i != 0) {
                    s.append(",");
                }
                s.append(getVal(i));
            }
        } else {
            for (i = 0; i < this.alphabet.length(); i++) {
                if (i != 0) {
                    s.append(",");
                }
                s.append(this.translation[i]);
            }
        }
        s.append("\"");
        return s.toString();
    }

    public String toScript() {
        return toString();
    }

    public boolean equals(Object B) {
        if (!(B instanceof WordFactory)) {
            return false;
        }
        return this.alphabet.equals(((WordFactory) B).alphabet);
    }

    public int hashCode() {
        return this.alphabet.hashCode();
    }

    public List<Word> generators() {
        int len = this.alphabet.length();
        List<Word> gens = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            gens.add(new Word(this, String.valueOf(this.alphabet.charAt(i)), false));
        }
        return gens;
    }

    public Word fromInteger(long a) {
        throw new UnsupportedOperationException("not implemented for WordFactory");
    }

    public Word fromInteger(BigInteger a) {
        throw new UnsupportedOperationException("not implemented for WordFactory");
    }

    public Word valueOf(ExpVector e) {
        Word w = this.ONE;
        List<Word> gens = generators();
        int n = this.alphabet.length();
        int m = e.length();
        if (m > n) {
            throw new IllegalArgumentException("alphabet to short for exponent " + e + ", alpahbet = " + this.alphabet);
        }
        for (int i = 0; i < m; i++) {
            int x = (int) e.getVal((m - i) - 1);
            Word y = (Word) gens.get(i);
            Word u = this.ONE;
            for (int j = 0; j < x; j++) {
                u = u.multiply(y);
            }
            w = w.multiply(u);
        }
        return w;
    }

    public int indexOf(char s) {
        return this.alphabet.indexOf(s);
    }

    public Word random(int n) {
        return random(n, random);
    }

    public Word random(int n, Random random) {
        StringBuffer sb = new StringBuffer();
        int len = this.alphabet.length();
        for (int i = 0; i < n; i++) {
            sb.append(this.alphabet.charAt(Math.abs(random.nextInt() % len)));
        }
        return new Word(this, sb.toString(), false);
    }

    public Word parse(String s) {
        String regex;
        String st = clean(s);
        if (this.translation == null) {
            regex = "[" + this.alphabet + " ]*";
        } else {
            regex = "[" + concat(this.translation) + " ]*";
        }
        if (st.matches(regex)) {
            return new Word(this, st, true);
        }
        throw new IllegalArgumentException("word '" + st + "' contains letters not from: " + this.alphabet + " or from " + concat(this.translation));
    }

    public Word parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public WordComparator getDescendComparator() {
        return horder;
    }

    public WordComparator getAscendComparator() {
        return lorder;
    }

    public static String cleanSpace(String s) {
        return s.trim().replaceAll("\\*", BuildConfig.FLAVOR).replaceAll("\\s", BuildConfig.FLAVOR).replaceAll("\\(", BuildConfig.FLAVOR).replaceAll("\\)", BuildConfig.FLAVOR).replaceAll("\\\"", BuildConfig.FLAVOR);
    }

    public static String clean(String s) {
        return s.trim().replaceAll("\\*", " ").replaceAll("\\(", BuildConfig.FLAVOR).replaceAll("\\)", BuildConfig.FLAVOR).replaceAll("\\\"", BuildConfig.FLAVOR);
    }

    public static String[] cleanAll(String[] v) {
        String[] t = new String[v.length];
        for (int i = 0; i < v.length; i++) {
            t[i] = cleanSpace(v[i]);
            if (t[i].length() == 0) {
                logger.error("empty v[i]: '" + v[i] + "'");
            }
        }
        return t;
    }

    public static String concat(String[] v) {
        StringBuffer s = new StringBuffer();
        if (v == null) {
            return s.toString();
        }
        for (String append : v) {
            s.append(append);
        }
        return s.toString();
    }

    public static String[] trimAll(String[] v) {
        String[] t = new String[v.length];
        for (int i = 0; i < v.length; i++) {
            t[i] = v[i].trim();
            if (t[i].length() == 0) {
                logger.error("empty v[i]: '" + v[i] + "'");
            }
        }
        return t;
    }

    public static int indexOf(String[] v, String s) {
        for (int i = 0; i < v.length; i++) {
            if (s.equals(v[i])) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isSingleLetters(String[] v) {
        for (String length : v) {
            if (length.length() != 1) {
                return false;
            }
        }
        return true;
    }

    public String translate(String[] v) {
        StringBuffer s = new StringBuffer();
        for (String a : v) {
            int k = indexOf(this.translation, a);
            if (k < 0) {
                System.out.println("t = " + Arrays.toString(this.translation));
                System.out.println("v = " + Arrays.toString(v));
                logger.error("v[i] not found in t: " + a);
                throw new IllegalArgumentException("v[i] not found in t: " + a);
            }
            s.append(transRef.charAt(k));
        }
        return s.toString();
    }

    public String transVar(char c) {
        return this.translation[this.alphabet.indexOf(c)];
    }
}
