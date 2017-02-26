package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.graphics.glutils.IndexBufferObject;
import com.badlogic.gdx.graphics.glutils.IndexBufferObjectSubData;
import com.badlogic.gdx.graphics.glutils.IndexData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexArray;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectSubData;
import com.badlogic.gdx.graphics.glutils.VertexData;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.android.gms.drive.events.CompletionEvent;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Map;

public class Mesh implements Disposable {
    public static boolean forceVBO;
    static final Map<Application, Array<Mesh>> meshes;
    boolean autoBind;
    final IndexData indices;
    final boolean isVertexArray;
    private final Vector3 tmpV;
    final VertexData vertices;

    public enum VertexDataType {
        VertexArray,
        VertexBufferObject,
        VertexBufferObjectSubData
    }

    static {
        meshes = new HashMap();
        forceVBO = false;
    }

    public Mesh(boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
        this.autoBind = true;
        this.tmpV = new Vector3();
        if (Gdx.gl20 == null && Gdx.gl11 == null && !forceVBO) {
            this.vertices = new VertexArray(maxVertices, attributes);
            this.indices = new IndexArray(maxIndices);
            this.isVertexArray = true;
        } else {
            this.vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
            this.indices = new IndexBufferObject(isStatic, maxIndices);
            this.isVertexArray = false;
        }
        addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean isStatic, int maxVertices, int maxIndices, VertexAttributes attributes) {
        this.autoBind = true;
        this.tmpV = new Vector3();
        if (Gdx.gl20 == null && Gdx.gl11 == null && !forceVBO) {
            this.vertices = new VertexArray(maxVertices, attributes);
            this.indices = new IndexArray(maxIndices);
            this.isVertexArray = true;
        } else {
            this.vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
            this.indices = new IndexBufferObject(isStatic, maxIndices);
            this.isVertexArray = false;
        }
        addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean staticVertices, boolean staticIndices, int maxVertices, int maxIndices, VertexAttributes attributes) {
        this.autoBind = true;
        this.tmpV = new Vector3();
        if (Gdx.gl20 == null && Gdx.gl11 == null && !forceVBO) {
            this.vertices = new VertexArray(maxVertices, attributes);
            this.indices = new IndexArray(maxIndices);
            this.isVertexArray = true;
        } else {
            this.vertices = new VertexBufferObject(staticVertices, maxVertices, attributes);
            this.indices = new IndexBufferObject(staticIndices, maxIndices);
            this.isVertexArray = false;
        }
        addManagedMesh(Gdx.app, this);
    }

    public Mesh(VertexDataType type, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
        this.autoBind = true;
        this.tmpV = new Vector3();
        if (type == VertexDataType.VertexBufferObject || forceVBO) {
            this.vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
            this.indices = new IndexBufferObject(isStatic, maxIndices);
            this.isVertexArray = false;
        } else if (type == VertexDataType.VertexBufferObjectSubData) {
            this.vertices = new VertexBufferObjectSubData(isStatic, maxVertices, attributes);
            this.indices = new IndexBufferObjectSubData(isStatic, maxIndices);
            this.isVertexArray = false;
        } else {
            this.vertices = new VertexArray(maxVertices, attributes);
            this.indices = new IndexArray(maxIndices);
            this.isVertexArray = true;
        }
        addManagedMesh(Gdx.app, this);
    }

    public static Mesh create(boolean isStatic, Mesh base, Matrix4[] transformations) {
        VertexAttribute posAttr = base.getVertexAttribute(1);
        int offset = posAttr.offset / 4;
        int numComponents = posAttr.numComponents;
        int numVertices = base.getNumVertices();
        int vertexSize = base.getVertexSize() / 4;
        int baseSize = numVertices * vertexSize;
        int numIndices = base.getNumIndices();
        float[] vertices = new float[((numVertices * vertexSize) * transformations.length)];
        short[] indices = new short[(transformations.length * numIndices)];
        base.getIndices(indices);
        for (int i = 0; i < transformations.length; i++) {
            base.getVertices(0, baseSize, vertices, baseSize * i);
            transform(transformations[i], vertices, vertexSize, offset, numComponents, numVertices * i, numVertices);
            if (i > 0) {
                for (int j = 0; j < numIndices; j++) {
                    indices[(numIndices * i) + j] = (short) (indices[j] + (numVertices * i));
                }
            }
        }
        Mesh result = new Mesh(isStatic, vertices.length / vertexSize, indices.length, base.getVertexAttributes());
        result.setVertices(vertices);
        result.setIndices(indices);
        return result;
    }

