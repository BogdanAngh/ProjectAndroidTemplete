package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.nearby.connection.Connections;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ObjLoader extends ModelLoader<ObjLoaderParameters> {
    final Array<Group> groups;
    final FloatArray norms;
    final FloatArray uvs;
    final FloatArray verts;

    private class Group {
        Array<Integer> faces;
        boolean hasNorms;
        boolean hasUVs;
        Material mat;
        String materialName;
        final String name;
        int numFaces;

        Group(String name) {
            this.name = name;
            this.faces = new Array((int) HttpStatus.SC_OK);
            this.numFaces = 0;
            this.mat = new Material("");
            this.materialName = "default";
        }
    }

    public static class ObjLoaderParameters extends AssetLoaderParameters<Model> {
        public boolean flipV;

        public ObjLoaderParameters(boolean flipV) {
            this.flipV = flipV;
        }
    }

    public ObjLoader() {
        this(null);
    }

    public ObjLoader(FileHandleResolver resolver) {
        super(resolver);
        this.verts = new FloatArray((int) HttpStatus.SC_MULTIPLE_CHOICES);
        this.norms = new FloatArray((int) HttpStatus.SC_MULTIPLE_CHOICES);
        this.uvs = new FloatArray((int) HttpStatus.SC_OK);
        this.groups = new Array(10);
    }

    public Model loadObj(FileHandle file) {
        return loadModel(file);
    }

    public Model loadObj(FileHandle file, boolean flipV) {
        return loadModel(file, flipV);
    }

    public Model loadModel(FileHandle fileHandle, boolean flipV) {
        return loadModel(fileHandle, new ObjLoaderParameters(flipV));
    }

    public ModelData loadModelData(FileHandle file, ObjLoaderParameters parameters) {
        return loadModelData(file, parameters == null ? false : parameters.flipV);
    }

    protected ModelData loadModelData(FileHandle file, boolean flipV) {
        int length;
        int i;
        MtlLoader mtl = new MtlLoader();
        Group activeGroup = new Group("default");
        this.groups.add(activeGroup);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.read()), Connections.MAX_RELIABLE_MESSAGE_LEN);
        int id = 0;
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] tokens = line.split("\\s+");
            length = tokens.length;
            if (r0 < 1) {
                break;
            }
            Array<Integer> faces;
            try {
                if (tokens[0].length() != 0) {
                    char firstChar = tokens[0].toLowerCase().charAt(0);
                    if (firstChar != '#') {
                        if (firstChar == 'v') {
                            if (tokens[0].length() == 1) {
                                this.verts.add(Float.parseFloat(tokens[1]));
                                this.verts.add(Float.parseFloat(tokens[2]));
                                this.verts.add(Float.parseFloat(tokens[3]));
                            } else if (tokens[0].charAt(1) == 'n') {
                                this.norms.add(Float.parseFloat(tokens[1]));
                                this.norms.add(Float.parseFloat(tokens[2]));
                                this.norms.add(Float.parseFloat(tokens[3]));
                            } else if (tokens[0].charAt(1) == 't') {
                                this.uvs.add(Float.parseFloat(tokens[1]));
                                this.uvs.add(flipV ? TextTrackStyle.DEFAULT_FONT_SCALE - Float.parseFloat(tokens[2]) : Float.parseFloat(tokens[2]));
                            }
                        } else if (firstChar == 'f') {
                            faces = activeGroup.faces;
                            i = 1;
                            while (true) {
                                if (i >= tokens.length - 2) {
                                    break;
                                }
                                String[] parts = tokens[1].split("/");
                                faces.add(Integer.valueOf(getIndex(parts[0], this.verts.size)));
                                length = parts.length;
                                if (r0 > 2) {
                                    if (i == 1) {
                                        activeGroup.hasNorms = true;
                                    }
                                    faces.add(Integer.valueOf(getIndex(parts[2], this.norms.size)));
                                }
                                length = parts.length;
                                if (r0 > 1 && parts[1].length() > 0) {
                                    if (i == 1) {
                                        activeGroup.hasUVs = true;
                                    }
                                    faces.add(Integer.valueOf(getIndex(parts[1], this.uvs.size)));
                                }
                                i++;
                                parts = tokens[i].split("/");
                                faces.add(Integer.valueOf(getIndex(parts[0], this.verts.size)));
                                length = parts.length;
                                if (r0 > 2) {
                                    faces.add(Integer.valueOf(getIndex(parts[2], this.norms.size)));
                                }
                                length = parts.length;
                                if (r0 > 1 && parts[1].length() > 0) {
                                    faces.add(Integer.valueOf(getIndex(parts[1], this.uvs.size)));
                                }
                                i++;
                                parts = tokens[i].split("/");
                                faces.add(Integer.valueOf(getIndex(parts[0], this.verts.size)));
                                length = parts.length;
                                if (r0 > 2) {
                                    faces.add(Integer.valueOf(getIndex(parts[2], this.norms.size)));
                                }
                                length = parts.length;
                                if (r0 > 1 && parts[1].length() > 0) {
                                    faces.add(Integer.valueOf(getIndex(parts[1], this.uvs.size)));
                                }
                                activeGroup.numFaces++;
                                i--;
                            }
                        } else if (firstChar == 'o' || firstChar == 'g') {
                            length = tokens.length;
                            if (r0 > 1) {
                                activeGroup = setActiveGroup(tokens[1]);
                            } else {
                                activeGroup = setActiveGroup("default");
                            }
                        } else if (tokens[0].equals("mtllib")) {
                            mtl.load(file.parent().child(tokens[1]));
                        } else if (tokens[0].equals("usemtl")) {
                            length = tokens.length;
                            if (r0 == 1) {
                                activeGroup.materialName = "default";
                            } else {
                                activeGroup.materialName = tokens[1];
                            }
                        }
                    }
                }
            } catch (IOException e) {
                return null;
            }
        }
        bufferedReader.close();
        i = 0;
        while (true) {
            length = this.groups.size;
            if (i >= r0) {
                break;
            }
            length = ((Group) this.groups.get(i)).numFaces;
            if (r0 < 1) {
                this.groups.removeIndex(i);
                i--;
            }
            i++;
        }
        length = this.groups.size;
        if (r0 < 1) {
            return null;
        }
        int numGroups = this.groups.size;
        ModelData data = new ModelData();
        for (int g = 0; g < numGroups; g++) {
            Group group = (Group) this.groups.get(g);
            faces = group.faces;
            int numElements = faces.size;
            int numFaces = group.numFaces;
            boolean hasNorms = group.hasNorms;
            boolean hasUVs = group.hasUVs;
            int i2 = numFaces * 3;
            int i3 = (hasNorms ? 3 : 0) + 3;
            if (hasUVs) {
                length = 2;
            } else {
                length = 0;
            }
            float[] finalVerts = new float[((length + i3) * i2)];
            int vi = 0;
            int i4 = 0;
            while (i4 < numElements) {
                i = i4 + 1;
                int intValue = ((Integer) faces.get(i4)).intValue() * 3;
                int i5 = vi + 1;
                int i6 = intValue + 1;
                finalVerts[vi] = this.verts.get(intValue);
                vi = i5 + 1;
                intValue = i6 + 1;
                finalVerts[i5] = this.verts.get(i6);
                i5 = vi + 1;
                finalVerts[vi] = this.verts.get(intValue);
                if (hasNorms) {
                    i4 = i + 1;
                    int intValue2 = ((Integer) faces.get(i)).intValue() * 3;
                    vi = i5 + 1;
                    int i7 = intValue2 + 1;
                    finalVerts[i5] = this.norms.get(intValue2);
                    i5 = vi + 1;
                    intValue2 = i7 + 1;
                    finalVerts[vi] = this.norms.get(i7);
                    vi = i5 + 1;
                    finalVerts[i5] = this.norms.get(intValue2);
                } else {
                    vi = i5;
                    i4 = i;
                }
                if (hasUVs) {
                    i = i4 + 1;
                    int intValue3 = ((Integer) faces.get(i4)).intValue() * 2;
                    i5 = vi + 1;
                    int uvIndex = intValue3 + 1;
                    finalVerts[vi] = this.uvs.get(intValue3);
                    vi = i5 + 1;
                    finalVerts[i5] = this.uvs.get(uvIndex);
                    i5 = vi;
                } else {
                    i5 = vi;
                    i = i4;
                }
                vi = i5;
                i4 = i;
            }
            int numIndices = numFaces * 3 >= 32767 ? 0 : numFaces * 3;
            short[] finalIndices = new short[numIndices];
            if (numIndices > 0) {
                for (i = 0; i < numIndices; i++) {
                    finalIndices[i] = (short) i;
                }
            }
            Array<VertexAttribute> attributes = new Array();
            attributes.add(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
            if (hasNorms) {
                attributes.add(new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE));
            }
            if (hasUVs) {
                attributes.add(new VertexAttribute(16, 2, "a_texCoord0"));
            }
            id++;
            String nodeId = "node" + id;
            String meshId = "mesh" + id;
            String partId = "part" + id;
            ModelNode node = new ModelNode();
            node.id = nodeId;
            node.meshId = meshId;
            node.scale = new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            node.translation = new Vector3();
            node.rotation = new Quaternion();
            ModelNodePart pm = new ModelNodePart();
            pm.meshPartId = partId;
            pm.materialId = group.materialName;
            node.parts = new ModelNodePart[]{pm};
            ModelMeshPart part = new ModelMeshPart();
            part.id = partId;
            part.indices = finalIndices;
            part.primitiveType = 4;
            ModelMesh mesh = new ModelMesh();
            mesh.id = meshId;
            mesh.attributes = (VertexAttribute[]) attributes.toArray(VertexAttribute.class);
            mesh.vertices = finalVerts;
            mesh.parts = new ModelMeshPart[]{part};
            data.nodes.add(node);
            data.meshes.add(mesh);
            data.materials.add(mtl.getMaterial(group.materialName));
        }
        if (this.verts.size > 0) {
            this.verts.clear();
        }
        if (this.norms.size > 0) {
            this.norms.clear();
        }
        if (this.uvs.size > 0) {
            this.uvs.clear();
        }
        if (this.groups.size <= 0) {
            return data;
        }
        this.groups.clear();
        return data;
    }

    private Group setActiveGroup(String name) {
        Group group;
        Iterator i$ = this.groups.iterator();
        while (i$.hasNext()) {
            group = (Group) i$.next();
            if (group.name.equals(name)) {
                return group;
            }
        }
        group = new Group(name);
        this.groups.add(group);
        return group;
    }

    private int getIndex(String index, int size) {
        if (index == null || index.length() == 0) {
            return 0;
        }
        int idx = Integer.parseInt(index);
        if (idx < 0) {
            return size + idx;
        }
        return idx - 1;
    }
}
