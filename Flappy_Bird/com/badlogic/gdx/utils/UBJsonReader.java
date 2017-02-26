package com.badlogic.gdx.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UBJsonReader implements BaseJsonReader {
    public JsonValue parse(InputStream input) {
        try {
            return parse(new DataInputStream(input));
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public JsonValue parse(FileHandle file) {
        try {
            return parse(file.read());
        } catch (Exception ex) {
            throw new SerializationException("Error parsing file: " + file, ex);
        }
    }

    public JsonValue parse(DataInputStream din) throws IOException {
        return parse(din, din.readByte());
    }

    protected JsonValue parse(DataInputStream din, byte type) throws IOException {
        if (type == 91) {
            return parseArray(din);
        }
        if (type == 123) {
            return parseObject(din);
        }
        if (type == 90) {
            return new JsonValue(ValueType.nullValue);
        }
        if (type == 84) {
            return new JsonValue(true);
        }
        if (type == 70) {
            return new JsonValue(false);
        }
        if (type == 66) {
            return new JsonValue((long) readUChar(din));
        }
        if (type == 105) {
            return new JsonValue((long) din.readShort());
        }
        if (type == 73) {
            return new JsonValue((long) din.readInt());
        }
        if (type == 76) {
            return new JsonValue(din.readLong());
        }
        if (type == 100) {
            return new JsonValue((double) din.readFloat());
        }
        if (type == 68) {
            return new JsonValue(din.readDouble());
        }
        if (type == 115 || type == 83) {
            return new JsonValue(parseString(din, type));
        }
        if (type == 97 || type == 65) {
            return parseData(din, type);
        }
        throw new GdxRuntimeException("Unrecognized data type");
    }

    protected JsonValue parseArray(DataInputStream din) throws IOException {
        JsonValue result = new JsonValue(ValueType.array);
        byte type = din.readByte();
        JsonValue prev = null;
        while (din.available() > 0 && type != 93) {
            JsonValue val = parse(din, type);
            if (prev != null) {
                prev.next = val;
                result.size++;
            } else {
                result.child = val;
                result.size = 1;
            }
            prev = val;
            type = din.readByte();
        }
        return result;
    }

    protected JsonValue parseObject(DataInputStream din) throws IOException {
        JsonValue result = new JsonValue(ValueType.object);
        byte type = din.readByte();
        JsonValue prev = null;
        while (din.available() > 0 && type != 125) {
            if (type == 115 || type == 83) {
                String key = parseString(din, type);
                JsonValue child = parse(din);
                child.setName(key);
                if (prev != null) {
                    prev.next = child;
                    result.size++;
                } else {
                    result.child = child;
                    result.size = 1;
                }
                prev = child;
                type = din.readByte();
            } else {
                throw new GdxRuntimeException("Only string key are currently supported");
            }
        }
        return result;
    }

    protected JsonValue parseData(DataInputStream din, byte blockType) throws IOException {
        byte dataType = din.readByte();
        long size = blockType == 65 ? readUInt(din) : (long) readUChar(din);
        JsonValue result = new JsonValue(ValueType.array);
        JsonValue prev = null;
        for (long i = 0; i < size; i++) {
            JsonValue val = parse(din, dataType);
            if (prev != null) {
                prev.next = val;
                result.size++;
            } else {
                result.child = val;
                result.size = 1;
            }
            prev = val;
        }
        return result;
    }

    protected String parseString(DataInputStream din, byte type) throws IOException {
        return readString(din, type == 115 ? (long) readUChar(din) : readUInt(din));
    }

    protected short readUChar(DataInputStream din) throws IOException {
        return (short) (((short) din.readByte()) & Keys.F12);
    }

    protected long readUInt(DataInputStream din) throws IOException {
        return ((long) din.readInt()) & -1;
    }

    protected String readString(DataInputStream din, long size) throws IOException {
        byte[] data = new byte[((int) size)];
        din.readFully(data);
        return new String(data, "UTF-8");
    }
}
