package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
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
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider.FileTextureProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.util.Iterator;

public class Model implements Disposable {
    public final Array<Animation> animations;
    protected final Array<Disposable> disposables;
    public final Array<Material> materials;
    public final Array<MeshPart> meshParts;
    public final Array<Mesh> meshes;
    private ObjectMap<NodePart, ArrayMap<String, Matrix4>> nodePartBones;
    public final Array<Node> nodes;

    public Model() {
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.meshes = new Array();
        this.meshParts = new Array();
        this.disposables = new Array();
        this.nodePartBones = new ObjectMap();
    }

    public Model(ModelData modelData) {
        this(modelData, new FileTextureProvider());
    }

    public Model(ModelData modelData, TextureProvider textureProvider) {
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.meshes = new Array();
        this.meshParts = new Array();
        this.disposables = new Array();
        this.nodePartBones = new ObjectMap();
        load(modelData, textureProvider);
    }

    private void load(ModelData modelData, TextureProvider textureProvider) {
        loadMeshes(modelData.meshes);
        loadMaterials(modelData.materials, textureProvider);
        loadNodes(modelData.nodes);
        loadAnimations(modelData.animations);
        calculateTransforms();
    }

    private void loadAnimations(Iterable<ModelAnimation> modelAnimations) {
        for (ModelAnimation anim : modelAnimations) {
            Animation animation = new Animation();
            animation.id = anim.id;
            Iterator it = anim.nodeAnimations.iterator();
            while (it.hasNext()) {
                ModelNodeAnimation nanim = (ModelNodeAnimation) it.next();
                Node node = getNode(nanim.nodeId);
                if (node != null) {
                    NodeAnimation nodeAnim = new NodeAnimation();
                    nodeAnim.node = node;
                    Iterator i$ = nanim.keyframes.iterator();
                    while (i$.hasNext()) {
                        ModelNodeKeyframe kf = (ModelNodeKeyframe) i$.next();
                        if (kf.keytime > animation.duration) {
                            animation.duration = kf.keytime;
                        }
                        NodeKeyframe keyframe = new NodeKeyframe();
                        keyframe.keytime = kf.keytime;
                        keyframe.rotation.set(kf.rotation == null ? node.rotation : kf.rotation);
                        keyframe.scale.set(kf.scale == null ? node.scale : kf.scale);
                        keyframe.translation.set(kf.translation == null ? node.translation : kf.translation);
                        nodeAnim.keyframes.add(keyframe);
                    }
                    if (nodeAnim.keyframes.size > 0) {
                        animation.nodeAnimations.add(nodeAnim);
                    }
                }
            }
            if (animation.nodeAnimations.size > 0) {
                this.animations.add(animation);
            }
        }
    }

    private void loadNodes(Iterable<ModelNode> modelNodes) {
        this.nodePartBones.clear();
        for (ModelNode node : modelNodes) {
            this.nodes.add(loadNode(null, node));
        }
        Iterator i$ = this.nodePartBones.entries().iterator();
        while (i$.hasNext()) {
            Entry<NodePart, ArrayMap<String, Matrix4>> e = (Entry) i$.next();
            if (((NodePart) e.key).invBoneBindTransforms == null) {
                ((NodePart) e.key).invBoneBindTransforms = new ArrayMap(Node.class, Matrix4.class);
            }
            ((NodePart) e.key).invBoneBindTransforms.clear();
            Iterator i$2 = ((ArrayMap) e.value).entries().iterator();
            while (i$2.hasNext()) {
                Entry<String, Matrix4> b = (Entry) i$2.next();
                ((NodePart) e.key).invBoneBindTransforms.put(getNode((String) b.key), new Matrix4((Matrix4) b.value).inv());
            }
        }
    }

