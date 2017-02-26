package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.graphics.Mesh;

public class MeshPart {
    public String id;
    public int indexOffset;
    public Mesh mesh;
    public int numVertices;
    public int primitiveType;

    public boolean equals(Object arg0) {
        boolean z = true;
        if (arg0 == null) {
            return false;
        }
        if (arg0 == this) {
            return true;
        }
        if (!(arg0 instanceof MeshPart)) {
            return false;
        }
        MeshPart other = (MeshPart) arg0;
        if (!(other.mesh == this.mesh && other.primitiveType == this.primitiveType && other.indexOffset == this.indexOffset && other.numVertices == this.numVertices)) {
            z = false;
        }
        return z;
    }
}
