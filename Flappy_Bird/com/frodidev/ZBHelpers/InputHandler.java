package com.frodidev.ZBHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.frodidev.GameObjects.Bird;
import com.frodidev.GameWorld.GameWorld;
import com.frodidev.ui.SimpleButton;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements InputProcessor {
    private SimpleButton achivement;
    private List<SimpleButton> menuButtons;
    private Bird myBird;
    private GameWorld myWorld;
    private SimpleButton playButton;
    private SimpleButton promo;
    private SimpleButton runButton;
    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
        this.myWorld = myWorld;
        this.myBird = myWorld.getBird();
        int midPointY = myWorld.getMidPointY();
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        this.menuButtons = new ArrayList();
        this.playButton = new SimpleButton((float) (68 - (AssetLoader.playButtonUp.getRegionWidth() / 2)), (float) (midPointY + 50), 40.0f, 14.0f, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        this.menuButtons.add(this.playButton);
        this.runButton = new SimpleButton((float) (68 - (AssetLoader.scoreup.getRegionWidth() / 2)), (float) (midPointY + 20), 40.0f, 14.0f, AssetLoader.scoreup, AssetLoader.scoredown);
        this.menuButtons.add(this.runButton);
        this.achivement = new SimpleButton((float) (68 - (AssetLoader.unlockup.getRegionWidth() / 2)), (float) (midPointY + 35), 40.0f, 14.0f, AssetLoader.unlockup, AssetLoader.unlockdown);
        this.menuButtons.add(this.achivement);
        this.promo = new SimpleButton((float) (152 - (AssetLoader.promoRegion.getRegionWidth() / 2)), (float) (midPointY - 90), 86.0f, 35.0f, AssetLoader.promoRegion, AssetLoader.promoRegion);
        this.menuButtons.add(this.promo);
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (this.myWorld.isMenu()) {
            this.playButton.isTouchDown(screenX, screenY);
            this.runButton.isTouchDown(screenX, screenY);
            this.achivement.isTouchDown(screenX, screenY);
            this.promo.isTouchDown(screenX, screenY);
        } else if (this.myWorld.isReady()) {
            this.myWorld.start();
            this.myBird.onClick();
        } else if (this.myWorld.isRunning()) {
            this.myBird.onClick();
        }
        if (this.myWorld.isGameOver() || this.myWorld.isHighScore()) {
            this.myWorld.actionResolver.loadInterAds();
            this.myWorld.actionResolver.showInterAds();
            this.myWorld.restart();
        }
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (this.myWorld.isMenu()) {
            if (this.playButton.isTouchUp(screenX, screenY)) {
                this.myWorld.ready();
                return true;
            } else if (this.runButton.isTouchUp(screenX, screenY)) {
                if (this.myWorld.actionResolver.isSignedIn()) {
                    this.myWorld.actionResolver.showScores();
                    return true;
                }
                this.myWorld.actionResolver.signIn();
                return true;
            }
        }
        if (this.achivement.isTouchUp(screenX, screenY)) {
            if (this.myWorld.actionResolver.isSignedIn()) {
                this.myWorld.actionResolver.showAchievement();
                return true;
            }
            this.myWorld.actionResolver.signIn();
            return true;
        } else if (!this.promo.isTouchUp(screenX, screenY)) {
            return false;
        } else {
            Gdx.net.openURI("market://details?id=com.frodidev.RoundBird");
            return true;
        }
    }

    public boolean keyDown(int keycode) {
        if (keycode == 62) {
            if (this.myWorld.isMenu()) {
                this.myWorld.ready();
            } else if (this.myWorld.isReady()) {
                this.myWorld.start();
            }
            this.myBird.onClick();
            if (this.myWorld.isGameOver() || this.myWorld.isHighScore()) {
                this.myWorld.restart();
            }
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (((float) screenX) / this.scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (((float) screenY) / this.scaleFactorY);
    }

    public List<SimpleButton> getMenuButtons() {
        return this.menuButtons;
    }
}
