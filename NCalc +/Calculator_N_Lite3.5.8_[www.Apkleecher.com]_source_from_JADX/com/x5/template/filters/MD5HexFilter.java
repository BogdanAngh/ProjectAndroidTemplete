package com.x5.template.filters;

import com.x5.template.Chunk;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HexFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        return text == null ? null : md5Hex(text);
    }

    public String getFilterName() {
        return "md5";
    }

    public String[] getFilterAliases() {
        return new String[]{"md5hex"};
    }

    public static String md5Hex(String text) {
        return md5(text, false);
    }

    public static String md5Base64(String text) {
        return md5(text, true);
    }

    public static String md5(String text, boolean base64) {
        return hashCrypt("MD5", text, base64);
    }

    public static String hashCrypt(String alg, String text, boolean base64) {
        byte[] textBytes;
        try {
            textBytes = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            textBytes = text.getBytes();
        }
        try {
            MessageDigest hasher = MessageDigest.getInstance(alg);
            hasher.update(textBytes, 0, textBytes.length);
            if (base64) {
                text = Base64EncodeFilter.base64(hasher.digest());
            } else {
                text = new BigInteger(1, hasher.digest()).toString(16);
            }
        } catch (NoSuchAlgorithmException e2) {
        }
        return text;
    }
}
