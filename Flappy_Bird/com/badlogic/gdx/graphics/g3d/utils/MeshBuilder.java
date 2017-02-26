package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ShortArray;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.util.Iterator;

public class MeshBuilder implements MeshPartBuilder {
    private static final Array<Vector3> vectorArray;
    private static final Pool<Vector3> vectorPool;
    private VertexAttributes attributes;
    private int colOffset;
    private int colSize;
    private final Color color;
    private boolean colorSet;
    private int cpOffset;
    private ShortArray indices;
    private int istart;
    private final Matrix4 matTmp1;
    private int norOffset;
    private MeshPart part;
    private Array<MeshPart> parts;
    private int posOffset;
    private int posSize;
    private int primitiveType;
    private int stride;
    private final Vector3 tempV1;
    private final Vector3 tempV2;
    private final Vector3 tempV3;
    private final Vector3 tempV4;
    private final Vector3 tempV5;
    private final Vector3 tempV6;
    private final Vector3 tempV7;
    private final Vector3 tempV8;
    private float uMax;
    private float uMin;
    private int uvOffset;
    private float vMax;
    private float vMin;
    private final VertexInfo vertTmp1;
    private final VertexInfo vertTmp2;
    private final VertexInfo vertTmp3;
    private final VertexInfo vertTmp4;
    private final VertexInfo vertTmp5;
    private final VertexInfo vertTmp6;
    private final VertexInfo vertTmp7;
    private final VertexInfo vertTmp8;
    private float[] vertex;
    private FloatArray vertices;
    private short vindex;

    /* renamed from: com.badlogic.gdx.graphics.g3d.utils.MeshBuilder.1 */
    static class C03641 extends Pool<Vector3> {
        C03641() {
        }

        protected Vector3 newObject() {
            return new Vector3();
        }
    }

    public MeshBuilder() {
        this.vertTmp1 = new VertexInfo();
        this.vertTmp2 = new VertexInfo();
        this.vertTmp3 = new VertexInfo();
        this.vertTmp4 = new VertexInfo();
        this.vertTmp5 = new VertexInfo();
        this.vertTmp6 = new VertexInfo();
        this.vertTmp7 = new VertexInfo();
        this.vertTmp8 = new VertexInfo();
        this.matTmp1 = new Matrix4();
        this.tempV1 = new Vector3();
        this.tempV2 = new Vector3();
        this.tempV3 = new Vector3();
        this.tempV4 = new Vector3();
        this.tempV5 = new Vector3();
        this.tempV6 = new Vector3();
        this.tempV7 = new Vector3();
        this.tempV8 = new Vector3();
        this.vertices = new FloatArray();
        this.indices = new ShortArray();
        this.parts = new Array();
        this.color = new Color();
        this.uMin = 0.0f;
        this.uMax = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.vMin = 0.0f;
        this.vMax = TextTrackStyle.DEFAULT_FONT_SCALE;
    }

