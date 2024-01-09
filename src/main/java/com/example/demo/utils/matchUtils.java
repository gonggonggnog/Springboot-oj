package com.example.demo.utils;

import java.io.UnsupportedEncodingException;

public interface matchUtils {
    static boolean Match(String st1, String st2) throws UnsupportedEncodingException {

        st1 = st1.replace("\r\n","");
        st2 = st2.replace("\r\n","");
        st1 = st1.replace("\n","");
        st2 = st2.replace("\n","");
        return st1.equals(st2);
    }
    private static String getUnicodeString(String input) {
        StringBuilder unicodeString = new StringBuilder();
        for (char c : input.toCharArray()) {
            unicodeString.append("\\u").append(Integer.toHexString(c | 0x10000).substring(1));
        }
        return unicodeString.toString();
    }
}
