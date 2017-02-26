package com.frodidev.GameWorld;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.net.HttpStatus;
import com.frodidev.FloppyBird.ZBGame;
import com.frodidev.GameObjects.Bird;
import com.frodidev.GameObjects.Grass;
import com.frodidev.GameObjects.Pipe;
import com.frodidev.GameObjects.ScrollHandler;
import com.frodidev.TweenAccessors.Value;
import com.frodidev.TweenAccessors.ValueAccessor;
import com.frodidev.ZBHelpers.AssetLoader;
import com.frodidev.ZBHelpers.InputHandler;
import com.frodidev.ui.SimpleButton;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;

public class GameRenderer {
    private Value alpha;
    private Grass backGrass;
    private TextureRegion bar;
    private SpriteBatch batcher;
    private TextureRegion bg;
    private Bird bird;
    private Animation birdAnimation;
    private TextureRegion birdMid;
    private OrthographicCamera cam;
    private Grass frontGrass;
    private ZBGame game;
    private TextureRegion gameOver;
    private TextureRegion grass;
    private TextureRegion highScore;
    private TweenManager manager;
    private List<SimpleButton> menuButtons;
    private int midPointY;
    private GameWorld myWorld;
    private TextureRegion noStar;
    private Pipe pipe1;
    private Pipe pipe2;
    private Pipe pipe3;
    private TextureRegion ready;
    private TextureRegion retry;
    private TextureRegion scoreboard;
    private ScrollHandler scroller;
    private ShapeRenderer shapeRenderer;
    private TextureRegion skullDown;
    private TextureRegion skullUp;
    private TextureRegion star;
    private Color transitionColor;
    private TextureRegion zbLogo;

    public GameRenderer(ZBGame game, GameWorld world, int gameHeight, int midPointY) {
        this.alpha = new Value();
        this.myWorld = world;
        this.game = game;
        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(true, 136.0f, (float) gameHeight);
        this.batcher = new SpriteBatch();
        this.batcher.setProjectionMatrix(this.cam.combined);
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(this.cam.combined);
        initGameObjects();
        initAssets();
        this.transitionColor = new Color();
        prepareTransition(Keys.F12, Keys.F12, Keys.F12, 0.5f);
    }

    private void initGameObjects() {
        this.bird = this.myWorld.getBird();
        this.scroller = this.myWorld.getScroller();
        this.frontGrass = this.scroller.getFrontGrass();
        this.backGrass = this.scroller.getBackGrass();
        this.pipe1 = this.scroller.getPipe1();
        this.pipe2 = this.scroller.getPipe2();
        this.pipe3 = this.scroller.getPipe3();
    }

    private void initAssets() {
        this.bg = AssetLoader.bg;
        this.grass = AssetLoader.grass;
        this.birdAnimation = AssetLoader.birdAnimation;
        this.birdMid = AssetLoader.bird;
        this.skullUp = AssetLoader.skullUp;
        this.skullDown = AssetLoader.skullDown;
        this.bar = AssetLoader.bar;
        this.ready = AssetLoader.ready;
        this.zbLogo = AssetLoader.zbLogo;
        this.gameOver = AssetLoader.gameOver;
        this.highScore = AssetLoader.highScore;
        this.scoreboard = AssetLoader.scoreboard;
        this.retry = AssetLoader.retry;
        this.star = AssetLoader.star;
        this.noStar = AssetLoader.noStar;
    }

    private void drawGrass() {
        this.batcher.draw(this.grass, this.frontGrass.getX(), this.frontGrass.getY(), (float) this.frontGrass.getWidth(), (float) this.frontGrass.getHeight());
        this.batcher.draw(this.grass, this.backGrass.getX(), this.backGrass.getY(), (float) this.backGrass.getWidth(), (float) this.backGrass.getHeight());
    }

    private void drawSkulls() {
        this.batcher.draw(this.skullUp, this.pipe1.getX() - TextTrackStyle.DEFAULT_FONT_SCALE, (this.pipe1.getY() + ((float) this.pipe1.getHeight())) - 14.0f, 24.0f, 14.0f);
        this.batcher.draw(this.skullDown, this.pipe1.getX() - TextTrackStyle.DEFAULT_FONT_SCALE, (this.pipe1.getY() + ((float) this.pipe1.getHeight())) + 45.0f, 24.0f, 14.0f);
        this.batcher.draw(this.skullUp, this.pipe2.getX() - TextTrackStyle.DEFAULT_FONT_SCALE, (this.pipe2.getY() + ((float) this.pipe2.getHeight())) - 14.0f, 24.0f, 14.0f);
        this.batcher.draw(this.skullDown, this.pipe2.getX() - TextTrackStyle.DEFAULT_FONT_SCALE, (this.pipe2.getY() + ((float) this.pipe2.getHeight())) + 45.0f, 24.0f, 14.0f);
        this.batcher.draw(this.skullUp, this.pipe3.getX() - TextTrackStyle.DEFAULT_FONT_SCALE, (this.pipe3.getY() + ((float) this.pipe3.getHeight())) - 14.0f, 24.0f, 14.0f);
        this.batcher.draw(this.skullDown, this.pipe3.getX() - TextTrackStyle.DEFAULT_FONT_SCALE, (this.pipe3.getY() + ((float) this.pipe3.getHeight())) + 45.0f, 24.0f, 14.0f);
    }

