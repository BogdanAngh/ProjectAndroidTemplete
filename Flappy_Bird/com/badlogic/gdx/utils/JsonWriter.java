package com.badlogic.gdx.utils;

import com.google.android.gms.drive.events.CompletionEvent;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

public class JsonWriter extends Writer {
    private JsonObject current;
    private boolean named;
    private OutputType outputType;
    private final Array<JsonObject> stack;
    final Writer writer;

    /* renamed from: com.badlogic.gdx.utils.JsonWriter.1 */
    static /* synthetic */ class C00631 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType;

        static {
            $SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType = new int[OutputType.values().length];
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType[OutputType.minimal.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType[OutputType.javascript.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private class JsonObject {
        final boolean array;
        boolean needsComma;

        JsonObject(boolean array) throws IOException {
            this.array = array;
            JsonWriter.this.writer.write(array ? 91 : 123);
        }

        void close() throws IOException {
            JsonWriter.this.writer.write(this.array ? 93 : 125);
        }
    }

    public enum OutputType {
        json,
        javascript,
        minimal;
        
        private static Pattern javascriptPattern;
        private static Pattern minimalNamePattern;
        private static Pattern minimalValuePattern;

        static {
            javascriptPattern = Pattern.compile("[a-zA-Z_$][a-zA-Z_$0-9]*");
            minimalValuePattern = Pattern.compile("[a-zA-Z_$][^:}\\], ]*");
            minimalNamePattern = Pattern.compile("[a-zA-Z0-9_$][^:}\\], ]*");
        }

        public String quoteValue(Object value) {
            if (value == null || (value instanceof Number) || (value instanceof Boolean)) {
                return String.valueOf(value);
            }
            String string = String.valueOf(value).replace("\\", "\\\\");
            return (this != minimal || string.equals("true") || string.equals("false") || string.equals("null") || !minimalValuePattern.matcher(string).matches()) ? '\"' + string.replace("\"", "\\\"") + '\"' : string;
        }

        public String quoteName(String value) {
            value = value.replace("\\", "\\\\");
            switch (C00631.$SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType[ordinal()]) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    if (minimalNamePattern.matcher(value).matches()) {
                        return value;
                    }
                    return '\"' + value.replace("\"", "\\\"") + '\"';
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    if (javascriptPattern.matcher(value).matches()) {
                        return value;
                    }
                    return '\"' + value.replace("\"", "\\\"") + '\"';
                default:
                    return '\"' + value.replace("\"", "\\\"") + '\"';
            }
        }
    }

    public JsonWriter(Writer writer) {
        this.stack = new Array();
        this.outputType = OutputType.json;
        this.writer = writer;
    }

    public Writer getWriter() {
        return this.writer;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public JsonWriter name(String name) throws IOException {
        if (this.current == null || this.current.array) {
            throw new IllegalStateException("Current item must be an object.");
        }
        if (this.current.needsComma) {
            this.writer.write(44);
        } else {
            this.current.needsComma = true;
        }
        this.writer.write(this.outputType.quoteName(name));
        this.writer.write(58);
        this.named = true;
        return this;
    }

    public JsonWriter object() throws IOException {
        if (this.current != null) {
            if (this.current.array) {
                if (this.current.needsComma) {
                    this.writer.write(44);
                } else {
                    this.current.needsComma = true;
                }
            } else if (this.named || this.current.array) {
                this.named = false;
            } else {
                throw new IllegalStateException("Name must be set.");
            }
        }
        Array array = this.stack;
        JsonObject jsonObject = new JsonObject(false);
        this.current = jsonObject;
        array.add(jsonObject);
        return this;
    }

    public JsonWriter array() throws IOException {
        if (this.current != null) {
            if (this.current.array) {
                if (this.current.needsComma) {
                    this.writer.write(44);
                } else {
                    this.current.needsComma = true;
                }
            } else if (this.named || this.current.array) {
                this.named = false;
            } else {
                throw new IllegalStateException("Name must be set.");
            }
        }
        Array array = this.stack;
        JsonObject jsonObject = new JsonObject(true);
        this.current = jsonObject;
        array.add(jsonObject);
        return this;
    }

    public JsonWriter value(Object value) throws IOException {
        if (value instanceof Number) {
            Number number = (Number) value;
            long longValue = number.longValue();
            if (number.doubleValue() == ((double) longValue)) {
                value = Long.valueOf(longValue);
            }
        }
        if (this.current != null) {
            if (this.current.array) {
                if (this.current.needsComma) {
                    this.writer.write(44);
                } else {
                    this.current.needsComma = true;
                }
            } else if (this.named) {
                this.named = false;
            } else {
                throw new IllegalStateException("Name must be set.");
            }
        }
        this.writer.write(this.outputType.quoteValue(value));
        return this;
    }

    public JsonWriter object(String name) throws IOException {
        return name(name).object();
    }

    public JsonWriter array(String name) throws IOException {
        return name(name).array();
    }

    public JsonWriter set(String name, Object value) throws IOException {
        return name(name).value(value);
    }

    public JsonWriter pop() throws IOException {
        if (this.named) {
            throw new IllegalStateException("Expected an object, array, or value since a name was set.");
        }
        ((JsonObject) this.stack.pop()).close();
        this.current = this.stack.size == 0 ? null : (JsonObject) this.stack.peek();
        return this;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        this.writer.write(cbuf, off, len);
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        while (this.stack.size > 0) {
            pop();
        }
        this.writer.close();
    }
}
