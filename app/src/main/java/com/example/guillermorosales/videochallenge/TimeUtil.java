package com.example.guillermorosales.videochallenge;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by guillermo.rosales on 8/13/16.
 */
public class TimeUtil {

    public static String intRawToTimeString(int time) {

        return String.format(Locale.ENGLISH,"%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));

    }

}
