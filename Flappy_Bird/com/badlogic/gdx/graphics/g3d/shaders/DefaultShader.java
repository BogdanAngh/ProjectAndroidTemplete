package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.util.Iterator;

public class DefaultShader extends BaseShader {
    public static int defaultCullFace;
    public static int defaultDepthFunc;
    private static String defaultFragmentShader;
    private static String defaultVertexShader;
    public static boolean ignoreUnimplemented;
    protected static long implementedFlags;
    private static final long optionalAttributes;
    protected final AmbientCubemap ambientCubemap;
    private Camera camera;
    Material currentMaterial;
    protected int dirLightsColorOffset;
    protected int dirLightsDirectionOffset;
    protected int dirLightsLoc;
    protected int dirLightsSize;
    protected final DirectionalLight[] directionalLights;
    protected final boolean environmentCubemap;
    protected final boolean fog;
    protected final boolean lighting;
    private boolean lightsSet;
    private long materialMask;
    private Matrix3 normalMatrix;
    protected final PointLight[] pointLights;
    protected int pointLightsColorOffset;
    protected int pointLightsIntensityOffset;
    protected int pointLightsLoc;
    protected int pointLightsPositionOffset;
    protected int pointLightsSize;
    private Renderable renderable;
    protected final boolean shadowMap;
    private float time;
    private final Vector3 tmpV1;
    public final int u_alphaTest;
    protected final int u_ambientCubemap;
    public final int u_bones;
    public final int u_cameraDirection;
    public final int u_cameraPosition;
    public final int u_cameraUp;
    public final int u_diffuseColor;
    public final int u_diffuseTexture;
    protected final int u_dirLights0color;
    protected final int u_dirLights0direction;
    protected final int u_dirLights1color;
    public final int u_emissiveColor;
    protected final int u_environmentCubemap;
    protected final int u_fogColor;
    public final int u_normalMatrix;
    public final int u_normalTexture;
    public final int u_opacity;
    protected final int u_pointLights0color;
    protected final int u_pointLights0intensity;
    protected final int u_pointLights0position;
    protected final int u_pointLights1color;
    public final int u_projTrans;
    public final int u_projViewTrans;
    public final int u_projViewWorldTrans;
    public final int u_reflectionColor;
    protected final int u_shadowMapProjViewTrans;
    protected final int u_shadowPCFOffset;
    protected final int u_shadowTexture;
    public final int u_shininess;
    public final int u_specularColor;
    public final int u_specularTexture;
    public final int u_time;
    public final int u_viewTrans;
    public final int u_viewWorldTrans;
    public final int u_worldTrans;
    private long vertexMask;

    public static class Config {
        public String fragmentShader;
        public int numBones;
        public int numDirectionalLights;
        public int numPointLights;
        public int numSpotLights;
        public String vertexShader;

        public Config() {
            this.vertexShader = null;
            this.fragmentShader = null;
            this.numDirectionalLights = 2;
            this.numPointLights = 5;
            this.numSpotLights = 0;
            this.numBones = 12;
        }

        public Config(String vertexShader, String fragmentShader) {
            this.vertexShader = null;
            this.fragmentShader = null;
            this.numDirectionalLights = 2;
            this.numPointLights = 5;
            this.numSpotLights = 0;
            this.numBones = 12;
            this.vertexShader = vertexShader;
            this.fragmentShader = fragmentShader;
        }
    }

    public static class Inputs {
        public static final Uniform alphaTest;
        public static final Uniform ambientCube;
        public static final Uniform bones;
        public static final Uniform cameraDirection;
        public static final Uniform cameraPosition;
        public static final Uniform cameraUp;
        public static final Uniform diffuseColor;
        public static final Uniform diffuseTexture;
        public static final Uniform dirLights;
        public static final Uniform emissiveColor;
        public static final Uniform environmentCubemap;
        public static final Uniform normalMatrix;
        public static final Uniform normalTexture;
        public static final Uniform opacity;
        public static final Uniform pointLights;
        public static final Uniform projTrans;
        public static final Uniform projViewTrans;
        public static final Uniform projViewWorldTrans;
        public static final Uniform reflectionColor;
        public static final Uniform shininess;
        public static final Uniform specularColor;
        public static final Uniform specularTexture;
        public static final Uniform viewTrans;
        public static final Uniform worldTrans;
        public static final Uniform worldViewTrans;

