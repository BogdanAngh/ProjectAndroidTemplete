package edu.jas.util;

import java.io.Serializable;

/* compiled from: TaggedSocketChannel */
class TaggedMessage implements Serializable {
    public final Object msg;
    public final Integer tag;

    public TaggedMessage(Integer tag, Object msg) {
        this.tag = tag;
        this.msg = msg;
    }
}
