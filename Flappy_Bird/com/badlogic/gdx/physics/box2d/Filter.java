package com.badlogic.gdx.physics.box2d;

public class Filter {
    public short categoryBits;
    public short groupIndex;
    public short maskBits;

    public Filter() {
        this.categoryBits = (short) 1;
        this.maskBits = (short) -1;
        this.groupIndex = (short) 0;
    }
}