        static {
            projTrans = new Uniform("u_projTrans");
            viewTrans = new Uniform("u_viewTrans");
            projViewTrans = new Uniform("u_projViewTrans");
            cameraPosition = new Uniform("u_cameraPosition");
            cameraDirection = new Uniform("u_cameraDirection");
            cameraUp = new Uniform("u_cameraUp");
            worldTrans = new Uniform("u_worldTrans");
            worldViewTrans = new Uniform("u_worldViewTrans");
            projViewWorldTrans = new Uniform("u_projViewWorldTrans");
            normalMatrix = new Uniform("u_normalMatrix");
            bones = new Uniform("u_bones");
            shininess = new Uniform("u_shininess", FloatAttribute.Shininess);
            opacity = new Uniform("u_opacity", BlendingAttribute.Type);
            diffuseColor = new Uniform("u_diffuseColor", ColorAttribute.Diffuse);
            diffuseTexture = new Uniform("u_diffuseTexture", TextureAttribute.Diffuse);
            specularColor = new Uniform("u_specularColor", ColorAttribute.Specular);
            specularTexture = new Uniform("u_specularTexture", TextureAttribute.Specular);
            emissiveColor = new Uniform("u_emissiveColor", ColorAttribute.Emissive);
            reflectionColor = new Uniform("u_reflectionColor", ColorAttribute.Reflection);
            normalTexture = new Uniform("u_normalTexture", TextureAttribute.Normal);
            alphaTest = new Uniform("u_alphaTest", FloatAttribute.AlphaTest);
            ambientCube = new Uniform("u_ambientCubemap");
            dirLights = new Uniform("u_dirLights");
            pointLights = new Uniform("u_pointLights");
            environmentCubemap = new Uniform("u_environmentCubemap");
        }
    }

    public static class Setters {
        public static final Setter cameraDirection;
        public static final Setter cameraPosition;
        public static final Setter cameraUp;
        public static final Setter diffuseColor;
        public static final Setter diffuseTexture;
        public static final Setter emissiveColor;
        public static final Setter environmentCubemap;
        public static final Setter normalMatrix;
        public static final Setter normalTexture;
        public static final Setter projTrans;
        public static final Setter projViewTrans;
        public static final Setter projViewWorldTrans;
        public static final Setter reflectionColor;
        public static final Setter shininess;
        public static final Setter specularColor;
        public static final Setter specularTexture;
        public static final Setter viewTrans;
        public static final Setter worldTrans;
        public static final Setter worldViewTrans;

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.1 */
        static class C03531 implements Setter {
            C03531() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, shader.camera.projection);
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.2 */
        static class C03542 implements Setter {
            C03542() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, shader.camera.view);
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.3 */
        static class C03553 implements Setter {
            C03553() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, shader.camera.combined);
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.4 */
        static class C03564 implements Setter {
            C03564() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, shader.camera.position.f105x, shader.camera.position.f106y, shader.camera.position.f107z, 1.1881f / (shader.camera.far * shader.camera.far));
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.5 */
        static class C03575 implements Setter {
            C03575() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, shader.camera.direction);
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.6 */
        static class C03586 implements Setter {
            C03586() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, shader.camera.up);
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.7 */
        static class C03597 implements Setter {
            C03597() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return false;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, renderable.worldTransform);
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.8 */
        static class C03608 implements Setter {
            final Matrix4 temp;

