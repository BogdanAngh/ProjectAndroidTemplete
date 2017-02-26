package com.google.android.gms.cast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.accessibility.CaptioningManager;
import android.view.accessibility.CaptioningManager.CaptionStyle;
import com.google.android.gms.cast.internal.zzf;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.internal.zzlh;
import com.google.android.gms.internal.zzlk;
import org.json.JSONException;
import org.json.JSONObject;

public final class TextTrackStyle {
    public static final int COLOR_UNSPECIFIED = 0;
    public static final float DEFAULT_FONT_SCALE = 1.0f;
    public static final int EDGE_TYPE_DEPRESSED = 4;
    public static final int EDGE_TYPE_DROP_SHADOW = 2;
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_OUTLINE = 1;
    public static final int EDGE_TYPE_RAISED = 3;
    public static final int EDGE_TYPE_UNSPECIFIED = -1;
    public static final int FONT_FAMILY_CASUAL = 4;
    public static final int FONT_FAMILY_CURSIVE = 5;
    public static final int FONT_FAMILY_MONOSPACED_SANS_SERIF = 1;
    public static final int FONT_FAMILY_MONOSPACED_SERIF = 3;
    public static final int FONT_FAMILY_SANS_SERIF = 0;
    public static final int FONT_FAMILY_SERIF = 2;
    public static final int FONT_FAMILY_SMALL_CAPITALS = 6;
    public static final int FONT_FAMILY_UNSPECIFIED = -1;
    public static final int FONT_STYLE_BOLD = 1;
    public static final int FONT_STYLE_BOLD_ITALIC = 3;
    public static final int FONT_STYLE_ITALIC = 2;
    public static final int FONT_STYLE_NORMAL = 0;
    public static final int FONT_STYLE_UNSPECIFIED = -1;
    public static final int WINDOW_TYPE_NONE = 0;
    public static final int WINDOW_TYPE_NORMAL = 1;
    public static final int WINDOW_TYPE_ROUNDED = 2;
    public static final int WINDOW_TYPE_UNSPECIFIED = -1;
    private JSONObject zzRJ;
    private float zzTc;
    private int zzTd;
    private int zzTe;
    private int zzTf;
    private int zzTg;
    private int zzTh;
    private int zzTi;
    private String zzTj;
    private int zzTk;
    private int zzTl;
    private int zzvc;

    public TextTrackStyle() {
        clear();
    }

    private void clear() {
        this.zzTc = DEFAULT_FONT_SCALE;
        this.zzTd = WINDOW_TYPE_NONE;
        this.zzvc = WINDOW_TYPE_NONE;
        this.zzTe = WINDOW_TYPE_UNSPECIFIED;
        this.zzTf = WINDOW_TYPE_NONE;
        this.zzTg = WINDOW_TYPE_UNSPECIFIED;
        this.zzTh = WINDOW_TYPE_NONE;
        this.zzTi = WINDOW_TYPE_NONE;
        this.zzTj = null;
        this.zzTk = WINDOW_TYPE_UNSPECIFIED;
        this.zzTl = WINDOW_TYPE_UNSPECIFIED;
        this.zzRJ = null;
    }

    public static TextTrackStyle fromSystemSettings(Context context) {
        TextTrackStyle textTrackStyle = new TextTrackStyle();
        if (!zzlk.zzoX()) {
            return textTrackStyle;
        }
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService("captioning");
        textTrackStyle.setFontScale(captioningManager.getFontScale());
        CaptionStyle userStyle = captioningManager.getUserStyle();
        textTrackStyle.setBackgroundColor(userStyle.backgroundColor);
        textTrackStyle.setForegroundColor(userStyle.foregroundColor);
        switch (userStyle.edgeType) {
            case WINDOW_TYPE_NORMAL /*1*/:
                textTrackStyle.setEdgeType(WINDOW_TYPE_NORMAL);
                break;
            case WINDOW_TYPE_ROUNDED /*2*/:
                textTrackStyle.setEdgeType(WINDOW_TYPE_ROUNDED);
                break;
            default:
                textTrackStyle.setEdgeType(WINDOW_TYPE_NONE);
                break;
        }
        textTrackStyle.setEdgeColor(userStyle.edgeColor);
        Typeface typeface = userStyle.getTypeface();
        if (typeface != null) {
            if (Typeface.MONOSPACE.equals(typeface)) {
                textTrackStyle.setFontGenericFamily(WINDOW_TYPE_NORMAL);
            } else if (Typeface.SANS_SERIF.equals(typeface)) {
                textTrackStyle.setFontGenericFamily(WINDOW_TYPE_NONE);
            } else if (Typeface.SERIF.equals(typeface)) {
                textTrackStyle.setFontGenericFamily(WINDOW_TYPE_ROUNDED);
            } else {
                textTrackStyle.setFontGenericFamily(WINDOW_TYPE_NONE);
            }
            boolean isBold = typeface.isBold();
            boolean isItalic = typeface.isItalic();
            if (isBold && isItalic) {
                textTrackStyle.setFontStyle(FONT_STYLE_BOLD_ITALIC);
            } else if (isBold) {
                textTrackStyle.setFontStyle(WINDOW_TYPE_NORMAL);
            } else if (isItalic) {
                textTrackStyle.setFontStyle(WINDOW_TYPE_ROUNDED);
            } else {
                textTrackStyle.setFontStyle(WINDOW_TYPE_NONE);
            }
        }
        return textTrackStyle;
    }

