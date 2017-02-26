package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.model.data.ModelAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BaseJsonReader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonValue;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Iterator;

public class G3dModelLoader extends ModelLoader<AssetLoaderParameters<Model>> {
    public static final short VERSION_HI = (short) 0;
    public static final short VERSION_LO = (short) 1;
    protected final BaseJsonReader reader;
    private final Quaternion tempQ;

    public G3dModelLoader(BaseJsonReader reader) {
        this(reader, null);
    }

    public G3dModelLoader(BaseJsonReader reader, FileHandleResolver resolver) {
        super(resolver);
        this.tempQ = new Quaternion();
        this.reader = reader;
    }

    public ModelData loadModelData(FileHandle fileHandle, AssetLoaderParameters<Model> assetLoaderParameters) {
        return parseModel(fileHandle);
    }

    public ModelData parseModel(FileHandle handle) {
        JsonValue json = this.reader.parse(handle);
        ModelData model = new ModelData();
        JsonValue version = json.require("version");
        model.version[0] = (short) version.getInt(0);
        model.version[1] = (short) version.getInt(1);
        if (model.version[0] == (short) 0 && model.version[1] == VERSION_LO) {
            model.id = json.getString("id", "");
            parseMeshes(model, json);
            parseMaterials(model, json, handle.parent().path());
            parseNodes(model, json);
            parseAnimations(model, json);
            return model;
        }
        throw new GdxRuntimeException("Model version not supported");
    }

    private void parseMeshes(ModelData model, JsonValue json) {
        JsonValue meshes = json.require("meshes");
        model.meshes.ensureCapacity(meshes.size);
        for (JsonValue mesh = meshes.child(); mesh != null; mesh = mesh.next()) {
            ModelMesh jsonMesh = new ModelMesh();
            String id = mesh.getString("id", "");
            jsonMesh.id = id;
            jsonMesh.attributes = parseAttributes(mesh.require("attributes"));
            JsonValue vertices = mesh.require("vertices");
            float[] verts = new float[vertices.size];
            int j = 0;
            JsonValue value = vertices.child();
            while (value != null) {
                verts[j] = value.asFloat();
                value = value.next();
                j++;
            }
            jsonMesh.vertices = verts;
            JsonValue meshParts = mesh.require("parts");
            Array<ModelMeshPart> parts = new Array();
            for (JsonValue meshPart = meshParts.child(); meshPart != null; meshPart = meshPart.next()) {
                ModelMeshPart jsonPart = new ModelMeshPart();
                String partId = meshPart.getString("id", null);
                if (id == null) {
                    throw new GdxRuntimeException("Not id given for mesh part");
                }
                Iterator i$ = parts.iterator();
                while (i$.hasNext()) {
                    if (((ModelMeshPart) i$.next()).id.equals(partId)) {
                        throw new GdxRuntimeException("Mesh part with id '" + partId + "' already in defined");
                    }
                }
                jsonPart.id = partId;
                String type = meshPart.getString("type", null);
                if (type == null) {
                    throw new GdxRuntimeException("No primitive type given for mesh part '" + partId + "'");
                }
                jsonPart.primitiveType = parseType(type);
                JsonValue indices = meshPart.require("indices");
                short[] partIndices = new short[indices.size];
                int k = 0;
                value = indices.child();
                while (value != null) {
                    partIndices[k] = (short) value.asInt();
                    value = value.next();
                    k++;
                }
                jsonPart.indices = partIndices;
                parts.add(jsonPart);
            }
            jsonMesh.parts = (ModelMeshPart[]) parts.toArray(ModelMeshPart.class);
            model.meshes.add(jsonMesh);
        }
    }

    private int parseType(String type) {
        if (type.equals("TRIANGLES")) {
            return 4;
        }
        if (type.equals("LINES")) {
            return 1;
        }
        if (type.equals("POINTS")) {
            return 0;
        }
        if (type.equals("TRIANGLE_STRIP")) {
            return 5;
        }
        if (type.equals("LINE_STRIP")) {
            return 3;
        }
        throw new GdxRuntimeException("Unknown primitive type '" + type + "', should be one of triangle, trianglestrip, line, linestrip, lineloop or point");
    }

