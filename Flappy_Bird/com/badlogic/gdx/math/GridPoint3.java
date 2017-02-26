package com.badlogic.gdx.math;

public class GridPoint3 {
    public int f61x;
    public int f62y;
    public int f63z;

    public GridPoint3(int x, int y, int z) {
        this.f61x = x;
        this.f62y = y;
        this.f63z = z;
    }

    public GridPoint3(GridPoint3 point) {
        this.f61x = point.f61x;
        this.f62y = point.f62y;
        this.f63z = point.f63z;
    }

    public GridPoint3 set(GridPoint3 point) {
        this.f61x = point.f61x;
        this.f62y = point.f62y;
        this.f63z = point.f63z;
        return this;
    }

    public GridPoint3 set(int x, int y, int z) {
        this.f61x = x;
        this.f62y = y;
        this.f63z = z;
        return this;
    }
}
