package butterknife;

import android.support.annotation.UiThread;

public interface Unbinder {
    public static final Unbinder EMPTY;

    static class 1 implements Unbinder {
        1() {
        }

        public void unbind() {
        }
    }

    @UiThread
    void unbind();

    static {
        EMPTY = new 1();
    }
}
