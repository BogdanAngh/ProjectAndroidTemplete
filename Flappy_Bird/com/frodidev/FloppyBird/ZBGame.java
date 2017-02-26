package com.frodidev.FloppyBird;

import com.badlogic.gdx.Game;
import com.frodidev.Screens.SplashScreen;
import com.frodidev.ZBHelpers.AssetLoader;

public class ZBGame extends Game {
    public ActionResolver actionResolver;

    public ZBGame(ActionResolver actionresolver) {
        this.actionResolver = actionresolver;
    }

    public void loadInterAds() {
        this.actionResolver.loadInterAds();
    }

    public void showInterAds() {
        this.actionResolver.showInterAds();
    }

    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this, this.actionResolver));
    }

    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
