package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;
import com.esotericsoftware.tablelayout.Cell;
import com.esotericsoftware.tablelayout.Toolkit;

public class TableToolkit extends Toolkit<Actor, Table, TableLayout> {
    static Pool<Cell> cellPool;
    static boolean drawDebug;

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.TableToolkit.1 */
    static class C03781 extends Pool {
        C03781() {
        }

        protected Cell newObject() {
            return new Cell();
        }
    }

    static class DebugRect extends Rectangle {
        final Debug type;

        public DebugRect(Debug type, float x, float y, float width, float height) {
            super(x, y, width, height);
            this.type = type;
        }
    }

    static {
        cellPool = new C03781();
    }

    public Cell obtainCell(TableLayout layout) {
        Cell cell = (Cell) cellPool.obtain();
        cell.setLayout(layout);
        return cell;
    }

    public void freeCell(Cell cell) {
        cell.free();
        cellPool.free(cell);
    }

    public void addChild(Actor parent, Actor child) {
        child.remove();
        ((Group) parent).addActor(child);
    }

    public void removeChild(Actor parent, Actor child) {
        ((Group) parent).removeActor(child);
    }

    public float getMinWidth(Actor actor) {
        if (actor instanceof Layout) {
            return ((Layout) actor).getMinWidth();
        }
        return actor.getWidth();
    }

    public float getMinHeight(Actor actor) {
        if (actor instanceof Layout) {
            return ((Layout) actor).getMinHeight();
        }
        return actor.getHeight();
    }

    public float getPrefWidth(Actor actor) {
        if (actor instanceof Layout) {
            return ((Layout) actor).getPrefWidth();
        }
        return actor.getWidth();
    }

    public float getPrefHeight(Actor actor) {
        if (actor instanceof Layout) {
            return ((Layout) actor).getPrefHeight();
        }
        return actor.getHeight();
    }

    public float getMaxWidth(Actor actor) {
        if (actor instanceof Layout) {
            return ((Layout) actor).getMaxWidth();
        }
        return 0.0f;
    }

    public float getMaxHeight(Actor actor) {
        if (actor instanceof Layout) {
            return ((Layout) actor).getMaxHeight();
        }
        return 0.0f;
    }

    public float getWidth(Actor widget) {
        return widget.getWidth();
    }

    public float getHeight(Actor widget) {
        return widget.getHeight();
    }

    public void clearDebugRectangles(TableLayout layout) {
        if (layout.debugRects != null) {
            layout.debugRects.clear();
        }
    }

    public void addDebugRectangle(TableLayout layout, Debug type, float x, float y, float w, float h) {
        drawDebug = true;
        if (layout.debugRects == null) {
            layout.debugRects = new Array();
        }
        layout.debugRects.add(new DebugRect(type, x, ((Table) layout.getTable()).getHeight() - y, w, h));
    }
}
