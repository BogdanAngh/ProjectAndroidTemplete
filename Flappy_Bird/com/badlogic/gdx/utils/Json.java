package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Json {
    private static final boolean debug = false;
    private final ObjectMap<Class, Object[]> classToDefaultValues;
    private final ObjectMap<Class, Serializer> classToSerializer;
    private final ObjectMap<Class, String> classToTag;
    private Serializer defaultSerializer;
    private boolean ignoreUnknownFields;
    private OutputType outputType;
    private final ObjectMap<String, Class> tagToClass;
    private String typeName;
    private final ObjectMap<Class, ObjectMap<String, FieldMetadata>> typeToFields;
    private boolean usePrototypes;
    private JsonWriter writer;

    private static class FieldMetadata {
        Class elementType;
        Field field;

        public FieldMetadata(Field field) {
            this.field = field;
            int index = (ClassReflection.isAssignableFrom(ObjectMap.class, field.getType()) || ClassReflection.isAssignableFrom(Map.class, field.getType())) ? 1 : 0;
            this.elementType = field.getElementType(index);
        }
    }

    public interface Serializable {
        void read(Json json, JsonValue jsonValue);

        void write(Json json);
    }

    public interface Serializer<T> {
        T read(Json json, JsonValue jsonValue, Class cls);

        void write(Json json, T t, Class cls);
    }

    public static abstract class ReadOnlySerializer<T> implements Serializer<T> {
        public abstract T read(Json json, JsonValue jsonValue, Class cls);

        public void write(Json json, T t, Class knownType) {
        }
    }

    public Json() {
        this.typeName = "class";
        this.usePrototypes = true;
        this.typeToFields = new ObjectMap();
        this.tagToClass = new ObjectMap();
        this.classToTag = new ObjectMap();
        this.classToSerializer = new ObjectMap();
        this.classToDefaultValues = new ObjectMap();
        this.outputType = OutputType.minimal;
    }

    public Json(OutputType outputType) {
        this.typeName = "class";
        this.usePrototypes = true;
        this.typeToFields = new ObjectMap();
        this.tagToClass = new ObjectMap();
        this.classToTag = new ObjectMap();
        this.classToSerializer = new ObjectMap();
        this.classToDefaultValues = new ObjectMap();
        this.outputType = outputType;
    }

    public void setIgnoreUnknownFields(boolean ignoreUnknownFields) {
        this.ignoreUnknownFields = ignoreUnknownFields;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void addClassTag(String tag, Class type) {
        this.tagToClass.put(tag, type);
        this.classToTag.put(type, tag);
    }

    public Class getClass(String tag) {
        Class type = (Class) this.tagToClass.get(tag);
        if (type == null) {
            try {
                type = ClassReflection.forName(tag);
            } catch (Throwable ex) {
                throw new SerializationException(ex);
            }
        }
        return type;
    }

    public String getTag(Class type) {
        String tag = (String) this.classToTag.get(type);
        return tag != null ? tag : type.getName();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDefaultSerializer(Serializer defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    public <T> void setSerializer(Class<T> type, Serializer<T> serializer) {
        this.classToSerializer.put(type, serializer);
    }

    public <T> Serializer<T> getSerializer(Class<T> type) {
        return (Serializer) this.classToSerializer.get(type);
    }

    public void setUsePrototypes(boolean usePrototypes) {
        this.usePrototypes = usePrototypes;
    }

    public void setElementType(Class type, String fieldName, Class elementType) {
        ObjectMap<String, FieldMetadata> fields = (ObjectMap) this.typeToFields.get(type);
        if (fields == null) {
            fields = cacheFields(type);
        }
        FieldMetadata metadata = (FieldMetadata) fields.get(fieldName);
        if (metadata == null) {
            throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")");
        }
        metadata.elementType = elementType;
    }

    private ObjectMap<String, FieldMetadata> cacheFields(Class type) {
        ArrayList<Field> allFields = new ArrayList();
        for (Class nextClass = type; nextClass != Object.class; nextClass = nextClass.getSuperclass()) {
            Collections.addAll(allFields, ClassReflection.getDeclaredFields(nextClass));
        }
        ObjectMap<String, FieldMetadata> nameToField = new ObjectMap();
        int n = allFields.size();
        for (int i = 0; i < n; i++) {
            Field field = (Field) allFields.get(i);
            if (!(field.isTransient() || field.isStatic() || field.isSynthetic())) {
                if (!field.isAccessible()) {
                    try {
                        field.setAccessible(true);
                    } catch (AccessControlException e) {
                    }
                }
                nameToField.put(field.getName(), new FieldMetadata(field));
            }
        }
        this.typeToFields.put(type, nameToField);
        return nameToField;
    }

    public String toJson(Object object) {
        return toJson(object, object == null ? null : object.getClass(), (Class) null);
    }

    public String toJson(Object object, Class knownType) {
        return toJson(object, knownType, (Class) null);
    }

    public String toJson(Object object, Class knownType, Class elementType) {
        Writer buffer = new StringWriter();
        toJson(object, knownType, elementType, buffer);
        return buffer.toString();
    }

    public void toJson(Object object, FileHandle file) {
        toJson(object, object == null ? null : object.getClass(), null, file);
    }

    public void toJson(Object object, Class knownType, FileHandle file) {
        toJson(object, knownType, null, file);
    }

    public void toJson(Object object, Class knownType, Class elementType, FileHandle file) {
        Writer writer = null;
        try {
            writer = file.writer(false);
            toJson(object, knownType, elementType, writer);
            StreamUtils.closeQuietly(writer);
        } catch (Exception ex) {
            throw new SerializationException("Error writing file: " + file, ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(writer);
        }
    }

    public void toJson(Object object, Writer writer) {
        toJson(object, object == null ? null : object.getClass(), null, writer);
    }

    public void toJson(Object object, Class knownType, Writer writer) {
        toJson(object, knownType, null, writer);
    }

    public void toJson(Object object, Class knownType, Class elementType, Writer writer) {
        setWriter(writer);
        try {
            writeValue(object, knownType, elementType);
        } finally {
            StreamUtils.closeQuietly(this.writer);
            this.writer = null;
        }
    }

    public void setWriter(Writer writer) {
        if (!(writer instanceof JsonWriter)) {
            writer = new JsonWriter(writer);
        }
        this.writer = (JsonWriter) writer;
        this.writer.setOutputType(this.outputType);
    }

    public JsonWriter getWriter() {
        return this.writer;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeFields(java.lang.Object r17) {
        /*
        r16 = this;
        r11 = r17.getClass();
        r0 = r16;
        r2 = r0.getDefaultValues(r11);
        r0 = r16;
        r13 = r0.typeToFields;
        r5 = r13.get(r11);
        r5 = (com.badlogic.gdx.utils.ObjectMap) r5;
        if (r5 != 0) goto L_0x001c;
    L_0x0016:
        r0 = r16;
        r5 = r0.cacheFields(r11);
    L_0x001c:
        r6 = 0;
        r13 = new com.badlogic.gdx.utils.ObjectMap$Values;
        r13.<init>(r5);
        r8 = r13.iterator();
    L_0x0026:
        r13 = r8.hasNext();
        if (r13 == 0) goto L_0x00ed;
    L_0x002c:
        r9 = r8.next();
        r9 = (com.badlogic.gdx.utils.Json.FieldMetadata) r9;
        r4 = r9.field;
        r0 = r17;
        r12 = r4.get(r0);	 Catch:{ ReflectionException -> 0x006a, SerializationException -> 0x009c, Exception -> 0x00c2 }
        if (r2 == 0) goto L_0x0053;
    L_0x003c:
        r7 = r6 + 1;
        r1 = r2[r6];	 Catch:{ ReflectionException -> 0x00f4, SerializationException -> 0x00f1, Exception -> 0x00ee }
        if (r12 != 0) goto L_0x0046;
    L_0x0042:
        if (r1 != 0) goto L_0x0046;
    L_0x0044:
        r6 = r7;
        goto L_0x0026;
    L_0x0046:
        if (r12 == 0) goto L_0x0052;
    L_0x0048:
        if (r1 == 0) goto L_0x0052;
    L_0x004a:
        r13 = r12.equals(r1);	 Catch:{ ReflectionException -> 0x00f4, SerializationException -> 0x00f1, Exception -> 0x00ee }
        if (r13 == 0) goto L_0x0052;
    L_0x0050:
        r6 = r7;
        goto L_0x0026;
    L_0x0052:
        r6 = r7;
    L_0x0053:
        r0 = r16;
        r13 = r0.writer;	 Catch:{ ReflectionException -> 0x006a, SerializationException -> 0x009c, Exception -> 0x00c2 }
        r14 = r4.getName();	 Catch:{ ReflectionException -> 0x006a, SerializationException -> 0x009c, Exception -> 0x00c2 }
        r13.name(r14);	 Catch:{ ReflectionException -> 0x006a, SerializationException -> 0x009c, Exception -> 0x00c2 }
        r13 = r4.getType();	 Catch:{ ReflectionException -> 0x006a, SerializationException -> 0x009c, Exception -> 0x00c2 }
        r14 = r9.elementType;	 Catch:{ ReflectionException -> 0x006a, SerializationException -> 0x009c, Exception -> 0x00c2 }
        r0 = r16;
        r0.writeValue(r12, r13, r14);	 Catch:{ ReflectionException -> 0x006a, SerializationException -> 0x009c, Exception -> 0x00c2 }
        goto L_0x0026;
    L_0x006a:
        r3 = move-exception;
    L_0x006b:
        r13 = new com.badlogic.gdx.utils.SerializationException;
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = "Error accessing field: ";
        r14 = r14.append(r15);
        r15 = r4.getName();
        r14 = r14.append(r15);
        r15 = " (";
        r14 = r14.append(r15);
        r15 = r11.getName();
        r14 = r14.append(r15);
        r15 = ")";
        r14 = r14.append(r15);
        r14 = r14.toString();
        r13.<init>(r14, r3);
        throw r13;
    L_0x009c:
        r3 = move-exception;
    L_0x009d:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r13 = r13.append(r4);
        r14 = " (";
        r13 = r13.append(r14);
        r14 = r11.getName();
        r13 = r13.append(r14);
        r14 = ")";
        r13 = r13.append(r14);
        r13 = r13.toString();
        r3.addTrace(r13);
        throw r3;
    L_0x00c2:
        r10 = move-exception;
    L_0x00c3:
        r3 = new com.badlogic.gdx.utils.SerializationException;
        r3.<init>(r10);
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r13 = r13.append(r4);
        r14 = " (";
        r13 = r13.append(r14);
        r14 = r11.getName();
        r13 = r13.append(r14);
        r14 = ")";
        r13 = r13.append(r14);
        r13 = r13.toString();
        r3.addTrace(r13);
        throw r3;
    L_0x00ed:
        return;
    L_0x00ee:
        r10 = move-exception;
        r6 = r7;
        goto L_0x00c3;
    L_0x00f1:
        r3 = move-exception;
        r6 = r7;
        goto L_0x009d;
    L_0x00f4:
        r3 = move-exception;
        r6 = r7;
        goto L_0x006b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.Json.writeFields(java.lang.Object):void");
    }

    private Object[] getDefaultValues(Class type) {
        SerializationException ex;
        if (!this.usePrototypes) {
            return null;
        }
        if (this.classToDefaultValues.containsKey(type)) {
            return (Object[]) this.classToDefaultValues.get(type);
        }
        try {
            Object object = newInstance(type);
            ObjectMap<String, FieldMetadata> fields = (ObjectMap) this.typeToFields.get(type);
            if (fields == null) {
                fields = cacheFields(type);
            }
            Object[] values = new Object[fields.size];
            this.classToDefaultValues.put(type, values);
            int i = 0;
            Iterator i$ = fields.values().iterator();
            while (i$.hasNext()) {
                Field field = ((FieldMetadata) i$.next()).field;
                int i2 = i + 1;
                try {
                    values[i] = field.get(object);
                    i = i2;
                } catch (ReflectionException ex2) {
                    throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex2);
                } catch (SerializationException ex3) {
                    ex3.addTrace(field + " (" + type.getName() + ")");
                    throw ex3;
                } catch (Throwable runtimeEx) {
                    ex3 = new SerializationException(runtimeEx);
                    ex3.addTrace(field + " (" + type.getName() + ")");
                    throw ex3;
                }
            }
            return values;
        } catch (Exception e) {
            this.classToDefaultValues.put(type, null);
            return null;
        }
    }

    public void writeField(Object object, String name) {
        writeField(object, name, name, null);
    }

    public void writeField(Object object, String name, Class elementType) {
        writeField(object, name, name, elementType);
    }

    public void writeField(Object object, String fieldName, String jsonName) {
        writeField(object, fieldName, jsonName, null);
    }

    public void writeField(Object object, String fieldName, String jsonName, Class elementType) {
        SerializationException ex;
        Class type = object.getClass();
        ObjectMap<String, FieldMetadata> fields = (ObjectMap) this.typeToFields.get(type);
        if (fields == null) {
            fields = cacheFields(type);
        }
        FieldMetadata metadata = (FieldMetadata) fields.get(fieldName);
        if (metadata == null) {
            throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")");
        }
        Field field = metadata.field;
        if (elementType == null) {
            elementType = metadata.elementType;
        }
        try {
            this.writer.name(jsonName);
            writeValue(field.get(object), field.getType(), elementType);
        } catch (ReflectionException ex2) {
            throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex2);
        } catch (SerializationException ex3) {
            ex3.addTrace(field + " (" + type.getName() + ")");
            throw ex3;
        } catch (Throwable runtimeEx) {
            ex3 = new SerializationException(runtimeEx);
            ex3.addTrace(field + " (" + type.getName() + ")");
            throw ex3;
        }
    }

    public void writeValue(String name, Object value) {
        try {
            this.writer.name(name);
            if (value == null) {
                writeValue(value, null, null);
            } else {
                writeValue(value, value.getClass(), null);
            }
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeValue(String name, Object value, Class knownType) {
        try {
            this.writer.name(name);
            writeValue(value, knownType, null);
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeValue(String name, Object value, Class knownType, Class elementType) {
        try {
            this.writer.name(name);
            writeValue(value, knownType, elementType);
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeValue(Object value) {
        if (value == null) {
            writeValue(value, null, null);
        } else {
            writeValue(value, value.getClass(), null);
        }
    }

    public void writeValue(Object value, Class knownType) {
        writeValue(value, knownType, null);
    }

    public void writeValue(Object value, Class knownType, Class elementType) {
        if (value == null) {
            try {
                this.writer.value(null);
            } catch (Throwable ex) {
                throw new SerializationException(ex);
            }
        } else if ((knownType != null && knownType.isPrimitive()) || knownType == String.class || knownType == Integer.class || knownType == Boolean.class || knownType == Float.class || knownType == Long.class || knownType == Double.class || knownType == Short.class || knownType == Byte.class || knownType == Character.class) {
            this.writer.value(value);
        } else {
            Class actualType = value.getClass();
            if (actualType.isPrimitive() || actualType == String.class || actualType == Integer.class || actualType == Boolean.class || actualType == Float.class || actualType == Long.class || actualType == Double.class || actualType == Short.class || actualType == Byte.class || actualType == Character.class) {
                writeObjectStart(actualType, null);
                writeValue("value", value);
                writeObjectEnd();
            } else if (value instanceof Serializable) {
                writeObjectStart(actualType, knownType);
                ((Serializable) value).write(this);
                writeObjectEnd();
            } else {
                Serializer serializer = (Serializer) this.classToSerializer.get(actualType);
                if (serializer != null) {
                    serializer.write(this, value, knownType);
                } else if (value instanceof Array) {
                    if (knownType == null || actualType == knownType || actualType == Array.class) {
                        writeArrayStart();
                        Array array = (Array) value;
                        n = array.size;
                        for (i = 0; i < n; i++) {
                            writeValue(array.get(i), elementType, null);
                        }
                        writeArrayEnd();
                        return;
                    }
                    throw new SerializationException("Serialization of an Array other than the known type is not supported.\nKnown type: " + knownType + "\nActual type: " + actualType);
                } else if (value instanceof Collection) {
                    if (knownType == null || actualType == knownType || actualType == ArrayList.class) {
                        writeArrayStart();
                        for (Object item : (Collection) value) {
                            writeValue(item, elementType, null);
                        }
                        writeArrayEnd();
                        return;
                    }
                    throw new SerializationException("Serialization of a Collection other than the known type is not supported.\nKnown type: " + knownType + "\nActual type: " + actualType);
                } else if (actualType.isArray()) {
                    if (elementType == null) {
                        elementType = actualType.getComponentType();
                    }
                    int length = ArrayReflection.getLength(value);
                    writeArrayStart();
                    for (i = 0; i < length; i++) {
                        writeValue(ArrayReflection.get(value, i), elementType, null);
                    }
                    writeArrayEnd();
                } else if (value instanceof OrderedMap) {
                    if (knownType == null) {
                        knownType = OrderedMap.class;
                    }
                    writeObjectStart(actualType, knownType);
                    OrderedMap map = (OrderedMap) value;
                    i$ = map.orderedKeys().iterator();
                    while (i$.hasNext()) {
                        Object key = i$.next();
                        this.writer.name(convertToString(key));
                        writeValue(map.get(key), elementType, null);
                    }
                    writeObjectEnd();
                } else if (value instanceof ArrayMap) {
                    if (knownType == null) {
                        knownType = ArrayMap.class;
                    }
                    writeObjectStart(actualType, knownType);
                    ArrayMap map2 = (ArrayMap) value;
                    n = map2.size;
                    for (i = 0; i < n; i++) {
                        this.writer.name(convertToString(map2.keys[i]));
                        writeValue(map2.values[i], elementType, null);
                    }
                    writeObjectEnd();
                } else if (value instanceof ObjectMap) {
                    if (knownType == null) {
                        knownType = OrderedMap.class;
                    }
                    writeObjectStart(actualType, knownType);
                    i$ = ((ObjectMap) value).entries().iterator();
                    while (i$.hasNext()) {
                        Entry entry = (Entry) i$.next();
                        this.writer.name(convertToString(entry.key));
                        writeValue(entry.value, elementType, null);
                    }
                    writeObjectEnd();
                } else if (value instanceof Map) {
                    if (knownType == null) {
                        knownType = HashMap.class;
                    }
                    writeObjectStart(actualType, knownType);
                    for (Map.Entry entry2 : ((Map) value).entrySet()) {
                        this.writer.name(convertToString(entry2.getKey()));
                        writeValue(entry2.getValue(), elementType, null);
                    }
                    writeObjectEnd();
                } else if (!ClassReflection.isAssignableFrom(Enum.class, actualType)) {
                    writeObjectStart(actualType, knownType);
                    writeFields(value);
                    writeObjectEnd();
                } else if (knownType == null || !knownType.equals(actualType)) {
                    writeObjectStart(actualType, null);
                    this.writer.name("value");
                    this.writer.value(value);
                    writeObjectEnd();
                } else {
                    this.writer.value(value);
                }
            }
        }
    }

    public void writeObjectStart(String name) {
        try {
            this.writer.name(name);
            writeObjectStart();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectStart(String name, Class actualType, Class knownType) {
        try {
            this.writer.name(name);
            writeObjectStart(actualType, knownType);
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectStart() {
        try {
            this.writer.object();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectStart(Class actualType, Class knownType) {
        try {
            this.writer.object();
            if (knownType == null || knownType != actualType) {
                writeType(actualType);
            }
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeObjectEnd() {
        try {
            this.writer.pop();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeArrayStart(String name) {
        try {
            this.writer.name(name);
            this.writer.array();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeArrayStart() {
        try {
            this.writer.array();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeArrayEnd() {
        try {
            this.writer.pop();
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        }
    }

    public void writeType(Class type) {
        if (this.typeName != null) {
            String className = (String) this.classToTag.get(type);
            if (className == null) {
                className = type.getName();
            }
            try {
                this.writer.set(this.typeName, className);
            } catch (Throwable ex) {
                throw new SerializationException(ex);
            }
        }
    }

    public <T> T fromJson(Class<T> type, Reader reader) {
        return readValue((Class) type, null, new JsonReader().parse(reader));
    }

    public <T> T fromJson(Class<T> type, Class elementType, Reader reader) {
        return readValue((Class) type, elementType, new JsonReader().parse(reader));
    }

    public <T> T fromJson(Class<T> type, InputStream input) {
        return readValue((Class) type, null, new JsonReader().parse(input));
    }

    public <T> T fromJson(Class<T> type, Class elementType, InputStream input) {
        return readValue((Class) type, elementType, new JsonReader().parse(input));
    }

    public <T> T fromJson(Class<T> type, FileHandle file) {
        try {
            return readValue((Class) type, null, new JsonReader().parse(file));
        } catch (Exception ex) {
            throw new SerializationException("Error reading file: " + file, ex);
        }
    }

    public <T> T fromJson(Class<T> type, Class elementType, FileHandle file) {
        try {
            return readValue((Class) type, elementType, new JsonReader().parse(file));
        } catch (Exception ex) {
            throw new SerializationException("Error reading file: " + file, ex);
        }
    }

    public <T> T fromJson(Class<T> type, char[] data, int offset, int length) {
        return readValue((Class) type, null, new JsonReader().parse(data, offset, length));
    }

    public <T> T fromJson(Class<T> type, Class elementType, char[] data, int offset, int length) {
        return readValue((Class) type, elementType, new JsonReader().parse(data, offset, length));
    }

    public <T> T fromJson(Class<T> type, String json) {
        return readValue((Class) type, null, new JsonReader().parse(json));
    }

    public <T> T fromJson(Class<T> type, Class elementType, String json) {
        return readValue((Class) type, elementType, new JsonReader().parse(json));
    }

    public void readField(Object object, String name, JsonValue jsonData) {
        readField(object, name, name, null, jsonData);
    }

    public void readField(Object object, String name, Class elementType, JsonValue jsonData) {
        readField(object, name, name, elementType, jsonData);
    }

    public void readField(Object object, String fieldName, String jsonName, JsonValue jsonData) {
        readField(object, fieldName, jsonName, null, jsonData);
    }

    public void readField(Object object, String fieldName, String jsonName, Class elementType, JsonValue jsonMap) {
        SerializationException ex;
        Class type = object.getClass();
        ObjectMap<String, FieldMetadata> fields = (ObjectMap) this.typeToFields.get(type);
        if (fields == null) {
            fields = cacheFields(type);
        }
        FieldMetadata metadata = (FieldMetadata) fields.get(fieldName);
        if (metadata == null) {
            throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")");
        }
        Field field = metadata.field;
        JsonValue jsonValue = jsonMap.get(jsonName);
        if (jsonValue != null) {
            if (elementType == null) {
                elementType = metadata.elementType;
            }
            try {
                field.set(object, readValue(field.getType(), elementType, jsonValue));
            } catch (ReflectionException ex2) {
                throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex2);
            } catch (SerializationException ex3) {
                ex3.addTrace(field.getName() + " (" + type.getName() + ")");
                throw ex3;
            } catch (Throwable runtimeEx) {
                ex3 = new SerializationException(runtimeEx);
                ex3.addTrace(field.getName() + " (" + type.getName() + ")");
                throw ex3;
            }
        }
    }

    public void readFields(Object object, JsonValue jsonMap) {
        SerializationException ex;
        Class type = object.getClass();
        ObjectMap<String, FieldMetadata> fields = (ObjectMap) this.typeToFields.get(type);
        if (fields == null) {
            fields = cacheFields(type);
        }
        for (JsonValue child = jsonMap.child(); child != null; child = child.next()) {
            FieldMetadata metadata = (FieldMetadata) fields.get(child.name());
            if (metadata != null) {
                Field field = metadata.field;
                try {
                    field.set(object, readValue(field.getType(), metadata.elementType, child));
                } catch (ReflectionException ex2) {
                    throw new SerializationException("Error accessing field: " + field.getName() + " (" + type.getName() + ")", ex2);
                } catch (SerializationException ex3) {
                    ex3.addTrace(field.getName() + " (" + type.getName() + ")");
                    throw ex3;
                } catch (Throwable runtimeEx) {
                    ex3 = new SerializationException(runtimeEx);
                    ex3.addTrace(field.getName() + " (" + type.getName() + ")");
                    throw ex3;
                }
            } else if (!this.ignoreUnknownFields) {
                throw new SerializationException("Field not found: " + child.name() + " (" + type.getName() + ")");
            }
        }
    }

    public <T> T readValue(String name, Class<T> type, JsonValue jsonMap) {
        return readValue((Class) type, null, jsonMap.get(name));
    }

    public <T> T readValue(String name, Class<T> type, T defaultValue, JsonValue jsonMap) {
        JsonValue jsonValue = jsonMap.get(name);
        return jsonValue == null ? defaultValue : readValue((Class) type, null, jsonValue);
    }

    public <T> T readValue(String name, Class<T> type, Class elementType, JsonValue jsonMap) {
        return readValue((Class) type, elementType, jsonMap.get(name));
    }

    public <T> T readValue(String name, Class<T> type, Class elementType, T defaultValue, JsonValue jsonMap) {
        JsonValue jsonValue = jsonMap.get(name);
        return jsonValue == null ? defaultValue : readValue((Class) type, elementType, jsonValue);
    }

    public <T> T readValue(Class<T> type, Class elementType, T t, JsonValue jsonData) {
        return readValue((Class) type, elementType, jsonData);
    }

    public <T> T readValue(Class<T> type, JsonValue jsonData) {
        return readValue((Class) type, null, jsonData);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T readValue(java.lang.Class<T> r22, java.lang.Class r23, com.badlogic.gdx.utils.JsonValue r24) {
        /*
        r21 = this;
        if (r24 != 0) goto L_0x0004;
    L_0x0002:
        r13 = 0;
    L_0x0003:
        return r13;
    L_0x0004:
        r18 = r24.isObject();
        if (r18 == 0) goto L_0x0173;
    L_0x000a:
        r0 = r21;
        r0 = r0.typeName;
        r18 = r0;
        if (r18 != 0) goto L_0x0086;
    L_0x0012:
        r5 = 0;
    L_0x0013:
        if (r5 == 0) goto L_0x0026;
    L_0x0015:
        r0 = r21;
        r0 = r0.typeName;
        r18 = r0;
        r0 = r24;
        r1 = r18;
        r0.remove(r1);
        r22 = com.badlogic.gdx.utils.reflect.ClassReflection.forName(r5);	 Catch:{ ReflectionException -> 0x009a }
    L_0x0026:
        if (r22 == 0) goto L_0x011b;
    L_0x0028:
        r18 = java.lang.String.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0030:
        r18 = java.lang.Integer.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0038:
        r18 = java.lang.Boolean.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0040:
        r18 = java.lang.Float.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0048:
        r18 = java.lang.Long.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0050:
        r18 = java.lang.Double.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0058:
        r18 = java.lang.Short.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0060:
        r18 = java.lang.Byte.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0068:
        r18 = java.lang.Character.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0076;
    L_0x0070:
        r18 = r22.isEnum();
        if (r18 == 0) goto L_0x00b3;
    L_0x0076:
        r18 = "value";
        r0 = r21;
        r1 = r18;
        r2 = r22;
        r3 = r24;
        r13 = r0.readValue(r1, r2, r3);
        goto L_0x0003;
    L_0x0086:
        r0 = r21;
        r0 = r0.typeName;
        r18 = r0;
        r19 = 0;
        r0 = r24;
        r1 = r18;
        r2 = r19;
        r5 = r0.getString(r1, r2);
        goto L_0x0013;
    L_0x009a:
        r8 = move-exception;
        r0 = r21;
        r0 = r0.tagToClass;
        r18 = r0;
        r0 = r18;
        r22 = r0.get(r5);
        r22 = (java.lang.Class) r22;
        if (r22 != 0) goto L_0x0026;
    L_0x00ab:
        r18 = new com.badlogic.gdx.utils.SerializationException;
        r0 = r18;
        r0.<init>(r8);
        throw r18;
    L_0x00b3:
        r0 = r21;
        r0 = r0.classToSerializer;
        r18 = r0;
        r0 = r18;
        r1 = r22;
        r16 = r0.get(r1);
        r16 = (com.badlogic.gdx.utils.Json.Serializer) r16;
        if (r16 == 0) goto L_0x00d3;
    L_0x00c5:
        r0 = r16;
        r1 = r21;
        r2 = r24;
        r3 = r22;
        r13 = r0.read(r1, r2, r3);
        goto L_0x0003;
    L_0x00d3:
        r14 = r21.newInstance(r22);
        r0 = r14 instanceof com.badlogic.gdx.utils.Json.Serializable;
        r18 = r0;
        if (r18 == 0) goto L_0x00ed;
    L_0x00dd:
        r18 = r14;
        r18 = (com.badlogic.gdx.utils.Json.Serializable) r18;
        r0 = r18;
        r1 = r21;
        r2 = r24;
        r0.read(r1, r2);
        r13 = r14;
        goto L_0x0003;
    L_0x00ed:
        r0 = r14 instanceof java.util.HashMap;
        r18 = r0;
        if (r18 == 0) goto L_0x013b;
    L_0x00f3:
        r15 = r14;
        r15 = (java.util.HashMap) r15;
        r4 = r24.child();
    L_0x00fa:
        if (r4 == 0) goto L_0x0118;
    L_0x00fc:
        r18 = r4.name();
        r19 = 0;
        r0 = r21;
        r1 = r23;
        r2 = r19;
        r19 = r0.readValue(r1, r2, r4);
        r0 = r18;
        r1 = r19;
        r15.put(r0, r1);
        r4 = r4.next();
        goto L_0x00fa;
    L_0x0118:
        r13 = r15;
        goto L_0x0003;
    L_0x011b:
        r0 = r21;
        r0 = r0.defaultSerializer;
        r18 = r0;
        if (r18 == 0) goto L_0x0137;
    L_0x0123:
        r0 = r21;
        r0 = r0.defaultSerializer;
        r18 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r24;
        r3 = r22;
        r13 = r0.read(r1, r2, r3);
        goto L_0x0003;
    L_0x0137:
        r13 = r24;
        goto L_0x0003;
    L_0x013b:
        r0 = r14 instanceof com.badlogic.gdx.utils.ObjectMap;
        r18 = r0;
        if (r18 == 0) goto L_0x0169;
    L_0x0141:
        r15 = r14;
        r15 = (com.badlogic.gdx.utils.ObjectMap) r15;
        r4 = r24.child();
    L_0x0148:
        if (r4 == 0) goto L_0x0166;
    L_0x014a:
        r18 = r4.name();
        r19 = 0;
        r0 = r21;
        r1 = r23;
        r2 = r19;
        r19 = r0.readValue(r1, r2, r4);
        r0 = r18;
        r1 = r19;
        r15.put(r0, r1);
        r4 = r4.next();
        goto L_0x0148;
    L_0x0166:
        r13 = r15;
        goto L_0x0003;
    L_0x0169:
        r0 = r21;
        r1 = r24;
        r0.readFields(r14, r1);
        r13 = r14;
        goto L_0x0003;
    L_0x0173:
        if (r22 == 0) goto L_0x0195;
    L_0x0175:
        r0 = r21;
        r0 = r0.classToSerializer;
        r18 = r0;
        r0 = r18;
        r1 = r22;
        r16 = r0.get(r1);
        r16 = (com.badlogic.gdx.utils.Json.Serializer) r16;
        if (r16 == 0) goto L_0x0195;
    L_0x0187:
        r0 = r16;
        r1 = r21;
        r2 = r24;
        r3 = r22;
        r13 = r0.read(r1, r2, r3);
        goto L_0x0003;
    L_0x0195:
        r18 = r24.isArray();
        if (r18 == 0) goto L_0x028f;
    L_0x019b:
        if (r22 == 0) goto L_0x01b1;
    L_0x019d:
        r18 = java.lang.Object.class;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x01b1;
    L_0x01a5:
        r18 = com.badlogic.gdx.utils.Array.class;
        r0 = r18;
        r1 = r22;
        r18 = com.badlogic.gdx.utils.reflect.ClassReflection.isAssignableFrom(r0, r1);
        if (r18 == 0) goto L_0x01e5;
    L_0x01b1:
        if (r22 == 0) goto L_0x01bb;
    L_0x01b3:
        r18 = java.lang.Object.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x01dc;
    L_0x01bb:
        r13 = new com.badlogic.gdx.utils.Array;
        r13.<init>();
    L_0x01c0:
        r4 = r24.child();
    L_0x01c4:
        if (r4 == 0) goto L_0x0003;
    L_0x01c6:
        r18 = 0;
        r0 = r21;
        r1 = r23;
        r2 = r18;
        r18 = r0.readValue(r1, r2, r4);
        r0 = r18;
        r13.add(r0);
        r4 = r4.next();
        goto L_0x01c4;
    L_0x01dc:
        r18 = r21.newInstance(r22);
        r18 = (com.badlogic.gdx.utils.Array) r18;
        r13 = r18;
        goto L_0x01c0;
    L_0x01e5:
        r18 = java.util.List.class;
        r0 = r18;
        r1 = r22;
        r18 = com.badlogic.gdx.utils.reflect.ClassReflection.isAssignableFrom(r0, r1);
        if (r18 == 0) goto L_0x0223;
    L_0x01f1:
        if (r22 == 0) goto L_0x01f9;
    L_0x01f3:
        r18 = r22.isInterface();
        if (r18 == 0) goto L_0x021a;
    L_0x01f9:
        r13 = new java.util.ArrayList;
        r13.<init>();
    L_0x01fe:
        r4 = r24.child();
    L_0x0202:
        if (r4 == 0) goto L_0x0003;
    L_0x0204:
        r18 = 0;
        r0 = r21;
        r1 = r23;
        r2 = r18;
        r18 = r0.readValue(r1, r2, r4);
        r0 = r18;
        r13.add(r0);
        r4 = r4.next();
        goto L_0x0202;
    L_0x021a:
        r18 = r21.newInstance(r22);
        r18 = (java.util.List) r18;
        r13 = r18;
        goto L_0x01fe;
    L_0x0223:
        r18 = r22.isArray();
        if (r18 == 0) goto L_0x025e;
    L_0x0229:
        r6 = r22.getComponentType();
        if (r23 != 0) goto L_0x0231;
    L_0x022f:
        r23 = r6;
    L_0x0231:
        r0 = r24;
        r0 = r0.size;
        r18 = r0;
        r0 = r18;
        r13 = com.badlogic.gdx.utils.reflect.ArrayReflection.newInstance(r6, r0);
        r9 = 0;
        r4 = r24.child();
        r10 = r9;
    L_0x0243:
        if (r4 == 0) goto L_0x0003;
    L_0x0245:
        r9 = r10 + 1;
        r18 = 0;
        r0 = r21;
        r1 = r23;
        r2 = r18;
        r18 = r0.readValue(r1, r2, r4);
        r0 = r18;
        com.badlogic.gdx.utils.reflect.ArrayReflection.set(r13, r10, r0);
        r4 = r4.next();
        r10 = r9;
        goto L_0x0243;
    L_0x025e:
        r18 = new com.badlogic.gdx.utils.SerializationException;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "Unable to convert value to required type: ";
        r19 = r19.append(r20);
        r0 = r19;
        r1 = r24;
        r19 = r0.append(r1);
        r20 = " (";
        r19 = r19.append(r20);
        r20 = r22.getName();
        r19 = r19.append(r20);
        r20 = ")";
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.<init>(r19);
        throw r18;
    L_0x028f:
        r18 = r24.isNumber();
        if (r18 == 0) goto L_0x0362;
    L_0x0295:
        if (r22 == 0) goto L_0x02a7;
    L_0x0297:
        r18 = java.lang.Float.TYPE;	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x02a7;
    L_0x029f:
        r18 = java.lang.Float.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x02b1;
    L_0x02a7:
        r18 = r24.asFloat();	 Catch:{ NumberFormatException -> 0x0354 }
        r13 = java.lang.Float.valueOf(r18);	 Catch:{ NumberFormatException -> 0x0354 }
        goto L_0x0003;
    L_0x02b1:
        r18 = java.lang.Integer.TYPE;	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x02c1;
    L_0x02b9:
        r18 = java.lang.Integer.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x02cb;
    L_0x02c1:
        r18 = r24.asInt();	 Catch:{ NumberFormatException -> 0x0354 }
        r13 = java.lang.Integer.valueOf(r18);	 Catch:{ NumberFormatException -> 0x0354 }
        goto L_0x0003;
    L_0x02cb:
        r18 = java.lang.Long.TYPE;	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x02db;
    L_0x02d3:
        r18 = java.lang.Long.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x02e5;
    L_0x02db:
        r18 = r24.asLong();	 Catch:{ NumberFormatException -> 0x0354 }
        r13 = java.lang.Long.valueOf(r18);	 Catch:{ NumberFormatException -> 0x0354 }
        goto L_0x0003;
    L_0x02e5:
        r18 = java.lang.Double.TYPE;	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x02f5;
    L_0x02ed:
        r18 = java.lang.Double.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0304;
    L_0x02f5:
        r18 = r24.asFloat();	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r18;
        r0 = (double) r0;	 Catch:{ NumberFormatException -> 0x0354 }
        r18 = r0;
        r13 = java.lang.Double.valueOf(r18);	 Catch:{ NumberFormatException -> 0x0354 }
        goto L_0x0003;
    L_0x0304:
        r18 = java.lang.String.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0316;
    L_0x030c:
        r18 = r24.asFloat();	 Catch:{ NumberFormatException -> 0x0354 }
        r13 = java.lang.Float.toString(r18);	 Catch:{ NumberFormatException -> 0x0354 }
        goto L_0x0003;
    L_0x0316:
        r18 = java.lang.Short.TYPE;	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0326;
    L_0x031e:
        r18 = java.lang.Short.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0335;
    L_0x0326:
        r18 = r24.asInt();	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r18;
        r0 = (short) r0;	 Catch:{ NumberFormatException -> 0x0354 }
        r18 = r0;
        r13 = java.lang.Short.valueOf(r18);	 Catch:{ NumberFormatException -> 0x0354 }
        goto L_0x0003;
    L_0x0335:
        r18 = java.lang.Byte.TYPE;	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0345;
    L_0x033d:
        r18 = java.lang.Byte.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0355;
    L_0x0345:
        r18 = r24.asInt();	 Catch:{ NumberFormatException -> 0x0354 }
        r0 = r18;
        r0 = (byte) r0;	 Catch:{ NumberFormatException -> 0x0354 }
        r18 = r0;
        r13 = java.lang.Byte.valueOf(r18);	 Catch:{ NumberFormatException -> 0x0354 }
        goto L_0x0003;
    L_0x0354:
        r18 = move-exception;
    L_0x0355:
        r11 = new com.badlogic.gdx.utils.JsonValue;
        r18 = r24.asString();
        r0 = r18;
        r11.<init>(r0);
        r24 = r11;
    L_0x0362:
        r18 = r24.isBoolean();
        if (r18 == 0) goto L_0x0392;
    L_0x0368:
        if (r22 == 0) goto L_0x037a;
    L_0x036a:
        r18 = java.lang.Boolean.TYPE;	 Catch:{ NumberFormatException -> 0x0384 }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x037a;
    L_0x0372:
        r18 = java.lang.Boolean.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0385;
    L_0x037a:
        r18 = r24.asBoolean();	 Catch:{ NumberFormatException -> 0x0384 }
        r13 = java.lang.Boolean.valueOf(r18);	 Catch:{ NumberFormatException -> 0x0384 }
        goto L_0x0003;
    L_0x0384:
        r18 = move-exception;
    L_0x0385:
        r11 = new com.badlogic.gdx.utils.JsonValue;
        r18 = r24.asString();
        r0 = r18;
        r11.<init>(r0);
        r24 = r11;
    L_0x0392:
        r18 = r24.isString();
        if (r18 == 0) goto L_0x04c5;
    L_0x0398:
        r17 = r24.asString();
        if (r22 == 0) goto L_0x03a6;
    L_0x039e:
        r18 = java.lang.String.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x03aa;
    L_0x03a6:
        r13 = r17;
        goto L_0x0003;
    L_0x03aa:
        r18 = java.lang.Integer.TYPE;	 Catch:{ NumberFormatException -> 0x042e }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x03ba;
    L_0x03b2:
        r18 = java.lang.Integer.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x03c0;
    L_0x03ba:
        r13 = java.lang.Integer.valueOf(r17);	 Catch:{ NumberFormatException -> 0x042e }
        goto L_0x0003;
    L_0x03c0:
        r18 = java.lang.Float.TYPE;	 Catch:{ NumberFormatException -> 0x042e }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x03d0;
    L_0x03c8:
        r18 = java.lang.Float.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x03d6;
    L_0x03d0:
        r13 = java.lang.Float.valueOf(r17);	 Catch:{ NumberFormatException -> 0x042e }
        goto L_0x0003;
    L_0x03d6:
        r18 = java.lang.Long.TYPE;	 Catch:{ NumberFormatException -> 0x042e }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x03e6;
    L_0x03de:
        r18 = java.lang.Long.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x03ec;
    L_0x03e6:
        r13 = java.lang.Long.valueOf(r17);	 Catch:{ NumberFormatException -> 0x042e }
        goto L_0x0003;
    L_0x03ec:
        r18 = java.lang.Double.TYPE;	 Catch:{ NumberFormatException -> 0x042e }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x03fc;
    L_0x03f4:
        r18 = java.lang.Double.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0402;
    L_0x03fc:
        r13 = java.lang.Double.valueOf(r17);	 Catch:{ NumberFormatException -> 0x042e }
        goto L_0x0003;
    L_0x0402:
        r18 = java.lang.Short.TYPE;	 Catch:{ NumberFormatException -> 0x042e }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0412;
    L_0x040a:
        r18 = java.lang.Short.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0418;
    L_0x0412:
        r13 = java.lang.Short.valueOf(r17);	 Catch:{ NumberFormatException -> 0x042e }
        goto L_0x0003;
    L_0x0418:
        r18 = java.lang.Byte.TYPE;	 Catch:{ NumberFormatException -> 0x042e }
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0428;
    L_0x0420:
        r18 = java.lang.Byte.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x042f;
    L_0x0428:
        r13 = java.lang.Byte.valueOf(r17);	 Catch:{ NumberFormatException -> 0x042e }
        goto L_0x0003;
    L_0x042e:
        r18 = move-exception;
    L_0x042f:
        r18 = java.lang.Boolean.TYPE;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x043f;
    L_0x0437:
        r18 = java.lang.Boolean.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0445;
    L_0x043f:
        r13 = java.lang.Boolean.valueOf(r17);
        goto L_0x0003;
    L_0x0445:
        r18 = java.lang.Character.TYPE;
        r0 = r22;
        r1 = r18;
        if (r0 == r1) goto L_0x0455;
    L_0x044d:
        r18 = java.lang.Character.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0461;
    L_0x0455:
        r18 = 0;
        r18 = r17.charAt(r18);
        r13 = java.lang.Character.valueOf(r18);
        goto L_0x0003;
    L_0x0461:
        r18 = java.lang.Enum.class;
        r0 = r18;
        r1 = r22;
        r18 = com.badlogic.gdx.utils.reflect.ClassReflection.isAssignableFrom(r0, r1);
        if (r18 == 0) goto L_0x0488;
    L_0x046d:
        r7 = r22.getEnumConstants();
        r9 = 0;
        r12 = r7.length;
    L_0x0473:
        if (r9 >= r12) goto L_0x0488;
    L_0x0475:
        r18 = r7[r9];
        r18 = r18.toString();
        r18 = r17.equals(r18);
        if (r18 == 0) goto L_0x0485;
    L_0x0481:
        r13 = r7[r9];
        goto L_0x0003;
    L_0x0485:
        r9 = r9 + 1;
        goto L_0x0473;
    L_0x0488:
        r18 = java.lang.CharSequence.class;
        r0 = r22;
        r1 = r18;
        if (r0 != r1) goto L_0x0494;
    L_0x0490:
        r13 = r17;
        goto L_0x0003;
    L_0x0494:
        r18 = new com.badlogic.gdx.utils.SerializationException;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "Unable to convert value to required type: ";
        r19 = r19.append(r20);
        r0 = r19;
        r1 = r24;
        r19 = r0.append(r1);
        r20 = " (";
        r19 = r19.append(r20);
        r20 = r22.getName();
        r19 = r19.append(r20);
        r20 = ")";
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.<init>(r19);
        throw r18;
    L_0x04c5:
        r13 = 0;
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.Json.readValue(java.lang.Class, java.lang.Class, com.badlogic.gdx.utils.JsonValue):T");
    }

    private String convertToString(Object object) {
        if (object instanceof Class) {
            return ((Class) object).getName();
        }
        return String.valueOf(object);
    }

    private Object newInstance(Class type) {
        try {
            return ClassReflection.newInstance(type);
        } catch (Exception e) {
            Exception ex = e;
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, new Class[0]);
                constructor.setAccessible(true);
                return constructor.newInstance(new Object[0]);
            } catch (SecurityException e2) {
                throw new SerializationException("Error constructing instance of class: " + type.getName(), ex);
            } catch (ReflectionException e3) {
                if (type.isEnum()) {
                    return type.getEnumConstants()[0];
                }
                if (type.isArray()) {
                    throw new SerializationException("Encountered JSON object when expected array of type: " + type.getName(), ex);
                } else if (!ClassReflection.isMemberClass(type) || ClassReflection.isStaticClass(type)) {
                    throw new SerializationException("Class cannot be created (missing no-arg constructor): " + type.getName(), ex);
                } else {
                    throw new SerializationException("Class cannot be created (non-static member class): " + type.getName(), ex);
                }
            } catch (Exception privateConstructorException) {
                ex = privateConstructorException;
                throw new SerializationException("Error constructing instance of class: " + type.getName(), ex);
            }
        }
    }

    public String prettyPrint(Object object) {
        return prettyPrint(object, 0);
    }

    public String prettyPrint(String json) {
        return prettyPrint(json, 0);
    }

    public String prettyPrint(Object object, int singleLineColumns) {
        return prettyPrint(toJson(object), singleLineColumns);
    }

    public String prettyPrint(String json, int singleLineColumns) {
        return new JsonReader().parse(json).prettyPrint(this.outputType, singleLineColumns);
    }
}
