package edu.jas.kern;

public class PreemptStatus {
    private static boolean allowPreempt;

    static {
        allowPreempt = true;
    }

    protected PreemptStatus() {
    }

    public static boolean isAllowed() {
        return allowPreempt;
    }

    public static void setAllow() {
        allowPreempt = true;
    }

    public static void setNotAllow() {
        allowPreempt = false;
    }
}
