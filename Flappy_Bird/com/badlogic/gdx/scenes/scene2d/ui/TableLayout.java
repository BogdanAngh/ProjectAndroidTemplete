package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer10;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.tablelayout.BaseTableLayout;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;
import com.esotericsoftware.tablelayout.Cell;
import com.esotericsoftware.tablelayout.Toolkit;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.List;

class TableLayout extends BaseTableLayout<Actor, Table, TableLayout, TableToolkit> {
    Array<DebugRect> debugRects;
    private ImmediateModeRenderer debugRenderer;
    boolean round;

    public TableLayout() {
        super((TableToolkit) Toolkit.instance);
        this.round = true;
    }

    public void layout() {
        int n;
        int i;
        Table table = (Table) getTable();
        float width = table.getWidth();
        float height = table.getHeight();
        super.layout(0.0f, 0.0f, width, height);
        List<Cell> cells = getCells();
        Cell c;
        float widgetHeight;
        float widgetY;
        Actor actor;
        if (this.round) {
            n = cells.size();
            for (i = 0; i < n; i++) {
                c = (Cell) cells.get(i);
                if (!c.getIgnore()) {
                    float widgetWidth = (float) Math.round(c.getWidgetWidth());
                    widgetHeight = (float) Math.round(c.getWidgetHeight());
                    float widgetX = (float) Math.round(c.getWidgetX());
                    widgetY = (height - ((float) Math.round(c.getWidgetY()))) - widgetHeight;
                    c.setWidgetBounds(widgetX, widgetY, widgetWidth, widgetHeight);
                    actor = (Actor) c.getWidget();
                    if (actor != null) {
                        actor.setBounds(widgetX, widgetY, widgetWidth, widgetHeight);
                    }
                }
            }
        } else {
            n = cells.size();
            for (i = 0; i < n; i++) {
                c = (Cell) cells.get(i);
                if (!c.getIgnore()) {
                    widgetHeight = c.getWidgetHeight();
                    widgetY = (height - c.getWidgetY()) - widgetHeight;
                    c.setWidgetY(widgetY);
                    actor = (Actor) c.getWidget();
                    if (actor != null) {
                        actor.setBounds(c.getWidgetX(), widgetY, c.getWidgetWidth(), widgetHeight);
                    }
                }
            }
        }
        Array<Actor> children = table.getChildren();
        n = children.size;
        for (i = 0; i < n; i++) {
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                ((Layout) child).validate();
            }
        }
    }

    public void invalidateHierarchy() {
        super.invalidate();
        ((Table) getTable()).invalidateHierarchy();
    }

    public void drawDebug(SpriteBatch batch) {
        if (getDebug() != Debug.none && this.debugRects != null) {
            if (this.debugRenderer == null) {
                if (Gdx.graphics.isGL20Available()) {
                    this.debugRenderer = new ImmediateModeRenderer20(64, false, true, 0);
                } else {
                    this.debugRenderer = new ImmediateModeRenderer10(64);
                }
            }
            float x = 0.0f;
            float y = 0.0f;
            for (Actor parent = (Actor) getTable(); parent != null; parent = parent.getParent()) {
                if (parent instanceof Group) {
                    x += parent.getX();
                    y += parent.getY();
                }
            }
            this.debugRenderer.begin(batch.getProjectionMatrix(), 1);
            int n = this.debugRects.size;
            for (int i = 0; i < n; i++) {
                DebugRect rect = (DebugRect) this.debugRects.get(i);
                float x1 = x + rect.x;
                float y1 = (rect.y + y) - rect.height;
                float x2 = x1 + rect.width;
                float y2 = y1 + rect.height;
                float r = rect.type == Debug.cell ? TextTrackStyle.DEFAULT_FONT_SCALE : 0.0f;
                float g = rect.type == Debug.widget ? TextTrackStyle.DEFAULT_FONT_SCALE : 0.0f;
                float b = rect.type == Debug.table ? TextTrackStyle.DEFAULT_FONT_SCALE : 0.0f;
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x1, y1, 0.0f);
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x1, y2, 0.0f);
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x1, y2, 0.0f);
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x2, y2, 0.0f);
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x2, y2, 0.0f);
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x2, y1, 0.0f);
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x2, y1, 0.0f);
                this.debugRenderer.color(r, g, b, TextTrackStyle.DEFAULT_FONT_SCALE);
                this.debugRenderer.vertex(x1, y1, 0.0f);
                if (this.debugRenderer.getNumVertices() == 64) {
                    this.debugRenderer.end();
                    this.debugRenderer.begin(batch.getProjectionMatrix(), 1);
                }
            }
            this.debugRenderer.end();
        }
    }
}
