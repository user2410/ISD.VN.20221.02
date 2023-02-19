package edu.hust.vn.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String convertSecondsToTimeFormat(long seconds) {
        int hour = (int) (seconds / 3600);
        int minute = (int) ((seconds % 3600) / 60);
        int second = (int) (seconds % 60);
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static String getToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Timestamp stringToTimestamp(String dateString, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDateTime dateTime = date.atStartOfDay();
        return localDateTimeToTimeStamp(dateTime);
    }

    public static Timestamp localDateTimeToTimeStamp(LocalDateTime time){
        ZonedDateTime zonedDateTime = time.atZone(ZoneOffset.UTC);
        return Timestamp.from(zonedDateTime.toInstant());
    }
}
