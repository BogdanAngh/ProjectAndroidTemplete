package edu.hws.jcm.data;

import java.io.Serializable;
import java.util.Hashtable;

public class SymbolTable implements Serializable {
    private SymbolTable parent;
    private Hashtable symbols;

    SymbolTable() {
        this(null);
    }

    SymbolTable(SymbolTable parent) {
        this.parent = parent;
        this.symbols = new Hashtable();
    }

    SymbolTable getParent() {
        return this.parent;
    }

    public synchronized MathObject get(String name) {
        MathObject mathObject;
        if (name == null) {
            mathObject = null;
        } else {
            Object sym = this.symbols.get(name);
            mathObject = sym != null ? (MathObject) sym : this.parent != null ? this.parent.get(name) : null;
        }
        return mathObject;
    }

    public synchronized void add(MathObject sym) {
        if (sym == null) {
            throw new NullPointerException("Can't put a null symbol in SymbolTable.");
        }
        add(sym.getName(), sym);
    }

    public synchronized void add(String name, MathObject sym) {
        if (sym == null) {
            throw new NullPointerException("Can't put a null symbol in SymbolTable.");
        } else if (name == null) {
            throw new NullPointerException("Can't put unnamed MathObject in SymbolTable.");
        } else {
            this.symbols.put(name, sym);
        }
    }

    public synchronized void remove(String name) {
        if (name != null) {
            this.symbols.remove(name);
        }
    }
}
