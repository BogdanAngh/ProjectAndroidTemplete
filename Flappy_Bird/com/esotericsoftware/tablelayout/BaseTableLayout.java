package com.esotericsoftware.tablelayout;

import com.esotericsoftware.tablelayout.Value.FixedValue;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTableLayout<C, T extends C, L extends BaseTableLayout, K extends Toolkit<C, T, L>> {
    public static final int BOTTOM = 4;
    public static final int CENTER = 1;
    public static final int LEFT = 8;
    public static final int RIGHT = 16;
    public static final int TOP = 2;
    int align;
    private final Cell cellDefaults;
    private final ArrayList<Cell> cells;
    private final ArrayList<Cell> columnDefaults;
    private float[] columnMinWidth;
    private float[] columnPrefWidth;
    private float[] columnWeightedWidth;
    private float[] columnWidth;
    private int columns;
    Debug debug;
    private float[] expandHeight;
    private float[] expandWidth;
    Value padBottom;
    Value padLeft;
    Value padRight;
    Value padTop;
    private Cell rowDefaults;
    private float[] rowHeight;
    private float[] rowMinHeight;
    private float[] rowPrefHeight;
    private float[] rowWeightedHeight;
    private int rows;
    private boolean sizeInvalid;
    T table;
    private float tableMinHeight;
    private float tableMinWidth;
    private float tablePrefHeight;
    private float tablePrefWidth;
    K toolkit;

    public enum Debug {
        none,
        all,
        table,
        cell,
        widget
    }

    public abstract void invalidateHierarchy();

    public BaseTableLayout(K toolkit) {
        this.cells = new ArrayList(BOTTOM);
        this.columnDefaults = new ArrayList(TOP);
        this.sizeInvalid = true;
        this.align = CENTER;
        this.debug = Debug.none;
        this.toolkit = toolkit;
        this.cellDefaults = toolkit.obtainCell(this);
        this.cellDefaults.defaults();
    }

    public void invalidate() {
        this.sizeInvalid = true;
    }

    public Cell<C> add(C widget) {
        Cell cell = this.toolkit.obtainCell(this);
        cell.widget = widget;
        if (this.cells.size() > 0) {
            Cell lastCell = (Cell) this.cells.get(this.cells.size() - 1);
            if (lastCell.endRow) {
                cell.column = 0;
                cell.row = lastCell.row + CENTER;
            } else {
                cell.column = lastCell.column + lastCell.colspan.intValue();
                cell.row = lastCell.row;
            }
            if (cell.row > 0) {
                loop0:
                for (int i = this.cells.size() - 1; i >= 0; i--) {
                    Cell other = (Cell) this.cells.get(i);
                    int column = other.column;
                    int nn = column + other.colspan.intValue();
                    while (column < nn) {
                        if (column == cell.column) {
                            cell.cellAboveIndex = i;
                            break loop0;
                        }
                        column += CENTER;
                    }
                }
            }
        } else {
            cell.column = 0;
            cell.row = 0;
        }
        this.cells.add(cell);
        cell.set(this.cellDefaults);
        if (cell.column < this.columnDefaults.size()) {
            Cell columnCell = (Cell) this.columnDefaults.get(cell.column);
            if (columnCell != null) {
                cell.merge(columnCell);
            }
        }
        cell.merge(this.rowDefaults);
        if (widget != null) {
            this.toolkit.addChild(this.table, widget);
        }
        return cell;
    }

    public Cell row() {
        if (this.cells.size() > 0) {
            endRow();
            invalidate();
        }
        if (this.rowDefaults != null) {
            this.toolkit.freeCell(this.rowDefaults);
        }
        this.rowDefaults = this.toolkit.obtainCell(this);
        this.rowDefaults.clear();
        return this.rowDefaults;
    }

    private void endRow() {
        int rowColumns = 0;
        for (int i = this.cells.size() - 1; i >= 0; i--) {
            Cell cell = (Cell) this.cells.get(i);
            if (cell.endRow) {
                break;
            }
            rowColumns += cell.colspan.intValue();
        }
        this.columns = Math.max(this.columns, rowColumns);
        this.rows += CENTER;
        ((Cell) this.cells.get(this.cells.size() - 1)).endRow = true;
    }

    public Cell columnDefaults(int column) {
        Cell cell;
        if (this.columnDefaults.size() > column) {
            cell = (Cell) this.columnDefaults.get(column);
        } else {
            cell = null;
        }
        if (cell == null) {
            cell = this.toolkit.obtainCell(this);
            cell.clear();
            if (column >= this.columnDefaults.size()) {
                for (int i = this.columnDefaults.size(); i < column; i += CENTER) {
                    this.columnDefaults.add(null);
                }
                this.columnDefaults.add(cell);
            } else {
                this.columnDefaults.set(column, cell);
            }
        }
        return cell;
    }

    public void reset() {
        clear();
        this.padTop = null;
        this.padLeft = null;
        this.padBottom = null;
        this.padRight = null;
        this.align = CENTER;
        if (this.debug != Debug.none) {
            this.toolkit.clearDebugRectangles(this);
        }
        this.debug = Debug.none;
        this.cellDefaults.defaults();
        int n = this.columnDefaults.size();
        for (int i = 0; i < n; i += CENTER) {
            Cell columnCell = (Cell) this.columnDefaults.get(i);
            if (columnCell != null) {
                this.toolkit.freeCell(columnCell);
            }
        }
        this.columnDefaults.clear();
    }

    public void clear() {
        for (int i = this.cells.size() - 1; i >= 0; i--) {
            Cell cell = (Cell) this.cells.get(i);
            Object widget = cell.widget;
            if (widget != null) {
                this.toolkit.removeChild(this.table, widget);
            }
            this.toolkit.freeCell(cell);
        }
        this.cells.clear();
        this.rows = 0;
        this.columns = 0;
        if (this.rowDefaults != null) {
            this.toolkit.freeCell(this.rowDefaults);
        }
        this.rowDefaults = null;
        invalidate();
    }

    public Cell getCell(C widget) {
        int n = this.cells.size();
        for (int i = 0; i < n; i += CENTER) {
            Cell c = (Cell) this.cells.get(i);
            if (c.widget == widget) {
                return c;
            }
        }
        return null;
    }

    public List<Cell> getCells() {
        return this.cells;
    }

    public void setToolkit(K toolkit) {
        this.toolkit = toolkit;
    }

    public T getTable() {
        return this.table;
    }

    public void setTable(T table) {
        this.table = table;
    }

    public float getMinWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tableMinWidth;
    }

    public float getMinHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tableMinHeight;
    }

    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tablePrefWidth;
    }

    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.tablePrefHeight;
    }

    public Cell defaults() {
        return this.cellDefaults;
    }

    public L pad(Value pad) {
        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        this.sizeInvalid = true;
        return this;
    }

    public L pad(Value top, Value left, Value bottom, Value right) {
        this.padTop = top;
        this.padLeft = left;
        this.padBottom = bottom;
        this.padRight = right;
        this.sizeInvalid = true;
        return this;
    }

    public L padTop(Value padTop) {
        this.padTop = padTop;
        this.sizeInvalid = true;
        return this;
    }

    public L padLeft(Value padLeft) {
        this.padLeft = padLeft;
        this.sizeInvalid = true;
        return this;
    }

    public L padBottom(Value padBottom) {
        this.padBottom = padBottom;
        this.sizeInvalid = true;
        return this;
    }

    public L padRight(Value padRight) {
        this.padRight = padRight;
        this.sizeInvalid = true;
        return this;
    }

    public L pad(float pad) {
        this.padTop = new FixedValue(pad);
        this.padLeft = new FixedValue(pad);
        this.padBottom = new FixedValue(pad);
        this.padRight = new FixedValue(pad);
        this.sizeInvalid = true;
        return this;
    }

    public L pad(float top, float left, float bottom, float right) {
        this.padTop = new FixedValue(top);
        this.padLeft = new FixedValue(left);
        this.padBottom = new FixedValue(bottom);
        this.padRight = new FixedValue(right);
        this.sizeInvalid = true;
        return this;
    }

    public L padTop(float padTop) {
        this.padTop = new FixedValue(padTop);
        this.sizeInvalid = true;
        return this;
    }

    public L padLeft(float padLeft) {
        this.padLeft = new FixedValue(padLeft);
        this.sizeInvalid = true;
        return this;
    }

    public L padBottom(float padBottom) {
        this.padBottom = new FixedValue(padBottom);
        this.sizeInvalid = true;
        return this;
    }

    public L padRight(float padRight) {
        this.padRight = new FixedValue(padRight);
        this.sizeInvalid = true;
        return this;
    }

    public L align(int align) {
        this.align = align;
        return this;
    }

    public L center() {
        this.align = CENTER;
        return this;
    }

    public L top() {
        this.align |= TOP;
        this.align &= -5;
        return this;
    }

    public L left() {
        this.align |= LEFT;
        this.align &= -17;
        return this;
    }

    public L bottom() {
        this.align |= BOTTOM;
        this.align &= -3;
        return this;
    }

    public L right() {
        this.align |= RIGHT;
        this.align &= -9;
        return this;
    }

    public L debug() {
        this.debug = Debug.all;
        invalidate();
        return this;
    }

    public L debugTable() {
        this.debug = Debug.table;
        invalidate();
        return this;
    }

    public L debugCell() {
        this.debug = Debug.cell;
        invalidate();
        return this;
    }

    public L debugWidget() {
        this.debug = Debug.widget;
        invalidate();
        return this;
    }

    public L debug(Debug debug) {
        this.debug = debug;
        if (debug == Debug.none) {
            this.toolkit.clearDebugRectangles(this);
        } else {
            invalidate();
        }
        return this;
    }

    public Debug getDebug() {
        return this.debug;
    }

    public Value getPadTopValue() {
        return this.padTop;
    }

    public float getPadTop() {
        return this.padTop == null ? 0.0f : this.padTop.height(this.table);
    }

    public Value getPadLeftValue() {
        return this.padLeft;
    }

    public float getPadLeft() {
        return this.padLeft == null ? 0.0f : this.padLeft.width(this.table);
    }

    public Value getPadBottomValue() {
        return this.padBottom;
    }

    public float getPadBottom() {
        return this.padBottom == null ? 0.0f : this.padBottom.height(this.table);
    }

    public Value getPadRightValue() {
        return this.padRight;
    }

    public float getPadRight() {
        return this.padRight == null ? 0.0f : this.padRight.width(this.table);
    }

    public int getAlign() {
        return this.align;
    }

    public int getRow(float y) {
        int row = 0;
        y += m1h(this.padTop);
        int n = this.cells.size();
        if (n == 0) {
            return -1;
        }
        if (n == CENTER) {
            return 0;
        }
        int i;
        int i2;
        Cell c;
        if (((Cell) this.cells.get(0)).widgetY < ((Cell) this.cells.get(CENTER)).widgetY) {
            i = 0;
            while (i < n) {
                i2 = i + CENTER;
                c = (Cell) this.cells.get(i);
                if (c.getIgnore()) {
                    i = i2;
                } else if (c.widgetY + c.computedPadTop > y) {
                    break;
                } else {
                    if (c.endRow) {
                        row += CENTER;
                    }
                    i = i2;
                }
            }
            return row - 1;
        }
        i = 0;
        while (i < n) {
            i2 = i + CENTER;
            c = (Cell) this.cells.get(i);
            if (c.getIgnore()) {
                i = i2;
            } else if (c.widgetY + c.computedPadTop < y) {
                break;
            } else {
                if (c.endRow) {
                    row += CENTER;
                }
                i = i2;
            }
        }
        i2 = i;
        return row;
    }

    private float[] ensureSize(float[] array, int size) {
        if (array == null || array.length < size) {
            return new float[size];
        }
        int n = array.length;
        for (int i = 0; i < n; i += CENTER) {
            array[i] = 0.0f;
        }
        return array;
    }

    private float m3w(Value value) {
        return value == null ? 0.0f : value.width(this.table);
    }

    private float m1h(Value value) {
        return value == null ? 0.0f : value.height(this.table);
    }

    private float m4w(Value value, Cell cell) {
        return value == null ? 0.0f : value.width(cell);
    }

    private float m2h(Value value, Cell cell) {
        return value == null ? 0.0f : value.height(cell);
    }

    private void computeSize() {
        int i;
        this.sizeInvalid = false;
        ArrayList<Cell> cells = this.cells;
        if (cells.size() > 0) {
            if (!((Cell) cells.get(cells.size() - 1)).endRow) {
                endRow();
            }
        }
        this.columnMinWidth = ensureSize(this.columnMinWidth, this.columns);
        this.rowMinHeight = ensureSize(this.rowMinHeight, this.rows);
        this.columnPrefWidth = ensureSize(this.columnPrefWidth, this.columns);
        this.rowPrefHeight = ensureSize(this.rowPrefHeight, this.rows);
        this.columnWidth = ensureSize(this.columnWidth, this.columns);
        this.rowHeight = ensureSize(this.rowHeight, this.rows);
        this.expandWidth = ensureSize(this.expandWidth, this.columns);
        this.expandHeight = ensureSize(this.expandHeight, this.rows);
        float spaceRightLast = 0.0f;
        int n = cells.size();
        for (i = 0; i < n; i += CENTER) {
            float f;
            float prefWidth;
            float minWidth;
            float maxWidth;
            Cell c = (Cell) cells.get(i);
            if (!c.ignore.booleanValue()) {
                if (c.expandY.intValue() != 0) {
                    if (this.expandHeight[c.row] == 0.0f) {
                        this.expandHeight[c.row] = (float) c.expandY.intValue();
                    }
                }
                if (c.colspan.intValue() == CENTER) {
                    if (c.expandX.intValue() != 0) {
                        if (this.expandWidth[c.column] == 0.0f) {
                            this.expandWidth[c.column] = (float) c.expandX.intValue();
                        }
                    }
                }
                float w = m4w(c.padLeft, c);
                if (c.column == 0) {
                    f = 0.0f;
                } else {
                    f = Math.max(0.0f, m4w(c.spaceLeft, c) - spaceRightLast);
                }
                c.computedPadLeft = f + w;
                c.computedPadTop = m2h(c.padTop, c);
                int i2 = c.cellAboveIndex;
                if (r0 != -1) {
                    Cell above = (Cell) cells.get(c.cellAboveIndex);
                    c.computedPadTop += Math.max(0.0f, m2h(c.spaceTop, c) - m2h(above.spaceBottom, above));
                }
                float spaceRight = m4w(c.spaceRight, c);
                w = m4w(c.padRight, c);
                if (c.column + c.colspan.intValue() == this.columns) {
                    f = 0.0f;
                } else {
                    f = spaceRight;
                }
                c.computedPadRight = f + w;
                w = m2h(c.padBottom, c);
                if (c.row == this.rows - 1) {
                    f = 0.0f;
                } else {
                    f = m2h(c.spaceBottom, c);
                }
                c.computedPadBottom = f + w;
                spaceRightLast = spaceRight;
                prefWidth = c.prefWidth.get(c);
                float prefHeight = c.prefHeight.get(c);
                minWidth = c.minWidth.get(c);
                float minHeight = c.minHeight.get(c);
                maxWidth = c.maxWidth.get(c);
                float maxHeight = c.maxHeight.get(c);
                if (prefWidth < minWidth) {
                    prefWidth = minWidth;
                }
                if (prefHeight < minHeight) {
                    prefHeight = minHeight;
                }
                if (maxWidth > 0.0f && prefWidth > maxWidth) {
                    prefWidth = maxWidth;
                }
                if (maxHeight > 0.0f && prefHeight > maxHeight) {
                    prefHeight = maxHeight;
                }
                if (c.colspan.intValue() == CENTER) {
                    float hpadding = c.computedPadLeft + c.computedPadRight;
                    this.columnPrefWidth[c.column] = Math.max(this.columnPrefWidth[c.column], prefWidth + hpadding);
                    this.columnMinWidth[c.column] = Math.max(this.columnMinWidth[c.column], minWidth + hpadding);
                }
                float vpadding = c.computedPadTop + c.computedPadBottom;
                this.rowPrefHeight[c.row] = Math.max(this.rowPrefHeight[c.row], prefHeight + vpadding);
                this.rowMinHeight[c.row] = Math.max(this.rowMinHeight[c.row], minHeight + vpadding);
            }
        }
        n = cells.size();
        for (i = 0; i < n; i += CENTER) {
            int column;
            int nn;
            c = (Cell) cells.get(i);
            if (!c.ignore.booleanValue()) {
                if (c.expandX.intValue() != 0) {
                    column = c.column;
                    nn = column + c.colspan.intValue();
                    while (column < nn) {
                        if (this.expandWidth[column] != 0.0f) {
                            break;
                        }
                        column += CENTER;
                    }
                    column = c.column;
                    nn = column + c.colspan.intValue();
                    while (column < nn) {
                        this.expandWidth[column] = (float) c.expandX.intValue();
                        column += CENTER;
                    }
                }
            }
        }
        n = cells.size();
        for (i = 0; i < n; i += CENTER) {
            c = (Cell) cells.get(i);
            if (!c.ignore.booleanValue()) {
                if (c.colspan.intValue() != CENTER) {
                    minWidth = c.minWidth.get(c);
                    prefWidth = c.prefWidth.get(c);
                    maxWidth = c.maxWidth.get(c);
                    if (prefWidth < minWidth) {
                        prefWidth = minWidth;
                    }
                    if (maxWidth > 0.0f && prefWidth > maxWidth) {
                        prefWidth = maxWidth;
                    }
                    float spannedMinWidth = -(c.computedPadLeft + c.computedPadRight);
                    float spannedPrefWidth = spannedMinWidth;
                    column = c.column;
                    nn = column + c.colspan.intValue();
                    while (column < nn) {
                        spannedMinWidth += this.columnMinWidth[column];
                        spannedPrefWidth += this.columnPrefWidth[column];
                        column += CENTER;
                    }
                    float totalExpandWidth = 0.0f;
                    column = c.column;
                    nn = column + c.colspan.intValue();
                    while (column < nn) {
                        totalExpandWidth += this.expandWidth[column];
                        column += CENTER;
                    }
                    float extraMinWidth = Math.max(0.0f, minWidth - spannedMinWidth);
                    float extraPrefWidth = Math.max(0.0f, prefWidth - spannedPrefWidth);
                    column = c.column;
                    nn = column + c.colspan.intValue();
                    while (column < nn) {
                        float ratio;
                        if (totalExpandWidth == 0.0f) {
                            ratio = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) c.colspan.intValue());
                        } else {
                            ratio = this.expandWidth[column] / totalExpandWidth;
                        }
                        float[] fArr = this.columnMinWidth;
                        fArr[column] = fArr[column] + (extraMinWidth * ratio);
                        fArr = this.columnPrefWidth;
                        fArr[column] = fArr[column] + (extraPrefWidth * ratio);
                        column += CENTER;
                    }
                }
            }
        }
        float uniformMinWidth = 0.0f;
        float uniformMinHeight = 0.0f;
        float uniformPrefWidth = 0.0f;
        float uniformPrefHeight = 0.0f;
        n = cells.size();
        for (i = 0; i < n; i += CENTER) {
            c = (Cell) cells.get(i);
            if (!c.ignore.booleanValue()) {
                if (c.uniformX == Boolean.TRUE) {
                    if (c.colspan.intValue() == CENTER) {
                        hpadding = c.computedPadLeft + c.computedPadRight;
                        uniformMinWidth = Math.max(uniformMinWidth, this.columnMinWidth[c.column] - hpadding);
                        uniformPrefWidth = Math.max(uniformPrefWidth, this.columnPrefWidth[c.column] - hpadding);
                    }
                }
                if (c.uniformY == Boolean.TRUE) {
                    vpadding = c.computedPadTop + c.computedPadBottom;
                    uniformMinHeight = Math.max(uniformMinHeight, this.rowMinHeight[c.row] - vpadding);
                    uniformPrefHeight = Math.max(uniformPrefHeight, this.rowPrefHeight[c.row] - vpadding);
                }
            }
        }
        if (uniformPrefWidth > 0.0f || uniformPrefHeight > 0.0f) {
            n = cells.size();
            for (i = 0; i < n; i += CENTER) {
                c = (Cell) cells.get(i);
                if (!c.ignore.booleanValue()) {
                    if (uniformPrefWidth > 0.0f && c.uniformX == Boolean.TRUE) {
                        if (c.colspan.intValue() == CENTER) {
                            hpadding = c.computedPadLeft + c.computedPadRight;
                            this.columnMinWidth[c.column] = uniformMinWidth + hpadding;
                            this.columnPrefWidth[c.column] = uniformPrefWidth + hpadding;
                        }
                    }
                    if (uniformPrefHeight > 0.0f && c.uniformY == Boolean.TRUE) {
                        vpadding = c.computedPadTop + c.computedPadBottom;
                        this.rowMinHeight[c.row] = uniformMinHeight + vpadding;
                        this.rowPrefHeight[c.row] = uniformPrefHeight + vpadding;
                    }
                }
            }
        }
        this.tableMinWidth = 0.0f;
        this.tableMinHeight = 0.0f;
        this.tablePrefWidth = 0.0f;
        this.tablePrefHeight = 0.0f;
        i = 0;
        while (true) {
            i2 = this.columns;
            if (i >= r0) {
                break;
            }
            this.tableMinWidth += this.columnMinWidth[i];
            this.tablePrefWidth += this.columnPrefWidth[i];
            i += CENTER;
        }
        i = 0;
        while (true) {
            i2 = this.rows;
            if (i < r0) {
                this.tableMinHeight += this.rowMinHeight[i];
                this.tablePrefHeight += Math.max(this.rowMinHeight[i], this.rowPrefHeight[i]);
                i += CENTER;
            } else {
                hpadding = m3w(this.padLeft) + m3w(this.padRight);
                vpadding = m1h(this.padTop) + m1h(this.padBottom);
                this.tableMinWidth += hpadding;
                this.tableMinHeight += vpadding;
                f = this.tablePrefWidth;
                this.tablePrefWidth = Math.max(r0 + hpadding, this.tableMinWidth);
                f = this.tablePrefHeight;
                this.tablePrefHeight = Math.max(r0 + vpadding, this.tableMinHeight);
                return;
            }
        }
    }

    public void layout(float layoutX, float layoutY, float layoutWidth, float layoutHeight) {
        int i;
        float[] columnWeightedWidth;
        float extraWidth;
        float[] rowWeightedHeight;
        int nn;
        float maxWidth;
        float maxHeight;
        float extra;
        float used;
        int lastIndex;
        float[] fArr;
        float spannedCellWidth;
        Toolkit toolkit = this.toolkit;
        ArrayList<Cell> cells = this.cells;
        if (this.sizeInvalid) {
            computeSize();
        }
        float padLeft = m3w(this.padLeft);
        float hpadding = padLeft + m3w(this.padRight);
        float padTop = m1h(this.padTop);
        float vpadding = padTop + m1h(this.padBottom);
        float totalExpandWidth = 0.0f;
        float totalExpandHeight = 0.0f;
        for (i = 0; i < this.columns; i += CENTER) {
            totalExpandWidth += this.expandWidth[i];
        }
        for (i = 0; i < this.rows; i += CENTER) {
            totalExpandHeight += this.expandHeight[i];
        }
        float totalGrowWidth = this.tablePrefWidth - this.tableMinWidth;
        if (totalGrowWidth == 0.0f) {
            columnWeightedWidth = this.columnMinWidth;
        } else {
            extraWidth = Math.min(totalGrowWidth, Math.max(0.0f, layoutWidth - this.tableMinWidth));
            columnWeightedWidth = ensureSize(this.columnWeightedWidth, this.columns);
            this.columnWeightedWidth = columnWeightedWidth;
            for (i = 0; i < this.columns; i += CENTER) {
                columnWeightedWidth[i] = this.columnMinWidth[i] + (extraWidth * ((this.columnPrefWidth[i] - this.columnMinWidth[i]) / totalGrowWidth));
            }
        }
        float totalGrowHeight = this.tablePrefHeight - this.tableMinHeight;
        if (totalGrowHeight == 0.0f) {
            rowWeightedHeight = this.rowMinHeight;
        } else {
            rowWeightedHeight = ensureSize(this.rowWeightedHeight, this.rows);
            this.rowWeightedHeight = rowWeightedHeight;
            float extraHeight = Math.min(totalGrowHeight, Math.max(0.0f, layoutHeight - this.tableMinHeight));
            for (i = 0; i < this.rows; i += CENTER) {
                rowWeightedHeight[i] = this.rowMinHeight[i] + (extraHeight * ((this.rowPrefHeight[i] - this.rowMinHeight[i]) / totalGrowHeight));
            }
        }
        int n = cells.size();
        for (i = 0; i < n; i += CENTER) {
            int column;
            Cell c = (Cell) cells.get(i);
            if (!c.ignore.booleanValue()) {
                float spannedWeightedWidth = 0.0f;
                column = c.column;
                nn = column + c.colspan.intValue();
                while (column < nn) {
                    spannedWeightedWidth += columnWeightedWidth[column];
                    column += CENTER;
                }
                float weightedHeight = rowWeightedHeight[c.row];
                float prefWidth = c.prefWidth.get(c);
                float prefHeight = c.prefHeight.get(c);
                float minWidth = c.minWidth.get(c);
                float minHeight = c.minHeight.get(c);
                maxWidth = c.maxWidth.get(c);
                maxHeight = c.maxHeight.get(c);
                if (prefWidth < minWidth) {
                    prefWidth = minWidth;
                }
                if (prefHeight < minHeight) {
                    prefHeight = minHeight;
                }
                if (maxWidth > 0.0f && prefWidth > maxWidth) {
                    prefWidth = maxWidth;
                }
                if (maxHeight > 0.0f && prefHeight > maxHeight) {
                    prefHeight = maxHeight;
                }
                c.widgetWidth = Math.min((spannedWeightedWidth - c.computedPadLeft) - c.computedPadRight, prefWidth);
                c.widgetHeight = Math.min((weightedHeight - c.computedPadTop) - c.computedPadBottom, prefHeight);
                if (c.colspan.intValue() == CENTER) {
                    this.columnWidth[c.column] = Math.max(this.columnWidth[c.column], spannedWeightedWidth);
                }
                this.rowHeight[c.row] = Math.max(this.rowHeight[c.row], weightedHeight);
            }
        }
        if (totalExpandWidth > 0.0f) {
            extra = layoutWidth - hpadding;
            for (i = 0; i < this.columns; i += CENTER) {
                extra -= this.columnWidth[i];
            }
            used = 0.0f;
            lastIndex = 0;
            for (i = 0; i < this.columns; i += CENTER) {
                if (this.expandWidth[i] != 0.0f) {
                    float amount = (this.expandWidth[i] * extra) / totalExpandWidth;
                    fArr = this.columnWidth;
                    fArr[i] = fArr[i] + amount;
                    used += amount;
                    lastIndex = i;
                }
            }
            fArr = this.columnWidth;
            fArr[lastIndex] = fArr[lastIndex] + (extra - used);
        }
        if (totalExpandHeight > 0.0f) {
            extra = layoutHeight - vpadding;
            for (i = 0; i < this.rows; i += CENTER) {
                extra -= this.rowHeight[i];
            }
            used = 0.0f;
            lastIndex = 0;
            for (i = 0; i < this.rows; i += CENTER) {
                if (this.expandHeight[i] != 0.0f) {
                    amount = (this.expandHeight[i] * extra) / totalExpandHeight;
                    fArr = this.rowHeight;
                    fArr[i] = fArr[i] + amount;
                    used += amount;
                    lastIndex = i;
                }
            }
            fArr = this.rowHeight;
            fArr[lastIndex] = fArr[lastIndex] + (extra - used);
        }
        n = cells.size();
        for (i = 0; i < n; i += CENTER) {
            c = (Cell) cells.get(i);
            if (!(c.ignore.booleanValue() || c.colspan.intValue() == CENTER)) {
                extraWidth = 0.0f;
                column = c.column;
                nn = column + c.colspan.intValue();
                while (column < nn) {
                    extraWidth += columnWeightedWidth[column] - this.columnWidth[column];
                    column += CENTER;
                }
                extraWidth = (extraWidth - Math.max(0.0f, c.computedPadLeft + c.computedPadRight)) / ((float) c.colspan.intValue());
                if (extraWidth > 0.0f) {
                    column = c.column;
                    nn = column + c.colspan.intValue();
                    while (column < nn) {
                        fArr = this.columnWidth;
                        fArr[column] = fArr[column] + extraWidth;
                        column += CENTER;
                    }
                }
            }
        }
        float tableWidth = hpadding;
        float tableHeight = vpadding;
        for (i = 0; i < this.columns; i += CENTER) {
            tableWidth += this.columnWidth[i];
        }
        for (i = 0; i < this.rows; i += CENTER) {
            tableHeight += this.rowHeight[i];
        }
        float x = layoutX + padLeft;
        if ((this.align & RIGHT) != 0) {
            x += layoutWidth - tableWidth;
        } else if ((this.align & LEFT) == 0) {
            x += (layoutWidth - tableWidth) / 2.0f;
        }
        float y = layoutY + padTop;
        if ((this.align & BOTTOM) != 0) {
            y += layoutHeight - tableHeight;
        } else if ((this.align & TOP) == 0) {
            y += (layoutHeight - tableHeight) / 2.0f;
        }
        float currentX = x;
        float currentY = y;
        n = cells.size();
        for (i = 0; i < n; i += CENTER) {
            c = (Cell) cells.get(i);
            if (!c.ignore.booleanValue()) {
                spannedCellWidth = 0.0f;
                column = c.column;
                nn = column + c.colspan.intValue();
                while (column < nn) {
                    spannedCellWidth += this.columnWidth[column];
                    column += CENTER;
                }
                spannedCellWidth -= c.computedPadLeft + c.computedPadRight;
                currentX += c.computedPadLeft;
                if (c.fillX.floatValue() > 0.0f) {
                    c.widgetWidth = c.fillX.floatValue() * spannedCellWidth;
                    maxWidth = c.maxWidth.get(c);
                    if (maxWidth > 0.0f) {
                        c.widgetWidth = Math.min(c.widgetWidth, maxWidth);
                    }
                }
                if (c.fillY.floatValue() > 0.0f) {
                    c.widgetHeight = ((this.rowHeight[c.row] * c.fillY.floatValue()) - c.computedPadTop) - c.computedPadBottom;
                    maxHeight = c.maxHeight.get(c);
                    if (maxHeight > 0.0f) {
                        c.widgetHeight = Math.min(c.widgetHeight, maxHeight);
                    }
                }
                if ((c.align.intValue() & LEFT) != 0) {
                    c.widgetX = currentX;
                } else if ((c.align.intValue() & RIGHT) != 0) {
                    c.widgetX = (currentX + spannedCellWidth) - c.widgetWidth;
                } else {
                    c.widgetX = ((spannedCellWidth - c.widgetWidth) / 2.0f) + currentX;
                }
                if ((c.align.intValue() & TOP) != 0) {
                    c.widgetY = c.computedPadTop + currentY;
                } else if ((c.align.intValue() & BOTTOM) != 0) {
                    c.widgetY = ((this.rowHeight[c.row] + currentY) - c.widgetHeight) - c.computedPadBottom;
                } else {
                    c.widgetY = ((((this.rowHeight[c.row] - c.widgetHeight) + c.computedPadTop) - c.computedPadBottom) / 2.0f) + currentY;
                }
                if (c.endRow) {
                    currentX = x;
                    currentY += this.rowHeight[c.row];
                } else {
                    currentX += c.computedPadRight + spannedCellWidth;
                }
            }
        }
        if (this.debug != Debug.none) {
            toolkit.clearDebugRectangles(this);
            float currentX2 = x;
            currentY = y;
            if (this.debug == Debug.table || this.debug == Debug.all) {
                toolkit.addDebugRectangle(this, Debug.table, layoutX, layoutY, layoutWidth, layoutHeight);
                toolkit.addDebugRectangle(this, Debug.table, x, y, tableWidth - hpadding, tableHeight - vpadding);
            }
            i = 0;
            n = cells.size();
            while (i < n) {
                c = (Cell) cells.get(i);
                if (c.ignore.booleanValue()) {
                    currentX = currentX2;
                } else {
                    if (this.debug == Debug.widget || this.debug == Debug.all) {
                        toolkit.addDebugRectangle(this, Debug.widget, c.widgetX, c.widgetY, c.widgetWidth, c.widgetHeight);
                    }
                    spannedCellWidth = 0.0f;
                    column = c.column;
                    nn = column + c.colspan.intValue();
                    while (column < nn) {
                        spannedCellWidth += this.columnWidth[column];
                        column += CENTER;
                    }
                    spannedCellWidth -= c.computedPadLeft + c.computedPadRight;
                    currentX = currentX2 + c.computedPadLeft;
                    if (this.debug == Debug.cell || this.debug == Debug.all) {
                        toolkit.addDebugRectangle(this, Debug.cell, currentX, currentY + c.computedPadTop, spannedCellWidth, (this.rowHeight[c.row] - c.computedPadTop) - c.computedPadBottom);
                    }
                    if (c.endRow) {
                        currentX = x;
                        currentY += this.rowHeight[c.row];
                    } else {
                        currentX += c.computedPadRight + spannedCellWidth;
                    }
                }
                i += CENTER;
                currentX2 = currentX;
            }
        }
    }
}
