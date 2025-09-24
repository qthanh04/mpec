package com.tavi.tavi_mrs.utils;

import java.text.Normalizer;

public class StringUtils {

    public static String replacingAllAccents(String input){
        String str = input.replaceAll("Đ","D");
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{M}]", "");
        return s;
    }

    public static void main(String[] args) {
        System.out.println(new StringUtils().replacingAllAccents("Đống Đa"));
    }
}
