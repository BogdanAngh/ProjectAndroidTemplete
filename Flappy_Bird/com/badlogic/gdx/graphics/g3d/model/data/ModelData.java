package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

public class ModelData {
    public final Array<ModelAnimation> animations;
    public String id;
    public final Array<ModelMaterial> materials;
    public final Array<ModelMesh> meshes;
    public final Array<ModelNode> nodes;
    public final short[] version;

    public ModelData() {
        this.version = new short[2];
        this.meshes = new Array();
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
    }

    public void addMesh(ModelMesh mesh) {
        Iterator i$ = this.meshes.iterator();
        while (i$.hasNext()) {
            ModelMesh other = (ModelMesh) i$.next();
            if (other.id.equals(mesh.id)) {
                throw new GdxRuntimeException("Mesh with id '" + other.id + "' already in model");
            }
        }
        this.meshes.add(mesh);
    }
}
