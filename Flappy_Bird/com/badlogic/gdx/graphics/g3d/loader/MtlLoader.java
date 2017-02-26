package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/* compiled from: ObjLoader */
class MtlLoader {
    public Array<ModelMaterial> materials;

    MtlLoader() {
        this.materials = new Array();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void load(com.badlogic.gdx.files.FileHandle r24) {
        /*
        r23 = this;
        r4 = "default";
        r5 = com.badlogic.gdx.graphics.Color.WHITE;
        r16 = com.badlogic.gdx.graphics.Color.WHITE;
        r12 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r15 = 0;
        r19 = 0;
        if (r24 == 0) goto L_0x0013;
    L_0x000d:
        r21 = r24.exists();
        if (r21 != 0) goto L_0x0014;
    L_0x0013:
        return;
    L_0x0014:
        r14 = new java.io.BufferedReader;
        r21 = new java.io.InputStreamReader;
        r22 = r24.read();
        r21.<init>(r22);
        r22 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r0 = r21;
        r1 = r22;
        r14.<init>(r0, r1);
        r17 = r16;
        r6 = r5;
    L_0x002b:
        r10 = r14.readLine();	 Catch:{ IOException -> 0x024e }
        if (r10 == 0) goto L_0x01de;
    L_0x0031:
        r21 = r10.length();	 Catch:{ IOException -> 0x024e }
        if (r21 <= 0) goto L_0x0053;
    L_0x0037:
        r21 = 0;
        r0 = r21;
        r21 = r10.charAt(r0);	 Catch:{ IOException -> 0x024e }
        r22 = 9;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x0053;
    L_0x0047:
        r21 = 1;
        r0 = r21;
        r21 = r10.substring(r0);	 Catch:{ IOException -> 0x024e }
        r10 = r21.trim();	 Catch:{ IOException -> 0x024e }
    L_0x0053:
        r21 = "\\s+";
        r0 = r21;
        r20 = r10.split(r0);	 Catch:{ IOException -> 0x024e }
        r21 = 0;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r21 = r21.length();	 Catch:{ IOException -> 0x024e }
        if (r21 == 0) goto L_0x002b;
    L_0x0065:
        r21 = 0;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r22 = 0;
        r21 = r21.charAt(r22);	 Catch:{ IOException -> 0x024e }
        r22 = 35;
        r0 = r21;
        r1 = r22;
        if (r0 == r1) goto L_0x002b;
    L_0x0077:
        r21 = 0;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r9 = r21.toLowerCase();	 Catch:{ IOException -> 0x024e }
        r21 = "newmtl";
        r0 = r21;
        r21 = r9.equals(r0);	 Catch:{ IOException -> 0x024e }
        if (r21 == 0) goto L_0x011d;
    L_0x0089:
        r11 = new com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;	 Catch:{ IOException -> 0x024e }
        r11.<init>();	 Catch:{ IOException -> 0x024e }
        r11.id = r4;	 Catch:{ IOException -> 0x024e }
        r21 = new com.badlogic.gdx.graphics.Color;	 Catch:{ IOException -> 0x024e }
        r0 = r21;
        r0.<init>(r6);	 Catch:{ IOException -> 0x024e }
        r0 = r21;
        r11.diffuse = r0;	 Catch:{ IOException -> 0x024e }
        r21 = new com.badlogic.gdx.graphics.Color;	 Catch:{ IOException -> 0x024e }
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);	 Catch:{ IOException -> 0x024e }
        r0 = r21;
        r11.specular = r0;	 Catch:{ IOException -> 0x024e }
        r11.opacity = r12;	 Catch:{ IOException -> 0x024e }
        r11.shininess = r15;	 Catch:{ IOException -> 0x024e }
        if (r19 == 0) goto L_0x00e6;
    L_0x00ae:
        r18 = new com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;	 Catch:{ IOException -> 0x024e }
        r18.<init>();	 Catch:{ IOException -> 0x024e }
        r21 = 2;
        r0 = r21;
        r1 = r18;
        r1.usage = r0;	 Catch:{ IOException -> 0x024e }
        r21 = new java.lang.String;	 Catch:{ IOException -> 0x024e }
        r0 = r21;
        r1 = r19;
        r0.<init>(r1);	 Catch:{ IOException -> 0x024e }
        r0 = r21;
        r1 = r18;
        r1.fileName = r0;	 Catch:{ IOException -> 0x024e }
        r0 = r11.textures;	 Catch:{ IOException -> 0x024e }
        r21 = r0;
        if (r21 != 0) goto L_0x00db;
    L_0x00d0:
        r21 = new com.badlogic.gdx.utils.Array;	 Catch:{ IOException -> 0x024e }
        r22 = 1;
        r21.<init>(r22);	 Catch:{ IOException -> 0x024e }
        r0 = r21;
        r11.textures = r0;	 Catch:{ IOException -> 0x024e }
    L_0x00db:
        r0 = r11.textures;	 Catch:{ IOException -> 0x024e }
        r21 = r0;
        r0 = r21;
        r1 = r18;
        r0.add(r1);	 Catch:{ IOException -> 0x024e }
    L_0x00e6:
        r0 = r23;
        r0 = r0.materials;	 Catch:{ IOException -> 0x024e }
        r21 = r0;
        r0 = r21;
        r0.add(r11);	 Catch:{ IOException -> 0x024e }
        r0 = r20;
        r0 = r0.length;	 Catch:{ IOException -> 0x024e }
        r21 = r0;
        r22 = 1;
        r0 = r21;
        r1 = r22;
        if (r0 <= r1) goto L_0x011a;
    L_0x00fe:
        r21 = 1;
        r4 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r21 = 46;
        r22 = 95;
        r0 = r21;
        r1 = r22;
        r4 = r4.replace(r0, r1);	 Catch:{ IOException -> 0x024e }
    L_0x010e:
        r5 = com.badlogic.gdx.graphics.Color.WHITE;	 Catch:{ IOException -> 0x024e }
        r16 = com.badlogic.gdx.graphics.Color.WHITE;	 Catch:{ IOException -> 0x0254 }
        r12 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r15 = 0;
    L_0x0115:
        r17 = r16;
        r6 = r5;
        goto L_0x002b;
    L_0x011a:
        r4 = "default";
        goto L_0x010e;
    L_0x011d:
        r21 = "kd";
        r0 = r21;
        r21 = r9.equals(r0);	 Catch:{ IOException -> 0x024e }
        if (r21 != 0) goto L_0x0131;
    L_0x0127:
        r21 = "ks";
        r0 = r21;
        r21 = r9.equals(r0);	 Catch:{ IOException -> 0x024e }
        if (r21 == 0) goto L_0x0187;
    L_0x0131:
        r21 = 1;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r13 = java.lang.Float.parseFloat(r21);	 Catch:{ IOException -> 0x024e }
        r21 = 2;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r8 = java.lang.Float.parseFloat(r21);	 Catch:{ IOException -> 0x024e }
        r21 = 3;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r3 = java.lang.Float.parseFloat(r21);	 Catch:{ IOException -> 0x024e }
        r2 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r20;
        r0 = r0.length;	 Catch:{ IOException -> 0x024e }
        r21 = r0;
        r22 = 4;
        r0 = r21;
        r1 = r22;
        if (r0 <= r1) goto L_0x0160;
    L_0x0158:
        r21 = 4;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r2 = java.lang.Float.parseFloat(r21);	 Catch:{ IOException -> 0x024e }
    L_0x0160:
        r21 = 0;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r21 = r21.toLowerCase();	 Catch:{ IOException -> 0x024e }
        r22 = "kd";
        r21 = r21.equals(r22);	 Catch:{ IOException -> 0x024e }
        if (r21 == 0) goto L_0x017b;
    L_0x0170:
        r5 = new com.badlogic.gdx.graphics.Color;	 Catch:{ IOException -> 0x024e }
        r5.<init>();	 Catch:{ IOException -> 0x024e }
        r5.set(r13, r8, r3, r2);	 Catch:{ IOException -> 0x0254 }
        r16 = r17;
        goto L_0x0115;
    L_0x017b:
        r16 = new com.badlogic.gdx.graphics.Color;	 Catch:{ IOException -> 0x024e }
        r16.<init>();	 Catch:{ IOException -> 0x024e }
        r0 = r16;
        r0.set(r13, r8, r3, r2);	 Catch:{ IOException -> 0x0258 }
        r5 = r6;
        goto L_0x0115;
    L_0x0187:
        r21 = "tr";
        r0 = r21;
        r21 = r9.equals(r0);	 Catch:{ IOException -> 0x024e }
        if (r21 != 0) goto L_0x019b;
    L_0x0191:
        r21 = "d";
        r0 = r21;
        r21 = r9.equals(r0);	 Catch:{ IOException -> 0x024e }
        if (r21 == 0) goto L_0x01a8;
    L_0x019b:
        r21 = 1;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r12 = java.lang.Float.parseFloat(r21);	 Catch:{ IOException -> 0x024e }
        r16 = r17;
        r5 = r6;
        goto L_0x0115;
    L_0x01a8:
        r21 = "ns";
        r0 = r21;
        r21 = r9.equals(r0);	 Catch:{ IOException -> 0x024e }
        if (r21 == 0) goto L_0x01bf;
    L_0x01b2:
        r21 = 1;
        r21 = r20[r21];	 Catch:{ IOException -> 0x024e }
        r15 = java.lang.Float.parseFloat(r21);	 Catch:{ IOException -> 0x024e }
        r16 = r17;
        r5 = r6;
        goto L_0x0115;
    L_0x01bf:
        r21 = "map_kd";
        r0 = r21;
        r21 = r9.equals(r0);	 Catch:{ IOException -> 0x024e }
        if (r21 == 0) goto L_0x025b;
    L_0x01c9:
        r21 = r24.parent();	 Catch:{ IOException -> 0x024e }
        r22 = 1;
        r22 = r20[r22];	 Catch:{ IOException -> 0x024e }
        r21 = r21.child(r22);	 Catch:{ IOException -> 0x024e }
        r19 = r21.path();	 Catch:{ IOException -> 0x024e }
        r16 = r17;
        r5 = r6;
        goto L_0x0115;
    L_0x01de:
        r14.close();	 Catch:{ IOException -> 0x024e }
        r11 = new com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
        r11.<init>();
        r11.id = r4;
        r21 = new com.badlogic.gdx.graphics.Color;
        r0 = r21;
        r0.<init>(r6);
        r0 = r21;
        r11.diffuse = r0;
        r21 = new com.badlogic.gdx.graphics.Color;
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r11.specular = r0;
        r11.opacity = r12;
        r11.shininess = r15;
        if (r19 == 0) goto L_0x023e;
    L_0x0206:
        r18 = new com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
        r18.<init>();
        r21 = 2;
        r0 = r21;
        r1 = r18;
        r1.usage = r0;
        r21 = new java.lang.String;
        r0 = r21;
        r1 = r19;
        r0.<init>(r1);
        r0 = r21;
        r1 = r18;
        r1.fileName = r0;
        r0 = r11.textures;
        r21 = r0;
        if (r21 != 0) goto L_0x0233;
    L_0x0228:
        r21 = new com.badlogic.gdx.utils.Array;
        r22 = 1;
        r21.<init>(r22);
        r0 = r21;
        r11.textures = r0;
    L_0x0233:
        r0 = r11.textures;
        r21 = r0;
        r0 = r21;
        r1 = r18;
        r0.add(r1);
    L_0x023e:
        r0 = r23;
        r0 = r0.materials;
        r21 = r0;
        r0 = r21;
        r0.add(r11);
        r16 = r17;
        r5 = r6;
        goto L_0x0013;
    L_0x024e:
        r7 = move-exception;
        r16 = r17;
        r5 = r6;
    L_0x0252:
        goto L_0x0013;
    L_0x0254:
        r7 = move-exception;
        r16 = r17;
        goto L_0x0252;
    L_0x0258:
        r7 = move-exception;
        r5 = r6;
        goto L_0x0252;
    L_0x025b:
        r16 = r17;
        r5 = r6;
        goto L_0x0115;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.graphics.g3d.loader.MtlLoader.load(com.badlogic.gdx.files.FileHandle):void");
    }

    public ModelMaterial getMaterial(String name) {
        Iterator i$ = this.materials.iterator();
        while (i$.hasNext()) {
            ModelMaterial m = (ModelMaterial) i$.next();
            if (m.id.equals(name)) {
                return m;
            }
        }
        ModelMaterial mat = new ModelMaterial();
        mat.id = name;
        mat.diffuse = new Color(Color.WHITE);
        this.materials.add(mat);
        return mat;
    }
}