    public static VertexAttributes createAttributes(long usage) {
        Array<VertexAttribute> attrs = new Array();
        if ((1 & usage) == 1) {
            attrs.add(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
        }
        if ((2 & usage) == 2) {
            attrs.add(new VertexAttribute(2, 4, ShaderProgram.COLOR_ATTRIBUTE));
        }
        if ((8 & usage) == 8) {
            attrs.add(new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE));
        }
        if ((16 & usage) == 16) {
            attrs.add(new VertexAttribute(16, 2, "a_texCoord0"));
        }
        VertexAttribute[] attributes = new VertexAttribute[attrs.size];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = (VertexAttribute) attrs.get(i);
        }
        return new VertexAttributes(attributes);
    }

    public void begin(long attributes) {
        begin(createAttributes(attributes), 0);
    }

    public void begin(VertexAttributes attributes) {
        begin(attributes, 0);
    }

    public void begin(long attributes, int primitiveType) {
        begin(createAttributes(attributes), primitiveType);
    }

    public void begin(VertexAttributes attributes, int primitiveType) {
        int i = -1;
        if (this.attributes != null) {
            throw new RuntimeException("Call end() first");
        }
        this.attributes = attributes;
        this.vertices.clear();
        this.indices.clear();
        this.parts.clear();
        this.vindex = (short) 0;
        this.istart = 0;
        this.part = null;
        this.stride = attributes.vertexSize / 4;
        this.vertex = new float[this.stride];
        VertexAttribute a = attributes.findByUsage(1);
        if (a == null) {
            throw new GdxRuntimeException("Cannot build mesh without position attribute");
        }
        this.posOffset = a.offset / 4;
        this.posSize = a.numComponents;
        a = attributes.findByUsage(8);
        this.norOffset = a == null ? -1 : a.offset / 4;
        a = attributes.findByUsage(2);
        this.colOffset = a == null ? -1 : a.offset / 4;
        this.colSize = a == null ? 0 : a.numComponents;
        a = attributes.findByUsage(4);
        this.cpOffset = a == null ? -1 : a.offset / 4;
        a = attributes.findByUsage(16);
        if (a != null) {
            i = a.offset / 4;
        }
        this.uvOffset = i;
        setColor(null);
        this.primitiveType = primitiveType;
    }

    private void endpart() {
        if (this.part != null) {
            this.part.indexOffset = this.istart;
            this.part.numVertices = this.indices.size - this.istart;
            this.istart = this.indices.size;
            this.part = null;
        }
    }

    public MeshPart part(String id, int primitiveType) {
        if (this.attributes == null) {
            throw new RuntimeException("Call begin() first");
        }
        endpart();
        this.part = new MeshPart();
        this.part.id = id;
        this.part.primitiveType = primitiveType;
        this.primitiveType = primitiveType;
        this.parts.add(this.part);
        setColor(null);
        return this.part;
    }

    public Mesh end() {
        if (this.attributes == null) {
            throw new RuntimeException("Call begin() first");
        }
        endpart();
        Mesh mesh = new Mesh(true, this.vertices.size, this.indices.size, this.attributes);
        mesh.setVertices(this.vertices.items, 0, this.vertices.size);
        mesh.setIndices(this.indices.items, 0, this.indices.size);
        Iterator i$ = this.parts.iterator();
        while (i$.hasNext()) {
            ((MeshPart) i$.next()).mesh = mesh;
        }
        this.parts.clear();
        this.attributes = null;
        this.vertices.clear();
        this.indices.clear();
        return mesh;
    }

    public VertexAttributes getAttributes() {
        return this.attributes;
    }

    public MeshPart getMeshPart() {
        return this.part;
    }

    static {
        vectorPool = new C03641();
        vectorArray = new Array();
    }

    private Vector3 tmp(float x, float y, float z) {
        Vector3 result = ((Vector3) vectorPool.obtain()).set(x, y, z);
        vectorArray.add(result);
        return result;
    }

    private Vector3 tmp(Vector3 copyFrom) {
        return tmp(copyFrom.f105x, copyFrom.f106y, copyFrom.f107z);
    }

    private void cleanup() {
        vectorPool.freeAll(vectorArray);
        vectorArray.clear();
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        this.colorSet = true;
    }

    public void setColor(Color color) {
        boolean z = color != null;
        this.colorSet = z;
        if (z) {
            this.color.set(color);
        }
    }

    public void setUVRange(float u1, float v1, float u2, float v2) {
        this.uMin = u1;
        this.vMin = v1;
        this.uMax = u2;
        this.vMax = v2;
    }

    public void ensureVertices(int numVertices) {
        this.vertices.ensureCapacity(this.vertex.length * numVertices);
    }

    public void ensureIndices(int numIndices) {
        this.indices.ensureCapacity(numIndices);
    }

    public void ensureCapacity(int numVertices, int numIndices) {
        ensureVertices(numVertices);
        ensureIndices(numIndices);
    }

    public void ensureTriangleIndices(int numTriangles) {
        if (this.primitiveType == 1) {
            ensureIndices(numTriangles * 6);
        } else {
            ensureIndices(numTriangles * 3);
        }
    }

    public void ensureTriangles(int numVertices, int numTriangles) {
        ensureVertices(numVertices);
        ensureTriangleIndices(numTriangles);
    }

    public void ensureTriangles(int numTriangles) {
        ensureTriangles(numTriangles * 3, numTriangles);
    }

    public void ensureRectangleIndices(int numRectangles) {
        if (this.primitiveType == 0) {
            ensureIndices(numRectangles * 4);
        } else if (this.primitiveType == 1) {
            ensureIndices(numRectangles * 8);
        } else {
            ensureIndices(numRectangles * 6);
        }
    }

    public void ensureRectangles(int numVertices, int numRectangles) {
        ensureVertices(numVertices);
        ensureRectangleIndices(numRectangles);
    }

    public void ensureRectangles(int numRectangles) {
        ensureRectangles(numRectangles * 4, numRectangles);
    }

    public short vertex(Vector3 pos, Vector3 nor, Color col, Vector2 uv) {
        if (this.vindex >= Short.MAX_VALUE) {
            throw new GdxRuntimeException("Too many vertices used");
        }
        if (col == null && this.colorSet) {
            col = this.color;
        }
        if (pos != null) {
            this.vertex[this.posOffset] = pos.f105x;
            if (this.posSize > 1) {
                this.vertex[this.posOffset + 1] = pos.f106y;
            }
            if (this.posSize > 2) {
                this.vertex[this.posOffset + 2] = pos.f107z;
            }
        }
        if (nor != null && this.norOffset >= 0) {
            this.vertex[this.norOffset] = nor.f105x;
            this.vertex[this.norOffset + 1] = nor.f106y;
            this.vertex[this.norOffset + 2] = nor.f107z;
        }
        if (col != null) {
            if (this.colOffset >= 0) {
                this.vertex[this.colOffset] = col.f40r;
                this.vertex[this.colOffset + 1] = col.f39g;
                this.vertex[this.colOffset + 2] = col.f38b;
                if (this.colSize > 3) {
                    this.vertex[this.colOffset + 3] = col.f37a;
                }
            } else if (this.cpOffset > 0) {
                this.vertex[this.cpOffset] = col.toFloatBits();
            }
        }
        if (uv != null && this.uvOffset >= 0) {
            this.vertex[this.uvOffset] = uv.f100x;
            this.vertex[this.uvOffset + 1] = uv.f101y;
        }
        this.vertices.addAll(this.vertex);
        short s = this.vindex;
        this.vindex = (short) (s + 1);
        return s;
    }

    public short lastIndex() {
        return (short) (this.vindex - 1);
    }

    public short vertex(float... values) {
        this.vertices.addAll(values);
        this.vindex = (short) (this.vindex + (values.length / this.stride));
        return (short) (this.vindex - 1);
    }

    public short vertex(VertexInfo info) {
        Vector2 vector2 = null;
        Vector3 vector3 = info.hasPosition ? info.position : null;
        Vector3 vector32 = info.hasNormal ? info.normal : null;
        Color color = info.hasColor ? info.color : null;
        if (info.hasUV) {
            vector2 = info.uv;
        }
        return vertex(vector3, vector32, color, vector2);
    }

    public void index(short value) {
        this.indices.add(value);
    }

    public void index(short value1, short value2) {
        ensureIndices(2);
        this.indices.add(value1);
        this.indices.add(value2);
    }

    public void index(short value1, short value2, short value3) {
        ensureIndices(3);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
    }

    public void index(short value1, short value2, short value3, short value4) {
        ensureIndices(4);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
    }

    public void index(short value1, short value2, short value3, short value4, short value5, short value6) {
        ensureIndices(6);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
        this.indices.add(value5);
        this.indices.add(value6);
    }

    public void index(short value1, short value2, short value3, short value4, short value5, short value6, short value7, short value8) {
        ensureIndices(8);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
        this.indices.add(value5);
        this.indices.add(value6);
        this.indices.add(value7);
        this.indices.add(value8);
    }

    public void line(short index1, short index2) {
        if (this.primitiveType != 1) {
            throw new GdxRuntimeException("Incorrect primitive type");
        }
        index(index1, index2);
    }

    public void line(VertexInfo p1, VertexInfo p2) {
        ensureVertices(2);
        line(vertex(p1), vertex(p2));
    }

    public void line(Vector3 p1, Vector3 p2) {
        line(this.vertTmp1.set(p1, null, null, null), this.vertTmp2.set(p2, null, null, null));
    }

    public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
        line(this.vertTmp1.set(null, null, null, null).setPos(x1, y1, z1), this.vertTmp2.set(null, null, null, null).setPos(x2, y2, z2));
    }

    public void line(Vector3 p1, Color c1, Vector3 p2, Color c2) {
        line(this.vertTmp1.set(p1, null, c1, null), this.vertTmp2.set(p2, null, c2, null));
    }

    public void triangle(short index1, short index2, short index3) {
        if (this.primitiveType == 4 || this.primitiveType == 0) {
            index(index1, index2, index3);
        } else if (this.primitiveType == 1) {
            index(index1, index2, index2, index3, index3, index1);
        } else {
            throw new GdxRuntimeException("Incorrect primitive type");
        }
    }

    public void triangle(VertexInfo p1, VertexInfo p2, VertexInfo p3) {
        ensureVertices(3);
        triangle(vertex(p1), vertex(p2), vertex(p3));
    }

    public void triangle(Vector3 p1, Vector3 p2, Vector3 p3) {
        triangle(this.vertTmp1.set(p1, null, null, null), this.vertTmp2.set(p2, null, null, null), this.vertTmp3.set(p3, null, null, null));
    }

    public void triangle(Vector3 p1, Color c1, Vector3 p2, Color c2, Vector3 p3, Color c3) {
        triangle(this.vertTmp1.set(p1, null, c1, null), this.vertTmp2.set(p2, null, c2, null), this.vertTmp3.set(p3, null, c3, null));
    }

    public void rect(short corner00, short corner10, short corner11, short corner01) {
        if (this.primitiveType == 4) {
            index(corner00, corner10, corner11, corner11, corner01, corner00);
        } else if (this.primitiveType == 1) {
            index(corner00, corner10, corner10, corner11, corner11, corner01, corner01, corner00);
        } else if (this.primitiveType == 0) {
            index(corner00, corner10, corner11, corner01);
        } else {
            throw new GdxRuntimeException("Incorrect primitive type");
        }
    }

    public void rect(VertexInfo corner00, VertexInfo corner10, VertexInfo corner11, VertexInfo corner01) {
        ensureVertices(4);
        rect(vertex(corner00), vertex(corner10), vertex(corner11), vertex(corner01));
    }

    public void rect(Vector3 corner00, Vector3 corner10, Vector3 corner11, Vector3 corner01, Vector3 normal) {
        rect(this.vertTmp1.set(corner00, normal, null, null).setUV(this.uMin, this.vMin), this.vertTmp2.set(corner10, normal, null, null).setUV(this.uMax, this.vMin), this.vertTmp3.set(corner11, normal, null, null).setUV(this.uMax, this.vMax), this.vertTmp4.set(corner01, normal, null, null).setUV(this.uMin, this.vMax));
    }

    public void rect(float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11, float x01, float y01, float z01, float normalX, float normalY, float normalZ) {
        rect(this.vertTmp1.set(null, null, null, null).setPos(x00, y00, z00).setNor(normalX, normalY, normalZ).setUV(this.uMin, this.vMin), this.vertTmp2.set(null, null, null, null).setPos(x10, y10, z10).setNor(normalX, normalY, normalZ).setUV(this.uMax, this.vMin), this.vertTmp3.set(null, null, null, null).setPos(x11, y11, z11).setNor(normalX, normalY, normalZ).setUV(this.uMax, this.vMax), this.vertTmp4.set(null, null, null, null).setPos(x01, y01, z01).setNor(normalX, normalY, normalZ).setUV(this.uMin, this.vMax));
    }

    public void patch(VertexInfo corner00, VertexInfo corner10, VertexInfo corner11, VertexInfo corner01, int divisionsU, int divisionsV) {
        ensureRectangles((divisionsV + 1) * (divisionsU + 1), divisionsV * divisionsU);
        for (int u = 0; u <= divisionsU; u++) {
            float alphaU = ((float) u) / ((float) divisionsU);
            this.vertTmp5.set(corner00).lerp(corner10, alphaU);
            this.vertTmp6.set(corner01).lerp(corner11, alphaU);
            int v = 0;
            while (v <= divisionsV) {
                short idx = vertex(this.vertTmp7.set(this.vertTmp5).lerp(this.vertTmp6, ((float) v) / ((float) divisionsV)));
                if (u > 0 && v > 0) {
                    rect((short) ((idx - divisionsV) - 2), (short) (idx - 1), idx, (short) ((idx - divisionsV) - 1));
                }
                v++;
            }
        }
    }

    public void patch(Vector3 corner00, Vector3 corner10, Vector3 corner11, Vector3 corner01, Vector3 normal, int divisionsU, int divisionsV) {
        patch(this.vertTmp1.set(corner00, normal, null, null).setUV(this.uMin, this.vMin), this.vertTmp2.set(corner10, normal, null, null).setUV(this.uMax, this.vMin), this.vertTmp3.set(corner11, normal, null, null).setUV(this.uMax, this.vMax), this.vertTmp4.set(corner01, normal, null, null).setUV(this.uMin, this.vMax), divisionsU, divisionsV);
    }

    public void patch(float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11, float x01, float y01, float z01, float normalX, float normalY, float normalZ, int divisionsU, int divisionsV) {
        patch(this.vertTmp1.set(null).setPos(x00, y00, z00).setNor(normalX, normalY, normalZ).setUV(this.uMin, this.vMin), this.vertTmp2.set(null).setPos(x10, y10, z10).setNor(normalX, normalY, normalZ).setUV(this.uMax, this.vMin), this.vertTmp3.set(null).setPos(x11, y11, z11).setNor(normalX, normalY, normalZ).setUV(this.uMax, this.vMax), this.vertTmp4.set(null).setPos(x01, y01, z01).setNor(normalX, normalY, normalZ).setUV(this.uMin, this.vMax), divisionsU, divisionsV);
    }

    public void box(VertexInfo corner000, VertexInfo corner010, VertexInfo corner100, VertexInfo corner110, VertexInfo corner001, VertexInfo corner011, VertexInfo corner101, VertexInfo corner111) {
        ensureVertices(8);
        short i000 = vertex(corner000);
        short i100 = vertex(corner100);
        short i110 = vertex(corner110);
        short i010 = vertex(corner010);
        short i001 = vertex(corner001);
        short i101 = vertex(corner101);
        short i111 = vertex(corner111);
        short i011 = vertex(corner011);
        if (this.primitiveType == 1) {
            ensureIndices(24);
            rect(i000, i100, i110, i010);
            rect(i101, i001, i011, i111);
            index(i000, i001, i010, i011, i110, i111, i100, i101);
        } else if (this.primitiveType != 0) {
            ensureRectangleIndices(2);
            rect(i000, i100, i110, i010);
            rect(i101, i001, i011, i111);
        } else {
            ensureRectangleIndices(6);
            rect(i000, i100, i110, i010);
            rect(i101, i001, i011, i111);
            rect(i000, i010, i011, i001);
            rect(i101, i111, i110, i100);
            rect(i101, i100, i000, i001);
            rect(i110, i111, i011, i010);
        }
    }

    public void box(Vector3 corner000, Vector3 corner010, Vector3 corner100, Vector3 corner110, Vector3 corner001, Vector3 corner011, Vector3 corner101, Vector3 corner111) {
        if (this.norOffset >= 0 || this.uvOffset >= 0) {
            ensureRectangles(6);
            Vector3 nor = this.tempV1.set(corner000).lerp(corner110, 0.5f).sub(this.tempV2.set(corner001).lerp(corner111, 0.5f)).nor();
            rect(corner000, corner010, corner110, corner100, nor);
            rect(corner011, corner001, corner101, corner111, nor.scl((float) GroundOverlayOptions.NO_DIMENSION));
            nor = this.tempV1.set(corner000).lerp(corner101, 0.5f).sub(this.tempV2.set(corner010).lerp(corner111, 0.5f)).nor();
            rect(corner001, corner000, corner100, corner101, nor);
            rect(corner010, corner011, corner111, corner110, nor.scl((float) GroundOverlayOptions.NO_DIMENSION));
            nor = this.tempV1.set(corner000).lerp(corner011, 0.5f).sub(this.tempV2.set(corner100).lerp(corner111, 0.5f)).nor();
            rect(corner001, corner011, corner010, corner000, nor);
            rect(corner100, corner110, corner111, corner101, nor.scl((float) GroundOverlayOptions.NO_DIMENSION));
            return;
        }
        box(this.vertTmp1.set(corner000, null, null, null), this.vertTmp2.set(corner010, null, null, null), this.vertTmp3.set(corner100, null, null, null), this.vertTmp4.set(corner110, null, null, null), this.vertTmp5.set(corner001, null, null, null), this.vertTmp6.set(corner011, null, null, null), this.vertTmp7.set(corner101, null, null, null), this.vertTmp8.set(corner111, null, null, null));
    }

    public void box(Matrix4 transform) {
        box(tmp(-0.5f, -0.5f, -0.5f).mul(transform), tmp(-0.5f, 0.5f, -0.5f).mul(transform), tmp(0.5f, -0.5f, -0.5f).mul(transform), tmp(0.5f, 0.5f, -0.5f).mul(transform), tmp(-0.5f, -0.5f, 0.5f).mul(transform), tmp(-0.5f, 0.5f, 0.5f).mul(transform), tmp(0.5f, -0.5f, 0.5f).mul(transform), tmp(0.5f, 0.5f, 0.5f).mul(transform));
        cleanup();
    }

    public void box(float width, float height, float depth) {
        box(this.matTmp1.setToScaling(width, height, depth));
    }

    public void box(float x, float y, float z, float width, float height, float depth) {
        box(this.matTmp1.setToScaling(width, height, depth).trn(x, y, z));
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        circle(radius, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0.0f, 360.0f);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal) {
        circle(radius, divisions, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal) {
        circle(radius, divisions, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z, tangent.f105x, tangent.f106y, tangent.f107z, binormal.f105x, binormal.f106y, binormal.f107z);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ) {
        circle(radius, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, 0.0f, 360.0f);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        ellipse(radius * 2.0f, radius * 2.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, float angleFrom, float angleTo) {
        circle(radius, divisions, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal, float angleFrom, float angleTo) {
        circle(radius, divisions, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z, tangent.f105x, tangent.f106y, tangent.f107z, binormal.f105x, binormal.f106y, binormal.f107z, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        ellipse(radius * 2.0f, radius * 2.0f, 0.0f, 0.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        ellipse(width, height, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal) {
        ellipse(width, height, divisions, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal) {
        float f = width;
        float f2 = height;
        int i = divisions;
        ellipse(f, f2, i, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z, tangent.f105x, tangent.f106y, tangent.f107z, binormal.f105x, binormal.f106y, binormal.f107z);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ) {
        ellipse(width, height, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        ellipse(width, height, 0.0f, 0.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, float angleFrom, float angleTo) {
        ellipse(width, height, 0.0f, 0.0f, divisions, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal, float angleFrom, float angleTo) {
        float f = width;
        float f2 = height;
        int i = divisions;
        ellipse(f, f2, 0.0f, 0.0f, i, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z, tangent.f105x, tangent.f106y, tangent.f107z, binormal.f105x, binormal.f106y, binormal.f107z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        ellipse(width, height, 0.0f, 0.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, Vector3 center, Vector3 normal) {
        ellipse(width, height, innerWidth, innerHeight, divisions, center.f105x, center.f106y, center.f107z, normal.f105x, normal.f106y, normal.f107z, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        ellipse(width, height, innerWidth, innerHeight, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        this.tempV1.set(normalX, normalY, normalZ).crs(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.tempV2.set(normalX, normalY, normalZ).crs(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        if (this.tempV2.len2() > this.tempV1.len2()) {
            this.tempV1.set(this.tempV2);
        }
        this.tempV2.set(this.tempV1.nor()).crs(normalX, normalY, normalZ).nor();
        float f = width;
        float f2 = height;
        float f3 = innerWidth;
        float f4 = innerHeight;
        int i = divisions;
        float f5 = centerX;
        float f6 = centerY;
        float f7 = centerZ;
        float f8 = normalX;
        float f9 = normalY;
        float f10 = normalZ;
        ellipse(f, f2, f3, f4, i, f5, f6, f7, f8, f9, f10, this.tempV1.f105x, this.tempV1.f106y, this.tempV1.f107z, this.tempV2.f105x, this.tempV2.f106y, this.tempV2.f107z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        if (innerWidth <= 0.0f || innerHeight <= 0.0f) {
            ensureTriangles(divisions + 2, divisions);
        } else if (innerWidth == width && innerHeight == height) {
            ensureVertices(divisions + 1);
            ensureIndices(divisions + 1);
            int i = this.primitiveType;
            if (r0 != 1) {
                throw new GdxRuntimeException("Incorrect primitive type : expect GL_LINES because innerWidth == width && innerHeight == height");
            }
        } else {
            ensureRectangles((divisions + 1) * 2, divisions + 1);
        }
        float ao = MathUtils.degreesToRadians * angleFrom;
        float step = (MathUtils.degreesToRadians * (angleTo - angleFrom)) / ((float) divisions);
        Vector3 sxEx = this.tempV1.set(tangentX, tangentY, tangentZ).scl(0.5f * width);
        Vector3 syEx = this.tempV2.set(binormalX, binormalY, binormalZ).scl(0.5f * height);
        Vector3 sxIn = this.tempV3.set(tangentX, tangentY, tangentZ).scl(0.5f * innerWidth);
        Vector3 syIn = this.tempV4.set(binormalX, binormalY, binormalZ).scl(0.5f * innerHeight);
        VertexInfo currIn = this.vertTmp3.set(null, null, null, null);
        currIn.hasNormal = true;
        currIn.hasPosition = true;
        currIn.hasUV = true;
        currIn.uv.set(0.5f, 0.5f);
        currIn.position.set(centerX, centerY, centerZ);
        currIn.normal.set(normalX, normalY, normalZ);
        VertexInfo currEx = this.vertTmp4.set(null, null, null, null);
        currEx.hasNormal = true;
        currEx.hasPosition = true;
        currEx.hasUV = true;
        currEx.uv.set(0.5f, 0.5f);
        currEx.position.set(centerX, centerY, centerZ);
        currEx.normal.set(normalX, normalY, normalZ);
        short center = vertex(currEx);
        float us = 0.5f * (innerWidth / width);
        float vs = 0.5f * (innerHeight / height);
        for (int i2 = 0; i2 <= divisions; i2++) {
            float angle = ao + (((float) i2) * step);
            float x = MathUtils.cos(angle);
            float y = MathUtils.sin(angle);
            currEx.position.set(centerX, centerY, centerZ).add((sxEx.f105x * x) + (syEx.f105x * y), (sxEx.f106y * x) + (syEx.f106y * y), (sxEx.f107z * x) + (syEx.f107z * y));
            currEx.uv.set(0.5f + (0.5f * x), 0.5f + (0.5f * y));
            vertex(currEx);
            if (innerWidth <= 0.0f || innerHeight <= 0.0f) {
                if (i2 != 0) {
                    triangle((short) (this.vindex - 1), (short) (this.vindex - 2), center);
                }
            } else if (innerWidth != width || innerHeight != height) {
                currIn.position.set(centerX, centerY, centerZ).add((sxIn.f105x * x) + (syIn.f105x * y), (sxIn.f106y * x) + (syIn.f106y * y), (sxIn.f107z * x) + (syIn.f107z * y));
                currIn.uv.set(0.5f + (us * x), 0.5f + (vs * y));
                vertex(currIn);
                if (i2 != 0) {
                    rect((short) (this.vindex - 1), (short) (this.vindex - 2), (short) (this.vindex - 4), (short) (this.vindex - 3));
                }
            } else if (i2 != 0) {
                line((short) (this.vindex - 1), (short) (this.vindex - 2));
            }
        }
    }

    public void cylinder(float width, float height, float depth, int divisions) {
        cylinder(width, height, depth, divisions, 0.0f, 360.0f);
    }

    public void cylinder(float width, float height, float depth, int divisions, float angleFrom, float angleTo) {
        cylinder(width, height, depth, divisions, angleFrom, angleTo, true);
    }

    public void cylinder(float width, float height, float depth, int divisions, float angleFrom, float angleTo, boolean close) {
        float hw = width * 0.5f;
        float hh = height * 0.5f;
        float hd = depth * 0.5f;
        float ao = MathUtils.degreesToRadians * angleFrom;
        float step = (MathUtils.degreesToRadians * (angleTo - angleFrom)) / ((float) divisions);
        float us = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) divisions);
        VertexInfo curr1 = this.vertTmp3.set(null, null, null, null);
        curr1.hasNormal = true;
        curr1.hasPosition = true;
        curr1.hasUV = true;
        VertexInfo curr2 = this.vertTmp4.set(null, null, null, null);
        curr2.hasNormal = true;
        curr2.hasPosition = true;
        curr2.hasUV = true;
        ensureRectangles((divisions + 1) * 2, divisions);
        for (int i = 0; i <= divisions; i++) {
            float angle = ao + (((float) i) * step);
            float u = TextTrackStyle.DEFAULT_FONT_SCALE - (((float) i) * us);
            curr1.position.set(MathUtils.cos(angle) * hw, 0.0f, MathUtils.sin(angle) * hd);
            curr1.normal.set(curr1.position).nor();
            curr1.position.f106y = -hh;
            curr1.uv.set(u, TextTrackStyle.DEFAULT_FONT_SCALE);
            curr2.position.set(curr1.position);
            curr2.normal.set(curr1.normal);
            curr2.position.f106y = hh;
            curr2.uv.set(u, 0.0f);
            vertex(curr1);
            vertex(curr2);
            if (i != 0) {
                rect((short) (this.vindex - 3), (short) (this.vindex - 1), (short) (this.vindex - 2), (short) (this.vindex - 4));
            }
        }
        if (close) {
            ellipse(width, depth, 0.0f, 0.0f, divisions, 0.0f, hh, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, angleFrom, angleTo);
            float f = width;
            float f2 = depth;
            int i2 = divisions;
            ellipse(f, f2, 0.0f, 0.0f, i2, 0.0f, -hh, 0.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, BitmapDescriptorFactory.HUE_CYAN - angleTo, BitmapDescriptorFactory.HUE_CYAN - angleFrom);
        }
    }

    public void cone(float width, float height, float depth, int divisions) {
        cone(width, height, depth, divisions, 0.0f, 360.0f);
    }

    public void cone(float width, float height, float depth, int divisions, float angleFrom, float angleTo) {
        ensureTriangles(divisions + 2, divisions);
        float hw = width * 0.5f;
        float hh = height * 0.5f;
        float hd = depth * 0.5f;
        float ao = MathUtils.degreesToRadians * angleFrom;
        float step = (MathUtils.degreesToRadians * (angleTo - angleFrom)) / ((float) divisions);
        float us = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) divisions);
        VertexInfo curr1 = this.vertTmp3.set(null, null, null, null);
        curr1.hasNormal = true;
        curr1.hasPosition = true;
        curr1.hasUV = true;
        int base = vertex(this.vertTmp4.set(null, null, null, null).setPos(0.0f, hh, 0.0f).setNor(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f).setUV(0.5f, 0.0f));
        for (int i = 0; i <= divisions; i++) {
            float angle = ao + (((float) i) * step);
            float u = TextTrackStyle.DEFAULT_FONT_SCALE - (((float) i) * us);
            curr1.position.set(MathUtils.cos(angle) * hw, 0.0f, MathUtils.sin(angle) * hd);
            curr1.normal.set(curr1.position).nor();
            curr1.position.f106y = -hh;
            curr1.uv.set(u, TextTrackStyle.DEFAULT_FONT_SCALE);
            vertex(curr1);
            if (i != 0) {
                triangle((short) base, (short) (this.vindex - 1), (short) (this.vindex - 2));
            }
        }
        ellipse(width, depth, 0.0f, 0.0f, divisions, 0.0f, -hh, 0.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, BitmapDescriptorFactory.HUE_CYAN - angleTo, BitmapDescriptorFactory.HUE_CYAN - angleFrom);
    }

    public void sphere(float width, float height, float depth, int divisionsU, int divisionsV) {
        sphere(width, height, depth, divisionsU, divisionsV, 0.0f, 360.0f, 0.0f, BitmapDescriptorFactory.HUE_CYAN);
    }

    public void sphere(Matrix4 transform, float width, float height, float depth, int divisionsU, int divisionsV) {
        sphere(transform, width, height, depth, divisionsU, divisionsV, 0.0f, 360.0f, 0.0f, BitmapDescriptorFactory.HUE_CYAN);
    }

    public void sphere(float width, float height, float depth, int divisionsU, int divisionsV, float angleUFrom, float angleUTo, float angleVFrom, float angleVTo) {
        sphere(this.matTmp1.idt(), width, height, depth, divisionsU, divisionsV, angleUFrom, angleUTo, angleVFrom, angleVTo);
    }

    public void sphere(Matrix4 transform, float width, float height, float depth, int divisionsU, int divisionsV, float angleUFrom, float angleUTo, float angleVFrom, float angleVTo) {
        float hw = width * 0.5f;
        float hh = height * 0.5f;
        float hd = depth * 0.5f;
        float auo = MathUtils.degreesToRadians * angleUFrom;
        float stepU = (MathUtils.degreesToRadians * (angleUTo - angleUFrom)) / ((float) divisionsU);
        float avo = MathUtils.degreesToRadians * angleVFrom;
        float stepV = (MathUtils.degreesToRadians * (angleVTo - angleVFrom)) / ((float) divisionsV);
        float us = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) divisionsU);
        float vs = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) divisionsV);
        VertexInfo curr1 = this.vertTmp3.set(null, null, null, null);
        curr1.hasNormal = true;
        curr1.hasPosition = true;
        curr1.hasUV = true;
        ensureRectangles((divisionsV + 1) * (divisionsU + 1), divisionsV * divisionsU);
        for (int iv = 0; iv <= divisionsV; iv++) {
            float angleV = avo + (((float) iv) * stepV);
            float v = vs * ((float) iv);
            float t = MathUtils.sin(angleV);
            float h = MathUtils.cos(angleV) * hh;
            int iu = 0;
            while (iu <= divisionsU) {
                float angleU = auo + (((float) iu) * stepU);
                float u = TextTrackStyle.DEFAULT_FONT_SCALE - (((float) iu) * us);
                curr1.position.set((MathUtils.cos(angleU) * hw) * t, h, (MathUtils.sin(angleU) * hd) * t).mul(transform);
                curr1.normal.set(curr1.position).nor();
                curr1.uv.set(u, v);
                vertex(curr1);
                if (iv > 0 && iu > 0) {
                    rect((short) (this.vindex - 1), (short) (this.vindex - 2), (short) (this.vindex - (divisionsU + 3)), (short) (this.vindex - (divisionsU + 2)));
                }
                iu++;
            }
        }
    }

    public void capsule(float radius, float height, int divisions) {
        if (height < 2.0f * radius) {
            throw new GdxRuntimeException("Height must be at least twice the radius");
        }
        float d = 2.0f * radius;
        cylinder(d, height - d, d, divisions, 0.0f, 360.0f, false);
        sphere(this.matTmp1.setToTranslation(0.0f, 0.5f * (height - d), 0.0f), d, d, d, divisions, divisions, 0.0f, 360.0f, 0.0f, 90.0f);
        sphere(this.matTmp1.setToTranslation(0.0f, -0.5f * (height - d), 0.0f), d, d, d, divisions, divisions, 0.0f, 360.0f, 90.0f, BitmapDescriptorFactory.HUE_CYAN);
    }
}