    private VertexAttribute[] parseAttributes(JsonValue attributes) {
        Array<VertexAttribute> vertexAttributes = new Array();
        JsonValue value = attributes.child();
        int blendWeightCount = 0;
        int unit = 0;
        while (value != null) {
            int blendWeightCount2;
            int unit2;
            String attr = value.asString();
            if (attr.equals("POSITION")) {
                vertexAttributes.add(VertexAttribute.Position());
                blendWeightCount2 = blendWeightCount;
                unit2 = unit;
            } else if (attr.equals("NORMAL")) {
                vertexAttributes.add(VertexAttribute.Normal());
                blendWeightCount2 = blendWeightCount;
                unit2 = unit;
            } else if (attr.equals("COLOR")) {
                vertexAttributes.add(VertexAttribute.ColorUnpacked());
                blendWeightCount2 = blendWeightCount;
                unit2 = unit;
            } else if (attr.equals("COLORPACKED")) {
                vertexAttributes.add(VertexAttribute.Color());
                blendWeightCount2 = blendWeightCount;
                unit2 = unit;
            } else if (attr.equals("TANGENT")) {
                vertexAttributes.add(VertexAttribute.Tangent());
                blendWeightCount2 = blendWeightCount;
                unit2 = unit;
            } else if (attr.equals("BINORMAL")) {
                vertexAttributes.add(VertexAttribute.Binormal());
                blendWeightCount2 = blendWeightCount;
                unit2 = unit;
            } else if (attr.startsWith("TEXCOORD")) {
                unit2 = unit + 1;
                vertexAttributes.add(VertexAttribute.TexCoords(unit));
                blendWeightCount2 = blendWeightCount;
            } else if (attr.startsWith("BLENDWEIGHT")) {
                blendWeightCount2 = blendWeightCount + 1;
                vertexAttributes.add(VertexAttribute.BoneWeight(blendWeightCount));
                unit2 = unit;
            } else {
                throw new GdxRuntimeException("Unknown vertex attribute '" + attr + "', should be one of position, normal, uv, tangent or binormal");
            }
            value = value.next();
            blendWeightCount = blendWeightCount2;
            unit = unit2;
        }
        return (VertexAttribute[]) vertexAttributes.toArray(VertexAttribute.class);
    }

