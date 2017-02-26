package com.frodidev.Screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frodidev.FloppyBird.ActionResolver;
import com.frodidev.FloppyBird.ZBGame;
import com.frodidev.TweenAccessors.SpriteAccessor;
import com.frodidev.ZBHelpers.AssetLoader;
import com.google.android.gms.cast.TextTrackStyle;

public class SplashScreen implements Screen {
    private ActionResolver actionResolver;
    private SpriteBatch batcher;
    private ZBGame game;
    private TweenManager manager;
    private Sprite sprite;

    /* renamed from: com.frodidev.Screens.SplashScreen.1 */
    class C03841 implements TweenCallback {
        C03841() {
        }

        public void onEvent(int type, BaseTween<?> baseTween) {
            SplashScreen.this.game.setScreen(new GameScreen(SplashScreen.this.actionResolver, SplashScreen.this.game));
        }
    }

    public SplashScreen(ZBGame game, ActionResolver actionresolver) {
        this.game = game;
        this.actionResolver = actionresolver;
    }

    public void show() {
        this.sprite = new Sprite(AssetLoader.logo);
        this.sprite.setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        float width = (float) Gdx.graphics.getWidth();
        float height = (float) Gdx.graphics.getHeight();
        float scale = (width * 0.7f) / this.sprite.getWidth();
        this.sprite.setSize(this.sprite.getWidth() * scale, this.sprite.getHeight() * scale);
        this.sprite.setPosition((width / 2.0f) - (this.sprite.getWidth() / 2.0f), (height / 2.0f) - (this.sprite.getHeight() / 2.0f));
        setupTween();
        this.batcher = new SpriteBatch();
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        this.manager = new TweenManager();
        ((Tween) ((Tween) ((Tween) Tween.to(this.sprite, 1, 0.8f).target((float) TextTrackStyle.DEFAULT_FONT_SCALE).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4f)).setCallback(new C03841())).setCallbackTriggers(8)).start(this.manager);
    }

    public void render(float delta) {
        this.manager.update(delta);
        Gdx.gl.glClearColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batcher.begin();
        this.sprite.draw(this.batcher);
        this.batcher.end();
    }

    public void resize(int width, int height) {
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