    public static Mesh create(boolean isStatic, Mesh[] meshes) {
        return create(isStatic, meshes, null);
    }

    public static Mesh create(boolean isStatic, Mesh[] meshes, Matrix4[] transformations) {
        if (transformations == null || transformations.length >= meshes.length) {
            VertexAttributes attributes = meshes[0].getVertexAttributes();
            int vertCount = meshes[0].getNumVertices();
            int idxCount = meshes[0].getNumIndices();
            int i = 1;
            while (i < meshes.length) {
                if (meshes[i].getVertexAttributes().equals(attributes)) {
                    vertCount += meshes[i].getNumVertices();
                    idxCount += meshes[i].getNumIndices();
                    i++;
                } else {
                    throw new IllegalArgumentException("Inconsistent VertexAttributes");
                }
            }
            VertexAttribute posAttr = meshes[0].getVertexAttribute(1);
            int offset = posAttr.offset / 4;
            int numComponents = posAttr.numComponents;
            int vertexSize = attributes.vertexSize / 4;
            float[] vertices = new float[(vertCount * vertexSize)];
            short[] indices = new short[idxCount];
            meshes[0].getVertices(vertices);
            meshes[0].getIndices(indices);
            int voffset = meshes[0].getNumVertices() * vertexSize;
            int ioffset = meshes[0].getNumIndices();
            for (i = 1; i < meshes.length; i++) {
                Mesh mesh = meshes[i];
                int vsize = mesh.getNumVertices() * vertexSize;
                int isize = mesh.getNumIndices();
                mesh.getVertices(0, vsize, vertices, voffset);
                if (transformations != null) {
                    transform(transformations[i], vertices, vertexSize, offset, numComponents, voffset / vertexSize, vsize / vertexSize);
                }
                mesh.getIndices(indices, ioffset);
                for (int j = 0; j < isize; j++) {
                    indices[ioffset + j] = (short) (indices[ioffset + j] + voffset);
                }
                voffset += vsize;
                ioffset += isize;
            }
            Mesh mesh2 = new Mesh(isStatic, vertices.length / vertexSize, indices.length, attributes);
            mesh2.setVertices(vertices);
            mesh2.setIndices(indices);
            return mesh2;
        }
        throw new IllegalArgumentException("Not enough transformations specified");
    }

    public Mesh setVertices(float[] vertices) {
        this.vertices.setVertices(vertices, 0, vertices.length);
        return this;
    }

    public Mesh setVertices(float[] vertices, int offset, int count) {
        this.vertices.setVertices(vertices, offset, count);
        return this;
    }

    public void getVertices(float[] vertices) {
        getVertices(0, -1, vertices);
    }

    public void getVertices(int srcOffset, float[] vertices) {
        getVertices(srcOffset, -1, vertices);
    }

    public void getVertices(int srcOffset, int count, float[] vertices) {
        getVertices(srcOffset, count, vertices, 0);
    }

    public void getVertices(int srcOffset, int count, float[] vertices, int destOffset) {
        int max = (getNumVertices() * getVertexSize()) / 4;
        if (count == -1) {
            count = max - srcOffset;
            if (count > vertices.length - destOffset) {
                count = vertices.length - destOffset;
            }
        }
        if (srcOffset < 0 || count <= 0 || srcOffset + count > max || destOffset < 0 || destOffset >= vertices.length) {
            throw new IndexOutOfBoundsException();
        } else if (vertices.length - destOffset < count) {
            throw new IllegalArgumentException("not enough room in vertices array, has " + vertices.length + " floats, needs " + count);
        } else {
            int pos = getVerticesBuffer().position();
            getVerticesBuffer().position(srcOffset);
            getVerticesBuffer().get(vertices, destOffset, count);
            getVerticesBuffer().position(pos);
        }
    }

