package com.badlogic.gdx.math;

public class GridPoint2 {
    public int f59x;
    public int f60y;

    public GridPoint2(int x, int y) {
        this.f59x = x;
        this.f60y = y;
    }

    public GridPoint2(GridPoint2 point) {
        this.f59x = point.f59x;
        this.f60y = point.f60y;
    }

    public GridPoint2 set(GridPoint2 point) {
        this.f59x = point.f59x;
        this.f60y = point.f60y;
        return this;
    }

    public GridPoint2 set(int x, int y) {
        this.f59x = x;
        this.f60y = y;
        return this;
    }
}