    private Node loadNode(Node parent, ModelNode modelNode) {
        Node node = new Node();
        node.id = modelNode.id;
        node.parent = parent;
        if (modelNode.translation != null) {
            node.translation.set(modelNode.translation);
        }
        if (modelNode.rotation != null) {
            node.rotation.set(modelNode.rotation);
        }
        if (modelNode.scale != null) {
            node.scale.set(modelNode.scale);
        }
        if (modelNode.parts != null) {
            for (ModelNodePart modelNodePart : modelNode.parts) {
                Iterator i$;
                MeshPart meshPart = null;
                Material meshMaterial = null;
                if (modelNodePart.meshPartId != null) {
                    i$ = this.meshParts.iterator();
                    while (i$.hasNext()) {
                        MeshPart part = (MeshPart) i$.next();
                        if (modelNodePart.meshPartId.equals(part.id)) {
                            meshPart = part;
                            break;
                        }
                    }
                }
                if (modelNodePart.materialId != null) {
                    i$ = this.materials.iterator();
                    while (i$.hasNext()) {
                        Material material = (Material) i$.next();
                        if (modelNodePart.materialId.equals(material.id)) {
                            meshMaterial = material;
                            break;
                        }
                    }
                }
                if (meshPart == null || meshMaterial == null) {
                    throw new GdxRuntimeException("Invalid node: " + node.id);
                }
                if (!(meshPart == null || meshMaterial == null)) {
                    NodePart nodePart = new NodePart();
                    nodePart.meshPart = meshPart;
                    nodePart.material = meshMaterial;
                    node.parts.add(nodePart);
                    if (modelNodePart.bones != null) {
                        this.nodePartBones.put(nodePart, modelNodePart.bones);
                    }
                }
            }
        }
        if (modelNode.children != null) {
            for (ModelNode child : modelNode.children) {
                node.children.add(loadNode(node, child));
            }
        }
        return node;
    }

    private void loadMeshes(Iterable<ModelMesh> meshes) {
        for (ModelMesh mesh : meshes) {
            convertMesh(mesh);
        }
    }

    private void convertMesh(ModelMesh modelMesh) {
        int numIndices = 0;
        for (ModelMeshPart part : modelMesh.parts) {
            numIndices += part.indices.length;
        }
        VertexAttributes attributes = new VertexAttributes(modelMesh.attributes);
        Mesh mesh = new Mesh(true, modelMesh.vertices.length / (attributes.vertexSize / 4), numIndices, attributes);
        this.meshes.add(mesh);
        this.disposables.add(mesh);
        BufferUtils.copy(modelMesh.vertices, mesh.getVerticesBuffer(), modelMesh.vertices.length, 0);
        int offset = 0;
        mesh.getIndicesBuffer().clear();
        for (ModelMeshPart part2 : modelMesh.parts) {
            MeshPart meshPart = new MeshPart();
            meshPart.id = part2.id;
            meshPart.primitiveType = part2.primitiveType;
            meshPart.indexOffset = offset;
            meshPart.numVertices = part2.indices.length;
            meshPart.mesh = mesh;
            mesh.getIndicesBuffer().put(part2.indices);
            offset += meshPart.numVertices;
            this.meshParts.add(meshPart);
        }
        mesh.getIndicesBuffer().position(0);
    }

    private void loadMaterials(Iterable<ModelMaterial> modelMaterials, TextureProvider textureProvider) {
        for (ModelMaterial mtl : modelMaterials) {
            this.materials.add(convertMaterial(mtl, textureProvider));
        }
    }