    public Mesh setIndices(short[] indices) {
        this.indices.setIndices(indices, 0, indices.length);
        return this;
    }

    public Mesh setIndices(short[] indices, int offset, int count) {
        this.indices.setIndices(indices, offset, count);
        return this;
    }

    public void getIndices(short[] indices) {
        getIndices(indices, 0);
    }

    public void getIndices(short[] indices, int destOffset) {
        if (indices.length - destOffset < getNumIndices()) {
            throw new IllegalArgumentException("not enough room in indices array, has " + indices.length + " floats, needs " + getNumIndices());
        }
        int pos = getIndicesBuffer().position();
        getIndicesBuffer().position(0);
        getIndicesBuffer().get(indices, destOffset, getNumIndices());
        getIndicesBuffer().position(pos);
    }

    public int getNumIndices() {
        return this.indices.getNumIndices();
    }

    public int getNumVertices() {
        return this.vertices.getNumVertices();
    }

    public int getMaxVertices() {
        return this.vertices.getNumMaxVertices();
    }

    public int getMaxIndices() {
        return this.indices.getNumMaxIndices();
    }

    public int getVertexSize() {
        return this.vertices.getAttributes().vertexSize;
    }

    public void setAutoBind(boolean autoBind) {
        this.autoBind = autoBind;
    }

    public void bind() {
        if (Gdx.graphics.isGL20Available()) {
            throw new IllegalStateException("can't use this render method with OpenGL ES 2.0");
        }
        this.vertices.bind();
        if (!this.isVertexArray && this.indices.getNumIndices() > 0) {
            this.indices.bind();
        }
    }

    public void unbind() {
        if (Gdx.graphics.isGL20Available()) {
            throw new IllegalStateException("can't use this render method with OpenGL ES 2.0");
        }
        this.vertices.unbind();
        if (!this.isVertexArray && this.indices.getNumIndices() > 0) {
            this.indices.unbind();
        }
    }

    public void bind(ShaderProgram shader) {
        bind(shader, null);
    }

    public void bind(ShaderProgram shader, int[] locations) {
        if (Gdx.graphics.isGL20Available()) {
            this.vertices.bind(shader, locations);
            if (this.indices.getNumIndices() > 0) {
                this.indices.bind();
                return;
            }
            return;
        }
        throw new IllegalStateException("can't use this render method with OpenGL ES 1.x");
    }

    public void unbind(ShaderProgram shader) {
        unbind(shader, null);
    }

    public void unbind(ShaderProgram shader, int[] locations) {
        if (Gdx.graphics.isGL20Available()) {
            this.vertices.unbind(shader, locations);
            if (this.indices.getNumIndices() > 0) {
                this.indices.unbind();
                return;
            }
            return;
        }
        throw new IllegalStateException("can't use this render method with OpenGL ES 1.x");
    }

    public void render(int primitiveType) {
        render(primitiveType, 0, this.indices.getNumMaxIndices() > 0 ? getNumIndices() : getNumVertices(), this.autoBind);
    }

    public void render(int primitiveType, int offset, int count) {
        render(primitiveType, offset, count, this.autoBind);
    }

