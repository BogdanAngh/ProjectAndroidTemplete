package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

public class AtlasTmxMapLoader extends AsynchronousAssetLoader<TiledMap, AtlasTiledMapLoaderParameters> {
    protected static final int FLAG_FLIP_DIAGONALLY = 536870912;
    protected static final int FLAG_FLIP_HORIZONTALLY = Integer.MIN_VALUE;
    protected static final int FLAG_FLIP_VERTICALLY = 1073741824;
    protected static final int MASK_CLEAR = -536870912;
    protected TiledMap map;
    protected int mapHeightInPixels;
    protected int mapWidthInPixels;
    protected Element root;
    protected Array<Texture> trackedTextures;
    protected XmlReader xml;
    protected boolean yUp;

    private interface AtlasResolver {

        public static class AssetManagerAtlasResolver implements AtlasResolver {
            private final AssetManager assetManager;

            public AssetManagerAtlasResolver(AssetManager assetManager) {
                this.assetManager = assetManager;
            }

            public TextureAtlas getAtlas(String name) {
                return (TextureAtlas) this.assetManager.get(name, TextureAtlas.class);
            }
        }

        public static class DirectAtlasResolver implements AtlasResolver {
            private final ObjectMap<String, TextureAtlas> atlases;

            public DirectAtlasResolver(ObjectMap<String, TextureAtlas> atlases) {
                this.atlases = atlases;
            }

            public TextureAtlas getAtlas(String name) {
                return (TextureAtlas) this.atlases.get(name);
            }
        }

        TextureAtlas getAtlas(String str);
    }

    public static class AtlasTiledMapLoaderParameters extends AssetLoaderParameters<TiledMap> {
        public boolean forceTextureFilters;
        public TextureFilter textureMagFilter;
        public TextureFilter textureMinFilter;
        public boolean yUp;

        public AtlasTiledMapLoaderParameters() {
            this.yUp = true;
            this.forceTextureFilters = false;
            this.textureMinFilter = TextureFilter.Nearest;
            this.textureMagFilter = TextureFilter.Nearest;
        }
    }

    public AtlasTmxMapLoader() {
        super(new InternalFileHandleResolver());
        this.xml = new XmlReader();
        this.trackedTextures = new Array();
    }

