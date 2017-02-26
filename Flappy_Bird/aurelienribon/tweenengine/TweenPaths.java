package aurelienribon.tweenengine;

import aurelienribon.tweenengine.paths.CatmullRom;
import aurelienribon.tweenengine.paths.Linear;

public interface TweenPaths {
    public static final CatmullRom catmullRom;
    public static final Linear linear;

    static {
        linear = new Linear();
        catmullRom = new CatmullRom();
    }
}
