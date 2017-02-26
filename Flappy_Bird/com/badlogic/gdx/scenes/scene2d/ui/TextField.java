package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer.Task;
import com.google.android.gms.cast.TextTrackStyle;

public class TextField extends Widget implements Disableable {
    private static final char BACKSPACE = '\b';
    private static final char BULLET = '\u0095';
    private static final char DELETE = '\u007f';
    private static final char ENTER_ANDROID = '\n';
    private static final char ENTER_DESKTOP = '\r';
    private static final char TAB = '\t';
    static boolean isMac;
    private static final Vector2 tmp1;
    private static final Vector2 tmp2;
    private static final Vector2 tmp3;
    private float blinkTime;
    private Clipboard clipboard;
    int cursor;
    boolean cursorOn;
    boolean disabled;
    private CharSequence displayText;
    private final Rectangle fieldBounds;
    TextFieldFilter filter;
    boolean focusTraversal;
    private final FloatArray glyphAdvances;
    final FloatArray glyphPositions;
    boolean hasSelection;
    InputListener inputListener;
    float keyRepeatInitialTime;
    KeyRepeatTask keyRepeatTask;
    float keyRepeatTime;
    OnscreenKeyboard keyboard;
    long lastBlink;
    TextFieldListener listener;
    int maxLength;
    String messageText;
    boolean onlyFontChars;
    private StringBuilder passwordBuffer;
    private char passwordCharacter;
    private boolean passwordMode;
    float renderOffset;
    boolean rightAligned;
    private final Rectangle scissor;
    int selectionStart;
    private float selectionWidth;
    private float selectionX;
    TextFieldStyle style;
    String text;
    private final TextBounds textBounds;
    float textOffset;
    private int visibleTextEnd;
    private int visibleTextStart;

    public interface OnscreenKeyboard {
        void show(boolean z);
    }

    public interface TextFieldFilter {

        public static class DigitsOnlyFilter implements TextFieldFilter {
            public boolean acceptChar(TextField textField, char key) {
                return Character.isDigit(key);
            }
        }

        boolean acceptChar(TextField textField, char c);
    }

    public interface TextFieldListener {
        void keyTyped(TextField textField, char c);
    }

    public static class TextFieldStyle {
        public Drawable background;
        public Drawable cursor;
        public Drawable disabledBackground;
        public Color disabledFontColor;
        public Drawable focusedBackground;
        public Color focusedFontColor;
        public BitmapFont font;
        public Color fontColor;
        public BitmapFont messageFont;
        public Color messageFontColor;
        public Drawable selection;

        public TextFieldStyle(BitmapFont font, Color fontColor, Drawable cursor, Drawable selection, Drawable background) {
            this.background = background;
            this.cursor = cursor;
            this.font = font;
            this.fontColor = fontColor;
            this.selection = selection;
        }

        public TextFieldStyle(TextFieldStyle style) {
            this.messageFont = style.messageFont;
            if (style.messageFontColor != null) {
                this.messageFontColor = new Color(style.messageFontColor);
            }
            this.background = style.background;
            this.focusedBackground = style.focusedBackground;
            this.disabledBackground = style.disabledBackground;
            this.cursor = style.cursor;
            this.font = style.font;
            if (style.fontColor != null) {
                this.fontColor = new Color(style.fontColor);
            }
            if (style.focusedFontColor != null) {
                this.focusedFontColor = new Color(style.focusedFontColor);
            }
            if (style.disabledFontColor != null) {
                this.disabledFontColor = new Color(style.disabledFontColor);
            }
            this.selection = style.selection;
        }
    }

    public static class DefaultOnscreenKeyboard implements OnscreenKeyboard {
        public void show(boolean visible) {
            Gdx.input.setOnscreenKeyboardVisible(visible);
        }
    }

    class KeyRepeatTask extends Task {
        int keycode;

        KeyRepeatTask() {
        }