    public void render(int primitiveType, int offset, int count, boolean autoBind) {
        if (Gdx.graphics.isGL20Available()) {
            throw new IllegalStateException("can't use this render method with OpenGL ES 2.0");
        } else if (count != 0) {
            if (autoBind) {
                bind();
            }
            if (this.isVertexArray) {
                if (this.indices.getNumIndices() > 0) {
                    ShortBuffer buffer = this.indices.getBuffer();
                    int oldPosition = buffer.position();
                    int oldLimit = buffer.limit();
                    buffer.position(offset);
                    buffer.limit(offset + count);
                    Gdx.gl10.glDrawElements(primitiveType, count, GL20.GL_UNSIGNED_SHORT, buffer);
                    buffer.position(oldPosition);
                    buffer.limit(oldLimit);
                } else {
                    Gdx.gl10.glDrawArrays(primitiveType, offset, count);
                }
            } else if (this.indices.getNumIndices() > 0) {
                Gdx.gl11.glDrawElements(primitiveType, count, GL20.GL_UNSIGNED_SHORT, offset * 2);
            } else {
                Gdx.gl11.glDrawArrays(primitiveType, offset, count);
            }
            if (autoBind) {
                unbind();
            }
        }
    }

    public void render(ShaderProgram shader, int primitiveType) {
        render(shader, primitiveType, 0, this.indices.getNumMaxIndices() > 0 ? getNumIndices() : getNumVertices(), this.autoBind);
    }

    public void render(ShaderProgram shader, int primitiveType, int offset, int count) {
        render(shader, primitiveType, offset, count, this.autoBind);
    }

    public void render(ShaderProgram shader, int primitiveType, int offset, int count, boolean autoBind) {
        if (!Gdx.graphics.isGL20Available()) {
            throw new IllegalStateException("can't use this render method with OpenGL ES 1.x");
        } else if (count != 0) {
            if (autoBind) {
                bind(shader);
            }
            if (this.isVertexArray) {
                if (this.indices.getNumIndices() > 0) {
                    ShortBuffer buffer = this.indices.getBuffer();
                    int oldPosition = buffer.position();
                    int oldLimit = buffer.limit();
                    buffer.position(offset);
                    buffer.limit(offset + count);
                    Gdx.gl20.glDrawElements(primitiveType, count, GL20.GL_UNSIGNED_SHORT, buffer);
                    buffer.position(oldPosition);
                    buffer.limit(oldLimit);
                } else {
                    Gdx.gl20.glDrawArrays(primitiveType, offset, count);
                }
            } else if (this.indices.getNumIndices() > 0) {
                Gdx.gl20.glDrawElements(primitiveType, count, GL20.GL_UNSIGNED_SHORT, offset * 2);
            } else {
                Gdx.gl20.glDrawArrays(primitiveType, offset, count);
            }
            if (autoBind) {
                unbind(shader);
            }
        }
    }

    public void dispose() {
        if (meshes.get(Gdx.app) != null) {
            ((Array) meshes.get(Gdx.app)).removeValue(this, true);
        }
        this.vertices.dispose();
        this.indices.dispose();
    }

    public VertexAttribute getVertexAttribute(int usage) {
        VertexAttributes attributes = this.vertices.getAttributes();
        int len = attributes.size();
        for (int i = 0; i < len; i++) {
            if (attributes.get(i).usage == usage) {
                return attributes.get(i);
            }
        }
        return null;
    }

    public VertexAttributes getVertexAttributes() {
        return this.vertices.getAttributes();
    }

    public FloatBuffer getVerticesBuffer() {
        return this.vertices.getBuffer();
    }

    public BoundingBox calculateBoundingBox() {
        BoundingBox bbox = new BoundingBox();
        calculateBoundingBox(bbox);
        return bbox;
    }

    public void calculateBoundingBox(BoundingBox bbox) {
        int numVertices = getNumVertices();
        if (numVertices == 0) {
            throw new GdxRuntimeException("No vertices defined");
        }
        FloatBuffer verts = this.vertices.getBuffer();
        bbox.inf();
        VertexAttribute posAttrib = getVertexAttribute(1);
        int vertexSize = this.vertices.getAttributes().vertexSize / 4;
        int idx = posAttrib.offset / 4;
        int i;
        switch (posAttrib.numComponents) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                for (i = 0; i < numVertices; i++) {
                    bbox.ext(verts.get(idx), 0.0f, 0.0f);
                    idx += vertexSize;
                }
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                for (i = 0; i < numVertices; i++) {
                    bbox.ext(verts.get(idx), verts.get(idx + 1), 0.0f);
                    idx += vertexSize;
                }
            case CompletionEvent.STATUS_CANCELED /*3*/:
                for (i = 0; i < numVertices; i++) {
                    bbox.ext(verts.get(idx), verts.get(idx + 1), verts.get(idx + 2));
                    idx += vertexSize;
                }
            default:
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox out, int offset, int count) {
        return extendBoundingBox(out.inf(), offset, count);
    }

