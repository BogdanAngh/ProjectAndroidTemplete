package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.ImageResolver.AssetManagerImageResolver;
import com.badlogic.gdx.maps.ImageResolver.DirectImageResolver;
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

public class TmxMapLoader extends AsynchronousAssetLoader<TiledMap, Parameters> {
    protected static final int FLAG_FLIP_DIAGONALLY = 536870912;
    protected static final int FLAG_FLIP_HORIZONTALLY = Integer.MIN_VALUE;
    protected static final int FLAG_FLIP_VERTICALLY = 1073741824;
    protected static final int MASK_CLEAR = -536870912;
    protected TiledMap map;
    protected int mapHeightInPixels;
    protected int mapWidthInPixels;
    protected Element root;
    protected XmlReader xml;
    protected boolean yUp;

    public static class Parameters extends AssetLoaderParameters<TiledMap> {
        public boolean generateMipMaps;
        public TextureFilter textureMagFilter;
        public TextureFilter textureMinFilter;
        public boolean yUp;

        public Parameters() {
            this.yUp = true;
            this.generateMipMaps = false;
            this.textureMinFilter = TextureFilter.Nearest;
            this.textureMagFilter = TextureFilter.Nearest;
        }
    }

    public TmxMapLoader() {
        super(new InternalFileHandleResolver());
        this.xml = new XmlReader();
    }

    public TmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
        this.xml = new XmlReader();
    }

    public TiledMap load(String fileName) {
        return load(fileName, new Parameters());
    }

    public TiledMap load(String fileName, Parameters parameters) {
        try {
            this.yUp = parameters.yUp;
            FileHandle tmxFile = resolve(fileName);
            this.root = this.xml.parse(tmxFile);
            ObjectMap<String, Texture> textures = new ObjectMap();
            Iterator i$ = loadTilesets(this.root, tmxFile).iterator();
            while (i$.hasNext()) {
                FileHandle textureFile = (FileHandle) i$.next();
                Texture texture = new Texture(textureFile, parameters.generateMipMaps);
                texture.setFilter(parameters.textureMinFilter, parameters.textureMagFilter);
                textures.put(textureFile.path(), texture);
            }
            TiledMap map = loadTilemap(this.root, tmxFile, new DirectImageResolver(textures));
            map.setOwnedResources(textures.values().toArray());
            return map;
        } catch (IOException e) {
            throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
        }
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, Parameters parameter) {
        this.map = null;
        if (parameter != null) {
            this.yUp = parameter.yUp;
        } else {
            this.yUp = true;
        }
        try {
            this.map = loadTilemap(this.root, tmxFile, new AssetManagerImageResolver(manager));
        } catch (Exception e) {
            throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
        }
    }

    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
        return this.map;
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle tmxFile, Parameters parameter) {
        Array<AssetDescriptor> dependencies = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            boolean generateMipMaps = parameter != null ? parameter.generateMipMaps : false;
            AssetLoaderParameters texParams = new TextureParameter();
            texParams.genMipMaps = generateMipMaps;
            if (parameter != null) {
                texParams.minFilter = parameter.textureMinFilter;
                texParams.magFilter = parameter.textureMagFilter;
            }
            Iterator i$ = loadTilesets(this.root, tmxFile).iterator();
            while (i$.hasNext()) {
                dependencies.add(new AssetDescriptor((FileHandle) i$.next(), Texture.class, texParams));
            }
            return dependencies;
        } catch (IOException e) {
            throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
        }
    }

    protected TiledMap loadTilemap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
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
        Element properties = root.getChildByName("properties");
        if (properties != null) {
            loadProperties(map.getProperties(), properties);
        }
        Iterator i$ = root.getChildrenByName("tileset").iterator();
        while (i$.hasNext()) {
            Element element = (Element) i$.next();
            loadTileSet(map, element, tmxFile, imageResolver);
            root.removeChild(element);
        }
        int j = root.getChildCount();
        for (int i = 0; i < j; i++) {
            element = root.getChild(i);
            String name = element.getName();
            if (name.equals("layer")) {
                loadTileLayer(map, element);
            } else {
                if (name.equals("objectgroup")) {
                    loadObjectGroup(map, element);
                }
            }
        }
        return map;
    }

    protected Array<FileHandle> loadTilesets(Element root, FileHandle tmxFile) throws IOException {
        Array<FileHandle> images = new Array();
        Iterator i$ = root.getChildrenByName("tileset").iterator();
        while (i$.hasNext()) {
            FileHandle image;
            Element tileset = (Element) i$.next();
            String source = tileset.getAttribute("source", null);
            if (source != null) {
                FileHandle tsx = getRelativeFileHandle(tmxFile, source);
                image = getRelativeFileHandle(tsx, this.xml.parse(tsx).getChildByName("image").getAttribute("source"));
            } else {
                image = getRelativeFileHandle(tmxFile, tileset.getChildByName("image").getAttribute("source"));
            }
            images.add(image);
        }
        return images;
    }

    protected void loadTileSet(TiledMap map, Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        if (element.getName().equals("tileset")) {
            int imageWidth;
            int imageHeight;
            FileHandle image;
            Element properties;
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
                    image = getRelativeFileHandle(tsx, imageSource);
                } catch (IOException e) {
                    throw new GdxRuntimeException("Error parsing external tileset.");
                }
            }
            imageSource = element.getChildByName("image").getAttribute("source");
            imageWidth = element.getChildByName("image").getIntAttribute("width", 0);
            imageHeight = element.getChildByName("image").getIntAttribute("height", 0);
            image = getRelativeFileHandle(tmxFile, imageSource);
            TextureRegion texture = imageResolver.getImage(image.path());
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
            int stopWidth = texture.getRegionWidth() - tilewidth;
            int stopHeight = texture.getRegionHeight() - tileheight;
            int id = firstgid;
            int y = margin;
            while (y <= stopHeight) {
                int x = margin;
                int id2 = id;
                while (x <= stopWidth) {
                    TextureRegion tileRegion = new TextureRegion(texture, x, y, tilewidth, tileheight);
                    if (!this.yUp) {
                        tileRegion.flip(false, true);
                    }
                    TiledMapTile staticTiledMapTile = new StaticTiledMapTile(tileRegion);
                    staticTiledMapTile.setId(id2);
                    id = id2 + 1;
                    tileset.putTile(id2, staticTiledMapTile);
                    x += tilewidth + spacing;
                    id2 = id;
                }
                y += tileheight + spacing;
                id = id2;
            }
            Iterator i$ = element.getChildrenByName("tile").iterator();
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

    protected static FileHandle getRelativeFileHandle(FileHandle file, String path) {
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
