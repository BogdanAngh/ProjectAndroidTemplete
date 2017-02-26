package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.utils.ObjectMap;

public class Dialog extends Window {
    public static float fadeDuration;
    Table buttonTable;
    boolean cancelHide;
    Table contentTable;
    InputListener ignoreTouchDown;
    Actor previousKeyboardFocus;
    Actor previousScrollFocus;
    private Skin skin;
    ObjectMap<Actor, Object> values;

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Dialog.1 */
    class C05701 extends InputListener {
        C05701() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            event.cancel();
            return false;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Dialog.2 */
    class C05712 extends ChangeListener {
        C05712() {
        }

        public void changed(ChangeEvent event, Actor actor) {
            if (Dialog.this.values.containsKey(actor)) {
                while (actor.getParent() != Dialog.this.buttonTable) {
                    actor = actor.getParent();
                }
                Dialog.this.result(Dialog.this.values.get(actor));
                if (!Dialog.this.cancelHide) {
                    Dialog.this.hide();
                }
                Dialog.this.cancelHide = false;
            }
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Dialog.3 */
    class C05723 extends FocusListener {
        C05723() {
        }

        public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
            if (!focused) {
                focusChanged(event);
            }
        }

        public void scrollFocusChanged(FocusEvent event, Actor actor, boolean focused) {
            if (!focused) {
                focusChanged(event);
            }
        }

        private void focusChanged(FocusEvent event) {
            Stage stage = Dialog.this.getStage();
            if (Dialog.this.isModal && stage != null && stage.getRoot().getChildren().size > 0 && stage.getRoot().getChildren().peek() == Dialog.this) {
                Actor newFocusedActor = event.getRelatedActor();
                if (newFocusedActor != null && !newFocusedActor.isDescendantOf(Dialog.this)) {
                    event.cancel();
                }
            }
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Dialog.4 */
    class C05734 extends InputListener {
        final /* synthetic */ int val$keycode;
        final /* synthetic */ Object val$object;

        C05734(int i, Object obj) {
            this.val$keycode = i;
            this.val$object = obj;
        }

        public boolean keyDown(InputEvent event, int keycode2) {
            if (this.val$keycode == keycode2) {
                Dialog.this.result(this.val$object);
                if (!Dialog.this.cancelHide) {
                    Dialog.this.hide();
                }
                Dialog.this.cancelHide = false;
            }
            return false;
        }
    }

    static {
        fadeDuration = 0.4f;
    }

    public Dialog(String title, Skin skin) {
        super(title, (WindowStyle) skin.get(WindowStyle.class));
        this.values = new ObjectMap();
        this.ignoreTouchDown = new C05701();
        this.skin = skin;
        initialize();
    }

    public Dialog(String title, Skin skin, String windowStyleName) {
        super(title, (WindowStyle) skin.get(windowStyleName, WindowStyle.class));
        this.values = new ObjectMap();
        this.ignoreTouchDown = new C05701();
        this.skin = skin;
        initialize();
    }

    public Dialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
        this.values = new ObjectMap();
        this.ignoreTouchDown = new C05701();
        initialize();
    }

    private void initialize() {
        setModal(true);
        defaults().space(6.0f);
        Actor table = new Table(this.skin);
        this.contentTable = table;
        add(table).expand().fill();
        row();
        table = new Table(this.skin);
        this.buttonTable = table;
        add(table);
        this.contentTable.defaults().space(6.0f);
        this.buttonTable.defaults().space(6.0f);
        this.buttonTable.addListener(new C05712());
        addListener(new C05723());
    }

    public Table getContentTable() {
        return this.contentTable;
    }

    public Table getButtonTable() {
        return this.buttonTable;
    }

    public Dialog text(String text) {
        if (this.skin != null) {
            return text(text, (LabelStyle) this.skin.get(LabelStyle.class));
        }
        throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
    }

    public Dialog text(String text, LabelStyle labelStyle) {
        return text(new Label((CharSequence) text, labelStyle));
    }

    public Dialog text(Label label) {
        this.contentTable.add((Actor) label);
        return this;
    }

    public Dialog button(String text) {
        return button(text, null);
    }

    public Dialog button(String text, Object object) {
        if (this.skin != null) {
            return button(text, object, (TextButtonStyle) this.skin.get(TextButtonStyle.class));
        }
        throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
    }

    public Dialog button(String text, Object object, TextButtonStyle buttonStyle) {
        return button(new TextButton(text, buttonStyle), object);
    }

    public Dialog button(Button button) {
        return button(button, null);
    }

    public Dialog button(Button button, Object object) {
        this.buttonTable.add((Actor) button);
        setObject(button, object);
        return this;
    }

    public Dialog show(Stage stage) {
        clearActions();
        removeCaptureListener(this.ignoreTouchDown);
        this.previousKeyboardFocus = null;
        Actor actor = stage.getKeyboardFocus();
        if (!(actor == null || actor.isDescendantOf(this))) {
            this.previousKeyboardFocus = actor;
        }
        this.previousScrollFocus = null;
        actor = stage.getScrollFocus();
        if (!(actor == null || actor.isDescendantOf(this))) {
            stage.setScrollFocus(this.previousScrollFocus);
        }
        pack();
        setPosition((float) Math.round((stage.getWidth() - getWidth()) / 2.0f), (float) Math.round((stage.getHeight() - getHeight()) / 2.0f));
        stage.addActor(this);
        stage.setKeyboardFocus(this);
        stage.setScrollFocus(this);
        if (fadeDuration > 0.0f) {
            getColor().f37a = 0.0f;
            addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
        }
        return this;
    }

    public void hide() {
        if (fadeDuration > 0.0f) {
            addCaptureListener(this.ignoreTouchDown);
            addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions.removeListener(this.ignoreTouchDown, true), Actions.removeActor()));
            return;
        }
        remove();
    }

    protected void setParent(Group parent) {
        super.setParent(parent);
        if (parent == null) {
            Stage stage = getStage();
            if (stage != null) {
                if (this.previousKeyboardFocus != null && this.previousKeyboardFocus.getStage() == null) {
                    this.previousKeyboardFocus = null;
                }
                Actor actor = stage.getKeyboardFocus();
                if (actor == null || actor.isDescendantOf(this)) {
                    stage.setKeyboardFocus(this.previousKeyboardFocus);
                }
                if (this.previousScrollFocus != null && this.previousScrollFocus.getStage() == null) {
                    this.previousScrollFocus = null;
                }
                actor = stage.getScrollFocus();
                if (actor == null || actor.isDescendantOf(this)) {
                    stage.setScrollFocus(this.previousScrollFocus);
                }
            }
        }
    }

    public void setObject(Actor actor, Object object) {
        this.values.put(actor, object);
    }

    public Dialog key(int keycode, Object object) {
        addListener(new C05734(keycode, object));
        return this;
    }

    protected void result(Object object) {
    }

    public void cancel() {
        this.cancelHide = true;
    }
}
