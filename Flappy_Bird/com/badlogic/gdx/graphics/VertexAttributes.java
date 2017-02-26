package com.badlogic.gdx.graphics;

public final class VertexAttributes {
    private final VertexAttribute[] attributes;
    private long mask;
    public final int vertexSize;

    public static final class Usage {
        public static final int BiNormal = 256;
        public static final int BoneWeight = 64;
        public static final int Color = 2;
        public static final int ColorPacked = 4;
        public static final int Generic = 32;
        public static final int Normal = 8;
        public static final int Position = 1;
        public static final int Tangent = 128;
        public static final int TextureCoordinates = 16;
    }

    public VertexAttributes(VertexAttribute... attributes) {
        this.mask = -1;
        if (attributes.length == 0) {
            throw new IllegalArgumentException("attributes must be >= 1");
        }
        VertexAttribute[] list = new VertexAttribute[attributes.length];
        for (int i = 0; i < attributes.length; i++) {
            list[i] = attributes[i];
        }
        this.attributes = list;
        checkValidity();
        this.vertexSize = calculateOffsets();
    }

    public int getOffset(int usage) {
        VertexAttribute vertexAttribute = findByUsage(usage);
        if (vertexAttribute == null) {
            return 0;
        }
        return vertexAttribute.offset / 4;
    }

    public VertexAttribute findByUsage(int usage) {
        int len = size();
        for (int i = 0; i < len; i++) {
            if (get(i).usage == usage) {
                return get(i);
            }
        }
        return null;
    }

    private int calculateOffsets() {
        int count = 0;
        for (VertexAttribute attribute : this.attributes) {
            attribute.offset = count;
            if (attribute.usage == 4) {
                count += 4;
            } else {
                count += attribute.numComponents * 4;
            }
        }
        return count;
    }

    private void checkValidity() {
        boolean pos = false;
        boolean cols = false;
        for (VertexAttribute attribute : this.attributes) {
            if (attribute.usage == 1) {
                if (pos) {
                    throw new IllegalArgumentException("two position attributes were specified");
                }
                pos = true;
            }
            if (attribute.usage == 8 && false) {
                throw new IllegalArgumentException("two normal attributes were specified");
            }
            if (attribute.usage == 2 || attribute.usage == 4) {
                if (attribute.numComponents != 4) {
                    throw new IllegalArgumentException("color attribute must have 4 components");
                } else if (cols) {
                    throw new IllegalArgumentException("two color attributes were specified");
                } else {
                    cols = true;
                }
            }
        }
        if (!pos) {
            throw new IllegalArgumentException("no position attribute was specified");
        }
    }

    public int size() {
        return this.attributes.length;
    }

    public VertexAttribute get(int index) {
        return this.attributes[index];
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < this.attributes.length; i++) {
            builder.append("(");
            builder.append(this.attributes[i].alias);
            builder.append(", ");
            builder.append(this.attributes[i].usage);
            builder.append(", ");
            builder.append(this.attributes[i].numComponents);
            builder.append(", ");
            builder.append(this.attributes[i].offset);
            builder.append(")");
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VertexAttributes)) {
            return false;
        }
        VertexAttributes other = (VertexAttributes) obj;
        if (this.attributes.length != other.size()) {
            return false;
        }
        for (int i = 0; i < this.attributes.length; i++) {
            if (!this.attributes[i].equals(other.attributes[i])) {
                return false;
            }
        }
        return true;
    }

    public long getMask() {
        if (this.mask == -1) {
            long result = 0;
            for (VertexAttribute vertexAttribute : this.attributes) {
                result |= (long) vertexAttribute.usage;
            }
            this.mask = result;
        }
        return this.mask;
    }
}
