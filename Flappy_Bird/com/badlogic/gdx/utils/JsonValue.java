package com.badlogic.gdx.utils;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonValue implements Iterable<JsonValue> {
    public JsonValue child;
    private double doubleValue;
    private long longValue;
    public String name;
    public JsonValue next;
    public JsonValue prev;
    public int size;
    private String stringValue;
    private ValueType type;

    /* renamed from: com.badlogic.gdx.utils.JsonValue.1 */
    static /* synthetic */ class C00621 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType;

        static {
            $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType = new int[ValueType.values().length];
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.stringValue.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.doubleValue.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.longValue.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.booleanValue.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.nullValue.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public class JsonIterator implements Iterator<JsonValue>, Iterable<JsonValue> {
        JsonValue current;
        JsonValue entry;

        public JsonIterator() {
            this.entry = JsonValue.this.child;
        }

        public boolean hasNext() {
            return this.entry != null;
        }

        public JsonValue next() {
            this.current = this.entry;
            if (this.current == null) {
                throw new NoSuchElementException();
            }
            this.entry = this.current.next;
            return this.current;
        }

        public void remove() {
            if (this.current.prev == null) {
                JsonValue.this.child = this.current.next;
                if (JsonValue.this.child != null) {
                    JsonValue.this.child.prev = null;
                }
            } else {
                this.current.prev.next = this.current.next;
                if (this.current.next != null) {
                    this.current.next.prev = this.current.prev;
                }
            }
            JsonValue jsonValue = JsonValue.this;
            jsonValue.size--;
        }

        public Iterator<JsonValue> iterator() {
            return this;
        }
    }

    public enum ValueType {
        object,
        array,
        stringValue,
        doubleValue,
        longValue,
        booleanValue,
        nullValue
    }

    public JsonValue(ValueType type) {
        this.type = type;
    }

    public JsonValue(String value) {
        set(value);
    }

    public JsonValue(double value) {
        set(value);
    }

    public JsonValue(long value) {
        set(value);
    }

    public JsonValue(boolean value) {
        set(value);
    }

    public JsonValue get(int index) {
        JsonValue current = this.child;
        while (current != null && index > 0) {
            index--;
            current = current.next;
        }
        return current;
    }

    public JsonValue get(String name) {
        JsonValue current = this.child;
        while (current != null && !current.name.equalsIgnoreCase(name)) {
            current = current.next;
        }
        return current;
    }

    public JsonValue require(int index) {
        JsonValue current = this.child;
        while (current != null && index > 0) {
            index--;
            current = current.next;
        }
        if (current != null) {
            return current;
        }
        throw new IllegalArgumentException("Child not found with index: " + index);
    }

    public JsonValue require(String name) {
        JsonValue current = this.child;
        while (current != null && !current.name.equalsIgnoreCase(name)) {
            current = current.next;
        }
        if (current != null) {
            return current;
        }
        throw new IllegalArgumentException("Child not found with name: " + name);
    }

    public JsonValue remove(int index) {
        JsonValue child = get(index);
        if (child == null) {
            return null;
        }
        if (child.prev == null) {
            this.child = child.next;
            if (this.child != null) {
                this.child.prev = null;
            }
        } else {
            child.prev.next = child.next;
            if (child.next != null) {
                child.next.prev = child.prev;
            }
        }
        this.size--;
        return child;
    }

    public JsonValue remove(String name) {
        JsonValue child = get(name);
        if (child == null) {
            return null;
        }
        if (child.prev == null) {
            this.child = child.next;
            if (this.child != null) {
                this.child.prev = null;
            }
        } else {
            child.prev.next = child.next;
            if (child.next != null) {
                child.next.prev = child.prev;
            }
        }
        this.size--;
        return child;
    }

    public int size() {
        return this.size;
    }

    public String asString() {
        switch (C00621.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return this.stringValue;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return Double.toString(this.doubleValue);
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return Long.toString(this.longValue);
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return this.longValue != 0 ? "true" : "false";
            case Place.TYPE_ART_GALLERY /*5*/:
                return null;
            default:
                throw new IllegalStateException("Value cannot be converted to string: " + this.type);
        }
    }

    public float asFloat() {
        switch (C00621.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return Float.parseFloat(this.stringValue);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return (float) this.doubleValue;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return (float) this.longValue;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return this.longValue != 0 ? TextTrackStyle.DEFAULT_FONT_SCALE : 0.0f;
            default:
                throw new IllegalStateException("Value cannot be converted to float: " + this.type);
        }
    }

    public double asDouble() {
        switch (C00621.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return Double.parseDouble(this.stringValue);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return this.doubleValue;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return (double) this.longValue;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return this.longValue != 0 ? 1.0d : 0.0d;
            default:
                throw new IllegalStateException("Value cannot be converted to double: " + this.type);
        }
    }

    public long asLong() {
        switch (C00621.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return Long.parseLong(this.stringValue);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return (long) this.doubleValue;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return this.longValue;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return this.longValue != 0 ? 1 : 0;
            default:
                throw new IllegalStateException("Value cannot be converted to long: " + this.type);
        }
    }

    public int asInt() {
        switch (C00621.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return Integer.parseInt(this.stringValue);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return (int) this.doubleValue;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return (int) this.longValue;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return this.longValue != 0 ? 1 : 0;
            default:
                throw new IllegalStateException("Value cannot be converted to int: " + this.type);
        }
    }

    public boolean asBoolean() {
        switch (C00621.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return this.stringValue.equalsIgnoreCase("true");
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                if (this.doubleValue != 0.0d) {
                    return false;
                }
                return true;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                if (this.longValue != 0) {
                    return false;
                }
                return true;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return this.longValue != 0;
            default:
                throw new IllegalStateException("Value cannot be converted to boolean: " + this.type);
        }
    }

    public boolean hasChild(String name) {
        return getChild(name) != null;
    }

    public JsonValue getChild(String name) {
        JsonValue child = get(name);
        return child == null ? null : child.child;
    }

    public String getString(String name, String defaultValue) {
        JsonValue child = get(name);
        return (child == null || !child.isValue() || child.isNull()) ? defaultValue : child.asString();
    }

    public float getFloat(String name, float defaultValue) {
        JsonValue child = get(name);
        return (child == null || !child.isValue()) ? defaultValue : child.asFloat();
    }

    public double getDouble(String name, double defaultValue) {
        JsonValue child = get(name);
        return (child == null || !child.isValue()) ? defaultValue : child.asDouble();
    }

    public long getLong(String name, long defaultValue) {
        JsonValue child = get(name);
        return (child == null || !child.isValue()) ? defaultValue : child.asLong();
    }

    public int getInt(String name, int defaultValue) {
        JsonValue child = get(name);
        return (child == null || !child.isValue()) ? defaultValue : child.asInt();
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        JsonValue child = get(name);
        return (child == null || !child.isValue()) ? defaultValue : child.asBoolean();
    }

    public String getString(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asString();
        }
        throw new IllegalArgumentException("Named value not found: " + name);
    }

    public float getFloat(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asFloat();
        }
        throw new IllegalArgumentException("Named value not found: " + name);
    }

    public double getDouble(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asDouble();
        }
        throw new IllegalArgumentException("Named value not found: " + name);
    }

    public long getLong(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asLong();
        }
        throw new IllegalArgumentException("Named value not found: " + name);
    }

    public int getInt(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asInt();
        }
        throw new IllegalArgumentException("Named value not found: " + name);
    }

    public boolean getBoolean(String name) {
        JsonValue child = get(name);
        if (child != null) {
            return child.asBoolean();
        }
        throw new IllegalArgumentException("Named value not found: " + name);
    }

    public String getString(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asString();
        }
        throw new IllegalArgumentException("Indexed value not found: " + this.name);
    }

    public float getFloat(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asFloat();
        }
        throw new IllegalArgumentException("Indexed value not found: " + this.name);
    }

    public double getDouble(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asDouble();
        }
        throw new IllegalArgumentException("Indexed value not found: " + this.name);
    }

    public long getLong(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asLong();
        }
        throw new IllegalArgumentException("Indexed value not found: " + this.name);
    }

    public int getInt(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asInt();
        }
        throw new IllegalArgumentException("Indexed value not found: " + this.name);
    }

    public boolean getBoolean(int index) {
        JsonValue child = get(index);
        if (child != null) {
            return child.asBoolean();
        }
        throw new IllegalArgumentException("Indexed value not found: " + this.name);
    }

    public ValueType type() {
        return this.type;
    }

    public void setType(ValueType type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        this.type = type;
    }

    public boolean isArray() {
        return this.type == ValueType.array;
    }

    public boolean isObject() {
        return this.type == ValueType.object;
    }

    public boolean isString() {
        return this.type == ValueType.stringValue;
    }

    public boolean isNumber() {
        return this.type == ValueType.doubleValue || this.type == ValueType.longValue;
    }

    public boolean isDouble() {
        return this.type == ValueType.doubleValue;
    }

    public boolean isLong() {
        return this.type == ValueType.longValue;
    }

    public boolean isBoolean() {
        return this.type == ValueType.booleanValue;
    }

    public boolean isNull() {
        return this.type == ValueType.nullValue;
    }

    public boolean isValue() {
        switch (C00621.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
            case CompletionEvent.STATUS_CONFLICT /*2*/:
            case CompletionEvent.STATUS_CANCELED /*3*/:
            case GameHelper.CLIENT_APPSTATE /*4*/:
            case Place.TYPE_ART_GALLERY /*5*/:
                return true;
            default:
                return false;
        }
    }

    public String name() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonValue child() {
        return this.child;
    }

    public JsonValue next() {
        return this.next;
    }

    public void setNext(JsonValue next) {
        this.next = next;
    }

    public JsonValue prev() {
        return this.prev;
    }

    public void setPrev(JsonValue prev) {
        this.prev = prev;
    }

    public void set(String value) {
        this.stringValue = value;
        this.type = value == null ? ValueType.nullValue : ValueType.stringValue;
    }

    public void set(double value) {
        this.doubleValue = value;
        this.longValue = (long) value;
        this.type = ValueType.doubleValue;
    }

    public void set(long value) {
        this.longValue = value;
        this.doubleValue = (double) value;
        this.type = ValueType.longValue;
    }

    public void set(boolean value) {
        this.longValue = value ? 1 : 0;
        this.type = ValueType.booleanValue;
    }

    public String toString() {
        if (isValue()) {
            return this.name == null ? asString() : this.name + ": " + asString();
        } else {
            return prettyPrint(OutputType.minimal, 0);
        }
    }

    public String prettyPrint(OutputType outputType, int singleLineColumns) {
        StringBuilder buffer = new StringBuilder((int) GL20.GL_NEVER);
        prettyPrint(this, buffer, outputType, 0, singleLineColumns);
        return buffer.toString();
    }

    private void prettyPrint(JsonValue object, StringBuilder buffer, OutputType outputType, int indent, int singleLineColumns) {
        boolean newLines;
        int start;
        JsonValue child;
        if (object.isObject()) {
            if (object.child() == null) {
                buffer.append("{}");
                return;
            }
            newLines = !isFlat(object);
            start = buffer.length();
            loop0:
            while (true) {
                buffer.append(newLines ? "{\n" : "{ ");
                child = object.child();
                while (child != null) {
                    if (newLines) {
                        indent(indent, buffer);
                    }
                    buffer.append(outputType.quoteName(child.name()));
                    buffer.append(": ");
                    prettyPrint(child, buffer, outputType, indent + 1, singleLineColumns);
                    if (child.next() != null) {
                        buffer.append(",");
                    }
                    buffer.append(newLines ? '\n' : ' ');
                    if (newLines || buffer.length() - start <= singleLineColumns) {
                        child = child.next();
                    } else {
                        buffer.setLength(start);
                        newLines = true;
                    }
                }
                break loop0;
            }
            if (newLines) {
                indent(indent - 1, buffer);
            }
            buffer.append('}');
        } else if (object.isArray()) {
            if (object.child() == null) {
                buffer.append("[]");
                return;
            }
            newLines = !isFlat(object);
            start = buffer.length();
            loop2:
            while (true) {
                buffer.append(newLines ? "[\n" : "[ ");
                child = object.child();
                while (child != null) {
                    if (newLines) {
                        indent(indent, buffer);
                    }
                    prettyPrint(child, buffer, outputType, indent + 1, singleLineColumns);
                    if (child.next() != null) {
                        buffer.append(",");
                    }
                    buffer.append(newLines ? '\n' : ' ');
                    if (newLines || buffer.length() - start <= singleLineColumns) {
                        child = child.next();
                    } else {
                        buffer.setLength(start);
                        newLines = true;
                    }
                }
                break loop2;
            }
            if (newLines) {
                indent(indent - 1, buffer);
            }
            buffer.append(']');
        } else if (object.isString()) {
            buffer.append(outputType.quoteValue(object.asString()));
        } else if (object.isDouble()) {
            double doubleValue = object.asDouble();
            long longValue = object.asLong();
            if (doubleValue == ((double) longValue)) {
                doubleValue = (double) longValue;
            }
            buffer.append(doubleValue);
        } else if (object.isLong()) {
            buffer.append(object.asLong());
        } else if (object.isBoolean()) {
            buffer.append(object.asBoolean());
        } else if (object.isNull()) {
            buffer.append("null");
        } else {
            throw new SerializationException("Unknown object type: " + object);
        }
    }

    private static boolean isFlat(JsonValue object) {
        JsonValue child = object.child();
        while (child != null) {
            if (child.isObject() || child.isArray()) {
                return false;
            }
            child = child.next();
        }
        return true;
    }

    private static void indent(int count, StringBuilder buffer) {
        for (int i = 0; i < count; i++) {
            buffer.append('\t');
        }
    }

    public JsonIterator iterator() {
        return new JsonIterator();
    }
}
