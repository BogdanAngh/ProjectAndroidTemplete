package com.badlogic.gdx;

import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.TimeUtils;

public class InputProcessorQueue implements InputProcessor {
    private static final int KEY_DOWN = 0;
    private static final int KEY_TYPED = 2;
    private static final int KEY_UP = 1;
    private static final int MOUSE_MOVED = 6;
    private static final int SCROLLED = 7;
    private static final int TOUCH_DOWN = 3;
    private static final int TOUCH_DRAGGED = 5;
    private static final int TOUCH_UP = 4;
    private long currentEventTime;
    private final IntArray processingQueue;
    private InputProcessor processor;
    private final IntArray queue;

    public InputProcessorQueue() {
        this.queue = new IntArray();
        this.processingQueue = new IntArray();
    }

    public InputProcessorQueue(InputProcessor processor) {
        this.queue = new IntArray();
        this.processingQueue = new IntArray();
        this.processor = processor;
    }

    public void setProcessor(InputProcessor processor) {
        this.processor = processor;
    }

    public InputProcessor getProcessor() {
        return this.processor;
    }

    public void drain() {
        IntArray q = this.processingQueue;
        synchronized (this) {
            if (this.processor == null) {
                this.queue.clear();
                return;
            }
            q.addAll(this.queue);
            this.queue.clear();
            int n = q.size;
            int i = KEY_DOWN;
            while (i < n) {
                int i2 = i + KEY_UP;
                i = i2 + KEY_UP;
                this.currentEventTime = (((long) q.get(i)) << 32) | (((long) q.get(i2)) & 4294967295L);
                i2 = i + KEY_UP;
                InputProcessor inputProcessor;
                int i3;
                int i4;
                int i5;
                switch (q.get(i)) {
                    case KEY_DOWN /*0*/:
                        i = i2 + KEY_UP;
                        this.processor.keyDown(q.get(i2));
                        i2 = i;
                        break;
                    case KEY_UP /*1*/:
                        i = i2 + KEY_UP;
                        this.processor.keyUp(q.get(i2));
                        i2 = i;
                        break;
                    case KEY_TYPED /*2*/:
                        i = i2 + KEY_UP;
                        this.processor.keyTyped((char) q.get(i2));
                        i2 = i;
                        break;
                    case TOUCH_DOWN /*3*/:
                        inputProcessor = this.processor;
                        i = i2 + KEY_UP;
                        i3 = q.get(i2);
                        i2 = i + KEY_UP;
                        i4 = q.get(i);
                        i = i2 + KEY_UP;
                        i5 = q.get(i2);
                        i2 = i + KEY_UP;
                        inputProcessor.touchDown(i3, i4, i5, q.get(i));
                        break;
                    case TOUCH_UP /*4*/:
                        inputProcessor = this.processor;
                        i = i2 + KEY_UP;
                        i3 = q.get(i2);
                        i2 = i + KEY_UP;
                        i4 = q.get(i);
                        i = i2 + KEY_UP;
                        i5 = q.get(i2);
                        i2 = i + KEY_UP;
                        inputProcessor.touchUp(i3, i4, i5, q.get(i));
                        break;
                    case TOUCH_DRAGGED /*5*/:
                        inputProcessor = this.processor;
                        i = i2 + KEY_UP;
                        i3 = q.get(i2);
                        i2 = i + KEY_UP;
                        i4 = q.get(i);
                        i = i2 + KEY_UP;
                        inputProcessor.touchDragged(i3, i4, q.get(i2));
                        i2 = i;
                        break;
                    case MOUSE_MOVED /*6*/:
                        inputProcessor = this.processor;
                        i = i2 + KEY_UP;
                        i3 = q.get(i2);
                        i2 = i + KEY_UP;
                        inputProcessor.mouseMoved(i3, q.get(i));
                        break;
                    case SCROLLED /*7*/:
                        i = i2 + KEY_UP;
                        this.processor.scrolled(q.get(i2));
                        i2 = i;
                        break;
                    default:
                        break;
                }
                i = i2;
            }
            q.clear();
        }
    }

    private void queueTime() {
        long time = TimeUtils.nanoTime();
        this.queue.add((int) (time >> 32));
        this.queue.add((int) time);
    }

    public synchronized boolean keyDown(int keycode) {
        queueTime();
        this.queue.add(KEY_DOWN);
        this.queue.add(keycode);
        return false;
    }

    public synchronized boolean keyUp(int keycode) {
        queueTime();
        this.queue.add(KEY_UP);
        this.queue.add(keycode);
        return false;
    }

    public synchronized boolean keyTyped(char character) {
        queueTime();
        this.queue.add(KEY_TYPED);
        this.queue.add(character);
        return false;
    }

    public synchronized boolean touchDown(int screenX, int screenY, int pointer, int button) {
        queueTime();
        this.queue.add(TOUCH_DOWN);
        this.queue.add(screenX);
        this.queue.add(screenY);
        this.queue.add(pointer);
        this.queue.add(button);
        return false;
    }

    public synchronized boolean touchUp(int screenX, int screenY, int pointer, int button) {
        queueTime();
        this.queue.add(TOUCH_UP);
        this.queue.add(screenX);
        this.queue.add(screenY);
        this.queue.add(pointer);
        this.queue.add(button);
        return false;
    }

    public synchronized boolean touchDragged(int screenX, int screenY, int pointer) {
        queueTime();
        this.queue.add(TOUCH_DRAGGED);
        this.queue.add(screenX);
        this.queue.add(screenY);
        this.queue.add(pointer);
        return false;
    }

    public synchronized boolean mouseMoved(int screenX, int screenY) {
        queueTime();
        this.queue.add(MOUSE_MOVED);
        this.queue.add(screenX);
        this.queue.add(screenY);
        return false;
    }

    public synchronized boolean scrolled(int amount) {
        queueTime();
        this.queue.add(SCROLLED);
        this.queue.add(amount);
        return false;
    }

    public long getCurrentEventTime() {
        return this.currentEventTime;
    }
}