        public void run() {
            TextField.this.inputListener.keyDown(null, this.keycode);
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.TextField.1 */
    class C08421 extends ClickListener {
        C08421() {
        }

        public void clicked(InputEvent event, float x, float y) {
            if (getTapCount() > 1) {
                TextField.this.setSelection(0, TextField.this.text.length());
            }
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (!super.touchDown(event, x, y, pointer, button)) {
                return false;
            }
            if (pointer == 0 && button != 0) {
                return false;
            }
            if (TextField.this.disabled) {
                return true;
            }
            TextField.this.clearSelection();
            setCursorPosition(x);
            TextField.this.selectionStart = TextField.this.cursor;
            Stage stage = TextField.this.getStage();
            if (stage != null) {
                stage.setKeyboardFocus(TextField.this);
            }
            TextField.this.keyboard.show(true);
            return true;
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            super.touchDragged(event, x, y, pointer);
            TextField.this.lastBlink = 0;
            TextField.this.cursorOn = false;
            setCursorPosition(x);
            TextField.this.hasSelection = true;
        }

        private void setCursorPosition(float x) {
            TextField.this.lastBlink = 0;
            TextField.this.cursorOn = false;
            x -= TextField.this.renderOffset + TextField.this.textOffset;
            for (int i = 0; i < TextField.this.glyphPositions.size; i++) {
                if (TextField.this.glyphPositions.items[i] > x) {
                    TextField.this.cursor = Math.max(0, i - 1);
                    return;
                }
            }
            TextField.this.cursor = Math.max(0, TextField.this.glyphPositions.size - 1);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean keyDown(com.badlogic.gdx.scenes.scene2d.InputEvent r14, int r15) {
            /*
            r13 = this;
            r12 = 65;
            r11 = 57;
            r10 = 48;
            r5 = 0;
            r6 = 1;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.disabled;
            if (r7 == 0) goto L_0x0010;
        L_0x000e:
            r6 = r5;
        L_0x000f:
            return r6;
        L_0x0010:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = 0;
            r7.lastBlink = r8;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.cursorOn = r5;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r4 = r7.getStage();
            if (r4 == 0) goto L_0x0282;
        L_0x0022:
            r7 = r4.getKeyboardFocus();
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            if (r7 != r8) goto L_0x0282;
        L_0x002a:
            r3 = 0;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.isMac;
            if (r7 == 0) goto L_0x0043;
        L_0x002f:
            r7 = com.badlogic.gdx.Gdx.input;
            r8 = 63;
            r1 = r7.isKeyPressed(r8);
        L_0x0037:
            if (r1 == 0) goto L_0x0081;
        L_0x0039:
            r7 = 50;
            if (r15 != r7) goto L_0x005b;
        L_0x003d:
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5.paste();
            goto L_0x000f;
        L_0x0043:
            r7 = com.badlogic.gdx.Gdx.input;
            r8 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
            r7 = r7.isKeyPressed(r8);
            if (r7 != 0) goto L_0x0057;
        L_0x004d:
            r7 = com.badlogic.gdx.Gdx.input;
            r8 = 130; // 0x82 float:1.82E-43 double:6.4E-322;
            r7 = r7.isKeyPressed(r8);
            if (r7 == 0) goto L_0x0059;
        L_0x0057:
            r1 = r6;
        L_0x0058:
            goto L_0x0037;
        L_0x0059:
            r1 = r5;
            goto L_0x0058;
        L_0x005b:
            r7 = 31;
            if (r15 == r7) goto L_0x0063;
        L_0x005f:
            r7 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
            if (r15 != r7) goto L_0x0069;
        L_0x0063:
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5.copy();
            goto L_0x000f;
        L_0x0069:
            r7 = 52;
            if (r15 == r7) goto L_0x0071;
        L_0x006d:
            r7 = 67;
            if (r15 != r7) goto L_0x0077;
        L_0x0071:
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5.cut();
            goto L_0x000f;
        L_0x0077:
            r7 = 29;
            if (r15 != r7) goto L_0x0081;
        L_0x007b:
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5.selectAll();
            goto L_0x000f;
        L_0x0081:
            r7 = com.badlogic.gdx.Gdx.input;
            r8 = 59;
            r7 = r7.isKeyPressed(r8);
            if (r7 != 0) goto L_0x0095;
        L_0x008b:
            r7 = com.badlogic.gdx.Gdx.input;
            r8 = 60;
            r7 = r7.isKeyPressed(r8);
            if (r7 == 0) goto L_0x01cb;
        L_0x0095:
            r7 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
            if (r15 != r7) goto L_0x009e;
        L_0x0099:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.paste();
        L_0x009e:
            r7 = 112; // 0x70 float:1.57E-43 double:5.53E-322;
            if (r15 != r7) goto L_0x00b2;
        L_0x00a2:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.hasSelection;
            if (r7 == 0) goto L_0x00b2;
        L_0x00a8:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.copy();
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.delete();
        L_0x00b2:
            r7 = 21;
            if (r15 != r7) goto L_0x00f3;
        L_0x00b6:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.hasSelection;
            if (r7 != 0) goto L_0x00c8;
        L_0x00bc:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r7.selectionStart = r8;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.hasSelection = r6;
        L_0x00c8:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r7.cursor;
            r8 = r8 + -1;
            r7.cursor = r8;
            if (r8 <= 0) goto L_0x00f2;
        L_0x00d2:
            if (r1 == 0) goto L_0x00f2;
        L_0x00d4:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r0 = r7.charAt(r8);
            if (r0 < r12) goto L_0x00e6;
        L_0x00e2:
            r7 = 90;
            if (r0 <= r7) goto L_0x00c8;
        L_0x00e6:
            r7 = 97;
            if (r0 < r7) goto L_0x00ee;
        L_0x00ea:
            r7 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
            if (r0 <= r7) goto L_0x00c8;
        L_0x00ee:
            if (r0 < r10) goto L_0x00f2;
        L_0x00f0:
            if (r0 <= r11) goto L_0x00c8;
        L_0x00f2:
            r3 = 1;
        L_0x00f3:
            r7 = 22;
            if (r15 != r7) goto L_0x013e;
        L_0x00f7:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.hasSelection;
            if (r7 != 0) goto L_0x0109;
        L_0x00fd:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r7.selectionStart = r8;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.hasSelection = r6;
        L_0x0109:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r2 = r7.length();
        L_0x0111:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r7.cursor;
            r8 = r8 + 1;
            r7.cursor = r8;
            if (r8 >= r2) goto L_0x013d;
        L_0x011b:
            if (r1 == 0) goto L_0x013d;
        L_0x011d:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r8 = r8 + -1;
            r0 = r7.charAt(r8);
            if (r0 < r12) goto L_0x0131;
        L_0x012d:
            r7 = 90;
            if (r0 <= r7) goto L_0x0111;
        L_0x0131:
            r7 = 97;
            if (r0 < r7) goto L_0x0139;
        L_0x0135:
            r7 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
            if (r0 <= r7) goto L_0x0111;
        L_0x0139:
            if (r0 < r10) goto L_0x013d;
        L_0x013b:
            if (r0 <= r11) goto L_0x0111;
        L_0x013d:
            r3 = 1;
        L_0x013e:
            r7 = 3;
            if (r15 != r7) goto L_0x0157;
        L_0x0141:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.hasSelection;
            if (r7 != 0) goto L_0x0153;
        L_0x0147:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r7.selectionStart = r8;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.hasSelection = r6;
        L_0x0153:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.cursor = r5;
        L_0x0157:
            r7 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
            if (r15 != r7) goto L_0x0179;
        L_0x015b:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.hasSelection;
            if (r7 != 0) goto L_0x016d;
        L_0x0161:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r7.selectionStart = r8;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.hasSelection = r6;
        L_0x016d:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.text;
            r8 = r8.length();
            r7.cursor = r8;
        L_0x0179:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r5 = java.lang.Math.max(r5, r8);
            r7.cursor = r5;
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r7 = r7.length();
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r7 = java.lang.Math.min(r7, r8);
            r5.cursor = r7;
        L_0x0199:
            if (r3 == 0) goto L_0x000f;
        L_0x019b:
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5 = r5.keyRepeatTask;
            r5 = r5.isScheduled();
            if (r5 == 0) goto L_0x01ad;
        L_0x01a5:
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5 = r5.keyRepeatTask;
            r5 = r5.keycode;
            if (r5 == r15) goto L_0x000f;
        L_0x01ad:
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5 = r5.keyRepeatTask;
            r5.keycode = r15;
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5 = r5.keyRepeatTask;
            r5.cancel();
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r5 = r5.keyRepeatTask;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.keyRepeatInitialTime;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.keyRepeatTime;
            com.badlogic.gdx.utils.Timer.schedule(r5, r7, r8);
            goto L_0x000f;
        L_0x01cb:
            r7 = 21;
            if (r15 != r7) goto L_0x0201;
        L_0x01cf:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r7.cursor;
            r9 = r8 + -1;
            r7.cursor = r9;
            if (r8 <= r6) goto L_0x01fb;
        L_0x01d9:
            if (r1 == 0) goto L_0x01fb;
        L_0x01db:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r8 = r8 + -1;
            r0 = r7.charAt(r8);
            if (r0 < r12) goto L_0x01ef;
        L_0x01eb:
            r7 = 90;
            if (r0 <= r7) goto L_0x01cf;
        L_0x01ef:
            r7 = 97;
            if (r0 < r7) goto L_0x01f7;
        L_0x01f3:
            r7 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
            if (r0 <= r7) goto L_0x01cf;
        L_0x01f7:
            if (r0 < r10) goto L_0x01fb;
        L_0x01f9:
            if (r0 <= r11) goto L_0x01cf;
        L_0x01fb:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.clearSelection();
            r3 = 1;
        L_0x0201:
            r7 = 22;
            if (r15 != r7) goto L_0x023f;
        L_0x0205:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r2 = r7.length();
        L_0x020d:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r7.cursor;
            r8 = r8 + 1;
            r7.cursor = r8;
            if (r8 >= r2) goto L_0x0239;
        L_0x0217:
            if (r1 == 0) goto L_0x0239;
        L_0x0219:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r8 = r8 + -1;
            r0 = r7.charAt(r8);
            if (r0 < r12) goto L_0x022d;
        L_0x0229:
            r7 = 90;
            if (r0 <= r7) goto L_0x020d;
        L_0x022d:
            r7 = 97;
            if (r0 < r7) goto L_0x0235;
        L_0x0231:
            r7 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
            if (r0 <= r7) goto L_0x020d;
        L_0x0235:
            if (r0 < r10) goto L_0x0239;
        L_0x0237:
            if (r0 <= r11) goto L_0x020d;
        L_0x0239:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.clearSelection();
            r3 = 1;
        L_0x023f:
            r7 = 3;
            if (r15 != r7) goto L_0x024b;
        L_0x0242:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.cursor = r5;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.clearSelection();
        L_0x024b:
            r7 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
            if (r15 != r7) goto L_0x0260;
        L_0x024f:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.text;
            r8 = r8.length();
            r7.cursor = r8;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7.clearSelection();
        L_0x0260:
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r5 = java.lang.Math.max(r5, r8);
            r7.cursor = r5;
            r5 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r7 = r7.text;
            r7 = r7.length();
            r8 = com.badlogic.gdx.scenes.scene2d.ui.TextField.this;
            r8 = r8.cursor;
            r7 = java.lang.Math.min(r7, r8);
            r5.cursor = r7;
            goto L_0x0199;
        L_0x0282:
            r6 = r5;
            goto L_0x000f;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.scenes.scene2d.ui.TextField.1.keyDown(com.badlogic.gdx.scenes.scene2d.InputEvent, int):boolean");
        }

        public boolean keyUp(InputEvent event, int keycode) {
            if (TextField.this.disabled) {
                return false;
            }
            TextField.this.keyRepeatTask.cancel();
            return true;
        }

        public boolean keyTyped(InputEvent event, char character) {
            boolean z = false;
            if (TextField.this.disabled) {
                return false;
            }
            BitmapFont font = TextField.this.style.font;
            Stage stage = TextField.this.getStage();
            if (stage == null || stage.getKeyboardFocus() != TextField.this) {
                return false;
            }
            TextField textField;
            if (character == TextField.BACKSPACE) {
                if (TextField.this.cursor > 0 || TextField.this.hasSelection) {
                    if (TextField.this.hasSelection) {
                        TextField.this.delete();
                    } else {
                        TextField.this.text = TextField.this.text.substring(0, TextField.this.cursor - 1) + TextField.this.text.substring(TextField.this.cursor);
                        TextField.this.updateDisplayText();
                        textField = TextField.this;
                        textField.cursor--;
                        TextField.this.renderOffset = 0.0f;
                    }
                }
            } else if (character == TextField.DELETE) {
                if (TextField.this.cursor < TextField.this.text.length() || TextField.this.hasSelection) {
                    if (TextField.this.hasSelection) {
                        TextField.this.delete();
                    } else {
                        TextField.this.text = TextField.this.text.substring(0, TextField.this.cursor) + TextField.this.text.substring(TextField.this.cursor + 1);
                        TextField.this.updateDisplayText();
                    }
                }
            } else if ((character == TextField.TAB || character == TextField.ENTER_ANDROID) && TextField.this.focusTraversal) {
                TextField textField2 = TextField.this;
                if (Gdx.input.isKeyPressed(59) || Gdx.input.isKeyPressed(60)) {
                    z = true;
                }
                textField2.next(z);
            } else if (font.containsCharacter(character)) {
                if (character != TextField.ENTER_DESKTOP && character != TextField.ENTER_ANDROID && TextField.this.filter != null && !TextField.this.filter.acceptChar(TextField.this, character)) {
                    return true;
                }
                if (TextField.this.maxLength > 0 && TextField.this.text.length() + 1 > TextField.this.maxLength) {
                    return true;
                }
                if (TextField.this.hasSelection) {
                    int minIndex = Math.min(TextField.this.cursor, TextField.this.selectionStart);
                    int maxIndex = Math.max(TextField.this.cursor, TextField.this.selectionStart);
                    TextField.this.text = (minIndex > 0 ? TextField.this.text.substring(0, minIndex) : "") + (maxIndex < TextField.this.text.length() ? TextField.this.text.substring(maxIndex, TextField.this.text.length()) : "");
                    TextField.this.cursor = minIndex;
                    TextField.this.text = TextField.this.text.substring(0, TextField.this.cursor) + character + TextField.this.text.substring(TextField.this.cursor, TextField.this.text.length());
                    TextField.this.updateDisplayText();
                    textField = TextField.this;
                    textField.cursor++;
                    TextField.this.clearSelection();
                } else {
                    TextField.this.text = TextField.this.text.substring(0, TextField.this.cursor) + character + TextField.this.text.substring(TextField.this.cursor, TextField.this.text.length());
                    TextField.this.updateDisplayText();
                    textField = TextField.this;
                    textField.cursor++;
                }
            }
            if (TextField.this.listener == null) {
                return true;
            }
            TextField.this.listener.keyTyped(TextField.this, character);
            return true;
        }
    }

    static {
        tmp1 = new Vector2();
        tmp2 = new Vector2();
        tmp3 = new Vector2();
        isMac = System.getProperty("os.name").contains("Mac");
    }

    public TextField(String text, Skin skin) {
        this(text, (TextFieldStyle) skin.get(TextFieldStyle.class));
    }

    public TextField(String text, Skin skin, String styleName) {
        this(text, (TextFieldStyle) skin.get(styleName, TextFieldStyle.class));
    }

    public TextField(String text, TextFieldStyle style) {
        this.keyboard = new DefaultOnscreenKeyboard();
        this.focusTraversal = true;
        this.onlyFontChars = true;
        this.fieldBounds = new Rectangle();
        this.textBounds = new TextBounds();
        this.scissor = new Rectangle();
        this.glyphAdvances = new FloatArray();
        this.glyphPositions = new FloatArray();
        this.cursorOn = true;
        this.blinkTime = 0.32f;
        this.passwordCharacter = BULLET;
        this.keyRepeatTask = new KeyRepeatTask();
        this.keyRepeatInitialTime = 0.4f;
        this.keyRepeatTime = 0.1f;
        this.maxLength = 0;
        setStyle(style);
        this.clipboard = Gdx.app.getClipboard();
        setText(text);
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
        initialize();
    }

    private void initialize() {
        EventListener c08421 = new C08421();
        this.inputListener = c08421;
        addListener(c08421);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setOnlyFontChars(boolean onlyFontChars) {
        this.onlyFontChars = onlyFontChars;
    }

    public void setStyle(TextFieldStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        invalidateHierarchy();
    }

    public void setPasswordCharacter(char passwordCharacter) {
        this.passwordCharacter = passwordCharacter;
        if (this.passwordMode) {
            updateDisplayText();
        }
    }

    public TextFieldStyle getStyle() {
        return this.style;
    }

    private void calculateOffsets() {
        float visibleWidth = getWidth();
        if (this.style.background != null) {
            visibleWidth -= this.style.background.getLeftWidth() + this.style.background.getRightWidth();
        }
        float distance = this.glyphPositions.get(this.cursor) - Math.abs(this.renderOffset);
        if (distance <= 0.0f) {
            if (this.cursor > 0) {
                this.renderOffset = -this.glyphPositions.get(this.cursor - 1);
            } else {
                this.renderOffset = 0.0f;
            }
        } else if (distance > visibleWidth) {
            this.renderOffset -= distance - visibleWidth;
        }
        this.visibleTextStart = 0;
        this.textOffset = 0.0f;
        float start = Math.abs(this.renderOffset);
        int len = this.glyphPositions.size;
        float startPos = 0.0f;
        for (int i = 0; i < len; i++) {
            if (this.glyphPositions.items[i] >= start) {
                this.visibleTextStart = i;
                startPos = this.glyphPositions.items[i];
                this.textOffset = startPos - start;
                break;
            }
        }
        this.visibleTextEnd = Math.min(this.displayText.length(), this.cursor + 1);
        while (this.visibleTextEnd <= this.displayText.length() && this.glyphPositions.items[this.visibleTextEnd] - startPos <= visibleWidth) {
            this.visibleTextEnd++;
        }
        this.visibleTextEnd = Math.max(0, this.visibleTextEnd - 1);
        if (this.hasSelection) {
            int minIndex = Math.min(this.cursor, this.selectionStart);
            int maxIndex = Math.max(this.cursor, this.selectionStart);
            float minX = Math.max(this.glyphPositions.get(minIndex), startPos);
            float maxX = Math.min(this.glyphPositions.get(maxIndex), this.glyphPositions.get(this.visibleTextEnd));
            this.selectionX = minX;
            this.selectionWidth = maxX - minX;
        }
        if (this.rightAligned) {
            this.textOffset = visibleWidth - (this.glyphPositions.items[this.visibleTextEnd] - startPos);
            if (this.hasSelection) {
                this.selectionX += this.textOffset;
            }
        }
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        Stage stage = getStage();
        boolean focused = stage != null && stage.getKeyboardFocus() == this;
        BitmapFont font = this.style.font;
        Color fontColor = (!this.disabled || this.style.disabledFontColor == null) ? (!focused || this.style.focusedFontColor == null) ? this.style.fontColor : this.style.focusedFontColor : this.style.disabledFontColor;
        Drawable selection = this.style.selection;
        Drawable cursorPatch = this.style.cursor;
        Drawable background = (!this.disabled || this.style.disabledBackground == null) ? (!focused || this.style.focusedBackground == null) ? this.style.background : this.style.focusedBackground : this.style.disabledBackground;
        Color color = getColor();
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        float textY = (this.textBounds.height / 2.0f) + font.getDescent();
        batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
        float bgLeftWidth = 0.0f;
        if (background != null) {
            background.draw(batch, x, y, width, height);
            bgLeftWidth = background.getLeftWidth();
            float bottom = background.getBottomHeight();
            textY = (float) ((int) (((((height - background.getTopHeight()) - bottom) / 2.0f) + textY) + bottom));
        } else {
            textY = (float) ((int) ((height / 2.0f) + textY));
        }
        calculateOffsets();
        if (focused && this.hasSelection && selection != null) {
            selection.draw(batch, ((this.selectionX + x) + bgLeftWidth) + this.renderOffset, ((y + textY) - this.textBounds.height) - font.getDescent(), this.selectionWidth, this.textBounds.height + (font.getDescent() / 2.0f));
        }
        float yOffset = font.isFlipped() ? -this.textBounds.height : 0.0f;
        if (this.displayText.length() != 0) {
            font.setColor(fontColor.f40r, fontColor.f39g, fontColor.f38b, fontColor.f37a * parentAlpha);
            font.draw(batch, this.displayText, (x + bgLeftWidth) + this.textOffset, (y + textY) + yOffset, this.visibleTextStart, this.visibleTextEnd);
        } else if (!(focused || this.messageText == null)) {
            BitmapFont messageFont;
            if (this.style.messageFontColor != null) {
                font.setColor(this.style.messageFontColor.f40r, this.style.messageFontColor.f39g, this.style.messageFontColor.f38b, this.style.messageFontColor.f37a * parentAlpha);
            } else {
                font.setColor(0.7f, 0.7f, 0.7f, parentAlpha);
            }
            if (this.style.messageFont != null) {
                messageFont = this.style.messageFont;
            } else {
                messageFont = font;
            }
            messageFont.draw(batch, this.messageText, x + bgLeftWidth, (y + textY) + yOffset);
        }
        if (focused && !this.disabled) {
            blink();
            if (this.cursorOn && cursorPatch != null) {
                cursorPatch.draw(batch, ((((x + bgLeftWidth) + this.textOffset) + this.glyphPositions.get(this.cursor)) - this.glyphPositions.items[this.visibleTextStart]) - TextTrackStyle.DEFAULT_FONT_SCALE, ((y + textY) - this.textBounds.height) - font.getDescent(), cursorPatch.getMinWidth(), this.textBounds.height + (font.getDescent() / 2.0f));
            }
        }
    }

    void updateDisplayText() {
        int i;
        StringBuilder buffer = new StringBuilder();
        for (i = 0; i < this.text.length(); i++) {
            char c = this.text.charAt(i);
            if (!this.style.font.containsCharacter(c)) {
                c = ' ';
            }
            buffer.append(c);
        }
        String text = buffer.toString();
        if (this.passwordMode && this.style.font.containsCharacter(this.passwordCharacter)) {
            if (this.passwordBuffer == null) {
                this.passwordBuffer = new StringBuilder(text.length());
            }
            if (this.passwordBuffer.length() > text.length()) {
                this.passwordBuffer.setLength(text.length());
            } else {
                int n = text.length();
                for (i = this.passwordBuffer.length(); i < n; i++) {
                    this.passwordBuffer.append(this.passwordCharacter);
                }
            }
            this.displayText = this.passwordBuffer;
        } else {
            this.displayText = text;
        }
        this.style.font.computeGlyphAdvancesAndPositions(this.displayText, this.glyphAdvances, this.glyphPositions);
        if (this.selectionStart > text.length()) {
            this.selectionStart = text.length();
        }
    }

    private void blink() {
        long time = TimeUtils.nanoTime();
        if (((float) (time - this.lastBlink)) / 1.0E9f > this.blinkTime) {
            this.cursorOn = !this.cursorOn;
            this.lastBlink = time;
        }
    }

    public void copy() {
        if (this.hasSelection && !this.passwordMode) {
            this.clipboard.setContents(this.text.substring(Math.min(this.cursor, this.selectionStart), Math.max(this.cursor, this.selectionStart)));
        }
    }

    public void cut() {
        if (this.hasSelection && !this.passwordMode) {
            copy();
            delete();
        }
    }

    void paste() {
        String content = this.clipboard.getContents();
        if (content != null) {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < content.length() && (this.maxLength <= 0 || (this.text.length() + buffer.length()) + 1 <= this.maxLength); i++) {
                char c = content.charAt(i);
                if (this.style.font.containsCharacter(c) && (this.filter == null || this.filter.acceptChar(this, c))) {
                    buffer.append(c);
                }
            }
            content = buffer.toString();
            if (this.hasSelection) {
                int minIndex = Math.min(this.cursor, this.selectionStart);
                int maxIndex = Math.max(this.cursor, this.selectionStart);
                this.text = (minIndex > 0 ? this.text.substring(0, minIndex) : "") + (maxIndex < this.text.length() ? this.text.substring(maxIndex, this.text.length()) : "");
                this.cursor = minIndex;
                this.text = this.text.substring(0, this.cursor) + content + this.text.substring(this.cursor, this.text.length());
                updateDisplayText();
                this.cursor = content.length() + minIndex;
                clearSelection();
                return;
            }
            this.text = this.text.substring(0, this.cursor) + content + this.text.substring(this.cursor, this.text.length());
            updateDisplayText();
            this.cursor += content.length();
        }
    }

    void delete() {
        int minIndex = Math.min(this.cursor, this.selectionStart);
        int maxIndex = Math.max(this.cursor, this.selectionStart);
        this.text = (minIndex > 0 ? this.text.substring(0, minIndex) : "") + (maxIndex < this.text.length() ? this.text.substring(maxIndex, this.text.length()) : "");
        updateDisplayText();
        this.cursor = minIndex;
        clearSelection();
    }

    public void next(boolean up) {
        Stage stage = getStage();
        if (stage != null) {
            getParent().localToStageCoordinates(tmp1.set(getX(), getY()));
            TextField textField = findNextTextField(stage.getActors(), null, tmp2, tmp1, up);
            if (textField == null) {
                if (up) {
                    tmp1.set(Float.MIN_VALUE, Float.MIN_VALUE);
                } else {
                    tmp1.set(Float.MAX_VALUE, Float.MAX_VALUE);
                }
                textField = findNextTextField(getStage().getActors(), null, tmp2, tmp1, up);
            }
            if (textField != null) {
                stage.setKeyboardFocus(textField);
            } else {
                Gdx.input.setOnscreenKeyboardVisible(false);
            }
        }
    }

    private TextField findNextTextField(Array<Actor> actors, TextField best, Vector2 bestCoords, Vector2 currentCoords, boolean up) {
        int n = actors.size;
        for (int i = 0; i < n; i++) {
            Actor actor = (Actor) actors.get(i);
            if (actor != this) {
                if (actor instanceof TextField) {
                    TextField textField = (TextField) actor;
                    if (!textField.isDisabled() && textField.focusTraversal) {
                        Vector2 actorCoords = actor.getParent().localToStageCoordinates(tmp3.set(actor.getX(), actor.getY()));
                        int i2 = (actorCoords.f101y < currentCoords.f101y || (actorCoords.f101y == currentCoords.f101y && actorCoords.f100x > currentCoords.f100x)) ? 1 : 0;
                        if ((i2 ^ up) != 0) {
                            if (best != null) {
                                i2 = (actorCoords.f101y > bestCoords.f101y || (actorCoords.f101y == bestCoords.f101y && actorCoords.f100x < bestCoords.f100x)) ? 1 : 0;
                                if ((i2 ^ up) == 0) {
                                }
                            }
                            best = (TextField) actor;
                            bestCoords.set(actorCoords);
                        }
                    }
                } else if (actor instanceof Group) {
                    best = findNextTextField(((Group) actor).getChildren(), best, bestCoords, currentCoords, up);
                }
            }
        }
        return best;
    }

    public void setTextFieldListener(TextFieldListener listener) {
        this.listener = listener;
    }

    public void setTextFieldFilter(TextFieldFilter filter) {
        this.filter = filter;
    }

    public void setFocusTraversal(boolean focusTraversal) {
        this.focusTraversal = focusTraversal;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        BitmapFont font = this.style.font;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < text.length() && (this.maxLength <= 0 || buffer.length() + 1 <= this.maxLength); i++) {
            char c = text.charAt(i);
            if ((!this.onlyFontChars || this.style.font.containsCharacter(c)) && (this.filter == null || this.filter.acceptChar(this, c))) {
                buffer.append(c);
            }
        }
        this.text = buffer.toString();
        updateDisplayText();
        this.cursor = 0;
        clearSelection();
        this.textBounds.set(font.getBounds(this.displayText));
        TextBounds textBounds = this.textBounds;
        textBounds.height -= font.getDescent() * 2.0f;
        font.computeGlyphAdvancesAndPositions(this.displayText, this.glyphAdvances, this.glyphPositions);
    }

    public String getText() {
        return this.text;
    }

    public void setSelection(int selectionStart, int selectionEnd) {
        if (selectionStart < 0) {
            throw new IllegalArgumentException("selectionStart must be >= 0");
        } else if (selectionEnd < 0) {
            throw new IllegalArgumentException("selectionEnd must be >= 0");
        } else {
            selectionStart = Math.min(this.text.length(), selectionStart);
            selectionEnd = Math.min(this.text.length(), selectionEnd);
            if (selectionEnd == selectionStart) {
                clearSelection();
                return;
            }
            if (selectionEnd < selectionStart) {
                int temp = selectionEnd;
                selectionEnd = selectionStart;
                selectionStart = temp;
            }
            this.hasSelection = true;
            this.selectionStart = selectionStart;
            this.cursor = selectionEnd;
        }
    }

    public void selectAll() {
        setSelection(0, this.text.length());
    }

    public void clearSelection() {
        this.hasSelection = false;
    }

    public void setCursorPosition(int cursorPosition) {
        if (cursorPosition < 0) {
            throw new IllegalArgumentException("cursorPosition must be >= 0");
        }
        clearSelection();
        this.cursor = Math.min(cursorPosition, this.text.length());
    }

    public int getCursorPosition() {
        return this.cursor;
    }

    public OnscreenKeyboard getOnscreenKeyboard() {
        return this.keyboard;
    }

    public void setOnscreenKeyboard(OnscreenKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void setClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    public float getPrefWidth() {
        return 150.0f;
    }

    public float getPrefHeight() {
        float prefHeight = this.textBounds.height;
        if (this.style.background != null) {
            return Math.max((this.style.background.getBottomHeight() + prefHeight) + this.style.background.getTopHeight(), this.style.background.getMinHeight());
        }
        return prefHeight;
    }

    public void setRightAligned(boolean rightAligned) {
        this.rightAligned = rightAligned;
    }

    public void setPasswordMode(boolean passwordMode) {
        this.passwordMode = passwordMode;
        updateDisplayText();
    }

    public void setBlinkTime(float blinkTime) {
        this.blinkTime = blinkTime;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public boolean isPasswordMode() {
        return this.passwordMode;
    }

    public TextFieldFilter getTextFieldFilter() {
        return this.filter;
    }
}
