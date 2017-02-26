package edu.jas.poly;

import edu.jas.structure.MonoidElem;
import edu.jas.structure.MonoidFactory;
import edu.jas.structure.NotInvertibleException;
import io.github.kexanie.library.BuildConfig;
import java.util.SortedMap;
import java.util.TreeMap;

public final class Word implements MonoidElem<Word> {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected int hash;
    public final WordFactory mono;
    final String val;

    static {
        $assertionsDisabled = !Word.class.desiredAssertionStatus();
    }

    public Word(WordFactory m) {
        this(m, BuildConfig.FLAVOR);
    }

    public Word(WordFactory m, String s) {
        this(m, s, true);
    }

    public Word(WordFactory m, String s, boolean translate) {
        this.hash = 0;
        this.mono = m;
        this.hash = 0;
        if (s == null) {
            throw new IllegalArgumentException("null string not allowed");
        } else if (!translate) {
            this.val = s;
        } else if (this.mono.translation != null) {
            this.val = this.mono.translate(GenPolynomialTokenizer.variableList(s));
        } else {
            this.val = WordFactory.cleanSpace(s);
        }
    }

    public MonoidFactory<Word> factory() {
        return this.mono;
    }

    public Word copy() {
        return new Word(this.mono, this.val, false);
    }

    String getVal() {
        return this.val;
    }

    public char getVal(int i) {
        return this.val.charAt(i);
    }

    public int length() {
        return this.val.length();
    }

    public String toString() {
        if (this.val.length() == 0) {
            return BuildConfig.FLAVOR;
        }
        StringBuffer s = new StringBuffer("\"");
        int i;
        if (this.mono.translation == null) {
            for (i = 0; i < length(); i++) {
                if (i != 0) {
                    s.append(" ");
                }
                s.append(getVal(i));
            }
        } else {
            for (i = 0; i < length(); i++) {
                if (i != 0) {
                    s.append(" ");
                }
                s.append(this.mono.transVar(getVal(i)));
            }
        }
        s.append("\"");
        return s.toString();
    }

    public String toScript() {
        if (this.val.length() == 0) {
            return BuildConfig.FLAVOR;
        }
        StringBuffer s = new StringBuffer(BuildConfig.FLAVOR);
        int i;
        if (this.mono.translation == null) {
            for (i = 0; i < length(); i++) {
                if (i != 0) {
                    s.append("*");
                }
                s.append(getVal(i));
            }
        } else {
            for (i = 0; i < length(); i++) {
                if (i != 0) {
                    s.append("*");
                }
                s.append(this.mono.transVar(getVal(i)));
            }
        }
        s.append(BuildConfig.FLAVOR);
        return s.toString();
    }

    public String toScriptFactory() {
        return this.mono.toString();
    }