    public BoundingBox calculateBoundingBox(BoundingBox out, int offset, int count, Matrix4 transform) {
        return extendBoundingBox(out.inf(), offset, count, transform);
    }

    public BoundingBox extendBoundingBox(BoundingBox out, int offset, int count) {
        return extendBoundingBox(out, offset, count, null);
    }

    public BoundingBox extendBoundingBox(BoundingBox out, int offset, int count, Matrix4 transform) {
        int numIndices = getNumIndices();
        if (offset < 0 || count < 1 || offset + count > numIndices) {
            throw new GdxRuntimeException("Not enough indices");
        }
        FloatBuffer verts = this.vertices.getBuffer();
        ShortBuffer index = this.indices.getBuffer();
        VertexAttribute posAttrib = getVertexAttribute(1);
        int posoff = posAttrib.offset / 4;
        int vertexSize = this.vertices.getAttributes().vertexSize / 4;
        int end = offset + count;
        int i;
        int idx;
        switch (posAttrib.numComponents) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                for (i = offset; i < end; i++) {
                    this.tmpV.set(verts.get((index.get(i) * vertexSize) + posoff), 0.0f, 0.0f);
                    if (transform != null) {
                        this.tmpV.mul(transform);
                    }
                    out.ext(this.tmpV);
                }
                break;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                for (i = offset; i < end; i++) {
                    idx = (index.get(i) * vertexSize) + posoff;
                    this.tmpV.set(verts.get(idx), verts.get(idx + 1), 0.0f);
                    if (transform != null) {
                        this.tmpV.mul(transform);
                    }
                    out.ext(this.tmpV);
                }
                break;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                for (i = offset; i < end; i++) {
                    idx = (index.get(i) * vertexSize) + posoff;
                    this.tmpV.set(verts.get(idx), verts.get(idx + 1), verts.get(idx + 2));
                    if (transform != null) {
                        this.tmpV.mul(transform);
                    }
                    out.ext(this.tmpV);
                }
                break;
        }
        return out;
    }

    public ShortBuffer getIndicesBuffer() {
        return this.indices.getBuffer();
    }

    private static void addManagedMesh(Application app, Mesh mesh) {
        Array<Mesh> managedResources = (Array) meshes.get(app);
        if (managedResources == null) {
            managedResources = new Array();
        }
        managedResources.add(mesh);
        meshes.put(app, managedResources);
    }

    public static void invalidateAllMeshes(Application app) {
        Array<Mesh> meshesArray = (Array) meshes.get(app);
        if (meshesArray != null) {
            for (int i = 0; i < meshesArray.size; i++) {
                if (((Mesh) meshesArray.get(i)).vertices instanceof VertexBufferObject) {
                    ((VertexBufferObject) ((Mesh) meshesArray.get(i)).vertices).invalidate();
                }
                ((Mesh) meshesArray.get(i)).indices.invalidate();
            }
        }
    }

    public static void clearAllMeshes(Application app) {
        meshes.remove(app);
    }

    public static String getManagedStatus() {
        StringBuilder builder = new StringBuilder();
        builder.append("Managed meshes/app: { ");
        for (Application app : meshes.keySet()) {
            builder.append(((Array) meshes.get(app)).size);
            builder.append(" ");
        }
        builder.append("}");
        return builder.toString();
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        VertexAttribute posAttr = getVertexAttribute(1);
        int offset = posAttr.offset / 4;
        int numComponents = posAttr.numComponents;
        int numVertices = getNumVertices();
        int vertexSize = getVertexSize() / 4;
        float[] vertices = new float[(numVertices * vertexSize)];
        getVertices(vertices);
        int idx = offset;
        int i;
        int i2;
        switch (numComponents) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                for (i = 0; i < numVertices; i++) {
                    vertices[idx] = vertices[idx] * scaleX;
                    idx += vertexSize;
                }
                break;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                for (i = 0; i < numVertices; i++) {
                    vertices[idx] = vertices[idx] * scaleX;
                    i2 = idx + 1;
                    vertices[i2] = vertices[i2] * scaleY;
                    idx += vertexSize;
                }
                break;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                for (i = 0; i < numVertices; i++) {
                    vertices[idx] = vertices[idx] * scaleX;
                    i2 = idx + 1;
                    vertices[i2] = vertices[i2] * scaleY;
                    i2 = idx + 2;
                    vertices[i2] = vertices[i2] * scaleZ;
                    idx += vertexSize;
                }
                break;
        }
        setVertices(vertices);
    }

    public void transform(Matrix4 matrix) {
        transform(matrix, 0, getNumVertices());
    }

    protected void transform(Matrix4 matrix, int start, int count) {
        VertexAttribute posAttr = getVertexAttribute(1);
        int offset = posAttr.offset / 4;
        int vertexSize = getVertexSize() / 4;
        int numComponents = posAttr.numComponents;
        float[] vertices = new float[(getNumVertices() * vertexSize)];
        getVertices(0, vertices.length, vertices);
        transform(matrix, vertices, vertexSize, offset, numComponents, start, count);
        setVertices(vertices, 0, vertices.length);
    }

    public static void transform(Matrix4 matrix, float[] vertices, int vertexSize, int offset, int dimensions, int start, int count) {
        if (offset < 0 || dimensions < 1 || offset + dimensions > vertexSize) {
            throw new IndexOutOfBoundsException();
        } else if (start < 0 || count < 1 || (start + count) * vertexSize > vertices.length) {
            throw new IndexOutOfBoundsException("start = " + start + ", count = " + count + ", vertexSize = " + vertexSize + ", length = " + vertices.length);
        } else {
            Vector3 tmp = new Vector3();
            int idx = offset + (start * vertexSize);
            int i;
            switch (dimensions) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    for (i = 0; i < count; i++) {
                        tmp.set(vertices[idx], 0.0f, 0.0f).mul(matrix);
                        vertices[idx] = tmp.f105x;
                        idx += vertexSize;
                    }
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    for (i = 0; i < count; i++) {
                        tmp.set(vertices[idx], vertices[idx + 1], 0.0f).mul(matrix);
                        vertices[idx] = tmp.f105x;
                        vertices[idx + 1] = tmp.f106y;
                        idx += vertexSize;
                    }
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    for (i = 0; i < count; i++) {
                        tmp.set(vertices[idx], vertices[idx + 1], vertices[idx + 2]).mul(matrix);
                        vertices[idx] = tmp.f105x;
                        vertices[idx + 1] = tmp.f106y;
                        vertices[idx + 2] = tmp.f107z;
                        idx += vertexSize;
                    }
                default:
            }
        }
    }

    public void transformUV(Matrix3 matrix) {
        transformUV(matrix, 0, getNumVertices());
    }

    protected void transformUV(Matrix3 matrix, int start, int count) {
        int offset = getVertexAttribute(16).offset / 4;
        int vertexSize = getVertexSize() / 4;
        float[] vertices = new float[(getNumVertices() * vertexSize)];
        getVertices(0, vertices.length, vertices);
        transformUV(matrix, vertices, vertexSize, offset, start, count);
        setVertices(vertices, 0, vertices.length);
    }

    public static void transformUV(Matrix3 matrix, float[] vertices, int vertexSize, int offset, int start, int count) {
        if (start < 0 || count < 1 || (start + count) * vertexSize > vertices.length) {
            throw new IndexOutOfBoundsException("start = " + start + ", count = " + count + ", vertexSize = " + vertexSize + ", length = " + vertices.length);
        }
        Vector2 tmp = new Vector2();
        int idx = offset + (start * vertexSize);
        for (int i = 0; i < count; i++) {
            tmp.set(vertices[idx], vertices[idx + 1]).mul(matrix);
            vertices[idx] = tmp.f100x;
            vertices[idx + 1] = tmp.f101y;
            idx += vertexSize;
        }
    }

    public Mesh copy(boolean isStatic, boolean removeDuplicates, int[] usage) {
        int size;
        int i;
        int idx;
        int j;
        short vertexSize = getVertexSize() / 4;
        int numVertices = getNumVertices();
        float[] vertices = new float[(numVertices * vertexSize)];
        getVertices(0, vertices.length, vertices);
        short[] checks = null;
        VertexAttribute[] attrs = null;
        short newVertexSize = (short) 0;
        if (usage != null) {
            int length;
            size = 0;
            int as = 0;
            i = 0;
            while (true) {
                length = usage.length;
                if (i >= r0) {
                    break;
                }
                if (getVertexAttribute(usage[i]) != null) {
                    size += getVertexAttribute(usage[i]).numComponents;
                    as++;
                }
                i++;
            }
            if (size > 0) {
                attrs = new VertexAttribute[as];
                checks = new short[size];
                idx = -1;
                int ai = -1;
                i = 0;
                while (true) {
                    length = usage.length;
                    if (i >= r0) {
                        break;
                    }
                    VertexAttribute a = getVertexAttribute(usage[i]);
                    if (a != null) {
                        for (j = 0; j < a.numComponents; j++) {
                            idx++;
                            checks[idx] = (short) (a.offset + j);
                        }
                        ai++;
                        attrs[ai] = new VertexAttribute(a.usage, a.numComponents, a.alias);
                        newVertexSize += a.numComponents;
                    }
                    i++;
                }
            }
        }
        if (checks == null) {
            checks = new short[vertexSize];
            for (short i2 = (short) 0; i2 < vertexSize; i2 = (short) (i2 + 1)) {
                checks[i2] = i2;
            }
            newVertexSize = vertexSize;
        }
        int numIndices = getNumIndices();
        short[] indices = null;
        if (numIndices > 0) {
            indices = new short[numIndices];
            getIndices(indices);
            if (removeDuplicates || newVertexSize != vertexSize) {
                float[] tmp = new float[vertices.length];
                size = 0;
                for (i = 0; i < numIndices; i++) {
                    int idx1 = indices[i] * vertexSize;
                    short newIndex = (short) -1;
                    if (removeDuplicates) {
                        for (short j2 = (short) 0; j2 < size && newIndex < (short) 0; j2 = (short) (j2 + 1)) {
                            int idx2 = j2 * newVertexSize;
                            boolean found = true;
                            for (int k = 0; k < checks.length && found; k++) {
                                if (tmp[idx2 + k] != vertices[checks[k] + idx1]) {
                                    found = false;
                                }
                            }
                            if (found) {
                                newIndex = j2;
                            }
                        }
                    }
                    if (newIndex > (short) 0) {
                        indices[i] = newIndex;
                    } else {
                        idx = size * newVertexSize;
                        for (j = 0; j < checks.length; j++) {
                            tmp[idx + j] = vertices[checks[j] + idx1];
                        }
                        indices[i] = (short) size;
                        size++;
                    }
                }
                vertices = tmp;
                numVertices = size;
            }
        }
        Mesh mesh;
        if (attrs == null) {
            mesh = new Mesh(isStatic, numVertices, indices == null ? 0 : indices.length, getVertexAttributes());
        } else {
            mesh = new Mesh(isStatic, numVertices, indices == null ? 0 : indices.length, attrs);
        }
        result.setVertices(vertices, 0, numVertices * newVertexSize);
        result.setIndices(indices);
        return result;
    }

    public Mesh copy(boolean isStatic) {
        return copy(isStatic, false, null);
    }
}
