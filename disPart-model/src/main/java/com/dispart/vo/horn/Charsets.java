package com.dispart.vo.horn;

import java.nio.charset.Charset;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 13:46
 * @ Description:
 */
public class Charsets {
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public Charsets() {
    }

    public static Charset toCharset(Charset charset) {
        return charset == null?Charset.defaultCharset():charset;
    }

    public static Charset toCharset(String charset) {
        return charset == null?Charset.defaultCharset():Charset.forName(charset);
    }
}