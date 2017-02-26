package com.badlogic.gdx.assets.loaders.resolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class ResolutionFileResolver implements FileHandleResolver {
    protected final FileHandleResolver baseResolver;
    protected final Resolution[] descriptors;

    public static class Resolution {
        public final int portraitHeight;
        public final int portraitWidth;
        public final String suffix;

        public Resolution(int portraitWidth, int portraitHeight, String suffix) {
            this.portraitWidth = portraitWidth;
            this.portraitHeight = portraitHeight;
            this.suffix = suffix;
        }
    }

    public ResolutionFileResolver(FileHandleResolver baseResolver, Resolution... descriptors) {
        this.baseResolver = baseResolver;
        this.descriptors = descriptors;
    }

    public FileHandle resolve(String fileName) {
        Resolution bestDesc = choose(this.descriptors);
        FileHandle handle = this.baseResolver.resolve(resolve(new FileHandle(fileName), bestDesc.suffix));
        if (handle.exists()) {
            return handle;
        }
        return this.baseResolver.resolve(fileName);
    }

    protected String resolve(FileHandle originalHandle, String suffix) {
        return originalHandle.parent() + "/" + suffix + "/" + originalHandle.name();
    }

    public static Resolution choose(Resolution... descriptors) {
        if (descriptors == null) {
            throw new IllegalArgumentException("descriptors cannot be null.");
        }
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        Resolution best = descriptors[0];
        int n;
        int i;
        Resolution other;
        if (w < h) {
            n = descriptors.length;
            for (i = 0; i < n; i++) {
                other = descriptors[i];
                if (w >= other.portraitWidth && other.portraitWidth >= best.portraitWidth && h >= other.portraitHeight && other.portraitHeight >= best.portraitHeight) {
                    best = descriptors[i];
                }
            }
        } else {
            n = descriptors.length;
            for (i = 0; i < n; i++) {
                other = descriptors[i];
                if (w >= other.portraitHeight && other.portraitHeight >= best.portraitHeight && h >= other.portraitWidth && other.portraitWidth >= best.portraitWidth) {
                    best = descriptors[i];
                }
            }
        }
        return best;
    }
}