            C03608() {
                this.temp = new Matrix4();
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return false;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, this.temp.set(renderable.worldTransform).mul(shader.camera.view));
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.9 */
        static class C03619 implements Setter {
            final Matrix4 temp;

            C03619() {
                this.temp = new Matrix4();
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return false;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, this.temp.set(shader.camera.combined).mul(renderable.worldTransform));
            }
        }

        public static class ACubemap implements Setter {
            private static final float[] ones;
            private static final Vector3 tmpV1;
            private final AmbientCubemap cacheAmbientCubemap;
            public final int dirLightsOffset;
            public final int pointLightsOffset;

            static {
                ones = new float[]{TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE};
                tmpV1 = new Vector3();
            }

            public ACubemap(int dirLightsOffset, int pointLightsOffset) {
                this.cacheAmbientCubemap = new AmbientCubemap();
                this.dirLightsOffset = dirLightsOffset;
                this.pointLightsOffset = pointLightsOffset;
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return false;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                if (renderable.environment == null) {
                    shader.program.setUniform3fv(shader.loc(inputID), ones, 0, ones.length);
                    return;
                }
                int i;
                renderable.worldTransform.getTranslation(tmpV1);
                if (renderable.environment.has(ColorAttribute.AmbientLight)) {
                    this.cacheAmbientCubemap.set(((ColorAttribute) renderable.environment.get(ColorAttribute.AmbientLight)).color);
                }
                for (i = this.dirLightsOffset; i < renderable.environment.directionalLights.size; i++) {
                    this.cacheAmbientCubemap.add(((DirectionalLight) renderable.environment.directionalLights.get(i)).color, ((DirectionalLight) renderable.environment.directionalLights.get(i)).direction);
                }
                for (i = this.pointLightsOffset; i < renderable.environment.pointLights.size; i++) {
                    this.cacheAmbientCubemap.add(((PointLight) renderable.environment.pointLights.get(i)).color, ((PointLight) renderable.environment.pointLights.get(i)).position, tmpV1, ((PointLight) renderable.environment.pointLights.get(i)).intensity);
                }
                this.cacheAmbientCubemap.clamp();
                shader.program.setUniform3fv(shader.loc(inputID), this.cacheAmbientCubemap.data, 0, this.cacheAmbientCubemap.data.length);
            }
        }

        public static class Bones implements Setter {
            private static final Matrix4 idtMatrix;
            public final float[] bones;

            static {
                idtMatrix = new Matrix4();
            }

            public Bones(int numBones) {
                this.bones = new float[(numBones * 16)];
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return false;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                for (int i = 0; i < this.bones.length; i++) {
                    int idx = i / 16;
                    float[] fArr = this.bones;
                    float f = (renderable.bones == null || idx >= renderable.bones.length || renderable.bones[idx] == null) ? idtMatrix.val[i % 16] : renderable.bones[idx].val[i % 16];
                    fArr[i] = f;
                }
                shader.program.setUniformMatrix4fv(shader.loc(inputID), this.bones, 0, this.bones.length);
            }
        }

