package org.bohao.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by bohao on 03-29-0029.
 */
public class TimeUtils {
    private static final DateTimeFormatter RFC1123_DATE_TIME_FORMATTER =
            DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
                    .withZoneUTC().withLocale(Locale.US);


    public static String getServerTime() {
        return RFC1123_DATE_TIME_FORMATTER.print(new DateTime());
    }

    public static String toHttpTime(DateTime time) {
        return RFC1123_DATE_TIME_FORMATTER.print(time);
    }
}
