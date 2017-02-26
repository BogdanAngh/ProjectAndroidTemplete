package com.frodidev.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.frodidev.FloppyBird.ActionResolver;
import com.frodidev.FloppyBird.ZBGame;
import com.frodidev.GameWorld.GameRenderer;
import com.frodidev.GameWorld.GameWorld;
import com.frodidev.ZBHelpers.InputHandler;

public class GameScreen implements Screen {
    private GameRenderer renderer;
    private float runTime;
    private GameWorld world;

    public GameScreen(ActionResolver actionResolver, ZBGame game) {
        float screenWidth = (float) Gdx.graphics.getWidth();
        float screenHeight = (float) Gdx.graphics.getHeight();
        float gameHeight = screenHeight / (screenWidth / 136.0f);
        int midPointY = (int) (gameHeight / 2.0f);
        this.world = new GameWorld(midPointY, actionResolver);
        Gdx.input.setInputProcessor(new InputHandler(this.world, screenWidth / 136.0f, screenHeight / gameHeight));
        this.renderer = new GameRenderer(game, this.world, (int) gameHeight, midPointY);
        this.world.setRenderer(this.renderer);
    }

    public void render(float delta) {
        this.runTime += delta;
        this.world.update(delta);
        this.renderer.render(delta, this.runTime);
    }

    public void resize(int width, int height) {
    }

    public void show() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}
