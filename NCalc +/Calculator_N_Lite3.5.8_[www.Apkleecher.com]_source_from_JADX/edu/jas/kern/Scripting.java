package edu.jas.kern;

public class Scripting {
    private static Lang script;

    public enum Lang {
        Python,
        Ruby
    }

    static {
        script = Lang.Python;
    }

    protected Scripting() {
    }

    public static Lang getLang() {
        return script;
    }

    public static Lang setLang(Lang s) {
        Lang o = script;
        script = s;
        return o;
    }
}
