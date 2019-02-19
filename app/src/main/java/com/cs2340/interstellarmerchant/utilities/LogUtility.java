package com.cs2340.interstellarmerchant.utilities;

import android.util.Log;

public class LogUtility {
    /**
     * To print large log messages - courtesy of the Proff
     * @param tag - the tag of the log message
     * @param content - the content of the log message
     */
    public static void log(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            log(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }
}