    public boolean equals(Object B) {
        if ((B instanceof Word) && compareTo((Word) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash = this.val.hashCode();
        }
        return this.hash;
    }

    public boolean isONE() {
        return this.val.length() == 0;
    }

    public boolean isUnit() {
        return isONE();
    }

    public Word multiply(Word V) {
        return new Word(this.mono, this.val + V.val, false);
    }

    public Word divide(Word V) {
        Word[] ret = divideWord(V);
        if (ret[0].isONE() || ret[1].isONE()) {
            return ret[0].multiply(ret[1]);
        }
        throw new IllegalArgumentException("not simple dividable: left = " + ret[0] + ", right = " + ret[1] + ", use divideWord");
    }

    public Word[] divideWord(Word V) {
        int i = this.val.indexOf(V.val);
        if (i < 0) {
            throw new NotInvertibleException("not dividable: " + this + ", other " + V);
        }
        int len = V.val.length();
        String pre = this.val.substring(0, i);
        String suf = this.val.substring(i + len);
        return new Word[]{new Word(this.mono, pre, false), new Word(this.mono, suf, false)};
    }

    public Word remainder(Word V) {
        if (this.val.indexOf(V.val) >= 0) {
            return V;
        }
        throw new NotInvertibleException("not dividable: " + this + ", other " + V);
    }

    public Word[] quotientRemainder(Word S) {
        return new Word[]{divide(S), remainder(S)};
    }

    public Word inverse() {
        if (this.val.length() == 0) {
            return this;
        }
        throw new NotInvertibleException("not inversible " + this);
    }

    public int signum() {
        int i = this.val.length();
        if (i > 0) {
            i = 1;
        }
        if ($assertionsDisabled || i >= 0) {
            return i;
        }
        throw new AssertionError();
    }

    public long degree() {
        return (long) this.val.length();
    }

    public SortedMap<String, Integer> dependencyOnVariables() {
        SortedMap<String, Integer> map = new TreeMap();
        for (int i = 0; i < this.val.length(); i++) {
            String s = String.valueOf(this.val.charAt(i));
            Integer n = (Integer) map.get(s);
            if (n == null) {
                n = Integer.valueOf(0);
            }
            map.put(s, Integer.valueOf(n.intValue() + 1));
        }
        return map;
    }

    public ExpVector leadingExpVector() {
        long n = 0;
        char letter = Letters.SPACE;
        for (int i = 0; i < this.val.length(); i++) {
            char s = this.val.charAt(i);
            if (n != 0) {
                if (letter != s) {
                    break;
                }
                n++;
            } else {
                letter = s;
                n++;
            }
        }
        int k = this.mono.length();
        if (n == 0) {
            return ExpVector.create(k);
        }
        return ExpVector.create(k, (k - this.mono.indexOf(letter)) - 1, n);
    }

    public Word reductum() {
        if (isONE()) {
            return this;
        }
        int n = 0;
        char letter = Letters.SPACE;
        for (int i = 0; i < this.val.length(); i++) {
            char s = this.val.charAt(i);
            if (n != 0) {
                if (letter != s) {
                    break;
                }
                n++;
            } else {
                letter = s;
                n++;
            }
        }
        return new Word(this.mono, this.val.substring(n), false);
    }

    public boolean multipleOf(Word V) {
        return this.val.contains(V.val);
    }

    public boolean divides(Word V) {
        return V.val.contains(this.val);
    }

    public int compareTo(Word V) {
        return this.val.compareTo(V.val);
    }

    public int gradCompareTo(Word V) {
        long e = degree();
        long f = V.degree();
        if (e < f) {
            return 1;
        }
        if (e > f) {
            return -1;
        }
        return compareTo(V);
    }

    public int gradInvlexCompareTo(Word V) {
        long e = degree();
        long f = V.degree();
        if (e < f) {
            return 1;
        }
        if (e > f) {
            return -1;
        }
        return -compareTo(V);
    }

    public boolean isOverlap(Overlap ol, Word V) {
        return ol.isOverlap(this, V);
    }

    public OverlapList overlap(Word V) {
        OverlapList ret = new OverlapList();
        Word wone = this.mono.getONE();
        String a = this.val;
        String b = V.val;
        int ai = a.length();
        int bi = b.length();
        int j = b.indexOf(a);
        String pre;
        String suf;
        if (j >= 0) {
            while (j >= 0) {
                pre = b.substring(0, j);
                suf = b.substring(j + ai);
                ret.add(new Overlap(new Word(this.mono, pre, false), new Word(this.mono, suf, false), wone, wone));
                j = b.indexOf(a, j + ai);
            }
        } else {
            j = a.indexOf(b);
            if (j >= 0) {
                while (j >= 0) {
                    pre = a.substring(0, j);
                    suf = a.substring(j + bi);
                    ret.add(new Overlap(wone, wone, new Word(this.mono, pre, false), new Word(this.mono, suf, false)));
                    j = a.indexOf(b, j + bi);
                }
            } else if (ai >= bi) {
                for (i = 0; i < bi; i++) {
                    if (a.substring(0, i + 1).equals(b.substring((bi - i) - 1, bi))) {
                        ret.add(new Overlap(new Word(this.mono, b.substring(0, (bi - i) - 1), false), wone, wone, new Word(this.mono, a.substring(i + 1), false)));
                        break;
                    }
                }
                for (i = 0; i < bi; i++) {
                    if (a.substring((ai - i) - 1, ai).equals(b.substring(0, i + 1))) {
                        ret.add(new Overlap(wone, new Word(this.mono, b.substring(i + 1), false), new Word(this.mono, a.substring(0, (ai - i) - 1), false), wone));
                        break;
                    }
                }
            } else {
                for (i = 0; i < ai; i++) {
                    if (a.substring((ai - i) - 1, ai).equals(b.substring(0, i + 1))) {
                        ret.add(new Overlap(wone, new Word(this.mono, b.substring(i + 1), false), new Word(this.mono, a.substring(0, (ai - i) - 1), false), wone));
                        break;
                    }
                }
                for (i = 0; i < ai; i++) {
                    if (a.substring(0, i + 1).equals(b.substring((bi - i) - 1, bi))) {
                        ret.add(new Overlap(new Word(this.mono, b.substring(0, (bi - i) - 1), false), wone, wone, new Word(this.mono, a.substring(i + 1), false)));
                        break;
                    }
                }
            }
        }
        return ret;
    }

    public Word lcm(Word V) {
        OverlapList oll = overlap(V);
        if (oll.ols.isEmpty()) {
            return null;
        }
        Overlap ol = (Overlap) oll.ols.get(0);
        return ol.l1.multiply(this).multiply(ol.r1);
    }
}
