package com.Roman.memorysportssim;

import android.os.SystemClock;

public class timerStuff {
    public String ShowTime(long startTime, boolean upToMillis) {//when upToMillis == true, time is shown in mm:ss.tt format. Otherwise mm:ss
        long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
        int secs = (int) (timeInMilliseconds / 1000);
        int mins = secs / 60;
        int hrs = secs / 3600;
        secs = secs % 60;
        int milliseconds = (int) ((timeInMilliseconds % 1000) / 10);
        String r = "";
        if (hrs != 0)
            r += hrs + ":";
        if (mins != 0 || hrs != 0) {
            if (hrs == 0 && mins < 10)
                r += mins + ":";
            else
                r += String.format("%02d", mins) + ":";
        }
        if (mins == 0 && hrs == 0 && secs < 10)
            r += secs;
        else
            r += String.format("%02d", secs);
        if (upToMillis)
            r += "." + String.format("%02d", milliseconds);
        return r;
    }

}
