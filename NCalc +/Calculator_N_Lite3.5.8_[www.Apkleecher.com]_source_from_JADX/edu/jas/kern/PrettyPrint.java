package edu.jas.kern;

public class PrettyPrint {
    private static volatile boolean toDo;

    static {
        toDo = true;
    }

    protected PrettyPrint() {
    }

    public static boolean isTrue() {
        return toDo;
    }

    public static void setPretty() {
        toDo = true;
    }

    public static void setInternal() {
        toDo = false;
    }
}
