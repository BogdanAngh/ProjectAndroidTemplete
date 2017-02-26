package com.badlogic.gdx.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonReader implements BaseJsonReader {
    private static final byte[] _json_actions;
    private static final byte[] _json_eof_actions;
    private static final short[] _json_index_offsets;
    private static final short[] _json_key_offsets;
    private static final byte[] _json_range_lengths;
    private static final byte[] _json_single_lengths;
    private static final byte[] _json_trans_actions;
    private static final char[] _json_trans_keys;
    private static final byte[] _json_trans_targs;
    static final int json_en_array = 46;
    static final int json_en_main = 1;
    static final int json_en_object = 8;
    static final int json_error = 0;
    static final int json_first_final = 72;
    static final int json_start = 1;
    private JsonValue current;
    private final Array<JsonValue> elements;
    private final Array<JsonValue> lastChild;
    private JsonValue root;

    public JsonReader() {
        this.elements = new Array((int) json_en_object);
        this.lastChild = new Array((int) json_en_object);
    }

    public JsonValue parse(String json) {
        char[] data = json.toCharArray();
        return parse(data, json_error, data.length);
    }

    public JsonValue parse(Reader reader) {
        try {
            char[] data = new char[Place.TYPE_SUBLOCALITY_LEVEL_2];
            int offset = json_error;
            while (true) {
                int length = reader.read(data, offset, data.length - offset);
                if (length == -1) {
                    JsonValue parse = parse(data, json_error, offset);
                    StreamUtils.closeQuietly(reader);
                    return parse;
                } else if (length == 0) {
                    char[] newData = new char[(data.length * 2)];
                    System.arraycopy(data, json_error, newData, json_error, data.length);
                    data = newData;
                } else {
                    offset += length;
                }
            }
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(reader);
        }
    }

    public JsonValue parse(InputStream input) {
        try {
            JsonValue parse = parse(new InputStreamReader(input, "ISO-8859-1"));
            StreamUtils.closeQuietly(input);
            return parse;
        } catch (Throwable ex) {
            throw new SerializationException(ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(input);
        }
    }

    public JsonValue parse(FileHandle file) {
        try {
            return parse(file.read());
        } catch (Exception ex) {
            throw new SerializationException("Error parsing file: " + file, ex);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.badlogic.gdx.utils.JsonValue parse(char[] r47, int r48, int r49) {
        /*
        r46 = this;
        r33 = r48;
        r35 = r49;
        r25 = r35;
        r39 = 0;
        r42 = 4;
        r0 = r42;
        r0 = new int[r0];
        r38 = r0;
        r37 = 0;
        r30 = new com.badlogic.gdx.utils.Array;
        r42 = 8;
        r0 = r30;
        r1 = r42;
        r0.<init>(r1);
        r31 = 0;
        r23 = 0;
        r34 = 0;
        r22 = 0;
        if (r22 == 0) goto L_0x002c;
    L_0x0027:
        r42 = java.lang.System.out;
        r42.println();
    L_0x002c:
        r21 = 1;
        r39 = 0;
        r19 = 0;
        r12 = 0;
        r40 = r39;
    L_0x0035:
        switch(r12) {
            case 0: goto L_0x0078;
            case 1: goto L_0x0084;
            case 2: goto L_0x0484;
            case 3: goto L_0x0038;
            case 4: goto L_0x0494;
            default: goto L_0x0038;
        };
    L_0x0038:
        r39 = r40;
    L_0x003a:
        r0 = r46;
        r0 = r0.root;
        r36 = r0;
        r42 = 0;
        r0 = r42;
        r1 = r46;
        r1.root = r0;
        r42 = 0;
        r0 = r42;
        r1 = r46;
        r1.current = r0;
        r0 = r46;
        r0 = r0.lastChild;
        r42 = r0;
        r42.clear();
        r0 = r33;
        r1 = r35;
        if (r0 >= r1) goto L_0x06e1;
    L_0x005f:
        r28 = 1;
        r27 = 0;
    L_0x0063:
        r0 = r27;
        r1 = r33;
        if (r0 >= r1) goto L_0x06a5;
    L_0x0069:
        r42 = r47[r27];
        r43 = 10;
        r0 = r42;
        r1 = r43;
        if (r0 != r1) goto L_0x0075;
    L_0x0073:
        r28 = r28 + 1;
    L_0x0075:
        r27 = r27 + 1;
        goto L_0x0063;
    L_0x0078:
        r0 = r33;
        r1 = r35;
        if (r0 != r1) goto L_0x0080;
    L_0x007e:
        r12 = 4;
        goto L_0x0035;
    L_0x0080:
        if (r21 != 0) goto L_0x0084;
    L_0x0082:
        r12 = 5;
        goto L_0x0035;
    L_0x0084:
        r42 = _json_key_offsets;	 Catch:{ RuntimeException -> 0x017c }
        r13 = r42[r21];	 Catch:{ RuntimeException -> 0x017c }
        r42 = _json_index_offsets;	 Catch:{ RuntimeException -> 0x017c }
        r19 = r42[r21];	 Catch:{ RuntimeException -> 0x017c }
        r42 = _json_single_lengths;	 Catch:{ RuntimeException -> 0x017c }
        r14 = r42[r21];	 Catch:{ RuntimeException -> 0x017c }
        if (r14 <= 0) goto L_0x009e;
    L_0x0092:
        r15 = r13;
        r42 = r13 + r14;
        r20 = r42 + -1;
    L_0x0097:
        r0 = r20;
        if (r0 >= r15) goto L_0x00d8;
    L_0x009b:
        r13 = r13 + r14;
        r19 = r19 + r14;
    L_0x009e:
        r42 = _json_range_lengths;	 Catch:{ RuntimeException -> 0x017c }
        r14 = r42[r21];	 Catch:{ RuntimeException -> 0x017c }
        if (r14 <= 0) goto L_0x00b1;
    L_0x00a4:
        r15 = r13;
        r42 = r14 << 1;
        r42 = r42 + r13;
        r20 = r42 + -2;
    L_0x00ab:
        r0 = r20;
        if (r0 >= r15) goto L_0x0101;
    L_0x00af:
        r19 = r19 + r14;
    L_0x00b1:
        r42 = _json_trans_targs;	 Catch:{ RuntimeException -> 0x017c }
        r21 = r42[r19];	 Catch:{ RuntimeException -> 0x017c }
        r42 = _json_trans_actions;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r42[r19];	 Catch:{ RuntimeException -> 0x017c }
        if (r42 == 0) goto L_0x0484;
    L_0x00bb:
        r42 = _json_trans_actions;	 Catch:{ RuntimeException -> 0x017c }
        r10 = r42[r19];	 Catch:{ RuntimeException -> 0x017c }
        r42 = _json_actions;	 Catch:{ RuntimeException -> 0x017c }
        r11 = r10 + 1;
        r17 = r42[r10];	 Catch:{ RuntimeException -> 0x017c }
        r18 = r17;
    L_0x00c7:
        r17 = r18 + -1;
        if (r18 <= 0) goto L_0x0484;
    L_0x00cb:
        r42 = _json_actions;	 Catch:{ RuntimeException -> 0x017c }
        r10 = r11 + 1;
        r42 = r42[r11];	 Catch:{ RuntimeException -> 0x017c }
        switch(r42) {
            case 0: goto L_0x0130;
            case 1: goto L_0x0137;
            case 2: goto L_0x013a;
            case 3: goto L_0x0183;
            case 4: goto L_0x01ea;
            case 5: goto L_0x0249;
            case 6: goto L_0x02a8;
            case 7: goto L_0x02ee;
            case 8: goto L_0x0334;
            case 9: goto L_0x0374;
            case 10: goto L_0x03e7;
            case 11: goto L_0x03fc;
            case 12: goto L_0x046f;
            default: goto L_0x00d4;
        };	 Catch:{ RuntimeException -> 0x017c }
    L_0x00d4:
        r18 = r17;
        r11 = r10;
        goto L_0x00c7;
    L_0x00d8:
        r42 = r20 - r15;
        r42 = r42 >> 1;
        r16 = r15 + r42;
        r42 = r47[r33];	 Catch:{ RuntimeException -> 0x017c }
        r43 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43[r16];	 Catch:{ RuntimeException -> 0x017c }
        r0 = r42;
        r1 = r43;
        if (r0 >= r1) goto L_0x00ed;
    L_0x00ea:
        r20 = r16 + -1;
        goto L_0x0097;
    L_0x00ed:
        r42 = r47[r33];	 Catch:{ RuntimeException -> 0x017c }
        r43 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43[r16];	 Catch:{ RuntimeException -> 0x017c }
        r0 = r42;
        r1 = r43;
        if (r0 <= r1) goto L_0x00fc;
    L_0x00f9:
        r15 = r16 + 1;
        goto L_0x0097;
    L_0x00fc:
        r42 = r16 - r13;
        r19 = r19 + r42;
        goto L_0x00b1;
    L_0x0101:
        r42 = r20 - r15;
        r42 = r42 >> 1;
        r42 = r42 & -2;
        r16 = r15 + r42;
        r42 = r47[r33];	 Catch:{ RuntimeException -> 0x017c }
        r43 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43[r16];	 Catch:{ RuntimeException -> 0x017c }
        r0 = r42;
        r1 = r43;
        if (r0 >= r1) goto L_0x0118;
    L_0x0115:
        r20 = r16 + -2;
        goto L_0x00ab;
    L_0x0118:
        r42 = r47[r33];	 Catch:{ RuntimeException -> 0x017c }
        r43 = _json_trans_keys;	 Catch:{ RuntimeException -> 0x017c }
        r44 = r16 + 1;
        r43 = r43[r44];	 Catch:{ RuntimeException -> 0x017c }
        r0 = r42;
        r1 = r43;
        if (r0 <= r1) goto L_0x0129;
    L_0x0126:
        r15 = r16 + 2;
        goto L_0x00ab;
    L_0x0129:
        r42 = r16 - r13;
        r42 = r42 >> 1;
        r19 = r19 + r42;
        goto L_0x00b1;
    L_0x0130:
        r37 = r33;
        r31 = 0;
        r23 = 0;
        goto L_0x00d4;
    L_0x0137:
        r31 = 1;
        goto L_0x00d4;
    L_0x013a:
        r29 = new java.lang.String;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r33 - r37;
        r0 = r29;
        r1 = r47;
        r2 = r37;
        r3 = r42;
        r0.<init>(r1, r2, r3);	 Catch:{ RuntimeException -> 0x017c }
        r37 = r33;
        if (r31 == 0) goto L_0x0155;
    L_0x014d:
        r0 = r46;
        r1 = r29;
        r29 = r0.unescape(r1);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0155:
        if (r22 == 0) goto L_0x0173;
    L_0x0157:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "name: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0173:
        r0 = r30;
        r1 = r29;
        r0.add(r1);	 Catch:{ RuntimeException -> 0x017c }
        goto L_0x00d4;
    L_0x017c:
        r26 = move-exception;
        r39 = r40;
    L_0x017f:
        r34 = r26;
        goto L_0x003a;
    L_0x0183:
        if (r23 != 0) goto L_0x00d4;
    L_0x0185:
        r41 = new java.lang.String;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r33 - r37;
        r0 = r41;
        r1 = r47;
        r2 = r37;
        r3 = r42;
        r0.<init>(r1, r2, r3);	 Catch:{ RuntimeException -> 0x017c }
        r37 = r33;
        if (r31 == 0) goto L_0x01a0;
    L_0x0198:
        r0 = r46;
        r1 = r41;
        r41 = r0.unescape(r1);	 Catch:{ RuntimeException -> 0x017c }
    L_0x01a0:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x01e7;
    L_0x01a8:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x01b0:
        if (r22 == 0) goto L_0x01dc;
    L_0x01b2:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "string: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r41;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x01dc:
        r0 = r46;
        r1 = r29;
        r2 = r41;
        r0.string(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        goto L_0x00d4;
    L_0x01e7:
        r29 = 0;
        goto L_0x01b0;
    L_0x01ea:
        r41 = new java.lang.String;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r33 - r37;
        r0 = r41;
        r1 = r47;
        r2 = r37;
        r3 = r42;
        r0.<init>(r1, r2, r3);	 Catch:{ RuntimeException -> 0x017c }
        r37 = r33;
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x0246;
    L_0x0203:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x020b:
        if (r22 == 0) goto L_0x0237;
    L_0x020d:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "double: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r44 = java.lang.Double.parseDouble(r41);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0237:
        r42 = java.lang.Double.parseDouble(r41);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.number(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        goto L_0x00d4;
    L_0x0246:
        r29 = 0;
        goto L_0x020b;
    L_0x0249:
        r41 = new java.lang.String;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r33 - r37;
        r0 = r41;
        r1 = r47;
        r2 = r37;
        r3 = r42;
        r0.<init>(r1, r2, r3);	 Catch:{ RuntimeException -> 0x017c }
        r37 = r33;
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x02a5;
    L_0x0262:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x026a:
        if (r22 == 0) goto L_0x0296;
    L_0x026c:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "long: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r44 = java.lang.Long.parseLong(r41);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0296:
        r42 = java.lang.Long.parseLong(r41);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.number(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        goto L_0x00d4;
    L_0x02a5:
        r29 = 0;
        goto L_0x026a;
    L_0x02a8:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x02eb;
    L_0x02b0:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x02b8:
        if (r22 == 0) goto L_0x02dc;
    L_0x02ba:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "boolean: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=true";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x02dc:
        r42 = 1;
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.bool(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        r23 = 1;
        goto L_0x00d4;
    L_0x02eb:
        r29 = 0;
        goto L_0x02b8;
    L_0x02ee:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x0331;
    L_0x02f6:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x02fe:
        if (r22 == 0) goto L_0x0322;
    L_0x0300:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "boolean: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=false";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0322:
        r42 = 0;
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.bool(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        r23 = 1;
        goto L_0x00d4;
    L_0x0331:
        r29 = 0;
        goto L_0x02fe;
    L_0x0334:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x0371;
    L_0x033c:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x0344:
        if (r22 == 0) goto L_0x0362;
    L_0x0346:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "null: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0362:
        r42 = 0;
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.string(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        r23 = 1;
        goto L_0x00d4;
    L_0x0371:
        r29 = 0;
        goto L_0x0344;
    L_0x0374:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x03e4;
    L_0x037c:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x0384:
        if (r22 == 0) goto L_0x03a2;
    L_0x0386:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "startObject: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x03a2:
        r0 = r46;
        r1 = r29;
        r0.startObject(r1);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r38;
        r0 = r0.length;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        r0 = r40;
        r1 = r42;
        if (r0 != r1) goto L_0x03d9;
    L_0x03b4:
        r0 = r38;
        r0 = r0.length;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        r42 = r42 * 2;
        r0 = r42;
        r0 = new int[r0];	 Catch:{ RuntimeException -> 0x017c }
        r32 = r0;
        r42 = 0;
        r43 = 0;
        r0 = r38;
        r0 = r0.length;	 Catch:{ RuntimeException -> 0x017c }
        r44 = r0;
        r0 = r38;
        r1 = r42;
        r2 = r32;
        r3 = r43;
        r4 = r44;
        java.lang.System.arraycopy(r0, r1, r2, r3, r4);	 Catch:{ RuntimeException -> 0x017c }
        r38 = r32;
    L_0x03d9:
        r39 = r40 + 1;
        r38[r40] = r21;	 Catch:{ RuntimeException -> 0x0747 }
        r21 = 8;
        r12 = 2;
        r40 = r39;
        goto L_0x0035;
    L_0x03e4:
        r29 = 0;
        goto L_0x0384;
    L_0x03e7:
        if (r22 == 0) goto L_0x03f0;
    L_0x03e9:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = "endObject";
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x03f0:
        r46.pop();	 Catch:{ RuntimeException -> 0x017c }
        r39 = r40 + -1;
        r21 = r38[r39];	 Catch:{ RuntimeException -> 0x0747 }
        r12 = 2;
        r40 = r39;
        goto L_0x0035;
    L_0x03fc:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x046c;
    L_0x0404:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x040c:
        if (r22 == 0) goto L_0x042a;
    L_0x040e:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "startArray: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x042a:
        r0 = r46;
        r1 = r29;
        r0.startArray(r1);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r38;
        r0 = r0.length;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        r0 = r40;
        r1 = r42;
        if (r0 != r1) goto L_0x0461;
    L_0x043c:
        r0 = r38;
        r0 = r0.length;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        r42 = r42 * 2;
        r0 = r42;
        r0 = new int[r0];	 Catch:{ RuntimeException -> 0x017c }
        r32 = r0;
        r42 = 0;
        r43 = 0;
        r0 = r38;
        r0 = r0.length;	 Catch:{ RuntimeException -> 0x017c }
        r44 = r0;
        r0 = r38;
        r1 = r42;
        r2 = r32;
        r3 = r43;
        r4 = r44;
        java.lang.System.arraycopy(r0, r1, r2, r3, r4);	 Catch:{ RuntimeException -> 0x017c }
        r38 = r32;
    L_0x0461:
        r39 = r40 + 1;
        r38[r40] = r21;	 Catch:{ RuntimeException -> 0x0747 }
        r21 = 46;
        r12 = 2;
        r40 = r39;
        goto L_0x0035;
    L_0x046c:
        r29 = 0;
        goto L_0x040c;
    L_0x046f:
        if (r22 == 0) goto L_0x0478;
    L_0x0471:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = "endArray";
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0478:
        r46.pop();	 Catch:{ RuntimeException -> 0x017c }
        r39 = r40 + -1;
        r21 = r38[r39];	 Catch:{ RuntimeException -> 0x0747 }
        r12 = 2;
        r40 = r39;
        goto L_0x0035;
    L_0x0484:
        if (r21 != 0) goto L_0x0489;
    L_0x0486:
        r12 = 5;
        goto L_0x0035;
    L_0x0489:
        r33 = r33 + 1;
        r0 = r33;
        r1 = r35;
        if (r0 == r1) goto L_0x0494;
    L_0x0491:
        r12 = 1;
        goto L_0x0035;
    L_0x0494:
        r0 = r33;
        r1 = r25;
        if (r0 != r1) goto L_0x0038;
    L_0x049a:
        r42 = _json_eof_actions;	 Catch:{ RuntimeException -> 0x017c }
        r6 = r42[r21];	 Catch:{ RuntimeException -> 0x017c }
        r42 = _json_actions;	 Catch:{ RuntimeException -> 0x017c }
        r7 = r6 + 1;
        r8 = r42[r6];	 Catch:{ RuntimeException -> 0x017c }
        r9 = r8;
    L_0x04a5:
        r8 = r9 + -1;
        if (r9 <= 0) goto L_0x0038;
    L_0x04a9:
        r42 = _json_actions;	 Catch:{ RuntimeException -> 0x017c }
        r6 = r7 + 1;
        r42 = r42[r7];	 Catch:{ RuntimeException -> 0x017c }
        switch(r42) {
            case 3: goto L_0x04b5;
            case 4: goto L_0x051b;
            case 5: goto L_0x057a;
            case 6: goto L_0x05d9;
            case 7: goto L_0x061f;
            case 8: goto L_0x0665;
            default: goto L_0x04b2;
        };	 Catch:{ RuntimeException -> 0x017c }
    L_0x04b2:
        r9 = r8;
        r7 = r6;
        goto L_0x04a5;
    L_0x04b5:
        if (r23 != 0) goto L_0x04b2;
    L_0x04b7:
        r41 = new java.lang.String;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r33 - r37;
        r0 = r41;
        r1 = r47;
        r2 = r37;
        r3 = r42;
        r0.<init>(r1, r2, r3);	 Catch:{ RuntimeException -> 0x017c }
        r37 = r33;
        if (r31 == 0) goto L_0x04d2;
    L_0x04ca:
        r0 = r46;
        r1 = r41;
        r41 = r0.unescape(r1);	 Catch:{ RuntimeException -> 0x017c }
    L_0x04d2:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x0518;
    L_0x04da:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x04e2:
        if (r22 == 0) goto L_0x050e;
    L_0x04e4:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "string: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r41;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x050e:
        r0 = r46;
        r1 = r29;
        r2 = r41;
        r0.string(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        goto L_0x04b2;
    L_0x0518:
        r29 = 0;
        goto L_0x04e2;
    L_0x051b:
        r41 = new java.lang.String;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r33 - r37;
        r0 = r41;
        r1 = r47;
        r2 = r37;
        r3 = r42;
        r0.<init>(r1, r2, r3);	 Catch:{ RuntimeException -> 0x017c }
        r37 = r33;
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x0577;
    L_0x0534:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x053c:
        if (r22 == 0) goto L_0x0568;
    L_0x053e:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "double: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r44 = java.lang.Double.parseDouble(r41);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0568:
        r42 = java.lang.Double.parseDouble(r41);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.number(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        goto L_0x04b2;
    L_0x0577:
        r29 = 0;
        goto L_0x053c;
    L_0x057a:
        r41 = new java.lang.String;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r33 - r37;
        r0 = r41;
        r1 = r47;
        r2 = r37;
        r3 = r42;
        r0.<init>(r1, r2, r3);	 Catch:{ RuntimeException -> 0x017c }
        r37 = r33;
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x05d6;
    L_0x0593:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x059b:
        if (r22 == 0) goto L_0x05c7;
    L_0x059d:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "long: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r44 = java.lang.Long.parseLong(r41);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x05c7:
        r42 = java.lang.Long.parseLong(r41);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.number(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        goto L_0x04b2;
    L_0x05d6:
        r29 = 0;
        goto L_0x059b;
    L_0x05d9:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x061c;
    L_0x05e1:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x05e9:
        if (r22 == 0) goto L_0x060d;
    L_0x05eb:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "boolean: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=true";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x060d:
        r42 = 1;
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.bool(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        r23 = 1;
        goto L_0x04b2;
    L_0x061c:
        r29 = 0;
        goto L_0x05e9;
    L_0x061f:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x0662;
    L_0x0627:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x062f:
        if (r22 == 0) goto L_0x0653;
    L_0x0631:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "boolean: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r44 = "=false";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0653:
        r42 = 0;
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.bool(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        r23 = 1;
        goto L_0x04b2;
    L_0x0662:
        r29 = 0;
        goto L_0x062f;
    L_0x0665:
        r0 = r30;
        r0 = r0.size;	 Catch:{ RuntimeException -> 0x017c }
        r42 = r0;
        if (r42 <= 0) goto L_0x06a2;
    L_0x066d:
        r42 = r30.pop();	 Catch:{ RuntimeException -> 0x017c }
        r42 = (java.lang.String) r42;	 Catch:{ RuntimeException -> 0x017c }
        r29 = r42;
    L_0x0675:
        if (r22 == 0) goto L_0x0693;
    L_0x0677:
        r42 = java.lang.System.out;	 Catch:{ RuntimeException -> 0x017c }
        r43 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x017c }
        r43.<init>();	 Catch:{ RuntimeException -> 0x017c }
        r44 = "null: ";
        r43 = r43.append(r44);	 Catch:{ RuntimeException -> 0x017c }
        r0 = r43;
        r1 = r29;
        r43 = r0.append(r1);	 Catch:{ RuntimeException -> 0x017c }
        r43 = r43.toString();	 Catch:{ RuntimeException -> 0x017c }
        r42.println(r43);	 Catch:{ RuntimeException -> 0x017c }
    L_0x0693:
        r42 = 0;
        r0 = r46;
        r1 = r29;
        r2 = r42;
        r0.string(r1, r2);	 Catch:{ RuntimeException -> 0x017c }
        r23 = 1;
        goto L_0x04b2;
    L_0x06a2:
        r29 = 0;
        goto L_0x0675;
    L_0x06a5:
        r42 = new com.badlogic.gdx.utils.SerializationException;
        r43 = new java.lang.StringBuilder;
        r43.<init>();
        r44 = "Error parsing JSON on line ";
        r43 = r43.append(r44);
        r0 = r43;
        r1 = r28;
        r43 = r0.append(r1);
        r44 = " near: ";
        r43 = r43.append(r44);
        r44 = new java.lang.String;
        r45 = r35 - r33;
        r0 = r44;
        r1 = r47;
        r2 = r33;
        r3 = r45;
        r0.<init>(r1, r2, r3);
        r43 = r43.append(r44);
        r43 = r43.toString();
        r0 = r42;
        r1 = r43;
        r2 = r34;
        r0.<init>(r1, r2);
        throw r42;
    L_0x06e1:
        r0 = r46;
        r0 = r0.elements;
        r42 = r0;
        r0 = r42;
        r0 = r0.size;
        r42 = r0;
        if (r42 == 0) goto L_0x071c;
    L_0x06ef:
        r0 = r46;
        r0 = r0.elements;
        r42 = r0;
        r24 = r42.peek();
        r24 = (com.badlogic.gdx.utils.JsonValue) r24;
        r0 = r46;
        r0 = r0.elements;
        r42 = r0;
        r42.clear();
        if (r24 == 0) goto L_0x0714;
    L_0x0706:
        r42 = r24.isObject();
        if (r42 == 0) goto L_0x0714;
    L_0x070c:
        r42 = new com.badlogic.gdx.utils.SerializationException;
        r43 = "Error parsing JSON, unmatched brace.";
        r42.<init>(r43);
        throw r42;
    L_0x0714:
        r42 = new com.badlogic.gdx.utils.SerializationException;
        r43 = "Error parsing JSON, unmatched bracket.";
        r42.<init>(r43);
        throw r42;
    L_0x071c:
        if (r34 == 0) goto L_0x0746;
    L_0x071e:
        r42 = new com.badlogic.gdx.utils.SerializationException;
        r43 = new java.lang.StringBuilder;
        r43.<init>();
        r44 = "Error parsing JSON: ";
        r43 = r43.append(r44);
        r44 = new java.lang.String;
        r0 = r44;
        r1 = r47;
        r0.<init>(r1);
        r43 = r43.append(r44);
        r43 = r43.toString();
        r0 = r42;
        r1 = r43;
        r2 = r34;
        r0.<init>(r1, r2);
        throw r42;
    L_0x0746:
        return r36;
    L_0x0747:
        r26 = move-exception;
        goto L_0x017f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.JsonReader.parse(char[], int, int):com.badlogic.gdx.utils.JsonValue");
    }

    private static byte[] init__json_actions_0() {
        return new byte[]{(byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 3, (byte) 1, (byte) 4, (byte) 1, (byte) 5, (byte) 1, (byte) 9, (byte) 1, (byte) 10, (byte) 1, (byte) 11, (byte) 1, (byte) 12, (byte) 2, (byte) 0, (byte) 2, (byte) 2, (byte) 0, (byte) 3, (byte) 2, (byte) 3, (byte) 10, (byte) 2, (byte) 3, (byte) 12, (byte) 2, (byte) 4, (byte) 10, (byte) 2, (byte) 4, (byte) 12, (byte) 2, (byte) 5, (byte) 10, (byte) 2, (byte) 5, (byte) 12, (byte) 2, (byte) 6, (byte) 3, (byte) 2, (byte) 7, (byte) 3, (byte) 2, (byte) 8, (byte) 3, (byte) 3, (byte) 6, (byte) 3, (byte) 10, (byte) 3, (byte) 6, (byte) 3, (byte) 12, (byte) 3, (byte) 7, (byte) 3, (byte) 10, (byte) 3, (byte) 7, (byte) 3, (byte) 12, (byte) 3, (byte) 8, (byte) 3, (byte) 10, (byte) 3, (byte) 8, (byte) 3, (byte) 12};
    }

    static {
        _json_actions = init__json_actions_0();
        _json_key_offsets = init__json_key_offsets_0();
        _json_trans_keys = init__json_trans_keys_0();
        _json_single_lengths = init__json_single_lengths_0();
        _json_range_lengths = init__json_range_lengths_0();
        _json_index_offsets = init__json_index_offsets_0();
        _json_trans_targs = init__json_trans_targs_0();
        _json_trans_actions = init__json_trans_actions_0();
        _json_eof_actions = init__json_eof_actions_0();
    }

    private static short[] init__json_key_offsets_0() {
        return new short[]{(short) 0, (short) 0, (short) 18, (short) 20, (short) 22, (short) 31, (short) 33, (short) 37, (short) 39, (short) 54, (short) 56, (short) 58, (short) 62, (short) 80, (short) 82, (short) 84, (short) 89, (short) 103, (short) 110, (short) 112, (short) 115, (short) 123, (short) 127, (short) 129, (short) 135, (short) 144, (short) 151, (short) 153, (short) 161, (short) 170, (short) 174, (short) 176, (short) 183, (short) 191, (short) 199, (short) 207, (short) 215, (short) 222, (short) 230, (short) 238, (short) 246, (short) 253, (short) 261, (short) 269, (short) 277, (short) 284, (short) 293, (short) 313, (short) 315, (short) 317, (short) 322, (short) 341, (short) 348, (short) 350, (short) 358, (short) 367, (short) 371, (short) 373, (short) 380, (short) 388, (short) 396, (short) 404, (short) 412, (short) 419, (short) 427, (short) 435, (short) 443, (short) 450, (short) 458, (short) 466, (short) 474, (short) 481, (short) 490, (short) 493, (short) 500, (short) 506, (short) 513, (short) 518, (short) 526, (short) 534, (short) 542, (short) 550, (short) 557, (short) 565, (short) 573, (short) 581, (short) 588, (short) 596, (short) 604, (short) 612, (short) 619, (short) 619};
    }

    private static char[] init__json_trans_keys_0() {
        return new char[]{' ', '\"', '$', '-', '[', '_', 'f', 'n', 't', '{', '\t', '\r', '0', '9', 'A', 'Z', 'a', 'z', '\"', '\\', '\"', '\\', '\"', '/', '\\', 'b', 'f', 'n', 'r', 't', 'u', '0', '9', '+', '-', '0', '9', '0', '9', ' ', '\"', '$', ',', '-', '_', '}', '\t', '\r', '0', '9', 'A', 'Z', 'a', 'z', '\"', '\\', '\"', '\\', ' ', ':', '\t', '\r', ' ', '\"', '$', '-', '[', '_', 'f', 'n', 't', '{', '\t', '\r', '0', '9', 'A', 'Z', 'a', 'z', '\"', '\\', '\"', '\\', ' ', ',', '}', '\t', '\r', ' ', '\"', '$', '-', '_', '}', '\t', '\r', '0', '9', 'A', 'Z', 'a', 'z', ' ', ',', ':', ']', '}', '\t', '\r', '0', '9', '.', '0', '9', ' ', ':', 'E', 'e', '\t', '\r', '0', '9', '+', '-', '0', '9', '0', '9', ' ', ':', '\t', '\r', '0', '9', '\"', '/', '\\', 'b', 'f', 'n', 'r', 't', 'u', ' ', ',', ':', ']', '}', '\t', '\r', '0', '9', ' ', ',', '.', '}', '\t', '\r', '0', '9', ' ', ',', 'E', 'e', '}', '\t', '\r', '0', '9', '+', '-', '0', '9', '0', '9', ' ', ',', '}', '\t', '\r', '0', '9', ' ', ',', ':', ']', 'a', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', 's', '}', '\t', '\r', ' ', ',', ':', ']', 'e', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', ' ', ',', ':', ']', 'u', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', ' ', ',', ':', ']', 'r', '}', '\t', '\r', ' ', ',', ':', ']', 'u', '}', '\t', '\r', ' ', ',', ':', ']', 'e', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', '\"', '/', '\\', 'b', 'f', 'n', 'r', 't', 'u', ' ', '\"', '$', ',', '-', '[', ']', '_', 'f', 'n', 't', '{', '\t', '\r', '0', '9', 'A', 'Z', 'a', 'z', '\"', '\\', '\"', '\\', ' ', ',', ']', '\t', '\r', ' ', '\"', '$', '-', '[', ']', '_', 'f', 'n', 't', '{', '\t', '\r', '0', '9', 'A', 'Z', 'a', 'z', ' ', ',', ':', ']', '}', '\t', '\r', '0', '9', ' ', ',', '.', ']', '\t', '\r', '0', '9', ' ', ',', 'E', ']', 'e', '\t', '\r', '0', '9', '+', '-', '0', '9', '0', '9', ' ', ',', ']', '\t', '\r', '0', '9', ' ', ',', ':', ']', 'a', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', 's', '}', '\t', '\r', ' ', ',', ':', ']', 'e', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', ' ', ',', ':', ']', 'u', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', ' ', ',', ':', ']', 'r', '}', '\t', '\r', ' ', ',', ':', ']', 'u', '}', '\t', '\r', ' ', ',', ':', ']', 'e', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', '\"', '/', '\\', 'b', 'f', 'n', 'r', 't', 'u', ' ', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', ' ', '.', '\t', '\r', '0', '9', ' ', 'E', 'e', '\t', '\r', '0', '9', ' ', '\t', '\r', '0', '9', ' ', ',', ':', ']', 'a', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', 's', '}', '\t', '\r', ' ', ',', ':', ']', 'e', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', ' ', ',', ':', ']', 'u', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', 'l', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', ' ', ',', ':', ']', 'r', '}', '\t', '\r', ' ', ',', ':', ']', 'u', '}', '\t', '\r', ' ', ',', ':', ']', 'e', '}', '\t', '\r', ' ', ',', ':', ']', '}', '\t', '\r', '\u0000'};
    }

    private static byte[] init__json_single_lengths_0() {
        return new byte[]{(byte) 0, (byte) 10, (byte) 2, (byte) 2, (byte) 7, (byte) 0, (byte) 2, (byte) 0, (byte) 7, (byte) 2, (byte) 2, (byte) 2, (byte) 10, (byte) 2, (byte) 2, (byte) 3, (byte) 6, (byte) 5, (byte) 0, (byte) 1, (byte) 4, (byte) 2, (byte) 0, (byte) 2, (byte) 7, (byte) 5, (byte) 0, (byte) 4, (byte) 5, (byte) 2, (byte) 0, (byte) 3, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 7, (byte) 12, (byte) 2, (byte) 2, (byte) 3, (byte) 11, (byte) 5, (byte) 0, (byte) 4, (byte) 5, (byte) 2, (byte) 0, (byte) 3, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 7, (byte) 1, (byte) 5, (byte) 2, (byte) 3, (byte) 1, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 0, (byte) 0};
    }

    private static byte[] init__json_range_lengths_0() {
        return new byte[]{(byte) 0, (byte) 4, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 4, (byte) 0, (byte) 0, (byte) 1, (byte) 4, (byte) 0, (byte) 0, (byte) 1, (byte) 4, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 4, (byte) 0, (byte) 0, (byte) 1, (byte) 4, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0};
    }

    private static short[] init__json_index_offsets_0() {
        return new short[]{(short) 0, (short) 0, (short) 15, (short) 18, (short) 21, (short) 30, (short) 32, (short) 36, (short) 38, (short) 50, (short) 53, (short) 56, (short) 60, (short) 75, (short) 78, (short) 81, (short) 86, (short) 97, (short) 104, (short) 106, (short) 109, (short) 116, (short) 120, (short) 122, (short) 127, (short) 136, (short) 143, (short) 145, (short) 152, (short) 160, (short) 164, (short) 166, (short) 172, (short) 180, (short) 188, (short) 196, (short) 204, (short) 211, (short) 219, (short) 227, (short) 235, (short) 242, (short) 250, (short) 258, (short) 266, (short) 273, (short) 282, (short) 299, (short) 302, (short) 305, (short) 310, (short) 326, (short) 333, (short) 335, (short) 342, (short) 350, (short) 354, (short) 356, (short) 362, (short) 370, (short) 378, (short) 386, (short) 394, (short) 401, (short) 409, (short) 417, (short) 425, (short) 432, (short) 440, (short) 448, (short) 456, (short) 463, (short) 472, (short) 475, (short) 482, (short) 487, (short) 493, (short) 497, (short) 505, (short) 513, (short) 521, (short) 529, (short) 536, (short) 544, (short) 552, (short) 560, (short) 567, (short) 575, (short) 583, (short) 591, (short) 598, (short) 599};
    }

    private static byte[] init__json_trans_targs_0() {
        return new byte[]{(byte) 1, (byte) 2, (byte) 73, (byte) 5, (byte) 72, (byte) 73, (byte) 77, (byte) 82, (byte) 86, (byte) 72, (byte) 1, (byte) 74, (byte) 73, (byte) 73, (byte) 0, (byte) 72, (byte) 4, (byte) 3, (byte) 72, (byte) 4, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 0, (byte) 74, (byte) 0, (byte) 7, (byte) 7, (byte) 76, (byte) 0, (byte) 76, (byte) 0, (byte) 8, (byte) 9, (byte) 17, (byte) 16, (byte) 18, (byte) 17, (byte) 90, (byte) 8, (byte) 17, (byte) 17, (byte) 17, (byte) 0, (byte) 11, (byte) 45, (byte) 10, (byte) 11, (byte) 45, (byte) 10, (byte) 11, (byte) 12, (byte) 11, (byte) 0, (byte) 12, (byte) 13, (byte) 25, (byte) 26, (byte) 15, (byte) 25, (byte) 32, (byte) 37, (byte) 41, (byte) 15, (byte) 12, (byte) 27, (byte) 25, (byte) 25, (byte) 0, (byte) 15, (byte) 24, (byte) 14, (byte) 15, (byte) 24, (byte) 14, (byte) 15, (byte) 16, (byte) 90, (byte) 15, (byte) 0, (byte) 16, (byte) 9, (byte) 17, (byte) 18, (byte) 17, (byte) 90, (byte) 16, (byte) 17, (byte) 17, (byte) 17, (byte) 0, (byte) 11, (byte) 0, (byte) 12, (byte) 0, (byte) 0, (byte) 11, (byte) 17, (byte) 19, (byte) 0, (byte) 20, (byte) 19, (byte) 0, (byte) 11, (byte) 12, (byte) 21, (byte) 21, (byte) 11, (byte) 20, (byte) 0, (byte) 22, (byte) 22, (byte) 23, (byte) 0, (byte) 23, (byte) 0, (byte) 11, (byte) 12, (byte) 11, (byte) 23, (byte) 0, (byte) 14, (byte) 14, (byte) 14, (byte) 14, (byte) 14, (byte) 14, (byte) 14, (byte) 14, (byte) 0, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 90, (byte) 15, (byte) 25, (byte) 27, (byte) 0, (byte) 15, (byte) 16, (byte) 28, (byte) 90, (byte) 15, (byte) 27, (byte) 0, (byte) 15, (byte) 16, (byte) 29, (byte) 29, (byte) 90, (byte) 15, (byte) 28, (byte) 0, (byte) 30, (byte) 30, (byte) 31, (byte) 0, (byte) 31, (byte) 0, (byte) 15, (byte) 16, (byte) 90, (byte) 15, (byte) 31, (byte) 0, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 33, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 34, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 35, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 36, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 38, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 39, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 40, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 42, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 43, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 44, (byte) 90, (byte) 15, (byte) 25, (byte) 15, (byte) 16, (byte) 0, (byte) 0, (byte) 90, (byte) 15, (byte) 25, (byte) 10, (byte) 10, (byte) 10, (byte) 10, (byte) 10, (byte) 10, (byte) 10, (byte) 10, (byte) 0, (byte) 46, (byte) 47, (byte) 51, (byte) 50, (byte) 52, (byte) 49, (byte) 91, (byte) 51, (byte) 58, (byte) 63, (byte) 67, (byte) 49, (byte) 46, (byte) 53, (byte) 51, (byte) 51, (byte) 0, (byte) 49, (byte) 71, (byte) 48, (byte) 49, (byte) 71, (byte) 48, (byte) 49, (byte) 50, (byte) 91, (byte) 49, (byte) 0, (byte) 50, (byte) 47, (byte) 51, (byte) 52, (byte) 49, (byte) 91, (byte) 51, (byte) 58, (byte) 63, (byte) 67, (byte) 49, (byte) 50, (byte) 53, (byte) 51, (byte) 51, (byte) 0, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 0, (byte) 49, (byte) 51, (byte) 53, (byte) 0, (byte) 49, (byte) 50, (byte) 54, (byte) 91, (byte) 49, (byte) 53, (byte) 0, (byte) 49, (byte) 50, (byte) 55, (byte) 91, (byte) 55, (byte) 49, (byte) 54, (byte) 0, (byte) 56, (byte) 56, (byte) 57, (byte) 0, (byte) 57, (byte) 0, (byte) 49, (byte) 50, (byte) 91, (byte) 49, (byte) 57, (byte) 0, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 59, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 60, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 61, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 62, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 64, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 65, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 66, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 68, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 69, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 70, (byte) 0, (byte) 49, (byte) 51, (byte) 49, (byte) 50, (byte) 0, (byte) 91, (byte) 0, (byte) 49, (byte) 51, (byte) 48, (byte) 48, (byte) 48, (byte) 48, (byte) 48, (byte) 48, (byte) 48, (byte) 48, (byte) 0, (byte) 72, (byte) 72, (byte) 0, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 75, (byte) 72, (byte) 74, (byte) 0, (byte) 72, (byte) 6, (byte) 6, (byte) 72, (byte) 75, (byte) 0, (byte) 72, (byte) 72, (byte) 76, (byte) 0, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 78, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 79, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 80, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 81, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 83, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 84, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 85, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 87, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 88, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 89, (byte) 0, (byte) 72, (byte) 73, (byte) 72, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 72, (byte) 73, (byte) 0, (byte) 0, (byte) 0};
    }

    private static byte[] init__json_trans_actions_0() {
        return new byte[]{(byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 17, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 13, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 24, (byte) 1, (byte) 1, (byte) 7, (byte) 0, (byte) 0, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 15, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 21, (byte) 1, (byte) 1, (byte) 5, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 17, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 13, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 24, (byte) 1, (byte) 1, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 15, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 15, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 5, (byte) 0, (byte) 5, (byte) 0, (byte) 0, (byte) 5, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 5, (byte) 5, (byte) 0, (byte) 0, (byte) 5, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 5, (byte) 5, (byte) 5, (byte) 0, (byte) 0, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 11, (byte) 11, (byte) 0, (byte) 39, (byte) 11, (byte) 0, (byte) 0, (byte) 9, (byte) 9, (byte) 0, (byte) 0, (byte) 33, (byte) 9, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 9, (byte) 9, (byte) 33, (byte) 9, (byte) 0, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 48, (byte) 48, (byte) 0, (byte) 0, (byte) 62, (byte) 48, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 51, (byte) 51, (byte) 0, (byte) 0, (byte) 70, (byte) 51, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 27, (byte) 7, (byte) 0, (byte) 45, (byte) 45, (byte) 0, (byte) 0, (byte) 54, (byte) 45, (byte) 0, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 17, (byte) 19, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 13, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 24, (byte) 1, (byte) 1, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 19, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 17, (byte) 19, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 13, (byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 11, (byte) 11, (byte) 0, (byte) 42, (byte) 11, (byte) 0, (byte) 0, (byte) 9, (byte) 9, (byte) 0, (byte) 36, (byte) 0, (byte) 9, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 9, (byte) 9, (byte) 36, (byte) 9, (byte) 0, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 48, (byte) 48, (byte) 0, (byte) 66, (byte) 0, (byte) 48, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 51, (byte) 51, (byte) 0, (byte) 74, (byte) 0, (byte) 51, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 7, (byte) 0, (byte) 30, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 45, (byte) 45, (byte) 0, (byte) 58, (byte) 0, (byte) 45, (byte) 0, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 11, (byte) 0, (byte) 11, (byte) 0, (byte) 0, (byte) 9, (byte) 0, (byte) 0, (byte) 9, (byte) 0, (byte) 0, (byte) 9, (byte) 9, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 48, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 48, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 51, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 51, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 45, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 45, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    }

    private static byte[] init__json_eof_actions_0() {
        return new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 11, (byte) 9, (byte) 9, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 48, (byte) 7, (byte) 7, (byte) 7, (byte) 51, (byte) 7, (byte) 7, (byte) 7, (byte) 45, (byte) 0, (byte) 0};
    }

    private void addChild(String name, JsonValue child) {
        child.setName(name);
        if (this.current == null) {
            this.current = child;
            this.root = child;
        } else if (this.current.isArray() || this.current.isObject()) {
            if (this.current.size == 0) {
                this.current.child = child;
            } else {
                JsonValue last = (JsonValue) this.lastChild.pop();
                last.next = child;
                child.prev = last;
            }
            this.lastChild.add(child);
            JsonValue jsonValue = this.current;
            jsonValue.size += json_start;
        } else {
            this.root = this.current;
        }
    }

    protected void startObject(String name) {
        JsonValue value = new JsonValue(ValueType.object);
        if (this.current != null) {
            addChild(name, value);
        }
        this.elements.add(value);
        this.current = value;
    }

    protected void startArray(String name) {
        JsonValue value = new JsonValue(ValueType.array);
        if (this.current != null) {
            addChild(name, value);
        }
        this.elements.add(value);
        this.current = value;
    }

    protected void pop() {
        this.root = (JsonValue) this.elements.pop();
        if (this.current.size > 0) {
            this.lastChild.pop();
        }
        this.current = this.elements.size > 0 ? (JsonValue) this.elements.peek() : null;
    }

    protected void string(String name, String value) {
        addChild(name, new JsonValue(value));
    }

    protected void number(String name, double value) {
        addChild(name, new JsonValue(value));
    }

    protected void number(String name, long value) {
        addChild(name, new JsonValue(value));
    }

    protected void bool(String name, boolean value) {
        addChild(name, new JsonValue(value));
    }

    private String unescape(String value) {
        int i;
        int length = value.length();
        StringBuilder buffer = new StringBuilder(length + 16);
        int i2 = json_error;
        while (i2 < length) {
            i = i2 + json_start;
            char c = value.charAt(i2);
            if (c != '\\') {
                buffer.append(c);
                i2 = i;
            } else if (i == length) {
                return buffer.toString();
            } else {
                i2 = i + json_start;
                c = value.charAt(i);
                if (c == 'u') {
                    buffer.append(Character.toChars(Integer.parseInt(value.substring(i2, i2 + 4), 16)));
                    i2 += 4;
                } else {
                    switch (c) {
                        case Place.TYPE_ESTABLISHMENT /*34*/:
                        case Place.TYPE_HEALTH /*47*/:
                        case Place.TYPE_TRAIN_STATION /*92*/:
                            break;
                        case Keys.BUTTON_C /*98*/:
                            c = '\b';
                            break;
                        case LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY /*102*/:
                            c = '\f';
                            break;
                        case Keys.BUTTON_MODE /*110*/:
                            c = '\n';
                            break;
                        case 'r':
                            c = '\r';
                            break;
                        case 't':
                            c = '\t';
                            break;
                        default:
                            throw new SerializationException("Illegal escaped character: \\" + c);
                    }
                    buffer.append(c);
                }
            }
        }
        i = i2;
        return buffer.toString();
    }
}
