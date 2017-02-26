package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class Material extends Attributes {
    private static int counter;
    public String id;

    static {
        counter = 0;
    }

    public Material() {
        StringBuilder append = new StringBuilder().append("mtl");
        int i = counter + 1;
        counter = i;
        this(append.append(i).toString());
    }

    public Material(String id) {
        this.id = id;
    }

    public Material(Attribute... attributes) {
        this();
        set(attributes);
    }

    public Material(String id, Attribute... attributes) {
        this(id);
        set(attributes);
    }

    public Material(Array<Attribute> attributes) {
        this();
        set((Iterable) attributes);
    }

    public Material(String id, Array<Attribute> attributes) {
        this(id);
        set((Iterable) attributes);
    }

    public Material(Material copyFrom) {
        this(copyFrom.id, copyFrom);
    }

    public Material(String id, Material copyFrom) {
        this(id);
        Iterator i$ = copyFrom.iterator();
        while (i$.hasNext()) {
            set(((Attribute) i$.next()).copy());
        }
    }

    public final Material copy() {
        return new Material(this);
    }

    public final boolean equals(Material other) {
        return same(other, true) && this.id.equals(other.id);
    }

    public final boolean equals(Object obj) {
        return obj instanceof Material ? equals((Material) obj) : false;
    }
}