        static {
            projTrans = new C03531();
            viewTrans = new C03542();
            projViewTrans = new C03553();
            cameraPosition = new C03564();
            cameraDirection = new C03575();
            cameraUp = new C03586();
            worldTrans = new C03597();
            worldViewTrans = new C03608();
            projViewWorldTrans = new C03619();
            normalMatrix = new Setter() {
                private final Matrix3 tmpM;

                {
                    this.tmpM = new Matrix3();
                }

                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, this.tmpM.set(renderable.worldTransform).inv().transpose());
                }
            };
            shininess = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ((FloatAttribute) combinedAttributes.get(FloatAttribute.Shininess)).value);
                }
            };
            diffuseColor = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Diffuse)).color);
                }
            };
            diffuseTexture = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Diffuse)).textureDescription));
                }
            };
            specularColor = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Specular)).color);
                }
            };
            specularTexture = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Specular)).textureDescription));
                }
            };
            emissiveColor = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Emissive)).color);
                }
            };
            reflectionColor = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Reflection)).color);
                }
            };
            normalTexture = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Normal)).textureDescription));
                }
            };
            environmentCubemap = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return false;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    if (combinedAttributes.has(CubemapAttribute.EnvironmentMap)) {
                        shader.set(inputID, shader.context.textureBinder.bind(((CubemapAttribute) combinedAttributes.get(CubemapAttribute.EnvironmentMap)).textureDescription));
                    }
                }
            };
        }
    }

    static {
        defaultVertexShader = null;
        defaultFragmentShader = null;
        implementedFlags = (((BlendingAttribute.Type | TextureAttribute.Diffuse) | ColorAttribute.Diffuse) | ColorAttribute.Specular) | FloatAttribute.Shininess;
        ignoreUnimplemented = true;
        defaultCullFace = Place.TYPE_SYNTHETIC_GEOCODE;
        defaultDepthFunc = GL20.GL_LEQUAL;
        optionalAttributes = IntAttribute.CullFace | DepthTestAttribute.Type;
    }

    public static String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/default.vertex.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/default.fragment.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public DefaultShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public DefaultShader(Renderable renderable, Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }

    public DefaultShader(Renderable renderable, Config config, String prefix) {
        this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(), config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public DefaultShader(Renderable renderable, Config config, String prefix, String vertexShader, String fragmentShader) {
        this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
    }

    public DefaultShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        boolean z;
        int i;
        int i2;
        int i3 = -1;
        boolean z2 = true;
        int i4 = 0;
        this.u_dirLights0color = register(new Uniform("u_dirLights[0].color"));
        this.u_dirLights0direction = register(new Uniform("u_dirLights[0].direction"));
        this.u_dirLights1color = register(new Uniform("u_dirLights[1].color"));
        this.u_pointLights0color = register(new Uniform("u_pointLights[0].color"));
        this.u_pointLights0position = register(new Uniform("u_pointLights[0].position"));
        this.u_pointLights0intensity = register(new Uniform("u_pointLights[0].intensity"));
        this.u_pointLights1color = register(new Uniform("u_pointLights[1].color"));
        this.u_fogColor = register(new Uniform("u_fogColor"));
        this.u_shadowMapProjViewTrans = register(new Uniform("u_shadowMapProjViewTrans"));
        this.u_shadowTexture = register(new Uniform("u_shadowTexture"));
        this.u_shadowPCFOffset = register(new Uniform("u_shadowPCFOffset"));
        this.ambientCubemap = new AmbientCubemap();
        this.normalMatrix = new Matrix3();
        this.tmpV1 = new Vector3();
        this.program = shaderProgram;
        if (renderable.environment != null) {
            z = true;
        } else {
            z = false;
        }
        this.lighting = z;
        if (renderable.material.has(CubemapAttribute.EnvironmentMap) || (this.lighting && renderable.environment.has(CubemapAttribute.EnvironmentMap))) {
            z = true;
        } else {
            z = false;
        }
        this.environmentCubemap = z;
        if (!this.lighting || renderable.environment.shadowMap == null) {
            z = false;
        } else {
            z = true;
        }
        this.shadowMap = z;
        if (!(this.lighting && renderable.environment.has(ColorAttribute.Fog))) {
            z2 = false;
        }
        this.fog = z2;
        this.renderable = renderable;
        this.materialMask = renderable.material.getMask() | optionalAttributes;
        this.vertexMask = renderable.mesh.getVertexAttributes().getMask();
        if (!this.lighting || config.numDirectionalLights <= 0) {
            i = 0;
        } else {
            i = config.numDirectionalLights;
        }
        this.directionalLights = new DirectionalLight[i];
        for (i2 = 0; i2 < this.directionalLights.length; i2++) {
            this.directionalLights[i2] = new DirectionalLight();
        }
        if (this.lighting && config.numPointLights > 0) {
            i4 = config.numPointLights;
        }
        this.pointLights = new PointLight[i4];
        for (i2 = 0; i2 < this.pointLights.length; i2++) {
            this.pointLights[i2] = new PointLight();
        }
        if (ignoreUnimplemented || (implementedFlags & this.materialMask) == this.materialMask) {
            this.u_projTrans = register(Inputs.projTrans, Setters.projTrans);
            this.u_viewTrans = register(Inputs.viewTrans, Setters.viewTrans);
            this.u_projViewTrans = register(Inputs.projViewTrans, Setters.projViewTrans);
            this.u_cameraPosition = register(Inputs.cameraPosition, Setters.cameraPosition);
            this.u_cameraDirection = register(Inputs.cameraDirection, Setters.cameraDirection);
            this.u_cameraUp = register(Inputs.cameraUp, Setters.cameraUp);
            this.u_time = register(new Uniform("u_time"));
            this.u_worldTrans = register(Inputs.worldTrans, Setters.worldTrans);
            this.u_viewWorldTrans = register(Inputs.worldViewTrans, Setters.worldViewTrans);
            this.u_projViewWorldTrans = register(Inputs.projViewWorldTrans, Setters.projViewWorldTrans);
            this.u_normalMatrix = register(Inputs.normalMatrix, Setters.normalMatrix);
            i = (renderable.bones == null || config.numBones <= 0) ? -1 : register(Inputs.bones, new Bones(config.numBones));
            this.u_bones = i;
            this.u_shininess = register(Inputs.shininess, Setters.shininess);
            this.u_opacity = register(Inputs.opacity);
            this.u_diffuseColor = register(Inputs.diffuseColor, Setters.diffuseColor);
            this.u_diffuseTexture = register(Inputs.diffuseTexture, Setters.diffuseTexture);
            this.u_specularColor = register(Inputs.specularColor, Setters.specularColor);
            this.u_specularTexture = register(Inputs.specularTexture, Setters.specularTexture);
            this.u_emissiveColor = register(Inputs.emissiveColor, Setters.emissiveColor);
            this.u_reflectionColor = register(Inputs.reflectionColor, Setters.reflectionColor);
            this.u_normalTexture = register(Inputs.normalTexture, Setters.normalTexture);
            this.u_alphaTest = register(Inputs.alphaTest);
            this.u_ambientCubemap = this.lighting ? register(Inputs.ambientCube, new ACubemap(config.numDirectionalLights, config.numPointLights)) : -1;
            if (this.environmentCubemap) {
                i3 = register(Inputs.environmentCubemap, Setters.environmentCubemap);
            }
            this.u_environmentCubemap = i3;
            return;
        }
        throw new GdxRuntimeException("Some attributes not implemented yet (" + this.materialMask + ")");
    }

    public void init() {
        ShaderProgram program = this.program;
        this.program = null;
        init(program, this.renderable);
        this.renderable = null;
        this.dirLightsLoc = loc(this.u_dirLights0color);
        this.dirLightsColorOffset = loc(this.u_dirLights0color) - this.dirLightsLoc;
        this.dirLightsDirectionOffset = loc(this.u_dirLights0direction) - this.dirLightsLoc;
        this.dirLightsSize = loc(this.u_dirLights1color) - this.dirLightsLoc;
        if (this.dirLightsSize < 0) {
            this.dirLightsSize = 0;
        }
        this.pointLightsLoc = loc(this.u_pointLights0color);
        this.pointLightsColorOffset = loc(this.u_pointLights0color) - this.pointLightsLoc;
        this.pointLightsPositionOffset = loc(this.u_pointLights0position) - this.pointLightsLoc;
        this.pointLightsIntensityOffset = loc(this.u_pointLights0intensity) - this.pointLightsLoc;
        this.pointLightsSize = loc(this.u_pointLights1color) - this.pointLightsLoc;
        if (this.pointLightsSize < 0) {
            this.pointLightsSize = 0;
        }
    }

    private static final boolean and(long mask, long flag) {
        return (mask & flag) == flag;
    }

    private static final boolean or(long mask, long flag) {
        return (mask & flag) != 0;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        String prefix = "";
        long mask = renderable.material.getMask();
        long attributes = renderable.mesh.getVertexAttributes().getMask();
        if (and(attributes, 1)) {
            prefix = prefix + "#define positionFlag\n";
        }
        if (or(attributes, 6)) {
            prefix = prefix + "#define colorFlag\n";
        }
        if (and(attributes, 256)) {
            prefix = prefix + "#define binormalFlag\n";
        }
        if (and(attributes, 128)) {
            prefix = prefix + "#define tangentFlag\n";
        }
        if (and(attributes, 8)) {
            prefix = prefix + "#define normalFlag\n";
        }
        if ((and(attributes, 8) || and(attributes, 384)) && renderable.environment != null) {
            prefix = (((prefix + "#define lightingFlag\n") + "#define ambientCubemapFlag\n") + "#define numDirectionalLights " + config.numDirectionalLights + "\n") + "#define numPointLights " + config.numPointLights + "\n";
            if (renderable.environment.has(ColorAttribute.Fog)) {
                prefix = prefix + "#define fogFlag\n";
            }
            if (renderable.environment.shadowMap != null) {
                prefix = prefix + "#define shadowMapFlag\n";
            }
            if (renderable.material.has(CubemapAttribute.EnvironmentMap) || renderable.environment.has(CubemapAttribute.EnvironmentMap)) {
                prefix = prefix + "#define environmentCubemapFlag\n";
            }
        }
        int n = renderable.mesh.getVertexAttributes().size();
        for (int i = 0; i < n; i++) {
            VertexAttribute attr = renderable.mesh.getVertexAttributes().get(i);
            if (attr.usage == 64) {
                prefix = prefix + "#define boneWeight" + attr.unit + "Flag\n";
            } else if (attr.usage == 16) {
                prefix = prefix + "#define texCoord" + attr.unit + "Flag\n";
            }
        }
        if ((128 & attributes) == 128) {
            prefix = prefix + "#define tangentFlag\n";
        }
        if ((256 & attributes) == 256) {
            prefix = prefix + "#define binormalFlag\n";
        }
        if ((BlendingAttribute.Type & mask) == BlendingAttribute.Type) {
            prefix = prefix + "#define blendedFlag\n";
        }
        if ((TextureAttribute.Diffuse & mask) == TextureAttribute.Diffuse) {
            prefix = (prefix + "#define diffuseTextureFlag\n") + "#define diffuseTextureCoord texCoord0\n";
        }
        if ((TextureAttribute.Normal & mask) == TextureAttribute.Normal) {
            prefix = (prefix + "#define normalTextureFlag\n") + "#define normalTextureCoord texCoord0\n";
        }
        if ((ColorAttribute.Diffuse & mask) == ColorAttribute.Diffuse) {
            prefix = prefix + "#define diffuseColorFlag\n";
        }
        if ((ColorAttribute.Specular & mask) == ColorAttribute.Specular) {
            prefix = prefix + "#define specularColorFlag\n";
        }
        if ((ColorAttribute.Emissive & mask) == ColorAttribute.Emissive) {
            prefix = prefix + "#define emissiveColorFlag\n";
        }
        if ((ColorAttribute.Reflection & mask) == ColorAttribute.Reflection) {
            prefix = prefix + "#define reflectionColorFlag\n";
        }
        if ((FloatAttribute.Shininess & mask) == FloatAttribute.Shininess) {
            prefix = prefix + "#define shininessFlag\n";
        }
        if ((FloatAttribute.AlphaTest & mask) == FloatAttribute.AlphaTest) {
            prefix = prefix + "#define alphaTestFlag\n";
        }
        if (renderable.bones != null && config.numBones > 0) {
            prefix = prefix + "#define numBones " + config.numBones + "\n";
        }
        Gdx.app.log("Prefix", "\n" + prefix);
        return prefix;
    }

    public boolean canRender(Renderable renderable) {
        if (this.materialMask == (renderable.material.getMask() | optionalAttributes) && this.vertexMask == renderable.mesh.getVertexAttributes().getMask()) {
            if ((renderable.environment != null) == this.lighting) {
                boolean z = renderable.environment != null && renderable.environment.has(ColorAttribute.Fog);
                if (z == this.fog) {
                    return true;
                }
            }
        }
        return false;
    }

    public int compareTo(Shader other) {
        if (other == null) {
            return -1;
        }
        return other == this ? 0 : 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof DefaultShader ? equals((DefaultShader) obj) : false;
    }

    public boolean equals(DefaultShader obj) {
        return obj == this;
    }

    public void begin(Camera camera, RenderContext context) {
        super.begin(camera, context);
        for (DirectionalLight dirLight : this.directionalLights) {
            dirLight.set(0.0f, 0.0f, 0.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f);
        }
        for (PointLight pointLight : this.pointLights) {
            pointLight.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        this.lightsSet = false;
        if (has(this.u_time)) {
            int i = this.u_time;
            float deltaTime = this.time + Gdx.graphics.getDeltaTime();
            this.time = deltaTime;
            set(i, deltaTime);
        }
    }

    public void render(Renderable renderable) {
        if (!renderable.material.has(BlendingAttribute.Type)) {
            this.context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        bindMaterial(renderable);
        if (this.lighting) {
            bindLights(renderable);
        }
        super.render(renderable);
    }

    public void end() {
        this.currentMaterial = null;
        super.end();
    }

    protected final void bindMaterial(Renderable renderable) {
        if (this.currentMaterial != renderable.material) {
            int cullFace = defaultCullFace;
            int depthFunc = defaultDepthFunc;
            float depthRangeNear = 0.0f;
            float depthRangeFar = TextTrackStyle.DEFAULT_FONT_SCALE;
            boolean depthMask = true;
            this.currentMaterial = renderable.material;
            Iterator i$ = this.currentMaterial.iterator();
            while (i$.hasNext()) {
                Attribute attr = (Attribute) i$.next();
                long t = attr.type;
                if (BlendingAttribute.is(t)) {
                    this.context.setBlending(true, ((BlendingAttribute) attr).sourceFunction, ((BlendingAttribute) attr).destFunction);
                    set(this.u_opacity, ((BlendingAttribute) attr).opacity);
                } else if ((IntAttribute.CullFace & t) == IntAttribute.CullFace) {
                    cullFace = ((IntAttribute) attr).value;
                } else if ((FloatAttribute.AlphaTest & t) == FloatAttribute.AlphaTest) {
                    set(this.u_alphaTest, ((FloatAttribute) attr).value);
                } else if ((DepthTestAttribute.Type & t) == DepthTestAttribute.Type) {
                    DepthTestAttribute dta = (DepthTestAttribute) attr;
                    depthFunc = dta.depthFunc;
                    depthRangeNear = dta.depthRangeNear;
                    depthRangeFar = dta.depthRangeFar;
                    depthMask = dta.depthMask;
                } else if (!ignoreUnimplemented) {
                    throw new GdxRuntimeException("Unknown material attribute: " + attr.toString());
                }
            }
            this.context.setCullFace(cullFace);
            this.context.setDepthTest(depthFunc, depthRangeNear, depthRangeFar);
            this.context.setDepthMask(depthMask);
        }
    }

    protected final void bindLights(Renderable renderable) {
        int i;
        int idx;
        Environment lights = renderable.environment;
        Array<DirectionalLight> dirs = lights.directionalLights;
        Array<PointLight> points = lights.pointLights;
        if (this.dirLightsLoc >= 0) {
            i = 0;
            while (i < this.directionalLights.length) {
                if (dirs == null || i >= dirs.size) {
                    if (!this.lightsSet || this.directionalLights[i].color.f40r != 0.0f || this.directionalLights[i].color.f39g != 0.0f || this.directionalLights[i].color.f38b != 0.0f) {
                        this.directionalLights[i].color.set(0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
                        idx = this.dirLightsLoc + (this.dirLightsSize * i);
                        this.program.setUniformf(this.dirLightsColorOffset + idx, this.directionalLights[i].color.f40r, this.directionalLights[i].color.f39g, this.directionalLights[i].color.f38b);
                        this.program.setUniformf(this.dirLightsDirectionOffset + idx, this.directionalLights[i].direction);
                        if (this.dirLightsSize <= 0) {
                            break;
                        }
                    }
                } else if (!this.lightsSet || !this.directionalLights[i].equals((DirectionalLight) dirs.get(i))) {
                    this.directionalLights[i].set((DirectionalLight) dirs.get(i));
                    idx = this.dirLightsLoc + (this.dirLightsSize * i);
                    this.program.setUniformf(this.dirLightsColorOffset + idx, this.directionalLights[i].color.f40r, this.directionalLights[i].color.f39g, this.directionalLights[i].color.f38b);
                    this.program.setUniformf(this.dirLightsDirectionOffset + idx, this.directionalLights[i].direction);
                    if (this.dirLightsSize <= 0) {
                        break;
                    }
                }
                i++;
            }
        }
        if (this.pointLightsLoc >= 0) {
            i = 0;
            while (i < this.pointLights.length) {
                if (points == null || i >= points.size) {
                    if (!this.lightsSet || this.pointLights[i].intensity != 0.0f) {
                        this.pointLights[i].intensity = 0.0f;
                        idx = this.pointLightsLoc + (this.pointLightsSize * i);
                        this.program.setUniformf(this.pointLightsColorOffset + idx, this.pointLights[i].color.f40r, this.pointLights[i].color.f39g, this.pointLights[i].color.f38b);
                        this.program.setUniformf(this.pointLightsPositionOffset + idx, this.pointLights[i].position);
                        if (this.pointLightsIntensityOffset >= 0) {
                            this.program.setUniformf(this.pointLightsIntensityOffset + idx, this.pointLights[i].intensity);
                        }
                        if (this.pointLightsSize <= 0) {
                            break;
                        }
                    }
                } else if (!this.lightsSet || !this.pointLights[i].equals((PointLight) points.get(i))) {
                    this.pointLights[i].set((PointLight) points.get(i));
                    idx = this.pointLightsLoc + (this.pointLightsSize * i);
                    this.program.setUniformf(this.pointLightsColorOffset + idx, this.pointLights[i].color.f40r, this.pointLights[i].color.f39g, this.pointLights[i].color.f38b);
                    this.program.setUniformf(this.pointLightsPositionOffset + idx, this.pointLights[i].position);
                    if (this.pointLightsIntensityOffset >= 0) {
                        this.program.setUniformf(this.pointLightsIntensityOffset + idx, this.pointLights[i].intensity);
                    }
                    if (this.pointLightsSize <= 0) {
                        break;
                    }
                }
                i++;
            }
        }
        if (lights.has(ColorAttribute.Fog)) {
            set(this.u_fogColor, ((ColorAttribute) lights.get(ColorAttribute.Fog)).color);
        }
        if (lights.shadowMap != null) {
            set(this.u_shadowMapProjViewTrans, lights.shadowMap.getProjViewTrans());
            set(this.u_shadowTexture, lights.shadowMap.getDepthMap());
            set(this.u_shadowPCFOffset, TextTrackStyle.DEFAULT_FONT_SCALE / (2.0f * ((float) lights.shadowMap.getDepthMap().texture.getWidth())));
        }
        this.lightsSet = true;
    }

    public void dispose() {
        this.program.dispose();
        super.dispose();
    }
}
