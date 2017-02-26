package com.frodidev.ZBHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.games.quest.Quests;

public class AssetLoader {
    public static TextureRegion bar;
    public static TextureRegion bg;
    public static TextureRegion bird;
    public static Animation birdAnimation;
    public static TextureRegion birdDown;
    public static TextureRegion birdUp;
    public static Sound coin;
    public static Sound dead;
    public static Sound fall;
    public static Sound flap;
    public static BitmapFont font;
    public static TextureRegion gameOver;
    public static TextureRegion grass;
    public static TextureRegion highScore;
    public static TextureRegion logo;
    public static Texture logoTexture;
    public static TextureRegion noStar;
    public static TextureRegion playButtonDown;
    public static TextureRegion playButtonUp;
    private static Preferences prefs;
    public static Texture promo;
    public static TextureRegion promoRegion;
    public static Texture ptak;
    public static TextureRegion quizbanerdown;
    public static TextureRegion quizbanerup;
    public static TextureRegion ready;
    public static TextureRegion retry;
    public static TextureRegion run1;
    public static TextureRegion scoreboard;
    public static TextureRegion scoredown;
    public static TextureRegion scoreup;
    public static BitmapFont shadow;
    public static TextureRegion skullDown;
    public static TextureRegion skullUp;
    public static TextureRegion star;
    public static Texture texture;
    public static Texture textury;
    public static Texture tlo;
    public static TextureRegion unlockdown;
    public static TextureRegion unlockup;
    public static BitmapFont whiteFont;
    public static TextureRegion zbLogo;

    public static void load() {
        logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, (int) GL20.GL_NEVER, (int) Cast.MAX_NAMESPACE_LENGTH);
        promo = new Texture(Gdx.files.internal("data/banerek.png"));
        promo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        promoRegion = new TextureRegion(promo, 0, 0, (int) GL20.GL_DEPTH_BUFFER_BIT, (int) Cast.MAX_NAMESPACE_LENGTH);
        promoRegion.flip(false, true);
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        ptak = new Texture(Gdx.files.internal("data/male1.png"));
        ptak.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        textury = new Texture(Gdx.files.internal("data/Untitled2.png"));
        textury.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        tlo = new Texture(Gdx.files.internal("data/tlo123.png"));
        tlo.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        quizbanerup = new TextureRegion(textury, 154, 40, 84, 19);
        quizbanerdown = new TextureRegion(textury, (int) Keys.INSERT, 62, 84, 19);
        quizbanerup.flip(false, true);
        quizbanerdown.flip(false, true);
        playButtonUp = new TextureRegion(textury, 0, 0, 40, 14);
        playButtonDown = new TextureRegion(textury, 97, 86, 40, 14);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);
        scoreup = new TextureRegion(textury, 154, 25, 40, 14);
        scoredown = new TextureRegion(textury, 138, 86, 40, 14);
        scoreup.flip(false, true);
        scoredown.flip(false, true);
        unlockup = new TextureRegion(textury, 195, 25, 40, 14);
        unlockdown = new TextureRegion(textury, 179, 86, 40, 14);
        unlockup.flip(false, true);
        unlockdown.flip(false, true);
        ready = new TextureRegion(textury, 40, 0, 87, 22);
        ready.flip(false, true);
        retry = new TextureRegion(textury, 0, 14, 40, 14);
        retry.flip(false, true);
        gameOver = new TextureRegion(textury, 127, 0, 94, 19);
        gameOver.flip(false, true);
        scoreboard = new TextureRegion(textury, 0, 63, 97, 37);
        scoreboard.flip(false, true);
        star = new TextureRegion(textury, 0, 49, 10, 10);
        noStar = new TextureRegion(textury, 13, 49, 10, 10);
        star.flip(false, true);
        noStar.flip(false, true);
        highScore = new TextureRegion(texture, 59, (int) Quests.SELECT_COMPLETED_UNCLAIMED, 48, 7);
        highScore.flip(false, true);
        zbLogo = new TextureRegion(textury, 24, 28, 127, 33);
        zbLogo.flip(false, true);
        bg = new TextureRegion(tlo, 0, 0, (int) GL20.GL_DEPTH_BUFFER_BIT, (int) Cast.MAX_NAMESPACE_LENGTH);
        bg.flip(false, true);
        grass = new TextureRegion(textury, 0, 100, (int) Keys.F12, 28);
        grass.flip(false, true);
        birdDown = new TextureRegion(ptak, 4, 6, 74, 51);
        birdDown.flip(false, true);
        bird = new TextureRegion(ptak, 87, 6, 74, 51);
        bird.flip(false, true);
        birdUp = new TextureRegion(ptak, 174, 6, 74, 51);
        birdUp.flip(false, true);
        birdAnimation = new Animation(0.06f, birdDown, bird, birdUp);
        birdAnimation.setPlayMode(4);
        skullUp = new TextureRegion(textury, 0, 28, 24, 14);
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);
        bar = new TextureRegion(textury, 0, 43, 22, 3);
        bar.flip(false, true);
        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));
        font = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
        font.setScale(0.25f, -0.25f);
        whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
        whiteFont.setScale(0.1f, -0.1f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(0.25f, -0.25f);
        prefs = Gdx.app.getPreferences("FloppyBird");
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        texture.dispose();
        dead.dispose();
        flap.dispose();
        coin.dispose();
        font.dispose();
        shadow.dispose();
    }
}
