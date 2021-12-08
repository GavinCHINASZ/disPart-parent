package com.dispart.vo.horn;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 13:41
 * @ Description:
 */
public class StringUtils {
    public StringUtils() {
    }

    private static byte[] getBytes(String string, Charset charset) {
        return string == null?null:string.getBytes(charset);
    }

    public static byte[] getBytesIso8859_1(String string) {
        return getBytes(string, Charsets.ISO_8859_1);
    }

    public static byte[] getBytesUnchecked(String string, String charsetName) {
        if(string == null) {
            return null;
        } else {
            try {
                return string.getBytes(charsetName);
            } catch (UnsupportedEncodingException var3) {
                throw newIllegalStateException(charsetName, var3);
            }
        }
    }

    public static byte[] getBytesUsAscii(String string) {
        return getBytes(string, Charsets.US_ASCII);
    }

    public static byte[] getBytesUtf16(String string) {
        return getBytes(string, Charsets.UTF_16);
    }

    public static byte[] getBytesUtf16Be(String string) {
        return getBytes(string, Charsets.UTF_16BE);
    }

    public static byte[] getBytesUtf16Le(String string) {
        return getBytes(string, Charsets.UTF_16LE);
    }

    public static byte[] getBytesUtf8(String string) {
        return getBytes(string, Charsets.UTF_8);
    }

    private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }

    private static String newString(byte[] bytes, Charset charset) {
        return bytes == null?null:new String(bytes, charset);
    }

    public static String newString(byte[] bytes, String charsetName) {
        if(bytes == null) {
            return null;
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException var3) {
                throw newIllegalStateException(charsetName, var3);
            }
        }
    }

    public static String newStringIso8859_1(byte[] bytes) {
        return new String(bytes, Charsets.ISO_8859_1);
    }

    public static String newStringUsAscii(byte[] bytes) {
        return new String(bytes, Charsets.US_ASCII);
    }

    public static String newStringUtf16(byte[] bytes) {
        return new String(bytes, Charsets.UTF_16);
    }

    public static String newStringUtf16Be(byte[] bytes) {
        return new String(bytes, Charsets.UTF_16BE);
    }

    public static String newStringUtf16Le(byte[] bytes) {
        return new String(bytes, Charsets.UTF_16LE);
    }

    public static String newStringUtf8(byte[] bytes) {
        return newString(bytes, Charsets.UTF_8);
    }
}