    private Material convertMaterial(ModelMaterial mtl, TextureProvider textureProvider) {
        Material result = new Material();
        result.id = mtl.id;
        if (mtl.ambient != null) {
            result.set(new ColorAttribute(ColorAttribute.Ambient, mtl.ambient));
        }
        if (mtl.diffuse != null) {
            result.set(new ColorAttribute(ColorAttribute.Diffuse, mtl.diffuse));
        }
        if (mtl.specular != null) {
            result.set(new ColorAttribute(ColorAttribute.Specular, mtl.specular));
        }
        if (mtl.emissive != null) {
            result.set(new ColorAttribute(ColorAttribute.Emissive, mtl.emissive));
        }
        if (mtl.reflection != null) {
            result.set(new ColorAttribute(ColorAttribute.Reflection, mtl.reflection));
        }
        if (mtl.shininess > 0.0f) {
            result.set(new FloatAttribute(FloatAttribute.Shininess, mtl.shininess));
        }
        if (mtl.opacity != TextTrackStyle.DEFAULT_FONT_SCALE) {
            result.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, mtl.opacity));
        }
        ObjectMap<String, Texture> textures = new ObjectMap();
        if (mtl.textures != null) {
            Iterator i$ = mtl.textures.iterator();
            while (i$.hasNext()) {
                Texture texture;
                ModelTexture tex = (ModelTexture) i$.next();
                if (textures.containsKey(tex.fileName)) {
                    texture = (Texture) textures.get(tex.fileName);
                } else {
                    texture = textureProvider.load(tex.fileName);
                    textures.put(tex.fileName, texture);
                    this.disposables.add(texture);
                }
                TextureDescriptor descriptor = new TextureDescriptor(texture);
                descriptor.minFilter = TextureFilter.Linear;
                descriptor.magFilter = TextureFilter.Linear;
                descriptor.uWrap = TextureWrap.Repeat;
                descriptor.vWrap = TextureWrap.Repeat;
                switch (tex.usage) {
                    case CompletionEvent.STATUS_CONFLICT /*2*/:
                        result.set(new TextureAttribute(TextureAttribute.Diffuse, descriptor));
                        break;
                    case Place.TYPE_ART_GALLERY /*5*/:
                        result.set(new TextureAttribute(TextureAttribute.Specular, descriptor));
                        break;
                    case Place.TYPE_BAKERY /*7*/:
                        result.set(new TextureAttribute(TextureAttribute.Normal, descriptor));
                        break;
                    case GameHelper.CLIENT_SNAPSHOT /*8*/:
                        result.set(new TextureAttribute(TextureAttribute.Bump, descriptor));
                        break;
                    default:
                        break;
                }
            }
        }
        return result;
    }

    public void manageDisposable(Disposable disposable) {
        if (!this.disposables.contains(disposable, true)) {
            this.disposables.add(disposable);
        }
    }

    public Iterable<Disposable> getManagedDisposables() {
        return this.disposables;
    }

    public void dispose() {
        Iterator i$ = this.disposables.iterator();
        while (i$.hasNext()) {
            ((Disposable) i$.next()).dispose();
        }
    }

    public void calculateTransforms() {
        int i;
        int n = this.nodes.size;
        for (i = 0; i < n; i++) {
            ((Node) this.nodes.get(i)).calculateTransforms(true);
        }
        for (i = 0; i < n; i++) {
            ((Node) this.nodes.get(i)).calculateBoneTransforms(true);
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox out) {
        out.inf();
        return extendBoundingBox(out);
    }

    public BoundingBox extendBoundingBox(BoundingBox out) {
        int n = this.nodes.size;
        for (int i = 0; i < n; i++) {
            ((Node) this.nodes.get(i)).extendBoundingBox(out);
        }
        return out;
    }

    public Animation getAnimation(String id) {
        return getAnimation(id, true);
    }

    public Animation getAnimation(String id, boolean ignoreCase) {
        int n = this.animations.size;
        int i;
        Animation animation;
        if (ignoreCase) {
            for (i = 0; i < n; i++) {
                animation = (Animation) this.animations.get(i);
                if (animation.id.equalsIgnoreCase(id)) {
                    return animation;
                }
            }
        } else {
            for (i = 0; i < n; i++) {
                animation = (Animation) this.animations.get(i);
                if (animation.id.equals(id)) {
                    return animation;
                }
            }
        }
        return null;
    }

    public Material getMaterial(String id) {
        return getMaterial(id, true);
    }

    public Material getMaterial(String id, boolean ignoreCase) {
        int n = this.materials.size;
        int i;
        Material material;
        if (ignoreCase) {
            for (i = 0; i < n; i++) {
                material = (Material) this.materials.get(i);
                if (material.id.equalsIgnoreCase(id)) {
                    return material;
                }
            }
        } else {
            for (i = 0; i < n; i++) {
                material = (Material) this.materials.get(i);
                if (material.id.equals(id)) {
                    return material;
                }
            }
        }
        return null;
    }

    public Node getNode(String id) {
        return getNode(id, true);
    }

    public Node getNode(String id, boolean recursive) {
        return getNode(id, recursive, false);
    }

    public Node getNode(String id, boolean recursive, boolean ignoreCase) {
        return Node.getNode(this.nodes, id, recursive, ignoreCase);
    }
}
