package com.dispart.utils;
import java.io.File;
import java.io.IOException;

public class EncryptionHelper {
    public EncryptionHelper() {
    }

    public static String printException(Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append(e.toString());
        sb.append("\r\n");
        StackTraceElement[] exceptions = e.getStackTrace();

        for(int i = 0; i < exceptions.length; ++i) {
            StackTraceElement s = exceptions[i];
            sb.append(s);
            sb.append("\r\n");
        }

        String result = sb.toString();
        return result;
    }

    public static byte[] dec2Hex(int data) {
        byte[] bBuffer = new byte[]{(byte)(data >> 8 & 255), (byte)(data & 255)};
        return bBuffer;
    }

    public static String fillDataWith0(String data, int num) {
        if (data.length() > num) {
            data = data.substring(data.length() - num);
        }

        StringBuffer sb0 = new StringBuffer();

        for(int i = 0; i < num; ++i) {
            sb0.append("0");
        }

        StringBuffer sb = new StringBuffer(sb0.toString());
        sb.replace(num - data.length(), num, data);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(fillDataWith0("10000", 12));
    }

    public static byte[] hex2byte(String str) {
        int len = str.length();
        String stmp = null;
        byte[] bt = new byte[len / 2];

        for(int n = 0; n < len / 2; ++n) {
            stmp = str.substring(n * 2, n * 2 + 2);
            bt[n] = (byte)Integer.parseInt(stmp, 16);
        }

        return bt;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs + "";
            }
        }

        return hs.toUpperCase();
    }

    public static File genFile(String fileFullName) throws IOException {
        File file = new File(fileFullName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            if (!file.createNewFile()) {
                throw new IOException("can not create file");
            }
        }

        return file;
    }
}
