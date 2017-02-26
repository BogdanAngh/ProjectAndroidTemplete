package com.esotericsoftware.tablelayout;

public abstract class Value {
    public static Value maxHeight;
    public static Value maxWidth;
    public static Value minHeight;
    public static Value minWidth;
    public static Value prefHeight;
    public static Value prefWidth;
    public static final Value zero;

    /* renamed from: com.esotericsoftware.tablelayout.Value.10 */
    static class AnonymousClass10 extends Value {
        final /* synthetic */ float val$percent;
        final /* synthetic */ Object val$widget;

        AnonymousClass10(Object obj, float f) {
            this.val$widget = obj;
            this.val$percent = f;
        }

        public float get(Cell cell) {
            return Toolkit.instance.getWidth(this.val$widget) * this.val$percent;
        }

        public float get(Object table) {
            return Toolkit.instance.getWidth(this.val$widget) * this.val$percent;
        }
    }

    public static abstract class CellValue extends Value {
        public float get(Object table) {
            throw new UnsupportedOperationException("This value can only be used for a cell property.");
        }
    }

    public static class FixedValue extends Value {
        private float value;

        public FixedValue(float value) {
            this.value = value;
        }

        public void set(float value) {
            this.value = value;
        }

        public float get(Object table) {
            return this.value;
        }

        public float get(Cell cell) {
            return this.value;
        }
    }

    public static abstract class TableValue extends Value {
        public float get(Cell cell) {
            return get(cell.getLayout().getTable());
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.11 */
    static class AnonymousClass11 extends TableValue {
        final /* synthetic */ float val$percent;
        final /* synthetic */ Object val$widget;

        AnonymousClass11(Object obj, float f) {
            this.val$widget = obj;
            this.val$percent = f;
        }

        public float get(Object table) {
            return Toolkit.instance.getHeight(this.val$widget) * this.val$percent;
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.1 */
    static class C05931 extends CellValue {
        C05931() {
        }

        public float get(Cell cell) {
            return 0.0f;
        }

        public float get(Object table) {
            return 0.0f;
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.2 */
    static class C05942 extends CellValue {
        C05942() {
        }

        public float get(Cell cell) {
            if (cell == null) {
                throw new RuntimeException("minWidth can only be set on a cell property.");
            }
            Object widget = cell.widget;
            if (widget == null) {
                return 0.0f;
            }
            return Toolkit.instance.getMinWidth(widget);
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.3 */
    static class C05953 extends CellValue {
        C05953() {
        }

        public float get(Cell cell) {
            if (cell == null) {
                throw new RuntimeException("minHeight can only be set on a cell property.");
            }
            Object widget = cell.widget;
            if (widget == null) {
                return 0.0f;
            }
            return Toolkit.instance.getMinHeight(widget);
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.4 */
    static class C05964 extends CellValue {
        C05964() {
        }

        public float get(Cell cell) {
            if (cell == null) {
                throw new RuntimeException("prefWidth can only be set on a cell property.");
            }
            Object widget = cell.widget;
            if (widget == null) {
                return 0.0f;
            }
            return Toolkit.instance.getPrefWidth(widget);
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.5 */
    static class C05975 extends CellValue {
        C05975() {
        }

        public float get(Cell cell) {
            if (cell == null) {
                throw new RuntimeException("prefHeight can only be set on a cell property.");
            }
            Object widget = cell.widget;
            if (widget == null) {
                return 0.0f;
            }
            return Toolkit.instance.getPrefHeight(widget);
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.6 */
    static class C05986 extends CellValue {
        C05986() {
        }

        public float get(Cell cell) {
            if (cell == null) {
                throw new RuntimeException("maxWidth can only be set on a cell property.");
            }
            Object widget = cell.widget;
            if (widget == null) {
                return 0.0f;
            }
            return Toolkit.instance.getMaxWidth(widget);
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.7 */
    static class C05997 extends CellValue {
        C05997() {
        }

        public float get(Cell cell) {
            if (cell == null) {
                throw new RuntimeException("maxHeight can only be set on a cell property.");
            }
            Object widget = cell.widget;
            if (widget == null) {
                return 0.0f;
            }
            return Toolkit.instance.getMaxHeight(widget);
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.8 */
    static class C06008 extends TableValue {
        final /* synthetic */ float val$percent;

        C06008(float f) {
            this.val$percent = f;
        }

        public float get(Object table) {
            return Toolkit.instance.getWidth(table) * this.val$percent;
        }
    }

    /* renamed from: com.esotericsoftware.tablelayout.Value.9 */
    static class C06019 extends TableValue {
        final /* synthetic */ float val$percent;

        C06019(float f) {
            this.val$percent = f;
        }

        public float get(Object table) {
            return Toolkit.instance.getHeight(table) * this.val$percent;
        }
    }

    public abstract float get(Cell cell);

    public abstract float get(Object obj);

    public float width(Object table) {
        return Toolkit.instance.width(get(table));
    }

    public float height(Object table) {
        return Toolkit.instance.height(get(table));
    }

    public float width(Cell cell) {
        return Toolkit.instance.width(get(cell));
    }

    public float height(Cell cell) {
        return Toolkit.instance.height(get(cell));
    }

    static {
        zero = new C05931();
        minWidth = new C05942();
        minHeight = new C05953();
        prefWidth = new C05964();
        prefHeight = new C05975();
        maxWidth = new C05986();
        maxHeight = new C05997();
    }

    public static Value percentWidth(float percent) {
        return new C06008(percent);
    }

    public static Value percentHeight(float percent) {
        return new C06019(percent);
    }

    public static Value percentWidth(float percent, Object widget) {
        return new AnonymousClass10(widget, percent);
    }

    public static Value percentHeight(float percent, Object widget) {
        return new AnonymousClass11(widget, percent);
    }
}