    public AtlasTmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
        this.xml = new XmlReader();
        this.trackedTextures = new Array();
    }

    public TiledMap load(String fileName) {
        return load(fileName, new AtlasTiledMapLoaderParameters());
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle tmxFile, AtlasTiledMapLoaderParameters parameter) {
        Array<AssetDescriptor> dependencies = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            Element properties = this.root.getChildByName("properties");
            if (properties != null) {
                Iterator i$ = properties.getChildrenByName("property").iterator();
                while (i$.hasNext()) {
                    Element property = (Element) i$.next();
                    String name = property.getAttribute("name");
                    String value = property.getAttribute("value");
                    if (name.startsWith("atlas")) {
                        dependencies.add(new AssetDescriptor(getRelativeFileHandle(tmxFile, value), TextureAtlas.class));
                    }
                }
            }
            return dependencies;
        } catch (IOException e) {
            throw new GdxRuntimeException("Unable to parse .tmx file.");
        }
    }

    public TiledMap load(String fileName, AtlasTiledMapLoaderParameters parameter) {
        if (parameter != null) {
            try {
                this.yUp = parameter.yUp;
            } catch (IOException e) {
                throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
            }
        }
        this.yUp = true;
        FileHandle tmxFile = resolve(fileName);
        this.root = this.xml.parse(tmxFile);
        ObjectMap<String, TextureAtlas> atlases = new ObjectMap();
        FileHandle atlasFile = loadAtlas(this.root, tmxFile);
        if (atlasFile == null) {
            throw new GdxRuntimeException("Couldn't load atlas");
        }
        atlases.put(atlasFile.path(), new TextureAtlas(atlasFile));
        TiledMap map = loadMap(this.root, tmxFile, new DirectAtlasResolver(atlases), parameter);
        map.setOwnedResources(atlases.values().toArray());
        setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
        return map;
    }

    protected FileHandle loadAtlas(Element root, FileHandle tmxFile) throws IOException {
        Element e = root.getChildByName("properties");
        if (e == null) {
            return null;
        }
        Iterator i$ = e.getChildrenByName("property").iterator();
        while (i$.hasNext()) {
            Element property = (Element) i$.next();
            String name = property.getAttribute("name", null);
            String value = property.getAttribute("value", null);
            if (name.equals("atlas")) {
                if (value == null) {
                    value = property.getText();
                }
                if (!(value == null || value.length() == 0)) {
                    return getRelativeFileHandle(tmxFile, value);
                }
            }
        }
        return null;
    }

    private void setTextureFilters(TextureFilter min, TextureFilter mag) {
        Iterator i$ = this.trackedTextures.iterator();
        while (i$.hasNext()) {
            ((Texture) i$.next()).setFilter(min, mag);
        }
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, AtlasTiledMapLoaderParameters parameter) {
        this.map = null;
        if (parameter != null) {
            this.yUp = parameter.yUp;
        } else {
            this.yUp = true;
        }
        try {
            this.map = loadMap(this.root, tmxFile, new AssetManagerAtlasResolver(manager), parameter);
        } catch (Exception e) {
            throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
        }
    }

    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, AtlasTiledMapLoaderParameters parameter) {
        if (parameter != null) {
            setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
        }
        return this.map;
    }

    protected TiledMap loadMap(Element root, FileHandle tmxFile, AtlasResolver resolver, AtlasTiledMapLoaderParameters parameter) {
        TiledMap map = new TiledMap();
        String mapOrientation = root.getAttribute("orientation", null);
        int mapWidth = root.getIntAttribute("width", 0);
        int mapHeight = root.getIntAttribute("height", 0);
        int tileWidth = root.getIntAttribute("tilewidth", 0);
        int tileHeight = root.getIntAttribute("tileheight", 0);
        String mapBackgroundColor = root.getAttribute("backgroundcolor", null);
        MapProperties mapProperties = map.getProperties();
        if (mapOrientation != null) {
            mapProperties.put("orientation", mapOrientation);
        }
        mapProperties.put("width", Integer.valueOf(mapWidth));
        mapProperties.put("height", Integer.valueOf(mapHeight));
        mapProperties.put("tilewidth", Integer.valueOf(tileWidth));
        mapProperties.put("tileheight", Integer.valueOf(tileHeight));
        if (mapBackgroundColor != null) {
            mapProperties.put("backgroundcolor", mapBackgroundColor);
        }
        this.mapWidthInPixels = mapWidth * tileWidth;
        this.mapHeightInPixels = mapHeight * tileHeight;
        int j = root.getChildCount();
        for (int i = 0; i < j; i++) {
            Element element = root.getChild(i);
            String elementName = element.getName();
            if (elementName.equals("properties")) {
                loadProperties(map.getProperties(), element);
            } else if (elementName.equals("tileset")) {
                loadTileset(map, element, tmxFile, resolver, parameter);
            } else if (elementName.equals("layer")) {
                loadTileLayer(map, element);
            } else if (elementName.equals("objectgroup")) {
                loadObjectGroup(map, element);
            }
        }
        return map;
    }

    protected void loadTileset(TiledMap map, Element element, FileHandle tmxFile, AtlasResolver resolver, AtlasTiledMapLoaderParameters parameter) {
        if (element.getName().equals("tileset")) {
            int imageWidth;
            int imageHeight;
            String name = element.get("name", null);
            int firstgid = element.getIntAttribute("firstgid", 1);
            int tilewidth = element.getIntAttribute("tilewidth", 0);
            int tileheight = element.getIntAttribute("tileheight", 0);
            int spacing = element.getIntAttribute("spacing", 0);
            int margin = element.getIntAttribute("margin", 0);
            String source = element.getAttribute("source", null);
            String imageSource = "";
            if (source != null) {
                FileHandle tsx = getRelativeFileHandle(tmxFile, source);
                try {
                    element = this.xml.parse(tsx);
                    name = element.get("name", null);
                    tilewidth = element.getIntAttribute("tilewidth", 0);
                    tileheight = element.getIntAttribute("tileheight", 0);
                    spacing = element.getIntAttribute("spacing", 0);
                    margin = element.getIntAttribute("margin", 0);
                    imageSource = element.getChildByName("image").getAttribute("source");
                    imageWidth = element.getChildByName("image").getIntAttribute("width", 0);
                    imageHeight = element.getChildByName("image").getIntAttribute("height", 0);
                } catch (IOException e) {
                    throw new GdxRuntimeException("Error parsing external tileset.");
                }
            }
            imageSource = element.getChildByName("image").getAttribute("source");
            imageWidth = element.getChildByName("image").getIntAttribute("width", 0);
            imageHeight = element.getChildByName("image").getIntAttribute("height", 0);
            if (map.getProperties().containsKey("atlas")) {
                Element properties;
                FileHandle atlasHandle = resolve(getRelativeFileHandle(tmxFile, (String) map.getProperties().get("atlas", String.class)).path());
                TextureAtlas atlas = resolver.getAtlas(atlasHandle.path());
                String regionsName = atlasHandle.nameWithoutExtension();
                if (parameter != null && parameter.forceTextureFilters) {
                    for (Texture texture : atlas.getTextures()) {
                        this.trackedTextures.add(texture);
                    }
                }
                TiledMapTileSet tileset = new TiledMapTileSet();
                MapProperties props = tileset.getProperties();
                tileset.setName(name);
                props.put("firstgid", Integer.valueOf(firstgid));
                props.put("imagesource", imageSource);
                props.put("imagewidth", Integer.valueOf(imageWidth));
                props.put("imageheight", Integer.valueOf(imageHeight));
                props.put("tilewidth", Integer.valueOf(tilewidth));
                props.put("tileheight", Integer.valueOf(tileheight));
                props.put("margin", Integer.valueOf(margin));
                props.put("spacing", Integer.valueOf(spacing));
                Iterator i$ = atlas.findRegions(regionsName).iterator();
                while (i$.hasNext()) {
                    TextureRegion region = (AtlasRegion) i$.next();
                    if (region != null) {
                        StaticTiledMapTile staticTiledMapTile = new StaticTiledMapTile(region);
                        if (!this.yUp) {
                            region.flip(false, true);
                        }
                        int tileid = firstgid + region.index;
                        staticTiledMapTile.setId(tileid);
                        tileset.putTile(tileid, staticTiledMapTile);
                    }
                }
                i$ = element.getChildrenByName("tile").iterator();
                while (i$.hasNext()) {
                    Element tileElement = (Element) i$.next();
                    TiledMapTile tile = tileset.getTile(firstgid + tileElement.getIntAttribute("id", 0));
                    if (tile != null) {
                        String terrain = tileElement.getAttribute("terrain", null);
                        if (terrain != null) {
                            tile.getProperties().put("terrain", terrain);
                        }
                        String probability = tileElement.getAttribute("probability", null);
                        if (probability != null) {
                            tile.getProperties().put("probability", probability);
                        }
                        properties = tileElement.getChildByName("properties");
                        if (properties != null) {
                            loadProperties(tile.getProperties(), properties);
                        }
                    }
                }
                properties = element.getChildByName("properties");
                if (properties != null) {
                    loadProperties(tileset.getProperties(), properties);
                }
                map.getTileSets().addTileSet(tileset);
                return;
            }
            throw new GdxRuntimeException("The map is missing the 'atlas' property");
        }
    }

    protected void loadTileLayer(TiledMap map, Element element) {
        if (element.getName().equals("layer")) {
            String name = element.getAttribute("name", null);
            int width = element.getIntAttribute("width", 0);
            int height = element.getIntAttribute("height", 0);
            int tileWidth = element.getParent().getIntAttribute("tilewidth", 0);
            int tileHeight = element.getParent().getIntAttribute("tileheight", 0);
            boolean visible = element.getIntAttribute("visible", 1) == 1;
            float opacity = element.getFloatAttribute("opacity", TextTrackStyle.DEFAULT_FONT_SCALE);
            MapLayer tiledMapTileLayer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);
            tiledMapTileLayer.setVisible(visible);
            tiledMapTileLayer.setOpacity(opacity);
            tiledMapTileLayer.setName(name);
            TiledMapTileSets tilesets = map.getTileSets();
            Element data = element.getChildByName("data");
            String encoding = data.getAttribute("encoding", null);
            String compression = data.getAttribute("compression", null);
            if (encoding == null) {
                throw new GdxRuntimeException("Unsupported encoding (XML) for TMX Layer Data");
            }
            int y;
            int x;
            int id;
            boolean flipHorizontally;
            boolean flipVertically;
            boolean flipDiagonally;
            TiledMapTile tile;
            Cell cell;
            int i;
            if (encoding.equals("csv")) {
                String[] array = data.getText().split(",");
                for (y = 0; y < height; y++) {
                    for (x = 0; x < width; x++) {
                        id = (int) Long.parseLong(array[(y * width) + x].trim());
                        flipHorizontally = (FLAG_FLIP_HORIZONTALLY & id) != 0;
                        flipVertically = (FLAG_FLIP_VERTICALLY & id) != 0;
                        flipDiagonally = (FLAG_FLIP_DIAGONALLY & id) != 0;
                        id &= 536870911;
                        tilesets.getTile(id);
                        tile = tilesets.getTile(id);
                        if (tile != null) {
                            cell = createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
                            cell.setTile(tile);
                            if (this.yUp) {
                                i = (height - 1) - y;
                            } else {
                                i = y;
                            }
                            tiledMapTileLayer.setCell(x, i, cell);
                        }
                    }
                }
            } else {
                if (encoding.equals("base64")) {
                    byte[] bytes = Base64Coder.decode(data.getText());
                    if (compression == null) {
                        int read = 0;
                        y = 0;
                        while (y < height) {
                            int read2 = read;
                            for (x = 0; x < width; x++) {
                                read = read2 + 1;
                                read2 = read + 1;
                                read = read2 + 1;
                                read2 = read + 1;
                                id = ((unsignedByteToInt(bytes[read2]) | (unsignedByteToInt(bytes[read]) << 8)) | (unsignedByteToInt(bytes[read2]) << 16)) | (unsignedByteToInt(bytes[read]) << 24);
                                flipHorizontally = (FLAG_FLIP_HORIZONTALLY & id) != 0;
                                flipVertically = (FLAG_FLIP_VERTICALLY & id) != 0;
                                flipDiagonally = (FLAG_FLIP_DIAGONALLY & id) != 0;
                                id &= 536870911;
                                tilesets.getTile(id);
                                tile = tilesets.getTile(id);
                                if (tile != null) {
                                    cell = createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
                                    cell.setTile(tile);
                                    if (this.yUp) {
                                        i = (height - 1) - y;
                                    } else {
                                        i = y;
                                    }
                                    tiledMapTileLayer.setCell(x, i, cell);
                                }
                            }
                            y++;
                            read = read2;
                        }
                    } else {
                        byte[] temp;
                        if (compression.equals("gzip")) {
                            try {
                                GZIPInputStream GZIS = new GZIPInputStream(new ByteArrayInputStream(bytes), bytes.length);
                                temp = new byte[4];
                                for (y = 0; y < height; y++) {
                                    x = 0;
                                    while (x < width) {
                                        try {
                                            GZIS.read(temp, 0, 4);
                                            id = ((unsignedByteToInt(temp[0]) | (unsignedByteToInt(temp[1]) << 8)) | (unsignedByteToInt(temp[2]) << 16)) | (unsignedByteToInt(temp[3]) << 24);
                                            flipHorizontally = (FLAG_FLIP_HORIZONTALLY & id) != 0;
                                            flipVertically = (FLAG_FLIP_VERTICALLY & id) != 0;
                                            flipDiagonally = (FLAG_FLIP_DIAGONALLY & id) != 0;
                                            id &= 536870911;
                                            tilesets.getTile(id);
                                            tile = tilesets.getTile(id);
                                            if (tile != null) {
                                                cell = createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
                                                cell.setTile(tile);
                                                if (this.yUp) {
                                                    i = (height - 1) - y;
                                                } else {
                                                    i = y;
                                                }
                                                tiledMapTileLayer.setCell(x, i, cell);
                                            }
                                            x++;
                                        } catch (IOException e) {
                                            throw new GdxRuntimeException("Error Reading TMX Layer Data.", e);
                                        }
                                    }
                                }
                            } catch (IOException e2) {
                                throw new GdxRuntimeException("Error Reading TMX Layer Data - IOException: " + e2.getMessage());
                            }
                        }
                        if (compression.equals("zlib")) {
                            Inflater zlib = new Inflater();
                            temp = new byte[4];
                            zlib.setInput(bytes, 0, bytes.length);
                            for (y = 0; y < height; y++) {
                                x = 0;
                                while (x < width) {
                                    try {
                                        zlib.inflate(temp, 0, 4);
                                        id = ((unsignedByteToInt(temp[0]) | (unsignedByteToInt(temp[1]) << 8)) | (unsignedByteToInt(temp[2]) << 16)) | (unsignedByteToInt(temp[3]) << 24);
                                        flipHorizontally = (FLAG_FLIP_HORIZONTALLY & id) != 0;
                                        flipVertically = (FLAG_FLIP_VERTICALLY & id) != 0;
                                        flipDiagonally = (FLAG_FLIP_DIAGONALLY & id) != 0;
                                        id &= 536870911;
                                        tilesets.getTile(id);
                                        tile = tilesets.getTile(id);
                                        if (tile != null) {
                                            cell = createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
                                            cell.setTile(tile);
                                            if (this.yUp) {
                                                i = (height - 1) - y;
                                            } else {
                                                i = y;
                                            }
                                            tiledMapTileLayer.setCell(x, i, cell);
                                        }
                                        x++;
                                    } catch (DataFormatException e3) {
                                        throw new GdxRuntimeException("Error Reading TMX Layer Data.", e3);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new GdxRuntimeException("Unrecognised encoding (" + encoding + ") for TMX Layer Data");
                }
            }
            Element properties = element.getChildByName("properties");
            if (properties != null) {
                loadProperties(tiledMapTileLayer.getProperties(), properties);
            }
            map.getLayers().add(tiledMapTileLayer);
        }
    }

    protected void loadObjectGroup(TiledMap map, Element element) {
        if (element.getName().equals("objectgroup")) {
            String name = element.getAttribute("name", null);
            MapLayer layer = new MapLayer();
            layer.setName(name);
            Element properties = element.getChildByName("properties");
            if (properties != null) {
                loadProperties(layer.getProperties(), properties);
            }
            Iterator i$ = element.getChildrenByName("object").iterator();
            while (i$.hasNext()) {
                loadObject(layer, (Element) i$.next());
            }
            map.getLayers().add(layer);
        }
    }

    protected void loadObject(MapLayer layer, Element element) {
        if (element.getName().equals("object")) {
            int y;
            MapObject mapObject = null;
            int x = element.getIntAttribute("x", 0);
            if (this.yUp) {
                y = this.mapHeightInPixels - element.getIntAttribute("y", 0);
            } else {
                y = element.getIntAttribute("y", 0);
            }
            int width = element.getIntAttribute("width", 0);
            int height = element.getIntAttribute("height", 0);
            if (element.getChildCount() > 0) {
                Element child = element.getChildByName("polygon");
                String[] points;
                float[] vertices;
                int i;
                int length;
                String[] point;
                if (child != null) {
                    points = child.getAttribute("points").split(" ");
                    vertices = new float[(points.length * 2)];
                    i = 0;
                    while (true) {
                        length = points.length;
                        if (i >= r0) {
                            break;
                        }
                        point = points[i].split(",");
                        vertices[i * 2] = (float) Integer.parseInt(point[0]);
                        vertices[(i * 2) + 1] = (float) Integer.parseInt(point[1]);
                        if (this.yUp) {
                            length = (i * 2) + 1;
                            vertices[length] = vertices[length] * GroundOverlayOptions.NO_DIMENSION;
                        }
                        i++;
                    }
                    Polygon polygon = new Polygon(vertices);
                    polygon.setPosition((float) x, (float) y);
                    mapObject = new PolygonMapObject(polygon);
                } else {
                    child = element.getChildByName("polyline");
                    if (child != null) {
                        points = child.getAttribute("points").split(" ");
                        vertices = new float[(points.length * 2)];
                        i = 0;
                        while (true) {
                            length = points.length;
                            if (i >= r0) {
                                break;
                            }
                            point = points[i].split(",");
                            vertices[i * 2] = (float) Integer.parseInt(point[0]);
                            vertices[(i * 2) + 1] = (float) Integer.parseInt(point[1]);
                            if (this.yUp) {
                                length = (i * 2) + 1;
                                vertices[length] = vertices[length] * GroundOverlayOptions.NO_DIMENSION;
                            }
                            i++;
                        }
                        Polyline polyline = new Polyline(vertices);
                        polyline.setPosition((float) x, (float) y);
                        mapObject = new PolylineMapObject(polyline);
                    } else {
                        if (element.getChildByName("ellipse") != null) {
                            mapObject = new EllipseMapObject((float) x, this.yUp ? (float) (y - height) : (float) y, (float) width, (float) height);
                        }
                    }
                }
            }
            if (mapObject == null) {
                float f;
                float f2 = (float) x;
                if (this.yUp) {
                    f = (float) (y - height);
                } else {
                    f = (float) y;
                }
                mapObject = new RectangleMapObject(f2, f, (float) width, (float) height);
            }
            mapObject.setName(element.getAttribute("name", null));
            String type = element.getAttribute("type", null);
            if (type != null) {
                mapObject.getProperties().put("type", type);
            }
            int gid = element.getIntAttribute("gid", -1);
            if (gid != -1) {
                mapObject.getProperties().put("gid", Integer.valueOf(gid));
            }
            mapObject.getProperties().put("x", Integer.valueOf(x));
            MapProperties properties = mapObject.getProperties();
            String str = "y";
            if (this.yUp) {
                y -= height;
            }
            properties.put(str, Integer.valueOf(y));
            mapObject.setVisible(element.getIntAttribute("visible", 1) == 1);
            Element properties2 = element.getChildByName("properties");
            if (properties2 != null) {
                loadProperties(mapObject.getProperties(), properties2);
            }
            layer.getObjects().add(mapObject);
        }
    }

    protected void loadProperties(MapProperties properties, Element element) {
        if (element.getName().equals("properties")) {
            Iterator i$ = element.getChildrenByName("property").iterator();
            while (i$.hasNext()) {
                Element property = (Element) i$.next();
                String name = property.getAttribute("name", null);
                String value = property.getAttribute("value", null);
                if (value == null) {
                    value = property.getText();
                }
                properties.put(name, value);
            }
        }
    }

    protected Cell createTileLayerCell(boolean flipHorizontally, boolean flipVertically, boolean flipDiagonally) {
        int i = 3;
        int i2 = 1;
        Cell cell = new Cell();
        if (!flipDiagonally) {
            cell.setFlipHorizontally(flipHorizontally);
            cell.setFlipVertically(flipVertically);
        } else if (flipHorizontally && flipVertically) {
            cell.setFlipHorizontally(true);
            if (!this.yUp) {
                i = 1;
            }
            cell.setRotation(i);
        } else if (flipHorizontally) {
            if (!this.yUp) {
                i = 1;
            }
            cell.setRotation(i);
        } else if (flipVertically) {
            if (!this.yUp) {
                i2 = 3;
            }
            cell.setRotation(i2);
        } else {
            cell.setFlipVertically(true);
            if (!this.yUp) {
                i = 1;
            }
            cell.setRotation(i);
        }
        return cell;
    }

    public static FileHandle getRelativeFileHandle(FileHandle file, String path) {
        StringTokenizer tokenizer = new StringTokenizer(path, "\\/");
        FileHandle result = file.parent();
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            if (token.equals("..")) {
                result = result.parent();
            } else {
                result = result.child(token);
            }
        }
        return result;
    }

    protected static int unsignedByteToInt(byte b) {
        return b & Keys.F12;
    }
}
