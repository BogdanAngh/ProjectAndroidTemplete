package com.badlogic.gdx.utils;

import com.badlogic.gdx.Input.Keys;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.example.games.basegameutils.GameHelper;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataInput extends DataInputStream {
    private char[] chars;

    public DataInput(InputStream in) {
        super(in);
        this.chars = new char[32];
    }

    public int readInt(boolean optimizePositive) throws IOException {
        int b = read();
        int result = b & 127;
        if ((b & Cast.MAX_NAMESPACE_LENGTH) != 0) {
            b = read();
            result |= (b & 127) << 7;
            if ((b & Cast.MAX_NAMESPACE_LENGTH) != 0) {
                b = read();
                result |= (b & 127) << 14;
                if ((b & Cast.MAX_NAMESPACE_LENGTH) != 0) {
                    b = read();
                    result |= (b & 127) << 21;
                    if ((b & Cast.MAX_NAMESPACE_LENGTH) != 0) {
                        result |= (read() & 127) << 28;
                    }
                }
            }
        }
        return optimizePositive ? result : (result >>> 1) ^ (-(result & 1));
    }

    public String readString() throws IOException {
        int charCount = readInt(true);
        switch (charCount) {
            case GameHelper.CLIENT_NONE /*0*/:
                return null;
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return "";
            default:
                charCount--;
                if (this.chars.length < charCount) {
                    this.chars = new char[charCount];
                }
                char[] chars = this.chars;
                int b = 0;
                int i = 0;
                while (i < charCount) {
                    b = read();
                    if (b > 127) {
                        if (i < charCount) {
                            readUtf8_slow(charCount, i, b);
                        }
                        return new String(chars, 0, charCount);
                    }
                    int charIndex = i + 1;
                    chars[i] = (char) b;
                    i = charIndex;
                }
                if (i < charCount) {
                    readUtf8_slow(charCount, i, b);
                }
                return new String(chars, 0, charCount);
        }
    }

    private void readUtf8_slow(int charCount, int charIndex, int b) throws IOException {
        char[] chars = this.chars;
        while (true) {
            switch (b >> 4) {
                case GameHelper.CLIENT_NONE /*0*/:
                case CompletionEvent.STATUS_FAILURE /*1*/:
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                case CompletionEvent.STATUS_CANCELED /*3*/:
                case GameHelper.CLIENT_APPSTATE /*4*/:
                case Place.TYPE_ART_GALLERY /*5*/:
                case Place.TYPE_ATM /*6*/:
                case Place.TYPE_BAKERY /*7*/:
                    chars[charIndex] = (char) b;
                    break;
                case Place.TYPE_BOOK_STORE /*12*/:
                case ConnectionsStatusCodes.STATUS_ERROR /*13*/:
                    chars[charIndex] = (char) (((b & 31) << 6) | (read() & 63));
                    break;
                case Place.TYPE_BUS_STATION /*14*/:
                    chars[charIndex] = (char) ((((b & 15) << 12) | ((read() & 63) << 6)) | (read() & 63));
                    break;
            }
            charIndex++;
            if (charIndex < charCount) {
                b = read() & Keys.F12;
            } else {
                return;
            }
        }
    }
}