    private void drawPipes() {
        this.batcher.draw(this.bar, this.pipe1.getX(), this.pipe1.getY(), (float) this.pipe1.getWidth(), (float) this.pipe1.getHeight());
        this.batcher.draw(this.bar, this.pipe1.getX(), (this.pipe1.getY() + ((float) this.pipe1.getHeight())) + 45.0f, (float) this.pipe1.getWidth(), (float) ((this.midPointY + 66) - (this.pipe1.getHeight() + 45)));
        this.batcher.draw(this.bar, this.pipe2.getX(), this.pipe2.getY(), (float) this.pipe2.getWidth(), (float) this.pipe2.getHeight());
        this.batcher.draw(this.bar, this.pipe2.getX(), (this.pipe2.getY() + ((float) this.pipe2.getHeight())) + 45.0f, (float) this.pipe2.getWidth(), (float) ((this.midPointY + 66) - (this.pipe2.getHeight() + 45)));
        this.batcher.draw(this.bar, this.pipe3.getX(), this.pipe3.getY(), (float) this.pipe3.getWidth(), (float) this.pipe3.getHeight());
        this.batcher.draw(this.bar, this.pipe3.getX(), (this.pipe3.getY() + ((float) this.pipe3.getHeight())) + 45.0f, (float) this.pipe3.getWidth(), (float) ((this.midPointY + 66) - (this.pipe3.getHeight() + 45)));
    }

    private void drawBirdCentered(float runTime) {
        this.batcher.draw(this.birdAnimation.getKeyFrame(runTime), 59.0f, this.bird.getY() - 15.0f, this.bird.getWidth() / 2.0f, this.bird.getHeight() / 2.0f, this.bird.getWidth(), this.bird.getHeight(), TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, this.bird.getRotation());
    }

    private void drawBird(float runTime) {
        if (this.bird.shouldntFlap()) {
            this.batcher.draw(this.birdMid, this.bird.getX(), this.bird.getY(), this.bird.getWidth() / 2.0f, this.bird.getHeight() / 2.0f, this.bird.getWidth(), this.bird.getHeight(), TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, this.bird.getRotation());
            return;
        }
        this.batcher.draw(this.birdAnimation.getKeyFrame(runTime), this.bird.getX(), this.bird.getY(), this.bird.getWidth() / 2.0f, this.bird.getHeight() / 2.0f, this.bird.getWidth(), this.bird.getHeight(), TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, this.bird.getRotation());
    }

    private void drawMenuUI() {
        this.batcher.draw(this.zbLogo, 12.0f, (float) (this.midPointY - 50), ((float) this.zbLogo.getRegionWidth()) / 1.2f, ((float) this.zbLogo.getRegionHeight()) / 1.2f);
        for (SimpleButton button : this.menuButtons) {
            button.draw(this.batcher);
            for (SimpleButton button1 : this.menuButtons) {
                button1.draw(this.batcher);
            }
        }
    }

