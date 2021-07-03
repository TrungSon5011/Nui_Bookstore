package com.nui.nuibookstore.common;

public class ReplaceString {
    public static String encodeString(String string){
        return string.replace(".",",");
    }
    public static String decodeString(String string){
        return string.replace(",",".");
    }
}