    private String zzG(int i) {
        Object[] objArr = new Object[FONT_FAMILY_CASUAL];
        objArr[WINDOW_TYPE_NONE] = Integer.valueOf(Color.red(i));
        objArr[WINDOW_TYPE_NORMAL] = Integer.valueOf(Color.green(i));
        objArr[WINDOW_TYPE_ROUNDED] = Integer.valueOf(Color.blue(i));
        objArr[FONT_STYLE_BOLD_ITALIC] = Integer.valueOf(Color.alpha(i));
        return String.format("#%02X%02X%02X%02X", objArr);
    }

    private int zzbz(String str) {
        int i = WINDOW_TYPE_NONE;
        if (str != null && str.length() == 9 && str.charAt(i) == '#') {
            try {
                i = Color.argb(Integer.parseInt(str.substring(7, 9), 16), Integer.parseInt(str.substring(WINDOW_TYPE_NORMAL, FONT_STYLE_BOLD_ITALIC), 16), Integer.parseInt(str.substring(FONT_STYLE_BOLD_ITALIC, FONT_FAMILY_CURSIVE), 16), Integer.parseInt(str.substring(FONT_FAMILY_CURSIVE, 7), 16));
            } catch (NumberFormatException e) {
            }
        }
        return i;
    }

    public boolean equals(Object other) {
        boolean z = true;
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextTrackStyle)) {
            return false;
        }
        TextTrackStyle textTrackStyle = (TextTrackStyle) other;
        if ((this.zzRJ == null ? WINDOW_TYPE_NORMAL : false) != (textTrackStyle.zzRJ == null ? WINDOW_TYPE_NORMAL : false)) {
            return false;
        }
        if (this.zzRJ != null && textTrackStyle.zzRJ != null && !zzlh.zzd(this.zzRJ, textTrackStyle.zzRJ)) {
            return false;
        }
        if (!(this.zzTc == textTrackStyle.zzTc && this.zzTd == textTrackStyle.zzTd && this.zzvc == textTrackStyle.zzvc && this.zzTe == textTrackStyle.zzTe && this.zzTf == textTrackStyle.zzTf && this.zzTg == textTrackStyle.zzTg && this.zzTi == textTrackStyle.zzTi && zzf.zza(this.zzTj, textTrackStyle.zzTj) && this.zzTk == textTrackStyle.zzTk && this.zzTl == textTrackStyle.zzTl)) {
            z = false;
        }
        return z;
    }

    public int getBackgroundColor() {
        return this.zzvc;
    }

    public JSONObject getCustomData() {
        return this.zzRJ;
    }

    public int getEdgeColor() {
        return this.zzTf;
    }

    public int getEdgeType() {
        return this.zzTe;
    }

    public String getFontFamily() {
        return this.zzTj;
    }

    public int getFontGenericFamily() {
        return this.zzTk;
    }

    public float getFontScale() {
        return this.zzTc;
    }

    public int getFontStyle() {
        return this.zzTl;
    }

    public int getForegroundColor() {
        return this.zzTd;
    }

    public int getWindowColor() {
        return this.zzTh;
    }

    public int getWindowCornerRadius() {
        return this.zzTi;
    }

    public int getWindowType() {
        return this.zzTg;
    }

    public int hashCode() {
        return zzt.hashCode(Float.valueOf(this.zzTc), Integer.valueOf(this.zzTd), Integer.valueOf(this.zzvc), Integer.valueOf(this.zzTe), Integer.valueOf(this.zzTf), Integer.valueOf(this.zzTg), Integer.valueOf(this.zzTh), Integer.valueOf(this.zzTi), this.zzTj, Integer.valueOf(this.zzTk), Integer.valueOf(this.zzTl), this.zzRJ);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.zzvc = backgroundColor;
    }

    public void setCustomData(JSONObject customData) {
        this.zzRJ = customData;
    }

    public void setEdgeColor(int edgeColor) {
        this.zzTf = edgeColor;
    }

    public void setEdgeType(int edgeType) {
        if (edgeType < 0 || edgeType > FONT_FAMILY_CASUAL) {
            throw new IllegalArgumentException("invalid edgeType");
        }
        this.zzTe = edgeType;
    }

    public void setFontFamily(String fontFamily) {
        this.zzTj = fontFamily;
    }

    public void setFontGenericFamily(int fontGenericFamily) {
        if (fontGenericFamily < 0 || fontGenericFamily > FONT_FAMILY_SMALL_CAPITALS) {
            throw new IllegalArgumentException("invalid fontGenericFamily");
        }
        this.zzTk = fontGenericFamily;
    }

    public void setFontScale(float fontScale) {
        this.zzTc = fontScale;
    }

    public void setFontStyle(int fontStyle) {
        if (fontStyle < 0 || fontStyle > FONT_STYLE_BOLD_ITALIC) {
            throw new IllegalArgumentException("invalid fontStyle");
        }
        this.zzTl = fontStyle;
    }

    public void setForegroundColor(int foregroundColor) {
        this.zzTd = foregroundColor;
    }

    public void setWindowColor(int windowColor) {
        this.zzTh = windowColor;
    }

    public void setWindowCornerRadius(int windowCornerRadius) {
        if (windowCornerRadius < 0) {
            throw new IllegalArgumentException("invalid windowCornerRadius");
        }
        this.zzTi = windowCornerRadius;
    }

    public void setWindowType(int windowType) {
        if (windowType < 0 || windowType > WINDOW_TYPE_ROUNDED) {
            throw new IllegalArgumentException("invalid windowType");
        }
        this.zzTg = windowType;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("fontScale", (double) this.zzTc);
            if (this.zzTd != 0) {
                jSONObject.put("foregroundColor", zzG(this.zzTd));
            }
            if (this.zzvc != 0) {
                jSONObject.put("backgroundColor", zzG(this.zzvc));
            }
            switch (this.zzTe) {
                case WINDOW_TYPE_NONE /*0*/:
                    jSONObject.put("edgeType", "NONE");
                    break;
                case WINDOW_TYPE_NORMAL /*1*/:
                    jSONObject.put("edgeType", "OUTLINE");
                    break;
                case WINDOW_TYPE_ROUNDED /*2*/:
                    jSONObject.put("edgeType", "DROP_SHADOW");
                    break;
                case FONT_STYLE_BOLD_ITALIC /*3*/:
                    jSONObject.put("edgeType", "RAISED");
                    break;
                case FONT_FAMILY_CASUAL /*4*/:
                    jSONObject.put("edgeType", "DEPRESSED");
                    break;
            }
            if (this.zzTf != 0) {
                jSONObject.put("edgeColor", zzG(this.zzTf));
            }
            switch (this.zzTg) {
                case WINDOW_TYPE_NONE /*0*/:
                    jSONObject.put("windowType", "NONE");
                    break;
                case WINDOW_TYPE_NORMAL /*1*/:
                    jSONObject.put("windowType", "NORMAL");
                    break;
                case WINDOW_TYPE_ROUNDED /*2*/:
                    jSONObject.put("windowType", "ROUNDED_CORNERS");
                    break;
            }
            if (this.zzTh != 0) {
                jSONObject.put("windowColor", zzG(this.zzTh));
            }
            if (this.zzTg == WINDOW_TYPE_ROUNDED) {
                jSONObject.put("windowRoundedCornerRadius", this.zzTi);
            }
            if (this.zzTj != null) {
                jSONObject.put("fontFamily", this.zzTj);
            }
            switch (this.zzTk) {
                case WINDOW_TYPE_NONE /*0*/:
                    jSONObject.put("fontGenericFamily", "SANS_SERIF");
                    break;
                case WINDOW_TYPE_NORMAL /*1*/:
                    jSONObject.put("fontGenericFamily", "MONOSPACED_SANS_SERIF");
                    break;
                case WINDOW_TYPE_ROUNDED /*2*/:
                    jSONObject.put("fontGenericFamily", "SERIF");
                    break;
                case FONT_STYLE_BOLD_ITALIC /*3*/:
                    jSONObject.put("fontGenericFamily", "MONOSPACED_SERIF");
                    break;
                case FONT_FAMILY_CASUAL /*4*/:
                    jSONObject.put("fontGenericFamily", "CASUAL");
                    break;
                case FONT_FAMILY_CURSIVE /*5*/:
                    jSONObject.put("fontGenericFamily", "CURSIVE");
                    break;
                case FONT_FAMILY_SMALL_CAPITALS /*6*/:
                    jSONObject.put("fontGenericFamily", "SMALL_CAPITALS");
                    break;
            }
            switch (this.zzTl) {
                case WINDOW_TYPE_NONE /*0*/:
                    jSONObject.put("fontStyle", "NORMAL");
                    break;
                case WINDOW_TYPE_NORMAL /*1*/:
                    jSONObject.put("fontStyle", "BOLD");
                    break;
                case WINDOW_TYPE_ROUNDED /*2*/:
                    jSONObject.put("fontStyle", "ITALIC");
                    break;
                case FONT_STYLE_BOLD_ITALIC /*3*/:
                    jSONObject.put("fontStyle", "BOLD_ITALIC");
                    break;
            }
            if (this.zzRJ != null) {
                jSONObject.put("customData", this.zzRJ);
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public void zzf(JSONObject jSONObject) throws JSONException {
        String string;
        clear();
        this.zzTc = (float) jSONObject.optDouble("fontScale", 1.0d);
        this.zzTd = zzbz(jSONObject.optString("foregroundColor"));
        this.zzvc = zzbz(jSONObject.optString("backgroundColor"));
        if (jSONObject.has("edgeType")) {
            string = jSONObject.getString("edgeType");
            if ("NONE".equals(string)) {
                this.zzTe = WINDOW_TYPE_NONE;
            } else if ("OUTLINE".equals(string)) {
                this.zzTe = WINDOW_TYPE_NORMAL;
            } else if ("DROP_SHADOW".equals(string)) {
                this.zzTe = WINDOW_TYPE_ROUNDED;
            } else if ("RAISED".equals(string)) {
                this.zzTe = FONT_STYLE_BOLD_ITALIC;
            } else if ("DEPRESSED".equals(string)) {
                this.zzTe = FONT_FAMILY_CASUAL;
            }
        }
        this.zzTf = zzbz(jSONObject.optString("edgeColor"));
        if (jSONObject.has("windowType")) {
            string = jSONObject.getString("windowType");
            if ("NONE".equals(string)) {
                this.zzTg = WINDOW_TYPE_NONE;
            } else if ("NORMAL".equals(string)) {
                this.zzTg = WINDOW_TYPE_NORMAL;
            } else if ("ROUNDED_CORNERS".equals(string)) {
                this.zzTg = WINDOW_TYPE_ROUNDED;
            }
        }
        this.zzTh = zzbz(jSONObject.optString("windowColor"));
        if (this.zzTg == WINDOW_TYPE_ROUNDED) {
            this.zzTi = jSONObject.optInt("windowRoundedCornerRadius", WINDOW_TYPE_NONE);
        }
        this.zzTj = jSONObject.optString("fontFamily", null);
        if (jSONObject.has("fontGenericFamily")) {
            string = jSONObject.getString("fontGenericFamily");
            if ("SANS_SERIF".equals(string)) {
                this.zzTk = WINDOW_TYPE_NONE;
            } else if ("MONOSPACED_SANS_SERIF".equals(string)) {
                this.zzTk = WINDOW_TYPE_NORMAL;
            } else if ("SERIF".equals(string)) {
                this.zzTk = WINDOW_TYPE_ROUNDED;
            } else if ("MONOSPACED_SERIF".equals(string)) {
                this.zzTk = FONT_STYLE_BOLD_ITALIC;
            } else if ("CASUAL".equals(string)) {
                this.zzTk = FONT_FAMILY_CASUAL;
            } else if ("CURSIVE".equals(string)) {
                this.zzTk = FONT_FAMILY_CURSIVE;
            } else if ("SMALL_CAPITALS".equals(string)) {
                this.zzTk = FONT_FAMILY_SMALL_CAPITALS;
            }
        }
        if (jSONObject.has("fontStyle")) {
            string = jSONObject.getString("fontStyle");
            if ("NORMAL".equals(string)) {
                this.zzTl = WINDOW_TYPE_NONE;
            } else if ("BOLD".equals(string)) {
                this.zzTl = WINDOW_TYPE_NORMAL;
            } else if ("ITALIC".equals(string)) {
                this.zzTl = WINDOW_TYPE_ROUNDED;
            } else if ("BOLD_ITALIC".equals(string)) {
                this.zzTl = FONT_STYLE_BOLD_ITALIC;
            }
        }
        this.zzRJ = jSONObject.optJSONObject("customData");
    }
}
