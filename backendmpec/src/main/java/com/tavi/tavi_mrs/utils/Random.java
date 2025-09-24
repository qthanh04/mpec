package com.tavi.tavi_mrs.utils;

public class Random {

    public static String randomCode(){

        long timeSeed = System.nanoTime();

        double randSeed = Math.random() * 1000;

        long midSeed = (long) (timeSeed * randSeed);

        String s = String.valueOf(midSeed);

        String subStr = s.substring(0, 9);

        int finalSeed = Integer.parseInt(subStr);

        return String.valueOf(finalSeed);
    }

    public static String randomPassword(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
}