    private void drawScoreboard() {
        this.batcher.draw(this.scoreboard, 22.0f, (float) (this.midPointY - 30), 97.0f, 37.0f);
        this.batcher.draw(this.noStar, 25.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        this.batcher.draw(this.noStar, 37.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        this.batcher.draw(this.noStar, 49.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        this.batcher.draw(this.noStar, 61.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        this.batcher.draw(this.noStar, 73.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        if (this.myWorld.getScore() > 2) {
            this.batcher.draw(this.star, 73.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        }
        if (this.myWorld.getScore() > 17) {
            this.batcher.draw(this.star, 61.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        }
        if (this.myWorld.getScore() > 50) {
            this.batcher.draw(this.star, 49.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        }
        if (this.myWorld.getScore() > 80) {
            this.batcher.draw(this.star, 37.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        }
        if (this.myWorld.getScore() > 120) {
            this.batcher.draw(this.star, 25.0f, (float) (this.midPointY - 15), 10.0f, 10.0f);
        }
        AssetLoader.whiteFont.draw(this.batcher, this.myWorld.getScore(), (float) (104 - ((this.myWorld.getScore()).length() * 2)), (float) (this.midPointY - 20));
        AssetLoader.whiteFont.draw(this.batcher, AssetLoader.getHighScore(), 104.0f - (2.5f * ((float) (AssetLoader.getHighScore()).length())), (float) (this.midPointY - 3));
    }

    private void drawAchievements() {
        if (this.game.actionResolver.isSignedIn()) {
            this.game.actionResolver.submitScore((long) this.myWorld.getScore());
            if (this.myWorld.getScore() >= 1) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQBw");
            }
            if (this.myWorld.getScore() >= 10) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQAg");
            }
            if (this.myWorld.getScore() >= 20) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQAw");
            }
            if (this.myWorld.getScore() >= 30) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQBA");
            }
            if (this.myWorld.getScore() >= 40) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQBQ");
            }
            if (this.myWorld.getScore() >= 50) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQBg");
            }
            if (this.myWorld.getScore() >= 60) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQCA");
            }
            if (this.myWorld.getScore() >= 70) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQCQ");
            }
            if (this.myWorld.getScore() >= 80) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQCg");
            }
            if (this.myWorld.getScore() >= 90) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQCw");
            }
            if (this.myWorld.getScore() >= 100) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQDA");
            }
            if (this.myWorld.getScore() >= 120) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQDQ");
            }
            if (this.myWorld.getScore() >= Keys.NUMPAD_6) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQDg");
            }
            if (this.myWorld.getScore() >= HttpStatus.SC_OK) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQDw");
            }
            if (this.myWorld.getScore() >= HttpStatus.SC_MULTIPLE_CHOICES) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQEA");
            }
            if (this.myWorld.getScore() >= HttpStatus.SC_BAD_REQUEST) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQEQ");
            }
            if (this.myWorld.getScore() >= HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQEg");
            }
            if (this.myWorld.getScore() >= 600) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQEw");
            }
            if (this.myWorld.getScore() >= 700) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQFA");
            }
            if (this.myWorld.getScore() >= 800) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQFQ");
            }
            if (this.myWorld.getScore() >= 900) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQFg");
            }
            if (this.myWorld.getScore() >= GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) {
                this.game.actionResolver.unlockAchievementGPGS("CgkIrpvQ0fQMEAIQFw");
            }
        }
    }

    private void drawRetry() {
        this.batcher.draw(this.retry, 36.0f, (float) (this.midPointY + 10), 66.0f, 14.0f);
    }

    private void drawReady() {
        this.batcher.draw(this.ready, 36.0f, (float) (this.midPointY - 50), 68.0f, 14.0f);
    }

    private void drawGameOver() {
        this.batcher.draw(this.gameOver, 24.0f, (float) (this.midPointY - 50), 92.0f, 14.0f);
    }

    private void drawScore() {
        int length = (this.myWorld.getScore()).length();
        AssetLoader.shadow.draw(this.batcher, this.myWorld.getScore(), (float) (68 - (length * 3)), (float) (this.midPointY - 82));
        AssetLoader.font.draw(this.batcher, this.myWorld.getScore(), (float) (68 - (length * 3)), (float) (this.midPointY - 83));
    }

    private void drawHighScore() {
        this.batcher.draw(this.highScore, 22.0f, (float) (this.midPointY - 50), 96.0f, 14.0f);
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.shapeRenderer.begin(ShapeType.Filled);
        this.shapeRenderer.setColor(0.30980393f, 0.75686276f, 0.7882353f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.shapeRenderer.rect(0.0f, 0.0f, 136.0f, (float) (this.midPointY + 66));
        this.shapeRenderer.setColor(0.43529412f, 0.7294118f, 0.1764706f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.shapeRenderer.rect(0.0f, (float) (this.midPointY + 66), 136.0f, 11.0f);
        this.shapeRenderer.setColor(0.8117647f, 0.5921569f, 0.30588236f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.shapeRenderer.rect(0.0f, (float) (this.midPointY + 77), 136.0f, 52.0f);
        this.shapeRenderer.end();
        this.batcher.begin();
        this.batcher.disableBlending();
        this.batcher.draw(this.bg, 0.0f, (float) (this.midPointY + 23), 136.0f, 43.0f);
        drawPipes();
        this.batcher.enableBlending();
        drawSkulls();
        if (this.myWorld.isRunning()) {
            drawBird(runTime);
            drawScore();
        } else if (this.myWorld.isReady()) {
            drawBird(runTime);
            drawReady();
        } else if (this.myWorld.isMenu()) {
            drawBirdCentered(runTime);
            drawMenuUI();
        } else if (this.myWorld.isGameOver()) {
            drawScoreboard();
            drawBird(runTime);
            drawGameOver();
            drawRetry();
            drawAchievements();
        } else if (this.myWorld.isHighScore()) {
            drawScoreboard();
            drawBird(runTime);
            drawHighScore();
            drawRetry();
            drawAchievements();
        }
        drawGrass();
        this.batcher.end();
        drawTransition(delta);
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        this.transitionColor.set(((float) r) / 255.0f, ((float) g) / 255.0f, ((float) b) / 255.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.alpha.setValue(TextTrackStyle.DEFAULT_FONT_SCALE);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        this.manager = new TweenManager();
        Tween.to(this.alpha, -1, duration).target(0.0f).ease(TweenEquations.easeOutQuad).start(this.manager);
    }

    private void drawTransition(float delta) {
        if (this.alpha.getValue() > 0.0f) {
            this.manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            this.shapeRenderer.begin(ShapeType.Filled);
            this.shapeRenderer.setColor(this.transitionColor.f40r, this.transitionColor.f39g, this.transitionColor.f38b, this.alpha.getValue());
            this.shapeRenderer.rect(0.0f, 0.0f, 136.0f, BitmapDescriptorFactory.HUE_MAGENTA);
            this.shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
}
