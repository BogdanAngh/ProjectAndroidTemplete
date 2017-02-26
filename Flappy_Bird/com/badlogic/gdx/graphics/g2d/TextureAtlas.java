package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TextureAtlas implements Disposable {
    static final Comparator<Region> indexComparator;
    static final String[] tuple;
    private final Array<AtlasRegion> regions;
    private final HashSet<Texture> textures;

    /* renamed from: com.badlogic.gdx.graphics.g2d.TextureAtlas.1 */
    static class C00561 implements Comparator<Region> {
        C00561() {
        }

        public int compare(Region region1, Region region2) {
            int i1 = region1.index;
            if (i1 == -1) {
                i1 = Integer.MAX_VALUE;
            }
            int i2 = region2.index;
            if (i2 == -1) {
                i2 = Integer.MAX_VALUE;
            }
            return i1 - i2;
        }
    }

    public static class TextureAtlasData {
        final Array<Page> pages;
        final Array<Region> regions;

        public static class Page {
            public final Format format;
            public final TextureFilter magFilter;
            public final TextureFilter minFilter;
            public Texture texture;
            public final FileHandle textureFile;
            public final TextureWrap uWrap;
            public final boolean useMipMaps;
            public final TextureWrap vWrap;

            public Page(FileHandle handle, boolean useMipMaps, Format format, TextureFilter minFilter, TextureFilter magFilter, TextureWrap uWrap, TextureWrap vWrap) {
                this.textureFile = handle;
                this.useMipMaps = useMipMaps;
                this.format = format;
                this.minFilter = minFilter;
                this.magFilter = magFilter;
                this.uWrap = uWrap;
                this.vWrap = vWrap;
            }
        }

        public static class Region {
            public boolean flip;
            public int height;
            public int index;
            public int left;
            public String name;
            public float offsetX;
            public float offsetY;
            public int originalHeight;
            public int originalWidth;
            public int[] pads;
            public Page page;
            public boolean rotate;
            public int[] splits;
            public int top;
            public int width;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public TextureAtlasData(com.badlogic.gdx.files.FileHandle r28, com.badlogic.gdx.files.FileHandle r29, boolean r30) {
            /*
            r27 = this;
            r27.<init>();
            r6 = new com.badlogic.gdx.utils.Array;
            r6.<init>();
            r0 = r27;
            r0.pages = r6;
            r6 = new com.badlogic.gdx.utils.Array;
            r6.<init>();
            r0 = r27;
            r0.regions = r6;
            r18 = new java.io.BufferedReader;
            r6 = new java.io.InputStreamReader;
            r23 = r28.read();
            r0 = r23;
            r6.<init>(r0);
            r23 = 64;
            r0 = r18;
            r1 = r23;
            r0.<init>(r6, r1);
            r4 = 0;
            r17 = r4;
        L_0x002e:
            r16 = r18.readLine();	 Catch:{ Exception -> 0x024e }
            if (r16 != 0) goto L_0x0060;
        L_0x0034:
            com.badlogic.gdx.utils.StreamUtils.closeQuietly(r18);
            r23 = new com.badlogic.gdx.utils.Sort;
            r23.<init>();
            r0 = r27;
            r6 = r0.regions;
            r6 = r6.items;
            r6 = (java.lang.Object[]) r6;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.indexComparator;
            r25 = 0;
            r0 = r27;
            r0 = r0.regions;
            r26 = r0;
            r0 = r26;
            r0 = r0.size;
            r26 = r0;
            r0 = r23;
            r1 = r24;
            r2 = r25;
            r3 = r26;
            r0.sort(r6, r1, r2, r3);
            return;
        L_0x0060:
            r6 = r16.trim();	 Catch:{ Exception -> 0x024e }
            r6 = r6.length();	 Catch:{ Exception -> 0x024e }
            if (r6 != 0) goto L_0x006e;
        L_0x006a:
            r4 = 0;
        L_0x006b:
            r17 = r4;
            goto L_0x002e;
        L_0x006e:
            if (r17 != 0) goto L_0x00f7;
        L_0x0070:
            r0 = r29;
            r1 = r16;
            r5 = r0.child(r1);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.readValue(r18);	 Catch:{ Exception -> 0x024e }
            r7 = com.badlogic.gdx.graphics.Pixmap.Format.valueOf(r6);	 Catch:{ Exception -> 0x024e }
            com.badlogic.gdx.graphics.g2d.TextureAtlas.readTuple(r18);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 0;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r8 = com.badlogic.gdx.graphics.Texture.TextureFilter.valueOf(r6);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 1;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r9 = com.badlogic.gdx.graphics.Texture.TextureFilter.valueOf(r6);	 Catch:{ Exception -> 0x024e }
            r12 = com.badlogic.gdx.graphics.g2d.TextureAtlas.readValue(r18);	 Catch:{ Exception -> 0x024e }
            r10 = com.badlogic.gdx.graphics.Texture.TextureWrap.ClampToEdge;	 Catch:{ Exception -> 0x024e }
            r11 = com.badlogic.gdx.graphics.Texture.TextureWrap.ClampToEdge;	 Catch:{ Exception -> 0x024e }
            r6 = "x";
            r6 = r12.equals(r6);	 Catch:{ Exception -> 0x024e }
            if (r6 == 0) goto L_0x00df;
        L_0x00a7:
            r10 = com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;	 Catch:{ Exception -> 0x024e }
        L_0x00a9:
            r4 = new com.badlogic.gdx.graphics.g2d.TextureAtlas$TextureAtlasData$Page;	 Catch:{ Exception -> 0x024e }
            r6 = r8.isMipMap();	 Catch:{ Exception -> 0x024e }
            r4.<init>(r5, r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x024e }
            r0 = r27;
            r6 = r0.pages;	 Catch:{ Exception -> 0x00ba }
            r6.add(r4);	 Catch:{ Exception -> 0x00ba }
            goto L_0x006b;
        L_0x00ba:
            r13 = move-exception;
        L_0x00bb:
            r6 = new com.badlogic.gdx.utils.GdxRuntimeException;	 Catch:{ all -> 0x00da }
            r23 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00da }
            r23.<init>();	 Catch:{ all -> 0x00da }
            r24 = "Error reading pack file: ";
            r23 = r23.append(r24);	 Catch:{ all -> 0x00da }
            r0 = r23;
            r1 = r28;
            r23 = r0.append(r1);	 Catch:{ all -> 0x00da }
            r23 = r23.toString();	 Catch:{ all -> 0x00da }
            r0 = r23;
            r6.<init>(r0, r13);	 Catch:{ all -> 0x00da }
            throw r6;	 Catch:{ all -> 0x00da }
        L_0x00da:
            r6 = move-exception;
            com.badlogic.gdx.utils.StreamUtils.closeQuietly(r18);
            throw r6;
        L_0x00df:
            r6 = "y";
            r6 = r12.equals(r6);	 Catch:{ Exception -> 0x024e }
            if (r6 == 0) goto L_0x00ea;
        L_0x00e7:
            r11 = com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;	 Catch:{ Exception -> 0x024e }
            goto L_0x00a9;
        L_0x00ea:
            r6 = "xy";
            r6 = r12.equals(r6);	 Catch:{ Exception -> 0x024e }
            if (r6 == 0) goto L_0x00a9;
        L_0x00f2:
            r10 = com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;	 Catch:{ Exception -> 0x024e }
            r11 = com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;	 Catch:{ Exception -> 0x024e }
            goto L_0x00a9;
        L_0x00f7:
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.readValue(r18);	 Catch:{ Exception -> 0x024e }
            r6 = java.lang.Boolean.valueOf(r6);	 Catch:{ Exception -> 0x024e }
            r20 = r6.booleanValue();	 Catch:{ Exception -> 0x024e }
            com.badlogic.gdx.graphics.g2d.TextureAtlas.readTuple(r18);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 0;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r15 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 1;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r21 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            com.badlogic.gdx.graphics.g2d.TextureAtlas.readTuple(r18);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 0;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r22 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 1;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r14 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r19 = new com.badlogic.gdx.graphics.g2d.TextureAtlas$TextureAtlasData$Region;	 Catch:{ Exception -> 0x024e }
            r19.<init>();	 Catch:{ Exception -> 0x024e }
            r0 = r17;
            r1 = r19;
            r1.page = r0;	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.left = r15;	 Catch:{ Exception -> 0x024e }
            r0 = r21;
            r1 = r19;
            r1.top = r0;	 Catch:{ Exception -> 0x024e }
            r0 = r22;
            r1 = r19;
            r1.width = r0;	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.height = r14;	 Catch:{ Exception -> 0x024e }
            r0 = r16;
            r1 = r19;
            r1.name = r0;	 Catch:{ Exception -> 0x024e }
            r0 = r20;
            r1 = r19;
            r1.rotate = r0;	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.readTuple(r18);	 Catch:{ Exception -> 0x024e }
            r23 = 4;
            r0 = r23;
            if (r6 != r0) goto L_0x01f1;
        L_0x0166:
            r6 = 4;
            r6 = new int[r6];	 Catch:{ Exception -> 0x024e }
            r23 = 0;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 0;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r23 = 1;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 1;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r23 = 2;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 2;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r23 = 3;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 3;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.splits = r6;	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.readTuple(r18);	 Catch:{ Exception -> 0x024e }
            r23 = 4;
            r0 = r23;
            if (r6 != r0) goto L_0x01f1;
        L_0x01af:
            r6 = 4;
            r6 = new int[r6];	 Catch:{ Exception -> 0x024e }
            r23 = 0;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 0;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r23 = 1;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 1;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r23 = 2;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 2;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r23 = 3;
            r24 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r25 = 3;
            r24 = r24[r25];	 Catch:{ Exception -> 0x024e }
            r24 = java.lang.Integer.parseInt(r24);	 Catch:{ Exception -> 0x024e }
            r6[r23] = r24;	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.pads = r6;	 Catch:{ Exception -> 0x024e }
            com.badlogic.gdx.graphics.g2d.TextureAtlas.readTuple(r18);	 Catch:{ Exception -> 0x024e }
        L_0x01f1:
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 0;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r6 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.originalWidth = r6;	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 1;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r6 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.originalHeight = r6;	 Catch:{ Exception -> 0x024e }
            com.badlogic.gdx.graphics.g2d.TextureAtlas.readTuple(r18);	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 0;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r6 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r6 = (float) r6;	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.offsetX = r6;	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;	 Catch:{ Exception -> 0x024e }
            r23 = 1;
            r6 = r6[r23];	 Catch:{ Exception -> 0x024e }
            r6 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r6 = (float) r6;	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.offsetY = r6;	 Catch:{ Exception -> 0x024e }
            r6 = com.badlogic.gdx.graphics.g2d.TextureAtlas.readValue(r18);	 Catch:{ Exception -> 0x024e }
            r6 = java.lang.Integer.parseInt(r6);	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r0.index = r6;	 Catch:{ Exception -> 0x024e }
            if (r30 == 0) goto L_0x0241;
        L_0x023c:
            r6 = 1;
            r0 = r19;
            r0.flip = r6;	 Catch:{ Exception -> 0x024e }
        L_0x0241:
            r0 = r27;
            r6 = r0.regions;	 Catch:{ Exception -> 0x024e }
            r0 = r19;
            r6.add(r0);	 Catch:{ Exception -> 0x024e }
            r4 = r17;
            goto L_0x006b;
        L_0x024e:
            r13 = move-exception;
            r4 = r17;
            goto L_0x00bb;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.<init>(com.badlogic.gdx.files.FileHandle, com.badlogic.gdx.files.FileHandle, boolean):void");
        }

        public Array<Page> getPages() {
            return this.pages;
        }

        public Array<Region> getRegions() {
            return this.regions;
        }
    }

    public static class AtlasRegion extends TextureRegion {
        public int index;
        public String name;
        public float offsetX;
        public float offsetY;
        public int originalHeight;
        public int originalWidth;
        public int packedHeight;
        public int packedWidth;
        public int[] pads;
        public boolean rotate;
        public int[] splits;

        public AtlasRegion(Texture texture, int x, int y, int width, int height) {
            super(texture, x, y, width, height);
            this.originalWidth = width;
            this.originalHeight = height;
            this.packedWidth = width;
            this.packedHeight = height;
        }

        public AtlasRegion(AtlasRegion region) {
            setRegion((TextureRegion) region);
            this.index = region.index;
            this.name = region.name;
            this.offsetX = region.offsetX;
            this.offsetY = region.offsetY;
            this.packedWidth = region.packedWidth;
            this.packedHeight = region.packedHeight;
            this.originalWidth = region.originalWidth;
            this.originalHeight = region.originalHeight;
            this.rotate = region.rotate;
            this.splits = region.splits;
        }

        public void flip(boolean x, boolean y) {
            super.flip(x, y);
            if (x) {
                this.offsetX = (((float) this.originalWidth) - this.offsetX) - getRotatedPackedWidth();
            }
            if (y) {
                this.offsetY = (((float) this.originalHeight) - this.offsetY) - getRotatedPackedHeight();
            }
        }

        public float getRotatedPackedWidth() {
            return this.rotate ? (float) this.packedHeight : (float) this.packedWidth;
        }

        public float getRotatedPackedHeight() {
            return this.rotate ? (float) this.packedWidth : (float) this.packedHeight;
        }
    }

    public static class AtlasSprite extends Sprite {
        float originalOffsetX;
        float originalOffsetY;
        final AtlasRegion region;

        public AtlasSprite(AtlasRegion region) {
            this.region = new AtlasRegion(region);
            this.originalOffsetX = region.offsetX;
            this.originalOffsetY = region.offsetY;
            setRegion((TextureRegion) region);
            setOrigin(((float) region.originalWidth) / 2.0f, ((float) region.originalHeight) / 2.0f);
            int width = region.getRegionWidth();
            int height = region.getRegionHeight();
            if (region.rotate) {
                super.rotate90(true);
                super.setBounds(region.offsetX, region.offsetY, (float) height, (float) width);
            } else {
                super.setBounds(region.offsetX, region.offsetY, (float) width, (float) height);
            }
            setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public AtlasSprite(AtlasSprite sprite) {
            this.region = sprite.region;
            this.originalOffsetX = sprite.originalOffsetX;
            this.originalOffsetY = sprite.originalOffsetY;
            set(sprite);
        }

        public void setPosition(float x, float y) {
            super.setPosition(this.region.offsetX + x, this.region.offsetY + y);
        }

        public void setBounds(float x, float y, float width, float height) {
            float widthRatio = width / ((float) this.region.originalWidth);
            float heightRatio = height / ((float) this.region.originalHeight);
            this.region.offsetX = this.originalOffsetX * widthRatio;
            this.region.offsetY = this.originalOffsetY * heightRatio;
            super.setBounds(this.region.offsetX + x, this.region.offsetY + y, ((float) (this.region.rotate ? this.region.packedHeight : this.region.packedWidth)) * widthRatio, ((float) (this.region.rotate ? this.region.packedWidth : this.region.packedHeight)) * heightRatio);
        }

        public void setSize(float width, float height) {
            setBounds(getX(), getY(), width, height);
        }

        public void setOrigin(float originX, float originY) {
            super.setOrigin(originX - this.region.offsetX, originY - this.region.offsetY);
        }

        public void flip(boolean x, boolean y) {
            super.flip(x, y);
            float oldOriginX = getOriginX();
            float oldOriginY = getOriginY();
            float oldOffsetX = this.region.offsetX;
            float oldOffsetY = this.region.offsetY;
            float widthRatio = getWidthRatio();
            float heightRatio = getHeightRatio();
            this.region.offsetX = this.originalOffsetX;
            this.region.offsetY = this.originalOffsetY;
            this.region.flip(x, y);
            this.originalOffsetX = this.region.offsetX;
            this.originalOffsetY = this.region.offsetY;
            AtlasRegion atlasRegion = this.region;
            atlasRegion.offsetX *= widthRatio;
            atlasRegion = this.region;
            atlasRegion.offsetY *= heightRatio;
            translate(this.region.offsetX - oldOffsetX, this.region.offsetY - oldOffsetY);
            setOrigin(oldOriginX, oldOriginY);
        }

        public void rotate90(boolean clockwise) {
            super.rotate90(clockwise);
            float oldOriginX = getOriginX();
            float oldOriginY = getOriginY();
            float oldOffsetX = this.region.offsetX;
            float oldOffsetY = this.region.offsetY;
            float widthRatio = getWidthRatio();
            float heightRatio = getHeightRatio();
            if (clockwise) {
                this.region.offsetX = oldOffsetY;
                this.region.offsetY = ((((float) this.region.originalHeight) * heightRatio) - oldOffsetX) - (((float) this.region.packedWidth) * widthRatio);
            } else {
                this.region.offsetX = ((((float) this.region.originalWidth) * widthRatio) - oldOffsetY) - (((float) this.region.packedHeight) * heightRatio);
                this.region.offsetY = oldOffsetX;
            }
            translate(this.region.offsetX - oldOffsetX, this.region.offsetY - oldOffsetY);
            setOrigin(oldOriginX, oldOriginY);
        }

        public float getX() {
            return super.getX() - this.region.offsetX;
        }

        public float getY() {
            return super.getY() - this.region.offsetY;
        }

        public float getOriginX() {
            return super.getOriginX() + this.region.offsetX;
        }

        public float getOriginY() {
            return super.getOriginY() + this.region.offsetY;
        }

        public float getWidth() {
            return (super.getWidth() / this.region.getRotatedPackedWidth()) * ((float) this.region.originalWidth);
        }

        public float getHeight() {
            return (super.getHeight() / this.region.getRotatedPackedHeight()) * ((float) this.region.originalHeight);
        }

        public float getWidthRatio() {
            return super.getWidth() / this.region.getRotatedPackedWidth();
        }

        public float getHeightRatio() {
            return super.getHeight() / this.region.getRotatedPackedHeight();
        }

        public AtlasRegion getAtlasRegion() {
            return this.region;
        }
    }

    static {
        tuple = new String[4];
        indexComparator = new C00561();
    }

    public TextureAtlas() {
        this.textures = new HashSet(4);
        this.regions = new Array();
    }

    public TextureAtlas(String internalPackFile) {
        this(Gdx.files.internal(internalPackFile));
    }

    public TextureAtlas(FileHandle packFile) {
        this(packFile, packFile.parent());
    }

    public TextureAtlas(FileHandle packFile, boolean flip) {
        this(packFile, packFile.parent(), flip);
    }

    public TextureAtlas(FileHandle packFile, FileHandle imagesDir) {
        this(packFile, imagesDir, false);
    }

    public TextureAtlas(FileHandle packFile, FileHandle imagesDir, boolean flip) {
        this(new TextureAtlasData(packFile, imagesDir, flip));
    }

    public TextureAtlas(TextureAtlasData data) {
        this.textures = new HashSet(4);
        this.regions = new Array();
        if (data != null) {
            load(data);
        }
    }

    private void load(TextureAtlasData data) {
        ObjectMap<Page, Texture> pageToTexture = new ObjectMap();
        Iterator i$ = data.pages.iterator();
        while (i$.hasNext()) {
            Texture texture;
            Page page = (Page) i$.next();
            if (page.texture == null) {
                texture = new Texture(page.textureFile, page.format, page.useMipMaps);
                texture.setFilter(page.minFilter, page.magFilter);
                texture.setWrap(page.uWrap, page.vWrap);
            } else {
                texture = page.texture;
                texture.setFilter(page.minFilter, page.magFilter);
                texture.setWrap(page.uWrap, page.vWrap);
            }
            this.textures.add(texture);
            pageToTexture.put(page, texture);
        }
        i$ = data.regions.iterator();
        while (i$.hasNext()) {
            int i;
            int i2;
            Region region = (Region) i$.next();
            int width = region.width;
            int height = region.height;
            Texture texture2 = (Texture) pageToTexture.get(region.page);
            int i3 = region.left;
            int i4 = region.top;
            if (region.rotate) {
                i = height;
            } else {
                i = width;
            }
            if (region.rotate) {
                i2 = width;
            } else {
                i2 = height;
            }
            AtlasRegion atlasRegion = new AtlasRegion(texture2, i3, i4, i, i2);
            atlasRegion.index = region.index;
            atlasRegion.name = region.name;
            atlasRegion.offsetX = region.offsetX;
            atlasRegion.offsetY = region.offsetY;
            atlasRegion.originalHeight = region.originalHeight;
            atlasRegion.originalWidth = region.originalWidth;
            atlasRegion.rotate = region.rotate;
            atlasRegion.splits = region.splits;
            atlasRegion.pads = region.pads;
            if (region.flip) {
                atlasRegion.flip(false, true);
            }
            this.regions.add(atlasRegion);
        }
    }

    public AtlasRegion addRegion(String name, Texture texture, int x, int y, int width, int height) {
        this.textures.add(texture);
        AtlasRegion region = new AtlasRegion(texture, x, y, width, height);
        region.name = name;
        region.originalWidth = width;
        region.originalHeight = height;
        region.index = -1;
        this.regions.add(region);
        return region;
    }

    public AtlasRegion addRegion(String name, TextureRegion textureRegion) {
        return addRegion(name, textureRegion.texture, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    public Array<AtlasRegion> getRegions() {
        return this.regions;
    }

    public AtlasRegion findRegion(String name) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            if (((AtlasRegion) this.regions.get(i)).name.equals(name)) {
                return (AtlasRegion) this.regions.get(i);
            }
        }
        return null;
    }

    public AtlasRegion findRegion(String name, int index) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name) && region.index == index) {
                return region;
            }
        }
        return null;
    }

    public Array<AtlasRegion> findRegions(String name) {
        Array<AtlasRegion> matched = new Array();
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name)) {
                matched.add(new AtlasRegion(region));
            }
        }
        return matched;
    }

    public Array<Sprite> createSprites() {
        Array sprites = new Array(this.regions.size);
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            sprites.add(newSprite((AtlasRegion) this.regions.get(i)));
        }
        return sprites;
    }

    public Sprite createSprite(String name) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            if (((AtlasRegion) this.regions.get(i)).name.equals(name)) {
                return newSprite((AtlasRegion) this.regions.get(i));
            }
        }
        return null;
    }

    public Sprite createSprite(String name, int index) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name) && region.index == index) {
                return newSprite((AtlasRegion) this.regions.get(i));
            }
        }
        return null;
    }

    public Array<Sprite> createSprites(String name) {
        Array<Sprite> matched = new Array();
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name)) {
                matched.add(newSprite(region));
            }
        }
        return matched;
    }

    private Sprite newSprite(AtlasRegion region) {
        if (region.packedWidth != region.originalWidth || region.packedHeight != region.originalHeight) {
            return new AtlasSprite(region);
        }
        if (!region.rotate) {
            return new Sprite((TextureRegion) region);
        }
        Sprite sprite = new Sprite((TextureRegion) region);
        sprite.setBounds(0.0f, 0.0f, (float) region.getRegionHeight(), (float) region.getRegionWidth());
        sprite.rotate90(true);
        return sprite;
    }

    public NinePatch createPatch(String name) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            TextureRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name)) {
                int[] splits = region.splits;
                if (splits == null) {
                    throw new IllegalArgumentException("Region does not have ninepatch splits: " + name);
                }
                NinePatch patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
                if (region.pads == null) {
                    return patch;
                }
                patch.setPadding(region.pads[0], region.pads[1], region.pads[2], region.pads[3]);
                return patch;
            }
        }
        return null;
    }

    public Set<Texture> getTextures() {
        return this.textures;
    }

    public void dispose() {
        Iterator i$ = this.textures.iterator();
        while (i$.hasNext()) {
            ((Texture) i$.next()).dispose();
        }
        this.textures.clear();
    }

    static String readValue(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(58);
        if (colon != -1) {
            return line.substring(colon + 1).trim();
        }
        throw new GdxRuntimeException("Invalid line: " + line);
    }

    static int readTuple(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(58);
        if (colon == -1) {
            throw new GdxRuntimeException("Invalid line: " + line);
        }
        int i;
        int lastMatch = colon + 1;
        for (i = 0; i < 3; i++) {
            int comma = line.indexOf(44, lastMatch);
            if (comma == -1) {
                if (i == 0) {
                    throw new GdxRuntimeException("Invalid line: " + line);
                }
                tuple[i] = line.substring(lastMatch).trim();
                return i + 1;
            }
            tuple[i] = line.substring(lastMatch, comma).trim();
            lastMatch = comma + 1;
        }
        tuple[i] = line.substring(lastMatch).trim();
        return i + 1;
    }
}