    private void parseMaterials(ModelData model, JsonValue json, String materialDir) {
        JsonValue materials = json.get("materials");
        if (materials != null) {
            model.materials.ensureCapacity(materials.size);
            for (JsonValue material = materials.child(); material != null; material = material.next()) {
                ModelMaterial jsonMaterial = new ModelMaterial();
                String id = material.getString("id", null);
                if (id == null) {
                    throw new GdxRuntimeException("Material needs an id.");
                }
                jsonMaterial.id = id;
                JsonValue diffuse = material.get("diffuse");
                if (diffuse != null) {
                    jsonMaterial.diffuse = parseColor(diffuse);
                }
                JsonValue ambient = material.get("ambient");
                if (ambient != null) {
                    jsonMaterial.ambient = parseColor(ambient);
                }
                JsonValue emissive = material.get("emissive");
                if (emissive != null) {
                    jsonMaterial.emissive = parseColor(emissive);
                }
                JsonValue specular = material.get("specular");
                if (specular != null) {
                    jsonMaterial.specular = parseColor(specular);
                }
                JsonValue reflection = material.get("reflection");
                if (reflection != null) {
                    jsonMaterial.reflection = parseColor(reflection);
                }
                jsonMaterial.shininess = material.getFloat(FloatAttribute.ShininessAlias, 0.0f);
                jsonMaterial.opacity = material.getFloat("opacity", TextTrackStyle.DEFAULT_FONT_SCALE);
                JsonValue textures = material.get("textures");
                if (textures != null) {
                    JsonValue texture = textures.child();
                    while (texture != null) {
                        ModelTexture jsonTexture = new ModelTexture();
                        String textureId = texture.getString("id", null);
                        if (textureId == null) {
                            throw new GdxRuntimeException("Texture has no id.");
                        }
                        jsonTexture.id = textureId;
                        String fileName = texture.getString("filename", null);
                        if (fileName == null) {
                            throw new GdxRuntimeException("Texture needs filename.");
                        }
                        String str;
                        String textureType;
                        StringBuilder append = new StringBuilder().append(materialDir);
                        if (materialDir.length() != 0) {
                            if (!materialDir.endsWith("/")) {
                                str = "/";
                                jsonTexture.fileName = append.append(str).append(fileName).toString();
                                jsonTexture.uvTranslation = readVector2(texture.get("uvTranslation"), 0.0f, 0.0f);
                                jsonTexture.uvScaling = readVector2(texture.get("uvScaling"), TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
                                textureType = texture.getString("type", null);
                                if (textureType != null) {
                                    throw new GdxRuntimeException("Texture needs type.");
                                }
                                jsonTexture.usage = parseTextureUsage(textureType);
                                if (jsonMaterial.textures == null) {
                                    jsonMaterial.textures = new Array();
                                }
                                jsonMaterial.textures.add(jsonTexture);
                                texture = texture.next();
                            }
                        }
                        str = "";
                        jsonTexture.fileName = append.append(str).append(fileName).toString();
                        jsonTexture.uvTranslation = readVector2(texture.get("uvTranslation"), 0.0f, 0.0f);
                        jsonTexture.uvScaling = readVector2(texture.get("uvScaling"), TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
                        textureType = texture.getString("type", null);
                        if (textureType != null) {
                            jsonTexture.usage = parseTextureUsage(textureType);
                            if (jsonMaterial.textures == null) {
                                jsonMaterial.textures = new Array();
                            }
                            jsonMaterial.textures.add(jsonTexture);
                            texture = texture.next();
                        } else {
                            throw new GdxRuntimeException("Texture needs type.");
                        }
                    }
                    continue;
                }
                model.materials.add(jsonMaterial);
            }
        }
    }

    private int parseTextureUsage(String value) {
        if (value.equalsIgnoreCase("AMBIENT")) {
            return 4;
        }
        if (value.equalsIgnoreCase("BUMP")) {
            return 8;
        }
        if (value.equalsIgnoreCase("DIFFUSE")) {
            return 2;
        }
        if (value.equalsIgnoreCase("EMISSIVE")) {
            return 3;
        }
        if (value.equalsIgnoreCase("NONE")) {
            return 1;
        }
        if (value.equalsIgnoreCase("NORMAL")) {
            return 7;
        }
        if (value.equalsIgnoreCase("REFLECTION")) {
            return 10;
        }
        if (value.equalsIgnoreCase("SHININESS")) {
            return 6;
        }
        if (value.equalsIgnoreCase("SPECULAR")) {
            return 5;
        }
        if (value.equalsIgnoreCase("TRANSPARENCY")) {
            return 9;
        }
        return 0;
    }

    private Color parseColor(JsonValue colorArray) {
        if (colorArray.size >= 3) {
            return new Color(colorArray.getFloat(0), colorArray.getFloat(1), colorArray.getFloat(2), TextTrackStyle.DEFAULT_FONT_SCALE);
        }
        throw new GdxRuntimeException("Expected Color values <> than three.");
    }

    private Vector2 readVector2(JsonValue vectorArray, float x, float y) {
        if (vectorArray == null) {
            return new Vector2(x, y);
        }
        if (vectorArray.size == 2) {
            return new Vector2(vectorArray.getFloat(0), vectorArray.getFloat(1));
        }
        throw new GdxRuntimeException("Expected Vector2 values <> than two.");
    }

    private Array<ModelNode> parseNodes(ModelData model, JsonValue json) {
        JsonValue nodes = json.get("nodes");
        if (nodes == null) {
            throw new GdxRuntimeException("At least one node is required.");
        }
        model.nodes.ensureCapacity(nodes.size);
        for (JsonValue node = nodes.child(); node != null; node = node.next()) {
            model.nodes.add(parseNodesRecursively(node));
        }
        return model.nodes;
    }

    private ModelNode parseNodesRecursively(JsonValue json) {
        ModelNode jsonNode = new ModelNode();
        String id = json.getString("id", null);
        if (id == null) {
            throw new GdxRuntimeException("Node id missing.");
        }
        Vector3 vector3;
        int i;
        jsonNode.id = id;
        JsonValue translation = json.get("translation");
        if (translation != null) {
            int i2 = translation.size;
            if (r0 != 3) {
                throw new GdxRuntimeException("Node translation incomplete");
            }
        }
        jsonNode.translation = translation == null ? null : new Vector3(translation.getFloat(0), translation.getFloat(1), translation.getFloat(2));
        JsonValue rotation = json.get("rotation");
        if (rotation != null) {
            i2 = rotation.size;
            if (r0 != 4) {
                throw new GdxRuntimeException("Node rotation incomplete");
            }
        }
        jsonNode.rotation = rotation == null ? null : new Quaternion(rotation.getFloat(0), rotation.getFloat(1), rotation.getFloat(2), rotation.getFloat(3));
        JsonValue scale = json.get("scale");
        if (scale != null) {
            i2 = scale.size;
            if (r0 != 3) {
                throw new GdxRuntimeException("Node scale incomplete");
            }
        }
        if (scale == null) {
            vector3 = null;
        } else {
            vector3 = new Vector3(scale.getFloat(0), scale.getFloat(1), scale.getFloat(2));
        }
        jsonNode.scale = vector3;
        String meshId = json.getString("mesh", null);
        if (meshId != null) {
            jsonNode.meshId = meshId;
        }
        JsonValue materials = json.get("parts");
        if (materials != null) {
            jsonNode.parts = new ModelNodePart[materials.size];
            i = 0;
            JsonValue material = materials.child();
            while (material != null) {
                ModelNodePart nodePart = new ModelNodePart();
                String meshPartId = material.getString("meshpartid", null);
                String materialId = material.getString("materialid", null);
                if (meshPartId == null || materialId == null) {
                    throw new GdxRuntimeException("Node " + id + " part is missing meshPartId or materialId");
                }
                nodePart.materialId = materialId;
                nodePart.meshPartId = meshPartId;
                JsonValue bones = material.get("bones");
                if (bones != null) {
                    nodePart.bones = new ArrayMap(true, bones.size, String.class, Matrix4.class);
                    int j = 0;
                    JsonValue bone = bones.child();
                    while (bone != null) {
                        String nodeId = bone.getString("node", null);
                        if (nodeId == null) {
                            throw new GdxRuntimeException("Bone node ID missing");
                        }
                        Matrix4 transform = new Matrix4();
                        JsonValue val = bone.get("translation");
                        if (val != null) {
                            i2 = val.size;
                            if (r0 >= 3) {
                                transform.translate(val.getFloat(0), val.getFloat(1), val.getFloat(2));
                            }
                        }
                        val = bone.get("rotation");
                        if (val != null) {
                            i2 = val.size;
                            if (r0 >= 4) {
                                transform.rotate(this.tempQ.set(val.getFloat(0), val.getFloat(1), val.getFloat(2), val.getFloat(3)));
                            }
                        }
                        val = bone.get("scale");
                        if (val != null) {
                            i2 = val.size;
                            if (r0 >= 3) {
                                transform.scale(val.getFloat(0), val.getFloat(1), val.getFloat(2));
                            }
                        }
                        nodePart.bones.put(nodeId, transform);
                        bone = bone.next();
                        j++;
                    }
                    continue;
                }
                jsonNode.parts[i] = nodePart;
                material = material.next();
                i++;
            }
        }
        JsonValue children = json.get("children");
        if (children != null) {
            jsonNode.children = new ModelNode[children.size];
            i = 0;
            JsonValue child = children.child();
            while (child != null) {
                jsonNode.children[i] = parseNodesRecursively(child);
                child = child.next();
                i++;
            }
        }
        return jsonNode;
    }

    private void parseAnimations(ModelData model, JsonValue json) {
        JsonValue animations = json.get("animations");
        if (animations != null) {
            model.animations.ensureCapacity(animations.size);
            for (JsonValue anim = animations.child(); anim != null; anim = anim.next()) {
                JsonValue nodes = anim.get("bones");
                if (nodes != null) {
                    ModelAnimation animation = new ModelAnimation();
                    model.animations.add(animation);
                    animation.nodeAnimations.ensureCapacity(nodes.size);
                    animation.id = anim.getString("id");
                    for (JsonValue node = nodes.child(); node != null; node = node.next()) {
                        JsonValue keyframes = node.get("keyframes");
                        ModelNodeAnimation nodeAnim = new ModelNodeAnimation();
                        animation.nodeAnimations.add(nodeAnim);
                        nodeAnim.nodeId = node.getString("boneId");
                        nodeAnim.keyframes.ensureCapacity(keyframes.size);
                        for (JsonValue keyframe = keyframes.child(); keyframe != null; keyframe = keyframe.next()) {
                            ModelNodeKeyframe kf = new ModelNodeKeyframe();
                            nodeAnim.keyframes.add(kf);
                            kf.keytime = keyframe.getFloat("keytime") / 1000.0f;
                            JsonValue translation = keyframe.get("translation");
                            if (translation != null && translation.size == 3) {
                                kf.translation = new Vector3(translation.getFloat(0), translation.getFloat(1), translation.getFloat(2));
                            }
                            JsonValue rotation = keyframe.get("rotation");
                            if (rotation != null && rotation.size == 4) {
                                kf.rotation = new Quaternion(rotation.getFloat(0), rotation.getFloat(1), rotation.getFloat(2), rotation.getFloat(3));
                            }
                            JsonValue scale = keyframe.get("scale");
                            if (scale != null && scale.size == 3) {
                                kf.scale = new Vector3(scale.getFloat(0), scale.getFloat(1), scale.getFloat(2));
                            }
                        }
                    }
                }
            }
        }
    }
}
