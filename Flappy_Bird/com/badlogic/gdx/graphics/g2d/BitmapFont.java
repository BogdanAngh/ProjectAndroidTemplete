package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BitmapFont implements Disposable {
    private static final int LOG2_PAGE_SIZE = 9;
    private static final int PAGES = 128;
    private static final int PAGE_SIZE = 512;
    public static final char[] capChars;
    public static final char[] xChars;
    private final BitmapFontCache cache;
    final BitmapFontData data;
    private boolean flipped;
    private boolean integer;
    private boolean ownsTexture;
    TextureRegion[] regions;

    public static class BitmapFontData {
        public float ascent;
        public float capHeight;
        public float descent;
        public float down;
        public boolean flipped;
        public FileHandle fontFile;
        public final Glyph[][] glyphs;
        @Deprecated
        public String imagePath;
        public String[] imagePaths;
        public float lineHeight;
        public float scaleX;
        public float scaleY;
        public float spaceWidth;
        public float xHeight;

        public BitmapFontData() {
            this.capHeight = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.glyphs = new Glyph[BitmapFont.PAGES][];
            this.xHeight = TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public BitmapFontData(FileHandle fontFile, boolean flip) {
            this.capHeight = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.glyphs = new Glyph[BitmapFont.PAGES][];
            this.xHeight = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.fontFile = fontFile;
            this.flipped = flip;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fontFile.read()), BitmapFont.PAGE_SIZE);
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            if (line == null) {
                throw new GdxRuntimeException("Invalid font file: " + fontFile);
            }
            String[] common = line.split(" ", 7);
            int length = common.length;
            if (r0 < 3) {
                throw new GdxRuntimeException("Invalid font file: " + fontFile);
            } else if (common[1].startsWith("lineHeight=")) {
                this.lineHeight = (float) Integer.parseInt(common[1].substring(11));
                if (common[2].startsWith("base=")) {
                    float baseLine = (float) Integer.parseInt(common[2].substring(5));
                    int imgPageCount = 1;
                    length = common.length;
                    if (r0 >= 6 && common[5] != null && common[5].startsWith("pages=")) {
                        try {
                            imgPageCount = Math.max(1, Integer.parseInt(common[5].substring(6)));
                        } catch (NumberFormatException e) {
                        }
                    }
                    try {
                        Glyph glyph;
                        StringTokenizer stringTokenizer;
                        float f;
                        this.imagePaths = new String[imgPageCount];
                        int p = 0;
                        while (p < imgPageCount) {
                            line = bufferedReader.readLine();
                            if (line == null) {
                                throw new GdxRuntimeException("Expected more 'page' definitions in font file " + fontFile);
                            }
                            String[] pageLine = line.split(" ", 4);
                            if (pageLine[2].startsWith("file=")) {
                                String imgFilename;
                                if (pageLine[1].startsWith("id=")) {
                                    if (Integer.parseInt(pageLine[1].substring(3)) != p) {
                                        throw new GdxRuntimeException("Invalid font file: " + fontFile + " -- page ids must be indices starting at 0");
                                    }
                                }
                                if (pageLine[2].endsWith("\"")) {
                                    imgFilename = pageLine[2].substring(6, pageLine[2].length() - 1);
                                } else {
                                    imgFilename = pageLine[2].substring(5, pageLine[2].length());
                                }
                                String path = fontFile.parent().child(imgFilename).path().replaceAll("\\\\", "/");
                                if (this.imagePath == null) {
                                    this.imagePath = path;
                                }
                                this.imagePaths[p] = path;
                                p++;
                            } else {
                                throw new GdxRuntimeException("Invalid font file: " + fontFile);
                            }
                        }
                        this.descent = 0.0f;
                        while (true) {
                            line = bufferedReader.readLine();
                            if (line != null) {
                                if (line.startsWith("kernings ")) {
                                    break;
                                }
                                if (line.startsWith("char ")) {
                                    glyph = new Glyph();
                                    stringTokenizer = new StringTokenizer(line, " =");
                                    stringTokenizer.nextToken();
                                    stringTokenizer.nextToken();
                                    int ch = Integer.parseInt(stringTokenizer.nextToken());
                                    if (ch <= 65535) {
                                        setGlyph(ch, glyph);
                                        glyph.id = ch;
                                        stringTokenizer.nextToken();
                                        glyph.srcX = Integer.parseInt(stringTokenizer.nextToken());
                                        stringTokenizer.nextToken();
                                        glyph.srcY = Integer.parseInt(stringTokenizer.nextToken());
                                        stringTokenizer.nextToken();
                                        glyph.width = Integer.parseInt(stringTokenizer.nextToken());
                                        stringTokenizer.nextToken();
                                        glyph.height = Integer.parseInt(stringTokenizer.nextToken());
                                        stringTokenizer.nextToken();
                                        glyph.xoffset = Integer.parseInt(stringTokenizer.nextToken());
                                        stringTokenizer.nextToken();
                                        if (flip) {
                                            glyph.yoffset = Integer.parseInt(stringTokenizer.nextToken());
                                        } else {
                                            glyph.yoffset = -(glyph.height + Integer.parseInt(stringTokenizer.nextToken()));
                                        }
                                        stringTokenizer.nextToken();
                                        glyph.xadvance = Integer.parseInt(stringTokenizer.nextToken());
                                        if (stringTokenizer.hasMoreTokens()) {
                                            stringTokenizer.nextToken();
                                        }
                                        if (stringTokenizer.hasMoreTokens()) {
                                            try {
                                                glyph.page = Integer.parseInt(stringTokenizer.nextToken());
                                            } catch (NumberFormatException e2) {
                                            }
                                        }
                                        if (glyph.width > 0 && glyph.height > 0) {
                                            this.descent = Math.min(((float) glyph.yoffset) + baseLine, this.descent);
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                        while (true) {
                            line = bufferedReader.readLine();
                            if (line != null) {
                                if (!line.startsWith("kerning ")) {
                                    break;
                                }
                                stringTokenizer = new StringTokenizer(line, " =");
                                stringTokenizer.nextToken();
                                stringTokenizer.nextToken();
                                int first = Integer.parseInt(stringTokenizer.nextToken());
                                stringTokenizer.nextToken();
                                int second = Integer.parseInt(stringTokenizer.nextToken());
                                if (first >= 0 && first <= 65535 && second >= 0 && second <= 65535) {
                                    glyph = getGlyph((char) first);
                                    stringTokenizer.nextToken();
                                    glyph.setKerning(second, Integer.parseInt(stringTokenizer.nextToken()));
                                }
                            } else {
                                break;
                            }
                        }
                        Glyph spaceGlyph = getGlyph(' ');
                        if (spaceGlyph == null) {
                            spaceGlyph = new Glyph();
                            spaceGlyph.id = 32;
                            Glyph xadvanceGlyph = getGlyph('l');
                            if (xadvanceGlyph == null) {
                                xadvanceGlyph = getFirstGlyph();
                            }
                            spaceGlyph.xadvance = xadvanceGlyph.xadvance;
                            setGlyph(32, spaceGlyph);
                        }
                        if (spaceGlyph != null) {
                            f = (float) (spaceGlyph.xadvance + spaceGlyph.width);
                        } else {
                            f = TextTrackStyle.DEFAULT_FONT_SCALE;
                        }
                        this.spaceWidth = f;
                        Glyph xGlyph = null;
                        int i = 0;
                        while (true) {
                            length = BitmapFont.xChars.length;
                            if (i >= r0) {
                                break;
                            }
                            xGlyph = getGlyph(BitmapFont.xChars[i]);
                            if (xGlyph != null) {
                                break;
                            }
                            i++;
                        }
                        if (xGlyph == null) {
                            xGlyph = getFirstGlyph();
                        }
                        this.xHeight = (float) xGlyph.height;
                        Glyph capGlyph = null;
                        i = 0;
                        while (true) {
                            length = BitmapFont.capChars.length;
                            if (i >= r0) {
                                break;
                            }
                            capGlyph = getGlyph(BitmapFont.capChars[i]);
                            if (capGlyph != null) {
                                break;
                            }
                            i++;
                        }
                        if (capGlyph == null) {
                            for (Glyph[] page : this.glyphs) {
                                if (page != null) {
                                    for (Glyph glyph2 : page) {
                                        if (!(glyph2 == null || glyph2.height == 0 || glyph2.width == 0)) {
                                            this.capHeight = Math.max(this.capHeight, (float) glyph2.height);
                                        }
                                    }
                                }
                            }
                        } else {
                            this.capHeight = (float) capGlyph.height;
                        }
                        this.ascent = baseLine - this.capHeight;
                        this.down = -this.lineHeight;
                        if (flip) {
                            this.ascent = -this.ascent;
                            this.down = -this.down;
                        }
                        StreamUtils.closeQuietly(bufferedReader);
                    } catch (NumberFormatException e3) {
                        throw new GdxRuntimeException("NumberFormatException on 'page id' element of " + fontFile);
                    } catch (Exception ex) {
                        throw new GdxRuntimeException("Error loading font file: " + fontFile, ex);
                    } catch (Throwable th) {
                        StreamUtils.closeQuietly(bufferedReader);
                    }
                } else {
                    throw new GdxRuntimeException("Invalid font file: " + fontFile);
                }
            } else {
                throw new GdxRuntimeException("Invalid font file: " + fontFile);
            }
        }

        public void setGlyph(int ch, Glyph glyph) {
            Glyph[] page = this.glyphs[ch / BitmapFont.PAGE_SIZE];
            if (page == null) {
                page = new Glyph[BitmapFont.PAGE_SIZE];
                this.glyphs[ch / BitmapFont.PAGE_SIZE] = page;
            }
            page[ch & 511] = glyph;
        }

        public Glyph getFirstGlyph() {
            for (Glyph[] page : this.glyphs) {
                if (page != null) {
                    for (Glyph glyph : page) {
                        if (glyph != null && glyph.height != 0 && glyph.width != 0) {
                            return glyph;
                        }
                    }
                    continue;
                }
            }
            throw new GdxRuntimeException("No glyphs found!");
        }

        public Glyph getGlyph(char ch) {
            Glyph[] page = this.glyphs[ch / BitmapFont.PAGE_SIZE];
            if (page != null) {
                return page[ch & 511];
            }
            return null;
        }

        @Deprecated
        public String getImagePath() {
            return this.imagePath;
        }

        public String getImagePath(int index) {
            return this.imagePaths[index];
        }

        public String[] getImagePaths() {
            return this.imagePaths;
        }

        public FileHandle getFontFile() {
            return this.fontFile;
        }
    }

    public static class Glyph {
        public int height;
        public int id;
        public byte[][] kerning;
        public int page;
        public int srcX;
        public int srcY;
        public float f41u;
        public float u2;
        public float f42v;
        public float v2;
        public int width;
        public int xadvance;
        public int xoffset;
        public int yoffset;

        public Glyph() {
            this.page = 0;
        }

        public int getKerning(char ch) {
            if (this.kerning != null) {
                byte[] page = this.kerning[ch >>> BitmapFont.LOG2_PAGE_SIZE];
                if (page != null) {
                    return page[ch & 511];
                }
            }
            return 0;
        }

        public void setKerning(int ch, int value) {
            if (this.kerning == null) {
                this.kerning = new byte[BitmapFont.PAGES][];
            }
            byte[] page = this.kerning[ch >>> BitmapFont.LOG2_PAGE_SIZE];
            if (page == null) {
                page = new byte[BitmapFont.PAGE_SIZE];
                this.kerning[ch >>> BitmapFont.LOG2_PAGE_SIZE] = page;
            }
            page[ch & 511] = (byte) value;
        }
    }

    public enum HAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public static class TextBounds {
        public float height;
        public float width;

        public TextBounds(TextBounds bounds) {
            set(bounds);
        }

        public void set(TextBounds bounds) {
            this.width = bounds.width;
            this.height = bounds.height;
        }
    }

    static {
        xChars = new char[]{'x', 'e', 'a', 'o', 'n', 's', 'r', 'c', 'u', 'm', 'v', 'w', 'z'};
        capChars = new char[]{'M', 'N', 'B', 'D', 'C', 'E', 'F', 'K', 'A', 'G', 'H', 'I', 'J', 'L', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }

    public BitmapFont() {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), false, true);
    }

    public BitmapFont(boolean flip) {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), flip, true);
    }

    public BitmapFont(FileHandle fontFile, TextureRegion region) {
        this(fontFile, region, false);
    }

    public BitmapFont(FileHandle fontFile, TextureRegion region, boolean flip) {
        this(new BitmapFontData(fontFile, flip), region, true);
    }

    public BitmapFont(FileHandle fontFile) {
        this(fontFile, false);
    }

    public BitmapFont(FileHandle fontFile, boolean flip) {
        this(new BitmapFontData(fontFile, flip), (TextureRegion) null, true);
    }

    public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip) {
        this(fontFile, imageFile, flip, true);
    }

    public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip, boolean integer) {
        this(new BitmapFontData(fontFile, flip), new TextureRegion(new Texture(imageFile, false)), integer);
        this.ownsTexture = true;
    }

    public BitmapFont(BitmapFontData data, TextureRegion region, boolean integer) {
        this(data, region != null ? new TextureRegion[]{region} : null, integer);
    }

    public BitmapFont(BitmapFontData data, TextureRegion[] regions, boolean integer) {
        if (regions == null || regions.length == 0) {
            this.regions = new TextureRegion[data.imagePaths.length];
            for (int i = 0; i < this.regions.length; i++) {
                if (data.fontFile == null) {
                    this.regions[i] = new TextureRegion(new Texture(Gdx.files.internal(data.imagePaths[i]), false));
                } else {
                    this.regions[i] = new TextureRegion(new Texture(Gdx.files.getFileHandle(data.imagePaths[i], data.fontFile.type()), false));
                }
            }
            this.ownsTexture = true;
        } else {
            this.regions = regions;
            this.ownsTexture = false;
        }
        this.cache = new BitmapFontCache(this);
        this.cache.setUseIntegerPositions(integer);
        this.flipped = data.flipped;
        this.data = data;
        this.integer = integer;
        load(data);
    }

    private void load(BitmapFontData data) {
        for (Glyph[] page : data.glyphs) {
            if (page != null) {
                for (Glyph glyph : page) {
                    if (glyph != null) {
                        TextureRegion region = this.regions[glyph.page];
                        if (region == null) {
                            throw new IllegalArgumentException("BitmapFont texture region array cannot contain null elements");
                        }
                        float invTexWidth = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) region.getTexture().getWidth());
                        float invTexHeight = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) region.getTexture().getHeight());
                        float offsetX = 0.0f;
                        float offsetY = 0.0f;
                        float u = region.f51u;
                        float v = region.f52v;
                        float regionWidth = (float) region.getRegionWidth();
                        float regionHeight = (float) region.getRegionHeight();
                        if (region instanceof AtlasRegion) {
                            AtlasRegion atlasRegion = (AtlasRegion) region;
                            offsetX = atlasRegion.offsetX;
                            offsetY = ((float) (atlasRegion.originalHeight - atlasRegion.packedHeight)) - atlasRegion.offsetY;
                        }
                        float x = (float) glyph.srcX;
                        float x2 = (float) (glyph.srcX + glyph.width);
                        float y = (float) glyph.srcY;
                        float y2 = (float) (glyph.srcY + glyph.height);
                        if (offsetX > 0.0f) {
                            x -= offsetX;
                            if (x < 0.0f) {
                                glyph.width = (int) (((float) glyph.width) + x);
                                glyph.xoffset = (int) (((float) glyph.xoffset) - x);
                                x = 0.0f;
                            }
                            x2 -= offsetX;
                            if (x2 > regionWidth) {
                                glyph.width = (int) (((float) glyph.width) - (x2 - regionWidth));
                                x2 = regionWidth;
                            }
                        }
                        if (offsetY > 0.0f) {
                            y -= offsetY;
                            if (y < 0.0f) {
                                glyph.height = (int) (((float) glyph.height) + y);
                                y = 0.0f;
                            }
                            y2 -= offsetY;
                            if (y2 > regionHeight) {
                                float amount = y2 - regionHeight;
                                glyph.height = (int) (((float) glyph.height) - amount);
                                glyph.yoffset = (int) (((float) glyph.yoffset) + amount);
                                y2 = regionHeight;
                            }
                        }
                        glyph.f41u = (x * invTexWidth) + u;
                        glyph.u2 = (x2 * invTexWidth) + u;
                        if (data.flipped) {
                            glyph.f42v = (y * invTexHeight) + v;
                            glyph.v2 = (y2 * invTexHeight) + v;
                        } else {
                            glyph.v2 = (y * invTexHeight) + v;
                            glyph.f42v = (y2 * invTexHeight) + v;
                        }
                    }
                }
                continue;
            }
        }
    }

    public TextBounds draw(SpriteBatch spriteBatch, CharSequence str, float x, float y) {
        this.cache.clear();
        TextBounds bounds = this.cache.addText(str, x, y, 0, str.length());
        this.cache.draw(spriteBatch);
        return bounds;
    }

    public TextBounds draw(SpriteBatch spriteBatch, CharSequence str, float x, float y, int start, int end) {
        this.cache.clear();
        TextBounds bounds = this.cache.addText(str, x, y, start, end);
        this.cache.draw(spriteBatch);
        return bounds;
    }

    public TextBounds drawMultiLine(SpriteBatch spriteBatch, CharSequence str, float x, float y) {
        this.cache.clear();
        TextBounds bounds = this.cache.addMultiLineText(str, x, y, 0.0f, HAlignment.LEFT);
        this.cache.draw(spriteBatch);
        return bounds;
    }

    public TextBounds drawMultiLine(SpriteBatch spriteBatch, CharSequence str, float x, float y, float alignmentWidth, HAlignment alignment) {
        this.cache.clear();
        TextBounds bounds = this.cache.addMultiLineText(str, x, y, alignmentWidth, alignment);
        this.cache.draw(spriteBatch);
        return bounds;
    }

    public TextBounds drawWrapped(SpriteBatch spriteBatch, CharSequence str, float x, float y, float wrapWidth) {
        this.cache.clear();
        TextBounds bounds = this.cache.addWrappedText(str, x, y, wrapWidth, HAlignment.LEFT);
        this.cache.draw(spriteBatch);
        return bounds;
    }

    public TextBounds drawWrapped(SpriteBatch spriteBatch, CharSequence str, float x, float y, float wrapWidth, HAlignment alignment) {
        this.cache.clear();
        TextBounds bounds = this.cache.addWrappedText(str, x, y, wrapWidth, alignment);
        this.cache.draw(spriteBatch);
        return bounds;
    }

    public TextBounds getBounds(CharSequence str) {
        return getBounds(str, 0, str.length());
    }

    public TextBounds getBounds(CharSequence str, TextBounds textBounds) {
        return getBounds(str, 0, str.length(), textBounds);
    }

    public TextBounds getBounds(CharSequence str, int start, int end) {
        return getBounds(str, start, end, this.cache.getBounds());
    }

    public TextBounds getBounds(CharSequence str, int start, int end, TextBounds textBounds) {
        BitmapFontData data = this.data;
        int width = 0;
        Glyph lastGlyph = null;
        int start2 = start;
        while (start2 < end) {
            start = start2 + 1;
            lastGlyph = data.getGlyph(str.charAt(start2));
            if (lastGlyph != null) {
                width = lastGlyph.xadvance;
                start2 = start;
                break;
            }
            start2 = start;
        }
        while (start2 < end) {
            start = start2 + 1;
            char ch = str.charAt(start2);
            Glyph g = data.getGlyph(ch);
            if (g != null) {
                lastGlyph = g;
                width = (width + lastGlyph.getKerning(ch)) + g.xadvance;
            }
            start2 = start;
        }
        textBounds.width = ((float) width) * data.scaleX;
        textBounds.height = data.capHeight;
        return textBounds;
    }

    public TextBounds getMultiLineBounds(CharSequence str) {
        return getMultiLineBounds(str, this.cache.getBounds());
    }

    public TextBounds getMultiLineBounds(CharSequence str, TextBounds textBounds) {
        int start = 0;
        float maxWidth = 0.0f;
        int numLines = 0;
        int length = str.length();
        while (start < length) {
            int lineEnd = indexOf(str, '\n', start);
            maxWidth = Math.max(maxWidth, getBounds(str, start, lineEnd).width);
            start = lineEnd + 1;
            numLines++;
        }
        textBounds.width = maxWidth;
        textBounds.height = this.data.capHeight + (((float) (numLines - 1)) * this.data.lineHeight);
        return textBounds;
    }

    public TextBounds getWrappedBounds(CharSequence str, float wrapWidth) {
        return getWrappedBounds(str, wrapWidth, this.cache.getBounds());
    }

    public TextBounds getWrappedBounds(CharSequence str, float wrapWidth, TextBounds textBounds) {
        if (wrapWidth <= 0.0f) {
            wrapWidth = 2.14748365E9f;
        }
        int start = 0;
        int numLines = 0;
        int length = str.length();
        float maxWidth = 0.0f;
        while (start < length) {
            int newLine = indexOf(str, '\n', start);
            while (start < newLine && isWhitespace(str.charAt(start))) {
                start++;
            }
            int lineEnd = start + computeVisibleGlyphs(str, start, newLine, wrapWidth);
            int nextStart = lineEnd + 1;
            if (lineEnd < newLine) {
                while (lineEnd > start && !isWhitespace(str.charAt(lineEnd))) {
                    lineEnd--;
                }
                if (lineEnd == start) {
                    if (nextStart > start + 1) {
                        nextStart--;
                    }
                    lineEnd = nextStart;
                } else {
                    nextStart = lineEnd;
                    while (lineEnd > start && isWhitespace(str.charAt(lineEnd - 1))) {
                        lineEnd--;
                    }
                }
            }
            if (lineEnd > start) {
                maxWidth = Math.max(maxWidth, getBounds(str, start, lineEnd).width);
            }
            start = nextStart;
            numLines++;
        }
        textBounds.width = maxWidth;
        textBounds.height = this.data.capHeight + (((float) (numLines - 1)) * this.data.lineHeight);
        return textBounds;
    }

    public void computeGlyphAdvancesAndPositions(CharSequence str, FloatArray glyphAdvances, FloatArray glyphPositions) {
        glyphAdvances.clear();
        glyphPositions.clear();
        int index = 0;
        int end = str.length();
        float width = 0.0f;
        Glyph lastGlyph = null;
        BitmapFontData data = this.data;
        char ch;
        Glyph g;
        if (data.scaleX == TextTrackStyle.DEFAULT_FONT_SCALE) {
            while (index < end) {
                ch = str.charAt(index);
                g = data.getGlyph(ch);
                if (g != null) {
                    if (lastGlyph != null) {
                        width += (float) lastGlyph.getKerning(ch);
                    }
                    lastGlyph = g;
                    glyphAdvances.add((float) g.xadvance);
                    glyphPositions.add(width);
                    width += (float) g.xadvance;
                }
                index++;
            }
            glyphAdvances.add(0.0f);
            glyphPositions.add(width);
            return;
        }
        float scaleX = this.data.scaleX;
        while (index < end) {
            ch = str.charAt(index);
            g = data.getGlyph(ch);
            if (g != null) {
                if (lastGlyph != null) {
                    width += ((float) lastGlyph.getKerning(ch)) * scaleX;
                }
                lastGlyph = g;
                float xadvance = ((float) g.xadvance) * scaleX;
                glyphAdvances.add(xadvance);
                glyphPositions.add(width);
                width += xadvance;
            }
            index++;
        }
        glyphAdvances.add(0.0f);
        glyphPositions.add(width);
    }

    public int computeVisibleGlyphs(CharSequence str, int start, int end, float availableWidth) {
        BitmapFontData data = this.data;
        int index = start;
        float width = 0.0f;
        Glyph lastGlyph = null;
        availableWidth /= data.scaleX;
        while (index < end) {
            char ch = str.charAt(index);
            Glyph g = data.getGlyph(ch);
            if (g != null) {
                if (lastGlyph != null) {
                    width += (float) lastGlyph.getKerning(ch);
                }
                if ((((float) g.xadvance) + width) - availableWidth > 0.001f) {
                    break;
                }
                width += (float) g.xadvance;
                lastGlyph = g;
            }
            index++;
        }
        return index - start;
    }

    public void setColor(float color) {
        this.cache.setColor(color);
    }

    public void setColor(Color color) {
        this.cache.setColor(color);
    }

    public void setColor(float r, float g, float b, float a) {
        this.cache.setColor(r, g, b, a);
    }

    public Color getColor() {
        return this.cache.getColor();
    }

    public void setScale(float scaleX, float scaleY) {
        BitmapFontData data = this.data;
        float x = scaleX / data.scaleX;
        float y = scaleY / data.scaleY;
        data.lineHeight *= y;
        data.spaceWidth *= x;
        data.xHeight *= y;
        data.capHeight *= y;
        data.ascent *= y;
        data.descent *= y;
        data.down *= y;
        data.scaleX = scaleX;
        data.scaleY = scaleY;
    }

    public void setScale(float scaleXY) {
        setScale(scaleXY, scaleXY);
    }

    public void scale(float amount) {
        setScale(this.data.scaleX + amount, this.data.scaleY + amount);
    }

    public float getScaleX() {
        return this.data.scaleX;
    }

    public float getScaleY() {
        return this.data.scaleY;
    }

    public TextureRegion getRegion() {
        return this.regions[0];
    }

    public TextureRegion[] getRegions() {
        return this.regions;
    }

    public TextureRegion getRegion(int index) {
        return this.regions[index];
    }

    public float getLineHeight() {
        return this.data.lineHeight;
    }

    public float getSpaceWidth() {
        return this.data.spaceWidth;
    }

    public float getXHeight() {
        return this.data.xHeight;
    }

    public float getCapHeight() {
        return this.data.capHeight;
    }

    public float getAscent() {
        return this.data.ascent;
    }

    public float getDescent() {
        return this.data.descent;
    }

    public boolean isFlipped() {
        return this.flipped;
    }

    public void dispose() {
        if (this.ownsTexture) {
            for (TextureRegion texture : this.regions) {
                texture.getTexture().dispose();
            }
        }
    }

    public void setFixedWidthGlyphs(CharSequence glyphs) {
        int index;
        BitmapFontData data = this.data;
        int maxAdvance = 0;
        int end = glyphs.length();
        for (index = 0; index < end; index++) {
            Glyph g = data.getGlyph(glyphs.charAt(index));
            if (g != null && g.xadvance > maxAdvance) {
                maxAdvance = g.xadvance;
            }
        }
        end = glyphs.length();
        for (index = 0; index < end; index++) {
            g = data.getGlyph(glyphs.charAt(index));
            if (g != null) {
                g.xoffset += (maxAdvance - g.xadvance) / 2;
                g.xadvance = maxAdvance;
                g.kerning = (byte[][]) null;
            }
        }
    }

    public boolean containsCharacter(char character) {
        return this.data.getGlyph(character) != null;
    }

    public void setUseIntegerPositions(boolean integer) {
        this.integer = integer;
        this.cache.setUseIntegerPositions(integer);
    }

    public boolean usesIntegerPositions() {
        return this.integer;
    }

    public BitmapFontCache getCache() {
        return this.cache;
    }

    public BitmapFontData getData() {
        return this.data;
    }

    public boolean ownsTexture() {
        return this.ownsTexture;
    }

    public void setOwnsTexture(boolean ownsTexture) {
        this.ownsTexture = ownsTexture;
    }

    static int indexOf(CharSequence text, char ch, int start) {
        int n = text.length();
        while (start < n) {
            if (text.charAt(start) == ch) {
                return start;
            }
            start++;
        }
        return n;
    }

    static boolean isWhitespace(char c) {
        switch (c) {
            case LOG2_PAGE_SIZE /*9*/:
            case Place.TYPE_BEAUTY_SALON /*10*/:
            case ConnectionsStatusCodes.STATUS_ERROR /*13*/:
            case Place.TYPE_ELECTRONICS_STORE /*32*/:
                return true;
            default:
                return false;
        }
    }
}
