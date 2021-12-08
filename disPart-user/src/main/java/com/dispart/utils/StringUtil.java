package com.dispart.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author:xts
 * @date:Created in 2021/8/25 23:34
 * @description
 * @modified by:
 * @version: 1.0
 */
public class StringUtil {
    /**
     * byte[] 转char[]
     * @param bytes
     * @return
     */
    public static char[] getByteToChar(byte[] bytes){
        Charset  cs=Charset.forName("ISO-8859-1");
        ByteBuffer bb =ByteBuffer.allocate(bytes.length);
        bb.put(bytes).flip();
        CharBuffer cb=cs.decode(bb);
        return cb.array();
    }

    public static String getCharToString(char[] chars){
        StringBuffer sb=new StringBuffer();
        for (int i=0 ;i<chars.length;i++){
            sb.append(chars[i]);
        }
        Arrays.fill(chars,' ');
        return sb.toString();
    }
}