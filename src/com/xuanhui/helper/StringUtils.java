package com.xuanhui.helper;

public final class StringUtils {

    public static boolean isNullOrEmpty(String string){
        return string == null || string.length() == 0;
    }

    public static boolean containsAny(String haystack, String[] needles) {
        if (haystack == null) {
            return false;
        }

        for (String needle : needles) {
            if (haystack.contains(needle)) {
                return true;
            }
        }

        return false;
    }
    
    public static String getAttachmentSizeDescription(long size){
    	String str = "";
    	if(size < 1000){
    		str = size+"B";
    	}else if(size > 1000 && size < 1000*1000){
    		long t = size/1000;
    		str = t+"KB";
    	}else if(size > 1000*1000 && size < 1000*1000*1000){
    		long t = size/(1000*1000);
    		str = t+"MB";
    	}
    	return str;
    }
}
