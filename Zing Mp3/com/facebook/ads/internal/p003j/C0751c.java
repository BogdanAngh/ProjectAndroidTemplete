package com.facebook.ads.internal.p003j;

/* renamed from: com.facebook.ads.internal.j.c */
public enum C0751c {
    UNKNOWN(0),
    IS_VIEWABLE(1),
    AD_IS_NULL(2),
    INVALID_PARENT(3),
    INVALID_WINDOW(4),
    AD_IS_NOT_VISIBLE(5),
    INVALID_DIMENSIONS(6),
    AD_IS_TRANSPARENT(7),
    AD_IS_OBSTRUCTED(8),
    AD_OFFSCREEN_HORIZONTALLY(9),
    AD_OFFSCREEN_TOP(10),
    AD_OFFSCREEN_BOTTOM(11),
    SCREEN_OFF(12),
    AD_INSUFFICIENT_VISIBLE_AREA(13);
    
    private final int f1288o;

    private C0751c(int i) {
        this.f1288o = i;
    }

    public int m1498a() {
        return this.f1288o;
    }
}
