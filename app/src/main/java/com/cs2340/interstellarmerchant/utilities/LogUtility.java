package com.cs2340.interstellarmerchant.utilities;

import android.util.Log;

/**
 * Class to direct all log messages through
 */
public class LogUtility {
    private static final int maxLogLineLength = 4000;

    /**
     * To print large log messages - courtesy of the Proff
     * @param tag - the tag of the log message
     * @param content - the content of the log message
     */
    public static void log(String tag, String content) {
        if (content.length() > maxLogLineLength) {
            Log.d(tag, content.substring(0, maxLogLineLength));
            log(tag, content.substring(maxLogLineLength));
        } else {
            Log.d(tag, content);
        }
    }
}
